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

    private String nom;
    private int couleur;
    private Set<Case> terrain;
    private boolean distributionRenforts;
    private boolean attaque_deplacements;
    private int nbRenforts;

    public Joueur(String nom,int couleur){
        this.nom=nom;
        this.couleur=couleur;
        terrain= new HashSet<>();
        distributionRenforts=false;
        attaque_deplacements=false;
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

    public void setAttaque_deplacements(boolean attaque_deplacements) {
        this.attaque_deplacements = attaque_deplacements;
    }

    public void setDistributionRenforts(boolean distributionRenforts) {
        this.distributionRenforts = distributionRenforts;
    }

    public void setNbRenforts(int nbRenforts) {
        this.nbRenforts = nbRenforts;
    }

    public int getNbRenforts() {
        return nbRenforts;
    }

    public boolean isDistributionRenforts() {
        return distributionRenforts;
    }

    public boolean isAttaque_deplacements() {
        return attaque_deplacements;
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

    public void calculRenforts(){
        int nbCase= terrain.size();

    }


}