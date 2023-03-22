// Importation package
package coordonnee;

// Classe ''Coordonnee''
public class Coordonnee {

    // Attributs privates

    private double x;
    private double y;

    // Constructeur

    public Coordonnee(double x, double y) {
        this.x = x;
        this.y = y;
    }


    // Méthodes variées


    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}