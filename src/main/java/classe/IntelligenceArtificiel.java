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

    private GestionnaireOthello gestionnaireOthello;

    public IntelligenceArtificiel(Couleur couleurIA, GestionnaireOthello gestionnaireOthello){
        this.couleurIA = couleurIA;
        this.gestionnaireOthello = gestionnaireOthello;
        chargerPlateauDepuisFichier();
    }

    public void chargerPlateauDepuisFichier(){
        plateauValeur = new int[8][8];
        String cheminDuFichier = "src/main/resources/plateauValeur.csv";
        try {
            InputStream inputStream = new FileInputStream(cheminDuFichier);
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            CSVReader csvReader = new CSVReader(reader);
            for(int j=0;j<8;j++){
                String[] nextRecord = csvReader.readNext();
                for(int i=0;i<8;i++){
                    plateauValeur[j][i] = Integer.parseInt(nextRecord[i]);
                }
            }
        } catch (CsvValidationException | IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Chargement du plateau de valeur réussi");
    }

//    public void minMax() {
//        // Initialiser la profondeur maximale de recherche
//        int profondeurMax = 4;
//
//        // Appeler la fonction minimax avec alpha-beta
//        int meilleurCoup = minimax(profondeurMax, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
//
//        // Utiliser le meilleurCoup pour prendre une décision ou effectuer une action
//        System.out.println("Meilleur Coup : " + meilleurCoup);
//    }
//
//    private int minimax(int profondeur, int alpha, int beta, boolean maximizer) {
//        if (profondeur == 0 || /* condition de fin de jeu */) {
//            return evaluerPlateau(); // Retourner l'évaluation du plateau
//        }
//
//        if (maximizer) {
//            int maxEval = Integer.MIN_VALUE;
//            for (int i = 0; i < /*nombre de coups possibles*/; i++) {
//                // Simuler le coup
//                int eval = minimax(profondeur - 1, alpha, beta, false);
//                maxEval = Math.max(maxEval, eval);
//                alpha = Math.max(alpha, eval);
//                if (beta <= alpha) {
//                    break; // Élagage alpha-beta
//                }
//            }
//            return maxEval;
//        } else {
//            int minEval = Integer.MAX_VALUE;
//            for (int i = 0; i < /*nombre de coups possibles*/; i++) {
//                // Simuler le coup
//                int eval = minimax(profondeur - 1, alpha, beta, true);
//                minEval = Math.min(minEval, eval);
//                beta = Math.min(beta, eval);
//                if (beta <= alpha) {
//                    break; // Élagage alpha-beta
//                }
//            }
//            return minEval;
//        }
//    }

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


    private Noeud construireArbre(GestionnaireOthello gestionnaire){
        int i=0;
        int profondeur = 3;
        Noeud zero = new Noeud(0,true,null);
        Plateau plateauOriginel = gestionnaire.getPlateau().clone();
        zero.setPlateau(plateauOriginel);
        List<int[]> coupsValides = gestionnaire.obtenirCoupsValides(couleurIA);
        Noeud parent = zero;
        boolean max = true;

        while(!gestionnaire.obtenirCoupsValides(couleurIA).isEmpty() && !gestionnaire.obtenirCoupsValides(couleurJoueur).isEmpty() && i<=profondeur){
            for(int[] x : coupsValides){
                Plateau plateauActuel = parent.plateau.clone();
                gestionnaire.setPlateau(plateauActuel);
                gestionnaire.jouerCoup(x[0],x[1],couleurIA);
                plateauActuel = gestionnaire.getPlateau();
                Noeud fils = new Noeud(plateauValeur[x[0]][x[1]],x[0],x[1],max,parent,plateauActuel);
                parent.addFils(fils);
            }
                //verifier les coup disponible pour ce nouveau plateau en sachant que se sera min
            i++;
            if(max){
                max = false;
            }else{
                max = true;
            }

        }
        //generer la profondeur 1

        return null;
    }
}
