// Importation package
package miniJeux;

// Importations

import chronometre.Chrono;
import coordonnee.Coordonnee;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import main.Main;
import items.Etoile;
import items.Laser;
import items.Pointeur;
import items.Prisme;
import simulateur.Simulateur;
import son.SonMenu;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

public class MiniJeux {

    // Décaration des listes de rayons.

    private ArrayList<Laser> listLaserCube;
    private ArrayList<Laser> listLaserTriangle;

    // Déclaration de l'indice de réfraction et du pointage de nombres d'étoiles touchées

    private double indiceRefraction;
    public static int pointEtoile;

    // Déclaration du ''Chronomètre''
    public static Chrono chrono;

    // Méthode ''choixDifficulte''

    public Scene choixDifficulte() {

        // Déclaration du fond du menu

        Rectangle fond = new Rectangle(0,0,1400,800);
        Stop[] stops = new Stop[]{new Stop(0, Color.BLUE), new Stop(0.5, Color.LIGHTBLUE), new Stop(1, Color.PURPLE),};
        LinearGradient gradient = new LinearGradient(0,0, 1,1, true, CycleMethod.NO_CYCLE, stops);
        fond.setFill(gradient);

        // Déclaration du label du titre

        Label labelDifficulte = new Label("Choix de la difficulté");
        labelDifficulte.setFont(new Font("Bauhaus 93", 50));
        labelDifficulte.setTextFill(Color.RED);

        // Déclaration du bouton ''Facile''

        Button boutonFacile = new Button("Facile");
        boutonFacile.setFont(new Font("Bauhaus 93", 40));
        boutonFacile.setTextFill(Color.RED);
        boutonFacile.setStyle("-fx-background-color: #f5f5f5;");
        boutonFacile.setOnAction(event -> Main.setScene(jouerFacile()));

        // Déclaration du bouton ''Normal''

        Button boutonNormal = new Button("Normal");
        boutonNormal.setFont(new Font("Bauhaus 93", 40));
        boutonNormal.setTextFill(Color.RED);
        boutonNormal.setStyle("-fx-background-color: #f5f5f5;");
        boutonNormal.setOnAction(event -> Main.setScene(jouerNormal()));

        // Déclaration du bouton ''Difficile''

        Button boutonDifficile = new Button("Difficile");
        boutonDifficile.setFont(new Font("Bauhaus 93", 40));
        boutonDifficile.setTextFill(Color.RED);
        boutonDifficile.setStyle("-fx-background-color: #f5f5f5;");
        boutonDifficile.setOnAction(event -> Main.setScene(jouerDifficile()));

        // Déclaration du bouton retour au menu

        Button boutonRetour = new Button("➡️");
        boutonRetour.setFont(new Font("Bauhaus 93", 40));
        boutonRetour.setTextFill(Color.RED);
        boutonRetour.setStyle("-fx-background-color: #f5f5f5;");
        boutonRetour.setOnAction(event -> {
            SonMenu.getClip().close();
            Main.setScene(Main.menu());
        });

        // Allignement des boutons

        VBox vBoxDifficulte = new VBox(labelDifficulte,boutonFacile, boutonNormal, boutonDifficile, boutonRetour);
        vBoxDifficulte.setAlignment(Pos.CENTER_LEFT);
        vBoxDifficulte.setSpacing(50);
        vBoxDifficulte.setTranslateX(125);

        // Instructions

        // Déclaration du titre ''Instruction''

        Label labelInstruction = new Label("Instruction :" + "\n" + " ");
        labelInstruction.setFont(new Font("Bauhaus 93", 25));
        labelInstruction.setTextFill(Color.RED);

        // Déclaration des exemples ''Étoiles''

        Label labelVert = new Label("Les étoiles vertes doivent être touchées par des rayons réfractés standards");
        labelVert.setFont(new Font("Bauhaus 93", 20));
        labelVert.setTextFill(Color.RED);
        Etoile etoileVert = new Etoile(new Coordonnee(10,5), false);
        HBox hBoxVert = new HBox(labelVert, etoileVert);
        Label labelOrange = new Label("Les étoiles oranges doivent être touchées par des rayons réfractés en RTI" + "\n" + " ");
        labelOrange.setFont(new Font("Bauhaus 93", 20));
        labelOrange.setTextFill(Color.RED);
        Etoile etoileOrange = new Etoile(new Coordonnee(10,5), true);
        HBox hBoxOrange = new HBox(labelOrange, etoileOrange);

        // Définition d'une réflexion totale interne
        // Source : https://fr.wikipedia.org/wiki/R%C3%A9flexion_totale

        Label labelDefinitionRTI = new Label("Une Réflexion Totale Interne (RTI) est un phénomène qui survient lorsqu'un rayon"
                + "\n" + "lumineux arrive sur la surface de séparation de deux milieux d'indices de réfraction"
                + "\n" + "différents. Si son angle d'incidence est supérieur à l'angle critique, le rayon est"
                + "\n" + "réflèté à l'intérieur de son premier milieu."
                + "\n" + " "
                + "\n" + "** Vous pouvez aller expérimenter ce phénomène dans le mode Bac à sable! **");
        labelDefinitionRTI.setFont(new Font("Bauhaus 93", 20));
        labelDefinitionRTI.setTextFill(Color.RED);

        // Superposition des instructions

        VBox vBoxInstruction = new VBox(labelInstruction, hBoxVert, hBoxOrange, labelDefinitionRTI);
        vBoxInstruction.setTranslateX(25);
        vBoxInstruction.setTranslateY(250);

        // Allignement et superposition des éléments graphiques

        HBox hBox = new HBox(vBoxDifficulte, vBoxInstruction);
        hBox.setSpacing(50);
        StackPane stackPane = new StackPane(fond, hBox);

        // Affichage du menu du choix de difficulté

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(stackPane);
        return new Scene(borderPane);
    }

    // Méthode ''jouerFacile''

    public Scene jouerFacile() {

        // Initialisation des rayons, de l'indice de réfraction et des points des étoiles

        listLaserCube = new ArrayList<>();
        indiceRefraction = 1.5;
        pointEtoile = 0;

        // Déclaration des ''Rayons laser''

        listLaserCube.add(new Laser());
        listLaserCube.add(new Laser());
        listLaserCube.add(new Laser());
        listLaserCube.add(new Laser());

        // Déclaration du ''Pointeur laser''
        Pointeur pointeur = new Pointeur(100,325);

        // Déclaration du ''Chronomètre''

        chrono = new Chrono();
        chrono.start();

        // Déclaration du ''Cube réfractant''

        Rectangle cube = new Rectangle(550,250,200,200);
        cube.setFill(Color.rgb(66, 189, 237));
        cube.setStroke(Color.BLACK);
        cube.setOpacity(0.3);

        // Déclaration des ''Bordures''

        ArrayList<Line> bordures = new ArrayList<>();
        bordures.add(new Line(0,0,1360,0));
        bordures.add(new Line(1360,0,1360,650));
        bordures.add(new Line(0,650,1360,650));
        bordures.add(new Line(0,0,0,650));
        Group groupBordures = new Group();
        for (Line b : bordures) {
            b.setStroke(Color.TRANSPARENT);
            groupBordures.getChildren().add(b);
        }

        // Déclaration des ''Étoiles''

        ArrayList<Etoile> listEtoile = new ArrayList<>();

        // Étoiles avec réfraction (verte)

        listEtoile.add(new Etoile(new Coordonnee(800,275), false));
        listEtoile.add(new Etoile(new Coordonnee(1000,200), false));
        listEtoile.add(new Etoile(new Coordonnee(1200,400), false));
        listEtoile.add(new Etoile(new Coordonnee(775,400),false));
        listEtoile.add(new Etoile(new Coordonnee(1250,500), false));

        // Étoiles avec RTI (orange)

        listEtoile.add(new Etoile(new Coordonnee(1000,300), true));
        listEtoile.add(new Etoile(new Coordonnee(900,350), true));
        listEtoile.add(new Etoile(new Coordonnee(900,300), true));
        listEtoile.add(new Etoile(new Coordonnee(950,300), true));
        listEtoile.add(new Etoile(new Coordonnee(1200, 300), true));
        Collections.shuffle(listEtoile);

        // Affichage des étoiles

        Group groupeEtoile = new Group();
        groupeEtoile.getChildren().addAll(listEtoile);
        listEtoile.get(0).enable();
        for (int i = 1; i < listEtoile.size(); i++)
            listEtoile.get(i).disable();

        // Menu de contrôle

        // Déclaration du titre du menu de contrôle
        Label texteControle = new Label("Contrôle :");

        // Déclaration du DecimalFormat
        DecimalFormat df = new DecimalFormat("0.00");

        // Points des ''Étoiles'' touchées
        Label textePoints = new Label(" Points : " + pointEtoile + "/10");

        // Rotation

        Label texteRotation = new Label("  Rotation : " + df.format(pointeur.getRotation()) + "°");
        Slider rotationSlider = new Slider(-50,50,0);
        rotationSlider.setShowTickLabels(true);
        rotationSlider.setShowTickMarks(true);
        rotationSlider.setMajorTickUnit(20);
        rotationSlider.setMinorTickCount(1);
        rotationSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            pointeur.setRotation( (double) newValue);
            texteRotation.setText("  Rotation : " + df.format(pointeur.getRotation()) + "°");
            Simulateur.simulationMiniJeuxCube(listLaserCube,listEtoile, pointeur.getRotation(), cube, indiceRefraction);
            textePoints.setText(" Points : " + pointEtoile + "/10");
        });

        // Indice de réfraction
        Label texteIndiceRefraction = new Label("  Indice de réfraction : " + indiceRefraction);

        // Déclaration du ''Tooltip'' pour afficher la définition de l'indice de réfraction

        Tooltip messageIndiceRefraction = new Tooltip("L'indice de réfraction est une valeur décrivant le comportement de la lumière dans un milieu.");
        texteIndiceRefraction.setTooltip(messageIndiceRefraction);

        // Allignement des Sliders et des Labels

        VBox vBoxControle = new VBox(texteRotation,rotationSlider, texteIndiceRefraction, textePoints);
        vBoxControle.setSpacing(20);

        // Fond du menu

        Rectangle fondMenu = new Rectangle(300,250);
        fondMenu.setFill(Color.LIGHTGRAY);
        fondMenu.setStroke(Color.BLACK);

        // Alignement du menu

        VBox vBoxMenu = new VBox(texteControle, vBoxControle);
        vBoxMenu.setAlignment(Pos.CENTER);
        vBoxMenu.setSpacing(20);
        StackPane stackPaneMenu = new StackPane(fondMenu, vBoxMenu);
        stackPaneMenu.setAlignment(Pos.CENTER);
        stackPaneMenu.setLayoutX(1050);
        stackPaneMenu.setLayoutY(30);

        // Lancement de la simulation
        Simulateur.simulationMiniJeuxCube(listLaserCube,listEtoile, pointeur.getRotation(), cube, indiceRefraction);

        // Création du group des éléments du Mini Jeux
        Group groupMiniJeux = new Group(cube,groupBordures, listLaserCube.get(0), listLaserCube.get(1),
                listLaserCube.get(2), listLaserCube.get(3), groupeEtoile, pointeur.getPointeur(), stackPaneMenu);

        // Affichage du mode facile du ''Mini Jeux''

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(menuBar("facile"));
        borderPane.setCenter(groupMiniJeux);
        return new Scene(borderPane);
    }

    public Scene jouerNormal() {

        // Initialisation des rayons, de l'indice de réfraction et des points des étoiles

        listLaserTriangle = new ArrayList<>();
        indiceRefraction = 1.5;
        pointEtoile = 0;

        // Déclaration des ''Rayons laser''

        listLaserTriangle.add(new Laser());
        listLaserTriangle.add(new Laser());
        listLaserTriangle.add(new Laser());
        listLaserTriangle.add(new Laser());
        listLaserTriangle.get(0).setInclinaison(0);

        // Déclaration du ''Pointeur laser''
        Pointeur pointeur = new Pointeur(100,325);

        // Déclaration du ''Chronomètre''

        chrono = new Chrono();
        chrono.start();

        // Déclaration du ''Prisme''
        Prisme prisme = new Prisme(new Coordonnee(650,250), indiceRefraction);

        // Déclaration des ''Bordures''

        ArrayList<Line> bordures = new ArrayList<>();
        bordures.add(new Line(0,0,1370,0));
        bordures.add(new Line(1370,0,1370,650));
        bordures.add(new Line(0,660,1370,660));
        bordures.add(new Line(0,0,0,660));
        Group groupBordures = new Group();
        for (Line b : bordures) {
            b.setStroke(Color.TRANSPARENT);
            groupBordures.getChildren().add(b);
        }

        // Déclaration des ''Étoiles''

        ArrayList<Etoile> listEtoile = new ArrayList<>();

        // Étoiles avec réfraction (verte)

        listEtoile.add(new Etoile(new Coordonnee(900,300), false));
        listEtoile.add(new Etoile(new Coordonnee(950,530), false));
        listEtoile.add(new Etoile(new Coordonnee(1000,400), false));
        listEtoile.add(new Etoile(new Coordonnee(1200,350), false));
        listEtoile.add(new Etoile(new Coordonnee(950,300), false));

        // Étoiles avec RTI (orange)

        listEtoile.add(new Etoile(new Coordonnee(200,600),true));
        listEtoile.add(new Etoile(new Coordonnee(850,350), true));
        listEtoile.add(new Etoile(new Coordonnee(1100,350),true));
        listEtoile.add(new Etoile(new Coordonnee(650,500), true));
        listEtoile.add(new Etoile(new Coordonnee(450,550), true));
        Collections.shuffle(listEtoile);

        // Affichage des étoiles

        Group groupeEtoile = new Group();
        groupeEtoile.getChildren().addAll(listEtoile);
        listEtoile.get(0).enable();
        for (int i = 1; i < listEtoile.size(); i++)
            listEtoile.get(i).disable();

        // Menu de contrôle

        // Déclaration du titre du menu de contrôle
        Label texteControle = new Label("Contrôle :");

        // Déclaration du DecimalFormat
        DecimalFormat df = new DecimalFormat("0.00");

        // Points des ''Étoiles'' touchées
        Label textePoints = new Label(" Points : " + pointEtoile + "/10");

        // Rotation

        Label texteRotation = new Label("  Rotation : " + df.format(pointeur.getRotation()) + "°");
        Slider rotationSlider = new Slider(-50,50,0);
        rotationSlider.setShowTickLabels(true);
        rotationSlider.setShowTickMarks(true);
        rotationSlider.setMajorTickUnit(20);
        rotationSlider.setMinorTickCount(1);
        rotationSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            pointeur.setRotation((double) newValue);
            listLaserTriangle.get(0).setInclinaison(pointeur.getRotation());
            texteRotation.setText("  Rotation : " + df.format(pointeur.getRotation()) + "°");
            Simulateur.simulationMiniJeuTriangle(listLaserTriangle, prisme, listEtoile);
            textePoints.setText(" Points : " + pointEtoile + "/10");
        });
        VBox rotationVBox = new VBox(texteRotation,rotationSlider);
        rotationVBox.setSpacing(5);

        // Indice de réfraction

        Label texteIndiceRefraction = new Label("  Indice de réfraction : " + df.format(indiceRefraction));
        Slider indiceRefractionSlider = new Slider(1.2, 1.9,1.6);
        indiceRefractionSlider.setShowTickLabels(true);
        indiceRefractionSlider.setShowTickMarks(true);
        indiceRefractionSlider.setMajorTickUnit(1);
        indiceRefractionSlider.setMinorTickCount(1);
        indiceRefractionSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            this.indiceRefraction = (double) newValue;
            prisme.setIndiceDeRefraction(this.indiceRefraction);
            texteIndiceRefraction.setText("  Indice de réfraction : " + df.format(this.indiceRefraction));
            Simulateur.simulationMiniJeuTriangle(listLaserTriangle, prisme, listEtoile);
            textePoints.setText(" Points : " + pointEtoile + "/10");

            // Changement de couleur du cube en fonction de l'indice de réfraction

            if(1.2 <= prisme.getIndiceDeRefraction() && prisme.getIndiceDeRefraction() < 1.5)
                prisme.setColor(Color.rgb(112, 255, 245));
            if(1.5 == prisme.getIndiceDeRefraction() && prisme.getIndiceDeRefraction() < 1.7)
                prisme.setColor(Color.rgb(66, 189, 237));
            if(1.7 <= prisme.getIndiceDeRefraction() && prisme.getIndiceDeRefraction() < 1.8)
                prisme.setColor(Color.rgb(35, 117, 194));
            if(1.8 <= prisme.getIndiceDeRefraction())
                prisme.setColor(Color.rgb(87, 87, 235));
        });

        // Déclaration du ''Tooltip'' pour afficher la définition de l'indice de réfraction

        Tooltip messageIndiceRefraction = new Tooltip("L'indice de réfraction est une valeur décrivant le comportement de la lumière dans un milieu.");
        texteIndiceRefraction.setTooltip(messageIndiceRefraction);
        indiceRefractionSlider.setTooltip(messageIndiceRefraction);

        // Allignement des Sliders et des Labels

        VBox vBoxControle = new VBox(texteRotation,rotationSlider, texteIndiceRefraction, indiceRefractionSlider, textePoints);
        vBoxControle.setSpacing(20);

        // Fond du menu

        Rectangle fondMenu = new Rectangle(300,275);
        fondMenu.setFill(Color.LIGHTGRAY);
        fondMenu.setStroke(Color.BLACK);

        // Alignement du menu

        VBox vBoxMenu = new VBox(texteControle, vBoxControle);
        vBoxMenu.setAlignment(Pos.CENTER);
        vBoxMenu.setSpacing(20);
        StackPane stackPaneMenu = new StackPane(fondMenu, vBoxMenu);
        stackPaneMenu.setAlignment(Pos.CENTER);
        stackPaneMenu.setLayoutX(1050);
        stackPaneMenu.setLayoutY(30);

        // Lancement de la simulation
        Simulateur.simulationMiniJeuTriangle(listLaserTriangle, prisme, listEtoile);

        // Création du group des éléments du Mini Jeux
        Group groupMiniJeux = new Group(prisme, groupBordures, listLaserTriangle.get(0), listLaserTriangle.get(1), listLaserTriangle.get(2),
                listLaserTriangle.get(3),groupeEtoile, pointeur.getPointeur(), stackPaneMenu);

        // Affichage du mode normal du ''Mini Jeux''

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(menuBar("normal"));
        borderPane.setCenter(groupMiniJeux);
        return new Scene(borderPane);
    }

    // Méthode ''jouerDifficile''

    public Scene jouerDifficile() {

        // Initialisation des rayons, de l'indice de réfraction et des points des étoiles

        listLaserTriangle = new ArrayList<>();
        indiceRefraction = 1.5;
        pointEtoile = 0;

        // Déclaration des ''Rayons laser''

        listLaserTriangle.add(new Laser());
        listLaserTriangle.add(new Laser());
        listLaserTriangle.add(new Laser());
        listLaserTriangle.add(new Laser());
        listLaserTriangle.get(0).setInclinaison(0);

        // Déclaration du ''Pointeur laser''
        Pointeur pointeur = new Pointeur(100,325);

        // Déclaration du ''Prisme''
        Prisme prisme = new Prisme(new Coordonnee(650,250), indiceRefraction);

        // Déclaration du ''Chronomètre''

        chrono = new Chrono();
        chrono.start();

        // Déclaration des ''Bordures''

        ArrayList<Line> bordures = new ArrayList<>();
        bordures.add(new Line(0,0,1370,0));
        bordures.add(new Line(1370,0,1370,650));
        bordures.add(new Line(0,660,1370,660));
        bordures.add(new Line(0,0,0,660));
        Group groupBordures = new Group();
        for (Line b : bordures) {
            b.setStroke(Color.TRANSPARENT);
            groupBordures.getChildren().add(b);
        }

        // Déclaration des ''Étoiles''

        ArrayList<Etoile> listEtoile = new ArrayList<>();

        // Étoiles avec réfraction (verte)

        listEtoile.add(new Etoile(new Coordonnee(900,300), false));
        listEtoile.add(new Etoile(new Coordonnee(950,530), false));
        listEtoile.add(new Etoile(new Coordonnee(1000,400), false));
        listEtoile.add(new Etoile(new Coordonnee(1200,350), false));
        listEtoile.add(new Etoile(new Coordonnee(950,300), false));
        listEtoile.add(new Etoile(new Coordonnee(900,305), false));
        listEtoile.add(new Etoile(new Coordonnee(1000,250), false));
        listEtoile.add(new Etoile(new Coordonnee(1200,400), false));
        listEtoile.add(new Etoile(new Coordonnee(775,400),false));
        listEtoile.add(new Etoile(new Coordonnee(1250,500), false));

        // Étoiles avec RTI (orange)

        listEtoile.add(new Etoile(new Coordonnee(200,600),true));
        listEtoile.add(new Etoile(new Coordonnee(850,350), true));
        listEtoile.add(new Etoile(new Coordonnee(1100,350),true));
        listEtoile.add(new Etoile(new Coordonnee(650,500), true));
        listEtoile.add(new Etoile(new Coordonnee(450,550), true));
        listEtoile.add(new Etoile(new Coordonnee(1000,300), true));
        listEtoile.add(new Etoile(new Coordonnee(900,350), true));
        listEtoile.add(new Etoile(new Coordonnee(800,350), true));
        listEtoile.add(new Etoile(new Coordonnee(400,550), true));
        listEtoile.add(new Etoile(new Coordonnee(1200, 300), true));
        Collections.shuffle(listEtoile);

        // Affichage des étoiles

        Group groupeEtoile = new Group();
        groupeEtoile.getChildren().addAll(listEtoile);
        listEtoile.get(0).enable();
        for (int i = 1; i < listEtoile.size(); i++)
            listEtoile.get(i).disable();

        // Menu de contrôle

        // Déclaration du titre du menu de contrôle
        Label texteControle = new Label("Contrôle :");

        // Déclaration du DecimalFormat
        DecimalFormat df = new DecimalFormat("0.00");

        // Points des ''Étoiles'' touchées
        Label textePoints = new Label(" Points : " + pointEtoile + "/20");

        // Rotation

        Label texteRotation = new Label("  Rotation : " + df.format(pointeur.getRotation()) + "°");
        Slider rotationSlider = new Slider(-50,50,0);
        rotationSlider.setShowTickLabels(true);
        rotationSlider.setShowTickMarks(true);
        rotationSlider.setMajorTickUnit(20);
        rotationSlider.setMinorTickCount(1);
        rotationSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            pointeur.setRotation((double) newValue);
            listLaserTriangle.get(0).setInclinaison(pointeur.getRotation());
            texteRotation.setText("  Rotation : " + df.format(pointeur.getRotation()) + "°");
            Simulateur.simulationMiniJeuTriangle(listLaserTriangle, prisme, listEtoile);
            textePoints.setText(" Points : " + pointEtoile + "/20");
        });
        VBox rotationVBox = new VBox(texteRotation,rotationSlider);
        rotationVBox.setSpacing(5);

        // Indice de réfraction

        Label texteIndiceRefraction = new Label("  Indice de réfraction : " + df.format(indiceRefraction));
        Slider indiceRefractionSlider = new Slider(1.2, 1.9,1.6);
        indiceRefractionSlider.setShowTickLabels(true);
        indiceRefractionSlider.setShowTickMarks(true);
        indiceRefractionSlider.setMajorTickUnit(1);
        indiceRefractionSlider.setMinorTickCount(1);
        indiceRefractionSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            this.indiceRefraction = (double) newValue;
            prisme.setIndiceDeRefraction(this.indiceRefraction);
            texteIndiceRefraction.setText("  Indice de réfraction : " + df.format(this.indiceRefraction));
            Simulateur.simulationMiniJeuTriangle(listLaserTriangle, prisme, listEtoile);
            textePoints.setText(" Points : " + pointEtoile + "/20");

            // Changement de couleur du cube en fonction de l'indice de réfraction

            if(1.2 <= prisme.getIndiceDeRefraction() && prisme.getIndiceDeRefraction() < 1.5)
                prisme.setColor(Color.rgb(112, 255, 245));
            if(1.5 == prisme.getIndiceDeRefraction() && prisme.getIndiceDeRefraction() < 1.7)
                prisme.setColor(Color.rgb(66, 189, 237));
            if(1.7 <= prisme.getIndiceDeRefraction() && prisme.getIndiceDeRefraction() < 1.8)
                prisme.setColor(Color.rgb(35, 117, 194));
            if(1.8 <= prisme.getIndiceDeRefraction())
                prisme.setColor(Color.rgb(87, 87, 235));
        });

        // Déclaration du ''Tooltip'' pour afficher la définition de l'indice de réfraction

        Tooltip messageIndiceRefraction = new Tooltip("L'indice de réfraction est une valeur décrivant le comportement de la lumière dans un milieu.");
        texteIndiceRefraction.setTooltip(messageIndiceRefraction);
        indiceRefractionSlider.setTooltip(messageIndiceRefraction);

        // Allignement des Sliders et des Labels

        VBox vBoxControle = new VBox(texteRotation,rotationSlider, texteIndiceRefraction, indiceRefractionSlider, textePoints);
        vBoxControle.setSpacing(20);

        // Fond du menu

        Rectangle fondMenu = new Rectangle(300,275);
        fondMenu.setFill(Color.LIGHTGRAY);
        fondMenu.setStroke(Color.BLACK);

        // Alignement du menu

        VBox vBoxMenu = new VBox(texteControle, vBoxControle);
        vBoxMenu.setAlignment(Pos.CENTER);
        vBoxMenu.setSpacing(20);
        StackPane stackPaneMenu = new StackPane(fondMenu, vBoxMenu);
        stackPaneMenu.setAlignment(Pos.CENTER);
        stackPaneMenu.setLayoutX(1050);
        stackPaneMenu.setLayoutY(30);

        // Lancement de la simulation
        Simulateur.simulationMiniJeuTriangle(listLaserTriangle, prisme, listEtoile);

        // Création du group des éléments du Mini Jeux
        Group groupMiniJeux = new Group(prisme, groupBordures, listLaserTriangle.get(0), listLaserTriangle.get(1), listLaserTriangle.get(2),
                listLaserTriangle.get(3),groupeEtoile, pointeur.getPointeur(), stackPaneMenu);

        // Affichage du mode difficile du ''Mini Jeux''

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(menuBar("difficile"));
        borderPane.setCenter(groupMiniJeux);
        return new Scene(borderPane);
    }

    // Méthode ''alerteVictoire''

    public static Alert alerteVictoire(long temps) {

        // Déclaration de ''l'Alerte de victoire''

        Alert alerte = new Alert(Alert.AlertType.INFORMATION);
        alerte.setTitle("Félicitation !!!");
        alerte.setHeaderText("Vous avez réussi le niveau!");
        alerte.setContentText("Votre temps est de : " + temps + " secondes");

        return alerte;
    }

    // Méthode ''menuBar''

    public MenuBar menuBar(String mode) {

        // Options

        Menu options = new Menu("Options");
        MenuItem menuPrincipal = new MenuItem("Menu principal");
        menuPrincipal.setOnAction(event -> {
            SonMenu.getClip().close();
            Main.setScene(Main.menu());
        });
        MenuItem menuRecommencer = new MenuItem("Recommencer");
        menuRecommencer.setOnAction(event -> {
            if (chrono.isRunning())
                chrono.stop();
            switch (mode) {
                case "facile" -> Main.setScene(jouerFacile());
                case "normal" -> Main.setScene(jouerNormal());
                case "difficile" -> Main.setScene(jouerDifficile());
            }
        });
        options.getItems().addAll(menuPrincipal, menuRecommencer);

        // Son

        Menu son = new Menu("Son");
        MenuItem jouer = new MenuItem("Jouer");
        jouer.setOnAction(event -> {
            if (!SonMenu.getClip().isActive())
                SonMenu.jouer();
        });
        MenuItem arreter = new MenuItem("Arrêter");
        arreter.setOnAction(event -> SonMenu.getClip().close());
        son.getItems().addAll(jouer, arreter);

        return new MenuBar(options, son);
    }
}