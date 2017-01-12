package View;

import Model.Partie;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * Created by yhaffner on 08/12/16
 */
public class Game_View {
    private Partie model;
    public Stage stage;
    private Menu_View menu_view;

    public Button endTurn;
    public Label notice;


    public Game_View(Partie model, Stage stage) {
        this.model = model;
        this.stage = stage;
        initAttributs();
        setGameView();
    }

    private void initAttributs() {
        endTurn = new Button("Terminer le tour");
        notice = new Label("");
    }

    public void setGameView() {
        stage.getScene().getRoot().setVisible(false);
        ((BorderPane) stage.getScene().getRoot()).getChildren().clear();


        HBox panel = new HBox();
        panel.getChildren().add(endTurn);
        panel.getChildren().add(notice);

        ((BorderPane) stage.getScene().getRoot()).setCenter(endTurn);

        stage.getScene().getRoot().setVisible(true);
    }
}
