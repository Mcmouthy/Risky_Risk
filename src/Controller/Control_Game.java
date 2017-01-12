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
        while(!model.isFin()){
            //si le joueur clique sur une de ces cases et une case neutre
            //on lance la fonction conquerirNeutre() de Partie

            //si le joueur clique sur une de ces cases et une autre de ces cases
            // on demande de combien il veut deplacer et on utilise deplacement dans partie
            //NON DISPONIBLE EN RAPIDE

            //si il clique sur une de ces cases et une ennemie on demande de combien il veut envoyer ses
            //troupes et on lance l'attaque
            //si PARTIE RAPIDE, on demande pas le nombre a envoyer on envoie la totalité-1


        }

    }

}
