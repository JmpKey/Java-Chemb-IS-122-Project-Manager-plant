package org.example.plant.realization;

import org.example.plant.protocol.Adjustment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConfigReader implements Adjustment {
    private static ConfigReader instance;

    public static ConfigReader getInstance() {
        if (instance == null) {
            instance = new ConfigReader();
        }
        return instance;
    }

    @Override
    public List<String> readConfigValuesDb() throws IOException {
        List<String> values = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\[\\w+\\] = \\[(.+)\\]"); // Regex to search for values

        String filePath = "conf/connect.conf";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.matches()) {
                    values.add(matcher.group(1)); // Extract the text in parentheses
                }
            }
        }
        return values;
    }

    @Override
    public List<String> readConfigValuesMail() {
        String filePath = "conf/mail.conf";
        List<String> values = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // remove the spaces and check that the string is not empty.
                line = line.trim();
                if (!line.isEmpty()) {
                    // Extracting the value from the string by removing the key and delimiters
                    String[] parts = line.split("=");
                    if (parts.length == 2) {
                        String value = parts[1].trim().replaceAll("[\\[\\]]", ""); // Removing the square brackets
                        values.add(value);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Exception handling
        }

        return values;
    }

    public void userRemoval(Connection connection) {
        String filePath = "conf/guru_secession";

        File file = new File(filePath);
        if (!file.exists()) {
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String username;
            while ((username = br.readLine()) != null) {
                // We get the user ID by name
                DataBase base = new DataBase();
                Integer userId = base.getUserIdByUserNameDel(connection, username.trim());
                if (userId != null) {
                    base.clearAssignedTasks(connection, userId); // Clearing the ASSIGNED_TASK fields in TASKS
                    base.deleteUser(connection, userId); // Deleting user data
                    base.deleteUserFromBd(connection, username); // Let's remember and forget
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
