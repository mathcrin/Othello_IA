package org.uphf;

import java.util.List;

//Noeud de l'arbre de recherche
public class Noeud {
    public int valeur;
    //Représente si c'est un noeud max(true) ou min(false)
    public boolean max;
    public List<Noeud> fils;
    public Noeud parent;
    public Plateau plateau;
    int[] coupJoue;

    public Noeud(int valeur, boolean max, Noeud parent) {
        this.valeur = valeur;
        this.max = max;
        this.parent = parent;
    }

    public void addFils(Noeud fils) {
        if (this.fils == null)
            this.fils = new java.util.ArrayList<>();
        this.fils.add(fils);
    }

    public Noeud getFils(int index) {
        return this.fils.get(index);
    }

    public int getValeur() {
        return valeur;
    }

    public void setValeur(int valeur) {
        this.valeur = valeur;
    }

    public boolean isMax() {
        return max;
    }

    public void setMax(boolean max) {
        this.max = max;
    }

    public List<Noeud> getFils() {
        return fils;
    }

    public void setFils(List<Noeud> fils) {
        this.fils = fils;
    }

    public Noeud getParent() {
        return parent;
    }

    public void setParent(Noeud parent) {
        this.parent = parent;
    }

    public Plateau getPlateau() {
        return plateau;
    }

    public void setPlateau(Plateau plateau) {
        this.plateau = plateau;
    }

    public int[] getCoupJoue() {
        return coupJoue;
    }

    public void setCoupJoue(int[] coupJoue) {
        this.coupJoue = coupJoue;
    }
}
