package Controller;


import Model.Partie;
import View.Game_View;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * Created by yhaffner on 15/12/16.
 */
public class Control_Game implements EventHandler<MouseEvent> {
    private final Partie model;
    private final Game_View view;
    private final Control_Menu menu;

    public Control_Game(Partie model,Control_Menu control_menu){
        this.model=model;
        this.view = new Game_View(model,control_menu.getView().getStage());
        this.menu = control_menu;
    }
    @Override
    public void handle(MouseEvent event) {

    }

}
