package Controller;


import Model.Joueur;
import Model.Partie;
import View.Menu_View;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.io.File;

/**
 * Created by yhaffner on 21/11/16.
 */
public class Control_Menu implements EventHandler<MouseEvent>
{
    private Menu_View view;
    private Control_Game game;

    public Control_Menu(Menu_View menu)
    {
        this.view = menu;
        this.view.setController(this);

        // En attendant un menu fonctionnel, pour passer direct au jeu:
        // Partie p = new Partie();
        // p.ajouterJoueur(new Joueur("Pierre",0));
        // p.ajouterJoueur(new Joueur("Gérard",1));
        // new Control_Game(p,this);
        // fin truc inutile
    }

    @Override
    // TODO ULTRA IMPORTANT
    public void handle(MouseEvent event)
    {
        /*
        Réception des objets du genre:

        si(event.getSource().equals(view.bouton){
            traiter...
            ex: view.setOptionsView();
        }
        */

        if (event.getSource().equals(getView().startButton))
        {
            view.setMainMenuView();
        }

        if (event.getSource().equals(getView().nouvellePartie))
        {
            view.setPartieAskingView();
        }

        if (event.getSource().equals(getView().suivant)){
            if (!nbJoueursNonChoisi() && !typePartieChoisi()) {
                view.setNomCouleurJoueursAskingView();
            }else{
                //TODO faut faire un petit affichage qui dit nique ta maman t'as pas choisi les trucs
            }
        }

        if (event.getSource().equals(getView().retour)){
            view.setMainMenuView();
        }

        if (event.getSource().equals(getView().choix)){
            String nomImage="img/"+view.listeCarte.getValue()+".png";
            view.imagecarte.setImage(new Image(new File(nomImage).toURI().toString(), 150, 150, true, true));

        }

        if (event.getSource().equals(getView().retour2)){
            view.setPartieAskingView();
        }

        if (event.getSource().equals(getView().lancerPartie)){

            nouvellepartie();
        }

    }
/*
        pour le début de partie, il faudra juste créer ça:
                Partie p = new Partie();
                ajouter à p les joueurs
                new Control_Game(p,this);
       c tout!
         */

    public Menu_View getView()
    {
        return view;
    }

    public void nouvellepartie() {
        Partie p = new Partie();
        if (view.nbJoueursGroup.getToggles().get(0).isSelected()){
            p.ajouterJoueur(new Joueur(view.askNomJoueur1.getText(),view.couleurjoueur1.getItems().indexOf(view.couleurjoueur1.getValue())));
            p.ajouterJoueur(new Joueur(view.askNomJoueur2.getText(),view.couleurjoueur2.getItems().indexOf(view.couleurjoueur2.getValue())));
        }else if (view.nbJoueursGroup.getToggles().get(1).isSelected()){
            p.ajouterJoueur(new Joueur(view.askNomJoueur1.getText(),view.couleurjoueur1.getItems().indexOf(view.couleurjoueur1.getValue())));
            p.ajouterJoueur(new Joueur(view.askNomJoueur2.getText(),view.couleurjoueur2.getItems().indexOf(view.couleurjoueur2.getValue())));
            p.ajouterJoueur(new Joueur(view.askNomJoueur3.getText(),view.couleurjoueur3.getItems().indexOf(view.couleurjoueur3.getValue())));
        }else if(view.nbJoueursGroup.getToggles().get(2).isSelected()){
            p.ajouterJoueur(new Joueur(view.askNomJoueur1.getText(),view.couleurjoueur1.getItems().indexOf(view.couleurjoueur1.getValue())));
            p.ajouterJoueur(new Joueur(view.askNomJoueur2.getText(),view.couleurjoueur2.getItems().indexOf(view.couleurjoueur2.getValue())));
            p.ajouterJoueur(new Joueur(view.askNomJoueur3.getText(),view.couleurjoueur3.getItems().indexOf(view.couleurjoueur3.getValue())));
            p.ajouterJoueur(new Joueur(view.askNomJoueur4.getText(),view.couleurjoueur4.getItems().indexOf(view.couleurjoueur4.getValue())));
        }

        if(view.typeGroup.getToggles().get(0).isSelected()){
            p.setMode(0);
        }else{
            p.setMode(1);
        }

        switch (view.listeCarte.getValue()){
            case "Base":
                p.setTheme(2);
        }
        game=new Control_Game(p,this);
    }

    public boolean nbJoueursNonChoisi(){
        for (int i=0;i<view.nbJoueursGroup.getToggles().size();i++){
            if (view.nbJoueursGroup.getToggles().get(i).isSelected()){
                return false;
            }
        }
        return true;
    }

    public boolean typePartieChoisi(){
        for (int i=0;i<view.typeGroup.getToggles().size();i++){
            if (view.typeGroup.getToggles().get(i).isSelected()){
                return false;
            }
        }
        return true;
    }
}
