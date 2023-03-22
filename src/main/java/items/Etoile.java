// Importation package
package items;

// Importations

import coordonnee.Coordonnee;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import java.util.ArrayList;
import java.util.List;

// Classe ''Etoile''
public class Etoile extends Group {

    // Attributs privates

    private final Polygon etoile;
    private final Coordonnee coordonnee;
    private final boolean type; // Orange == true || verte == false
    private final List<Coordonnee> listSommets = new ArrayList<>(); // Commence du sommet le plus haut et suit le sens horaire
    private final List<List<Double>> listEquations = new ArrayList<>(); // .get( 0 == a || 1 == b).get( 0 à 9 == une arête de l'étoile)
    private boolean enable; // Active l'image ou non

    // Constructeur

    public Etoile(Coordonnee coordonnee, boolean type) {

        // Attributs

        this.type = type;
        this.coordonnee = coordonnee;

        // Création de l'étoile

        etoile = new Polygon(
                coordonnee.getX(), coordonnee.getY(),
                coordonnee.getX() + 5, coordonnee.getY() + 15,
                coordonnee.getX() + 20, coordonnee.getY() + 15,
                coordonnee.getX() + 10, coordonnee.getY() + 25,
                coordonnee.getX() + 15, coordonnee.getY() + 40,
                coordonnee.getX(), coordonnee.getY() + 30,
                coordonnee.getX() - 15, coordonnee.getY() + 40,
                coordonnee.getX() - 10, coordonnee.getY() + 25,
                coordonnee.getX() - 20, coordonnee.getY() + 15,
                coordonnee.getX() - 5, coordonnee.getY() + 15
        );

        // Création d'une liste contenant tous les sommets de l'étoile

        listSommets.add(new Coordonnee(coordonnee.getX(), coordonnee.getY()));
        listSommets.add(new Coordonnee(coordonnee.getX() + 5, coordonnee.getY() + 15));
        listSommets.add(new Coordonnee(coordonnee.getX() + 20, coordonnee.getY() + 15));
        listSommets.add(new Coordonnee(coordonnee.getX() + 10, coordonnee.getY() + 25));
        listSommets.add(new Coordonnee(coordonnee.getX() + 15, coordonnee.getY() + 40));
        listSommets.add(new Coordonnee(coordonnee.getX(), coordonnee.getY() + 30));
        listSommets.add(new Coordonnee(coordonnee.getX() - 15, coordonnee.getY() + 40));
        listSommets.add(new Coordonnee(coordonnee.getX() - 10, coordonnee.getY() + 25));
        listSommets.add(new Coordonnee(coordonnee.getX() - 20, coordonnee.getY() + 15));
        listSommets.add(new Coordonnee(coordonnee.getX() - 5, coordonnee.getY() + 15));


        // Création d'une liste qui va contenir toutes les équations linéaires (a et b) des droites du triangle


        List<Double> a = new ArrayList<>();
        List<Double> b = new ArrayList<>();

        // Taux de variations

        a.add((double) -3);
        a.add((double) 0);
        a.add((double) 1);
        a.add((double) -3);
        a.add((double) -2 / 3);
        a.add((double) 2 / 3);
        a.add((double) 3);
        a.add((double) -1);
        a.add((double) 0);
        a.add((double) 3);

        // Coordonnées à l'origine

        b.add(listSommets.get(0).getY() - (a.get(0) * listSommets.get(0).getX()));
        b.add(listSommets.get(1).getY() - (a.get(1) * listSommets.get(1).getX()));
        b.add(listSommets.get(2).getY() - (a.get(2) * listSommets.get(2).getX()));
        b.add(listSommets.get(3).getY() - (a.get(3) * listSommets.get(3).getX()));
        b.add(listSommets.get(4).getY() - (a.get(4) * listSommets.get(4).getX()));
        b.add(listSommets.get(5).getY() - (a.get(5) * listSommets.get(5).getX()));
        b.add(listSommets.get(6).getY() - (a.get(6) * listSommets.get(6).getX()));
        b.add(listSommets.get(7).getY() - (a.get(7) * listSommets.get(7).getX()));
        b.add(listSommets.get(8).getY() - (a.get(8) * listSommets.get(8).getX()));
        b.add(listSommets.get(9).getY() - (a.get(9) * listSommets.get(9).getX()));

        // Ajout des listes (a et b) dans la liste d'équations

        listEquations.add(a);
        listEquations.add(b);


        // Initialisation du type

        if (type)
            etoile.setFill(Color.ORANGE);
        else
            etoile.setFill(Color.GREEN);

        getChildren().addAll(etoile);
    }


    // Méthodes variées


    public void disable(){
        etoile.setFill(Color.TRANSPARENT);
        enable = false;
    }

    public void enable(){
        if(type)
            etoile.setFill(Color.ORANGE);
        else
            etoile.setFill(Color.GREEN);
        enable = true;
    }

    public Boolean isEnable(){
        return enable;
    }

    public boolean getType() { return type; }

    public Coordonnee getCoordonnee(){
        return coordonnee;
    }

    public List<Coordonnee> getSommet() { return listSommets;}

    public List<List<Double>> getEquation() { return listEquations;}
}