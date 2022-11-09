/**
 * Ce projet est créé pour gérer un magasin de femme
 * @author romainpasquier
 * @version 1.0
 * @since 2022
 */
package com.example.womensshop;

/**
 * La class Vetement represente un vetement de type Produit
 * @see Produit
 * @author romainpasquier
 **/
public class Vetement extends Produit{
    //Attributs
    /**
     * taille est la taille d'un vetement 34 <= taille <= 54 et taille est pair
     */
    private int taille;

    //Constructeur
    /**
     * Constructeur à paramètre de Vetement de type Produit
     * @see Produit
     * @param nom
     * @param prix
     * @param nbExemmplaires
     * @param taille
     * @exception IllegalArgumentException
     */
    public Vetement(String nom, double prix, int nbExemmplaires, int taille) {
        super(nom, prix, nbExemmplaires);
        if (taille < 34 || taille > 54 || taille % 2 != 0){
            throw new IllegalArgumentException("Taille erronée");
        }
        this.taille = taille;
    }

    /**
     * Constructeur à paramètre + num de vetement de type Produit
     * @param num
     * @param nom
     * @param prix
     * @param nbExemmplaires
     * @param taille
     */
    public Vetement(int num, String nom, double prix, int nbExemmplaires, int taille) {
        super(num, nom, prix, nbExemmplaires);
        if (taille < 34 || taille > 54 || taille % 2 != 0){
            throw new IllegalArgumentException("Taille erronée");
        }
        this.taille = taille;
    }

    //Getters
    /**
     * Getter de l'attribut taille
     * @return la valeur de l'attribu taille
     */
    public int getTaille() {
        return taille;
    }

    //Setters
    /**
     * Setter de l'attribu taille
     * @param taille
     */
    public void setTaille(int taille) {
        if (taille < 34 || taille > 54 || taille % 2 != 0){
            throw new IllegalArgumentException("Taille erronée");
        }
        this.taille = taille;
    }

    //Methods
    /**
     * La méthode toString
     * @return la représentation de l'objet Vetement en chaîne de charactères
     */
    @Override
    public String toString() {
        return  super.toString() +
                "\ntaille: " + taille;
    }
}
