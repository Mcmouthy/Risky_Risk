package Controller;


import Model.Generateur_v2;
import Model.Joueur;
import Model.Partie;
import View.Menu_View;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

import static java.lang.System.exit;

/**
 * Created by yhaffner on 21/11/16.
 */
public class Control_Menu implements EventHandler<MouseEvent>, javafx.beans.value.ChangeListener<String>
{
    public final static String[] resolutions = {
                        		"800 x 600",		"1024 x 600",		"1024 x 768",
            "1280 x 800",		"1366 x 768",		"1440 x 900",		"1600 x 900",
            "1680 x 900",		"1920 x 1080",		"1920 x 1200",		"2560 x 1440"
    };

    private Menu_View view;
    protected Control_Game game;
    private File[] maps;
    private File[] save;

    // SETTINGS
    int resolution = 0;
    boolean fullscreen = true;
    double soundVolume = 1;
    double musicVolume = 1;
    AudioClip clipo;

    public Control_Menu(Stage stage)
    {
        BorderPane root = new BorderPane();
        Scene scene;
        initFromConfigFile();


        stage.setFullScreen(fullscreen);
        if(fullscreen)
            scene = new Scene(root, Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight(), Color.BLACK);
        else
            scene = new Scene(root, Double.parseDouble(resolutions[resolution].split(" x ")[0]), Double.parseDouble(resolutions[resolution].split(" x ")[1]), Color.BLACK);


        stage.setScene(scene);
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        stage.show();

        this.view = new Menu_View(stage);
        setEvenHandlers();
        this.view.askFullscreen.setSelected(fullscreen);
        for (String s : Control_Menu.resolutions) if(
                Screen.getPrimary().getBounds().getWidth()>=Double.parseDouble(s.split(" x ")[0]) &&
                        Screen.getPrimary().getBounds().getHeight()>=Double.parseDouble(s.split(" x ")[1]))
            view.listeResolution.getItems().add(s);
        this.view.listeResolution.setValue(resolutions[resolution]);
        this.view.listeResolution.setDisable(fullscreen);
        this.view.sliderSoundVolume.setValue(soundVolume);
        this.view.sliderMusicVolume.setValue(musicVolume);

    }

    private void setEvenHandlers() {
        view.getStage().setOnCloseRequest(event -> {
            view.getStage().close();
            exit(0);
        });
        this.view.setController(this);
        this.view.setHoverSound(event -> {
            AudioClip clip = new AudioClip(new File("sounds/buttonHover.wav").toURI().toString());
            clip.setVolume(soundVolume);
            if(soundVolume>0.01) clip.play();
        });
        this.view.sliderSoundVolume.setOnMouseReleased(event -> {
            AudioClip clip = new AudioClip(new File("sounds/button.wav").toURI().toString());
            clip.setVolume(view.sliderSoundVolume.getValue());
            clip.play();
        });
        this.view.sliderMusicVolume.setOnMouseReleased(event -> {
            AudioClip clip = new AudioClip(new File("sounds/button.wav").toURI().toString());
            clip.setVolume(view.sliderMusicVolume.getValue());
            clip.play();
        });
        clipo = new AudioClip(new File("musics/VraieMenu.wav").toURI().toString());
        clipo.setVolume(musicVolume);
        clipo.setCycleCount(50);
        if (musicVolume>0.01)clipo.play();
    }

    @Override
    public void handle(MouseEvent event) {
        /* BRUITAGES */
        if (event.getSource() instanceof Button) {
            AudioClip clip = new AudioClip(new File("sounds/button.wav").toURI().toString());
            clip.setVolume(soundVolume);
            if(soundVolume>0.01) clip.play();
        }


        /* ACTIONS */
        if (event.getSource().equals(getView().startButton)) {
            view.setMainMenuView();
        } else if (event.getSource().equals(getView().nouvellePartie)) {
            view.listeCarte.getItems().clear();
            for (String s : getCartesNames())
                view.listeCarte.getItems().add(s);
            view.listeCarte.setValue(view.listeCarte.getItems().get(0));
            view.setPartieAskingView();
        } else if (event.getSource().equals(getView().suivant)) {
            if (!nbJoueursNonChoisi() && !typePartieChoisi()) {
                view.setNomCouleurJoueursAskingView();
            }
        } else if (event.getSource().equals(getView().creerMap)) {
            Generateur_v2 g = new Generateur_v2();
            try {
                g.start(view.getStage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (event.getSource().equals(getView().retour)) {
            view.setMainMenuView();
        } else if (event.getSource().equals(getView().retour2)) {
            view.setPartieAskingView();
        } else if (event.getSource().equals(getView().lancerPartie)) {
            if (!mauvaisChoixCouleurs() && !nomJoueurNull() && !sameNomJoueur()) {
                clipo.stop();
                nouvellepartie();
            } else {
                view.popUpErreurSetNomCouleur();
            }
        } else if (event.getSource().equals(getView().options)) {
            view.setOptionsView();
        } else if (event.getSource().equals(getView().saveSettings)) {
            boolean changement = fullscreen!=view.askFullscreen.isSelected();
            fullscreen = view.askFullscreen.isSelected();
            for(int i=0;i<resolutions.length;i++) if(resolutions[i].equals(view.listeResolution.getValue())) {
                changement |= resolution!=i;
                resolution = i;
            }
            double stat=clipo.getPan();
            clipo.stop();
            soundVolume = view.sliderSoundVolume.getValue();
            musicVolume = view.sliderMusicVolume.getValue();
            clipo.setPan(stat);
            clipo.setCycleCount(50);
            clipo.play(musicVolume);


            if(changement) view.getStage().hide();
            view.getStage().setFullScreen(fullscreen);
            if(fullscreen) {
                view.getScene().getWindow().setWidth(Screen.getPrimary().getBounds().getWidth());
                view.getScene().getWindow().setHeight(Screen.getPrimary().getBounds().getHeight());
            } else {
                view.getScene().getWindow().setWidth(Double.parseDouble(resolutions[resolution].split(" x ")[0]));
                view.getScene().getWindow().setHeight(Double.parseDouble(resolutions[resolution].split(" x ")[1]));
            }
            if(changement) {view.getStage().show();view.resetComponentsSize();}

            writeConfigFile();
            view.setMainMenuView();
        } else if(event.getSource().equals(getView().askFullscreen)) {
            view.listeResolution.setDisable(view.askFullscreen.isSelected());
        } else if(event.getSource().equals(getView().quitter)) {
            view.getStage().close();exit(0);
        }else if(event.getSource().equals(getView().continuer)){
            for (String s : getSavesName())
                view.listeSave.getItems().add(s);
            if(getSavesName().length!=0) {
                view.listeSave.setValue(view.listeSave.getItems().get(0));
                view.chooseSave();
            }
        }else if (event.getSource().equals(getView().lancerSave)){
            if (!getView().listeSave.getValue().equals("")) {
                Partie p = Partie.loadGame(getView().listeSave.getValue());
                clipo.stop();
                game = new Control_Game(p, this, false);

            }
        }
    }
    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        for(int i=0;i<view.listeCarte.getItems().size();i++)
            if(view.listeCarte.getItems().get(i).equals(newValue))
                view.imagecarte.setImage(SwingFXUtils.toFXImage(getImageForCarteId(i), null));
    }


    public Menu_View getView()
    {
        return view;
    }
    public void nouvellepartie() {
        Partie p = new Partie(view.listeCarte.getValue());
        p.setPlateauDimensions(view.getScene().getWidth(),view.getScene().getHeight());
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

        game=new Control_Game(p,this,true);
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

        if(maps==null){
            if(Generateur_v2.askForBoolean(view.getStage(),"Aucune map existante!", "Souhaitez vous créer un map?")){
                try {
                    (new Generateur_v2()).start(view.getStage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            } else {System.out.println("PAS DE MAPS DISPO, EN CRÉER UNE AVEC LE GÉNÉRATEUR");exit(-1);}
        }

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


    private void initFromConfigFile() {
        File config = new File("setup.cfg");
        if(config.exists()){
            try {
                DataInputStream dis = new DataInputStream(new FileInputStream(config));
                fullscreen = dis.readBoolean();
                if(!fullscreen) resolution = dis.readInt();
                soundVolume = dis.readDouble();
                musicVolume = dis.readDouble();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else writeConfigFile();
    }
    private void writeConfigFile(){
        try {
            DataOutputStream dos = new DataOutputStream(new FileOutputStream(new File("setup.cfg")));
            dos.writeBoolean(fullscreen);
            if(!fullscreen) dos.writeInt(resolution);
            dos.writeDouble(soundVolume);
            dos.writeDouble(musicVolume);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] getSavesName() {
        save = new File("save").listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                String[] parts = name.split("\\.");
                return parts.length > 0 && parts[parts.length - 1].equals("save");
            }
        });
        String[] savesName = new String[save.length];
        for (int i = 0; i < save.length; i++) {
            String name = "";
            for (int j = 0; j < save[i].getName().split("\\.").length - 1; j++)
                name += save[i].getName().split("\\.")[j];
            savesName[i] = name;
        }
        return savesName;
    }

}
