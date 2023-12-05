package classe;

public class GestionnaireOthello {
    private Plateau plateau;

    public GestionnaireOthello(Plateau plateau) {
        this.plateau = plateau;
    }

    public boolean jouerCoup(int ligne, int colonne, char couleurJoueur) {
        if (!coupValide(ligne, colonne, couleurJoueur)) {
            return false;
        }

        plateau.setPion(ligne, colonne, new Pion(couleurJoueur));
        plateau.getPion(ligne, colonne).retourner(); // Assurez-vous que la méthode retourner est correctement définie dans la classe Pion

        return true;
    }



    private boolean coupValide(int ligne, int colonne, char couleurJoueur) {
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
                    retournerPionsDansDirection(ligne, colonne, couleurJoueur, deltaLigne, deltaColonne);
                }
            }
        }

        if (!coupValide) {
            System.out.println("Aucune direction valide pour retourner des pions.");
        }

        return coupValide;
    }

    private boolean peutRetournerPionsDansDirection(int ligne, int colonne, char couleurJoueur, int deltaLigne, int deltaColonne) {
        int ligneActuelle = ligne + deltaLigne;
        int colonneActuelle = colonne + deltaColonne;

        boolean pionsAdversesTrouves = false;

        while (estDansPlateau(ligneActuelle, colonneActuelle) && plateau.getPion(ligneActuelle, colonneActuelle) != null
                && plateau.getPion(ligneActuelle, colonneActuelle).getCouleur() != couleurJoueur) {
            pionsAdversesTrouves = true;
            ligneActuelle += deltaLigne;
            colonneActuelle += deltaColonne;
        }

        return pionsAdversesTrouves && estDansPlateau(ligneActuelle, colonneActuelle)
                && plateau.getPion(ligneActuelle, colonneActuelle) != null
                && plateau.getPion(ligneActuelle, colonneActuelle).getCouleur() == couleurJoueur;
    }

    private void retournerPionsDansDirection(int ligne, int colonne, char couleurJoueur, int deltaLigne, int deltaColonne) {
        int ligneActuelle = ligne + deltaLigne;
        int colonneActuelle = colonne + deltaColonne;

        while (estDansPlateau(ligneActuelle, colonneActuelle) && plateau.getPion(ligneActuelle, colonneActuelle) != null
                && plateau.getPion(ligneActuelle, colonneActuelle).getCouleur() != couleurJoueur) {
            plateau.getPion(ligneActuelle, colonneActuelle).retourner();

            ligneActuelle += deltaLigne;
            colonneActuelle += deltaColonne;
        }
    }


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
                    if (plateau.getPion(i, j).getCouleur() == 'X') {
                        pionsBlancs++;
                    } else if (plateau.getPion(i, j).getCouleur() == 'O') {
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

    public boolean estGagnant(char couleurJoueur) {
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
