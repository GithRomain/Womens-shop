/**
 * Ce projet est créé pour gérer un magasin de femme
 * @author romainpasquier
 * @version 1.0
 * @since 2022
 */
package com.example.womensshop;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * La class DBManager represente la classe qui communique avec notre BDD en MySQL
 * @author romainpasquier
 **/
public class DBManager {
    /**
     * produitcollection représente une collection de tous les produits créés trié par Type (ajout automatique)
     */
    private static List<List<Produit>> produitList = new ArrayList<>(){
        {
            add(new LinkedList<>());
            add(new LinkedList<>());
            add(new LinkedList<>());
        }
    };
    /**
     * Getter de l'attribut produitList
     * @return la collection produitList
     */
    public static List<List<Produit>> getProduitList() {
        return produitList;
    }
    /**
     * Le setter de produitList
     * @param produitList
     */
    public static void setProduitList(List<List<Produit>> produitList) {
        DBManager.produitList = produitList;
    }
    /**
     * Méthode pour trier l'attribut produitList grâce à la fonction de comparaison compareTo
     */
    public static void sortList(){
        for (List<Produit> produitCollection : produitList){
            Collections.sort(produitCollection);
        }
    }
    /**
     * Method pour obtenir la connexion à la BDD mySQL
     * @return connection
     */
    public Connection Connector(){
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/womensShop?serverTimezone=Europe%2FParis", "","");
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * Method pour fermer l'acces à la BDD
     * @param myConn
     * @param myStmt
     * @param myRs
     */
    private void close(Connection myConn, Statement myStmt, ResultSet myRs) {
        try{
            if(myStmt!=null)
                myStmt.close();
            if(myRs!=null)
                myRs.close();
            if(myConn!=null)
                myConn.close();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Méthode pour attribuer à ma liste d'attribut tous les produits dans la BDD (on les tri par types)
     */
    public void loadProduct(){
        Connection myConn = this.Connector();
        List<List<Produit>> listReset = new ArrayList<>(){
            {
                add(new LinkedList<>());
                add(new LinkedList<>());
                add(new LinkedList<>());
            }
        };
        //Reset the list for updating this one
        DBManager.setProduitList(listReset);
        try {
            Statement myStmt = myConn.createStatement();

            String sqlVetement = "select * from produit join vetement on produit.num = vetement.num";
            ResultSet myRsVetement = myStmt.executeQuery(sqlVetement);
            while (myRsVetement.next()) {
                Produit vetement = new Vetement(myRsVetement.getInt("num"), myRsVetement.getString("nom"), myRsVetement.getDouble("prix"), myRsVetement.getInt("nbExemplaires"), myRsVetement.getInt("taille"));
                produitList.get(0).add(vetement);
            }

            String sqlChaussure = "select * from produit join chaussure on produit.num = chaussure.num";
            ResultSet myRsChaussure = myStmt.executeQuery(sqlChaussure);
            while (myRsChaussure.next()) {
                Produit chaussure = new Chaussure(myRsChaussure.getInt("num"), myRsChaussure.getString("nom"), myRsChaussure.getDouble("prix"), myRsChaussure.getInt("nbExemplaires"), myRsChaussure.getInt("pointure"));
                produitList.get(1).add(chaussure);
            }

            String sqlAccessoire = "select * from produit join accessoire on produit.num = accessoire.num";
            ResultSet myRsAccessoire = myStmt.executeQuery(sqlAccessoire);
            while (myRsAccessoire.next()) {
                Produit accessoire = new Accessoire(myRsAccessoire.getInt("num"), myRsAccessoire.getString("nom"), myRsAccessoire.getDouble("prix"), myRsAccessoire.getInt("nbExemplaires"));
                produitList.get(2).add(accessoire);
            }
            this.close(myConn, myStmt, myRsVetement);
            this.close(myConn, myStmt, myRsChaussure);
            this.close(myConn, myStmt, myRsAccessoire);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Méthode qui permet de selectionner mes infos dans la BDD (cost et recette) sur une et unique ligne
     */
    public void selectInfo(){
        Connection myConn = this.Connector();
        try {
            Statement myStmt = myConn.createStatement();

            String sql = "select * from INFO";
            ResultSet myRs = myStmt.executeQuery(sql);
            while (myRs.next()) {
                Produit.setCost(myRs.getDouble("cost"));
                Produit.setRecette(myRs.getDouble("recette"));
            }
            this.close(myConn, myStmt, myRs);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Method pour ajouter un produit à la BDD
     * @param produit
     */
    public void addProduct(Produit produit){
        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRs = null;
        try {
            myConn = this.Connector();
            //On ajoute les dans la BDD les args des produits
            String sql = "INSERT INTO produit (nom, prix, nbExemplaires) VALUES (?, ?, ?)";
            myStmt = myConn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            myStmt.setString(1, produit.getNom());
            myStmt.setDouble(2, produit.getPrix());
            myStmt.setInt(3, produit.getNbExemplaires());
            myStmt.execute();
            //On recupere ce qui le num auto increment
            ResultSet numero = myStmt.getGeneratedKeys();
            while (numero.next()) {
                //On set le num de l'objet produit afin de l'utiliser plus aisement par la suite
                produit.setNum(numero.getInt(1));
            }
            System.out.println("Adding product ok");

            if (Vetement.class.equals(produit.getClass())){
                System.out.println("Identify as a vetement");
                String sqlJoin = "INSERT INTO vetement (num, taille) VALUES (?, ?)";
                myStmt = myConn.prepareStatement(sqlJoin);
                myStmt.setInt(1, produit.getNum());
                myStmt.setInt(2, ((Vetement) produit).getTaille());
                myStmt.execute();
                System.out.println("Adding vetement ok");
            }
            else if (Chaussure.class.equals(produit.getClass())){
                System.out.println("Identify as a chaussure");
                String sqlJoin = "INSERT INTO chaussure (num, pointure) VALUES (?, ?)";
                myStmt = myConn.prepareStatement(sqlJoin);
                myStmt.setInt(1, produit.getNum());
                myStmt.setInt(2, (((Chaussure) produit).getPointure()));
                myStmt.execute();
                System.out.println("Adding chaussure ok");
            }
            else if (Accessoire.class.equals(produit.getClass())){
                System.out.println("Identify as an accessoire");
                String sqlJoin = "INSERT INTO accessoire (num) VALUES (?)";
                myStmt = myConn.prepareStatement(sqlJoin);
                myStmt.setInt(1, produit.getNum());
                myStmt.execute();
                System.out.println("Adding accessoire ok");
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        finally{
            close(myConn, myStmt, myRs);
        }
    }
    /**
     * Methode d'update les tables dans la BDD
     * @param produit
     * @param newNom
     * @param newPrix
     * @param newNbExemplaires
     * @param newTaille
     * @param newPointure
     * @exception Exception
     */
    public void updateProduct(Produit produit, Object newNom, Object newPrix, Object newNbExemplaires, Object newTaille, Object newPointure) throws SQLException {
        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRs = null;
        try {
            myConn = this.Connector();
            //Verification des exceptions
            if (newNom != null){
                produit.setNom((String) newNom);
            }
            if (newPrix != null){
                produit.setPrix((Double) newPrix);
            }
            if (newNbExemplaires != null){
                produit.setNbExemplaires((Integer) newNbExemplaires);
            }

            String sql = "UPDATE produit SET nom = ?, prix = ?, nbExemplaires = ? WHERE num = ?";
            myStmt = myConn.prepareStatement(sql);
            myStmt.setString(1, produit.getNom());
            myStmt.setDouble(2, produit.getPrix());
            myStmt.setInt(3, produit.getNbExemplaires());
            myStmt.setInt(4, produit.getNum());
            myStmt.executeUpdate();
            System.out.println("Update product ok");

            if (Vetement.class.equals(produit.getClass())){
                if (newTaille != null){
                    ((Vetement) produit).setTaille((Integer) newTaille);
                }
                String sqlJoin = "UPDATE vetement SET taille = ? WHERE num = ?";
                myStmt = myConn.prepareStatement(sqlJoin);
                myStmt.setInt(1, ((Vetement) produit).getTaille());
                myStmt.setInt(2, produit.getNum());
                myStmt.executeUpdate();
                System.out.println("Update vetement ok");
            }
            else if (Chaussure.class.equals(produit.getClass())){
                if (newPointure != null){
                    ((Chaussure) produit).setPointure((Integer) newPointure);
                }
                String sqlJoin = "UPDATE chaussure SET pointure = ? WHERE num = ?";
                myStmt = myConn.prepareStatement(sqlJoin);
                myStmt.setInt(1, ((Chaussure) produit).getPointure());
                myStmt.setInt(2, produit.getNum());
                myStmt.executeUpdate();
                System.out.println("Update chaussure ok");
            }
            else if (Accessoire.class.equals(produit.getClass())){
                //Symbolique
                System.out.println("Update accessoire ok");
            }
        }
        catch(Exception e){
            throw e;
        }
        finally{
            close(myConn, myStmt, myRs);
        }
    }
    /**
     * Methode d'update le bilan de l'entreprise dans la BDD
     */
    public void updateInfo(){
        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRs = null;
        try {
            myConn = this.Connector();

            String sql = "UPDATE INFO SET cost = ?, recette = ? WHERE id = 0";
            myStmt = myConn.prepareStatement(sql);
            myStmt.setDouble(1, Produit.getCost());
            myStmt.setDouble(2, Produit.getRecette());
            myStmt.executeUpdate();
            System.out.println("Update product ok");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        finally{
            close(myConn, myStmt, myRs);
        }
    }
    /**
     * Méthode pour supprimer des les informations des tables de notre BDD
     * @param produit
     */
    public void deleteProduct(Produit produit){
        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRs = null;
        try {
            myConn = this.Connector();
            String sqlJoin = "DELETE FROM " + produit.getClass().toString().substring(29) + " WHERE num = ?";
            myStmt = myConn.prepareStatement(sqlJoin);
            myStmt.setInt(1, produit.getNum());
            myStmt.execute();
            System.out.println("Deleting " + produit.getClass().toString().substring(29) + " ok");

            String sql = "DELETE FROM produit WHERE num = ?";
            myStmt = myConn.prepareStatement(sql);
            myStmt.setInt(1, produit.getNum());
            myStmt.execute();
            System.out.println("Deleting Product ok");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        finally{
            close(myConn, myStmt, myRs);
        }
    }
}
