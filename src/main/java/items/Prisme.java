// Importation package
package items;

// Importations

import coordonnee.Coordonnee;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import java.util.ArrayList;
import java.util.List;

// Classe ''Prisme''
public class Prisme extends Group {

    // Attributs privates

    private Polygon prisme;
    private Coordonnee coordonnee;
    private final Coordonnee coordonneeBasGauche;
    private final Coordonnee coordonneeBasDroit;
    private final List<List<Double>> listEquations = new ArrayList<>();
    private double indiceDeRefraction;

    // Constructeur

    public Prisme(Coordonnee coordonnee, double indiceDeRefraction) {

        this.coordonnee = coordonnee;
        this.indiceDeRefraction = indiceDeRefraction;

        coordonneeBasGauche = new Coordonnee(coordonnee.getX() - 100, coordonnee.getY() + 173);
        coordonneeBasDroit = new Coordonnee(coordonnee.getX() + 100, coordonnee.getY() + 173);

        prisme = new Polygon(coordonnee.getX(), coordonnee.getY(), coordonnee.getX() - 100, coordonnee.getY() + 173, coordonnee.getX() + 100, coordonnee.getY() + 173);

        // Décorations

        prisme.setFill(Color.rgb(66, 189, 237));
        prisme.setStroke(Color.BLACK);
        prisme.setOpacity(0.3);


        // Trouver les équations des droites (3)


        List<Double> listA = new ArrayList<>();
        List<Double> listB = new ArrayList<>();

        // Taux de variation = a / Ordonnée à l'origine = b

        // Droite à gauche du triangle
        double a1 = ((getCoordonnee().getY() - getCoordonneeBasGauche().getY()) / (getCoordonnee().getX() - getCoordonneeBasGauche().getX()));
        double b1 = (getCoordonnee().getY() - (a1 * getCoordonnee().getX()));
        listA.add(a1);
        listB.add(b1);

        // Droite à droite du triangle
        double a2 = ((getCoordonnee().getY() - getCoordonneeBasDroit().getY()) / (getCoordonnee().getX() - getCoordonneeBasDroit().getX()));
        double b2 = (getCoordonnee().getY() - (a2 * getCoordonnee().getX()));
        listA.add(a2);
        listB.add(b2);

        // Droite en dessous du triangle
        double a3 = 0;
        double b3 = getCoordonneeBasGauche().getY();
        listA.add(a3);
        listB.add(b3);

        listEquations.add(listA);
        listEquations.add(listB);

        getChildren().addAll(prisme);
    }


    // Méthodes variées


    public Coordonnee getCoordonnee() {
        return coordonnee;
    }

    public void setCoordonnee(Coordonnee coordonnee) {
        this.coordonnee = coordonnee;
    }

    public Coordonnee getCoordonneeBasGauche() {return coordonneeBasGauche;}

    public Coordonnee getCoordonneeBasDroit() {return coordonneeBasDroit;}

    public double getIndiceDeRefraction() {
        return indiceDeRefraction;
    }

    public void setIndiceDeRefraction(double indiceDeRefraction) {
        this.indiceDeRefraction = indiceDeRefraction;
    }

    public void setColor(Color couleur) {
        prisme.setFill(couleur);
    }

    public List<List<Double>> getEquation() {return listEquations;}
}