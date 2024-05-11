import java.util.ArrayList;
import java.util.UUID;

/**
 * Az ures sivatagi mezo, amelyen sem cso, sem pumpa, sem ciszterna, sem forras nem szerepel.
 * Ha a viz ide kifolyik, akkor a szabotorok kapnak pontot.
 */
public class BlankField extends Field{
    /**
     * referencia teszteleshez (nev amit a create fuggvenyben kapott
     */
    private String referenceID;
    /**
     * A BlankField mindig kicserelheto lesz
     */
    public BlankField(String refID){
        SetReplacable(true);
        referenceID=refID;
    }
    public BlankField(){
        SetReplacable(true);
        referenceID= UUID.randomUUID().toString();
    }

    /**
     * @return tesztelesi referencia
     */
    public String GetReferenceID(){return referenceID;}

    /**
     * A mezo fogadja a rafolyo vizet.
     * @param f mezo, ahonnan folyik a viz
     * @return a mezo tudta-e fogadni a vizet
     */
    public boolean AcceptWater(Field f){
        System.out.println("Blank");
        Game.Get().InceraseSaboteurPoints();
        return true;
    }
    public boolean CanAcceptWater() {
        return false;
    }
    /**
     * Az ures sivatagi mezok nem csinalnak semmit lepesuk soran
     */
    public void Step(){}
    /**
     * lephet-e ra jatekos
     * @return az ures sivatagi mezore sose fogadhat jatekost
     */
    public Boolean CanAcceptPlayer(){
        return false;
    }
    /**
     * kilistazza a szabotor altal elvegezheto akciokat, az ures sivatagi mezon
     * @param saboteur a szabotor aki elvegzi az akciot
     */
    public void SaboteurOptions(Saboteur saboteur){ Wait.Get().Wait(); }
    /**
     * kilistazza a szerelo altal elvegezheto akciokat, az ures sivatagi mezon
     * @param fixer a szerelo aki elvegzi az akciot
     */
    public void FixerOptions(Fixer fixer){ Wait.Get().Wait(); }

    public ArrayList<String> GetStatus(){
        ArrayList<String> list = new ArrayList<String>();
        list.add("BlankField");
        list.add(Integer.toString(Game.Get().GetMap().GetXIndex(this)));
        list.add(Integer.toString(Game.Get().GetMap().GetYIndex(this)));
        int s_number=0, f_number=0;
        for(Player p: GetPlayer()){
            if(p.GetStatus().get(0).equals("fixer")) f_number++;
            if(p.GetStatus().get(0).equals("saboteur")) s_number++;
        }
        list.add(Integer.toString(f_number));
        list.add(Integer.toString(s_number));
        return list;
    }
}

