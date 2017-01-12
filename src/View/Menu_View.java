package View;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;

/**
 * Created by yhaffner on 21/11/16.
 */
public class Menu_View
{
    public Button startButton;
    public Button nouvellePartie;
    public Button continuer;
    public Button options;
    public Button apropos;
    public ImageView titreJeu;
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
    private void initAttributs()
    {
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
        scene = new Scene(root, 500, 500, Color.BLACK);
        stage.setScene(scene);
        stage.close();
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
        titreJeu.setId("titre");



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
    public void setLauncherView()
    {
        stage.getScene().getRoot().setVisible(false);

        VBox panneau = new VBox();
        panneau.setId("panneau");
        panneau.getChildren().add(titreJeu);
        panneau.getChildren().add(startButton);

        ((BorderPane) stage.getScene().getRoot()).setCenter(/* élément principal au lieu de null */panneau);

        stage.getScene().getRoot().setVisible(true);
    }

    // TODO ULTRA IMPORTANT
    public void setMainMenuView()
    {
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

        VBox panneau = new VBox();
        panneau.setId("panneau");
        panneau.getChildren().add(titreJeu);
        panneau.getChildren().add(nouvellePartie);
        panneau.getChildren().add(continuer);
        panneau.getChildren().add(options);
        panneau.getChildren().add(apropos);


        // Ajout final
        ((BorderPane) stage.getScene().getRoot()).setCenter(panneau);


        // Montrer la vue
        stage.getScene().getRoot().setVisible(true);
    }

    // TODO ULTRA IMPORTANT
    public void setNbPlayerAskingView()
    {
        /*
        Cette vue doit afficher un input ou l'on peut rentrer le nombre de joueur et
        commencer la partie ou revenir en arrière
         */

    }

    // TODO
    public void setOptionsView()
    {
        /*
        Cette vue doit afficher des radio buttons choisir la resolution et le fullscreen
         */
    }

    // TODO ULTRA
    public void setController(EventHandler<MouseEvent> eh)
    {
        /*

        Attribution du controlleur au bouton INSTANCIÉS AU PRÉALABLE (dans initAttributs) par la méthode suivante:

        bouton.setOnMouseClicked(eh);

         */
        startButton.setOnMouseClicked(eh);
        nouvellePartie.setOnMouseClicked(eh);
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
