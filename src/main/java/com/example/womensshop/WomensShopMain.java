package com.example.womensshop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class WomensShopMain extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(WomensShopMain.class.getResource("womensShop.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1141, 899);
        stage.setTitle("Womens-Shop");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {launch();}
}