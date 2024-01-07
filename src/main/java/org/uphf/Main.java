package org.uphf;

import classe.*;

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
                    System.out.println("Joueur " + joueurActuel + " gagne !");
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
                ia.jouerCoup();
                joueurActuel = couleurHumain;

            }
            if (gestionnaire.plateauPlein()) {
                gestionnaire.afficherPlateau();
                System.out.println("Partie terminée. Match nul !");

            }
        }

        // Vérifier si le plateau est plein

    }
}




