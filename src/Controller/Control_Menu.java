package Controller;


import Model.Case;
import Model.Joueur;
import Model.Partie;
import View.Menu_View;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

import static java.lang.System.exit;

/**
 * Created by yhaffner on 21/11/16.
 */
public class Control_Menu implements EventHandler<MouseEvent>, javafx.beans.value.ChangeListener<String>
{
    private Menu_View view;
    private Control_Game game;
    private File[] maps;

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
            view.listeCarte.getItems().clear();
            for(String s:getCartesNames())
                view.listeCarte.getItems().add(s);
            view.listeCarte.setValue(view.listeCarte.getItems().get(0));
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

        if (event.getSource().equals(getView().retour2)){
            view.setPartieAskingView();
        }

        if (event.getSource().equals(getView().lancerPartie)){
            if (!mauvaisChoixCouleurs() && !nomJoueurNull() && !sameNomJoueur()){
                nouvellepartie();
            }else{
                //TODO petit pop-up qui dit l'erreur qui et détecté
            }
        }

    }

    public Menu_View getView()
    {
        return view;
    }

    public void nouvellepartie() {
        Partie p = new Partie(view.listeCarte.getValue());
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

    //DONE fonction qui verifie si il n'y a pas deux fois la meme couleur dans les combobox
    public boolean mauvaisChoixCouleurs(){
        if (view.nbJoueursGroup.getToggles().get(0).isSelected()){
            if (view.couleurjoueur1.getValue().equals(view.couleurjoueur2.getValue())) {
                return true;
            }
        }else if (view.nbJoueursGroup.getToggles().get(1).isSelected()){
            if (view.couleurjoueur1.getValue().equals(view.couleurjoueur2.getValue()) || view.couleurjoueur1.getValue().equals(view.couleurjoueur3.getValue())
                    || view.couleurjoueur2.getValue().equals(view.couleurjoueur3.getValue())) {
                return true;
            }
        }else{
            if (view.couleurjoueur1.getValue().equals(view.couleurjoueur2.getValue()) || view.couleurjoueur1.getValue().equals(view.couleurjoueur3.getValue()) || view.couleurjoueur1.getValue().equals(view.couleurjoueur4.getValue())
                    || view.couleurjoueur2.getValue().equals(view.couleurjoueur3.getValue()) || view.couleurjoueur2.getValue().equals(view.couleurjoueur4.getValue())
                    || view.couleurjoueur3.getValue().equals(view.couleurjoueur4.getValue())) {
                return true;
            }
        }
        return false;
    }

    public boolean nomJoueurNull(){
        if (view.nbJoueursGroup.getToggles().get(0).isSelected()){
            if (view.askNomJoueur1.getText().equals("") || view.askNomJoueur2.getText().equals("")){
                return true;
            }
        }else if (view.nbJoueursGroup.getToggles().get(1).isSelected()){
            if (view.askNomJoueur1.getText().equals("") || view.askNomJoueur2.getText().equals("") || view.askNomJoueur3.getText().equals("")){
                return true;
            }
        }else{
            if (view.askNomJoueur1.getText().equals("") || view.askNomJoueur2.getText().equals("")
                    || view.askNomJoueur3.getText().equals("") || view.askNomJoueur4.getText().equals("")){
                return true;
            }
        }
        return false;
    }

    private boolean sameNomJoueur() {
        if (view.nbJoueursGroup.getToggles().get(0).isSelected()){
            if (view.askNomJoueur1.getText().equals(view.askNomJoueur2.getText())){
                return true;
            }
        }else if (view.nbJoueursGroup.getToggles().get(1).isSelected()){
            if (view.askNomJoueur1.getText().equals(view.askNomJoueur2.getText()) || view.askNomJoueur1.getText().equals(view.askNomJoueur3.getText())
                    || view.askNomJoueur2.getText().equals(view.askNomJoueur3.getText())){
                return true;
            }
        }else{
            if (view.askNomJoueur1.getText().equals(view.askNomJoueur2.getText()) || view.askNomJoueur1.getText().equals(view.askNomJoueur3.getText()) || view.askNomJoueur1.getText().equals(view.askNomJoueur4.getText())
                    || view.askNomJoueur2.getText().equals(view.askNomJoueur3.getText()) || view.askNomJoueur2.getText().equals(view.askNomJoueur4.getText())
                    || view.askNomJoueur3.getText().equals(view.askNomJoueur4.getText())){
                return true;
            }
        }
        return false;
    }

    public String[] getCartesNames() {
        maps = new File("map").listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                String[] parts = name.split("\\.");
                return parts.length>0 && parts[parts.length-1].equals("map");
            }
        });


        // TODO AMENER L'UTILISATEUR À CRÉER SA MAP EN CAS D'ERREUR
        if(maps==null) {System.out.println("PAS DE MAPS DISPO, EN CRÉER UNE AVEC LE GÉNÉRATEUR");exit(-1);}

        String[] cartesName = new String[maps.length];
        for(int i=0;i<maps.length;i++) {
            String name = "";
            for(int j=0;j<maps[i].getName().split("\\.").length-1;j++)
                name+=maps[i].getName().split("\\.")[j];
            cartesName[i]=name;
        }
        return cartesName;
    }
    public BufferedImage getImageForCarteId(int id) {
        BufferedImage image = null;
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("map/"+maps[id].getName()));
            int nb = ois.readInt();
            for(int i=0;i<nb;i++) ois.readObject();
            image = ImageIO.read(ois);
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return image;
    }

    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        for(int i=0;i<view.listeCarte.getItems().size();i++)
            if(view.listeCarte.getItems().get(i).equals(newValue))
                view.imagecarte.setImage(SwingFXUtils.toFXImage(getImageForCarteId(i), null));
    }
}
