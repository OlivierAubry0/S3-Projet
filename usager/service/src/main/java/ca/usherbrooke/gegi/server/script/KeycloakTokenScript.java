package ca.usherbrooke.gegi.server.script;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
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
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));
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
        JSONArray usersData = new JSONArray();
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
                String username = user.getString("username");
                String email = user.getString("email");
                String firstName = user.getString("firstName");
                String lastName = user.getString("lastName");
                String userId = user.getString("id");

                JSONObject userJson = new JSONObject();
                userJson.put("username", username);
                userJson.put("email", email);
                userJson.put("firstName", firstName);
                userJson.put("lastName", lastName);

                JSONArray rolesArray = getUserRoles(token, userId);
                userJson.put("realmRoles", new JSONArray(rolesArray.toString()));

                JSONArray groupsArray = getUserGroups(token, userId);
                userJson.put("groups", new JSONArray(groupsArray.toString()));

                usersData.put(userJson);
            }
            System.out.println(usersData.toString(2));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static JSONArray getUserRoles(String token, String userId) {
        JSONArray rolesArray= new JSONArray();
        try {
            URL url = new URL("http://localhost:8180/admin/realms/usager/users/" + userId + "/role-mappings/realm");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setRequestProperty("Content-Type", "application/json");

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                output.append(line);
            }
            br.close();

            JSONArray roles = new JSONArray(output.toString());
            for (int i = 0; i < roles.length(); i++) {
                JSONObject role = roles.getJSONObject(i);
                rolesArray.put(role.getString("name"));  // add the role name to rolesArray
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rolesArray;  // return the array of role names
    }
    public static JSONArray getUserGroups(String token, String userId) {
        JSONArray groupsArray = new JSONArray();
        try {
            URL url = new URL("http://localhost:8180/admin/realms/usager/users/" + userId + "/groups");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setRequestProperty("Content-Type", "application/json");

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                output.append(line);
            }
            br.close();

            JSONArray groups = new JSONArray(output.toString());
            for (int i = 0; i < groups.length(); i++) {
                JSONObject group = groups.getJSONObject(i);
                JSONObject groupJson = new JSONObject();
                groupJson.put("name", group.getString("name"));

                JSONArray subGroupsArray = getGroupSubGroups(token, group.getString("id"));
                groupJson.put("subGroups", subGroupsArray);
                groupsArray.put(groupJson);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return groupsArray;
    }

    public static JSONArray getGroupSubGroups(String token, String groupId) {
        JSONArray subGroupsArray = new JSONArray();
        try {
            URL url = new URL("http://localhost:8180/admin/realms/usager/groups/" + groupId);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setRequestProperty("Content-Type", "application/json");

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                output.append(line);
            }
            br.close();

            JSONObject group = new JSONObject(output.toString());
            if (group.has("subGroups")) {
                JSONArray subGroups = group.getJSONArray("subGroups");
                for (int i = 0; i < subGroups.length(); i++) {
                    JSONObject subGroup = subGroups.getJSONObject(i);
                    subGroupsArray.put(subGroup.getString("name"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return subGroupsArray;
    }



    public static void insertUniversite(String universiteNom) throws Exception {
        try {
            Connection con = getConnection();

            String selectQuery = "SELECT * FROM base_de_donne.universite WHERE Universite_Nom = ?";
            PreparedStatement selectPreparedStatement = con.prepareStatement(selectQuery);
            selectPreparedStatement.setString(1, universiteNom);

            ResultSet resultSet = selectPreparedStatement.executeQuery();

            if(!resultSet.next()) {
                String insertQuery = "INSERT INTO base_de_donne.universite (UniversiteID, Universite_Nom) VALUES (?, ?)";

                PreparedStatement insertPreparedStatement = con.prepareStatement(insertQuery);

                int universiteID = generateRandom4DigitKey();
                insertPreparedStatement.setInt(1, universiteID);
                insertPreparedStatement.setString(2, universiteNom);

                insertPreparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertFaculty(String facultyName, String universityName) throws Exception {
        try {
            Connection con = getConnection();

            String selectUniversityQuery = "SELECT UniversiteID FROM base_de_donne.universite WHERE Universite_Nom = ?";
            PreparedStatement selectUniversityPreparedStatement = con.prepareStatement(selectUniversityQuery);
            selectUniversityPreparedStatement.setString(1, universityName);

            ResultSet universityResultSet = selectUniversityPreparedStatement.executeQuery();

            if(universityResultSet.next()) {
                int universityID = universityResultSet.getInt("UniversiteID");

                String selectFacultyQuery = "SELECT 1 FROM base_de_donne.faculte WHERE Faculte_Nom = ? AND UniversiteID = ?";
                PreparedStatement selectFacultyPreparedStatement = con.prepareStatement(selectFacultyQuery);
                selectFacultyPreparedStatement.setString(1, facultyName);
                selectFacultyPreparedStatement.setInt(2, universityID);

                ResultSet facultyResultSet = selectFacultyPreparedStatement.executeQuery();

                if(!facultyResultSet.next()) {
                    String insertQuery = "INSERT INTO base_de_donne.faculte (FaculteID, Faculte_Nom, UniversiteID) VALUES (?, ?, ?)";

                    PreparedStatement insertPreparedStatement = con.prepareStatement(insertQuery);

                    int facultyID = generateRandom4DigitKey();
                    insertPreparedStatement.setInt(1, facultyID);
                    insertPreparedStatement.setString(2, facultyName);
                    insertPreparedStatement.setInt(3, universityID);

                    insertPreparedStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertUser(String userId, String lastName, String firstName, String role, String facultyName) {
        try {
            Connection con = getConnection();

            String selectFacultyQuery = "SELECT FaculteID FROM base_de_donne.faculte WHERE Faculte_Nom = ?";
            PreparedStatement selectFacultyPreparedStatement = con.prepareStatement(selectFacultyQuery);
            selectFacultyPreparedStatement.setString(1, facultyName);

            ResultSet facultyResultSet = selectFacultyPreparedStatement.executeQuery();

            if(facultyResultSet.next()) {
                int facultyID = facultyResultSet.getInt("FaculteID");

                String selectUserQuery = "SELECT 1 FROM base_de_donne.usager WHERE UsagerID = ?";
                PreparedStatement selectUserPreparedStatement = con.prepareStatement(selectUserQuery);
                selectUserPreparedStatement.setString(1, userId);

                ResultSet userResultSet = selectUserPreparedStatement.executeQuery();

                if(!userResultSet.next()) {
                    String insertQuery = "INSERT INTO base_de_donne.usager (UsagerID, Usager_Nom, Usager_Prenom, Usager_Role, FaculteID) VALUES (?, ?, ?, ?, ?)";

                    PreparedStatement insertPreparedStatement = con.prepareStatement(insertQuery);

                    insertPreparedStatement.setString(1, userId);
                    insertPreparedStatement.setString(2, lastName);
                    insertPreparedStatement.setString(3, firstName);
                    insertPreparedStatement.setString(4, role);
                    insertPreparedStatement.setInt(5, facultyID);

                    insertPreparedStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private static int generateRandom4DigitKey() {
        Random random = new Random();
        return random.nextInt(9000) + 1000;
    }


    public static boolean universiteExists(String universiteName) {
        String query = "SELECT COUNT(*) FROM base_de_donne.universite WHERE Universite_Nom = ?";
        try (Connection con = getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query)) {

            preparedStatement.setString(1, universiteName);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean facultyExists(String facultyName, String universiteName) {
        String query = "SELECT COUNT(*) FROM base_de_donne.faculte JOIN base_de_donne.universite ON faculte.UniversiteID = universite.UniversiteID WHERE faculte.Faculte_Nom = ? AND universite.Universite_Nom = ?";
        try (Connection con = getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query)) {

            preparedStatement.setString(1, facultyName);
            preparedStatement.setString(2, universiteName);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
