/**
 * Ce projet est créé pour gérer un magasin de femme
 * @author romainpasquier
 * @version 1.0
 * @since 2022
 */
package com.example.womensshop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * La class WomensShopMain represente la classe de lancement du projet
 * @author romainpasquier
 **/
public class WomensShopMain extends Application {
    /**
     * La methode start représente le lancement de l'interface graphique connecté à nos classes java
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(WomensShopMain.class.getResource("womensShop.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1141, 899);
        stage.setTitle("Womens-Shop");
        stage.setScene(scene);
        stage.show();
    }
    /**
     * La methode main est le main de la classe
     * @param args
     */
    public static void main(String[] args) {launch();}
}