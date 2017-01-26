package View;

import Model.Partie;
import com.sun.webkit.ColorChooser;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;

import static java.lang.System.exit;

/**
 * Created by yhaffner on 21/11/16.
 */
public class Menu_View {

    public Button startButton;
    public Button nouvellePartie;
    public Button continuer;
    public Button options;
    public Button apropos;
    public ImageView titreJeu;
    public Label nbJoueurs;
    public ToggleGroup nbJoueursGroup;
    public RadioButton joueurButton1;
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
    public Button choix;
    public TextField askNomJoueur1;
    public TextField askNomJoueur2;
    public TextField askNomJoueur3;
    public TextField askNomJoueur4;
    public Label nomJoueur1;
    public Label nomJoueur2;
    public Label nomJoueur3;
    public Label nomJoueur4;
    private Stage stage; // Le stage est la fenetre principale
    private Scene scene; // La scene est le contenu visile de la fenetre
    private BorderPane root; // Le root est le panel (comme ds java SWING) principal. C'est dedans que vous metterez tout
    public Menu_View(Stage stage)
    {
        this.stage = stage;

        // intialisation des attributs (comme ca pas besoin de les recréer a chaque page du menu)
        initAttributs();
        // mise en place de la vue du menu principale
        setLauncherView();
        //setMainMenuView();
    }

    // TODO ULTRA IPORTANT
    private void initAttributs() {
        /*
         Initialisation ultra basique de la fenêtre
         Don't forget que vous pouvez rajouter pour chaque element graphique (héritant de Node),
         vous pouvez rajouter un ID ou une classe de la sorte:
            bouton.setId('boutonID');
            bouton.getStyleClass().add("boutonClass");
          */
        stage.setTitle("Risky Risk");
        stage.getIcons().add(new Image(new File("img/icon.png").toURI().toString()));
        stage.centerOnScreen();
        stage.setResizable(false);
        root = new BorderPane();
        scene = new Scene(root, 700, 500, Color.BLACK);
        stage.setScene(scene);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                stage.close();
                exit(0);
            }
        });
        // attribution du fichier CSS
        stage.getScene().getStylesheets().add(new File("css/menu_view.css").toURI().toString());
        stage.show();
        startButton = new Button("JOUER");
        startButton.setId("jouer");
        nouvellePartie = new Button("NOUVELLE PARTIE");
        nouvellePartie.setId("nouvellePartie");
        continuer = new Button("CONTINUER");
        continuer.setId("continuer");
        options = new Button("OPTIONS");
        options.setId("options");
        apropos = new Button("À PROPOS");
        apropos.setId("apropos");
        titreJeu = new ImageView(new Image(new File("img/logo_pt_v1.png").toURI().toString(), 300, 300, true, true));

        nbJoueurs = new Label("Nombre de joueurs : ");
        nbJoueurs.setId("nbjoueurs");
        nbJoueursGroup = new ToggleGroup();
        joueurButton1= new RadioButton("1");
        joueurButton1.setId("joueurButton1");
        joueurButton2= new RadioButton("2");
        joueurButton2.setId("joueurButton2");
        joueurButton3=new RadioButton("3");
        joueurButton3.setId("joueurButton3");
        joueurButton4=new RadioButton("4");
        joueurButton4.setId("joueurButton4");
        joueurButton1.setToggleGroup(nbJoueursGroup);
        joueurButton2.setToggleGroup(nbJoueursGroup);
        joueurButton3.setToggleGroup(nbJoueursGroup);
        joueurButton4.setToggleGroup(nbJoueursGroup);

        type = new Label("Type de partie : ");
        type.setId("type");
        typeGroup = new ToggleGroup();
        classique = new RadioButton("Classique");
        classique.setId("classique");
        rapide = new RadioButton("Rapide");
        rapide.setId("rapide");
        classique.setToggleGroup(typeGroup);
        rapide.setToggleGroup(typeGroup);

        carte=new Label("Carte : ");
        carte.setId("carte");
        listeCarte=new ComboBox<>();
        listeCarte.getItems().add("Base");
        listeCarte.getItems().add("icon");
        listeCarte.setId("listecarte");
        listeCarte.setValue("Base");

        suivant=new Button("SUIVANT");
        suivant.setId("suiv");
        retour=new Button("RETOUR");
        retour.setId("retour");
        choix=new Button("CHOISIR");
        choix.setId("choix");

        askNomJoueur1=new TextField();
        askNomJoueur2=new TextField();
        askNomJoueur3=new TextField();
        askNomJoueur4=new TextField();

        nomJoueur1 = new Label("Nom du joueur 1 :");
        nomJoueur2 = new Label("Nom du joueur 2 :");
        nomJoueur3 = new Label("Nom du joueur 3 :");
        nomJoueur4 = new Label("Nom du joueur 4 :");


        /*
          Initialisation des attributs:
          TOUS LES ELEMENTS DEVRONT ETRE INSTANCIÉS ICI.
          Voici les classes DE JAVAfx dont vous aurez surement besoin:

          new Button        : bouton
          new Label         : text
          new TextField     : input
          new BorderPane    : panel contenant les regions Center,Top,Bottom,Left et Right
          new VBox          : panel dont les élements vont de haut en bas
          new GridPane      : panel avec des coordonnées

         */
    }

    //TODO IMPORTANT
    public void setLauncherView(){
        stage.getScene().getRoot().setVisible(false);

        VBox panneau = new VBox();
        panneau.setId("pan-jouer");
        titreJeu.setId("titre1");

        panneau.getChildren().add(titreJeu);
        panneau.getChildren().add(startButton);

        ((BorderPane) stage.getScene().getRoot()).setCenter(/* élément principal au lieu de null */panneau);

        stage.getScene().getRoot().setVisible(true);
    }

    // TODO ULTRA IMPORTANT
    public void setMainMenuView(){
        /*
         Affichage du menu principal
         Prototype pour les autres view de cette classe
        */

        // Cacher la vue actuelle
        stage.getScene().getRoot().setVisible(false);


        // Nettoyer le contenu d'une précédente fenêtre
        // Manoeuvre a repeter pour tout les panel qui seront utilisées de la sorte:
        // panel.getChildren().clear()
        ((BorderPane) stage.getScene().getRoot()).getChildren().clear();


        // Ajout des éléments de la sorte:
        // panel.getChildren().add(Node element);
        // ex:
        //   vbox.getChildren().add(jLabel);
        //   gridlayout.getChildren().add(jnomTextField,indexX,indexY);
        //   borderpane.setCenter(blasonsJ);
        //   menuSection.getChildren().add(continueButton);
        titreJeu.setId("titre2");

        GridPane panneau = new GridPane();
        panneau.setId("panneau");
        panneau.add(nouvellePartie,0,0);
        panneau.add(continuer,0,1);
        panneau.add(options,0,2);
        panneau.add(apropos,0,3);


        // Ajout final
        ((BorderPane) stage.getScene().getRoot()).setTop(titreJeu);
        ((BorderPane) stage.getScene().getRoot()).setCenter(panneau);


        // Montrer la vue
        stage.getScene().getRoot().setVisible(true);
    }

    // TODO ULTRA IMPORTANT
    public void setPartieAskingView(){
        /*
        Cette vue doit afficher un input ou l'on peut rentrer le nombre de joueur et
        commencer la partie ou revenir en arrière
         */
        stage.getScene().getRoot().setVisible(false);
        ((BorderPane) stage.getScene().getRoot()).getChildren().clear();

        VBox panneau = new VBox();
        HBox nbjoueurspartie= new HBox();
        nbjoueurspartie.getChildren().addAll(nbJoueurs,joueurButton1,joueurButton2,joueurButton3,joueurButton4);
        HBox typepartie=new HBox();
        typepartie.getChildren().addAll(type,classique,rapide);
        VBox image=new VBox();
        HBox carteMenu=new HBox();
        imagecarte= new ImageView();
        carteMenu.getChildren().addAll(listeCarte,choix);
        image.getChildren().addAll(carteMenu,imagecarte);
        HBox bouton=new HBox();
        bouton.getChildren().addAll(retour,suivant);

        panneau.getChildren().addAll(nbjoueurspartie,typepartie,image,bouton);

        ((BorderPane) stage.getScene().getRoot()).setCenter(panneau);
        stage.getScene().getRoot().setVisible(true);

    }

    //TODO
    public void setNomCouleurJoueursAskingView() {

    }

    // TODO
    public void setOptionsView(){
        /*
        Cette vue doit afficher des radio buttons choisir la resolution et le fullscreen
         */
    }

    // TODO ULTRA
    public void setController(EventHandler<MouseEvent> eh){
        /*

        Attribution du controlleur au bouton INSTANCIÉS AU PRÉALABLE (dans initAttributs) par la méthode suivante:

        bouton.setOnMouseClicked(eh);

         */
        startButton.setOnMouseClicked(eh);
        nouvellePartie.setOnMouseClicked(eh);
        suivant.setOnMouseClicked(eh);
        retour.setOnMouseClicked(eh);
        choix.setOnMouseClicked(eh);

        // au final rajoute un bouton pour valider parce que sinon c'est chiant

    }

    public Scene getScene()
    {
        return scene;
    }

    public Stage getStage()
    {
        return stage;
    }

}
