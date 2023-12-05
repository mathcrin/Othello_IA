package classe;



public class Pion {
    private Couleur couleur;

    public Pion(Couleur couleur) {
        this.couleur = couleur;
    }

    public static void retourner(int ligne, int colonne, char couleurJoueur) {
    }

    public Couleur getCouleur() {
        return couleur;
    }

    public void retourner() {
        // Logique pour retourner le pion (changer sa couleur par exemple)
        if (couleur == Couleur.BLANC) {
            couleur = Couleur.NOIR;
        } else if (couleur == Couleur.NOIR) {
            couleur = Couleur.BLANC;
        }else{
            System.out.println("Erreur placement pion");
        }
    }
}
