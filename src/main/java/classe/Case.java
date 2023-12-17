package classe;

public class Case {
    private int x;
    private int y;
    private Couleur couleur;

    public Case(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() { return this.x; }
    public int getY() { return this.y; }

    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public Couleur getCouleur() { return this.couleur; }
    public void setCouleur(Couleur couleur) { this.couleur = couleur; }


}
