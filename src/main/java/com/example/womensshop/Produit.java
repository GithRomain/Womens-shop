/**
 * Ce projet est créé pour gérer un magasin de femme
 * @author romainpasquier
 * @version 1.0
 * @since 2022
 */
package com.example.womensshop;

/**
 * La class Produit represente un produit et implemente les soldes
 * Sert de base a plusieurs sous classe comme Vetement, Chaussure et Accessoire
 * @see Solde
 * @author romainpasquier
 **/
public abstract class Produit implements Solde{
    //Attributs
    /**
     * num est le nombre total de produit créé
     */
    private static int num;
    /**
     * numero est le numéro d'un produit il est auto incrémenté lorsqu'un nouveau produit est créé
     */
    private int numero;
    /**
     * nom est le nom du produit
     */
    private String nom;
    /**
     * prix est le prix d'un produit > 0
     */
    private double prix;
    /**
     * nbExemplaires représente le nombre d'exemplaire > 0 d'un produit donnée
     */
    private int nbExemmplaires;
    /**
     * recette représente le nombre total des recettes du magasin
     */
    private static int recette = 0;

    //Constructeur
    /**
     * Constructeur à paramètre d'un produit
     * @param nom
     * @param prix positif
     * @param nbExemmplaires positif
     * @exception IllegalArgumentException
     */
    public Produit(String nom, double prix, int nbExemmplaires){
        if (prix < 0){
            throw new IllegalArgumentException("Prix négatif");
        } else if (nbExemmplaires < 0) {
            throw new IllegalArgumentException("Nombre d'exemplaire négatif");
        }
        this.nom = nom;
        this.prix = prix;
        this.nbExemmplaires = nbExemmplaires;
        //incrémente le compteur général
        num++;
        //set le numéro du produit créé
        setNumero(getNum());
    }

    //Getters
    /**
     * Getter de l'attribut num
     * @return la valeur de l'attribut num
     */
    public static int getNum() {
        return num;
    }
    /**
     * Getter de l'attribut numero
     * @return la valeur de l'attribut numero
     */
    public int getNumero() {
        return numero;
    }
    /**
     * Getter de l'attribut nom
     * @return la valeur de l'attribut nom
     */
    public String getNom() {
        return nom;
    }
    /**
     * Getter de l'attribut prix
     * @return la valeur de l'attribut prix
     */
    public double getPrix() {
        return prix;
    }
    /**
     * Getter de l'attribut nbExemplaires
     * @return la valeur de l'attribut nbExemplaires
     */
    public int getNbExemmplaires() {
        return nbExemmplaires;
    }
    /**
     * Getter de l'attribut recette
     * @return la valeur de l'attribut recette
     */
    public static int getRecette() {
        return recette;
    }

    //Setters
    /**
     * Le setter de num
     * @param num
     */
    public static void setNum(int num) {
        Produit.num = num;
    }
    /**
     * Le setter de numero
     * @param numero
     */
    public void setNumero(int numero) {
        this.numero = numero;
    }
    /**
     * Le setter de nom
     * @param nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }
    /**
     * Le setter de prix
     * @param prix
     * @exception IllegalArgumentException
     */
    public void setPrix(double prix) {
        if (prix < 0){
            throw new IllegalArgumentException("Prix négatif");
        }
        this.prix = prix;
    }
    /**
     * Le setter de nbExemplaire
     * @param nbExemmplaires
     * @exception IllegalArgumentException
     */
    public void setNbExemmplaires(int nbExemmplaires) {
        if (nbExemmplaires < 0){
            throw new IllegalArgumentException("Nombre d'exemplaire négatif");
        }
        this.nbExemmplaires = nbExemmplaires;
    }
    /**
     * Le setter de recette
     * @param recette
     */
    public static void setRecette(int recette) {
        Produit.recette = recette;
    }

    //Methods
    /**
     * Méthode de vente d'un produit qui actualise l'attribu nbExemplaire et l'attribu recette
     * @param nbEx positif et inférieur à nbExemplaire
     * @exception IllegalArgumentException
     */
    public void vendre(int nbEx) {
        if (nbEx < 0) {
            throw new IllegalArgumentException("Nombre demandé à la vente érroné");
        }
        else if (nbEx > nbExemmplaires){
            throw new IllegalArgumentException("Produit indisponible");
        }
        nbExemmplaires -= nbEx;
        recette += nbEx * prix;
    }
    /**
     * Méthode d'achat d'un produit qui actualise l'attribu nbExemplaire
     * @param nbEx positif
     */
    public void achat(int nbEx) {
        if (nbEx < 0){
            throw new IllegalArgumentException("Nombre demandé à l'achat erroné");
        }
        nbExemmplaires += nbEx;
    }
    /**
     * Méthode remise qui solde à 30%, 20% et 50% les Vetements, Chaussures et Accessoires
     * @see Solde
     */
    @Override
    public void remise() {
        if (Vetement.class.equals(getClass())) {
            setPrix(getPrix() * 0.3);
        } else if (Chaussure.class.equals(getClass())) {
            setPrix(getPrix() * 0.2);
        } else if (Accessoire.class.equals(getClass())) {
            setPrix(getPrix() * 0.5);
        }
    }
    /**
     * La méthode toString
     * @return la représentation de l'objet Produit en chaîne de charactères
     */
    @Override
    public String toString() {
        return  getClass().toString().substring(29) +
                "\n{" +
                "\nnumero: " + numero +
                "\nnom: " + nom +
                "\nprix: " + prix +
                "\nnbExemmplaires: " + nbExemmplaires +
                "\nrecette: " + recette;
    }
}
