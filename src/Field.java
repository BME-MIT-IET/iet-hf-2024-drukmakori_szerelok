import java.util.ArrayList;
import java.util.List;

/**
 * absztrakt ososztaly minden mezonek.
 */
public abstract class Field {

    private String performAction;
    /**
     * referencia teszteleshez (nev amit a create fuggvenyben kapott
     */
    private String referenceID;
    /**
     * felveheto-e a mezo
     */
    private boolean removable = false;
    /**
     * lecserelheto-e a mezo
     */
    private boolean replacable = false;
    /**
     * van-e benne viz
     */
    private boolean hasWater = false;
    /**
     * rajta allo jatekosok
     */
    private List<Player> players;
    private boolean beenStepped = false;

    public void SetBeenStepped(boolean b) {
        beenStepped = b;
    }
    public boolean GetBeenStepped() {
        return beenStepped;
    }

    public abstract String GetReferenceID();

    /**
     * jatekos lista letrehozasa
     */
    public Field(){
        players = new ArrayList<>();
    }
    /**
     * absztrakt, az adott objektum kor vegi lepese
     */
    public abstract void Step();
    /**
     * visszaadja a jatekosok listajat
     * @return a jatekosok listaja
     */
    public List<Player> GetPlayer() {
        return players;
    }
    /**
     * beallitja a jatekosok listajat
     * @param p a jatekosok list
     */
    public void SetPlayer(List<Player> p) {
        players = p;
    }
    /**
     * kicsereli a mezot egy masikkal
     * @param fx szerelo, aki cserel
     * @param f mezo, amire cserelunk
     */
    public void ReplaceByFixer(Fixer fx, Field f){
        Map map = Game.Get().GetMap();
        map.ReplaceField(f, fx.GetActive());
        fx.DecreaseActionPoints();
        fx.SetActive(null);
        fx.SetHasActive(false);
    }
    /**
     * megadja, hogy van-e benne viz
     * @return hasWater erteke
     */
    public Boolean GetHasWater(){
        return hasWater;
    }
    /**
     * beallitja hogy van-e benne víz
     * @param b hasWater erteke
     */
    public void SetHasWater(Boolean b){
        hasWater = b;
    }
    /**
     * absztrakt fuggveny, mezo fogadja a vizet
     * @param f mezo, ahonnan fogadja a vizet
     */
    public abstract boolean AcceptWater(Field f);
    public boolean CanAcceptWater() {
        return true;
    }
    /**
     * ralepteti a jatekost
     * @param p jatekos, aki ralep
     */
    public void AddPlayer(Player p){
        players.add(p);
        p.SetField(this);
    }
    /**
     * leveszi magarol a jatekost
     * @param p jatekos, aki lelep
     */
    public void RemovePlayer(Player p){
        players.remove(p);
    }
    /**
     * elfogadja a jatekost
     */
    public void AcceptPlayer(){
        Player player = Game.Get().GetActivePlayer();
        player.DecreaseActionPoints();
        AddPlayer(player);
    }
    /**
     * lephet-e ra jatekos
     * @return tudja-e fogadni a jatekost
     */
    public Boolean CanAcceptPlayer(){
        return true;
    }
    /**
     * visszadja removable erteket
     * @return felveheto-e a mezo
     */
    public boolean GetRemovable(){
        return removable;
    }
    /**
     * beallitja a removable erteket
     * @param b felveheto-e a mezo
     */
    public void SetRemovable(boolean b){
        removable=b;
    }
    /**
     * visszadja replacable erteket
     * @return lecserelheto-e a mezo
     */
    public boolean GetReplacable(){
        return replacable;
    }
    /**
     * beallitja a replacable erteket
     * @param b lecserelheto-e a mezo
     */
    public void SetReplacable(boolean b){
        replacable=b;
    }

    /**
     * bejovo mezo beallitasa
     * @param f bejovo mezo
     */
    public void SetPIn(Field f) {}
    /**
     * kimeneti mezo beallitasa
     * @param f kimeneti mezo
     */
    public void SetPOut(Field f) {}
    /**
     * bejovo mezo lekerdezese
     * @return bejovo mezo
     */
    public Field GetPIn() {return null;}

    /**
     * kimeneti mezo lekerdezese
     * @return
     */
    public Field GetPOut() {return null;}
    /**
     * absztrakt metodus, kilistazza a szabotor altal elvegezheto akciokat
     */
    public abstract void SaboteurOptions(Saboteur saboteur);
    /**
     * absztrakt metodus, kilistazza a szerelo altal elvegezheto akciokat
     */
    public abstract void FixerOptions(Fixer fixer);
    /**
     * beallitja az elem iranyat, alapbol nem csinal semmit
     * @param in be, vagy kimenetkent allitjuk be az elemet
     */
    public void SetDirection(boolean in, Field a){ }

    /**
     * absztrakt metodus, visszaadja a mezo statuszat
     * @return a mezo statusza
     */
     public abstract ArrayList<String> GetStatus();

    /**
     * beallitja a vegrehajtando akciot
     * @param s
     */
    public void SetPerformAction(String s){
        performAction=s;
    }

    /**
     * visszaadja a vegrehajtando akciot
     * @return
     */
    public String GetPerformAction(){
        return performAction;
    }
}
