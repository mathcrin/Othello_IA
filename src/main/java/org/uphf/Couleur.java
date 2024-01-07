package org.uphf;

public enum Couleur {
    BLANC,
    NOIR;

    public String toString() {
        if (this == Couleur.BLANC) {
            return "X";
        } else {
            return "O";
        }
    }

    public Couleur getOppose() {
        if (this == Couleur.BLANC) {
            return Couleur.NOIR;
        } else {
            return Couleur.BLANC;
        }
    }
}
