/**
 * Ce projet est créé pour gérer un magasin de femme
 * @author romainpasquier
 * @version 1.0
 * @since 2022
 */
package com.example.womensshop;

import java.util.*;

/**
 * La class Produit represente un produit et implemente les soldes
 * Sert de base a plusieurs sous classe comme Vetement, Chaussure et Accessoire
 * @see Solde
 * @see Comparable
 * @author romainpasquier
 **/
public abstract class Produit implements Solde, Comparable<Produit>{
    //Attributs
    /**
     * num est l'identifiant du produit
     */
    private int num = 0;
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
    private int nbExemplaires;
    /**
     * recette représente le nombre total des recettes du magasin
     */
    private static double recette = 0;
    /**
     * cost représente tous l'argent que le magasin dépense
     */
    private static double cost = 0;

    //Constructeur
    /**
     * Constructeur à paramètre d'un produit
     * @param nom
     * @param prix positif
     * @param nbExemmplaires positif
     * @exception IllegalArgumentException
     */
    public Produit(String nom, double prix, int nbExemmplaires) throws IllegalArgumentException{
        if (prix < 0){
            throw new IllegalArgumentException("Prix négatif");
        } else if (nbExemmplaires < 0) {
            throw new IllegalArgumentException("Nombre d'exemplaire négatif");
        }
        this.nom = nom;
        this.prix = prix;
        this.nbExemplaires = nbExemmplaires;
    }
    /**
     * Constructeur à paramèrtre d'un produit
     * @param num
     * @param nom
     * @param prix
     * @param nbExemmplaires
     */
    public Produit(int num, String nom, double prix, int nbExemmplaires) throws IllegalArgumentException{
        if (prix < 0){
            throw new IllegalArgumentException("Prix négatif");
        } else if (nbExemmplaires < 0) {
            throw new IllegalArgumentException("Nombre d'exemplaire négatif");
        }
        this.num = num;
        this.nom = nom;
        this.prix = prix;
        this.nbExemplaires = nbExemmplaires;
    }

    //Getters
    /**
     * Getter de l'attribut num
     * @return la valeur de l'attribut num
     */
    public int getNum() {
        return num;
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
    public int getNbExemplaires() {
        return nbExemplaires;
    }
    /**
     * Getter de l'attribut recette
     * @return la valeur de l'attribut recette
     */
    public static double getRecette() {
        return recette;
    }
    /**
     * Getter de l'attribut cost
     * @return la valeur de l'attribut cost
     */
    public static double getCost() {
        return cost;
    }

    //Setters
    /**
     * Setter de num
     * @param num
     */
    public void setNum(int num) {
        this.num = num;
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
    public void setPrix(double prix) throws IllegalArgumentException{
        if (prix < 0){
            throw new IllegalArgumentException("Prix négatif");
        }
        this.prix = prix;
    }
    /**
     * Le setter de nbExemplaire
     * @param nbExemplaires
     * @exception IllegalArgumentException
     */
    public void setNbExemplaires(int nbExemplaires) throws IllegalArgumentException{
        if (nbExemplaires < 0){
            throw new IllegalArgumentException("Nombre d'exemplaire négatif");
        }
        this.nbExemplaires = nbExemplaires;
    }
    /**
     * Le setter de recette
     * @param recette
     */
    public static void setRecette(double recette) {
        Produit.recette = recette;
    }
    /**
     * Le setter de cost
     * @param cost
     */
    public static void setCost(double cost) {
        Produit.cost = cost;
    }
    //Methods
    /**
     * Méthode de vente d'un produit qui actualise l'attribu nbExemplaire et l'attribu recette
     * @param nbEx positif et inférieur à nbExemplaire
     * @exception IllegalArgumentException
     */
    public void vendre(int nbEx) throws IllegalArgumentException{
        if (nbEx < 0) {
            throw new IllegalArgumentException("Nombre demandé à la vente érroné");
        }
        else if (nbEx > nbExemplaires){
            throw new IllegalArgumentException("Produit indisponible");
        }
        nbExemplaires -= nbEx;
        recette += nbEx * prix;
    }
    /**
     * Méthode d'achat d'un produit qui actualise l'attribut nbExemplaire
     * @param nbEx positif
     * @exception IllegalArgumentException
     */
    public void achat(int nbEx, double purchasePrice) throws IllegalArgumentException{
        if (nbEx < 0){
            throw new IllegalArgumentException("Nombre demandé à l'achat erroné");
        }
        if (purchasePrice >= prix){
            throw new IllegalArgumentException("Prix d'achat plus cher que le prix de vente");
        }
        if (purchasePrice < 0){
            throw new IllegalArgumentException("Prix d'achat négatif");
        }
        nbExemplaires += nbEx;
        cost += nbEx * purchasePrice;
    }
    /**
     * Méthode remise qui solde à 30%, 20% et 50% les Vetements, Chaussures et Accessoires
     * @see Solde
     */
    @Override
    public void remise() {
        if (Vetement.class.equals(getClass())) setPrix(getPrix() * 0.3);
        else if (Chaussure.class.equals(getClass())) setPrix(getPrix() * 0.2);
        else if (Accessoire.class.equals(getClass())) setPrix(getPrix() * 0.5);
    }
    /**
     * Méthode de comparaison des prix des produits avec un double
     * @param produit the object to be compared.
     * @return 1, -1, 0
     */
    @Override
    public int compareTo(Produit produit) {
        return Double.compare(this.prix, produit.prix);
    }
    /**
     * La méthode toString
     * @return la représentation de l'objet Produit en chaîne de charactères
     */
    @Override
    public String toString() {
        return "nom: " + nom +
                "\nprix: " + prix +
                "\nnbExemmplaires: " + nbExemplaires;
    }
}
