package org.uphf;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class IntelligenceArtificiel {
    private int[][] plateauValeur;
    private final Couleur couleurIA;
    private final Couleur couleurJoueur;
    private final int PROFONDEURMAX = 3;
    private final GestionnaireOthello gestionnaireOthello;

    public IntelligenceArtificiel(Couleur couleurIA, GestionnaireOthello gestionnaireOthello) {
        this.couleurIA = couleurIA;
        this.couleurJoueur = couleurIA.getOppose();
        this.gestionnaireOthello = gestionnaireOthello;
        chargerPlateauDepuisFichier();
    }

    public void chargerPlateauDepuisFichier() {
        plateauValeur = new int[8][8];
        String cheminDuFichier = "src/main/resources/plateauValeur.csv";
        try {
            InputStream inputStream = new FileInputStream(cheminDuFichier);
            Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            CSVReader csvReader = new CSVReader(reader);
            for (int j = 0; j < 8; j++) {
                String[] nextRecord = csvReader.readNext();
                for (int i = 0; i < 8; i++) {
                    plateauValeur[j][i] = Integer.parseInt(nextRecord[i]);
                }
            }
        } catch (CsvValidationException | IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Chargement du plateau de valeur réussi");
    }

    public void jouerCoupMinMax() {
        Noeud arbre = construireArbreDesPossible();
        arbre = minMax(arbre, this.PROFONDEURMAX, true);
        trouverCoupAJoue(arbre);
    }

    private void trouverCoupAJoue(Noeud arbre) {
        if (arbre.getFils() == null) {
            System.out.println("L'IA ne peut pas jouer");
            return;
        }
        for (Noeud fils : arbre.getFils()) {
            if (fils.getValeur() == arbre.getValeur()) {
                gestionnaireOthello.jouerCoup(fils.getCoupJoue()[0], fils.getCoupJoue()[1], couleurIA);
                System.out.println("L'IA a joué en " + fils.getCoupJoue()[0] + ", " + fils.getCoupJoue()[1]);
                break;
            }
        }
    }

    public void jouerCoupMinMaxAlphaBeta() {
        Noeud arbre = construireArbreDesPossible();
        arbre = minMaxAlphaBeta(arbre, this.PROFONDEURMAX, true, Integer.MIN_VALUE, Integer.MAX_VALUE);
        trouverCoupAJoue(arbre);
    }

    public Noeud minMax(Noeud noeud, int profondeur, boolean max) {
        if (profondeur == 0 || noeud.fils == null) return noeud;
        if (max) {
            int bestValue = Integer.MIN_VALUE;
            for (Noeud fils : noeud.getFils()) {
                Noeud valeur = minMax(fils, profondeur - 1, false);
                bestValue = Math.max(bestValue, valeur.getValeur());
                noeud.setValeur(bestValue);
            }
        } else {
            int bestValue = Integer.MAX_VALUE;
            for (Noeud fils : noeud.getFils()) {
                Noeud valeur = minMax(fils, profondeur - 1, true);
                bestValue = Math.min(bestValue, valeur.getValeur());
                noeud.setValeur(bestValue);
            }
        }
        return noeud;
    }

    public Noeud minMaxAlphaBeta(Noeud noeud, int profondeur, boolean max, int alpha, int beta) {
        if (profondeur == 0 || noeud.fils == null) return noeud;
        if (max) {
            int bestValue = Integer.MIN_VALUE;
            for (Noeud fils : noeud.getFils()) {
                Noeud valeur = minMaxAlphaBeta(fils, profondeur - 1, false, alpha, beta);
                bestValue = Math.max(bestValue, valeur.getValeur());
                alpha = Math.max(alpha, bestValue);
                noeud.setValeur(bestValue);
                if (beta <= alpha) break;
            }
        } else {
            int bestValue = Integer.MAX_VALUE;
            for (Noeud fils : noeud.getFils()) {
                Noeud valeur = minMaxAlphaBeta(fils, profondeur - 1, true, alpha, beta);
                bestValue = Math.min(bestValue, valeur.getValeur());
                beta = Math.min(beta, bestValue);
                noeud.setValeur(bestValue);
                if (beta <= alpha) break;
            }
        }
        return noeud;
    }

    /**
     * Fonction qui retourne l'arbre des possibilités
     * sur une profondeur max donnée
     *
     * @return le preminer nœud de l'arbre des possibilités
     */
    private Noeud construireArbreDesPossible() {
        Noeud zero = new Noeud(0, false, null);
        zero.setPlateau(gestionnaireOthello.getPlateau().clone());
        //On garde le plateau originel car nous modifions le plateau dans la fonction récursive
        Plateau plateauOriginel = gestionnaireOthello.getPlateau().clone();
        gestionnaireOthello.obtenirCoupsValides(couleurIA).forEach(
                coup -> zero.addFils(construireArbreRecursif(zero, coup, 1)));
        gestionnaireOthello.setPlateau(plateauOriginel);
        return zero;
    }

    private Noeud construireArbreRecursif(Noeud parent, int[] coupAJoue, int profondeur) {
        boolean max = !parent.max;

        Noeud actuel = new Noeud(0, max, null);
        actuel.setParent(parent);
        actuel.setCoupJoue(coupAJoue);
        actuel.valeur = this.plateauValeur[coupAJoue[0]][coupAJoue[1]];

        Plateau plateauClone = parent.getPlateau().clone();
        gestionnaireOthello.setPlateau(plateauClone);
        gestionnaireOthello.jouerCoup(coupAJoue[0], coupAJoue[1], max ? this.couleurIA : this.couleurJoueur);
        actuel.setPlateau(gestionnaireOthello.getPlateau());

        if (profondeur < PROFONDEURMAX) {
            for (int[] coup : gestionnaireOthello.obtenirCoupsValides(max ? this.couleurJoueur : this.couleurIA)) {
                actuel.addFils(construireArbreRecursif(actuel, coup, profondeur + 1));
            }
        } else actuel.setFils(null);
        return actuel;
    }
}
