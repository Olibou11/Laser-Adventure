// Importation package
package bacASable;

// Importations

import coordonnee.Coordonnee;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import items.Laser;
import main.Main;
import items.Pointeur;
import items.Prisme;
import simulateur.Simulateur;
import son.SonMenu;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class BacASable {

    // Décaration des listes de rayons et de l'indice de réfraction

    private ArrayList<Laser> listLaserCube;
    private ArrayList<Laser> listLaserTriangle;
    private double indiceRefraction;


    // Méthode ''jouerCube''

    public Scene jouerCube() {

        // Initialisation de la liste de rayons et de l'indice de réfraction

        listLaserCube = new ArrayList<>();
        indiceRefraction = 1.5;

        // Déclaration des ''Rayons laser''
        listLaserCube.add(new Laser());
        listLaserCube.add(new Laser());
        listLaserCube.add(new Laser());
        listLaserCube.add(new Laser());

        // Déclaration du ''Pointeur laser''
        Pointeur pointeur = new Pointeur(100,325);

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

        // Menu de contrôle

        // Déclaration du titre du menu de contrôle
        Label texteControle = new Label("Contrôle :");

        // Déclaration du DecimalFormat
        DecimalFormat df = new DecimalFormat("0.00");

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
            Simulateur.simulationCube(listLaserCube,pointeur.getRotation(), cube, indiceRefraction);
        });
        VBox rotationVBox = new VBox(texteRotation,rotationSlider);
        rotationVBox.setSpacing(5);

        // Indice de réfraction

        Label texteIndiceRefraction = new Label("  Indice de réfraction : " + df.format(indiceRefraction));
        Slider indiceRefractionSlider = new Slider(1, 1.9,1.5);
        indiceRefractionSlider.setShowTickLabels(true);
        indiceRefractionSlider.setShowTickMarks(true);
        indiceRefractionSlider.setMajorTickUnit(1);
        indiceRefractionSlider.setMinorTickCount(1);
        indiceRefractionSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            indiceRefraction = (double) newValue;
            texteIndiceRefraction.setText("  Indice de réfraction : " + df.format(indiceRefraction));
            Simulateur.simulationCube(listLaserCube,pointeur.getRotation(), cube, indiceRefraction);

            // Changement de couleur du cube en fonction de l'indice de réfraction

            if (indiceRefraction == 1)
                cube.setFill(Color.WHITE);
            if(1 < indiceRefraction && indiceRefraction < 1.2)
                cube.setFill(Color.rgb(165, 240, 235));
            if(1.2 <= indiceRefraction && indiceRefraction < 1.5)
                cube.setFill(Color.rgb(112, 255, 245));
            if(1.5 <= indiceRefraction && indiceRefraction < 1.7)
                cube.setFill(Color.rgb(66, 189, 237));
            if(1.7 <= indiceRefraction && indiceRefraction < 1.8)
                cube.setFill(Color.rgb(35, 117, 194));
            if(1.8 <= indiceRefraction)
                cube.setFill(Color.rgb(87, 87, 235));
        });

        // Déclaration du ''Tooltip'' pour afficher la définition de l'indice de réfraction
        // Source : https://fr.wikipedia.org/wiki/Indice_de_r%C3%A9fraction

        Tooltip messageIndiceRefraction = new Tooltip("L'indice de réfraction est une valeur décrivant le comportement de la lumière dans un milieu.");
        texteIndiceRefraction.setTooltip(messageIndiceRefraction);
        indiceRefractionSlider.setTooltip(messageIndiceRefraction);
        VBox indiceVBox = new VBox(texteIndiceRefraction,indiceRefractionSlider);
        indiceVBox.setSpacing(5);

        // Fond du menu de contrôle

        Rectangle fondMenu = new Rectangle(300,250);
        fondMenu.setFill(Color.LIGHTGRAY);
        fondMenu.setStroke(Color.BLACK);

        // Alignement du menu

        VBox vBoxMenu = new VBox(texteControle, rotationVBox, indiceVBox);
        vBoxMenu.setAlignment(Pos.CENTER);
        vBoxMenu.setSpacing(20);
        StackPane stackPaneMenu = new StackPane(fondMenu, vBoxMenu);
        stackPaneMenu.setAlignment(Pos.CENTER);
        stackPaneMenu.setLayoutX(1050);
        stackPaneMenu.setLayoutY(30);

        // Lancement de la simulation
        Simulateur.simulationCube(listLaserCube, pointeur.getRotation(),cube, indiceRefraction);

        // Création du group des éléments du Bac à sable
        Group groupBacASable = new Group(cube,groupBordures, listLaserCube.get(0), listLaserCube.get(1), listLaserCube.get(2),
                listLaserCube.get(3), pointeur.getPointeur(), stackPaneMenu);

        // Affichage du cube du mode ''Bac à Sable''

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(menuBar());
        borderPane.setCenter(groupBacASable);
        return new Scene(borderPane);
    }

    // Méthode ''jouerTriangle''

    public Scene jouerTriangle() {

        // Initialisation de l'indice de réfraction et de la liste de rayons

        indiceRefraction = 1.5;
        listLaserTriangle = new ArrayList<>();

        // Déclaration des ''Rayons laser''
        listLaserTriangle.add(new Laser());
        listLaserTriangle.add(new Laser());
        listLaserTriangle.add(new Laser());
        listLaserTriangle.add(new Laser());
        listLaserTriangle.get(0).setInclinaison(0);

        // Déclaration du Prisme réfractant
        Prisme prisme = new Prisme(new Coordonnee(650,250), indiceRefraction);

        // Déclaration du ''Pointeur laser''
        Pointeur pointeur = new Pointeur(100,325);

        // Bordures
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

        // Menu de contrôle

        // Déclaration du titre du menu de contrôle
        Label texteControle = new Label("Contrôle :");

        // Déclaration du DecimalFormat
        DecimalFormat df = new DecimalFormat("0.00");

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
            Simulateur.simulationTriangle(listLaserTriangle,prisme);
        });
        VBox rotationVBox = new VBox(texteRotation,rotationSlider);
        rotationVBox.setSpacing(5);

        // Indice de réfraction

        Label texteIndiceRefraction = new Label("  Indice de réfraction : " + df.format(indiceRefraction));
        Slider indiceRefractionSlider = new Slider(1, 1.9,1.5);
        indiceRefractionSlider.setShowTickLabels(true);
        indiceRefractionSlider.setShowTickMarks(true);
        indiceRefractionSlider.setMajorTickUnit(1);
        indiceRefractionSlider.setMinorTickCount(1);
        indiceRefractionSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            this.indiceRefraction = (double) newValue;
            prisme.setIndiceDeRefraction(this.indiceRefraction);
            texteIndiceRefraction.setText("  Indice de réfraction : " + df.format(this.indiceRefraction));
            Simulateur.simulationTriangle(listLaserTriangle,prisme);

            // Changement de couleur du cube en fonction de l'indice de réfraction

            if (prisme.getIndiceDeRefraction() == 1)
                prisme.setColor(Color.WHITE);
            if(1 < prisme.getIndiceDeRefraction() && prisme.getIndiceDeRefraction() < 1.2)
                prisme.setColor(Color.rgb(165, 240, 235));
            if(1.2 <= prisme.getIndiceDeRefraction() && prisme.getIndiceDeRefraction() < 1.5)
                prisme.setColor(Color.rgb(112, 255, 245));
            if(1.5 <= prisme.getIndiceDeRefraction() && prisme.getIndiceDeRefraction() < 1.7)
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
        VBox indiceVBox = new VBox(texteIndiceRefraction,indiceRefractionSlider);
        indiceVBox.setSpacing(5);

        // Fond du menu

        Rectangle fondMenu = new Rectangle(300,250);
        fondMenu.setFill(Color.LIGHTGRAY);
        fondMenu.setStroke(Color.BLACK);

        // Alignement du menu

        VBox vBoxMenu = new VBox(texteControle, rotationVBox, indiceVBox);
        vBoxMenu.setAlignment(Pos.CENTER);
        vBoxMenu.setSpacing(20);
        StackPane stackPaneMenu = new StackPane(fondMenu, vBoxMenu);
        stackPaneMenu.setAlignment(Pos.CENTER);
        stackPaneMenu.setLayoutX(1050);
        stackPaneMenu.setLayoutY(30);

        // Lancement de la simulation
        Simulateur.simulationTriangle(listLaserTriangle,prisme);

        // Création du group des éléments du Bac à sable
        Group groupBacASable = new Group(prisme, groupBordures, listLaserTriangle.get(0), listLaserTriangle.get(1),
                listLaserTriangle.get(2), listLaserTriangle.get(3), pointeur.getPointeur(), stackPaneMenu);

        // Affichage du prisme du mode ''Bac à Sable''

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(menuBar());
        borderPane.setCenter(groupBacASable);
        return new Scene(borderPane);
    }

    public MenuBar menuBar() {

        // Options

        Menu options = new Menu("Options");
        MenuItem menuPrincipal = new MenuItem("Menu principal");
        menuPrincipal.setOnAction(event -> {
            SonMenu.getClip().close();
            Main.setScene(Main.menu());
        });
        options.getItems().add(menuPrincipal);

        // Mode de jeux

        Menu modeDeJeux = new Menu("Mode de jeux");
        MenuItem modeCube = new MenuItem("Cube");
        modeCube.setOnAction(event -> Main.setScene(jouerCube()));
        MenuItem modeTriangle = new MenuItem("Triangle");
        modeTriangle.setOnAction(event -> Main.setScene(jouerTriangle()));
        modeDeJeux.getItems().addAll(modeCube, modeTriangle);

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

        return new MenuBar(options, modeDeJeux, son);
    }
}