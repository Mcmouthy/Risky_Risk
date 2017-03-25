package Model;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.*;
import java.util.concurrent.CountDownLatch;

import static java.lang.System.exit;

/**
 * Created by yhaffner on 21/01/17.
 *
 */
public class Generateur_v2 extends Application {
    private final static Random loto = new Random();
    private static String name;
    private Image help_img;
    private Image img;
    private Stage stage;
    private StackPane userView;
    private GridPane buttonPanel;
    private ImageView background;
    private double zoom = 1.;
    private Point translate = new Point(0, 0);
    private Button createTerritory;
    private Button createContinent;
    private Button finalizeMap;
    private Continent actualContinent;
    private ArrayList<Point> actualTerritory = new ArrayList<>();
    private ArrayList<Point> allPoints = new ArrayList<>();
    private Color actualColor;
    private StackPane bufferPointsPane;
    private StackPane bufferTerritoryPane;
    private Label shortcuts_info;
    private Label step_title;
    private Label step_description;

    private Node path_on_focus;
    private ArrayList<Node> neighborhood = new ArrayList<>();
    private ArrayList<Node> finishedNodes = new ArrayList<>();


    // final elements
    private HashMap<Path, Case> allIn = new HashMap<>();
    private ArrayList<Continent> continents = new ArrayList<>();


    public static void main(String[] args) {
        Locale.setDefault(Locale.FRANCE);
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        initParameter();
        firstDisplay();
    }

    private void initParameter() {
        stage.hide();
        stage.setFullScreen(false);
        stage.setTitle("Map Creator");
        stage.getIcons().add(new Image(new File("img/icon.png").toURI().toString()));
        stage.setResizable(false);
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 600 + 200, 600, Color.BLACK);
        scene.setOnKeyPressed(new KeyListener());
        stage.setScene(scene);
        stage.sizeToScene();
        stage.centerOnScreen();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                stage.close();
                exit(0);
            }
        });
        stage.show();

        name = askForString("Création d'une map", "Nom:");
        if(name.equals("")) name = "default_map_name";
        File buf = askForImageFile();
        if(buf==null) exit(1);
        img = new Image(buf.toURI().toString());
        if (askForBoolean(stage,"Création d'une map", "Utiliser un fichier d'aide? (image utilisée pour la création mais n'apparaît pas dans le fichier final)")) {
            buf = askForImageFile();
            if(buf==null) help_img = img;
            else help_img = new Image(buf.toURI().toString());
        } else help_img = img;
        MenuListener menuListener = new MenuListener();
        createTerritory = new Button("Créer territoire");
        createTerritory.setOnMouseClicked(menuListener);
        createTerritory.setStyle("-fx-pref-width: 175px");
        createTerritory.setVisible(false);
        createContinent = new Button("Créer continent");
        createContinent.setOnMouseClicked(menuListener);
        createContinent.setStyle("-fx-pref-width: 175px");
        finalizeMap = new Button("Étape suivante");
        finalizeMap.setVisible(false);
        finalizeMap.setOnMouseClicked(menuListener);
        finalizeMap.setStyle("-fx-pref-width: 175px;");
        userView = new StackPane();
        userView.setOnMouseClicked(new MapListener());

        step_title = new Label("CRÉATION DE TERRITOIRES");
        step_title.setStyle("-fx-text-fill: white; -fx-padding:35px 15px 15px 15px;-fx-wrap-text: true;-fx-text-alignment: center;-fx-font-size: 18px;");
        step_description = new Label("Commencez par créer un continent.");
        step_description.setStyle("-fx-text-fill: white; -fx-padding:10px;-fx-wrap-text: true;-fx-text-alignment: center");
        shortcuts_info = new Label("Raccourcis:\n  +  : Se rapprocher\n   -   : S'éloigner\nFlèches :\n         Se déplacer\nCtrl+Z :\n         Annuler la création\n         d'un territoire");
        shortcuts_info.setStyle("-fx-text-fill: white; -fx-padding:7px 7px 17px 7px;-fx-wrap-text: true;-fx-font-size: 11.5px;");
    }

    private void firstDisplay() {
        stage.getScene().getRoot().setVisible(false);

        background = new ImageView(help_img);
        bufferPointsPane = new StackPane();
        bufferPointsPane.setStyle("-fx-pref-width: " + help_img.getWidth() + ";-fx-pref-height: " + help_img.getHeight() + ";-fx-alignment: top-left");
        bufferTerritoryPane = new StackPane();
        bufferTerritoryPane.setStyle("-fx-pref-width: " + help_img.getWidth() + ";-fx-pref-height: " + help_img.getHeight() + ";-fx-alignment: top-left");
        userView.getChildren().addAll(background, bufferPointsPane, bufferTerritoryPane);

        buttonPanel = new GridPane();
        Rectangle r1 = new Rectangle(160, 1);
        r1.setFill(Color.WHITE);
        Rectangle r2 = new Rectangle(160, 1);
        r2.setFill(Color.WHITE);
        buttonPanel.addColumn(0, createContinent, createTerritory, finalizeMap, shortcuts_info, r1, r2, step_title, step_description);
        buttonPanel.setStyle("-fx-background-color: rgb(0,0,0);-fx-hgap: 10px;-fx-vgap: 10px;-fx-alignment: top-center;-fx-padding: 20px;");
        buttonPanel.setPrefWidth(200);

        Pane limit_buf = new Pane(userView);
        limit_buf.setMaxWidth(600);
        limit_buf.setStyle("-fx-background-color: rgb(0,0,0)");

        zoom = 600. / help_img.getHeight();
        if (zoom > 1) zoom = 1;
        translate.x = (int) (help_img.getWidth() / 2 - 300);
        translate.y = (int) (help_img.getHeight() / 2 - 300);
        actualizeDisplay();

        ((BorderPane) stage.getScene().getRoot()).setCenter(limit_buf);
        ((BorderPane) stage.getScene().getRoot()).setRight(buttonPanel);


        stage.getScene().getRoot().setVisible(true);
    }

    private void actualizeDisplay() {
        if (zoom < 600. / help_img.getHeight()) zoom = 600. / help_img.getHeight();
        if (zoom > 1.7) zoom = 1.7;

        userView.setScaleX(zoom);
        userView.setScaleY(zoom);

        if (translate.x < help_img.getWidth() * (1 - zoom) / 2)
            translate.x = (int) (help_img.getWidth() * (1 - zoom) / 2);
        if (translate.x > help_img.getWidth() * zoom + help_img.getWidth() * (1 - zoom) / 2 - 600)
            translate.x = (int) (help_img.getWidth() * zoom + help_img.getWidth() * (1 - zoom) / 2 - 600);
        if (translate.y < help_img.getHeight() * (1 - zoom) / 2)
            translate.y = (int) (help_img.getHeight() * (1 - zoom) / 2);
        if (translate.y > help_img.getHeight() * zoom + help_img.getHeight() * (1 - zoom) / 2 - 600)
            translate.y = (int) (help_img.getHeight() * zoom + help_img.getHeight() * (1 - zoom) / 2 - 600);


        userView.setTranslateX(-translate.x);
        userView.setTranslateY(-translate.y);
    }



    private void creerTerritoire() {
        if (finalizeMap.getText().equals("Enregistrer le fichier")) {
            for (Map.Entry<Path, Case> e1 : allIn.entrySet())
                if (((Path) path_on_focus).getElements().equals(e1.getKey().getElements())) {
                    finishedNodes.add(path_on_focus);
                    for (Node n : neighborhood)
                        for (Map.Entry<Path, Case> e2 : allIn.entrySet())
                            if (((Path) n).getElements().equals(e2.getKey().getElements()))
                                e1.getValue().getVoisins().add(e2.getValue());
                    break;
                }
            createTerritory.setVisible(false);
            for (Node n : bufferTerritoryPane.getChildren())
                if (finishedNodes.contains(n)) ((Path) n).setFill(Color.color(0, 1, 0, 0.5));
                else ((Path) n).setFill(Color.color(1, 1, 1, 0.5));
            neighborhood.clear();

            if (finishedNodes.size() == bufferTerritoryPane.getChildren().size()) {
                finalizeMap.setVisible(true);
                step_description.setText("Cliquez sur 'Enregistrer le fichier' pour générer le fichier map réutilisable.");
            } else
                step_description.setText("Cliquez sur un territoire pour lui attribuer des voisins.\nTerrains en vert: terrains possédant des voisins");
        } else if (createTerritory.getText().equals("Finir territoire")) {
            step_description.setText("Ajoutez des territoires. Lorsque vous avez terminé le continent, cliquez sur 'Finir territoire'.");
            createTerritory.setText("Créer territoire");
            createContinent.setVisible(true);
            bufferPointsPane.getChildren().clear();

            Map.Entry<Path, Case> t = computeTerritory();
            allIn.put(t.getKey(), t.getValue());

            Path clone = new Path(t.getKey().getElements());
            clone.setFill(actualColor);
            clone.setTranslateX(t.getKey().getTranslateX());
            clone.setTranslateY(t.getKey().getTranslateY());
            bufferTerritoryPane.getChildren().add(clone);
        } else if (createTerritory.getText().equals("Créer territoire")) {
            actualTerritory.clear();
            createTerritory.setVisible(false);
            createContinent.setVisible(false);
            createTerritory.setText("Finir territoire");
            step_description.setText("Cliquez sur la map pour placer les points formant le territoire. Un fois le placement terimné, cliquez sur 'Finir territoire'");
        }
    }

    private void creerContinent() {
        if (createContinent.getText().equals("Créer continent")) {
            step_description.setText("Ajoutez des territoires en cliquant sur 'Créer territoire'. Lorsque vous avez terminé le continent, cliquez sur 'Finir continent'.");
            createContinent.setText("Finir continent");
            createTerritory.setVisible(true);
            createContinent.setVisible(false);
            finalizeMap.setVisible(false);
            if (actualContinent == null) actualContinent = new Continent(0, 0);
            else actualContinent = new Continent(actualContinent.getDescripteurContinent() + 1, 0);
            continents.add(actualContinent);
            actualColor = Color.color(loto.nextFloat(), loto.nextFloat(), loto.nextFloat(), 0.5);
        } else {
            createContinent.setText("Créer continent");
            createTerritory.setVisible(false);
            finalizeMap.setVisible(true);
            step_description.setText("Vous pouvez créer un nouveau continent ou bien passer à l'attribution des voisins, en cliquant sur 'Étape suivante'.");
        }
    }

    private void writeFileMap() {
        if (finalizeMap.getText().equals("Étape suivante")) {
            finalizeMap.setText("Enregistrer le fichier");
            finalizeMap.setVisible(false);
            createContinent.setVisible(false);
            createTerritory.setText("Terminer le territoire");
            TerritoryListener territoryListener = new TerritoryListener();
            for (Node e : bufferTerritoryPane.getChildren()) {
                ((Path) e).setFill(Color.color(1, 1, 1, 0.5));
                e.setOnMouseClicked(territoryListener);
            }
            ;
            step_title.setText("ATTRIBUTION DES VOISINS");
            step_description.setText("Cliquez sur un territoire pour lui attribuer des voisins.");
        } else {
            finalizeMap.setVisible(false);
            step_title.setText("GÉNÉRATION DU FICHIER MAP");
            step_description.setText("0%\nÉcriture de l'image de fond...");

            Service<Void> service = new Service<Void>() {
                @Override
                protected Task<Void> createTask() {
                    return new Task<Void>() {
                        @Override
                        protected Void call() throws Exception {
                            final CountDownLatch latch = new CountDownLatch(1);
                            try {
                                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("map/" + name + ".map"));
                                oos.writeInt(allIn.size());
                                int count = 0;
                                for (Case ca : allIn.values()) {
                                    count++;
                                    oos.writeObject(ca);
                                    int finalCount = count;
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                step_description.setText((70 * finalCount / allIn.size()) + "%\nÉcriture des données de la map...");
                                            } finally {
                                                latch.countDown();
                                            }
                                        }
                                    });
                                    latch.await();
                                }

                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            step_description.setText("70%\nÉcriture du des données de la map...");
                                        } finally {
                                            latch.countDown();
                                        }
                                    }
                                });
                                latch.await();

                                ImageIO.write(SwingFXUtils.fromFXImage(img, null), "jpg", oos);

                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            step_description.setText("100%\nFichier créé!");
                                        } finally {
                                            latch.countDown();
                                        }
                                    }
                                });
                                latch.await();
                                File file = new File("map");
                                Desktop desktop = Desktop.getDesktop();
                                desktop.open(file);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            //Keep with the background work
                            return null;
                        }
                    };
                }
            };
            service.start();
        }
    }



    private String askForString(String intitule, String texte) {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Map Creator");
        dialog.setHeaderText(intitule);
        dialog.setContentText(texte);
        dialog.initOwner(stage);

        Optional<String> result = dialog.showAndWait();
        return result.orElse("");
    }

    public static boolean askForBoolean(Stage stage, String intitule, String texte) {
        Optional<ButtonType> result = null;
        Alert alert;
        while (result == null || result.get() == ButtonType.CANCEL) {       //tant qu'on appuie sur la croix rouge, on reaffiche la popup
            alert = new Alert(Alert.AlertType.CONFIRMATION, texte, ButtonType.YES, ButtonType.NO);
            alert.getDialogPane().getChildren().stream().filter(node -> node instanceof Label).forEach(node -> ((Label) node).setMinHeight(Region.USE_PREF_SIZE));
            alert.initOwner(stage);
            alert.setTitle("RISKY RISK");
            alert.setHeaderText(intitule);
            result = alert.showAndWait();
        }
        return result.isPresent() && result.get() == ButtonType.YES;
    }


    public static int askForInt(Stage stage, String intitule, String texte) {
        TextInputDialog dialog = new TextInputDialog("1");
        dialog.setTitle("RISKY RISK");
        dialog.setHeaderText(intitule);
        dialog.setContentText(texte);

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            try {
                return Integer.parseInt(result.get());
            }
            catch(NumberFormatException nfe)
            {
                return -1;
            }
        }
        return 0;
    }

    private File askForImageFile() {
        FileChooser dialogue = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image file (*.png,*.jpg,*.jpeg,*.gif,*.bmp)", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp");
        dialogue.setTitle("Choix de l'image de fond");
        dialogue.getExtensionFilters().add(extFilter);
        dialogue.setInitialDirectory(new File("map"));
        return dialogue.showOpenDialog(stage);
    }

    private HashMap.Entry<Path, Case> computeTerritory() {
        Point min = new Point((int) help_img.getWidth(), (int) help_img.getHeight());
        for (Point pt : actualTerritory) {
            if (pt.x < min.x) min.x = pt.x;
            if (pt.y < min.y) min.y = pt.y;
        }
        Point lastPoint = null;
        Path p = new Path();
        for (Point pt : actualTerritory) {
            if (lastPoint == null) {
                lastPoint = pt;
                p.getElements().add(new MoveTo(pt.x - min.x, pt.y - min.y));
            } else {
                p.getElements().add(new LineTo(pt.x - min.x, pt.y - min.y));

            }
        }
        p.getElements().add(new ClosePath());
        p.setTranslateX(min.x);
        p.setTranslateY(min.y);
        return new AbstractMap.SimpleEntry<Path, Case>(p, new Case(null, 0, min.x, min.y, actualTerritory, actualContinent));
    }


    class KeyListener implements EventHandler<KeyEvent> {
        @Override
        public void handle(KeyEvent event) {
            switch (event.getCode()) {
                case UP:
                    translate.y -= 70;
                    break;
                case DOWN:
                    translate.y += 70;
                    break;
                case LEFT:
                    translate.x -= 70;
                    break;
                case RIGHT:
                    translate.x += 70;
                    break;
                case ADD:
                    zoom += 0.1;
                    break;
                case SUBTRACT:
                    zoom -= 0.1;
                    break;
                case Z:
                    if (event.isShortcutDown() && createTerritory.getText().equals("Créer territoire") && createContinent.getText().equals("Finir continent") && actualTerritory.size() > 0) {
                        Path toRemove = (Path) bufferTerritoryPane.getChildren().get(bufferTerritoryPane.getChildren().size() - 1);
                        Path toRemove2 = null;
                        for (Map.Entry<Path, Case> e : allIn.entrySet())
                            if (e.getKey().getElements().equals(toRemove.getElements()) && e.getValue().getContinent().equals(actualContinent))
                                toRemove2 = e.getKey();
                        if (toRemove2 != null) {
                            allIn.remove(toRemove2);
                            bufferTerritoryPane.getChildren().remove(toRemove);
                            for (int i = 0; i < actualTerritory.size(); i++) allPoints.remove(allPoints.size() - 1);
                        }
                    }
                    ;
                    break;
            }
            actualizeDisplay();
        }
    }

    class MenuListener implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            if (createContinent.equals(event.getSource())) creerContinent();
            else if (createTerritory.equals(event.getSource())) creerTerritoire();
            else if (finalizeMap.equals(event.getSource())) writeFileMap();
        }
    }

    class MapListener implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            if (createTerritory.getText().equals("Finir territoire")) {
                Point click = null;
                for (Point p : allPoints)
                    if (Math.abs(p.x - event.getX()) < 20 && Math.abs(p.y - event.getY()) < 20)
                        click = new Point(p.x, p.y);
                if (click == null) click = new Point((int) event.getX(), (int) event.getY());
                Circle c = new Circle(click.x, click.y, 2);
                c.setTranslateX(c.getCenterX());
                c.setTranslateY(c.getCenterY());
                bufferPointsPane.getChildren().add(c);
                allPoints.add(click);
                actualTerritory.add(click);
                if (actualTerritory.size() > 2) createTerritory.setVisible(true);
            }
        }
    }

    class TerritoryListener implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            Node n = (Node) event.getSource();
            if (step_description.getText().equals("Cliquez sur un territoire pour lui attribuer des voisins.\nTerrains en vert: terrains possédant des voisins") ||
                    step_description.getText().equals("Cliquez sur un territoire pour lui attribuer des voisins.")) {
                path_on_focus = n;
                for (Node ne : bufferTerritoryPane.getChildren()) ((Path) ne).setFill(Color.color(1, 1, 1, 0.5));
                ((Path) path_on_focus).setFill(Color.color(1, 1, 0, 0.5));
                step_description.setText("Sélectionnez les territoires voisins.\nTerrain sélectionné en jaune, voisins en bleu.");
            } else if (!path_on_focus.equals(n)) {
                createTerritory.setVisible(true);
                if (!neighborhood.contains(n)) neighborhood.add(n);
                ((Path) n).setFill(Color.color(0, 1, 1, 0.5));
            }
        }
    }
}