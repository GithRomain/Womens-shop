/**
 * Ce projet est créé pour gérer un magasin de femme
 * @author romainpasquier
 * @version 1.0
 * @since 2022
 */
package com.example.womensshop;

/**
 * La class Main représentre la classe qui nous permet d'effectuer les tests de nos classes grâce à la méthode main
 * @author romainpasquier
 **/
public class Main {
    public static void main(String[] args) {
        try{
            DBManager manager = new DBManager();
            Produit produit1 = new Vetement("produit1", 1, 8, 36);
            Produit produit2 = new Chaussure("produit2", 2, 9, 42);
            Produit produit3 = new Accessoire("produit3", 3, 10);

            //manager.addProduct(produit1);
            manager.addProduct(produit1);
            manager.addProduct(produit2);
            manager.addProduct(produit3);

            manager.deleteProduct(produit1);
            manager.updateProduct(produit2, "WOLA", 12.0, 3, null, 40);
        }
        catch (IllegalArgumentException illegalArgumentException){
            System.out.println(illegalArgumentException.getMessage());
        }
    }
}
