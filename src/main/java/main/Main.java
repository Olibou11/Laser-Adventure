// Importation package
package main;

// Importations

import bacASable.BacASable;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import miniJeux.MiniJeux;
import son.SonMenu;

public class Main extends Application {

    // Déclaration du stage

    private static Stage stage;
    public static void setScene(Scene scene) { stage.setScene(scene); }

    @Override
    public void start(Stage primaryStage){

        // Initialisation des caractéristiques du stage

        stage = primaryStage;
        stage.setTitle("Laser Adventure");
        stage.setMaximized(true);
        stage.setWidth(800);
        stage.setHeight(400);

        // Affichage du menu

        stage.setScene(menu());
        stage.show();
    }

    // Méthode ''menu''

    public static Scene menu() {

        // Lancement de la musique
        SonMenu.jouer();

        // Déclaration du fond du menu

        Rectangle fond = new Rectangle(1400,800);
        Stop[] stops = new Stop[]{new Stop(0, Color.BLUE), new Stop(0.5, Color.LIGHTBLUE), new Stop(1, Color.LIGHTGREY),};
        LinearGradient gradient = new LinearGradient(0,0, 1,1, true, CycleMethod.NO_CYCLE, stops);
        fond.setFill(gradient);

        // Déclaration du label du titre

        Label labelTitre = new Label("Laser Adventure");
        labelTitre.setFont(new Font("Bauhaus 93", 50));
        labelTitre.setTextFill(Color.RED);

        // Déclaration du bouton ''Bac à sable''

        Button boutonBacASable = new Button("Bac à sable");
        boutonBacASable.setFont(new Font("Bauhaus 93", 40));
        boutonBacASable.setTextFill(Color.RED);
        boutonBacASable.setStyle("-fx-background-color: #f5f5f5;");
        BacASable bacASable = new BacASable();
        boutonBacASable.setOnAction(event -> Main.setScene(bacASable.jouerCube()));

        // Déclaration du bouton ''Mini Jeux''

        Button boutonMiniJeux = new Button("Mini Jeux");
        boutonMiniJeux.setFont(new Font("Bauhaus 93", 40));
        boutonMiniJeux.setTextFill(Color.RED);
        boutonMiniJeux.setStyle("-fx-background-color: #f5f5f5;");
        MiniJeux miniJeux = new MiniJeux();
        boutonMiniJeux.setOnAction(event -> Main.setScene(miniJeux.choixDifficulte()));

        // Déclaration du bouton ''Quitter''

        Button boutonQuitter = new Button("Quitter");
        boutonQuitter.setFont(new Font("Bauhaus 93", 40));
        boutonQuitter.setTextFill(Color.RED);
        boutonQuitter.setStyle("-fx-background-color: #f5f5f5;");
        boutonQuitter.setOnAction(event -> stage.close());

        // Allignement et supperposition des éléments graphiques

        VBox vBox = new VBox(labelTitre,boutonBacASable, boutonMiniJeux, boutonQuitter);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(50);
        StackPane stackPane = new StackPane(fond, vBox);
        stackPane.setAlignment(Pos.CENTER);

        // Affichage du menu

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(stackPane);
        return new Scene(borderPane);
    }

    public static void main(String[] args) { launch(); }
}
/*
Le code magique :

Programme programme = new Programme();
programme.fonctionneBien();
*/