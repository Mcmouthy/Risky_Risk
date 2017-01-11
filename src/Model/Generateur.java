package Model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by dylan on 11/01/17.
 */
public class Generateur {
    public static void main(String[] args){
        ArrayList<Case>liste = new ArrayList<>();

        Case case1=new Case(null,0,0,0,100,100,new Continent(0,10));
        Case case2=new Case(null,0,100,0,100,100,new Continent(0,10));
        Case case3=new Case(null,0,200,0,100,100,new Continent(0,10));
        Case case4=new Case(null,0,300,0,100,100,new Continent(0,10));
        Case case5=new Case(null,0,400,0,100,100,new Continent(0,10));
        Case case6=new Case(null,0,0,100,100,100,new Continent(1,10));
        Case case7=new Case(null,0,100,100,100,100,new Continent(1,10));
        Case case8=new Case(null,0,200,100,100,100,new Continent(1,10));
        Case case9=new Case(null,0,300,100,100,100,new Continent(1,10));
        Case case10=new Case(null,0,400,100,100,100,new Continent(1,10));
        Case case11=new Case(null,0,0,200,100,100,new Continent(2,10));
        Case case12=new Case(null,0,100,200,100,100,new Continent(2,10));
        Case case13=new Case(null,0,200,200,100,100,new Continent(2,10));
        Case case14=new Case(null,0,300,200,100,100,new Continent(2,10));
        Case case15=new Case(null,0,400,200,100,100,new Continent(2,10));
        Case case16=new Case(null,0,0,300,100,100,new Continent(3,10));
        Case case17=new Case(null,0,100,300,100,100,new Continent(3,10));
        Case case18=new Case(null,0,200,300,100,100,new Continent(3,10));
        Case case19=new Case(null,0,300,300,100,100,new Continent(3,10));
        Case case20=new Case(null,0,400,300,100,100,new Continent(3,10));

        Set<Case> voisinCase1 = new HashSet<>();
        Set<Case> voisinCase2 = new HashSet<>();
        Set<Case> voisinCase3 = new HashSet<>();
        Set<Case> voisinCase4 = new HashSet<>();
        Set<Case> voisinCase5 = new HashSet<>();
        Set<Case> voisinCase6 = new HashSet<>();
        Set<Case> voisinCase7 = new HashSet<>();
        Set<Case> voisinCase8 = new HashSet<>();
        Set<Case> voisinCase9 = new HashSet<>();
        Set<Case> voisinCase10 = new HashSet<>();
        Set<Case> voisinCase11 = new HashSet<>();
        Set<Case> voisinCase12 = new HashSet<>();
        Set<Case> voisinCase13 = new HashSet<>();
        Set<Case> voisinCase14 = new HashSet<>();
        Set<Case> voisinCase15 = new HashSet<>();
        Set<Case> voisinCase16 = new HashSet<>();
        Set<Case> voisinCase17 = new HashSet<>();
        Set<Case> voisinCase18 = new HashSet<>();
        Set<Case> voisinCase19 = new HashSet<>();
        Set<Case> voisinCase20 = new HashSet<>();

        voisinCase1.add(case2);voisinCase1.add(case6);
        voisinCase2.add(case1);voisinCase1.add(case3);voisinCase2.add(case7);
        voisinCase3.add(case2);voisinCase3.add(case4);voisinCase3.add(case8);
        voisinCase4.add(case3);voisinCase4.add(case5);voisinCase4.add(case9);
        voisinCase5.add(case4);voisinCase5.add(case10);
        voisinCase6.add(case1);voisinCase6.add(case7);voisinCase6.add(case11);
        voisinCase7.add(case2);voisinCase7.add(case6);voisinCase7.add(case8);voisinCase7.add(case12);
        voisinCase8.add(case3);voisinCase8.add(case7);voisinCase8.add(case9);voisinCase8.add(case13);
        voisinCase9.add(case4);voisinCase9.add(case8);voisinCase9.add(case10);voisinCase9.add(case14);
        voisinCase10.add(case5);voisinCase10.add(case9);voisinCase10.add(case15);
        voisinCase11.add(case6);voisinCase11.add(case12);voisinCase11.add(case16);
        voisinCase12.add(case7);voisinCase12.add(case11);voisinCase12.add(case13);voisinCase12.add(case17);
        voisinCase13.add(case8);voisinCase13.add(case12);voisinCase13.add(case14);voisinCase13.add(case18);
        voisinCase14.add(case9);voisinCase14.add(case13);voisinCase14.add(case15);voisinCase14.add(case19);
        voisinCase15.add(case16);voisinCase15.add(case14);voisinCase15.add(case20);
        voisinCase16.add(case11);voisinCase16.add(case17);
        voisinCase17.add(case12);voisinCase17.add(case16);voisinCase17.add(case18);
        voisinCase18.add(case13);voisinCase18.add(case17);voisinCase18.add(case19);
        voisinCase19.add(case14);voisinCase19.add(case18);voisinCase19.add(case20);
        voisinCase20.add(case15);voisinCase20.add(case19);

        case1.setVoisins(voisinCase1);
        case2.setVoisins(voisinCase2);
        case3.setVoisins(voisinCase3);
        case4.setVoisins(voisinCase4);
        case5.setVoisins(voisinCase5);
        case6.setVoisins(voisinCase6);
        case7.setVoisins(voisinCase7);
        case8.setVoisins(voisinCase8);
        case9.setVoisins(voisinCase9);
        case10.setVoisins(voisinCase10);
        case11.setVoisins(voisinCase11);
        case12.setVoisins(voisinCase12);
        case13.setVoisins(voisinCase13);
        case14.setVoisins(voisinCase14);
        case15.setVoisins(voisinCase15);
        case16.setVoisins(voisinCase16);
        case17.setVoisins(voisinCase17);
        case18.setVoisins(voisinCase18);
        case19.setVoisins(voisinCase19);
        case20.setVoisins(voisinCase20);

        liste.add(case1);
        liste.add(case2);
        liste.add(case3);
        liste.add(case4);
        liste.add(case5);
        liste.add(case6);
        liste.add(case7);
        liste.add(case8);
        liste.add(case9);
        liste.add(case10);
        liste.add(case11);
        liste.add(case12);
        liste.add(case13);
        liste.add(case14);
        liste.add(case15);
        liste.add(case16);
        liste.add(case17);
        liste.add(case18);
        liste.add(case19);
        liste.add(case20);
        liste.add(null);

        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("src/Model/TerrainBase"));
            for (Case c:liste){
                oos.writeObject(c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Travail terminé !!!");
    }
}
