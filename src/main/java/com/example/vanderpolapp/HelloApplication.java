package com.example.vanderpolapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private TextField paramBField;
    private LineChart<Number, Number> chart;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Розпізнавання встановлених процесів (рівняння Ван дер Поля)");

        // Введення параметра b
        Label paramBLabel = new Label("Введіть параметр b:");
        paramBField = new TextField("0.01");

        // Кнопка для запуску обчислень
        Button calculateButton = new Button("Розпочати обчислення");
        calculateButton.setOnAction(e -> calculate());

        // Налаштування графіка
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Час");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("x");

        chart = new LineChart<>(xAxis, yAxis);
        chart.setTitle("Графік змінної x(t)");

        // Розміщення елементів
        VBox layout = new VBox(10, paramBLabel, paramBField, calculateButton, chart);
        Scene scene = new Scene(layout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void calculate() {
        double b;
        try {
            b = Double.parseDouble(paramBField.getText());
        } catch (NumberFormatException e) {
            paramBField.setText("Неправильний ввід");
            return;
        }

        double h = 0.005;  // Крок часу
        double x = 1.0, v = 0.0;  // Початкові значення
        int steps = 1000;  // Кількість кроків інтегрування

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("x(t) при b = " + b);

        // Ітераційний процес із чисельного методу Ейлера
        for (int i = 0; i < steps; i++) {
            double t = i * h;
            double newX = x + h * v;
            double newV = v + h * (b * (1 - x * x) * v - x);

            series.getData().add(new XYChart.Data<>(t, x));
            x = newX;
            v = newV;
        }

        chart.getData().clear();
        chart.getData().add(series);
    }
}