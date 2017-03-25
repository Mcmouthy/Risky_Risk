package Model;

import Controller.Control_Game;
import com.sun.javafx.geom.Vec2d;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.awt.*;
import java.io.File;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by yhaffner on 10/02/17.
 */
public class Dices {
    private final ArrayList<Node> savedElements;
    private StackPane plateau;
    private MeshView[][] dices;
    private Vec2d[][] dicesDirection;
    private Timeline[][] dicesTimer;
    private int[][] dicesPhase;
    private int[][] dicesFinalFace;
    private boolean[][] dicesIsEntered;
    private Label resultat;

    private static int cubeSize = 100;
    private int decalage = 300;

    public Dices(StackPane pane) {
        plateau = pane;
        plateau.setVisible(false);

        savedElements = new ArrayList<>(plateau.getChildren());
        resultat = new Label();
        resultat.setVisible(false);
        resultat.setId("dice_result");
        createAllDices();
    }

    private void createAllDices() {
        dices = new MeshView[2][3];
        for (int i = 0; i < 3; i++) {
            dices[0][i] = createDice("red");
        }
        for (int i = 0; i < 3; i++) {
            dices[1][i] = createDice("blue");
        }
        dicesDirection = new Vec2d[2][3];
        dicesPhase = new int[2][3];
        dicesFinalFace = new int[2][3];
        dicesTimer = new Timeline[2][3];
        dicesIsEntered = new boolean[2][3];
    }

    private static MeshView createDice(String s) {
        float hw = cubeSize / 2f;
        float hh = cubeSize / 2f;
        float hd = cubeSize / 2f;


        TriangleMesh mesh = new TriangleMesh();
        mesh.getPoints().addAll(
                hw, hh, hd,
                hw, hh, -hd,
                hw, -hh, hd,
                hw, -hh, -hd,
                -hw, hh, hd,
                -hw, hh, -hd,
                -hw, -hh, hd,
                -hw, -hh, -hd
        );
        mesh.getTexCoords().addAll(
                    /*X*/       /*Y*/
                1f / 4f, 0f / 3f,
                2f / 4f, 0f / 3f,
                0f / 4f, 1f / 3f,
                1f / 4f, 1f / 3f,
                2f / 4f, 1f / 3f,
                3f / 4f, 1f / 3f,
                4f / 4f, 1f / 3f,
                0f / 4f, 2f / 3f,
                1f / 4f, 2f / 3f,
                2f / 4f, 2f / 3f,
                3f / 4f, 2f / 3f,
                4f / 4f, 2f / 3f,
                1f / 4f, 3f / 3f,
                2f / 4f, 3f / 3f
        );

        mesh.getFaces().addAll(
                0, 10, 2, 5, 1, 9,
                2, 5, 3, 4, 1, 9,
                4, 7, 5, 8, 6, 2,
                6, 2, 5, 8, 7, 3,
                0, 13, 1, 9, 4, 12,
                4, 12, 1, 9, 5, 8,
                2, 1, 6, 0, 3, 4,
                3, 4, 6, 0, 7, 3,
                0, 10, 4, 11, 2, 5,
                2, 5, 4, 11, 6, 6,
                1, 9, 3, 4, 5, 8,
                5, 8, 3, 4, 7, 3
        );

        mesh.getFaceSmoothingGroups().addAll(
                0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5
        );

        MeshView cube = new MeshView(mesh);
        PhongMaterial p = new PhongMaterial();
        p.setDiffuseMap(new Image(new File("img/" + (s.equals("red") ? "red" : "blue") + "_dice.jpg").toURI().toString()));


        cube.setMaterial(p);
        return cube;
    }

    public static MeshView getASimpleDiceRolling(String s) {
        MeshView cube = createDice(s);
        cube.setRotationAxis(new Point3D(56, 23, 60));
        cube.setRotate(20);

        (new Timer()).schedule(new TimerTask() {
            @Override
            public void run() {
                if (cube == null) this.cancel();
                else if (cube.isVisible()) cube.setRotate(cube.getRotate() + 2);
            }
        }, 5, 30);

        return cube;
    }


    public void launchDices(int[] desRouges, int[] desBleus, int victory) {
        plateau.setVisible(true);
        Platform.runLater(() -> {
            plateau.getChildren().clear();
            plateau.getChildren().addAll(savedElements);

            for (MeshView[] t : dices) for (MeshView m : t) {m.setVisible(false);m.setTranslateX(10000);m.setTranslateY(10000);}
            for (int i = 0; i < desRouges.length; i++) {
                plateau.getChildren().add(dices[0][i]);
                dices[0][i].setTranslateY(-(plateau.getHeight() - 100) / 2 - cubeSize);
                dices[0][i].setTranslateX(0.5 * (-cubeSize + (2 * i - (desRouges.length - 1)) * decalage));
                dices[0][i].getTransforms().clear();
                dices[0][i].getTransforms().add(new Rotate(0, new Point3D(Control_Game.loto.nextDouble() * 180, Control_Game.loto.nextDouble() * 180, Control_Game.loto.nextDouble() * 180)));
                dicesDirection[0][i] = new Vec2d(Control_Game.loto.nextDouble() * 2 - 1, 1);
                dicesFinalFace[0][i] = desRouges[i];
                dicesIsEntered[0][i] = false;
                dicesPhase[0][i] = 0;
            }
            for (int i = 0; i < desBleus.length; i++) {
                plateau.getChildren().add(dices[1][i]);
                dices[1][i].setTranslateY(plateau.getHeight() / 2);
                dices[1][i].setTranslateX(0.5 * (-cubeSize + (2 * i - (desBleus.length - 1)) * decalage));
                dices[1][i].getTransforms().clear();
                dices[1][i].getTransforms().add(new Rotate(0, new Point3D(Control_Game.loto.nextDouble() * 180, Control_Game.loto.nextDouble() * 180, Control_Game.loto.nextDouble() * 180)));
                dicesDirection[1][i] = new Vec2d(Control_Game.loto.nextDouble() * 2 - 1, -1);
                dicesFinalFace[1][i] = desBleus[i];
                dicesIsEntered[1][i] = false;
                dicesPhase[1][i] = 0;
            }
            for (MeshView[] t : dices) for (MeshView m : t) m.setVisible(true);
            plateau.getChildren().add(resultat);
            plateau.setStyle("-fx-background-color: rgba(0,0,0,0.85)");
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 3; j++) {
                    int finalJ1 = j;
                    int finalI1 = i;
                    if ((finalI1 == 0 && finalJ1 < desRouges.length) || (finalI1 == 1 && finalJ1 < desBleus.length)) {
                        //(new Thread(() -> {

                        dicesTimer[finalI1][finalJ1] = new Timeline(new KeyFrame(
                                Duration.millis(30),
                                ae -> animateForDice(finalI1, finalJ1)));
                        dicesTimer[finalI1][finalJ1].setCycleCount(Animation.INDEFINITE);
                        dicesTimer[finalI1][finalJ1].play();
                    }
                }
            }
            Timeline timeline = new Timeline();
            timeline.getKeyFrames().add(new KeyFrame(
                    Duration.millis(60),
                    (ActionEvent aes) -> {
                        boolean letsgo = true;
                        for (boolean[] t : dicesIsEntered) for (boolean b : t) if (b) letsgo = false;
                        if (letsgo) {
                            switch (victory) {
                                case -1:
                                    resultat.setText("ÉCHEC");
                                    resultat.setStyle("-fx-text-fill: darkred");
                                    break;
                                case 0:
                                    resultat.setText("ÉGALITÉ");
                                    resultat.setStyle("-fx-text-fill: black");
                                    break;
                                case 1:
                                    resultat.setText("RÉUSSITE");
                                    resultat.setStyle("-fx-text-fill: darkgreen");
                                    break;
                            }
                            (new Thread(() -> {
                                Timeline ti = new Timeline(new KeyFrame(
                                        Duration.millis(400),
                                        ase -> resultat.setVisible(!resultat.isVisible())
                                ));
                                ti.setCycleCount(4);
                                ti.play();
                            })).start();
                            (new Thread(() -> (new Timer()).schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    plateau.setVisible(false);
                                }
                            }, Date.from(Instant.now().plusMillis(1600))))).start();
                            timeline.stop();
                        }
                    }
            ));
            timeline.setDelay(Duration.seconds(2));
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();
        });
        waitForEndOfLaunch();
    }

    private void animateForDice(int i, int j) {
        if (dices[i][j].getTranslateY() < (plateau.getHeight() - 100) / 2 - 100 && dices[i][j].getTranslateY() > -(plateau.getHeight() - 100) / 2)
            dicesIsEntered[i][j] = true;
        if (dicesIsEntered[i][j]) {
            if (dicesPhase[i][j] < 4 && Control_Game.loto.nextInt(100) < 4) {
                dicesPhase[i][j]++;
                if (dicesPhase[i][j] == 4) {
                    dicesPhase[i][j] = (int) plateau.getWidth() * 64;
                    stabilizeDice((Rotate) dices[i][j].getTransforms().get(0), dicesFinalFace[i][j]);
                }
            }

            if (dices[i][j].getTranslateY() > (plateau.getHeight() - 100) / 2 - 100 || dices[i][j].getTranslateY() < -(plateau.getHeight() - 100) / 2) {
                dicesDirection[i][j].y = -dicesDirection[i][j].y;
                if(dices[i][j].getTranslateY() > (plateau.getHeight() - 100) / 2 - 100) dices[i][j].setTranslateY((plateau.getHeight() - 100) / 2 - 100);
                else dices[i][j].setTranslateY(-(plateau.getHeight() - 100) / 2);
            }
            if (dices[i][j].getTranslateX() > (plateau.getWidth() - 100) / 2 || dices[i][j].getTranslateX() < -(plateau.getWidth() - 100) / 2) {
                dicesDirection[i][j].x = -dicesDirection[i][j].x;
                if (dices[i][j].getTranslateX() > (plateau.getWidth() - 100) / 2)  dices[i][j].setTranslateX((plateau.getWidth() - 100) / 2);
                else dices[i][j].setTranslateX(-(plateau.getWidth() - 100) / 2);
            }

            for (int i2 = 0; i2 < dices.length; i2++)
                for (int j2 = 0; j2 < dices[i2].length; j2++)
                    if (!dices[i][j].equals(dices[i2][j2]) &&
                            (Math.abs(dices[i][j].getTranslateX() - dices[i2][j2].getTranslateX()) < 100 && Math.abs(dices[i][j].getTranslateY() - dices[i2][j2].getTranslateY()) < 100)) {

                        //double angleDirectionDeActuel = Math.atan(dicesDirection[i][j].y / dicesDirection[i][j].x);
                        //double angleDirectionSecondDe = Math.atan(dicesDirection[i2][j2].y / dicesDirection[i2][j2].x);
                        //double angleCollision = Math.atan((dices[i][j].getTranslateY()-dices[i2][j2].getTranslateY()) / (dices[i][j].getTranslateX()-dices[i2][j2].getTranslateX()));
                        //double bufNewDirection = (angleDirectionDeActuel+Math.PI+)

                        if (dicesPhase[i][j] > 3 || dicesPhase[i2][j2] > 3 || dicesTimer[i2][j2].getStatus()==Timeline.Status.STOPPED) {
                            if (Math.abs(dices[i][j].getTranslateX() - dices[i2][j2].getTranslateX()) < Math.abs(dices[i][j].getTranslateY() - dices[i2][j2].getTranslateY())) {
                                dices[i][j].setTranslateX(
                                        dices[i][j].getTranslateX() +
                                                (100 - Math.abs(dices[i][j].getTranslateX() - dices[i2][j2].getTranslateX()))
                                        * (dices[i][j].getTranslateX() - dices[i2][j2].getTranslateX())/Math.abs(dices[i][j].getTranslateX() - dices[i2][j2].getTranslateX())
                                );
                                dicesDirection[i][j].x = -dicesDirection[i][j].x;
                            } else {
                                dices[i][j].setTranslateY(dices[i][j].getTranslateY() + (100 - Math.abs(dices[i][j].getTranslateX() - dices[i2][j2].getTranslateX()))
                                        * (dices[i][j].getTranslateX() - dices[i2][j2].getTranslateX())/Math.abs(dices[i][j].getTranslateX() - dices[i2][j2].getTranslateX()));
                                dicesDirection[i][j].y = -dicesDirection[i][j].y;
                            }
                        } else {
                            Vec2d buf = new Vec2d(dicesDirection[i][j]);
                            dicesDirection[i][j] = new Vec2d(dicesDirection[i2][j2]);
                            dicesDirection[i2][j2] = new Vec2d(buf);
                            int buf2 = dicesPhase[i][j];
                            dicesPhase[i][j] = dicesPhase[i2][j2];
                            dicesPhase[i2][j2] = buf2;

                            if (Math.abs(dices[i][j].getTranslateX() - dices[i2][j2].getTranslateX()) < Math.abs(dices[i][j].getTranslateY() - dices[i2][j2].getTranslateY()))
                                dices[i][j].setTranslateX(dices[i][j].getTranslateX() + (100 - Math.abs(dices[i][j].getTranslateX() - dices[i2][j2].getTranslateX()))
                                        * (dices[i][j].getTranslateX() - dices[i2][j2].getTranslateX())/Math.abs(dices[i][j].getTranslateX() - dices[i2][j2].getTranslateX()));
                            else
                                dices[i][j].setTranslateY(dices[i][j].getTranslateY() + (100 - Math.abs(dices[i][j].getTranslateX() - dices[i2][j2].getTranslateX()))
                                        * (dices[i][j].getTranslateX() - dices[i2][j2].getTranslateX())/Math.abs(dices[i][j].getTranslateX() - dices[i2][j2].getTranslateX()));
                        }
                        break;
                    }
        }
        switch (dicesPhase[i][j]) {
            case 0:
                dices[i][j].setTranslateX(dices[i][j].getTranslateX() + dicesDirection[i][j].x * plateau.getWidth() / 30);
                dices[i][j].setTranslateY(dices[i][j].getTranslateY() + dicesDirection[i][j].y * plateau.getHeight() / 30);
                ((Rotate) dices[i][j].getTransforms().get(0)).setAngle(((Rotate) dices[i][j].getTransforms().get(0)).getAngle() + 258.2);
                break;
            case 1:
                dices[i][j].setTranslateX(dices[i][j].getTranslateX() + dicesDirection[i][j].x * plateau.getWidth() / 120);
                dices[i][j].setTranslateY(dices[i][j].getTranslateY() + dicesDirection[i][j].y * plateau.getHeight() / 120);
                ((Rotate) dices[i][j].getTransforms().get(0)).setAngle(((Rotate) dices[i][j].getTransforms().get(0)).getAngle() + 100.1);
                break;
            case 2:
                dices[i][j].setTranslateX(dices[i][j].getTranslateX() + dicesDirection[i][j].x * plateau.getWidth() / 320);
                dices[i][j].setTranslateY(dices[i][j].getTranslateY() + dicesDirection[i][j].y * plateau.getHeight() / 320);
                ((Rotate) dices[i][j].getTransforms().get(0)).setAngle(((Rotate) dices[i][j].getTransforms().get(0)).getAngle() + 49.1);
                break;
            case 3:
                dices[i][j].setTranslateX(dices[i][j].getTranslateX() + dicesDirection[i][j].x * plateau.getWidth() / 720);
                dices[i][j].setTranslateY(dices[i][j].getTranslateY() + dicesDirection[i][j].y * plateau.getHeight() / 720);
                ((Rotate) dices[i][j].getTransforms().get(0)).setAngle(((Rotate) dices[i][j].getTransforms().get(0)).getAngle() + 23.7);
                break;
            default:
                dices[i][j].setTranslateX(dices[i][j].getTranslateX() + dicesDirection[i][j].x * (plateau.getWidth() / 720) * dicesPhase[i][j] / (plateau.getWidth() * 64));
                dices[i][j].setTranslateY(dices[i][j].getTranslateY() + dicesDirection[i][j].y * (plateau.getHeight() / 720) * dicesPhase[i][j] / (plateau.getWidth() * 64));
                dicesPhase[i][j] /= 1.2;
                if (dicesPhase[i][j] < 8) {
                    dicesTimer[i][j].stop();
                    dicesIsEntered[i][j] = false;
                }
                break;
        }
    }

    private void stabilizeDice(Rotate rotate, int result) {
        switch (result) {
            case 1:
                rotate.setAxis(new Point3D(1, 0, 0));
                rotate.setAngle(-90);
                break;
            case 2:
                rotate.setAxis(new Point3D(0, 1, 0));
                rotate.setAngle(180);
                break;
            case 3:
                rotate.setAxis(new Point3D(0, 1, 0));
                rotate.setAngle(90);
                break;
            case 4:
                rotate.setAxis(new Point3D(0, 1, 0));
                rotate.setAngle(-90);
                break;
            case 5:
                rotate.setAxis(new Point3D(0, 0, 0));
                rotate.setAngle(0);
                break;
            case 6:
                rotate.setAxis(new Point3D(1, 0, 0));
                rotate.setAngle(90);
                break;
        }
    }


    private void waitForEndOfLaunch() {

        while (plateau.isVisible())
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }

    public StackPane getPlateau() {
        return plateau;
    }
}