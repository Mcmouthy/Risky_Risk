package View;

import Model.Case;
import Model.Joueur;
import Model.Partie;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yhaffner on 08/12/16
 */
public class Game_View {
    final static String NEUTRE = "case-neutre";
    final static String COLOR_RED = "case-red";
    final static String COLOR_BLUE = "case-blue";
    final static String COLOR_GREEN = "case-green";
    final static String COLOR_YELLOW = "case-yellow";
    final static String COLOR_BLACK = "case-black";
    private Partie model;
    public Stage stage;
    private Menu_View menu_view;

    public Button endTurn;
    public Label notice;
    public Label mode;
    public HashMap<Button, Case> allCases;
    public Button caseOnFocus;
    public Label finDePartie;
    public Label message;
    public Button retour;
    public Button recommencer;


    public Game_View(Partie model, Stage stage) {
        this.model = model;
        this.stage = stage;
        initAttributs();
        setGameView();
    }

    private void initAttributs() {
        // attribution du fichier CSS
        stage.getScene().getStylesheets().clear();
        stage.getScene().getStylesheets().add(new File("css/game_view.css").toURI().toString());
        endTurn = new Button("Terminer le tour");
        endTurn.setId("terminer");
        notice = new Label("esrtdtyfyguhij");
        notice.setId("notice");
        mode = new Label("Partie Rapide");
        mode.setId("mode-label");
        finDePartie=new Label("Félicitations "+model.getJoueurCourant().getNom()+", vous avez gagné !");
        finDePartie.setId("fin");
        message=new Label("Vous pouvez revenir au menu principal ou rejouer avec les mêmes paramètres.");
        message.setId("message");
        retour = new Button("Menu Principal");
        retour.setId("retour");
        recommencer= new Button("Recommencer");
        recommencer.setId("recommencer");

        allCases = new HashMap<Button, Case>();
        for (Case c : model.getNeutres()) {
            allCases.put(new Button(c.getNbtroupes() + ""), c);
            for (Joueur j : model.getJoueurs())
                for (Case c2 : j.getTerrain())
                    allCases.put(new Button(c2.getNbtroupes() + ""), c2);
        }
        actualizeCases();
    }


    public void setGameView() {
        stage.getScene().getRoot().setVisible(false);
        ((BorderPane) stage.getScene().getRoot()).getChildren().clear();

        GridPane game = new GridPane();

        game.setId("game");
        for(Map.Entry<Button,Case> e: allCases.entrySet())
            game.add(e.getKey(),e.getValue().getX()/100,e.getValue().getY()/100);

        HBox panel = new HBox();
        panel.setId("panel");
        //panel.getChildren().add(game);
        panel.getChildren().add(endTurn);
        panel.getChildren().add(notice);
        panel.getChildren().add(mode);

        ((BorderPane) stage.getScene().getRoot()).setCenter(game);
        ((BorderPane) stage.getScene().getRoot()).setBottom(panel);

        stage.getScene().getRoot().setVisible(true);
    }

    public void setController(EventHandler<MouseEvent> eh) {
        for (Map.Entry<Button, Case> e : allCases.entrySet())
            e.getKey().setOnMouseClicked(eh);
        endTurn.setOnMouseClicked(eh);
        retour.setOnMouseClicked(eh);
        recommencer.setOnMouseClicked(eh);
    }

    public void actualizeCases() {
        for (Map.Entry<Button, Case> e : allCases.entrySet()) {
            e.getKey().setText(e.getValue().getNbtroupes() + "");
            e.getKey().getStyleClass().clear();
            e.getKey().getStyleClass().add("button");
            if(caseOnFocus!=null && caseOnFocus.equals(e.getKey()))
                e.getKey().getStyleClass().add("focus");
            if (e.getValue().getJoueur() != null)
                switch (e.getValue().getJoueur().getCouleur()) {
                    case Joueur.RED:
                        e.getKey().getStyleClass().add(COLOR_RED);
                        break;
                    case Joueur.BLUE:
                        e.getKey().getStyleClass().add(COLOR_BLUE);
                        break;
                    case Joueur.GREEN:
                        e.getKey().getStyleClass().add(COLOR_GREEN);
                        break;
                    case Joueur.YELLOW:
                        e.getKey().getStyleClass().add(COLOR_YELLOW);
                        break;
                    case Joueur.BLACK:
                        e.getKey().getStyleClass().add(COLOR_BLACK);
                        break;
                    default:
                        e.getKey().getStyleClass().add(NEUTRE);
                }
            else
                e.getKey().getStyleClass().add(NEUTRE);
        }
    }

    public void setFinDePartieView(){
        stage.getScene().getRoot().setVisible(false);

        VBox panneau=new VBox();
        panneau.setId("panneauFinPartie");
        HBox bouton = new HBox();
        bouton.setId("bouton");

        panneau.getChildren().add(finDePartie);
        panneau.getChildren().add(message);

        bouton.getChildren().add(retour);
        bouton.getChildren().add(recommencer);

        ((BorderPane) stage.getScene().getRoot()).setCenter(panneau);
        ((BorderPane) stage.getScene().getRoot()).setBottom(bouton);

        stage.getScene().getRoot().setVisible(true);
    }
}
