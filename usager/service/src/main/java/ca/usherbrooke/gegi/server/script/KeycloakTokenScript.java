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
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
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

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
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
                System.out.println("  ");

                System.out.println("Username: " + user.getString("username"));
                System.out.println("Email: " + user.getString("email"));
                System.out.println("First Name: " + user.getString("firstName"));
                System.out.println("Last Name: " + user.getString("lastName"));
                // get the user's roles
                JSONArray rolesArray = getUserRoles(token, userId);
                String role = null;
                for (int j = 0; j < rolesArray.length(); j++) {
                    String roleCandidate = rolesArray.getJSONObject(j).getString("name");
                    if (!"default-roles-master".equals(roleCandidate)) {
                        role = roleCandidate;
                        break;
                    }
                }
                // get the user's groups
                JSONArray groupsArray = getUserGroups(token, userId);
                String groupName = (groupsArray.length() > 0) ? groupsArray.getJSONObject(0).getString("name") : null;

                // insert the user into the usager table
                insertUser(userId, lastName, firstName, role, groupName);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static JSONArray getUserRoles(String token, String userId) {
        JSONArray rolesArray= null;
        try {
            URL url = new URL("http://localhost:8180/admin/realms/usager/users/" + userId + "/role-mappings/realm");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setRequestProperty("Content-Type", "application/json");

            System.out.println("Response Content-Type: " + conn.getHeaderField("Content-Type"));  // add this line


            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                output.append(line);
            }
            br.close();

            rolesArray = new JSONArray(output.toString());
            System.out.println("Roles:");
            for (int i = 0; i < rolesArray.length(); i++) {
                JSONObject role = rolesArray.getJSONObject(i);
                System.out.println("- " + role.getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rolesArray;
    }

    public static JSONArray getUserGroups(String token, String userId) {
        JSONArray groupsArray= null;
        try {
            URL url = new URL("http://localhost:8180/admin/realms/usager/users/" + userId + "/groups");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setRequestProperty("Content-Type", "application/json");

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                output.append(line);
            }
            br.close();

            groupsArray = new JSONArray(output.toString());
            System.out.println("Groups:");
            for (int i = 0; i < groupsArray.length(); i++) {
                JSONObject group = groupsArray.getJSONObject(i);
                System.out.println("- " + group.getString("name"));
                String groupName = group.getString("name");
                String groupId = group.getString("id");
                getGroupSubGroups(token, groupId, groupName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return groupsArray;
    }

    public static void getGroupSubGroups(String token, String groupId, String groupName) {
        try {
            URL url = new URL("http://localhost:8180/admin/realms/usager/groups/" + groupId);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setRequestProperty("Content-Type", "application/json");

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                output.append(line);
            }
            br.close();

            JSONObject group = new JSONObject(output.toString());
            if (group.has("subGroups")) {
                JSONArray subGroupsArray = group.getJSONArray("subGroups");

                // Only add to database if there's exactly one subgroup
                if (subGroupsArray.length() == 1) {
                    System.out.println("Sub-Group:");
                    JSONObject subGroup = subGroupsArray.getJSONObject(0);
                    String universityName = subGroup.getString("name");

                    // Generate a random 4 digit university ID and faculty ID
                    Random rand = new Random();
                    String universityID = String.format("%04d", rand.nextInt(10000));
                    String facultyID = String.format("%04d", rand.nextInt(10000));

                    insertIntoDatabase(universityID, universityName);
                    insertIntoFaculte(facultyID, groupName, universityName);
                    System.out.println("- " + universityName);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    public static void insertUser(String userId, String lastName, String firstName, String role, String groupName) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // Get a connection to the database
            conn = DatabaseConnection.getConnection();

            // First check if user already exists
            String checkUserSql = "SELECT 1 FROM BASE_DE_DONNE.USAGER WHERE UsagerID = ?";
            pstmt = conn.prepareStatement(checkUserSql);
            pstmt.setString(1, userId);
            rs = pstmt.executeQuery();

            if(rs.next()) {
                // User already exists, skip this user
                System.out.println("User with id: " + userId + " already exists. Skipping...");
                return;
            }
            pstmt.close(); // Close the previous PreparedStatement

            // Get faculty id if it exists
            String facultyId = null;
            String sqlFaculty = "SELECT FaculteID FROM BASE_DE_DONNE.FACULTE WHERE Faculte_Nom = ?";
            pstmt = conn.prepareStatement(sqlFaculty);
            pstmt.setString(1, groupName);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                facultyId = rs.getString("FaculteID");
            }
            rs.close(); // Close the previous ResultSet
            pstmt.close(); // Close the previous PreparedStatement

            // Prepare the SQL statement
            String sqlUser;
            if (facultyId != null) {
                sqlUser = "INSERT INTO BASE_DE_DONNE.USAGER (UsagerID, Usager_Nom, Usager_Prenom, Usager_Role, FaculteID) VALUES (?, ?, ?, ?, ?)";
                pstmt = conn.prepareStatement(sqlUser);

                // Set the values
                pstmt.setString(1, userId);
                pstmt.setString(2, lastName);
                pstmt.setString(3, firstName);
                pstmt.setString(4, role);
                pstmt.setString(5, facultyId);
            } else {
                sqlUser = "INSERT INTO BASE_DE_DONNE.USAGER (UsagerID, Usager_Nom, Usager_Prenom, Usager_Role) VALUES (?, ?, ?, ?)";
                pstmt = conn.prepareStatement(sqlUser);

                // Set the values
                pstmt.setString(1, userId);
                pstmt.setString(2, lastName);
                pstmt.setString(3, firstName);
                pstmt.setString(4, role);
            }

            // Execute the statement
            pstmt.executeUpdate();

            System.out.println("User inserted successfully");

        } catch (Exception ex) {
            System.out.println(ex.getMessage());

        } finally {
            // Finally block to close resources
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }



    public static void insertIntoDatabase(String universityID, String universityName) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Check if university is already in the database
            String sqlCheck = "SELECT * FROM base_de_donne.UNIVERSITE WHERE Universite_Nom = ?";
            PreparedStatement statementCheck = connection.prepareStatement(sqlCheck);
            statementCheck.setString(1, universityName);
            ResultSet resultSet = statementCheck.executeQuery();

            // If university is not already in the database, add it
            if (!resultSet.next()) {
                String sqlInsert = "INSERT INTO base_de_donne.UNIVERSITE (UniversiteID, Universite_Nom) VALUES (?, ?)";
                PreparedStatement statementInsert = connection.prepareStatement(sqlInsert);
                statementInsert.setString(1, universityID);
                statementInsert.setString(2, universityName);
                statementInsert.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insertIntoFaculte(String facultyID, String facultyName, String universityName) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Find the university ID in the UNIVERSITE table
            String sqlSelect = "SELECT * FROM base_de_donne.UNIVERSITE WHERE Universite_Nom = ?";
            PreparedStatement statementSelect = connection.prepareStatement(sqlSelect);
            statementSelect.setString(1, universityName);
            ResultSet resultSet = statementSelect.executeQuery();

            // If university found, insert new faculty
            if (resultSet.next()) {
                String universityID = resultSet.getString("UniversiteID");

                // Check if faculty with the same name and university ID is already in the database
                String sqlCheck = "SELECT * FROM base_de_donne.FACULTE WHERE Faculte_Nom = ? AND UniversiteID = ?";
                PreparedStatement statementCheck = connection.prepareStatement(sqlCheck);
                statementCheck.setString(1, facultyName);
                statementCheck.setString(2, universityID);
                ResultSet resultSetCheck = statementCheck.executeQuery();

                // If faculty is not already in the database, add it
                if (!resultSetCheck.next()) {
                    String sqlInsert = "INSERT INTO base_de_donne.FACULTE (FaculteID, Faculte_Nom, UniversiteID) VALUES (?, ?, ?)";
                    PreparedStatement statementInsert = connection.prepareStatement(sqlInsert);
                    statementInsert.setString(1, facultyID);
                    statementInsert.setString(2, facultyName);
                    statementInsert.setString(3, universityID);
                    statementInsert.executeUpdate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
