package com.unrisky.game.Model;

import java.util.*;

/**
 * Created by PC-Dylan on 09/11/2016.
 */
public class Case {
    Joueur joueur;
    int nbtroupes;
    int x,y,width,height;
    Set<Case> voisins;
    Continent continent;

    public Case(Joueur joueur,int nbtroupes,int x,int y,int width,int height,Continent continent){
        this.joueur=joueur;
        this.nbtroupes=nbtroupes;
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        voisins=new HashSet<Case>();
        this.continent=continent;
    }

    public void setJoueur(Joueur joueur) {
        this.joueur = joueur;
    }

    public void setNbtroupes(int nbtroupes) {
        this.nbtroupes = nbtroupes;
    }

    public void setContinent(Continent continent) {
        this.continent = continent;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Joueur getJoueur() {
        return joueur;
    }

    public int getNbtroupes() {
        return nbtroupes;
    }

    public Continent getContinent() {
        return continent;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void compteVoisins(){}

    public void addRenforts(){}
}
