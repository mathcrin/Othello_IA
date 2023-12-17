package classe;



public class Pion {
    private Couleur couleur;

    public Pion(Couleur couleur) {
        this.couleur = couleur;
    }

    public Couleur getCouleur() {
        return couleur;
    }

    public void retourner() {
        // Logique pour retourner le pion (changer sa couleur par exemple)
        if(couleur!= null){
            couleur = couleur.getOppose();
        }else{
            System.out.println("La couleur du pion est null");
        }
    }
}
