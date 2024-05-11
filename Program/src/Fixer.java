import java.util.ArrayList;

/**
 * A szerelo jatekos kepes mozogni a csovek halozatan,
 * kepes a ciszternanal letrejott csovek pumpaba kotesere es kepes pumpat elhelyezni a sivatagban.
 * At tudja allitani a pumpa melyik bemeneterol melyik kimenetere pumpalja a vizet
 * es meg is tudja javitani a pumpat, ha az elromlott.
 */
public class Fixer extends Player{
    /**
     * szerelonel levo aktiv elem
     */
    private Active active;
    /**
     * van-e nala aktiv elem
     */
    private boolean hasActive;

    /**
     * konstruktor
     * @param name a szerelo neve
     * @param picture a szerelo kepe
     */
    public Fixer(String name, String picture){
        this.SetReferenceID(name);
        SetPictureName(picture);
    }

    /**
     * a parameterkent kapott mezot megjavitja
     * @param active a megjavitando
     */
    public void Fix(Active active) {
        active.Fix();
    }
    /**
     * felvesz egy lent levo csovet
     * @param p a felvett cso
     */
    public void RemoveActivePipe(Pipe p){
        p.Remove(this);
    }
    /**
     * lerakja a nala levo aktiv elemet a parameterkent kapott mezore
     * @param f a mezo amit kicserel
     */
    public void Place(Field f){
        if(GetField().GetPIn() == f) {
            active.SetPOut(GetField());
            GetField().SetPIn(active);

            String dir = Game.Get().GetMap().GetDirection(f, GetField());
            if(dir == "right") {
                if(Game.Get().GetMap().GetNeighbourFromDirection(f, "left") != null)
                    Game.Get().GetMap().GetNeighbourFromDirection(f, "left").SetPOut(active);
                active.SetPIn(Game.Get().GetMap().GetNeighbourFromDirection(f, "left"));
            }
            if(dir == "left") {
                if(Game.Get().GetMap().GetNeighbourFromDirection(f, "right") != null)
                    Game.Get().GetMap().GetNeighbourFromDirection(f, "right").SetPOut(active);
                active.SetPIn(Game.Get().GetMap().GetNeighbourFromDirection(f, "right"));
            }
            if(dir == "up"){
                if(Game.Get().GetMap().GetNeighbourFromDirection(f, "down") != null)
                    Game.Get().GetMap().GetNeighbourFromDirection(f, "down").SetPOut(active);
                active.SetPIn(Game.Get().GetMap().GetNeighbourFromDirection(f, "down"));
            }
            if(dir == "down"){
                if(Game.Get().GetMap().GetNeighbourFromDirection(f, "up") != null)
                    Game.Get().GetMap().GetNeighbourFromDirection(f, "up").SetPOut(active);
                active.SetPIn(Game.Get().GetMap().GetNeighbourFromDirection(f, "up"));
            }
        }
        else{
            active.SetPIn(GetField());
            GetField().SetPOut(active);

            String dir = Game.Get().GetMap().GetDirection(f, GetField());
            if(dir == "right") {
                if(Game.Get().GetMap().GetNeighbourFromDirection(f, "left") != null)
                    Game.Get().GetMap().GetNeighbourFromDirection(f, "left").SetPIn(active);
                active.SetPOut(Game.Get().GetMap().GetNeighbourFromDirection(f, "left"));
            }
            if(dir == "left") {
                if(Game.Get().GetMap().GetNeighbourFromDirection(f, "right") != null)
                    Game.Get().GetMap().GetNeighbourFromDirection(f, "right").SetPIn(active);
                active.SetPOut(Game.Get().GetMap().GetNeighbourFromDirection(f, "right"));
            }
            if(dir == "up") {
                if(Game.Get().GetMap().GetNeighbourFromDirection(f, "down") != null)
                    Game.Get().GetMap().GetNeighbourFromDirection(f, "down").SetPIn(active);
                active.SetPOut(Game.Get().GetMap().GetNeighbourFromDirection(f, "down"));
            }
            if(dir == "down") {
                if(Game.Get().GetMap().GetNeighbourFromDirection(f, "up") != null)
                    Game.Get().GetMap().GetNeighbourFromDirection(f, "up").SetPIn(active);
                active.SetPOut(Game.Get().GetMap().GetNeighbourFromDirection(f, "up"));
            }
        }
        f.ReplaceByFixer(this, f);
    }
    /**
     * felvesz egy pumpat a ciszternabol
     * @param tank taroló
     */
    public void CarryPump(Tank tank){
        tank.GivePump(this);
    }
    /**
     * felvesz egy csovet a ciszternabol
     * @param tank tarolo
     */
    public void CarryPipe(Tank tank){
        tank.GivePipe(this);
    }
    /**
     * beallitja a megfelelo aktiv elemet, amely a jatekosnal van
     * @param a aktiv elem a jatekosnal
     */
    public void SetActive(Active a){
        active = a;
    }
    /**
     * meghivja azon mezo FixerOptions metodusat, amin all
     */
    public void InteractOptions(){
        GetField().FixerOptions(this);

        for(Field neighbour: Game.Get().GetMap().GetNeighbours(GetField())){
            GameFrame.Get().DrawField(neighbour);
            for(Field neighbour2: Game.Get().GetMap().GetNeighbours(neighbour)){
                GameFrame.Get().DrawField(neighbour2);
            }
        }

        GameFrame.Get().DrawField(GetField());
        GameFrame.Get().DrawPlayer(this);
    }
    /**
     * visszadja a nala levo aktiv elemet
     * @return nala levo aktiv elem
     */
    public Active GetActive(){
        return active;
    }
    /**
     * visszadja, hogy van-e nala aktiv elem
     * @return hasActive erteke
     */
    public boolean GetHasActive(){
        return hasActive;
    }
    /**
     * beallitja, hogy van-e nala aktiv elem
     * @param b hasActive uj erteke
     */
    public void SetHasActive(boolean b){
        hasActive = b;
    }

    /**
     * Visszaadja a jateokos statuszat
     * @return a jatekos statusza
     */
    public ArrayList<String> GetStatus(){
        ArrayList<String> list=new ArrayList<String>();
        list.add(GetReferenceID());
        list.add("fixer");
        list.add(Integer.toString(Game.Get().GetMap().GetXIndex(this.GetField())));
        list.add(Integer.toString(Game.Get().GetMap().GetYIndex(this.GetField())));
        list.add(Integer.toString(GetActionPoints()));
        list.add(Boolean.toString(GetHasActive()));

        return list;
    }
}
