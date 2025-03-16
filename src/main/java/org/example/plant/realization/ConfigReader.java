package org.example.plant.realization;

import org.example.plant.protocol.Adjustment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConfigReader implements Adjustment {
    private String filePath;

    public ConfigReader(String conf_n) {
        this.filePath = conf_n;
    }

    @Override
    public List<String> readConfigValues() throws IOException {
        List<String> values = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\[\\w+\\] = \\[(.+)\\]"); // Regex для поиска значений

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.matches()) {
                    values.add(matcher.group(1)); // Извлекаем текст в скобках
                }
            }
        }
        return values;
    }
}
