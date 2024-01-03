package classe;

import java.util.ArrayList;
import java.util.List;

public class GestionnaireOthello {
    private Plateau plateau;

    public GestionnaireOthello(Plateau plateau) {
        this.plateau = plateau;
    }

    public boolean jouerCoup(int ligne, int colonne, Couleur couleurJoueur) {
        if (!estCoupValide(ligne, colonne, couleurJoueur, true)) {
            return false;
        }

        plateau.setPion(ligne, colonne, new Pion(couleurJoueur));
        return true;
    }

    private boolean estCoupValide(int ligne, int colonne, Couleur couleurJoueur, boolean doFlip) {
        if (!estDansPlateau(ligne, colonne) || plateau.getPion(ligne, colonne) != null) {
            return false;
        }

        boolean coupValide = false;

        for (int deltaLigne = -1; deltaLigne <= 1; deltaLigne++) {
            for (int deltaColonne = -1; deltaColonne <= 1; deltaColonne++) {
                if (deltaLigne == 0 && deltaColonne == 0) {
                    continue;
                }
                if (peutRetournerPionsDansDirection(ligne, colonne, couleurJoueur, deltaLigne, deltaColonne, doFlip)) {
                    coupValide = true;
                }
            }
        }
        return coupValide;
    }

    private boolean peutRetournerPionsDansDirection(int ligne, int colonne, Couleur couleurJoueur, int deltaLigne, int deltaColonne, boolean doFlip) {
        int ligneActuelle = ligne + deltaLigne;
        int colonneActuelle = colonne + deltaColonne;

        boolean pionsAdversesTrouves = false;
        boolean pionAlieTrouves = false;

        while (estDansPlateau(ligneActuelle, colonneActuelle) && plateau.getPion(ligneActuelle, colonneActuelle) != null
                && plateau.getPion(ligneActuelle, colonneActuelle).getCouleur() != couleurJoueur) {

            pionsAdversesTrouves = true;
            ligneActuelle += deltaLigne;
            colonneActuelle += deltaColonne;
        }

        if (estDansPlateau(ligneActuelle, colonneActuelle) && plateau.getPion(ligneActuelle, colonneActuelle) != null && plateau.getPion(ligneActuelle, colonneActuelle).getCouleur() == couleurJoueur){
            pionAlieTrouves = true;
        }

        if(pionsAdversesTrouves && pionAlieTrouves && doFlip) {
            plateau.getPion(ligneActuelle - deltaLigne, colonneActuelle - deltaColonne).retourner();
        }

        return pionsAdversesTrouves && pionAlieTrouves;
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

    private boolean estCoupValideSansRetourner(int ligne, int colonne, Couleur couleurJoueur) {
        if (!estDansPlateau(ligne, colonne) || plateau.getPion(ligne, colonne) != null) {
            return false;
        }

        boolean coupValide = false;

        for (int deltaLigne = -1; deltaLigne <= 1; deltaLigne++) {
            for (int deltaColonne = -1; deltaColonne <= 1; deltaColonne++) {
                if (deltaLigne == 0 && deltaColonne == 0) {
                    continue;
                }
                if (peutRetournerPionsDansDirectionSansRetourner(ligne, colonne, couleurJoueur, deltaLigne, deltaColonne)) {
                    coupValide = true;
                }
            }
        }
        return coupValide;
    }

    private boolean peutRetournerPionsDansDirectionSansRetourner(int ligne, int colonne, Couleur couleurJoueur, int deltaLigne, int deltaColonne) {
        int ligneActuelle = ligne + deltaLigne;
        int colonneActuelle = colonne + deltaColonne;

        boolean pionsAdversesTrouves = false;
        boolean pionAlieTrouves = false;

        while (estDansPlateau(ligneActuelle, colonneActuelle) && plateau.getPion(ligneActuelle, colonneActuelle) != null
                && plateau.getPion(ligneActuelle, colonneActuelle).getCouleur() != couleurJoueur) {

            pionsAdversesTrouves = true;
            ligneActuelle += deltaLigne;
            colonneActuelle += deltaColonne;
        }

        if (estDansPlateau(ligneActuelle, colonneActuelle) && plateau.getPion(ligneActuelle, colonneActuelle) != null && plateau.getPion(ligneActuelle, colonneActuelle).getCouleur() == couleurJoueur){
            pionAlieTrouves = true;
        }

        return pionsAdversesTrouves && pionAlieTrouves;
    }

    public List<int[]> obtenirCoupsValides(Couleur couleurJoueur) {
        List<int[]> coupsValides = new ArrayList<>();

        for (int i = 0; i < Plateau.TAILLE; i++) {
            for (int j = 0; j < Plateau.TAILLE; j++) {
                if (estCoupValide(i, j, couleurJoueur, false)) {
                    coupsValides.add(new int[]{i, j});
                }
            }
        }

        return coupsValides;
    }



    public Plateau getPlateau() {
        return this.plateau;
    }

    public void setPlateau(Plateau plateauActuel) {
        this.plateau = plateauActuel;
    }
}