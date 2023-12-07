package classe;

public class GestionnaireOthello {
    private Plateau plateau;

    public GestionnaireOthello(Plateau plateau) {
        this.plateau = plateau;
    }

    public boolean jouerCoup(int ligne, int colonne, Couleur couleurJoueur) {

        if (!coupValide(ligne, colonne, couleurJoueur)) {
            return false;
        }

        plateau.setPion(ligne, colonne, new Pion(couleurJoueur));
        //TODO : faire un for each pour retourner les pions dans toutes les directions

        return true;
    }



    private boolean coupValide(int ligne, int colonne, Couleur couleurJoueur) {
        if (!estDansPlateau(ligne, colonne) || plateau.getPion(ligne, colonne) != null) {
            System.out.println("Coup invalide : hors du plateau ou case occupée.");
            return false;
        }

        boolean coupValide = false;

        for (int deltaLigne = -1; deltaLigne <= 1; deltaLigne++) {
            for (int deltaColonne = -1; deltaColonne <= 1; deltaColonne++) {
                if (deltaLigne == 0 && deltaColonne == 0) {
                    continue;
                }
                if (peutRetournerPionsDansDirection(ligne, colonne, couleurJoueur, deltaLigne, deltaColonne)) {
                    coupValide = true;

                    //retournerPionsDansDirection(ligne, colonne, couleurJoueur, deltaLigne, deltaColonne);
                }
            }
        }

        if (!coupValide) {
            System.out.println("Aucune direction valide pour retourner des pions.");
        }

        return coupValide;
    }

    private boolean peutRetournerPionsDansDirection(int ligne, int colonne, Couleur couleurJoueur, int deltaLigne, int deltaColonne) {
        int ligneActuelle = ligne + deltaLigne;
        int colonneActuelle = colonne + deltaColonne;
        int deltaLigne1=deltaLigne;
        int deltaColonne1=deltaColonne;
        int ligneActuelle1=ligne + deltaLigne;
        int colonneActuelle1=colonne + deltaColonne;

        boolean pionsAdversesTrouves = false;
        boolean pionAlieTrouves = false;

        while (estDansPlateau(ligneActuelle1, colonneActuelle1) && plateau.getPion(ligneActuelle1, colonneActuelle1) != null
                && plateau.getPion(ligneActuelle1, colonneActuelle1).getCouleur() != couleurJoueur) {

            ligneActuelle1 += deltaLigne1;
            colonneActuelle1 += deltaColonne1;
        }
        if (estDansPlateau(ligneActuelle1, colonneActuelle1) && plateau.getPion(ligneActuelle1, colonneActuelle1) != null && plateau.getPion(ligneActuelle1, colonneActuelle1).getCouleur() == couleurJoueur){
            pionAlieTrouves = true;}

        while (estDansPlateau(ligneActuelle, colonneActuelle) && plateau.getPion(ligneActuelle, colonneActuelle) != null
                && plateau.getPion(ligneActuelle, colonneActuelle).getCouleur() != couleurJoueur && pionAlieTrouves) {
            pionsAdversesTrouves = true;
            System.out.println(plateau.getPion(3, 3).getCouleur());
            plateau.getPion(ligneActuelle,colonneActuelle).retourner();
            System.out.println(plateau.getPion(3, 3).getCouleur());
            System.out.println(ligneActuelle);
            System.out.println(colonneActuelle);
            System.out.println(plateau.getPion(ligneActuelle, colonneActuelle).getCouleur());


            ligneActuelle += deltaLigne;
            colonneActuelle += deltaColonne;

        }

        //TODO : Vérifier qu'il y a un pion de notre couleur au bout de la direction pour pouvoir retourner les pions
        //je viens de le faire juste au dessus normalement ca doit bien verifier si un pion alier se trouve au bout
        if(pionsAdversesTrouves && pionAlieTrouves)
        {return true;}
        else{return false;}

    }

    /*private void retournerPionsDansDirection(int ligne, int colonne, Couleur couleurJoueur, int deltaLigne, int deltaColonne) {
        int ligneActuelle = ligne + deltaLigne;
        int colonneActuelle = colonne + deltaColonne;

        while (estDansPlateau(ligneActuelle, colonneActuelle) && plateau.getPion(ligneActuelle, colonneActuelle) != null
                && plateau.getPion(ligneActuelle, colonneActuelle).getCouleur() != couleurJoueur) {

            plateau.getPion(ligneActuelle, colonneActuelle).retourner();
            ligneActuelle += deltaLigne;
            colonneActuelle += deltaColonne;
        }
    }*/


    private boolean estDansPlateau(int ligne, int colonne) {
        return ligne >= 0 && ligne < Plateau.TAILLE && colonne >= 0 && colonne < Plateau.TAILLE;
    }

    public void afficherPlateau() {
        plateau.afficherPlateau();
    }

    public boolean plateauPlein() {
        for (int i = 0; i < Plateau.TAILLE; i++) {
            for (int j = 0; j < Plateau.TAILLE; j++) {
                if (plateau.getPion(i, j) == null) {
                    return false;
                }
            }
        }
        return true;
    }

    public char determinerVainqueur() {
        int pionsBlancs = 0;
        int pionsNoirs = 0;

        for (int i = 0; i < Plateau.TAILLE; i++) {
            for (int j = 0; j < Plateau.TAILLE; j++) {
                if (plateau.getPion(i, j) != null) {
                    if (plateau.getPion(i, j).getCouleur() == Couleur.BLANC) {
                        pionsBlancs++;
                    } else if (plateau.getPion(i, j).getCouleur() == Couleur.NOIR) {
                        pionsNoirs++;
                    }
                }
            }
        }

        if (pionsBlancs > pionsNoirs) {
            return 'X';
        } else if (pionsNoirs > pionsBlancs) {
            return 'O';
        } else {
            return ' ';
        }
    }

    public boolean estGagnant(Couleur couleurJoueur) {
        int pionsJoueur = 0;
        int pionsAdversaire = 0;

        for (int i = 0; i < Plateau.TAILLE; i++) {
            for (int j = 0; j < Plateau.TAILLE; j++) {
                if (plateau.getPion(i, j) != null) {
                    if (plateau.getPion(i, j).getCouleur() == couleurJoueur) {
                        pionsJoueur++;
                    } else {
                        pionsAdversaire++;
                    }
                }
            }
        }

        return pionsJoueur > pionsAdversaire;
    }
}
