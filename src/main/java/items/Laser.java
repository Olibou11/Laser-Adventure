// Imporation package
package items;

// Importations

import coordonnee.Coordonnee;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import java.util.ArrayList;
import java.util.List;

// Classe ''Laser''
public class Laser extends Group {

    // Attributs privates

    private Line laser;
    private double inclinaison;

    // Constructeur

    public Laser(double startX, double startY, double endX, double endY, double inclinaison) {
        this.inclinaison = inclinaison;
        this.laser = new Line(startX, startY, endX, endY);
        getChildren().add(laser);
    }

    // Constructeur simple

    public Laser(){
        this.laser = new Line();
        laser.setStroke(Color.RED);
        getChildren().add(laser);
    }


    // Méthodes variées


    public List<Double> getEquation(){

        List<Double> listEquation = new ArrayList<>();

        // Calculs

        double a = 0;
        if(getInclinaison() != 0)
            a = (Math.tan(Math.toRadians(getInclinaison())));
        double b = (getStartY() - (a * getStartX()));

        // Ajout des résultats dans les listes

        listEquation.add(a);
        listEquation.add(b);

        return listEquation;
    }

    public double getInclinaison() {
        return inclinaison;
    }

    public void setInclinaison(double inclinaison) {
        this.inclinaison = inclinaison;
    }

    public double getEndX(){return laser.getEndX();}

    public void setEndX(double x){laser.setEndX(x);}

    public double getEndY(){return laser.getEndY();}

    public void setEndY(double y){laser.setEndY(y);}

    public double getStartX(){return laser.getStartX();}

    public void setStartX(double x){laser.setStartX(x);}

    public double getStartY(){return laser.getStartY();}

    public void setStartY(double y){laser.setStartY(y);}

    public Coordonnee getCoordonneesDepart() { return new Coordonnee(laser.getStartX(), laser.getStartY()); }

    public void setCoordonneesDepart(Coordonnee coordonnee){
        laser.setStartX(coordonnee.getX());
        laser.setStartY(coordonnee.getY());
    }

    public Coordonnee getCoordonneesFin(){
        return new Coordonnee(laser.getEndX(), laser.getEndY());
    }

    public void setCoordonneesFin(Coordonnee coordonnee){
        laser.setEndX(coordonnee.getX());
        laser.setEndY(coordonnee.getY());
    }

    public void reset(Laser l) {
        laser.setStartX(l.getStartX());
        laser.setStartY(l.getStartY());
        laser.setEndX(l.getStartX());
        laser.setEndY(l.getStartY());
    }
}