/**
 * Ce projet est créé pour gérer un magasin de femme
 * @author romainpasquier
 * @version 1.0
 * @since 2022
 */
package com.example.womensshop;

import java.sql.*;

/**
 * La class DBManager represente la classe qui communique avec notre BDD en MySQL
 * @author romainpasquier
 **/
public class DBManager {
    /**
     * Method pour obtenir la connexion à la BDD mySQL
     * @return connection
     */
    public Connection Connector(){
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/womensShop?serverTimezone=Europe%2FParis", "user","password");
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
     */
    public void updateProduct(Produit produit, Object newNom, Object newPrix, Object newNbExemplaires, Object newTaille, Object newPointure){
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
