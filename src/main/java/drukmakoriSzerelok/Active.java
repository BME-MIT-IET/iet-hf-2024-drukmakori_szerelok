package drukmakoriSzerelok;

/**
 * Absztrakt osztaly, A jatek aktev elemeinek absztrakt ososztaly, amelybol leszarmazik a pumpa, illetve a cso.
 * Itt szerepelnek a kozos tulajdonsagaik az aktiv elemeknek, ilyen a mozgathatosag es az elromlas is.
 */
public abstract class Active extends  Field{
    /**
     * Jelzi, hogy el van-e romolva a mezo.
     */
    private boolean isBroken;
    /**
     * Mezo, ahonnan fogadja a vizet.
     */
    private Field pIn;
    /**
     * Mezo, ahova tovabbitja a vizet.
     */
    private Field pOut;

    /**
     * megjavitja az adott mezot, ha elromlott.
     */
    public void Fix(){
        SetIsBroken(false);
        Game.Get().GetActivePlayer().DecreaseActionPoints();
    }
    /**
     * elromlik az adott mezo.
     */
    public void Destroy(){
        Game.Get().GetActivePlayer().DecreaseActionPoints();
        SetIsBroken(true);
    }
    /**
     * beallitja a viz kimeno mezojet
     * @param b kimeno mezo
     */
    public void SetIsBroken(boolean b){
        isBroken = b;
    }
    /**
     * visszaadja az isBroken erteket
     * @return el van-e romolva a mezo
     */
    public boolean GetIsBroken(){
        return isBroken;
    }
    /**
     * beallitja a viz bejovo mezojet
     * @param f bejovo mezo
     */
    public void SetPIn(Field f){
        pIn = f;
    }
    /**
     * visszaadja a bejovo pumpa mezojet
     * @return bejovo mezo
     */
    public Field GetPIn(){
        return pIn;
    }
    /**
     * beallitja a viz kimeno mezojet
     * @param f kimeno mezo
     */
    public void SetPOut(Field f){
        pOut = f;
    }
    /**
     * visszaadja a kimeno pumpa mezojet
     * @return kimeno mezo
     */
    public Field GetPOut(){
        return pOut;
    }
    /**
     * absztrakt fuggveny, Vizet tovabbit az adott mezobe
     */
    public abstract void ForwardWater();
}
