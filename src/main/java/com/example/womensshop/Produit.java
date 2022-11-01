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
    private static int recette = 0;
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
    public Produit(String nom, double prix, int nbExemmplaires){
        if (prix < 0){
            throw new IllegalArgumentException("Prix négatif");
        } else if (nbExemmplaires < 0) {
            throw new IllegalArgumentException("Nombre d'exemplaire négatif");
        }
        this.nom = nom;
        this.prix = prix;
        this.nbExemplaires = nbExemmplaires;
        //ajout du produit créé à la collection
        if (Vetement.class.equals(getClass())) produitList.get(0).add(this);
        else if (Chaussure.class.equals(getClass())) produitList.get(1).add(this);
        else if (Accessoire.class.equals(getClass()))  produitList.get(2).add(this);
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
    public static int getRecette() {
        return recette;
    }
    /**
     * Getter de l'attribut produitList
     * @return la collection produitList
     */
    public static List<List<Produit>> getProduitList() {
        return produitList;
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
    public void setPrix(double prix) {
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
    public void setNbExemplaires(int nbExemplaires) {
        if (nbExemplaires < 0){
            throw new IllegalArgumentException("Nombre d'exemplaire négatif");
        }
        this.nbExemplaires = nbExemplaires;
    }
    /**
     * Le setter de recette
     * @param recette
     */
    public static void setRecette(int recette) {
        Produit.recette = recette;
    }
    /**
     * Le setter de produitList
     * @param produitList
     */
    public static void setProduitList(List<List<Produit>> produitList) {
        Produit.produitList = produitList;
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
    public void vendre(int nbEx) {
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
     * Méthode d'achat d'un produit qui actualise l'attribu nbExemplaire
     * @param nbEx positif
     * @exception IllegalArgumentException
     */
    public void achat(int nbEx, double purchasePrice) {
        if (nbEx < 0){
            throw new IllegalArgumentException("Nombre demandé à l'achat erroné");
        }
        if (purchasePrice >= prix){
            throw new IllegalArgumentException("Prix d'achat plus cher que le prix de vente");
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
     * Méthode pour trier l'attribut produitList grâce à la fonction de comparaison compareTo
     */
    public static void sortList(){
        for (List<Produit> produitCollection : produitList){
            Collections.sort(produitCollection);
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
                "\nnom: " + nom +
                "\nprix: " + prix +
                "\nnbExemmplaires: " + nbExemplaires;
    }
}
