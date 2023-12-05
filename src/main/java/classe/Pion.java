package classe;



public class Pion {
    private char couleur;

    public Pion(char couleur) {
        this.couleur = couleur;
    }

    public static void retourner(int ligne, int colonne, char couleurJoueur) {
    }

    public char getCouleur() {
        return couleur;
    }

    public void retourner() {
        // Logique pour retourner le pion (changer sa couleur par exemple)
        if (couleur == 'X') {
            couleur = 'O';
        } else if (couleur == 'O') {
            couleur = 'X';
        }
    }
}
