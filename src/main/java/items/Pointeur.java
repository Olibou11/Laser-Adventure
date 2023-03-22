// Importation package
package items;

// Importations

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

// Classe ''Pointeur''
public class Pointeur {

    // Attribut private
    private Group pointeur;

    // Constructeur

    public Pointeur(double x, double y) {

        // Base du pointeur

        Rectangle base = new Rectangle(x,y,125,50);
        Stop[] stops = new Stop[]{new Stop(0, Color.DARKGRAY), new Stop(1, Color.GRAY)};
        LinearGradient gradient = new LinearGradient(0,0, 1,1, true, CycleMethod.NO_CYCLE, stops);
        base.setFill(gradient);
        base.setStroke(Color.BLACK);

        // Bouton et bout du pointeur

        Circle bouton = new Circle(x + 75,y + 25,20);
        bouton.setFill(Color.RED);
        bouton.setStroke(Color.BLACK);
        Rectangle bout = new Rectangle(x + 125,y + 13,10,25);
        bout.setFill(Color.BLACK);

        pointeur = new Group(base, bouton, bout);
    }


    // Méthodes variées


    public Group getPointeur() { return pointeur; }

    public double getRotation() { return pointeur.getRotate(); }

    public void setRotation(double rotation) { pointeur.setRotate(rotation); }
}