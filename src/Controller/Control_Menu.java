package Controller;


import View.Menu_View;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Created by yhaffner on 21/11/16.
 */
public class Control_Menu implements EventHandler<ActionEvent> {
    private Menu_View view;

    public Control_Menu(Menu_View menu) {
        this.view = menu;
        this.view.setController(this);
    }

    @Override
    // TODO ULTRA IMPORTANT
    public void handle(ActionEvent event) {
        /*
        Réception des objets du genre:

        si(event.getSource().equals(view.bouton){
            traiter...
            ex: view.setOptionsView();
        }

         */
    }
}
