package View;

import Model.Case;
import Model.Partie;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.swing.text.html.ImageView;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yhaffner on 08/12/16
 */
public class Game_View {
    private Partie model;
    public Stage stage;
    private Menu_View menu_view;

    public Button endTurn;
    public Label notice;
    private HashMap<Button, Case> allCases;


    public Game_View(Partie model, Stage stage) {
        this.model = model;
        this.stage = stage;
        initAttributs();
        setGameView();
    }

    private void initAttributs() {
        // attribution du fichier CSS
        stage.getScene().getStylesheets().clear();
        stage.getScene().getStylesheets().add(new File("css/menu_view.css").toURI().toString());
        endTurn = new Button("Terminer le tour");
        notice = new Label("");

        allCases = new HashMap<Button,Case>();
        for(Case c:model.getNeutres()){
            allCases.put(
                    new Button(c.getNbtroupes()+""),
                    c
            );
        }
    }



    public void setGameView() {
        stage.getScene().getRoot().setVisible(false);
        ((BorderPane) stage.getScene().getRoot()).getChildren().clear();


        HBox panel = new HBox();
        panel.getChildren().add(endTurn);
        panel.getChildren().add(notice);

        GridPane game = new GridPane();
        for(Map.Entry<Button,Case> e: allCases.entrySet())
            game.add(e.getKey(),e.getValue().getX(),e.getValue().getY());

        ((BorderPane) stage.getScene().getRoot()).setCenter(endTurn);

        stage.getScene().getRoot().setVisible(true);
    }
}
