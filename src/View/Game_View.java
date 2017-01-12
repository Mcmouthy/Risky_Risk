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

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yhaffner on 08/12/16
 */
public class Game_View {
    final static String NEUTRE="case-neutre";
    final static String COLOR_RED="case-red";
    final static String COLOR_BLUE="case-blue";
    final static String COLOR_GREEN="case-green";
    final static String COLOR_YELLOW="case-yellow";
    final static String COLOR_BLACK="case-black";
    private Partie model;
    public Stage stage;
    private Menu_View menu_view;

    public Button endTurn;
    public Label notice;
    public HashMap<Button, Case> allCases;


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

        allCases = new HashMap<Button,Case>();
        Button b;
        for(Case c:model.getNeutres()){
            b = new Button(c.getNbtroupes()+"");
            b.getStyleClass().add(NEUTRE);
            allCases.put(b,c);
        }
        for(Joueur j:model.getJoueurs()){
            for(Case c:j.getTerrain()) {
                b = new Button(c.getNbtroupes() + "");
                switch(j.getCouleur()) {
                    case Joueur.RED:
                        b.getStyleClass().add(COLOR_RED);
                        break;
                    case Joueur.BLUE:
                        b.getStyleClass().add(COLOR_BLUE);
                        break;
                    case Joueur.GREEN:
                        b.getStyleClass().add(COLOR_GREEN);
                        break;
                    case Joueur.YELLOW:
                        b.getStyleClass().add(COLOR_YELLOW);
                        break;
                    case Joueur.BLACK:
                        b.getStyleClass().add(COLOR_BLACK);
                        break;
                }
                allCases.put(b, c);
            }
        }
    }



    public void setGameView() {
        stage.getScene().getRoot().setVisible(false);
        ((BorderPane) stage.getScene().getRoot()).getChildren().clear();

        GridPane game = new GridPane();
        game.setId("game");
        for(Map.Entry<Button,Case> e: allCases.entrySet())
            game.add(e.getKey(),e.getValue().getX(),e.getValue().getY());

        VBox panel = new VBox();
        panel.setId("panel");
        //panel.getChildren().add(game);
        panel.getChildren().add(endTurn);
        panel.getChildren().add(notice);

        ((BorderPane) stage.getScene().getRoot()).setLeft(panel);
        ((BorderPane) stage.getScene().getRoot()).setCenter(game);

        stage.getScene().getRoot().setVisible(true);
    }

    public void setController(EventHandler<MouseEvent> eh){
        for(Map.Entry<Button,Case> e: allCases.entrySet())
            e.getKey().setOnMouseClicked(eh);
        endTurn.setOnMouseClicked(eh);
    }
}
