// Importation package
package collisionneur;

// Importations

import coordonnee.Coordonnee;
import items.Laser;
import items.Prisme;
import items.Etoile;

// Classe ''Collisionneur''

public class Collisionneur {

    public static Coordonnee coordonneeImpactPrisme(Prisme prisme, Laser laser, int numero){

        // Calcul du point de rencontre entre le laser et le côté gauche du triangle

        double pointRencontreXG = ((laser.getEquation().get(1) - prisme.getEquation().get(1).get(0)) / (prisme.getEquation().get(0).get(0) - laser.getEquation().get(0))); // .get( 0 == a && 1 == b).get( 0 == G && 1 == D && 2 == B )
        double pointRencontreYG = ((laser.getEquation().get(0) * pointRencontreXG) + laser.getEquation().get(1));
        Coordonnee pointRencontreG = new Coordonnee(pointRencontreXG, pointRencontreYG);

        // Calcul du point de rencontre entre le laser et le côté droit du triangle

        double pointRencontreXD = ((laser.getEquation().get(1) - prisme.getEquation().get(1).get(1)) / (prisme.getEquation().get(0).get(1) - laser.getEquation().get(0))); // .get( 0 == a && 1 == b).get( 0 == G && 1 == D && 2 == B )
        double pointRencontreYD = ((laser.getEquation().get(0) * pointRencontreXD) + laser.getEquation().get(1));
        Coordonnee pointRencontreD = new Coordonnee(pointRencontreXD, pointRencontreYD);

        // Calcul du point de rencontre entre le laser et le côté bas du triangle

        double pointRencontreXB = ((laser.getEquation().get(1) - prisme.getEquation().get(1).get(2)) / (prisme.getEquation().get(0).get(2) - laser.getEquation().get(0))); // .get( 0 == a && 1 == b).get( 0 == G && 1 == D && 2 == B )
        double pointRencontreYB = ((laser.getEquation().get(0) * pointRencontreXB) + laser.getEquation().get(1));
        Coordonnee pointRencontreB = new Coordonnee(pointRencontreXB, pointRencontreYB);

        // Vérification d'un impact à gauche

        if (numero != 2) {
            if (pointRencontreG.getX() >= prisme.getCoordonneeBasGauche().getX() && pointRencontreG.getX() <= prisme.getCoordonnee().getX()) {
                if (pointRencontreG.getY() >= prisme.getCoordonnee().getY() && pointRencontreG.getY() <= prisme.getCoordonneeBasGauche().getY()) {
                    return pointRencontreG;
                }
            }
        }

        // Vérification d'un impact à droite

        if(numero != 4) {
            if (pointRencontreD.getX() >= prisme.getCoordonnee().getX()) {
                if (pointRencontreD.getY() >= prisme.getCoordonnee().getY() && pointRencontreD.getY() <= prisme.getCoordonneeBasDroit().getY()) {
                    return pointRencontreD;
                }
            }
        }

        // Vérification d'un impact en bas

        if (pointRencontreB.getX() >= prisme.getCoordonneeBasGauche().getX()) {
            if (pointRencontreB.getY() >= prisme.getCoordonneeBasGauche().getY()) {
                return pointRencontreB;
            }
        }

        return null; // Aucune collision
    }

    public static int surfaceImpactPrisme(Prisme prisme, Laser laser, int numero){

        // Calcul du point de rencontre entre le laser et le côté gauche du triangle

        double pointRencontreXG = ((laser.getEquation().get(1) - prisme.getEquation().get(1).get(0)) / (prisme.getEquation().get(0).get(0) - laser.getEquation().get(0))); // .get( 0 == a && 1 == b).get( 0 == G && 1 == D && 2 == B )
        double pointRencontreYG = ((laser.getEquation().get(0) * pointRencontreXG) + laser.getEquation().get(1));
        Coordonnee pointRencontreG = new Coordonnee(pointRencontreXG, pointRencontreYG);

        // Calcul du point de rencontre entre le laser et le côté droit du triangle

        double pointRencontreXD = ((laser.getEquation().get(1) - prisme.getEquation().get(1).get(1)) / (prisme.getEquation().get(0).get(1) - laser.getEquation().get(0))); // .get( 0 == a && 1 == b).get( 0 == G && 1 == D && 2 == B )
        double pointRencontreYD = ((laser.getEquation().get(0) * pointRencontreXD) + laser.getEquation().get(1));
        Coordonnee pointRencontreD = new Coordonnee(pointRencontreXD, pointRencontreYD);

        // Calcul du point de rencontre entre le laser et le côté bas du triangle

        double pointRencontreXB = ((laser.getEquation().get(1) - prisme.getEquation().get(1).get(2)) / (prisme.getEquation().get(0).get(2) - laser.getEquation().get(0))); // .get( 0 == a && 1 == b).get( 0 == G && 1 == D && 2 == B )
        double pointRencontreYB = ((laser.getEquation().get(0) * pointRencontreXB) + laser.getEquation().get(1));
        Coordonnee pointRencontreB = new Coordonnee(pointRencontreXB, pointRencontreYB);

        // Vérification d'un impact à gauche

        if (numero != 2) {
            if (pointRencontreG.getX() >= prisme.getCoordonneeBasGauche().getX() && pointRencontreG.getX() <= prisme.getCoordonnee().getX()) {
                if (pointRencontreG.getY() >= prisme.getCoordonnee().getY() && pointRencontreG.getY() <= prisme.getCoordonneeBasGauche().getY()) {
                    return 1;
                }
            }
        }

        // Vérification d'un impact à droite

        if(numero != 4) {
            if (pointRencontreD.getX() >= prisme.getCoordonnee().getX()) {
                if (pointRencontreD.getY() >= prisme.getCoordonnee().getY() && pointRencontreD.getY() <= prisme.getCoordonneeBasDroit().getY()) {
                    return 2;
                }
            }
        }

        // Vérification d'un impact en bas

        if (pointRencontreB.getX() >= prisme.getCoordonneeBasGauche().getX()) {
            if (pointRencontreB.getY() >= prisme.getCoordonneeBasGauche().getY()) {
                return 3;
            }
        }

        return 0; // Aucune collision
    }

    public static Coordonnee coordonneeImpactBordure(Laser laser, boolean special){

        // Calcul du point de rencontre entre le laser et le côté haut des bordures

        double pointRencontreXH = ((laser.getEquation().get(1) - 5) / (0 - laser.getEquation().get(0)));
        double pointRencontreYH = ((laser.getEquation().get(0) * pointRencontreXH) + laser.getEquation().get(1));
        Coordonnee pointRencontreH = new Coordonnee(pointRencontreXH, pointRencontreYH);

        // Calcul du point de rencontre entre le laser et le côté bas des bordures

        double pointRencontreXB = ((laser.getEquation().get(1) - 650) / (0 - laser.getEquation().get(0)));
        double pointRencontreYB = ((laser.getEquation().get(0) * pointRencontreXB) + laser.getEquation().get(1));
        Coordonnee pointRencontreB = new Coordonnee(pointRencontreXB, pointRencontreYB);

        // Calcul du point de rencontre entre le laser et le côté droit des bordures

        double pointRencontreXD = 1360;
        double pointRencontreYD = ((laser.getEquation().get(0) * pointRencontreXD) + laser.getEquation().get(1));
        Coordonnee pointRencontreD = new Coordonnee(pointRencontreXD, pointRencontreYD);

        // Cas spécial

        if (special) {

            // Vérification d'un impact en bas

            if (pointRencontreB.getX() >= -0.00001 && pointRencontreB.getX() <= 1360.1) {
                if (pointRencontreB.getY() <= 650.00001 && pointRencontreB.getY() >= 649.9) {
                    return pointRencontreB;
                }
            }
        }

        // Une inclinaison inférieure à 0

        if (laser.getInclinaison() <= 0) {

            // Vérification d'un impact à droite

            if (pointRencontreD.getX() <= 1360.1 && pointRencontreD.getX() >= 1359.9) {
                if (pointRencontreD.getY() <= 650.1 && pointRencontreD.getY() >= -0.00001) {
                    return pointRencontreD;
                }
            }

            // Vérification d'un impact en haut

            if (pointRencontreH.getX() >= -0.00001 && pointRencontreH.getX() <= 1360.1) {
                if (pointRencontreH.getY() >= 4.9 && pointRencontreH.getY() <= 5.00001) {
                    return pointRencontreH;
                }
            }

            // Vérification d'un impact en bas

            if (pointRencontreB.getX() >= -0.00001 && pointRencontreB.getX() <= 1360.1) {
                if (pointRencontreB.getY() <= 650.00001 && pointRencontreB.getY() >= 649.9) {
                    return pointRencontreB;
                }
            }

        } else { // Une inclinaison supérieure à 0

            // Vérification d'un impact à droite

            if (pointRencontreD.getX() <= 1360.1 && pointRencontreD.getX() >= 1359.9) {
                if (pointRencontreD.getY() <= 650.1 && pointRencontreD.getY() >= -0.00001) {
                    return pointRencontreD;
                }
            }

            // Vérification d'un impact en bas

            if (pointRencontreB.getX() >= -0.00001 && pointRencontreB.getX() <= 1360.1) {
                if (pointRencontreB.getY() <= 650.00001 && pointRencontreB.getY() >= 649.9) {
                    return pointRencontreB;
                }
            }

            // Vérification d'un impact en haut

            if (pointRencontreH.getX() >= -0.00001 && pointRencontreH.getX() <= 1360.1) {
                if (pointRencontreH.getY() >= 4.9 && pointRencontreH.getY() <= 5.00001) {
                    return pointRencontreH;
                }
            }
        }
        return null; // Aucun impact
    }

    public static Coordonnee coordonneeImpactEtoile(Etoile etoile, Laser laser){

        // Équations du laser

        double a = (laser.getEndY() - laser.getStartY()) / (laser.getEndX() - laser.getStartX());
        double b = (laser.getStartY() - ( a * laser.getStartX()));

        // Points de rencontre

        double pointRencontreX1 = ((b - etoile.getEquation().get(1).get(9)) / (etoile.getEquation().get(0).get(9) - a));
        double pointRencontreY1 = ((a * pointRencontreX1) + b);
        Coordonnee pointImpact = new Coordonnee(pointRencontreX1, pointRencontreY1);

        // Vérification

        if(pointImpact.getX() < etoile.getSommet().get(0).getX() && pointImpact.getX() > etoile.getSommet().get(9).getX()){
            return pointImpact;
        }
        return null; // Aucun impact
    }
}