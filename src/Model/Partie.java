package Model;

import java.io.File;
import java.io.Serializable;
import java.util.*;
/**
 * Created by PC-Dylan on 09/11/2016.
 */
public class Partie implements Serializable{
    final static int CLASSICO = 0;
    final static int RAPIDO = 1;
    final static int THEMEMONDE = 2;
    final static int THEMEAPOCALYPSE=3;
    final static int THEMEGLOOGLOO=4;

    Random random=new Random();
    List <Joueur> joueurs;
    Set <Case> neutres;
    int mode;
    int theme;
    int nbtour;
    Timer tempstour;
    boolean brouillard;
    boolean fin;

    public int getMode() {
        return mode;
    }

    public int getTheme() {
        return theme;
    }

    public int getNbtour() {
        return nbtour;
    }

    public boolean getbrouillard(){
        return brouillard;
    }

    public void setBrouillard(boolean brouillard) {
        this.brouillard = brouillard;
    }

    public void conquerirNeutre(Joueur j, Case c, int nbtroupe){
        j.gagneTerrain(c);
        neutres.remove(c);
        c.setJoueur(j);
        c.setNbtroupes(nbtroupe);
    }

    public void ajouterJoueur(Joueur j){
        joueurs.add(j);
    }

    public void findePartieJoueur(Joueur j){
        if (j.terrain.isEmpty())joueurs.remove(j);
    }

    public void captureTerrainAdverse(Joueur attaquant,Joueur defenseur, Case c, int nbtroupes){
        int nbtrouperestante;
        if (getMode()==CLASSICO){
            nbtrouperestante=attaqueClassique(defenseur,c,nbtroupes);
        }else{
            nbtrouperestante=attaqueRapide(defenseur,c,nbtroupes);
        }
        if (c.getNbtroupes()==0 && nbtrouperestante>0){
            c.setJoueur(attaquant);
            c.setNbtroupes(nbtrouperestante);
            defenseur.perdTerrain(c);
            attaquant.gagneTerrain(c);
        }else{
            c.setNbtroupes(1);
        }
    }

    public int attaqueRapide(Joueur defenseur, Case c, int nbtroupes) {
        /*c'est la meme chose que pour la methode attaqueClassique sauf que cette fois ci
        * on gere le nombre de troupe. Si nbtroupes est plus grand que les troupes sur la case
        * alors la probabilité de gagner est plus grande. on renvoie aussi le nombre de troupe restantes*/
        double chanceAtt=0f;
        double chanceDef=0f;
        if (nbtroupes==c.getNbtroupes()){
            chanceAtt= random.nextDouble();
            chanceDef=random.nextDouble();
            if (chanceAtt>chanceDef){
                nbtroupes= nbtroupes-c.getNbtroupes();
            }
        }else if(nbtroupes>c.getNbtroupes()){
            nbtroupes= nbtroupes-c.getNbtroupes();
        }else if(nbtroupes<c.getNbtroupes()){
            c.setNbtroupes(c.getNbtroupes()-nbtroupes);
            nbtroupes=0;
        }

        return nbtroupes;
    }

    public int attaqueClassique(Joueur defenseur,Case c,int nbtroupeenvoyer) {  //methode qui lance une attaque contre un joueur
        // et renvoie le nombre de troupes restantes a l'attaquant
        while (nbtroupeenvoyer > 0 && c.getNbtroupes() > 0) {
            int[] deAttaquant;
            if (nbtroupeenvoyer >= 3) {
                deAttaquant = new int[3];
            } else if (nbtroupeenvoyer == 2) {
                deAttaquant = new int[2];
            } else {
                deAttaquant = new int[1];
            }

            int[] deDefenseur;
            if (nbtroupeenvoyer >= 2) {
                deDefenseur = new int[2];
            } else {
                deDefenseur = new int[1];
            }

            while (nbtroupeenvoyer > 0 || c.getNbtroupes() < 0) {
                if (nbtroupeenvoyer > c.getNbtroupes()) {
                    int tempo = 6;
                    int totalAttaque = 0, totalDefense = 0;
                    for (int i = 0; i < deAttaquant.length; i++) {
                        deAttaquant[i] = random.nextInt(7);
                        totalAttaque += deAttaquant[i];
                        if (deAttaquant[i] < tempo) tempo = deAttaquant[i];
                    }
                    totalAttaque -= tempo;
                    for (int j = 0; j < deDefenseur.length; j++) {
                        deDefenseur[j] = random.nextInt(7);
                        totalDefense += deDefenseur[j];
                    }
                    if (totalAttaque > totalDefense) c.setNbtroupes(c.getNbtroupes() - (totalAttaque - totalDefense));
                    else nbtroupeenvoyer -= totalDefense - totalAttaque;
                }
            }
        }
        if (c.getNbtroupes() == 0 && nbtroupeenvoyer > 0) return nbtroupeenvoyer;
        else return 0;
    }

    public int calculRenforts(Joueur j){
        /* Pour le moment on ne prend pas en compte les continents*/
        return j.terrain.size();
    }

    public void initialiseSetCasesNeutres(File nomFile){

    }
}