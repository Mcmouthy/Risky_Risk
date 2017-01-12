package Model;

/**
 * Created by dylan on 12/01/17.
 */
public class TestPartieRecupNeutre {
    public static void main(String[] args){
        Partie partie = new Partie();
        partie.initialiseSetCasesNeutres("src/Model/TerrainBase");
        for (Case c : partie.getNeutres())System.out.println("case recuperée");
        System.out.println(partie.getNeutres().size()+"");
        return;
    }
}
