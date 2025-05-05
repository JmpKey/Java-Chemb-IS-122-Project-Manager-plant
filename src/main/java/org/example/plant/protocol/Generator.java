package org.example.plant.protocol;

import org.jfree.data.category.CategoryDataset;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface Generator {
    void createChartS(Map<String, Integer> data, String chartTitle, String fileName) throws IOException;

    CategoryDataset createDatasetS(Map<String, Integer> data);

    void createHtmlReport(String title, String imagePath, String fileName) throws IOException;

    void generateHtmlReport(String user, List<Forwarding> tasks, String filename) throws IOException;

    void generateTaskReport(DbCall base, String userName, String startDate, String endDate);
}
