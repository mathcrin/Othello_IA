package classe;
//on veut un plateau de 8x8
public class Plateau {
    public static final int TAILLE = 8;
    private Pion[][] plateau;

    public Plateau() {
        plateau = new Pion[8][8];
        initialiserPlateau();
    }

    private void initialiserPlateau() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                plateau[i][j] = null; // null représente une case vide
            }
        }

        plateau[3][3] = plateau[4][4] = new Pion('O'); // Pion noir
        plateau[3][4] = plateau[4][3] = new Pion('X'); // Pion blanc
    }

    public void setPion(int ligne, int colonne, Pion pion) {
        plateau[ligne][colonne] = pion;
    }

    public Pion getPion(int ligne, int colonne) {
        return plateau[ligne][colonne];
    }

    public void afficherPlateau() {
        System.out.println("  0 1 2 3 4 5 6 7");
        for (int i = 0; i < 8; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < 8; j++) {
                if (plateau[i][j] == null) {
                    System.out.print("  "); // Case vide
                } else {
                    System.out.print(plateau[i][j].getCouleur() + " ");
                }
            }
            System.out.println();
        }
    }
}