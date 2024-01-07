package org.uphf;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Création du plateau et du gestionnaire
        Plateau plateau = new Plateau();
        GestionnaireOthello gestionnaire = new GestionnaireOthello(plateau);

        // Choix du mode de jeu
        System.out.println("Choisissez le mode de jeu :");
        System.out.println("1 - 1Vs1");
        System.out.println("2 - 1VsOrdinateur");
        System.out.println("3 - OrdinateurVsOrdinateur");
        int choix = scanner.nextInt();

        switch (choix) {
            case 1:
                // Mode 1Vs1
                jouer1Vs1(gestionnaire);
                break;
            case 2:
                // Mode 1VsOrdinateur
                jouer1VsOrdinateur(gestionnaire);
                break;
            case 3:
                // Mode OrdinateurVsOrdinateur
                OrdinateurVsOrdinateur(gestionnaire);
                break;
            default:
                System.out.println("Choix invalide. Fin du programme.");
        }
    }

    private static void jouer1Vs1(GestionnaireOthello gestionnaire) {
        Scanner scanner = new Scanner(System.in);

        Couleur joueurActuel = Couleur.BLANC; // Le joueur blanc commence

        while (true) {
            // Affichage du plateau
            gestionnaire.afficherPlateau();

            // Demande au joueur de jouer
            System.out.println("Joueur " + joueurActuel + ", entrez la ligne (0-7) et la colonne (0-7) : ");
            int ligne = scanner.nextInt();
            int colonne = scanner.nextInt();

            // Jouer le coup
            if (gestionnaire.jouerCoup(ligne, colonne, joueurActuel)) {
                // Vérifier si le plateau est plein ou s'il y a un gagnant
                if (gestionnaire.plateauPlein()) {
                    gestionnaire.afficherPlateau();
                    System.out.println("Partie terminée. Gagnant : " + gestionnaire.determinerVainqueur());
                    break;
                }

                // Changer de joueur
                joueurActuel = joueurActuel.getOppose();
            } else {
                System.out.println("Coup invalide. Réessayez.");
            }
        }
    }

    private static void jouer1VsOrdinateur(GestionnaireOthello gestionnaire) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choisissez l'algorithme de l'IA :");
        System.out.println("1 - MinMax");
        System.out.println("2 - MinMax avec élagage alpha-beta");
        int choixAlgo = scanner.nextInt();

        // Choix de la couleur pour le joueur humain
        System.out.println("Choisissez votre couleur :");
        System.out.println("1 - Blanc");
        System.out.println("2 - Noir");
        int choixCouleur = scanner.nextInt();

        Couleur couleurHumain;
        Couleur couleurIA;

        if (choixCouleur == 1) {
            couleurHumain = Couleur.BLANC;
            couleurIA = Couleur.NOIR;
        } else {
            couleurHumain = Couleur.NOIR;
            couleurIA = Couleur.BLANC;
        }

        Couleur joueurActuel = couleurHumain;
        IntelligenceArtificiel ia = new IntelligenceArtificiel(couleurIA, gestionnaire);

        while (true) {
            // Affichage du plateau
            gestionnaire.afficherPlateau();

            // Tour du joueur humain
            if (joueurActuel == couleurHumain) {
                System.out.println("Joueur " + joueurActuel + ", entrez la ligne (0-7) et la colonne (0-7) : ");
                int ligne = scanner.nextInt();
                int colonne = scanner.nextInt();

                if (gestionnaire.jouerCoup(ligne, colonne, joueurActuel)) {
                    joueurActuel = couleurIA;
                } else {
                    System.out.println("Coup invalide. Réessayez.");
                }
            } else {
                // Tour de l'ordinateur
                if (choixAlgo == 1) {
                    ia.jouerCoupMinMax();
                } else {
                    ia.jouerCoupMinMaxAlphaBeta();
                }
                joueurActuel = couleurHumain;
            }
            if (gestionnaire.plateauPlein()) {
                gestionnaire.afficherPlateau();
                System.out.println("Partie terminée. Gagnant : " + gestionnaire.determinerVainqueur());
                break;
            }
        }
    }

    private static void OrdinateurVsOrdinateur(GestionnaireOthello gestionnaire) {
        IntelligenceArtificiel ia1 = new IntelligenceArtificiel(Couleur.BLANC, gestionnaire);
        IntelligenceArtificiel ia2 = new IntelligenceArtificiel(Couleur.NOIR, gestionnaire);
        Scanner scanner = new Scanner(System.in);
        IntelligenceArtificiel joueurActuel = ia1;

        System.out.println("Choisissez l'algorithme de l'IA :");
        System.out.println("1 - MinMax");
        System.out.println("2 - MinMax avec élagage alpha-beta");
        int choixAlgo = scanner.nextInt();

        //Mesure du temp d'éxecution
        long startTime = System.currentTimeMillis();

        while (true) {
            if (choixAlgo == 1) {
                joueurActuel.jouerCoupMinMax();
            } else {
                joueurActuel.jouerCoupMinMaxAlphaBeta();
            }

            joueurActuel = (joueurActuel == ia1) ? ia2 : ia1;
            gestionnaire.afficherPlateau();

            if (gestionnaire.plateauPlein()) {
                long endTime = System.currentTimeMillis();
                gestionnaire.afficherPlateau();
                System.out.println("Partie terminée. Gagnant : " + gestionnaire.determinerVainqueur());
                //Mesure du temp d'execution
                System.out.println("Temps d'éxécution : " + (endTime - startTime) + "ms");
                break;
            }
        }
    }
}




