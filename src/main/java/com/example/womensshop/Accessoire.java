/**
 * Ce projet est créé pour gérer un magasin de femme
 * @author romainpasquier
 * @version 1.0
 * @since 2022
 */
package com.example.womensshop;

/**
 * La class Accessoire represente un accessoire de type Produit
 * @see Produit
 * @author romainpasquier
 **/
public class Accessoire extends Produit{
    //Constructeur
    /**
     * Constructeur à paramètre d'un accessoire de type Produit
     * @see Produit
     * @param nom
     * @param prix
     * @param nbExemmplaires
     */
    public Accessoire(String nom, double prix, int nbExemmplaires) {
        super(nom, prix, nbExemmplaires);
    }

    //Methods
    /**
     * La méthode toString
     * @return la représentation de l'objet Accessoir en chaîne de charactères
     */
    @Override
    public String toString() {
        return super.toString() +
                "\n}";
    }
}