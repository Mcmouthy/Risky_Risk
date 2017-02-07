package Model;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.awt.*;
import java.io.Serializable;
import java.util.*;

/**
 * Created by PC-Dylan on 09/11/2016.
 */
public class Case implements Serializable{
    private static final long serialVersionUID = 2914403070993434934L;
    private Joueur joueur;
    private int nbtroupes;
    private int x,y,width,height;
    private ArrayList<Point> points;
    private Set<Case> voisins;
    private Continent continent;

    public Case(Joueur joueur,int nbtroupes,int x,int y,ArrayList<Point> points,Continent continent){
        this.joueur=joueur;
        this.nbtroupes=nbtroupes;
        this.x=x;
        this.y=y;
        this.points = (ArrayList<Point>) points.clone();
        voisins=new HashSet<Case>();
        this.continent=continent;

        int hmax=0,vmax=0;
        for(Point pt:this.points) {
            if(pt.x>hmax) hmax=pt.x;
            if(pt.y>vmax) vmax=pt.y;
        }
        this.width = hmax-x;
        this.height = vmax-y;
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

    public void setPoints(ArrayList<Point> points) {
        this.points = points;
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
    public int getHeight() {
        return height;
    }
    public int getWidth() {
        return width;
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public void setVoisins(Set<Case> voisins) {
        this.voisins = voisins;
    }

    public Set<Case> getVoisins() {
        return voisins;
    }

    public void addRenforts(){
        nbtroupes++;
    }

    public String toString(){
        return ""+joueur;
    }
}