// Importation package
package simulateur;

// Importations

import collisionneur.Collisionneur;
import coordonnee.Coordonnee;
import items.Etoile;
import javafx.scene.control.Alert;
import javafx.scene.shape.Rectangle;
import items.Laser;
import main.Main;
import miniJeux.MiniJeux;
import items.Prisme;
import son.SonCible;
import java.util.ArrayList;
import java.util.Objects;

// Classe ''Simulateur''

public class Simulateur {

    // Méthode ''simulationCube''

    public static void simulationCube(ArrayList<Laser> listLasers, double angleRayon, Rectangle cube, double indiceRefraction) {

        // Déclaration du premier rayon

        listLasers.get(0).setCoordonneesDepart(new Coordonnee(162.5, 350.5));
        listLasers.get(0).setCoordonneesFin(new Coordonnee(162.5, 350.5));

        // Calcul de la pente du premier rayon

        double aRayon1 = 0;
        if (angleRayon != 0)
            aRayon1 = Math.tan(Math.toRadians(angleRayon));

        // Aucune réfraction

        if (indiceRefraction == 1) {

            boolean aucuneCollision = false;

            do {
                listLasers.get(0).setCoordonneesFin(new Coordonnee(listLasers.get(0).getEndX() + 1, listLasers.get(0).getEndY() + aRayon1));

                if (listLasers.get(0).getEndY() <= 40)
                    aucuneCollision = true; // Collision avec la bordure du haut
                else if (listLasers.get(0).getEndY() >= 640)
                    aucuneCollision = true; // Collision avec la bordure du bas
                else if (listLasers.get(0).getEndX() >= 1200)
                    aucuneCollision = true; // Collision avec la bordure droite

            } while (!aucuneCollision);

            // Réinitialisation des rayons

            for (int i = 1; i < 4; i++)
                listLasers.get(i).reset(listLasers.get(0));

        } else {

            // Diffusion du rayon 1 pour trouver une collision

            boolean collisionRayon1 = false;

            do {
                listLasers.get(0).setCoordonneesFin(new Coordonnee(listLasers.get(0).getEndX() + 1, listLasers.get(0).getEndY() + aRayon1));

                if (listLasers.get(0).getEndY() <= 40)
                    collisionRayon1 = true; // Collision avec la bordure du haut
                else if (listLasers.get(0).getEndY() >= 640)
                    collisionRayon1 = true; // Collision avec la bordure du bas
                else if (listLasers.get(0).intersects(cube.getLayoutBounds()))
                    collisionRayon1 = true; // Collision avec le cube

            } while (!collisionRayon1);

            // Diffusion du rayon s'il n'y a aucune collision

            if (listLasers.get(0).getEndY() < cube.getY() || listLasers.get(0).getEndY() > cube.getY() + 200) {

                boolean collsionBordures = false;

                do {
                    listLasers.get(0).setCoordonneesFin(new Coordonnee(listLasers.get(0).getEndX() + 1, listLasers.get(0).getEndY() + aRayon1));

                    if (listLasers.get(0).getEndY() <= 40)
                        collsionBordures = true; // Collision avec la bordure du haut
                    else if (listLasers.get(0).getEndY() >= 640)
                        collsionBordures = true; // Collision avec la bordure du bas

                } while (!collsionBordures);

                // Réinitialisation des rayons

                for (int i = 1; i < 4; i++)
                    listLasers.get(i).reset(listLasers.get(0));

            } else {

                // Calcul de l'angle du deuxième rayon
                double angleRayon2 = Math.toDegrees(Math.sinh((Math.sin(Math.toRadians(angleRayon)) / indiceRefraction)));

                // Déclaration du deuxième rayon

                listLasers.get(1).setCoordonneesDepart(listLasers.get(0).getCoordonneesFin());
                listLasers.get(1).setCoordonneesFin(listLasers.get(0).getCoordonneesFin());

                // Calcul de la pente du deuxième rayon

                double aRayon2 = 0;
                if (angleRayon2 != 0)
                    aRayon2 = Math.tan(Math.toRadians(angleRayon2));

                // Diffusion du rayon pour trouver une collision

                do {
                    listLasers.get(1).setCoordonneesFin(new Coordonnee(listLasers.get(1).getEndX() + 1,listLasers.get(1).getEndY() + aRayon2));
                } while (listLasers.get(1).getEndX() < 750);

                // Calcul de l'ordonnée à l'origine du deuxième rayon
                double b = listLasers.get(1).getStartY() - (listLasers.get(1).getStartX() * aRayon2);

                if (listLasers.get(1).getEndY() < 250) // Le deuxième rayon dépasse vers le haut
                    listLasers.get(1).setCoordonneesFin(new Coordonnee(((250 - b) / aRayon2), 250)); // Trouver le x quand y est éguale à 250
                else if (listLasers.get(1).getEndY() > 450) // Le deuxième rayon dépasse vers le bas
                    listLasers.get(1).setCoordonneesFin(new Coordonnee(((450 - b) / aRayon2), 450)); // Trouver le x quand y est éguale à 450

                // RTI seulement sur les côtés

                if (listLasers.get(1).getEndY() == 450 || listLasers.get(1).getEndY() == 250) {

                    // Vérificarion s'il y a une RTI

                    double angleCritique = Math.toDegrees(Math.sinh(1 / indiceRefraction));
                    double angleIncident2 = 90 - angleRayon2;

                    // Note : L'angleIncident2 n'est jamais éguale à l'angle critique alors il n'est jamais réfracté à 90°

                    if (angleIncident2 > angleCritique) {

                        // Déclaration du rayon reflété

                        listLasers.get(3).setCoordonneesDepart(listLasers.get(1).getCoordonneesFin());
                        listLasers.get(3).setCoordonneesFin(listLasers.get(1).getCoordonneesFin());

                        // Diffusion du rayon reflété pour trouver une collision

                        do {
                            listLasers.get(3).setCoordonneesFin(new Coordonnee(listLasers.get(3).getEndX() + 1, listLasers.get(3).getEndY() + (-1 * aRayon2)));
                        } while (listLasers.get(3).getEndX() < 750);

                        // Déclaration du troisième rayon

                        listLasers.get(2).setCoordonneesDepart(listLasers.get(3).getCoordonneesFin());
                        listLasers.get(2).setCoordonneesFin(listLasers.get(3).getCoordonneesFin());

                        // Calcul de l'angle du troisième rayon

                        double angleIncident3 = 90 - angleIncident2;
                        double angleRayon3 = Math.toDegrees(Math.sinh((Math.sin(Math.toRadians(angleIncident3)) * indiceRefraction)));

                        // Calcul de la pente du deuxième rayon

                        double aRayon3 = 0;
                        if (angleRayon3 != 0)
                            aRayon3 = Math.tan(Math.toRadians(angleRayon3));

                        // Diffusion du rayon

                        int y = 0;
                        do {
                            listLasers.get(2).setCoordonneesFin(new Coordonnee(listLasers.get(2).getEndX() + 1, listLasers.get(2).getEndY() + (-1 * aRayon3)));
                            y++;
                        } while (y != 550); // Valeur fictive pour le faire dépasser de l'écran
                    }
                } else {

                    // Il n'y a aucune RTI
                    // Déclaration du troisième rayon

                    listLasers.get(2).setCoordonneesDepart(listLasers.get(1).getCoordonneesFin());
                    listLasers.get(2).setCoordonneesFin(listLasers.get(1).getCoordonneesFin());

                    // Diffusion du rayon

                    int y = 0;
                    do {
                        listLasers.get(2).setCoordonneesFin(new Coordonnee(listLasers.get(2).getEndX() + 1, listLasers.get(2).getEndY() + aRayon1));
                        y++;
                    } while (y != 550); // Valeur fictive pour le faire dépasser de l'écran

                    // Rayon qui ne sont pas nécessaire
                    listLasers.get(3).reset(listLasers.get(0));
                }
            }
        }
    }

    // Méthode ''simulationTriangle''

    public static void simulationTriangle(ArrayList<Laser> listLasers, Prisme prisme) {

        // Caractéristique de départ du rayon (0)
        listLasers.get(0).setCoordonneesDepart(new Coordonnee(162.5, 350.5));


        if (prisme.getIndiceDeRefraction() == 1) {

            // Impact du rayon (0) avec les bordures
            if(Collisionneur.coordonneeImpactBordure(listLasers.get(0), false) != null)
                listLasers.get(0).setCoordonneesFin(Objects.requireNonNull(Collisionneur.coordonneeImpactBordure(listLasers.get(0), false)));

            // Réinitialisation des rayons
            for (int i = 1; i < 4; i++) {
                listLasers.get(i).reset(listLasers.get(0));
            }

        } else {

            if (Collisionneur.coordonneeImpactPrisme(prisme, listLasers.get(0), 1) != null) {

                // Impact du rayon (0) avec le côté gauche du triangle
                if(Collisionneur.coordonneeImpactPrisme(prisme, listLasers.get(0), 1) != null)
                    listLasers.get(0).setCoordonneesFin(Objects.requireNonNull(Collisionneur.coordonneeImpactPrisme(prisme, listLasers.get(0), 1)));

                // Calculs de l'angle du rayon (1)

                double angleIncidentRayon1 = 30 - listLasers.get(0).getInclinaison();
                double angleRefracteRayon1 = Math.toDegrees(Math.sinh(Math.sin(Math.toRadians(angleIncidentRayon1)) / prisme.getIndiceDeRefraction()));
                listLasers.get(1).setInclinaison((30 - angleRefracteRayon1));

                // Caractéristique de départ du rayon (1)
                listLasers.get(1).setCoordonneesDepart(listLasers.get(0).getCoordonneesFin());

                if (Collisionneur.coordonneeImpactPrisme(prisme, listLasers.get(1), 2) != null) {

                    // Impact du rayon (1) avec le côté droit du triangle
                    if(Collisionneur.coordonneeImpactPrisme(prisme, listLasers.get(1), 2) != null)
                        listLasers.get(1).setCoordonneesFin(Objects.requireNonNull(Collisionneur.coordonneeImpactPrisme(prisme, listLasers.get(1), 2)));

                    // Caractéristique de départ du rayon (2)
                    listLasers.get(2).setCoordonneesDepart(listLasers.get(1).getCoordonneesFin());

                    if (RTI(listLasers.get(1), prisme)) {

                        // RTI sur le côté bas (3) du prisme
                        if (Collisionneur.surfaceImpactPrisme(prisme, listLasers.get(1), 2) == 3) {

                            // Calcul de l'angle du rayon (2)
                            listLasers.get(2).setInclinaison(listLasers.get(1).getInclinaison() * -1); // (Il s'agit d'une réflexion, ainsi * -1)

                            // Impact du rayon (2) avec le triangle
                            if(Collisionneur.coordonneeImpactPrisme(prisme, listLasers.get(2), 2) != null)
                                listLasers.get(2).setCoordonneesFin(Objects.requireNonNull(Collisionneur.coordonneeImpactPrisme(prisme, listLasers.get(2), 2)));

                            // Caractéristique de départ du rayon (3)
                            listLasers.get(3).setCoordonneesDepart(listLasers.get(2).getCoordonneesFin());

                            // Calcul de l'angle du rayon (3)

                            double angleIncidentRayon3 = (90 - (180 - (120 + listLasers.get(2).getInclinaison())));
                            double angleRefractionRayon3 = Math.toDegrees(Math.sinh(Math.sin(Math.toRadians(angleIncidentRayon3)) * prisme.getIndiceDeRefraction()));
                            listLasers.get(3).setInclinaison(60 - (90 - angleRefractionRayon3));

                            // Impact du rayon (3) avec les bordures
                            if(Collisionneur.coordonneeImpactBordure(listLasers.get(3), false) != null)
                                listLasers.get(3).setCoordonneesFin(Objects.requireNonNull(Collisionneur.coordonneeImpactBordure(listLasers.get(3), false)));
                        }

                        // RTI sur le côté droit (2) du prisme
                        if (Collisionneur.surfaceImpactPrisme(prisme, listLasers.get(1), 2) == 2) {

                            // Calcul de l'angle du rayon (2)
                            listLasers.get(2).setInclinaison((listLasers.get(1).getInclinaison() + 90)); // (Il s'agit d'une réflexion, ainsi + 90)

                            // Impact du rayon (2) avec le triangle
                            if(Collisionneur.coordonneeImpactPrisme(prisme, listLasers.get(2), 4) != null)
                                listLasers.get(2).setCoordonneesFin(Objects.requireNonNull(Collisionneur.coordonneeImpactPrisme(prisme, listLasers.get(2), 4)));

                            // Caractéristiques de départ du rayon (3)
                            listLasers.get(3).setCoordonneesDepart(listLasers.get(2).getCoordonneesFin());

                            // Calcul de l'angle du rayon (3)

                            double angleIncident1Rayon3 = 90 - (75 - listLasers.get(2).getInclinaison());
                            double angleRefracte1Rayon3 = Math.toDegrees(Math.sinh(Math.sin(Math.toRadians(angleIncident1Rayon3)) * prisme.getIndiceDeRefraction()));
                            listLasers.get(3).setInclinaison(angleRefracte1Rayon3);

                            // Impact du rayon (3) avec les bordures
                            if(Collisionneur.coordonneeImpactBordure(listLasers.get(3), false) != null)
                                listLasers.get(3).setCoordonneesFin(Objects.requireNonNull(Collisionneur.coordonneeImpactBordure(listLasers.get(3), true)));
                        }

                    } else { // Il y a aucune RTI

                        // Impact du laser (2) avec le triangle
                        if(Collisionneur.coordonneeImpactPrisme(prisme, listLasers.get(2), 2) != null)
                            listLasers.get(2).setCoordonneesFin(Objects.requireNonNull(Collisionneur.coordonneeImpactPrisme(prisme, listLasers.get(2), 2)));

                        // Caractéristique de départ du rayon (3)
                        listLasers.get(3).setCoordonneesDepart(listLasers.get(2).getCoordonneesFin());

                        // Calcul de l'angle du rayon (3)

                        double angleIncident2Rayon3 = (90 - (180 - (120 + listLasers.get(1).getInclinaison())));
                        double angleRefracte2Rayon3 = Math.toDegrees(Math.sinh(Math.sin(Math.toRadians(angleIncident2Rayon3)) * prisme.getIndiceDeRefraction()));
                        listLasers.get(3).setInclinaison(60 - (90 - angleRefracte2Rayon3));

                        // Impact du rayon (3) avec les bordures
                        if(Collisionneur.coordonneeImpactBordure(listLasers.get(3), false) != null)
                            listLasers.get(3).setCoordonneesFin(Objects.requireNonNull(Collisionneur.coordonneeImpactBordure(listLasers.get(3), false)));
                    }
                }
            } else {

                // Impact du rayon (0) avec les bordures
                if(Collisionneur.coordonneeImpactBordure(listLasers.get(0), false) != null)
                    listLasers.get(0).setCoordonneesFin(Objects.requireNonNull(Collisionneur.coordonneeImpactBordure(listLasers.get(0), false)));

                // Réinitialisation des rayons
                for (int i = 1; i < 4; i++) {
                    listLasers.get(i).reset(listLasers.get(0));
                }
            }
        }
    }

    // Méthode ''simulationMiniJeuCube''

    public static void simulationMiniJeuxCube(ArrayList<Laser> listLasers, ArrayList<Etoile> listEtoiles, double angleRayon, Rectangle cube, double indiceRefraction) {

        // Permet de savoir quelle étoile est activée

        int etoileEnable = 0;
        for (int i = 0; i < listEtoiles.size(); i++) {
            if(listEtoiles.get(i).isEnable())
                etoileEnable = i;
        }

        // Déclaration du premier rayon

        listLasers.get(0).setCoordonneesDepart(new Coordonnee(162.5, 350.5));
        listLasers.get(0).setCoordonneesFin(new Coordonnee(162.5, 350.5));

        // Calcul de la pente du premier rayon

        double aRayon1 = 0;
        if (angleRayon != 0)
            aRayon1 = Math.tan(Math.toRadians(angleRayon));

        // Diffusion du rayon 1 pour trouver une collision

        boolean collisionRayon1 = false;

        do {
            listLasers.get(0).setCoordonneesFin(new Coordonnee(listLasers.get(0).getEndX() + 1, listLasers.get(0).getEndY() + aRayon1));

            if (listLasers.get(0).getEndY() <= 40)
                collisionRayon1 = true; // Collision avec la bordure du haut
            else if (listLasers.get(0).getEndY() >= 640)
                collisionRayon1 = true; // Collision avec la bordure du bas
            else if (listLasers.get(0).intersects(cube.getLayoutBounds()))
                collisionRayon1 = true; // Collision avec le cube

        } while (!collisionRayon1);

        // Diffusion du rayon s'il n'y a aucune collision

        if (listLasers.get(0).getEndY() < cube.getY() || listLasers.get(0).getEndY() > cube.getY() + 200) {

            boolean collsionBordures = false;

            do {
                listLasers.get(0).setCoordonneesFin(new Coordonnee(listLasers.get(0).getEndX() + 1, listLasers.get(0).getEndY() + aRayon1));

                if (listLasers.get(0).getEndY() <= 40)
                    collsionBordures = true; // Collision avec la bordure du haut
                else if (listLasers.get(0).getEndY() >= 640)
                    collsionBordures = true; // Collision avec la bordure du bas

                } while (!collsionBordures);

            // Réinitialisation des rayons
            for (int i = 1; i < 4; i++)
                listLasers.get(i).reset(listLasers.get(0));

        } else {

            // Calcul de l'angle du deuxième rayon
            double angleRayon2 = Math.toDegrees(Math.sinh((Math.sin(Math.toRadians(angleRayon)) / indiceRefraction)));

            // Déclaration du deuxième rayon

            listLasers.get(1).setCoordonneesDepart(listLasers.get(0).getCoordonneesFin());
            listLasers.get(1).setCoordonneesFin(listLasers.get(0).getCoordonneesFin());

            // Calcul de la pente du deuxième rayon

            double aRayon2 = 0;
            if (angleRayon2 != 0)
                aRayon2 = Math.tan(Math.toRadians(angleRayon2));

            // Diffusion du rayon pour trouver une collision

            do {
                listLasers.get(1).setCoordonneesFin(new Coordonnee(listLasers.get(1).getEndX() + 1,listLasers.get(1).getEndY() + aRayon2));
            } while (listLasers.get(1).getEndX() < 750);

            // Calcul de l'ordonnée à l'origine du deuxième rayon
            double b = listLasers.get(1).getStartY() - (listLasers.get(1).getStartX() * aRayon2);

            if (listLasers.get(1).getEndY() < 250) // Le deuxième rayon dépasse vers le haut
                listLasers.get(1).setCoordonneesFin(new Coordonnee(((250 - b) / aRayon2), 250)); // Trouver le x quand y est éguale à 250
            else if (listLasers.get(1).getEndY() > 450) // Le deuxième rayon dépasse vers le bas
                listLasers.get(1).setCoordonneesFin(new Coordonnee(((450 - b) / aRayon2), 450)); // Trouver le x quand y est éguale à 450

            // RTI seulement sur les côtés

            if (listLasers.get(1).getEndY() == 450 || listLasers.get(1).getEndY() == 250) {

                // Vérificarion s'il y a une RTI

                double angleCritique = Math.toDegrees(Math.sinh(1 / indiceRefraction));
                double angleIncident2 = 90 - angleRayon2;

                // Note : L'angleIncident2 n'est jamais éguale à l'angle critique alors il n'est jamais réfracté à 90°

                if (angleIncident2 > angleCritique) {

                    // Déclaration du rayon reflété

                    listLasers.get(3).setCoordonneesDepart(listLasers.get(1).getCoordonneesFin());
                    listLasers.get(3).setCoordonneesFin(listLasers.get(1).getCoordonneesFin());

                    // Diffusion du rayon reflété pour trouver une collision

                    do {
                        listLasers.get(3).setCoordonneesFin(new Coordonnee(listLasers.get(3).getEndX() + 1, listLasers.get(3).getEndY() + (-1 * aRayon2)));
                    } while (listLasers.get(3).getEndX() < 750);

                    // Déclaration du troisième rayon

                    listLasers.get(2).setCoordonneesDepart(listLasers.get(3).getCoordonneesFin());
                    listLasers.get(2).setCoordonneesFin(listLasers.get(3).getCoordonneesFin());

                    // Calcul de l'angle du troisième rayon

                    double angleIncident3 = 90 - angleIncident2;
                    double angleRayon3 = Math.toDegrees(Math.sinh((Math.sin(Math.toRadians(angleIncident3)) * indiceRefraction)));

                    // Calcul de la pente du deuxième rayon
                    double aRayon3 = 0;
                    if (angleRayon3 != 0)
                        aRayon3 = Math.tan(Math.toRadians(angleRayon3));

                    // Diffusion du rayon

                    int y = 0;
                    do {
                        listLasers.get(2).setCoordonneesFin(new Coordonnee(listLasers.get(2).getEndX() + 1, listLasers.get(2).getEndY() + (-1 * aRayon3)));
                        y++;
                    } while (y != 550); // Valeur fictive pour le faire dépasser de l'écranà

                    // Impact avec une étoile

                    if(Collisionneur.coordonneeImpactEtoile(listEtoiles.get(etoileEnable),listLasers.get(2)) != null) {
                        if (listEtoiles.get(etoileEnable).getType()) { // Étoile orange seulement
                            listEtoiles.get(etoileEnable).disable();
                            MiniJeux.pointEtoile++;
                            SonCible.jouer();
                            if ((etoileEnable + 1) < listEtoiles.size()) {
                                listEtoiles.get(etoileEnable + 1).enable();
                            } else {
                                MiniJeux.chrono.stop();
                                Alert alert = MiniJeux.alerteVictoire( MiniJeux.chrono.getElapsedSeconds());
                                alert.showAndWait();
                                MiniJeux miniJeux = new MiniJeux();
                                Main.setScene(miniJeux.choixDifficulte());
                            }
                        }
                        else
                            listLasers.get(2).setCoordonneesFin(Collisionneur.coordonneeImpactEtoile(listEtoiles.get(etoileEnable),listLasers.get(2)));
                    }
                }
            } else {

                // Il n'y a aucune RTI
                // Déclaration du troisième rayon

                listLasers.get(2).setCoordonneesDepart(listLasers.get(1).getCoordonneesFin());
                listLasers.get(2).setCoordonneesFin(listLasers.get(1).getCoordonneesFin());

                // Diffusion du rayon

                int y = 0;

                do {
                    listLasers.get(2).setCoordonneesFin(new Coordonnee(listLasers.get(2).getEndX() + 1, listLasers.get(2).getEndY() + aRayon1));
                    y++;
                } while (y != 550); // Valeur fictive pour le faire dépasser de l'écran

                // Rayon qui ne sont pas nécessaire

                listLasers.get(3).reset(listLasers.get(0));

                if(Collisionneur.coordonneeImpactEtoile(listEtoiles.get(etoileEnable),listLasers.get(2)) != null) {
                    if (!listEtoiles.get(etoileEnable).getType()) { // Étoile verte seulement
                        listEtoiles.get(etoileEnable).disable();
                        MiniJeux.pointEtoile++;
                        SonCible.jouer();
                        if ((etoileEnable + 1) < listEtoiles.size()) {
                            listEtoiles.get(etoileEnable + 1).enable();
                        } else {
                            MiniJeux.chrono.stop();
                            Alert alert = MiniJeux.alerteVictoire( MiniJeux.chrono.getElapsedSeconds());
                            alert.showAndWait();
                            MiniJeux miniJeux = new MiniJeux();
                            Main.setScene(miniJeux.choixDifficulte());
                        }
                    }
                    else
                        listLasers.get(2).setCoordonneesFin(Collisionneur.coordonneeImpactEtoile(listEtoiles.get(etoileEnable),listLasers.get(2)));
                }
            }
        }
    }


    // Méthode ''simulationMiniJeuTriangle''

    public static void simulationMiniJeuTriangle(ArrayList<Laser> listLasers, Prisme prisme, ArrayList<Etoile> listEtoiles) {

        // Caractéristique de départ du rayon (0)
        listLasers.get(0).setCoordonneesDepart(new Coordonnee(162.5, 350.5));

        // Permet de savoir quelle étoile est activé

        int etoileEnable = 0;
        for (int i = 0; i < listEtoiles.size(); i++) {
            if (listEtoiles.get(i).isEnable())
                etoileEnable = i;
        }

        if (prisme.getIndiceDeRefraction() == 1) {

            // Impact du rayon (0) avec les bordures
            if (Collisionneur.coordonneeImpactBordure(listLasers.get(0), false) != null) {
                listLasers.get(0).setCoordonneesFin(Collisionneur.coordonneeImpactBordure(listLasers.get(0), false));

                // Impact avec une étoile

                if (Collisionneur.coordonneeImpactEtoile(listEtoiles.get(etoileEnable), listLasers.get(0)) != null) {
                    listLasers.get(0).setCoordonneesFin(Collisionneur.coordonneeImpactEtoile(listEtoiles.get(etoileEnable), listLasers.get(0)));
                }
            }

            // Réinitialisation des rayons
            for (int i = 1; i < 4; i++) {
                listLasers.get(i).reset(listLasers.get(0));
            }

        } else {

            if (Collisionneur.coordonneeImpactPrisme(prisme, listLasers.get(0), 1) != null) {

                // Impact du rayon (0) avec le côté gauche du triangle
                if (Collisionneur.coordonneeImpactPrisme(prisme, listLasers.get(0), 1) != null)
                    listLasers.get(0).setCoordonneesFin(Collisionneur.coordonneeImpactPrisme(prisme, listLasers.get(0), 1));

                // Calculs de l'angle du rayon (1)

                double angleIncidentRayon1 = 30 - listLasers.get(0).getInclinaison();
                double angleRefracteRayon1 = Math.toDegrees(Math.sinh(Math.sin(Math.toRadians(angleIncidentRayon1)) / prisme.getIndiceDeRefraction()));
                listLasers.get(1).setInclinaison((30 - angleRefracteRayon1));

                // Caractéristique de départ du rayon (1)
                listLasers.get(1).setCoordonneesDepart(listLasers.get(0).getCoordonneesFin());

                if (Collisionneur.coordonneeImpactPrisme(prisme, listLasers.get(1), 2) != null) {

                    // Impact du rayon (1) avec le côté droit du triangle
                    if (Collisionneur.coordonneeImpactPrisme(prisme, listLasers.get(1), 2) != null)
                        listLasers.get(1).setCoordonneesFin(Collisionneur.coordonneeImpactPrisme(prisme, listLasers.get(1), 2));

                    // Caractéristique de départ du rayon (2)
                    listLasers.get(2).setCoordonneesDepart(listLasers.get(1).getCoordonneesFin());

                    if (RTI(listLasers.get(1), prisme)) {

                        // RTI sur le côté bas (3) du prisme
                        if (Collisionneur.surfaceImpactPrisme(prisme, listLasers.get(1), 2) == 3) {

                            // Calcul de l'angle du rayon (2)
                            listLasers.get(2).setInclinaison(listLasers.get(1).getInclinaison() * -1); // (Il s'agit d'une réflexion, ainsi * -1)

                            // Impact du rayon (2) avec le triangle
                            if (Collisionneur.coordonneeImpactPrisme(prisme, listLasers.get(2), 2) != null)
                                listLasers.get(2).setCoordonneesFin(Collisionneur.coordonneeImpactPrisme(prisme, listLasers.get(2), 2));

                            // Caractéristique de départ du rayon (3)
                            listLasers.get(3).setCoordonneesDepart(listLasers.get(2).getCoordonneesFin());

                            // Calcul de l'angle du rayon (3)

                            double angleIncidentRayon3 = (90 - (180 - (120 + listLasers.get(2).getInclinaison())));
                            double angleRefractionRayon3 = Math.toDegrees(Math.sinh(Math.sin(Math.toRadians(angleIncidentRayon3)) * prisme.getIndiceDeRefraction()));
                            listLasers.get(3).setInclinaison(60 - (90 - angleRefractionRayon3));

                            // Impact du rayon (3) avec les bordures
                            if (Collisionneur.coordonneeImpactBordure(listLasers.get(3), false) != null) {
                                listLasers.get(3).setCoordonneesFin(Collisionneur.coordonneeImpactBordure(listLasers.get(3), false));

                                // Impact avec une étoile

                                if (Collisionneur.coordonneeImpactEtoile(listEtoiles.get(etoileEnable), listLasers.get(3)) != null) {
                                    if (listEtoiles.get(etoileEnable).getType()) { // Étoile orange seulement
                                        listEtoiles.get(etoileEnable).disable();
                                        MiniJeux.pointEtoile++;
                                        SonCible.jouer();
                                        if ((etoileEnable + 1) < listEtoiles.size()) {
                                            listEtoiles.get(etoileEnable + 1).enable();
                                        } else {
                                            MiniJeux.chrono.stop();
                                            Alert alert = MiniJeux.alerteVictoire(MiniJeux.chrono.getElapsedSeconds());
                                            alert.showAndWait();
                                            MiniJeux miniJeux = new MiniJeux();
                                            Main.setScene(miniJeux.choixDifficulte());
                                        }
                                    } else
                                        listLasers.get(3).setCoordonneesFin(Collisionneur.coordonneeImpactEtoile(listEtoiles.get(etoileEnable), listLasers.get(3)));
                                }
                            }
                        }

                        // RTI sur le côté droit (2) du prisme
                        if (Collisionneur.surfaceImpactPrisme(prisme, listLasers.get(1), 2) == 2) {

                            // Calcul de l'angle du rayon (2)
                            listLasers.get(2).setInclinaison((listLasers.get(1).getInclinaison() + 90)); // (Il s'agit d'une réflexion, ainsi + 90)

                            // Impact du rayon (2) avec le triangle
                            if (Collisionneur.coordonneeImpactPrisme(prisme, listLasers.get(2), 4) != null)
                                listLasers.get(2).setCoordonneesFin(Collisionneur.coordonneeImpactPrisme(prisme, listLasers.get(2), 4));

                            // Caractéristiques de départ du rayon (3)
                            listLasers.get(3).setCoordonneesDepart(listLasers.get(2).getCoordonneesFin());

                            // Calcul de l'angle du rayon (3)

                            double angleIncident1Rayon3 = 90 - (75 - listLasers.get(2).getInclinaison());
                            double angleRefracte1Rayon3 = Math.toDegrees(Math.sinh(Math.sin(Math.toRadians(angleIncident1Rayon3)) * prisme.getIndiceDeRefraction()));
                            listLasers.get(3).setInclinaison(angleRefracte1Rayon3);

                            // Impact du rayon (3) avec les bordures
                            if (Collisionneur.coordonneeImpactBordure(listLasers.get(3), false) != null) {
                                listLasers.get(3).setCoordonneesFin(Collisionneur.coordonneeImpactBordure(listLasers.get(3), true));

                                // Impact avec une étoile

                                if (Collisionneur.coordonneeImpactEtoile(listEtoiles.get(etoileEnable), listLasers.get(3)) != null) {
                                    if (listEtoiles.get(etoileEnable).getType()) { // Étoile orange seulement
                                        listEtoiles.get(etoileEnable).disable();
                                        MiniJeux.pointEtoile++;
                                        SonCible.jouer();
                                        if ((etoileEnable + 1) < listEtoiles.size()) {
                                            listEtoiles.get(etoileEnable + 1).enable();
                                        } else {
                                            MiniJeux.chrono.stop();
                                            Alert alert = MiniJeux.alerteVictoire(MiniJeux.chrono.getElapsedSeconds());
                                            alert.showAndWait();
                                            MiniJeux miniJeux = new MiniJeux();
                                            Main.setScene(miniJeux.choixDifficulte());
                                        }
                                    } else
                                        listLasers.get(3).setCoordonneesFin(Collisionneur.coordonneeImpactEtoile(listEtoiles.get(etoileEnable), listLasers.get(3)));
                                }
                            }
                        }

                    } else { // Il y a aucune RTI

                        // Impact du laser (2) avec le triangle
                        if (Collisionneur.coordonneeImpactPrisme(prisme, listLasers.get(2), 2) != null)
                            listLasers.get(2).setCoordonneesFin(Collisionneur.coordonneeImpactPrisme(prisme, listLasers.get(2), 2));

                        // Caractéristique de départ du rayon (3)
                        listLasers.get(3).setCoordonneesDepart(listLasers.get(2).getCoordonneesFin());

                        // Calcul de l'angle du rayon (3)

                        double angleIncident2Rayon3 = (90 - (180 - (120 + listLasers.get(1).getInclinaison())));
                        double angleRefracte2Rayon3 = Math.toDegrees(Math.sinh(Math.sin(Math.toRadians(angleIncident2Rayon3)) * prisme.getIndiceDeRefraction()));
                        listLasers.get(3).setInclinaison(60 - (90 - angleRefracte2Rayon3));

                        // Impact du rayon (3) avec les bordures
                        if (Collisionneur.coordonneeImpactBordure(listLasers.get(3), false) != null) {
                            listLasers.get(3).setCoordonneesFin(Collisionneur.coordonneeImpactBordure(listLasers.get(3), false));

                            // Impact avec une étoile

                            if (Collisionneur.coordonneeImpactEtoile(listEtoiles.get(etoileEnable), listLasers.get(3)) != null) {
                                if (!listEtoiles.get(etoileEnable).getType()) { // Étoile verte seulement
                                    listEtoiles.get(etoileEnable).disable();
                                    MiniJeux.pointEtoile++;
                                    SonCible.jouer();
                                    if ((etoileEnable + 1) < listEtoiles.size()) {
                                        listEtoiles.get(etoileEnable + 1).enable();
                                    } else {
                                        MiniJeux.chrono.stop();
                                        Alert alert = MiniJeux.alerteVictoire(MiniJeux.chrono.getElapsedSeconds());
                                        alert.showAndWait();
                                        MiniJeux miniJeux = new MiniJeux();
                                        Main.setScene(miniJeux.choixDifficulte());
                                    }
                                } else
                                    listLasers.get(3).setCoordonneesFin(Collisionneur.coordonneeImpactEtoile(listEtoiles.get(etoileEnable), listLasers.get(3)));
                            }
                        }
                    }
                }
            } else {

                // Impact du rayon (0) avec les bordures

                if (Collisionneur.coordonneeImpactBordure(listLasers.get(0), false) != null) {
                    listLasers.get(0).setCoordonneesFin(Collisionneur.coordonneeImpactBordure(listLasers.get(0), false));
                    if (Collisionneur.coordonneeImpactEtoile(listEtoiles.get(etoileEnable), listLasers.get(0)) != null) { // n'a aucun effet sur les étoiles, il fait juste une collision
                        listLasers.get(0).setCoordonneesFin(Collisionneur.coordonneeImpactEtoile(listEtoiles.get(etoileEnable), listLasers.get(0)));
                    }
                }

                // Réinitialisation des rayons
                for (int i = 1; i < 4; i++) {
                    listLasers.get(i).reset(listLasers.get(0));
                }
            }
        }
    }

    // Méthode ''RTI''

    private static Boolean RTI(Laser laser, Prisme prisme) {

        // Calcul des angles

        double angleCritique = Math.toDegrees(Math.sinh(1 / prisme.getIndiceDeRefraction()));
        double angleIncidentBas = 90 - laser.getInclinaison();
        double angleIncidentDroit = 90 - (180 - (120 + laser.getInclinaison()));

        // RTI avec le bas (3) du prisme

        if (Collisionneur.surfaceImpactPrisme(prisme, laser, 2) == 3) {
            if (angleIncidentBas > angleCritique) {
                return true;
            }
        }

        // RTI avec le côté droit (2) du prisme

        if (Collisionneur.surfaceImpactPrisme(prisme, laser, 2) == 2) {
            if (angleIncidentDroit > angleCritique) {
                return true;
            }
        }
        return false;
    }
}