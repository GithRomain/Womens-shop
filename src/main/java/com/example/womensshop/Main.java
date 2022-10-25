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
            Produit produit1 = new Chaussure("produit1", 10, 8, 36);
            Produit produit2 = new Vetement("produit1", 10, 8, 36);
            produit2.remise();
            System.out.println(produit2);
        }
        catch (IllegalArgumentException illegalArgumentException){
            System.out.println(illegalArgumentException.getMessage());
        }
    }
}
