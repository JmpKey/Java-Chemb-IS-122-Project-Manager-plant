package org.example.plant.realization;

import org.example.plant.protocol.DbCall;
import org.example.plant.protocol.FileNGen;
import org.example.plant.protocol.Forwarding;
import org.example.plant.protocol.Generator;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class StatisticsGenerator implements Generator {
    private static Generator instance;

    public static Generator getInstance() {
        if (instance == null) {
            instance = new StatisticsGenerator();
        }
        return instance;
    }

    @Override
    public void createChartS(Map<String, Integer> data, String chartTitle, String fileName) throws IOException {
        CategoryDataset dataset = createDatasetS(data);
        JFreeChart chart = ChartFactory.createBarChart(chartTitle, "Пользователи", "Подсчет", dataset);
        ChartUtils.saveChartAsPNG(new File(fileName), chart, 800, 600);
    }

    @Override
    public CategoryDataset createDatasetS(Map<String, Integer> data) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            dataset.addValue(entry.getValue(), "Tasks", entry.getKey().toString());
        }
        return dataset;
    }

    @Override
    public void createHtmlReport(String title, String imagePath, String fileName) throws IOException {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("<html><head><title>" + title + "</title></head><body>");
            writer.write("<h1>" + title + "</h1>");
            writer.write("<img src='" + imagePath + "' alt='" + title + "'/>");
            writer.write("</body></html>");
        }
    }

    @Override
    public void generateHtmlReport(String user, List<Forwarding> tasks, String filename) throws IOException {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("<html><head><title>Отчёт по задачам</title></head><body>");
            writer.write("<h1>Отчёт по задачам пользователя: " + user + "</h1>");
            writer.write("<table border='1'><tr><th>Название</th><th>Статус</th><th>Статус выполения</th></tr>"); // Добавлено поле Resolution Status

            for (Forwarding task : tasks) {
                String status = task.isResolved() ? "Выполненно" : "Не выполненно";
                writer.write("<tr><td>" + task.getName() + "</td><td>" + task.getStatus() + "</td><td>" + status + "</td></tr>"); // Отображение статуса задачи и статуса выполнения
            }

            writer.write("</table></body></html>");
        }
        System.out.println("Report generated successfully!");
    }

    @Override
    public void generateTaskReport(DbCall base, String userName, String startDate, String endDate) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"); // dd-MM-yyyy  yyyy-MM-dd
            Date parsedDate1 = null; // Получаем java.util.Date
            Date parsedDate2 = null;
            try {
                parsedDate1 = dateFormat.parse(startDate);
                parsedDate2 = dateFormat.parse(endDate);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            Timestamp startD = new Timestamp(parsedDate1.getTime());  // Преобразуем в java.sql.Timestamp
            Timestamp endD = new Timestamp(parsedDate2.getTime());

            List<Forwarding> tasks = base.getTasks(base.getUserIdByName(userName), startD, endD);

            FileNGen merge = MergeGen.getInstance();
            String htmlReportFileName = merge.mergeName("reports/task_report_") + "_" + userName + ".html";

            generateHtmlReport(userName, tasks, htmlReportFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
