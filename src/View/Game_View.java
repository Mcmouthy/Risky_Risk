package View;

import Model.Partie;
import javafx.stage.Stage;

/**
 * Created by yhaffner on 08/12/16
 */
public class Game_View {
    private Partie model;
    public Stage stage;
    private Menu_View menu_view;

    public Game_View(Partie model, Stage stage) {
        this.model = model;
        this.stage = stage;

        initAttributs();
        setGameView();

    }



    private void initAttributs() {

    }

    private void setGameView() {

    }
}
