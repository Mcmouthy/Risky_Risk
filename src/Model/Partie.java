package Model;

import Controller.Control_Game;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

/**
 * Created by PC-Dylan on 09/11/2016.
 */
public class Partie implements Serializable{
    private static final long serialVersionUID = 2914403070993434934L;
    public final static int CLASSICO = 0;
    public final static int RAPIDO = 1;

    private Random random=new Random();
    private List <Joueur> joueurs;
    private List <Case> neutres;
    private int joueurCourant;
    private int mode;
    private String mapName;
    private int nbtour;
    private Timer tempstour;
    private boolean brouillard;
    private boolean fin;
    private boolean distributionRenforts;
    private boolean attaque_deplacements;
    private Image backgroundImage;
    public double game_view_width;
    public double game_view_height;
    public boolean mute = false;
    public boolean pause = false;

    public double map_zoom = 1.;
    public Point map_translate = new Point(0,0);
    public double old_map_zoom = 1.;
    public Point old_map_translate = new Point(0,0);
    private int[] deAttaquant;
    private int[] deDefenseur;
    private int fightResult;
    public long time_secondes;
    public boolean lanceContinue;
    public Case actualDefCase;
    public Case actualAttCase;

    private ArrayDeque<Case> casesSabotes = new ArrayDeque<>();

    public Partie(String mapName){
        joueurs=new ArrayList<>();
        neutres=new ArrayList<>();
        joueurCourant=0;
        initialiseSetCasesNeutres("map/"+mapName+".map");
        mode=RAPIDO;
        this.mapName = mapName;
        nbtour=1;
        tempstour=new Timer();
        brouillard=false;
        fin=false;
        distributionRenforts=true;
        attaque_deplacements=false;
        lanceContinue=true;


        map_zoom =  game_view_width / backgroundImage.getHeight();
        if(map_zoom>1) map_zoom = 1;
        map_translate.x = (int) (backgroundImage.getWidth() / 2 - game_view_width / 2);
        map_translate.y = (int) (backgroundImage.getHeight() / 2 - game_view_width / 2);
    }

    public Image getBackgroundImage() {
        return backgroundImage;
    }

    public int[] getDeAttaquant() {
        return deAttaquant;
    }

    public int[] getDeDefenseur() {
        return deDefenseur;
    }

    public void setDeAttaquant(int[] deAttaquant) {
        this.deAttaquant = deAttaquant;
    }

    public void setDeDefenseur(int[] deDefenseur) {
        this.deDefenseur = deDefenseur;
    }

    public int getFightResult() {
        return fightResult;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode){ this.mode=mode;}

    public void setMapName(String mapName){this.mapName = mapName;}

    public String getMapName() {
        return mapName;
    }

    public void setJoueurs(List<Joueur> joueurs) {
        this.joueurs = joueurs;
    }

    public void setNbtour(int nbtour) {
        this.nbtour = nbtour;
    }

    public void setNeutres(List<Case> neutres) {
        this.neutres = neutres;
    }

    public void setGame_view_height(double game_view_height) {
        this.game_view_height = game_view_height;
    }

    public void setGame_view_width(double game_view_width) {
        this.game_view_width = game_view_width;
    }

    public void setMute(boolean mute) {
        this.mute = mute;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public void setMap_zoom(double map_zoom) {
        this.map_zoom = map_zoom;
    }

    public double getGame_view_height() {
        return game_view_height;
    }

    public double getGame_view_width() {
        return game_view_width;
    }

    public double getMap_zoom() {
        return map_zoom;
    }

    public int getNbtour() {
        return nbtour;
    }

    public boolean getbrouillard(){
        return brouillard;
    }

    public boolean isDistributionRenforts() {
        return distributionRenforts;
    }

    public boolean isAttaque_deplacements() {
        return attaque_deplacements;
    }

    public void passeEtapeSuivante(){
        if (distributionRenforts){setAttaque_deplacements(true);setDistributionRenforts(false);}
        else{setDistributionRenforts(true);setAttaque_deplacements(false);}
    }

    private void setAttaque_deplacements(boolean attaque_deplacements) {
        this.attaque_deplacements = attaque_deplacements;
    }

    private void setDistributionRenforts(boolean distributionRenforts) {
        this.distributionRenforts = distributionRenforts;
    }

    public void passeJoueurSuivant() {
        this.joueurCourant +=1;
        if (joueurCourant>=joueurs.size()){
            joueurCourant=0;
        }
        while(joueurs.get(joueurCourant).isEliminated()) {
            joueurCourant += 1;
            if (joueurCourant >= joueurs.size()) {
                joueurCourant = 0;
            }
        }
        time_secondes = System.currentTimeMillis();
    }

    public void setFin(boolean fin) {
        this.fin = fin;
    }

    public Joueur getJoueurCourant() {
        return joueurs.get(joueurCourant);
    }

    public List<Case> getNeutres() {
        return neutres;
    }

    public boolean isFin() {
        return fin;
    }

    public boolean isBrouillard() {
        return brouillard;
    }

    public List<Joueur> getJoueurs() {
        return joueurs;
    }

    public Timer getTempstour() {
        return tempstour;
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
        if (j.getTerrain().isEmpty())j.setEliminated(true);
    }

    public void captureTerrainAdverse(Joueur attaquant,Joueur defenseur, Case c, Case caseAttaquant,int nbtroupes){
        int nbtrouperestante;
        if (getMode()==CLASSICO){
            nbtrouperestante=attaqueClassique(c,nbtroupes-1);
            caseAttaquant.setNbtroupes(caseAttaquant.getNbtroupes()-(nbtroupes-1));

        }else{
            nbtrouperestante=attaqueRapide(c,nbtroupes);
            caseAttaquant.setNbtroupes(caseAttaquant.getNbtroupes()-(nbtroupes-1));
        }

        if(getMode()==CLASSICO) {
            if (c.getNbtroupes() > 0 && nbtrouperestante > 0) {
                caseAttaquant.setNbtroupes(caseAttaquant.getNbtroupes() + nbtrouperestante);
            } else if (c.getNbtroupes() == 0 && nbtrouperestante > 0) {
                c.setJoueur(attaquant);
                c.setNbtroupes(nbtrouperestante);
                defenseur.perdTerrain(c);
                attaquant.gagneTerrain(c);
            }
        }else if (getMode()==RAPIDO){
            if (c.getNbtroupes()==0 && nbtrouperestante>0){
                defenseur.perdTerrain(c);
                attaquant.gagneTerrain(c);
                c.setJoueur(attaquant);
                c.setNbtroupes(nbtrouperestante);
            }
        }
    }

    public int attaqueRapide(Case c, int nbtroupes) {
        /*c'est la meme chose que pour la methode attaqueClassique sauf que cette fois ci
        * on gere le nombre de troupe. Si nbtroupes est plus grand que les troupes sur la case
        * alors la probabilité de gagner est plus grande. on renvoie aussi le nombre de troupe restantes*/
        double chanceAtt=0f;
        double chanceDef=0f;
        if(nbtroupes==c.getNbtroupes()){
            chanceAtt=random.nextDouble();
            chanceDef=random.nextDouble();
            if (chanceAtt>chanceDef){
                nbtroupes=1;
                c.setNbtroupes(0);
            }else{
                nbtroupes=0;
                c.setNbtroupes(1);
            }
        }else if(Math.abs(nbtroupes-c.getNbtroupes())==1){
            if (nbtroupes>c.getNbtroupes()){
                chanceAtt=random.nextDouble()+0.50;
                chanceDef=random.nextDouble();
                if (chanceAtt>chanceDef){
                    nbtroupes=nbtroupes-c.getNbtroupes();
                    c.setNbtroupes(0);
                }else{
                    nbtroupes=0;
                    c.setNbtroupes(1);
                }
            }else {
                chanceAtt = random.nextDouble();
                chanceDef = random.nextDouble() + 0.50;
                if (chanceAtt > chanceDef) {
                    nbtroupes = 1;
                    c.setNbtroupes(0);
                } else {
                    c.setNbtroupes(c.getNbtroupes()-nbtroupes);
                    nbtroupes = 0;
                }
            }
        }else if (Math.abs(nbtroupes-c.getNbtroupes())>=2){
            if (nbtroupes>c.getNbtroupes()){
                nbtroupes=nbtroupes-c.getNbtroupes();
                c.setNbtroupes(0);
            }else{
                c.setNbtroupes(c.getNbtroupes()-nbtroupes);
                nbtroupes=0;
            }
        }
        return nbtroupes;
    }

    public int attaqueClassique(Case c,int nbtroupeenvoyer) {
        //methode qui lance une attaque contre un joueur
        // et renvoie le nombre de troupes restantes a l'attaquant
        if (nbtroupeenvoyer >= 3) {
            deAttaquant = new int[3];
        } else if (nbtroupeenvoyer == 2) {
            deAttaquant = new int[2];
        } else {
            deAttaquant = new int[1];
        }
        if (c.getNbtroupes() >= 2) {
            deDefenseur = new int[2];
        } else {
            deDefenseur = new int[1];
        }
        int maxAttaque = 0, maxDefense = 0;
        int moyenAttaque=0, moyenDefense=0;
        deAttaquant[0]=random.nextInt(6)+1;
        maxAttaque=deAttaquant[0];
        if (deAttaquant.length>1) {
            for (int i = 1; i < deAttaquant.length; i++) {
                deAttaquant[i] = random.nextInt(6) + 1;
                moyenAttaque=deAttaquant[i];
                if (deAttaquant[i] > maxAttaque) {
                    moyenAttaque = maxAttaque;
                    maxAttaque = deAttaquant[i];
                }else if (deAttaquant[i]>moyenAttaque){
                    moyenAttaque=deAttaquant[i];
                }
            }
        }

        deDefenseur[0]=random.nextInt(6)+1;
        maxDefense=deDefenseur[0];
        if (deDefenseur.length>1) {
            for (int j = 1; j < deDefenseur.length; j++) {
                deDefenseur[j] = random.nextInt(6) + 1;
                moyenDefense=deDefenseur[j];
                if (deDefenseur[j] > maxDefense) {
                    moyenDefense = maxDefense;
                    maxDefense = deDefenseur[j];
                }else if (deDefenseur[j]>moyenDefense){
                    moyenDefense=deDefenseur[j];
                }
            }
        }

        if (deDefenseur.length == 1) {
            if (maxAttaque > maxDefense) {
                c.setNbtroupes(c.getNbtroupes() - 1);
                if (c.getNbtroupes() <= 0) c.setNbtroupes(0);
                fightResult = 1;
            } else {
                fightResult = -1;
                nbtroupeenvoyer -= 1;
            }
        }

        if (deDefenseur.length>1 && deAttaquant.length>1) {
            if (maxAttaque > maxDefense) {
                c.setNbtroupes(c.getNbtroupes() - 1);
                if (c.getNbtroupes() <= 0) c.setNbtroupes(0);
                fightResult = 1;
            } else {
                fightResult = -1;
                nbtroupeenvoyer -= 1;
            }

            if (moyenAttaque > moyenDefense) {
                c.setNbtroupes(c.getNbtroupes() - 1);
                if (c.getNbtroupes() <= 0) c.setNbtroupes(0);
                fightResult += 1;
            } else {
                fightResult += -1;
                nbtroupeenvoyer -= 1;
            }
            fightResult /= 2;
        }

        if (deDefenseur.length>1 && deAttaquant.length==1) {
            if (maxAttaque > maxDefense) {
                c.setNbtroupes(c.getNbtroupes() - 1);
                if (c.getNbtroupes() <= 0) c.setNbtroupes(0);
            } else {
                nbtroupeenvoyer -= 1;
            }
        }
        return nbtroupeenvoyer;
    }

    public void calculRenforts(Joueur j){
        /* Pour le moment on ne prend pas en compte les continents*/
        j.setNbRenforts(j.getTerrain().size());
    }



    public void initialiseSetCasesNeutres(String nomFile){
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nomFile));
            int nb = ois.readInt();
            for(int i=0;i<nb;i++)
                neutres.add((Case) ois.readObject());
            backgroundImage = SwingFXUtils.toFXImage(ImageIO.read(ois), null);
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void deplacementTroupes(Case caseDepart,Case caseArrivee,int nbtroupes){
        if (caseDepart.getNbtroupes()>1){
            if (caseDepart.getNbtroupes()<nbtroupes)caseDepart.setNbtroupes(caseDepart.getNbtroupes()-(caseDepart.getNbtroupes()-1));
            else caseDepart.setNbtroupes(caseDepart.getNbtroupes()-nbtroupes);
            caseArrivee.setNbtroupes(nbtroupes+caseArrivee.getNbtroupes());
        }
    }

    public int nbjoueurRestant(){
        int nb=0;
        for (Joueur j : joueurs){
            if (!j.isEliminated())nb++;
        }
        return nb;
    }

    public void setPlateauDimensions(double width, double height) {
        game_view_width = width;
        game_view_height = height;
    }

    public void setJoueurCourant(int joueurCourant) {
        this.joueurCourant = joueurCourant;
    }

    public boolean creationFileSave(String nomFile){
        File file=new File(nomFile);
        try {
            if (file.createNewFile()){
                return true;
            }
            return false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void saveStation(String nomFile){
        nomFile+=".save";
        if (creationFileSave(nomFile)) {
            try {
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nomFile));
                oos.writeObject(joueurs);
                oos.writeObject(neutres);
                oos.writeInt(joueurCourant);
                oos.writeInt(mode);
                oos.writeObject(mapName);
                oos.writeInt(nbtour);
                oos.writeBoolean(brouillard);
                oos.writeBoolean(fin);
                oos.writeBoolean(distributionRenforts);
                oos.writeBoolean(attaque_deplacements);
                oos.writeDouble(game_view_width);
                oos.writeDouble(game_view_height);
                oos.writeBoolean(mute);
                oos.writeBoolean(pause);
                oos.writeDouble(map_zoom);
                oos.writeObject(deAttaquant);
                oos.writeObject(deDefenseur);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Partie loadGame(String nomFile){
        Partie partie;
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("save/"+nomFile+".save"));
            List<Joueur> listeJoueur=(List<Joueur>)ois.readObject();
            List<Case> listecase=(List<Case>)ois.readObject();
            int joueurCour=ois.readInt();
            int mode=ois.readInt();
            String name=ois.readObject().toString();
            int nbtours=ois.readInt();
            boolean brouillard=ois.readBoolean();
            boolean fin=ois.readBoolean();
            boolean distri=ois.readBoolean();
            boolean attak=ois.readBoolean();
            double game_width=ois.readDouble();
            double game_height=ois.readDouble();
            boolean mute=ois.readBoolean();
            boolean pause=ois.readBoolean();
            double map=ois.readDouble();
            int[] att=(int[])ois.readObject();
            int[] def=(int[])ois.readObject();

            partie = new Partie(name);
            partie.setJoueurs(listeJoueur);
            partie.setNeutres(listecase);
            partie.setJoueurCourant(joueurCour);
            partie.setMode(mode);
            partie.setNbtour(nbtours);
            partie.setBrouillard(brouillard);
            partie.setFin(fin);
            partie.setDistributionRenforts(distri);
            partie.setAttaque_deplacements(attak);
            partie.setGame_view_width(game_width);
            partie.setGame_view_height(game_height);
            partie.setMute(mute);
            partie.setPause(pause);
            partie.setMap_zoom(map);
            partie.setDeAttaquant(att);
            partie.setDeDefenseur(def);

            return partie;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Méthode qui tire un joueur aléatoire parmi les joueurs jouant
    private Joueur pickJoueur() {
        Joueur joueur;
        int jeSuisUnInt = random.nextInt(joueurs.size());
        joueur = joueurs.get(jeSuisUnInt);
        return joueur;
    }

    //Méthode qui tire une case aléatoire parmi les cases du joueur précedemment tiré
    private Case pickCase(Joueur j) {
        int moiAussiJeSuisUnInt = random.nextInt(j.getTerrain().size());
        return j.getTerrain().get(moiAussiJeSuisUnInt);
    }

    //Méthode qui tire une case aléatoire parmi les voisins de la case précédemment tirée
    public Case pickVoisin(Case c) {
        Set<Case> voisins = c.getVoisins();
        int ohNonUnTroisiemeInt = random.nextInt(voisins.size());
        int i = 0;
        for(Case cas : voisins)
        {
            if (i == ohNonUnTroisiemeInt)
                return cas;
            i++;
        }
        return null;
    }

    //Evenement qui ajoute une troupe
    private int eventRenfort(Joueur j) {
        Case selected = pickCase(j);
        int renfort = Control_Game.loto.nextInt(6)+1;
        if(renfort+selected.getNbtroupes()>24) selected.setNbtroupes(24);
        else selected.setNbtroupes(selected.getNbtroupes()+renfort);
        return renfort;
    }

    //Evenement qui ajoute trois troupes pendant un tour
    private int eventMercenaires(Joueur j) {
        Case selected = pickCase(j);
        int renfort = Control_Game.loto.nextInt(4)*3+7;
        if(renfort+selected.getNbtroupes()>24) selected.setNbtroupes(24);
        else selected.setNbtroupes(selected.getNbtroupes()+renfort);
        return renfort;
    }

    //Un joueur ne peux plus attaquer via une de ses cases pendant un tour
    //La case sabotée est ajotée à la liste des case sabotées
    private Case eventSabotage(Joueur j) {
        Case selected = pickCase(j);
        if(selected.isAbleToAttack) {
            casesSabotes.add(selected);
            selected.isAbleToAttack = false;
        }
        return selected;
    }

    //On remet le nombre de troupes de la case à son nombre initial pour qu'elle puisse attaquer ensuite
    private Case finEventSabotage() {
        Case selected = casesSabotes.poll();
        if(selected!=null) selected.isAbleToAttack = true;
        return selected;
    }

    //Evenement qui enleve deux troupes
    private Case eventDesertion(Joueur j) {
        Case selected = pickCase(j);
        int troupes = selected.getNbtroupes();
        troupes-=2;
        if(troupes<1) troupes =1;
        selected.setNbtroupes(troupes);
        return selected;
    }

    //Evenement qui prend aléatoirement 2 case qui ne sont pas du même joueur pour inverser leur troupes
    private void eventTrahison(Joueur j1, Joueur j2) {
        Case case1 = pickCase(j1);
        Case case2 = pickCase(j2);
        int buf = case1.getNbtroupes();
        case1.setNbtroupes(case2.getNbtroupes());
        case2.setNbtroupes(buf);
    }

    //Evenement qui supprime toutes les troupes présentes sur une case et la rend neutre
    private Joueur eventRevolte(Joueur j) {
        Case selected = pickCase(j);
        selected.setNbtroupes(0);
        j.perdTerrain(selected);
        selected.setJoueur(null);
        neutres.add(selected);
        calculRenforts(j);
        return j;
    }


    public String choixEvenements(){
        int numevents=random.nextInt(7);
        Joueur j=pickJoueur();

        switch (numevents){
            case 0:
                int nb=eventRenfort(j);
                return j.getNom()+": "+nb+" renfort(s) vous ont été attribué(s) !";

            case 1:
                int nb2=eventMercenaires(j);
                return j.getNom()+": "+nb2+" mercenaire(s) vous viennent en aide !";

            case 2:
                eventSabotage(j);
                return "ALERTE ! "+j.getNom()+" : l'une de vos case à été saboté !";

            case 3:
                eventDesertion(j);
                return "ALERTE ! "+j.getNom()+" : des troupes ont déserté !";
            case 4:
                Joueur j2;
                j2= pickJoueur();
                while(j2.equals(j)) j2= pickJoueur();
                eventTrahison(j, j2);
                return "ALERTE ! "+j.getNom()+" : des troupes nous ont trahis !";
            case 5:
                eventRevolte(j);
                return "ALERTE ! "+j.getNom()+" : une révolte a eclaté dans un de vos territoire ! \nCelui-ci est perdu...";

            case 6:
                if(finEventSabotage()!=null)
                    return "Il semblerait que des experts aient sauvé un territoire du sabotage !";
                else
                    return "Des experts ont essayer de sauver un territoire du sabotage, mais aucun territoire n'était saboté. Il ne se passe RIEN.";
        }
        return "";
    }

    public Joueur getLastPlayer() {
        for (Joueur j :
                joueurs) {
            if (!j.isEliminated()) return j;
        }
        return null;
    }
}