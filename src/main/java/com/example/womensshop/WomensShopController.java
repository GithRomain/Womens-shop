/**
 * Ce projet est créé pour gérer un magasin de femme
 * @author romainpasquier
 * @version 1.0
 * @since 2022
 */
package com.example.womensshop;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * La class WomensShopController represente le controller de l'interface graphique à l'aide de notre code java
 * @author romainpasquier
 **/
public class WomensShopController implements Initializable {
    //Attributs
    /**
     * manager est l'attribut qui controller notre BDD SQL
     */
    DBManager manager;
    /**
     * produitSelected est l'attribut qui correspond au dernier produits selectionné dans les 3 listes
     */
    Produit produitSelected = null;
    /**
     * L'attribut chooseTypeBox est la controllerBox qui sélectionne le type de produit
     */
    @FXML
    private ComboBox<String> chooseTypeBox;
    /**
     * L'attribut lblBilanSet est l'attribut qui correspond à l'affichage du Bilan du magasin
     */
    @FXML
    private Label lblBilanSet;
    /**
     * L'attribut lblCostSet est l'attribut qui correspond à l'affichage du cout total de l'entreprise
     */
    @FXML
    private Label lblCostSet;
    /**
     * L'attribut lblRecetteSet est l'attribut qui correspond à l'affichage de la recette total de l'entreprise
     */
    @FXML
    private Label lblRecetteSet;
    /**
     * L'attribut listAccessoire correspond à la liste de tous les accessoires
     */
    @FXML
    private ListView<Produit> listAccessoire;
    /**
     * L'attribut listChaussure correspond à la liste de toutes les choussures
     */
    @FXML
    private ListView<Produit> listChaussure;
    /**
     * L'attribut listVetement correspond à la liste de tous les vetements
     */
    @FXML
    private ListView<Produit> listVetement;
    /**
     * L'attribut sliderPointure correspond à la pointure de chaussure à selectionné
     */
    @FXML
    private Slider sliderPointure;
    /**
     * L'attribut sliderTaille correspond à la taille de vetements à selectionné
     */
    @FXML
    private Slider sliderTaille;
    /**
     * L'attribut textFieldName correspond au nom du produit à renseigné
     */
    @FXML
    private TextField textFieldName;
    /**
     * L'attribut textFielNbExemplaires correspond au nombre d'exemplaire du produit à renseigné pour ajouter un objet
     */
    @FXML
    private TextField textFieldNbExemplaires;
    /**
     * L'attribut textFieldNbExemplaires2 correspond au nom du produit à renseigné pour l'achat ou la vente d'un produit
     */
    @FXML
    private TextField textFieldNbExemplaires2;
    /**
     * L'attribut textFielPrix correspond au prix du produit à renseigné pour ajouter un objet
     */
    @FXML
    private TextField textFieldPrix;
    /**
     * L'attribut textFieldPrix2 correspond au prix du produit à renseigné pour l'achat d'un produit
     */
    @FXML
    private TextField textFieldPrix2;

    //Getters
    /**
     * Getter de l'attribut produitSelected
     * @return produitSelected
     */
    public Produit getProduitSelected() {
        return produitSelected;
    }

    //Setters
    /**
     * Setter de l'attribut produitSelected
     * @param produitSelected
     */
    public void setProduitSelected(Produit produitSelected) {
        this.produitSelected = produitSelected;
    }

    //Methods
    /**
     * Méthode qui va récupérer les infos de cost et recette en BDD et les afficher graphiquement
     */
    private void fetchInfo(){
        //Get info from BDD
        manager.selectInfo();
        //Copy info into the interface
        lblCostSet.setText(String.valueOf(Produit.getCost()));
        lblRecetteSet.setText(String.valueOf(Produit.getRecette()));
        lblBilanSet.setText(String.valueOf(Produit.getRecette() - Produit.getCost()));
    }
    /**
     * Méthode pour récupérer la liste des Produits et la trier selon le type des produits dans les 3 ListView définis
     */
    private void fetchList(){
        //Load les produits de la BDD
        manager.loadProduct();
        //Trier la liste de liste (pour les 3 types de produits selon leurs prix)
        DBManager.sortList();
        //Fixer la liste de listes des produits
        List<List<Produit>> listProduits = DBManager.getProduitList();

        //Récupérer les 3 sous listes différentes pour les 3 types de produits
        ObservableList<Produit> listVetements = FXCollections.observableArrayList(listProduits.get(0));
        ObservableList<Produit> listChaussures = FXCollections.observableArrayList(listProduits.get(1));
        ObservableList<Produit> listAccessoires = FXCollections.observableArrayList(listProduits.get(2));

        //Attribuer aux listView leurs data
        listChaussure.setItems(listChaussures);
        listVetement.setItems(listVetements);
        listAccessoire.setItems(listAccessoires);
    }
    /**
     * Méthode pour écouter tous les evenements de toutes les listsViews
     */
    private void listenEventLists(){
        listsListener(listVetement);
        listsListener(listChaussure);
        listsListener(listAccessoire);
    }
    /**
     * Méthode pour écouter les évenement d'une ListView
     * @param list
     */
    private void listsListener(ListView<Produit> list) {
        list.getSelectionModel().selectedItemProperty().addListener((observableValue, produit, newProduit) -> {
            try {
                onCancel();
                changeInterface(newProduit, produit);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }
    /**
     * Méthode pour écouter les évenement du bouton choose type
     * @param chooseTypeBox
     */
    public void ChooseTypeListener(ComboBox<String> chooseTypeBox) {
        chooseTypeBox.getSelectionModel().selectedItemProperty().addListener((observableValue, type, newType) -> {
        if (newType.equals("Vetement")){
            sliderPointure.setDisable(true);
            sliderTaille.setDisable(false);
        }
        else if (newType.equals("Chaussure")){
            sliderTaille.setDisable(true);
            sliderPointure.setDisable(false);
        }
        else{
            sliderTaille.setDisable(true);
            sliderPointure.setDisable(true);
        }
        });
    }
    /**
     * Méthodes pour changer les Fields graphiquement selon la sélection d'un produit
     * @param newProduit
     * @param produit
     * @throws Exception
     */
    private void changeInterface(Produit newProduit, Produit produit) throws Exception {
        //On vérifie que le produit selectionné n'est pas null
        if (newProduit != null) {
            //Changements des fields
            chooseTypeBox.setDisable(true);
            chooseTypeBox.setValue(newProduit.getClass().toString().substring(29));
            textFieldName.setText(newProduit.getNom());
            textFieldPrix.setText(String.valueOf(newProduit.getPrix()));
            textFieldNbExemplaires.setText(String.valueOf(newProduit.getNbExemplaires()));
            if (Vetement.class.equals(newProduit.getClass()))
                sliderTaille.setValue(((Vetement) newProduit).getTaille());
            if (Chaussure.class.equals(newProduit.getClass()))
                sliderPointure.setValue(((Chaussure) newProduit).getPointure());
            //Set le produit sélectionné pour travailler sur ce dernier (dernier élement selectionné)
            setProduitSelected(newProduit);
        }
        //Lors de la première vérification on vérifie que le nouveau produit est bien différent de l'ancien
        else if (newProduit != produit) System.out.println("Premiere modification");
        //Throw une exception si on essaie d'acceder à la fonction sans selectionné de produit ou si l'objet est null
        else throw new Exception("Produit selectionné deja nul");
    }

    /**
     * Méthode qui clear tous les fields pour les reset et reset la selection de produit grâce à l'interface
     */
    public void onCancel(){
        //Clear les différentes selections dans les listView
        listVetement.getSelectionModel().clearSelection();
        listChaussure.getSelectionModel().clearSelection();
        listAccessoire.getSelectionModel().clearSelection();

        //After a cancel you can select the chooseType bouton
        chooseTypeBox.setDisable(false);
        sliderTaille.setDisable(false);
        sliderPointure.setDisable(false);
        //Reset Fields
        chooseTypeBox.setValue("");
        textFieldName.clear();
        textFieldPrix.clear();
        textFieldNbExemplaires.clear();
        sliderTaille.setValue(sliderTaille.getMin());
        sliderPointure.setValue(sliderPointure.getMin());
        textFieldNbExemplaires2.clear();
        textFieldPrix2.clear();
        //Reset le produit selectionné
        setProduitSelected(null);
    }

    /**
     * Methode d'ajout d'un produit avec le constructeur (pas d'impact sur cost) grâce à l'interface
     */
    public void onAjout(){
        try {
            //Selection des cas selon les types pour ne pas créer d'execpetion
            if (chooseTypeBox.getValue().equals("Vetement")) {
                //Ajout à la BDD le produit
                manager.addProduct(new Vetement(textFieldName.getText(), Double.parseDouble(textFieldPrix.getText()), Integer.parseInt(textFieldNbExemplaires.getText()), (int) sliderTaille.getValue()));
            } else if (chooseTypeBox.getValue().equals("Chaussure")) {
                //Ajout à la BDD le produit
                manager.addProduct(new Chaussure(textFieldName.getText(), Double.parseDouble(textFieldPrix.getText()), Integer.parseInt(textFieldNbExemplaires.getText()), (int) sliderPointure.getValue()));
            } else if (chooseTypeBox.getValue().equals("Accessoire")) {
                //Ajout à la BDD le produit
                manager.addProduct(new Accessoire(textFieldName.getText(), Double.parseDouble(textFieldPrix.getText()), Integer.parseInt(textFieldNbExemplaires.getText())));
            }
            //Fetch la liste updaté
            fetchList();
            onCancel();
        }
        //Exception
        catch (Exception e){
            //Si le produit n'as pas de type
            if (chooseTypeBox.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Selectionné un type de produit");
                alert.showAndWait();
            }
            else {
                //Toutes les autres erreurs générées par le constructeur
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
    }
    /**
     * Méthode pour delete un produit grâce à l'interface
     */
    public void onDelete(){
        //Verifier qu'un produit est selectionné
        if (produitSelected != null){
            //Delete le produit
            manager.deleteProduct(produitSelected);
            //Fetch la liste
            fetchList();
            //Reset fields
            onCancel();
        }
        else {
            //Alert
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Aucun produit n'est selectionné pour vendre de nouveaux exemplaires");
            alert.showAndWait();
        }
    }
    /**
     * Méthode pour update un produit dans la BDD grâce à l'interface
     */
    public void onModify(){
        //Vérifier qu'un produit est selectionné
        if (produitSelected != null){
            try{
                //Update le produit
                manager.updateProduct(produitSelected, textFieldName.getText(), Double.parseDouble(textFieldPrix.getText()), Integer.parseInt(textFieldNbExemplaires.getText()), (int) sliderTaille.getValue(), (int) sliderPointure.getValue());
                //Update liste
                fetchList();
                //Reset
                onCancel();
            }
            //Vérifier si on ne rentre pas des fields vide
            catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
        else {
            //Alert
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Aucun produit n'est selectionné pour vendre de nouveaux exemplaires");
            alert.showAndWait();
        }
    }
    /**
     * Méthode pour que les gérants du magasin puisse acheter de nouveaux exemplaires d'un produit incremente cost
     */
    public void acheter(){
        //Verifier si un produit est selectionné
        if (produitSelected != null) {
            try {
                //Acheter le produit
                produitSelected.achat(Integer.parseInt(textFieldNbExemplaires2.getText()), Double.parseDouble(textFieldPrix2.getText()));
                //Mettre à jour recette et cost dans la BDD
                manager.updateInfo();
                //Mettre à jour graphiquement ces infos
                fetchInfo();
                //Mettre à jour le nombre d'exemplaire disponible
                textFieldNbExemplaires.setText(String.valueOf(Integer.parseInt(textFieldNbExemplaires.getText()) + Integer.parseInt(textFieldNbExemplaires2.getText())));
                //Update le produit
                onModify();
            //Gérer les exceptions de la méthode achat et des fields
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
        else{
            //alert
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Aucun produit n'est selectionné pour acheter de nouveaux exemplaires");
            alert.showAndWait();
            }
    }
    /**
     * Méthode pour que les gérants du magasin puisse vendre de nouveaux exemplaires d'un produit incrément recette
     */
    public void vendre() {
        //Vérifier que un produit est bien selectionné
        if (produitSelected != null) {
            try {
                //Vendre le produit
                produitSelected.vendre(Integer.parseInt(textFieldNbExemplaires2.getText()));
                //Mettre à jour recette et cost dans la BDD
                manager.updateInfo();
                //Mettre à jour ces infos graphiquement
                fetchInfo();
                //Mettre à jour le nombre d'exemplaires disponible
                textFieldNbExemplaires.setText(String.valueOf(Integer.parseInt(textFieldNbExemplaires.getText()) - Integer.parseInt(textFieldNbExemplaires2.getText())));
                //Update le produit
                onModify();
                }
            //Catch les expections de la méthodes ventes et des inputs des fields
            catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                }
            }
        else {
            //Alert
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Aucun produit n'est selectionné pour vendre de nouveaux exemplaires");
            alert.showAndWait();
        }
    }
    /**
     * Méthode qui effectue les soldes du produit selectionné graphiquement
     */
    public void onRemise() {
        //Remise
        produitSelected.remise();
        //Set prix
        textFieldPrix.setText(String.valueOf(produitSelected.getPrix()));
        //Update produit
        onModify();
    }
    /**
     * Fonction qui initialize notre interface
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Init notre connection BDD
        manager = new DBManager();

        //Choose bouton init
        List<String> gvalues = new ArrayList<>();
        gvalues.add("Vetement");
        gvalues.add("Chaussure");
        gvalues.add("Accessoire");
        ObservableList<String> types = FXCollections.observableArrayList(gvalues);
        chooseTypeBox.setItems(types);

        //Fetch init Lists and values (historic)
        fetchList();
        fetchInfo();

        //Listen events
        listenEventLists();
        ChooseTypeListener(chooseTypeBox);
    }
}