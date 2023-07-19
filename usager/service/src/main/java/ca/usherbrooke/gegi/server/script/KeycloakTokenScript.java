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
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

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
        JSONArray usersData = new JSONArray();
        try {
            URL url = new URL("http://localhost:8180/admin/realms/usager/users");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            String contentType = conn.getHeaderField("Content-Type");
            System.out.println(contentType);

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
            conn.setRequestProperty("Accept-Charset", "UTF-8");


            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
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

}
