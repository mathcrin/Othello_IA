package classe;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;

import javax.swing.*;
import java.io.*;
import java.util.List;

public class IntelligenceArtificiel {
    private int[][] plateauValeur;
    private Couleur couleurIA;
    private Couleur couleurJoueur;
    private static final int PROFONDEURMAX = 3;

    private GestionnaireOthello gestionnaireOthello;

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
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
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

    //TODO : Implementer l'algo minmax
    public void jouerCoup() {
        Noeud arbre = construireArbreDesPossible();
        arbre = minMax(arbre, this.PROFONDEURMAX, true);
        for(Noeud fils : arbre.getFils()){
            if(fils.getValeur() == arbre.getValeur()){
                gestionnaireOthello.jouerCoup(fils.getCoupJoue()[0], fils.getCoupJoue()[1], couleurIA);
                System.out.println("L'IA a joué en " + fils.getCoupJoue()[0] + ", " + fils.getCoupJoue()[1]);
                break;
            }
        }
    }
    public Noeud minMax(Noeud noeud, int profondeur, boolean max) {
        if(profondeur ==0 || noeud.fils==null) return noeud;
        if(max){
            int bestValue = Integer.MIN_VALUE;
            for(Noeud fils : noeud.getFils()){
                Noeud valeur = minMax(fils, profondeur-1, false);
                bestValue = Math.max(bestValue, valeur.getValeur());
                noeud.setValeur(bestValue);
            }
        }else {
            int bestValue = Integer.MAX_VALUE;
            for(Noeud fils : noeud.getFils()){
                Noeud valeur = minMax(fils, profondeur-1, true);
                bestValue = Math.min(bestValue, valeur.getValeur());
                noeud.setValeur(bestValue);
            }
        }

        return noeud;
    }

    private int evaluerPlateau() {
        int evaluation = 0;
        // Implémenter votre fonction d'évaluation du plateau ici
        // Cette fonction doit retourner une valeur numérique représentant la qualité de la position
        // Plus la valeur est élevée, meilleure est la position pour l'IA

        // Exemple basique : Somme des valeurs du plateau
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                evaluation += plateauValeur[i][j];
            }
        }
        return evaluation;
    }

    /**
     * Fonction qui retourne l'arbre des possibilités
     * sur une profondeur max donnée
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
