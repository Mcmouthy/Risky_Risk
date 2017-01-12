package Model;

import java.io.Serializable;
import java.util.*;

/**
 * Created by PC-Dylan on 09/11/2016.
 */
public class Joueur implements Serializable{
    private static final long serialVersionUID = 2914403070993434934L;
    final static int RED=0;
    final static int BLUE=1;
    final static int GREEN=2;
    final static int YELLOW=3;
    final static int BLACK=4;

    String nom;
    int couleur;
    Set<Case> terrain;

    public Joueur(String nom,int couleur){
        this.nom=nom;
        this.couleur=couleur;
        terrain= new HashSet<Case>();
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

    public int getCouleur(){
        return couleur;
    }

    public void gagneTerrain(Case c){
        terrain.add(c);
    }

    public void perdTerrain(Case c){
        terrain.remove(c);
    }

    public void calculRenforts(){
        int nbCase= terrain.size();

    }


}