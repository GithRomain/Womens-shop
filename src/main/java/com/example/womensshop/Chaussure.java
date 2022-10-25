/**
 * Ce projet est créé pour gérer un magasin de femme
 * @author romainpasquier
 * @version 1.0
 * @since 2022
 */
package com.example.womensshop;

/**
 * La class Chaussure represente une chaussure de type Produit
 * @see Produit
 * @author romainpasquier
 **/
public class Chaussure extends Produit{
    //Attributs
    /**
     * pointure est la pointure d'une chaussure 36 <= taille <= 50
     */
    private int pointure;

    //Constructeur
    /**
     * Constructeur à paramètre de Chaussure de type Produit
     * @see Produit
     * @param nom
     * @param prix
     * @param nbExemmplaires
     * @param  pointure
     * @exception IllegalArgumentException
     */
    public Chaussure(String nom, double prix, int nbExemmplaires, int pointure) {
        super(nom, prix, nbExemmplaires);
        if (pointure < 36 || pointure > 50){
            throw new IllegalArgumentException("Pointure erronée");
        }
        this.pointure = pointure;
    }

    //Getters
    /**
     * Getter de l'attribut pointure
     * @return la valeur de l'attribu pointure
     */
    public int getPointure() {
        return pointure;
    }

    //Setters
    /**
     * Setter de l'attribu pointure
     * @param pointure
     */
    public void setPointure(int pointure) {
        this.pointure = pointure;
    }

    //Methods
    /**
     * La méthode toString
     * @return la représentation de l'objet Chaussure en chaîne de charactères
     */
    @Override
    public String toString() {
        return  super.toString() +
                "\npointure: " + pointure +
                "\n}";
    }
}
