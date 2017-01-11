package com.unrisky.game.Model;

/**
 * Created by PC-Dylan on 09/11/2016.
 */
public class Continent {
    int descripteurContinent;
    int nbbonusrenforts;

    public Continent(int desc,int nb){descripteurContinent=desc; nbbonusrenforts=nb;}

    public void setNbbonusrenforts(int nb){nbbonusrenforts=nb;}

    public void setDescripteurContinent(int descripteurContinent) {
        this.descripteurContinent = descripteurContinent;
    }

    public int getDescripteurContinent() {
        return descripteurContinent;
    }

    public int getNbbonusrenforts(){return nbbonusrenforts;}
}


