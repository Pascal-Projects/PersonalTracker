package com.pascalprojects.personaltracker;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 * JavaFX App for tracking sleep and water intake
 */
public class Main extends Application {

    /**
     * Starts the application
     *
     * @param stage the stage to be displayed
     */
    @Override
    public void start(Stage stage) {

        // Create the text input dialogs to enter the amount of water and sleep for the day
        TextInputDialog tdw = new TextInputDialog();
        tdw.setHeaderText("Enter the amount of water you drank today");
        tdw.setTitle("Water");
        tdw.setGraphic(new ImageView(Objects.requireNonNull(this.getClass().getResource("water.png")).toString()));

        TextInputDialog tds = new TextInputDialog();
        tds.setHeaderText("Enter the amount of hours you slept today");
        tds.setTitle("Sleep");
        tds.setGraphic(new ImageView(Objects.requireNonNull(this.getClass().getResource("sleep.png")).toString()));

        // Create the root pane and the bar chart to display the data centered
        BorderPane root = new BorderPane();
        BarChart<String, Number> barChart = createChart();
        root.setCenter(barChart);
        root.setId("pane");

        // Create the scene
        Scene scene = new Scene(root, 600, 600);

        // Add event handlers for the shortcuts to enter the data
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if (key.getCode().toString().equals("W")) {
                tdw.showAndWait();
                if (tdw.getResult() != null) {
                    addWaterToday(tdw.getResult(), barChart);
                }
            }
        });

        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if (key.getCode().toString().equals("S")) {tds.showAndWait();
                if (tds.getResult() != null) {
                    addSleepToday(tds.getResult(), barChart);
                }
            }
        });

        // Set the stage
        stage.setTitle("Personal Tracker");
        stage.setScene(scene);
        stage.getScene().getStylesheets().add(Objects.requireNonNull(Main.class.getResource("main.css")).toExternalForm());
        stage.show();
    }

    /**
     * Adds the amount of water drank today to the bar chart
     * @param pWater the amount of water drank today
     * @param pBarChart the bar chart to add the data to
     */
    public void addWaterToday(String pWater, BarChart<String, Number> pBarChart) {
        XYChart.Series<String, Number> series = pBarChart.getData().get(0);
        SimpleDateFormat format = new SimpleDateFormat("EEEE", Locale.ENGLISH);
        format.setTimeZone(format.getTimeZone());
        series.getData().add(new XYChart.Data<>(format.format(new Date()), Double.parseDouble(pWater)));
    }

    /**
     * Adds the amount of sleep today to the bar chart
     * @param pSleep the amount of sleep today
     * @param pBarChart the bar chart to add the data to
     */
    public void addSleepToday(String pSleep, BarChart<String, Number> pBarChart) {
        XYChart.Series<String, Number> series = pBarChart.getData().get(1);
        SimpleDateFormat format = new SimpleDateFormat("EEEE", Locale.ENGLISH);
        format.setTimeZone(format.getTimeZone());
        series.getData().add(new XYChart.Data<>(format.format(new Date()), Double.parseDouble(pSleep)));
    }

    /**
     * Creates the bar chart
     * @return the bar chart
     */
    public BarChart<String, Number> createChart() {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setCategories(FXCollections.observableArrayList(Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")));
        xAxis.setLabel("Day");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Amount");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Personal Tracker");

        barChart.setLegendVisible(true);
        barChart.setLegendSide(javafx.geometry.Side.BOTTOM);

        return addData(barChart);
    }

    /**
     * Adds the data to the bar chart
     * @param pBarchart the bar chart to add the data to
     * @return the bar chart with the data added
     *
     * TODO: Add the data to a database running on a raspberry pi and read the data from the database
     */
    public BarChart<String, Number> addData(BarChart<String, Number> pBarchart) {
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("Hours of sleep");
        series1.getData().add(new XYChart.Data<>("Monday", 8));
        series1.getData().add(new XYChart.Data<>("Tuesday", 7));
        series1.getData().add(new XYChart.Data<>("Wednesday", 6));
        series1.getData().add(new XYChart.Data<>("Thursday", 7));
        series1.getData().add(new XYChart.Data<>("Friday", 7.5));
        series1.getData().add(new XYChart.Data<>("Saturday", 8));
        series1.getData().add(new XYChart.Data<>("Sunday", 4));

        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        series2.setName("Liters of Water");
        series2.getData().add(new XYChart.Data<>("Monday", 2));
        series2.getData().add(new XYChart.Data<>("Tuesday", 3));
        series2.getData().add(new XYChart.Data<>("Wednesday", 2));
        series2.getData().add(new XYChart.Data<>("Thursday", 1.5));
        series2.getData().add(new XYChart.Data<>("Friday", 2));
        series2.getData().add(new XYChart.Data<>("Saturday", 1));
        series2.getData().add(new XYChart.Data<>("Sunday", 4));

        pBarchart.getData().add(0, series1);
        pBarchart.getData().add(1, series2);
        return pBarchart;
    }

    /**
     * Launches the application
     * @param args the arguments
     */
    public static void main(String[] args) {
        launch();
    }
}