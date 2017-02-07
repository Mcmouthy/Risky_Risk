package View;

import Model.Case;
import Model.Joueur;
import Model.Partie;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yhaffner on 08/12/16
 */
public class Game_View {
    final static String NEUTRE = "case-neutre";
    final static String COLOR_RED = "case-red";
    final static String COLOR_BLUE = "case-blue";
    final static String COLOR_GREEN = "case-green";
    final static String COLOR_YELLOW = "case-yellow";
    final static String COLOR_BLACK = "case-black";
    private Partie model;
    public Stage stage;
    private Menu_View menu_view;

    public Button endTurn;
    public Label notice;
    public Label mode;
    public HashMap<Path, Case> allCases;
    public HashMap<Path, Label> labels;
    public Path caseOnFocus;
    public Label finDePartie;
    public Label message;
    public Button retour;
    public Button recommencer;
    private ImageView background;
    public StackPane game;


    public Game_View(Partie model, Stage stage) {
        this.model = model;
        this.stage = stage;
        initAttributs();
        setGameView();
    }

    private void initAttributs() {
        // attribution du fichier CSS
        stage.getScene().getStylesheets().clear();
        stage.getScene().getStylesheets().add(new File("css/game_view.css").toURI().toString());
        endTurn = new Button("Terminer le tour");
        endTurn.setId("terminer");
        notice = new Label("esrtdtyfyguhij");
        notice.setId("notice");
        mode = new Label("Partie Rapide");
        mode.setId("mode-label");
        finDePartie=new Label("Félicitations "+model.getJoueurCourant().getNom()+", vous avez gagné !");
        finDePartie.setId("fin");
        message=new Label("Vous pouvez revenir au menu principal ou rejouer avec les mêmes paramètres.");
        message.setId("message");
        retour = new Button("Menu Principal");
        retour.setId("retour");
        recommencer= new Button("Recommencer");
        recommencer.setId("recommencer");
        background = new ImageView(model.getBackgroundImage());

        allCases = new HashMap<Path, Case>();
        labels = new HashMap<Path, Label>();

        for (Case c : model.getNeutres())
            createTerritoryFor(c);
        for (Joueur j : model.getJoueurs())
            for (Case c : j.getTerrain())
                createTerritoryFor(c);
        actualizeCases();
    }


    public void createTerritoryFor(Case c) {
        int w=50,h=20;
        Point lastPoint = null;
        Path p = new Path();
        for(Point pt:c.getPoints()) {
            if(lastPoint==null) {
                lastPoint=pt;
                p.getElements().add(new MoveTo(pt.x,pt.y));
            }
            else p.getElements().add(new LineTo(pt.x,pt.y));
        }
        p.getElements().add(new ClosePath());
        p.setTranslateX(c.getX());
        p.setTranslateY(c.getY());
        allCases.put(p, c);
        Label label = new Label("0");
        label.getStyleClass().add("labels");
        label.setPrefWidth(w);label.setPrefHeight(h);
        label.setTranslateX(c.getX()+c.getWidth()/2-w/2);
        label.setTranslateY(c.getY()+c.getHeight()/2-h/2);
        labels.put(p,label);
    }


    public void setGameView() {
        stage.getScene().getRoot().setVisible(false);
        ((BorderPane) stage.getScene().getRoot()).getChildren().clear();

        //divide pane in 3

        StackPane territory_pane = new StackPane();
        StackPane labels_pane = new StackPane();


        territory_pane.setId("territory-pane");
        territory_pane.setStyle("-fx-pref-width: " + model.getBackgroundImage().getWidth() + ";-fx-pref-height: " + model.getBackgroundImage().getHeight() + ";-fx-alignment: top-left");
        for(Map.Entry<Path,Case> e: allCases.entrySet())
            territory_pane.getChildren().add(e.getKey());
        labels_pane.setId("labels-pane");
        labels_pane.setMouseTransparent(true);
        labels_pane.setFocusTraversable(true);
        labels_pane.setStyle("-fx-pref-width: " + model.getBackgroundImage().getWidth() + ";-fx-pref-height: " + model.getBackgroundImage().getHeight() + ";-fx-alignment: top-left");
        for(Map.Entry<Path,Label> e: labels.entrySet())
            labels_pane.getChildren().add(e.getValue());

        HBox panel = new HBox();
        panel.setId("panel");
        //panel.getChildren().add(game);
        panel.getChildren().add(endTurn);
        panel.getChildren().add(notice);
        panel.getChildren().add(mode);


        game = new StackPane(background,territory_pane,labels_pane);

        Pane limit_buf = new Pane(game);
        limit_buf.setMaxWidth(model.game_view_width);
        limit_buf.setMaxHeight(model.game_view_height);

        ((BorderPane) stage.getScene().getRoot()).setCenter(limit_buf);
        ((BorderPane) stage.getScene().getRoot()).setBottom(panel);

        stage.getScene().getRoot().setVisible(true);
    }

    public void setController(EventHandler<MouseEvent> eh) {
        for (Map.Entry<Path, Case> e : allCases.entrySet())
            e.getKey().setOnMouseClicked(eh);
        endTurn.setOnMouseClicked(eh);
        retour.setOnMouseClicked(eh);
        recommencer.setOnMouseClicked(eh);
    }

    public void actualizeCases() {
        for (Map.Entry<Path, Case> e : allCases.entrySet()) {
            labels.get(e.getKey()).setText(e.getValue().getNbtroupes()+"");
            e.getKey().getStyleClass().clear();
            if(caseOnFocus!=null && caseOnFocus.equals(e.getKey()))
                e.getKey().getStyleClass().add("focus");
            if (e.getValue().getJoueur() != null)
                switch (e.getValue().getJoueur().getCouleur()) {
                    case Joueur.RED:
                        e.getKey().getStyleClass().add(COLOR_RED);
                        break;
                    case Joueur.BLUE:
                        e.getKey().getStyleClass().add(COLOR_BLUE);
                        break;
                    case Joueur.GREEN:
                        e.getKey().getStyleClass().add(COLOR_GREEN);
                        break;
                    case Joueur.YELLOW:
                        e.getKey().getStyleClass().add(COLOR_YELLOW);
                        break;
                    case Joueur.BLACK:
                        e.getKey().getStyleClass().add(COLOR_BLACK);
                        break;
                    default:
                        e.getKey().getStyleClass().add(NEUTRE);
                }
            else
                e.getKey().getStyleClass().add(NEUTRE);
        }
    }

    public void setFinDePartieView(){
        stage.getScene().getRoot().setVisible(false);

        VBox panneau=new VBox();
        panneau.setId("panneauFinPartie");
        HBox bouton = new HBox();
        bouton.setId("bouton");

        panneau.getChildren().add(finDePartie);
        panneau.getChildren().add(message);

        bouton.getChildren().add(retour);
        bouton.getChildren().add(recommencer);

        ((BorderPane) stage.getScene().getRoot()).setCenter(panneau);
        ((BorderPane) stage.getScene().getRoot()).setBottom(bouton);

        stage.getScene().getRoot().setVisible(true);
    }

    public void actualiserAffichage() {
        if(model.map_zoom<model.game_view_width /model.getBackgroundImage().getHeight()) model.map_zoom=model.game_view_width /model.getBackgroundImage().getHeight();
        if(model.map_zoom>1.7) model.map_zoom=1.7;

        game.setScaleX(model.map_zoom);
        game.setScaleY(model.map_zoom);

        if(model.map_translate.x < model.getBackgroundImage().getWidth() * (1-model.map_zoom) / 2) model.map_translate.x = (int) (model.getBackgroundImage().getWidth() * (1-model.map_zoom) / 2);
        if(model.map_translate.x > model.getBackgroundImage().getWidth()*model.map_zoom +model.getBackgroundImage().getWidth() * (1-model.map_zoom) / 2-model.game_view_width) model.map_translate.x = (int) (model.getBackgroundImage().getWidth()*model.map_zoom +model.getBackgroundImage().getWidth() * (1-model.map_zoom) / 2-model.game_view_width);
        if(model.map_translate.y < model.getBackgroundImage().getHeight() * (1-model.map_zoom) / 2) model.map_translate.y = (int) (model.getBackgroundImage().getHeight() * (1-model.map_zoom) / 2);
        if(model.map_translate.y > model.getBackgroundImage().getHeight()*model.map_zoom +model.getBackgroundImage().getHeight() * (1-model.map_zoom) / 2-model.game_view_height) model.map_translate.y = (int) (model.getBackgroundImage().getHeight()*model.map_zoom +model.getBackgroundImage().getHeight() * (1-model.map_zoom) / 2-model.game_view_height);


        game.setTranslateX(-model.map_translate.x);
        game.setTranslateY(-model.map_translate.y);
    }
}
