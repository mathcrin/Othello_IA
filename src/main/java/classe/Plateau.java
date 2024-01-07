package classe;
import java.util.ArrayList;

public class Plateau implements Cloneable {
    public static final int TAILLE = 8;
    private ArrayList<ArrayList<Pion>> plateau;

    public Plateau() {
        plateau = new ArrayList<>();
        initialiserPlateau();
    }

    private void initialiserPlateau() {
        for (int i = 0; i < TAILLE; i++) {
            ArrayList<Pion> row = new ArrayList<>();
            for (int j = 0; j < TAILLE; j++) {
                row.add(null); // null represents an empty square
            }
            plateau.add(row);
        }

        plateau.get(3).set(3, new Pion(Couleur.NOIR)); // Black Pion
        plateau.get(3).set(4, new Pion(Couleur.BLANC)); // White Pion
        plateau.get(4).set(3, new Pion(Couleur.BLANC)); // White Pion
        plateau.get(4).set(4, new Pion(Couleur.NOIR)); // Black Pion
    }

    public void setPion(int ligne, int colonne, Pion pion) {
        plateau.get(ligne).set(colonne, pion);
    }

    public Pion getPion(int ligne, int colonne) {
        return plateau.get(ligne).get(colonne);
    }

    public void afficherPlateau() {
        System.out.println("  0 1 2 3 4 5 6 7");
        for (int i = 0; i < TAILLE; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < TAILLE; j++) {
                if (plateau.get(i).get(j) == null) {
                    System.out.print("  "); // Empty square
                } else {
                    System.out.print(plateau.get(i).get(j).getCouleur() + " ");
                }
            }
            System.out.println();
        }
    }

    @Override
    public Plateau clone() {
        try {
            Plateau clone = (Plateau) super.clone();
            clone.plateau = new ArrayList<>();
            for (int i = 0; i < TAILLE; i++) {
                ArrayList<Pion> row = new ArrayList<>();
                for (int j = 0; j < TAILLE; j++) {
                    Pion originalPion = plateau.get(i).get(j);
                    Pion clonedPion = originalPion != null ? originalPion.clone() : null;
                    row.add(clonedPion);
                }
                clone.plateau.add(row);
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}