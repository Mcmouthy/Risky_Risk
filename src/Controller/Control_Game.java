package Controller;


import Model.Case;
import Model.Joueur;
import Model.Partie;
import View.Game_View;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Path;
import java.io.File;
import java.util.Random;


/**
 * Created by yhaffner on 15/12/16.
 */
public class Control_Game implements EventHandler<MouseEvent>{
    private final Partie model;
    private final Game_View view;
    private final Control_Menu menu;
    private final Random loto = new Random();

    public Control_Game(Partie model,Control_Menu control_menu){
        this.model=model;
        model.setJoueurCourant(loto.nextInt(model.getJoueurs().size()));
        for(Joueur j:model.getJoueurs())
            model.conquerirNeutre(j,model.getNeutres().get(loto.nextInt(model.getNeutres().size())),1);
        this.view = new Game_View(model,control_menu.getView().getStage());
        this.menu = control_menu;

        view.endTurn.disableProperty().setValue(true);
        model.calculRenforts(model.getJoueurCourant());
        view.notice.setText(model.getJoueurCourant().getNom()+"\nPlacez vos renforts!");

        setEvenHandlers();

        File file = new File("musics/RiskSoundtrack.wav");
        AudioClip clip= new AudioClip(file.toURI().toString());
        clip.play();

        view.setGameView();
        view.actualiserAffichage();
    }

    private void setEvenHandlers() {
        view.setController(this);
        view.stage.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP:
                        model.map_translate.y -= 70;
                        break;
                    case DOWN:
                        model.map_translate.y += 70;
                        break;
                    case LEFT:
                        model.map_translate.x -= 70;
                        break;
                    case RIGHT:
                        model.map_translate.x += 70;
                        break;
                    case ADD:
                        model.map_zoom += 0.1;
                        break;
                    case SUBTRACT:
                        model.map_zoom -= 0.1;
                        break;
                }
                view.actualiserAffichage();
            }
        });
        view.stage.getScene().setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getX()<15) {model.map_translate.x -= 30;view.actualiserAffichage();}
                if(Math.abs(event.getX()-view.stage.getScene().getWidth())<15) {model.map_translate.x += 30;view.actualiserAffichage();}

                if(event.getY()<15) {model.map_translate.y -= 30;view.actualiserAffichage();}
                if(Math.abs(event.getY()-view.stage.getScene().getHeight())<15) {model.map_translate.y += 30;view.actualiserAffichage();}
            }
        });
        view.stage.getScene().setOnScroll(new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                model.map_zoom += event.getDeltaY()/400;
                view.actualiserAffichage();
            }
        });
    }

    @Override
    public void handle(MouseEvent event) {
        if(event.getSource().equals(view.endTurn))
        {
            view.endTurn.disableProperty().setValue(true);
            model.passeJoueurSuivant();
            model.passeEtapeSuivante();
            model.calculRenforts(model.getJoueurCourant());
            view.notice.setText(model.getJoueurCourant().getNom()+"\nPlacez vos renforts!");
            view.caseOnFocus=null;
        }
        else if(event.getSource() instanceof Path)
        {
            Path b = ((Path) event.getSource());
            Case c = view.allCases.get(b);

            if(model.isDistributionRenforts() && model.getJoueurCourant().getTerrain().contains(c)) {
                view.caseOnFocus=null;
                model.getJoueurCourant().setNbRenforts(model.getJoueurCourant().getNbRenforts()-1);
                c.addRenforts();
                if(model.getJoueurCourant().getNbRenforts()>0) {
                    view.notice.setText(model.getJoueurCourant().getNom() + "\n" +
                            model.getJoueurCourant().getNbRenforts() + " renforts restant");
                } else {
                    model.passeEtapeSuivante();
                    view.notice.setText(model.getJoueurCourant().getNom()+"\n"+
                            "Cliquez sur une de vos case puis sur une case adversaire ou neutre pour tenter de la conquérir");
                    view.endTurn.disableProperty().setValue(false);
                }


            } else if(model.isAttaque_deplacements()) {
                Case caseattaquante=view.allCases.get(view.caseOnFocus);
                if((view.caseOnFocus != null) && (view.allCases.get(view.caseOnFocus).getNbtroupes() > 1) && (view.allCases.get(view.caseOnFocus).getJoueur() == model.getJoueurCourant()) && model.getJoueurCourant().getTerrain().get(model.getJoueurCourant().getindexTerrain(view.allCases.get(view.caseOnFocus))).getVoisins().contains(c)) {
                    if (model.getNeutres().contains(c)) {
                        if (c.getNbtroupes() == 0) {
                            model.conquerirNeutre(model.getJoueurCourant(), c, caseattaquante.getNbtroupes()-1);
                            model.getJoueurCourant().getTerrain().get(model.getJoueurCourant().getindexTerrain(caseattaquante)).setNbtroupes(1);
                        }
                    } else if (!model.getJoueurCourant().getTerrain().contains(c)){
                        for (Joueur j : model.getJoueurs()) {
                            if (j.getTerrain().contains(c)) {
                                model.captureTerrainAdverse(model.getJoueurCourant(), j, c, caseattaquante.getNbtroupes());
                                model.getJoueurCourant().getTerrain().get(model.getJoueurCourant().getindexTerrain(caseattaquante)).setNbtroupes(1);
                            }
                        }
                    }
                    view.caseOnFocus=null;
                } else view.caseOnFocus=b;
            }

            for (Joueur j:model.getJoueurs()){
                if (j.getTerrain().size()==0){
                    j.setEliminated(true);
                }
            }

            if (model.nbjoueurRestant()==1){
                model.setFin(true);
                view.setFinDePartieView();
            }
        }
        view.actualizeCases();

        if (event.getSource().equals(view.retour)){
            menu.getView().setMainMenuView();
            menu.getView().getStage().getScene().getStylesheets().clear();
            menu.getView().getStage().getScene().getStylesheets().add(new File("css/menu_view.css").toURI().toString());
        }else if(event.getSource().equals(view.recommencer)){
            menu.nouvellepartie();

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