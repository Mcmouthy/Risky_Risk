package View;

import Controller.Control_Menu;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.File;

/**
 * Created by yhaffner on 21/11/16.
 */
public class Menu_View {

    public Button startButton;
    public Button nouvellePartie;
    public Button continuer;
    public Button options;
    public Button creerMap;
    public Button apropos;
    public Button quitter;
    public Pane titreJeu;
    public Label nbJoueurs;
    public ToggleGroup nbJoueursGroup;
    public RadioButton joueurButton2;
    public RadioButton joueurButton3;
    public RadioButton joueurButton4;
    public Label type;
    public ToggleGroup typeGroup;
    public RadioButton classique;
    public RadioButton rapide;
    public Label carte;
    public ComboBox<String> listeCarte;
    public Button suivant;
    public Button retour;
    public ImageView imagecarte;
    public TextField askNomJoueur1;
    public TextField askNomJoueur2;
    public TextField askNomJoueur3;
    public TextField askNomJoueur4;
    public Label nomJoueur1;
    public Label nomJoueur2;
    public Label nomJoueur3;
    public Label nomJoueur4;
    public ComboBox<String> couleurjoueur1;
    public ComboBox<String> couleurjoueur2;
    public ComboBox<String> couleurjoueur3;
    public ComboBox<String> couleurjoueur4;
    public Button lancerPartie;
    public ComboBox listeResolution;
    public CheckBox askFullscreen;
    public Button retour2;

    public Button saveSettings;
    private Stage stage; // Le stage est la fenetre principale
    public Slider sliderSoundVolume;
    public Slider sliderMusicVolume;

    public ComboBox<String> listeSave;
    public Button lancerSave;

    public Menu_View(Stage stage)
    {
        this.stage = stage;
        // intialisation des attributs (comme ca pas besoin de les recréer a chaque page du menu)
        initAttributs();
        // mise en place de la vue du menu principale
        setLauncherView();
    }

    private void initAttributs() {


        // attribution du fichier CSS
        stage.getScene().getStylesheets().add(new File("css/menu_view.css").toURI().toString());
        startButton = new Button("JOUER");
        startButton.setId("jouer");
        nouvellePartie = new Button("NOUVELLE PARTIE");
        nouvellePartie.setId("nouvellePartie");
        continuer = new Button("CONTINUER");
        continuer.setId("continuer");
        options = new Button("OPTIONS");
        options.setId("options");
        creerMap = new Button("CRÉER UNE MAP");
        creerMap.setId("options");
        apropos = new Button("À PROPOS");
        apropos.setId("apropos");
        quitter = new Button("QUITTER");
        quitter.setId("apropos");
        titreJeu = new VBox(new ImageView(new Image(new File("img/logo_pt_v1.png").toURI().toString(), 200 + stage.getScene().getWidth() * 30 / 192, 30000, true, true)));
        titreJeu.setId("titre");
        titreJeu.setMaxHeight(75 + stage.getScene().getWidth() * 10 / 192);

        nbJoueurs = new Label("Nombre de joueurs : ");
        nbJoueurs.setId("nbjoueurs");
        nbJoueursGroup = new ToggleGroup();
        joueurButton2 = new RadioButton("2");
        joueurButton2.setId("joueurButton2");
        joueurButton3 = new RadioButton("3");
        joueurButton3.setId("joueurButton3");
        joueurButton4 = new RadioButton("4");
        joueurButton4.setId("joueurButton4");
        joueurButton2.setToggleGroup(nbJoueursGroup);
        joueurButton3.setToggleGroup(nbJoueursGroup);
        joueurButton4.setToggleGroup(nbJoueursGroup);
        joueurButton2.setSelected(true);

        type = new Label("Type de partie : ");
        type.setId("type");
        typeGroup = new ToggleGroup();
        classique = new RadioButton("Classique");
        classique.setId("type");
        rapide = new RadioButton("Rapide");
        rapide.setId("type");
        classique.setToggleGroup(typeGroup);
        rapide.setToggleGroup(typeGroup);
        classique.setSelected(true);

        carte = new Label("Carte : ");
        carte.setId("carte");
        imagecarte = new ImageView();
        imagecarte.setFitWidth(stage.getScene().getWidth() / 3);
        imagecarte.setFitHeight(stage.getScene().getHeight() / 3);
        imagecarte.setPreserveRatio(true);
        listeCarte = new ComboBox<>();
        listeCarte.setId("listecarte");

        suivant = new Button("SUIVANT");
        suivant.setId("btAsking");
        retour = new Button("RETOUR");
        retour.setId("btAsking");
        saveSettings = new Button("APPLIQUER");
        saveSettings.setId("btAsking");

        askNomJoueur1 = new TextField();
        askNomJoueur1.setPromptText("Saisissez le nom");
        askNomJoueur1.setText("Joueur 1");
        askNomJoueur2 = new TextField();
        askNomJoueur2.setPromptText("Saisissez le nom");
        askNomJoueur2.setText("Joueur 2");
        askNomJoueur3 = new TextField();
        askNomJoueur3.setPromptText("Saisissez le nom");
        askNomJoueur3.setText("Joueur 3");
        askNomJoueur4 = new TextField();
        askNomJoueur4.setPromptText("Saisissez le nom");
        askNomJoueur4.setText("Joueur 4");

        nomJoueur1 = new Label("Joueur 1 :");
        nomJoueur1.setId("nomJoueur");
        nomJoueur2 = new Label("Joueur 2 :");
        nomJoueur2.setId("nomJoueur");
        nomJoueur3 = new Label("Joueur 3 :");
        nomJoueur3.setId("nomJoueur");
        nomJoueur4 = new Label("Joueur 4 :");
        nomJoueur4.setId("nomJoueur");

        couleurjoueur1 = new ComboBox<>();
        couleurjoueur2 = new ComboBox<>();
        couleurjoueur3 = new ComboBox<>();
        couleurjoueur4 = new ComboBox<>();

        couleurjoueur1.getItems().addAll("ROUGE", "BLEU", "VERT", "JAUNE", "NOIR");
        couleurjoueur1.setValue(couleurjoueur1.getItems().get(0));
        couleurjoueur2.getItems().addAll("ROUGE", "BLEU", "VERT", "JAUNE", "NOIR");
        couleurjoueur2.setValue(couleurjoueur2.getItems().get(1));
        couleurjoueur3.getItems().addAll("ROUGE", "BLEU", "VERT", "JAUNE", "NOIR");
        couleurjoueur3.setValue(couleurjoueur3.getItems().get(2));
        couleurjoueur4.getItems().addAll("ROUGE", "BLEU", "VERT", "JAUNE", "NOIR");
        couleurjoueur4.setValue(couleurjoueur4.getItems().get(3));

        lancerPartie = new Button("À L'ATTAQUE !");
        lancerPartie.setId("lancerpartie");
        retour2 = new Button("RETOUR");
        retour2.setId("retour2");


        listeResolution = new ComboBox<>();
        listeResolution.setId("listecarte");
        askFullscreen = new CheckBox("");
        sliderSoundVolume = new Slider(0, 1, 0);
        sliderMusicVolume = new Slider(0, 1, 0);

        listeSave= new ComboBox<>();
        listeSave.setId("listeSave");
        lancerSave = new Button("Charger la partie");
        lancerSave.setId("lancerSave");

    }

    public void setLauncherView(){
        stage.getScene().getRoot().setVisible(false);

        ((BorderPane) stage.getScene().getRoot()).setTop(titreJeu);
        ((BorderPane) stage.getScene().getRoot()).setCenter(startButton);


        stage.getScene().getRoot().setVisible(true);
    }

    public void setMainMenuView(){
        stage.getScene().getRoot().setVisible(false);

        ((BorderPane) stage.getScene().getRoot()).getChildren().clear();


        GridPane panneau = new GridPane();
        panneau.setId("panneau");
        panneau.add(nouvellePartie,0,0);
        panneau.add(continuer,0,1);
        panneau.add(options,0,2);
        panneau.add(creerMap,0,3);
        panneau.add(apropos,0,4);
        panneau.add(quitter,0,5);


        // Ajout final
        ((BorderPane) stage.getScene().getRoot()).setTop(titreJeu);
        ((BorderPane) stage.getScene().getRoot()).setCenter(panneau);


        // Montrer la vue
        stage.getScene().getRoot().setVisible(true);
    }

    public void setPartieAskingView(){
        stage.getScene().getRoot().setVisible(false);
        ((BorderPane) stage.getScene().getRoot()).getChildren().clear();

        VBox panneau = new VBox(5);
        panneau.setId("panAsking");
        HBox nbjoueurspartie= new HBox(5);
        nbjoueurspartie.setId("nbjoueurspartie");
        nbjoueurspartie.getChildren().addAll(nbJoueurs,joueurButton2,joueurButton3,joueurButton4);
        HBox typepartie=new HBox(5);
        typepartie.setId("typepartie");
        typepartie.getChildren().addAll(type,classique,rapide);
        VBox image=new VBox(8);
        image.setId("imgcarte");
        HBox carteMenu=new HBox(5);
        carteMenu.setId("cartemenu");
        carteMenu.setAlignment(Pos.CENTER);
        carteMenu.getChildren().addAll(listeCarte);
        image.getChildren().addAll(carteMenu,imagecarte);
        HBox bouton=new HBox(20);
        bouton.setId("btnHbox");
        bouton.getChildren().addAll(retour,suivant);
        bouton.setMaxHeight(50+stage.getScene().getWidth()*10/1920);

        panneau.getChildren().addAll(nbjoueurspartie,typepartie,image);

        ((BorderPane) stage.getScene().getRoot()).setTop(titreJeu);
        ((BorderPane) stage.getScene().getRoot()).setCenter(panneau);
        ((BorderPane) stage.getScene().getRoot()).setBottom(bouton);
        stage.getScene().getRoot().setVisible(true);
    }

    public void setNomCouleurJoueursAskingView() {
        stage.getScene().getRoot().setVisible(false);
        ((BorderPane) stage.getScene().getRoot()).getChildren().clear();
        VBox panneau = new VBox(25);
        panneau.setId("panJoueur");
        HBox joueur1 = new HBox(20);
        joueur1.setId("panJoueur");
        joueur1.getChildren().addAll(nomJoueur1,askNomJoueur1,couleurjoueur1);
        HBox joueur2=new HBox(20);
        joueur2.setId("panJoueur");
        joueur2.getChildren().addAll(nomJoueur2,askNomJoueur2,couleurjoueur2);
        HBox joueur3=new HBox(20);
        joueur3.setId("panJoueur");
        joueur3.getChildren().addAll(nomJoueur3,askNomJoueur3,couleurjoueur3);
        HBox joueur4=new HBox(20);
        joueur4.setId("panJoueur");
        joueur4.getChildren().addAll(nomJoueur4,askNomJoueur4,couleurjoueur4);
        HBox bouton = new HBox(20);
        bouton.setId("btnHbox");
        bouton.getChildren().addAll(retour2,lancerPartie);

        bouton.setMaxHeight(50+stage.getScene().getWidth()*10/1920);
        if (nbJoueursGroup.getToggles().get(0).isSelected()){
            panneau.getChildren().addAll(joueur1,joueur2);
        }else if (nbJoueursGroup.getToggles().get(1).isSelected()){
            panneau.getChildren().addAll(joueur1,joueur2,joueur3);
        }else{
            panneau.getChildren().addAll(joueur1,joueur2,joueur3,joueur4);
        }
        ((BorderPane) stage.getScene().getRoot()).setTop(titreJeu);
        ((BorderPane) stage.getScene().getRoot()).setCenter(panneau);
        ((BorderPane) stage.getScene().getRoot()).setBottom(bouton);
        stage.getScene().getRoot().setVisible(true);
    }

    public void popUpErreurSetNomCouleur(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(new File("css/dialog_view.css").toURI().toString());
        dialogPane.getStyleClass().add("dialog");
        alert.initOwner(stage);
        alert.setTitle("Erreur");
        alert.setHeaderText("Impossible de continuer");
        alert.setContentText("Les noms et couleurs doivent être différents");
        alert.showAndWait();
    }

    public void setOptionsView(){
        stage.getScene().getRoot().setVisible(false);
        ((BorderPane) stage.getScene().getRoot()).getChildren().clear();

        VBox panneau = new VBox(5);
        panneau.setId("panAsking");

        HBox itemFullsreen=new HBox(5);
        itemFullsreen.setId("cartemenu");
        itemFullsreen.setAlignment(Pos.CENTER);
        itemFullsreen.getChildren().addAll(new Label("Plein écran : "),askFullscreen);

        HBox itemResolution=new HBox(5);
        itemResolution.setId("cartemenu");
        itemResolution.setAlignment(Pos.CENTER);
        itemResolution.getChildren().addAll(new Label("Résolution : "),listeResolution);

        HBox itemSoundVolume=new HBox(5);
        itemSoundVolume.setId("cartemenu");
        itemSoundVolume.setAlignment(Pos.CENTER);
        itemSoundVolume.getChildren().addAll(new Label("Volume des bruitages : "), sliderSoundVolume);
        HBox itemMusicVolume=new HBox(5);
        itemMusicVolume.setId("cartemenu");
        itemMusicVolume.setAlignment(Pos.CENTER);
        itemMusicVolume.getChildren().addAll(new Label("Volume de la musique : "), sliderMusicVolume);

        panneau.getChildren().addAll(itemFullsreen,itemResolution,itemSoundVolume,itemMusicVolume);

        HBox bouton=new HBox(20);
        bouton.setId("btnHbox");
        bouton.getChildren().addAll(saveSettings);
        bouton.setMaxHeight(50+stage.getScene().getWidth()*10/1920);


        ((BorderPane) stage.getScene().getRoot()).setTop(titreJeu);
        ((BorderPane) stage.getScene().getRoot()).setCenter(panneau);
        ((BorderPane) stage.getScene().getRoot()).setBottom(bouton);
        stage.getScene().getRoot().setVisible(true);
    }

    public void resetComponentsSize() {
        titreJeu.getChildren().clear();
        titreJeu.getChildren().add(new ImageView(new Image(new File("img/logo_pt_v1.png").toURI().toString(), 200 + stage.getScene().getWindow().getWidth() * 30 / 192, 30000, true, true)));
        titreJeu.setMaxHeight(75 + stage.getScene().getWindow().getWidth() * 10 / 192);
        imagecarte.setFitWidth(stage.getScene().getWindow().getWidth() / 3);
        imagecarte.setFitHeight(stage.getScene().getWindow().getHeight() / 3);
    }

    public void setController(Control_Menu eh){
        startButton.setOnMouseClicked(eh);
        nouvellePartie.setOnMouseClicked(eh);
        suivant.setOnMouseClicked(eh);
        options.setOnMouseClicked(eh);
        quitter.setOnMouseClicked(eh);
        saveSettings.setOnMouseClicked(eh);
        retour.setOnMouseClicked(eh);
        retour2.setOnMouseClicked(eh);
        lancerPartie.setOnMouseClicked(eh);
        listeCarte.valueProperty().addListener(eh);
        askFullscreen.setOnMouseClicked(eh);
        continuer.setOnMouseClicked(eh);
        lancerSave.setOnMouseClicked(eh);
        creerMap.setOnMouseClicked(eh);
    }

    public Scene getScene()
    {
        return stage.getScene();
    }

    public Stage getStage()
    {
        return stage;
    }

    public void setHoverSound(EventHandler<MouseEvent> hoverSound) {
        startButton.setOnMouseEntered(hoverSound);
        nouvellePartie.setOnMouseEntered(hoverSound);
        continuer.setOnMouseEntered(hoverSound);
        options.setOnMouseEntered(hoverSound);
        apropos.setOnMouseEntered(hoverSound);
        quitter.setOnMouseEntered(hoverSound);
        retour2.setOnMouseEntered(hoverSound);
        lancerPartie.setOnMouseEntered(hoverSound);
        suivant.setOnMouseEntered(hoverSound);
        retour.setOnMouseEntered(hoverSound);
        saveSettings.setOnMouseEntered(hoverSound);
        lancerSave.setOnMouseEntered(hoverSound);
    }

    public void chooseSave(){
        stage.getScene().getRoot().setVisible(false);
        ((BorderPane) stage.getScene().getRoot()).getChildren().clear();

        VBox panneau = new VBox();
        HBox bout= new HBox();
        bout.getChildren().addAll(retour,lancerSave);
        panneau.getChildren().addAll(listeSave,bout);

        ((BorderPane) stage.getScene().getRoot()).setCenter(panneau);
        stage.getScene().getRoot().setVisible(true);

    }
}
