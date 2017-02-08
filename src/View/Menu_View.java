package View;

import Controller.Control_Menu;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
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
    public Button retour2;

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
    }

    private void initAttributs() {
        stage.setTitle("Risky Risk");
        stage.getIcons().add(new Image(new File("img/icon.png").toURI().toString()));
        stage.centerOnScreen();
        stage.setResizable(false);
        root = new BorderPane();


        /* TODO remove that ugly following thing */
            // TEST fullscreen
        //stage.setFullScreen(true);
        //scene = new Scene(root, Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight(), Color.BLACK);
             scene = new Scene(root, 700, 500, Color.BLACK);
        /* ************************************* */


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
        titreJeu = new VBox(new ImageView(new Image(new File("img/logo_pt_v1.png").toURI().toString(), 200+scene.getWidth()*30/192, 30000, true, true)));
        titreJeu.setId("titre");
        titreJeu.setMaxHeight(75+scene.getWidth()*10/192);

        nbJoueurs = new Label("Nombre de joueurs : ");
        nbJoueurs.setId("nbjoueurs");
        nbJoueursGroup = new ToggleGroup();
        joueurButton2= new RadioButton("2");
        joueurButton2.setId("joueurButton2");
        joueurButton3=new RadioButton("3");
        joueurButton3.setId("joueurButton3");
        joueurButton4=new RadioButton("4");
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

        carte=new Label("Carte : ");
        carte.setId("carte");
        imagecarte= new ImageView();
        imagecarte.setFitWidth(scene.getWidth()/3);
        imagecarte.setFitHeight(scene.getHeight()/3);
        imagecarte.setPreserveRatio(true);
        listeCarte=new ComboBox<>();
        listeCarte.setId("listecarte");

        suivant=new Button("SUIVANT");
        suivant.setId("btAsking");
        retour=new Button("RETOUR");
        retour.setId("btAsking");

        askNomJoueur1=new TextField();
        askNomJoueur1.setPromptText("Saisissez le nom");
        askNomJoueur2=new TextField();
        askNomJoueur2.setPromptText("Saisissez le nom");
        askNomJoueur3=new TextField();
        askNomJoueur3.setPromptText("Saisissez le nom");
        askNomJoueur4=new TextField();
        askNomJoueur4.setPromptText("Saisissez le nom");

        nomJoueur1 = new Label("Joueur 1 :");
        nomJoueur1.setId("nomJoueur");
        nomJoueur2 = new Label("Joueur 2 :");
        nomJoueur2.setId("nomJoueur");
        nomJoueur3 = new Label("Joueur 3 :");
        nomJoueur3.setId("nomJoueur");
        nomJoueur4 = new Label("Joueur 4 :");
        nomJoueur4.setId("nomJoueur");

        couleurjoueur1=new ComboBox<>();
        couleurjoueur2=new ComboBox<>();
        couleurjoueur3=new ComboBox<>();
        couleurjoueur4=new ComboBox<>();

        couleurjoueur1.getItems().addAll("ROUGE","BLEU","VERT","JAUNE","NOIR");
        couleurjoueur1.setValue(couleurjoueur1.getItems().get(0));
        couleurjoueur2.getItems().addAll("ROUGE","BLEU","VERT","JAUNE","NOIR");
        couleurjoueur2.setValue(couleurjoueur2.getItems().get(1));
        couleurjoueur3.getItems().addAll("ROUGE","BLEU","VERT","JAUNE","NOIR");
        couleurjoueur3.setValue(couleurjoueur3.getItems().get(2));
        couleurjoueur4.getItems().addAll("ROUGE","BLEU","VERT","JAUNE","NOIR");
        couleurjoueur4.setValue(couleurjoueur4.getItems().get(3));

        lancerPartie=new Button("À L'ATTAQUE !");
        lancerPartie.setId("lancerpartie");
        retour2=new Button("RETOUR");
        retour2.setId("retour2");
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
        panneau.add(apropos,0,3);


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
        bouton.setMaxHeight(50+scene.getWidth()*10/1920);

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

        bouton.setMaxHeight(50+scene.getWidth()*10/1920);
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

    //TODO petit pop up qui apparait si il y a une erreur lors du choix des parametres de la partie
    public void popUpErreurSetPartie(){

    }

    //TODO petit pop up qui apparait si il y a une erreur lors du choix des couleurs et du nom de joueur
    public void popUpErreurSetNomCouleur(){

    }

    // TODO menu qui demande de changer les parametres de la fenetre (pas necessaire pour le moment)
    public void setOptionsView(){
        /*
        Cette vue doit afficher des radio buttons choisir la resolution et le fullscreen
         */
    }

    public void setController(Control_Menu eh){

        startButton.setOnMouseClicked(eh);
        nouvellePartie.setOnMouseClicked(eh);
        suivant.setOnMouseClicked(eh);
        retour.setOnMouseClicked(eh);
        retour2.setOnMouseClicked(eh);
        lancerPartie.setOnMouseClicked(eh);
        listeCarte.valueProperty().addListener(eh);


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
