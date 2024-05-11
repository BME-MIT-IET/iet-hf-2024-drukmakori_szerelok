import java.util.ArrayList;
import java.util.Random;

/**
 * A jatekosok absztrakt ososztalya.
 * Ebbol szarmaznak le a kulonbozo szerepek.
 */
public abstract class Player {
    /**
     * hatralevo akciok szama
     */
    private int actionPoints=3;
    private String referenceID;
    /**
     * mezo, amin all
     */
    private Field field;
    /**
     * Jatekos kepenek a neve
     */
    private String PictureName;

    public void SetPictureName(String name){
        PictureName = name;
    }

    public String GetPictureName(){
        return PictureName;
    }

    /**
     * opcioi amit az adott lepesben csinalhat
     */
    private ArrayList<String> options = new ArrayList<String>();

    public String GetReferenceID() {
        return referenceID;
    }

    public void SetReferenceID(String name) {
        referenceID=name;
    }

    /**
     * a jatekos atlep az adott mezore
     * @param f mezo, amire lep
     */
    public void Move(Field f){
        field.RemovePlayer(this);
        f.AcceptPlayer();
    }

    /**
     * a jatekos beallitja a pumpaba bemeno, illetve kimeno mezoket
     * @param pIn bejovo mezo
     * @param pOut kimeno mezo
     */
    public void SetPump(Field pIn, Field pOut, Pump pu){
        pu.SetPIn(pIn);
        if(pIn!=null)
            pIn.SetDirection(false, pu);
        pu.SetPOut(pOut);
        if(pOut!=null)
            pOut.SetDirection(true, pu);
        DecreaseActionPoints();
    }

    /**
     * kilyukasztja a csovet, amit parameterul kap
     * @param pipe a cso, amin all
     */
    public void PipeSabotage(Pipe pipe) {
        pipe.Destroy();
    }

    /**
     * csokkenti az akciopontok szamat
     */
    public void DecreaseActionPoints(){
        actionPoints--;
    }

    /**
     * absztrakt fuggveny, az interakciok megvalositasahoz
     */
    public abstract void InteractOptions();

    /**
     * visszadja a hatralevo akciopontok szamat
     * @return az akciopontok szama
     */
    public int GetActionPoints() {
        return actionPoints;
    }

    /**
     * visszadja a mezot, amin a jatekos all
     * @return mezo, amin a jatekos all
     */
    public Field GetField() {
        return field;
    }

    /**
     * ragadossa teszi a csovet, amit kapott
     * @param p cso amit ragadossa kell tenni
     */
    public void MakeSticky(Pipe p){
        p.SetSticky(new Random().nextInt(3)+1);
        DecreaseActionPoints();
    }

    /**
     * Beallitja a mezot, amin all (field) attributumot a kapott mezore
     * @param f mezo amit be kell allitani
     */
    public void SetField(Field f){
        field = f;
    }

    /**
     * Hozzaad egy opciot az opciok listajahoz
     * @param s opcio
     */
    public void AddOption(String s){
        options.add(s);
    }

    /**
     * Kitorli az opciok listajat
     */
    public void ClearOptions(){
        options.clear();
    }

    /**
     * @return az opciok listajat adja vissza
     */
    public ArrayList<String> GetOptions(){
        return options;
    }

    public void SetActionPoints(int p){
        actionPoints=p;
    }

    // TO DO
    public abstract ArrayList<String> GetStatus();

}
