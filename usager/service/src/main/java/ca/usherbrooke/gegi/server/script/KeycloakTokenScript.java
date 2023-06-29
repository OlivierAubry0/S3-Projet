package ca.usherbrooke.gegi.server.script;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

import static ca.usherbrooke.gegi.server.script.DatabaseConnection.getConnection;

public class KeycloakTokenScript {

    public static void main(String[] args) {
        // Set the admin credentials
        String ADMIN_NAME = "admin";
        String ADMIN_PASSWORD = "admin";

        try {
            // cURL command
            String command = String.format("curl http://localhost:8180/realms/master/protocol/openid-connect/token " +
                            "-d \"client_id=admin-cli\" -d \"username=%s\" -d \"password=%s\" -d \"grant_type=password\"",
                    ADMIN_NAME, ADMIN_PASSWORD);

            // Execute
            Process process = Runtime.getRuntime().exec(command);

            // Read
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();

            JSONObject jsonResponse = new JSONObject(response.toString());
            String accessToken = jsonResponse.getString("access_token");

            System.out.println("Access Token: " + accessToken);
            System.out.println("  ");

            getUsers(accessToken);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getUsers(String token) {
        try {
            URL url = new URL("http://localhost:8180/admin/realms/usager/users");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                output.append(line);
            }
            br.close();

            JSONArray usersArray = new JSONArray(output.toString());
            for (int i = 0; i < usersArray.length(); i++) {
                JSONObject user = usersArray.getJSONObject(i);
                System.out.println("Username: " + user.getString("username"));
                System.out.println("Email: " + user.getString("email"));
                System.out.println("First Name: " + user.getString("firstName"));
                System.out.println("Last Name: " + user.getString("lastName"));
                String userId = user.getString("id");
                getUserRoles(token, userId);
                getUserGroups(token, userId);
                System.out.println("  ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getUserRoles(String token, String userId) {
        try {
            URL url = new URL("http://localhost:8180/admin/realms/usager/users/" + userId + "/role-mappings/realm");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setRequestProperty("Content-Type", "application/json");

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                output.append(line);
            }
            br.close();

            JSONArray rolesArray = new JSONArray(output.toString());
            System.out.println("Roles:");
            for (int i = 0; i < rolesArray.length(); i++) {
                JSONObject role = rolesArray.getJSONObject(i);
                System.out.println("- " + role.getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getUserGroups(String token, String userId) {
        try {
            URL url = new URL("http://localhost:8180/admin/realms/usager/users/" + userId + "/groups");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setRequestProperty("Content-Type", "application/json");

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                output.append(line);
            }
            br.close();

            JSONArray groupsArray = new JSONArray(output.toString());
            System.out.println("Groups:");
            for (int i = 0; i < groupsArray.length(); i++) {
                JSONObject group = groupsArray.getJSONObject(i);
                System.out.println("- " + group.getString("name"));
                String groupId = group.getString("id");
                getGroupSubGroups(token, groupId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getGroupSubGroups(String token, String groupId) {
        try {
            URL url = new URL("http://localhost:8180/admin/realms/usager/groups/" + groupId);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setRequestProperty("Content-Type", "application/json");

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                output.append(line);
            }
            br.close();

            JSONObject group = new JSONObject(output.toString());
            if(group.has("subGroups")) {
                JSONArray subGroupsArray = group.getJSONArray("subGroups");
                System.out.println("Sub-Groups:");
                for (int i = 0; i < subGroupsArray.length(); i++) {
                    JSONObject subGroup = subGroupsArray.getJSONObject(i);
                    System.out.println("- " + subGroup.getString("name"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isGroupExists(String groupName) {
        try {
            Connection conn = getConnection(); // Establish a database connection
            String query = "SELECT COUNT(*) FROM universite WHERE Universite_Nom = ?";
            PreparedStatement stmt = null;
            try {
                stmt = conn.prepareStatement(query);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            stmt.setString(2, groupName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public static void insertGroup(String groupName) {
        try {
            Connection conn = getConnection(); // Establish a database connection
            String query = "INSERT INTO universite (UniversiteID, Universite_Nom) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(2, groupName);
            stmt.setString(1, generateRandomKey());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:5432/base_de_donne";
        String username = "postgres";
        String password = "postgres";
        return DriverManager.getConnection(url, username, password);
    }


    public static String generateRandomKey() {
        Random random = new Random();
        int key = random.nextInt(9000) + 1000; // Generate a random 4-digit number
        return String.valueOf(key);
    }
}
