package Model;

import java.io.Serializable;
import java.util.*;

/**
 * Created by PC-Dylan on 09/11/2016.
 */
public class Joueur implements Serializable{
    private static final long serialVersionUID = 2914403070993434934L;
    public final static int RED=0;
    public final static int BLUE=1;
    public final static int GREEN=2;
    public final static int YELLOW=3;
    public final static int BLACK=4;

    private String nom;
    private int couleur;
    private Set<Case> terrain;
    private int nbRenforts;

    public Joueur(String nom,int couleur){
        this.nom=nom;
        this.couleur=couleur;
        terrain= new HashSet<>();
        nbRenforts=0;
    }

    public void setNom(String nom){
        this.nom=nom;
    }

    public void setCouleur(int couleur){
        this.couleur=couleur;
    }

    public String getNom(){
        return nom;
    }

    public void setNbRenforts(int nbRenforts) {
        this.nbRenforts = nbRenforts;
    }

    public int getNbRenforts() {
        return nbRenforts;
    }

    public Set<Case> getTerrain() {
        return terrain;
    }

    public int getCouleur(){
        return couleur;
    }

    public void gagneTerrain(Case c){
        terrain.add(c);
    }

    public void perdTerrain(Case c){
        terrain.remove(c);
    }


}