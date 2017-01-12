package Controller;


import Model.Joueur;
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
        view.setGameView();
    }
    @Override
    public void handle(MouseEvent event) {
        if(event.getSource().equals(view.endTurn)){
            model.passeJoueurSuivant();
        }
    }
}

// Au tour de JOUEUR.nom de joeur!
// Commencez par placer de vos renfort
// Renfort restant: n
// Attaquez ou déplacez vous en cliquant sur une case étrangère
// Cliquez sur 'Terminer le tour' pour finir votre tour


/* DÉROULEMENT

        while(!model.isFin()){
            for (Joueur j : model.getJoueurs()){
                //joueur.calcul renfort
                //tant que joueur.nbrenfort>0:
                    //si le joueur clique sur une case lui appartenant
                    //on incremente de 1 le nbtroupe de la case
                    //on decremente de 1 le nbrenfort
                    //sinon on fait rien ou message en mode
                    //ON NE FAIT PAS DE PRET DE TROUPE !

                //tant que le bouton fin du tour n'a pas ete clique on fait:
                    //si le joueur clique sur une de ces cases et une case neutre
                    //on lance la fonction conquerirNeutre() de Partie

                    //si le joueur clique sur une de ces cases et une autre de ces cases
                    // on demande de combien il veut deplacer et on utilise deplacement dans partie
                    //NON DISPONIBLE EN RAPIDE

                    //si il clique sur une de ces cases et une ennemie on demande de combien il veut envoyer ses
                    //troupes et on lance l'attaque
                    //si le defenseur n'a plus de terrain on le kick de la partie
                    //si PARTIE RAPIDE, on demande pas le nombre a envoyer on envoie la totalité-1

            }
            //une fois que tout les joueurs on jouer on incremente le tour de 1
        }
        //on affiche le vainqueur
 */