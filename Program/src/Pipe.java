import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * A cso szallitja a vizet a jatekban tovabba ez biztositja az utat a szerelo es a szabotor szamara is.
 * Egy csovon egyszerre csak egy jatekos lehet, azaz nem haladhatnak rajta parhuzamosan.
 * A csonek van kapacitasa, amely megmutatja, hogy a csoben az adott pillanatban van-e viz.
 * A csoben nincsenek elagazasok, ket vegpont kozott halad.
 * A csovet a szabotor jatekosok ki tudjak lyukasztani. A csovet lehet ragadossa es csuszossa tenni.
 * A csovek a ciszternaknal jonnek letre, es onnan flexibilisen bekothetok a jatek tobbi aktiv elemebe.
 */
public class Pipe extends Active{
    /**
     * referencia teszteleshez (nev amit a create fuggvenyben kapott
     */
    private String referenceID;
    public Pipe(String refID){
        SetRemovable(true);
        SetReplacable(true);
        referenceID=refID;
    }
    public Pipe(){
        SetRemovable(true);
        SetReplacable(true);
        referenceID= UUID.randomUUID().toString();
    }
    
    /**
     * azt mutatja, hogy meg hany korig nem lehet ujra kilyukasztani
     */
    private int unDestroyable = 0;
    /**
     * azt mutatja, hogy meg hany korig csuszos
     */
    private int slippery = 0;
    /**
     * azt mutatja, hogy meg hany korig ragados
     */
    private int sticky = 0;
    /**
     * jatekos, aki sticky-ve tette a csovet
     */
    private Player madeStickyBy;

    /**
     * @return tesztelesi referencia
     */
    public String GetReferenceID(){return referenceID;}

    /**
     * a cso korvegi lepese, ilyenkor folyik tovabb egy egysegnyi viz a csoben
     */
    public void Step(){
        if(Game.Get().GetMap().GetYIndex(this) == 12 && Game.Get().GetMap().GetXIndex(this) == 0)
            System.out.println("Step");
        LeakWater();
        ForwardWater();
        if(slippery > 0)
            slippery--;
        if(unDestroyable > 0)
            unDestroyable--;
        if(sticky > 0)
            sticky--;
    }

    /**
     * megjavitja az elromlott csovet
     */
    public void Fix(){
        SetIsBroken(false);
        Game.Get().GetActivePlayer().DecreaseActionPoints();
        unDestroyable = new Random().nextInt(3)+1;
    }

    /**
     * eltavolitja a csovet a halozatbol
     * @param f Fixer, aki felveszi
     */
    public void Remove(Fixer f){
        if(GetHasWater())
            Game.Get().InceraseSaboteurPoints();
        f.DecreaseActionPoints();
        Map map=Game.Get().GetMap();
        BlankField bf = new BlankField();
        GetPOut().SetPIn(bf);
        GetPIn().SetPOut(bf);
        SetPOut(null);
        SetPIn(null);
        map.ReplaceField(this, bf);
        f.SetActive(this);
        f.SetHasActive(true);
    }

    /**
     * kifolyatja a vizet
     */
    public void LeakWater(){
        if(GetHasWater() && GetIsBroken()){
            SetHasWater(false);
            Game.Get().InceraseSaboteurPoints();
        }
    }

    /**
     * Vizet tovabbit a kimeneten
     */
    public void ForwardWater(){
        if(GetHasWater() && !GetIsBroken()){
            Field pOut=GetPOut();
            if(GetPOut() != null){
                boolean accepted=pOut.AcceptWater(this);
                if(accepted){
                    SetHasWater(false);
                }
            }
            if(GetPOut() == null){
                Game.Get().InceraseSaboteurPoints();
                SetHasWater(false);
            }
        }
    }

    /**
     * cso fogadja a vizet
     * @param f mezo, ahonnan fogadja a vizet
     * @return sikeresen fogadta-e a vizet, vagy sem
     */
    public boolean AcceptWater(Field f){
        if(!GetHasWater()){
            if((Game.Get().GetMap().GetDirection(this, GetPIn()) == "right"  || Game.Get().GetMap().GetDirection(this, GetPIn()) == "left")
                    && (Game.Get().GetMap().GetDirection(this, f) == "right" || Game.Get().GetMap().GetDirection(this, f) == "left")){
                SetDirection(true, f);
                SetHasWater(true);
                return true;
            }
            if((Game.Get().GetMap().GetDirection(this, GetPIn()) == "up" || Game.Get().GetMap().GetDirection(this, GetPIn()) == "down")
                    && (Game.Get().GetMap().GetDirection(this, f) == "up" || Game.Get().GetMap().GetDirection(this, f) == "down")){
                SetDirection(true, f);
                SetHasWater(true);
                return true;
            }
        }
        return false;
    }

    /**
     * leveszi magarol a jatekost
     * @param p jatekos, aki lelep
     */
    public void RemovePlayer(Player p){
        SetRemovable(true);
        GetPlayer().remove(p);
    }

    /**
     * Fogadja a ralepo jatekost
     */
    public void AcceptPlayer(){
        Player player = Game.Get().GetActivePlayer();
        player.DecreaseActionPoints();
        if(slippery == 0) {
            SetRemovable(false);
            AddPlayer(player);
        }
        else{
            List<Field> neighbours = Game.Get().GetMap().GetNeighbours(this);
            List<Field> steppableNeighbours = new ArrayList<Field>();
            for(Field neighbour: neighbours) {
                if (neighbour.CanAcceptPlayer())
                    steppableNeighbours.add(neighbour);
            }
            if(steppableNeighbours.size() == 1) {
                steppableNeighbours.get(0).SetRemovable(false);
                steppableNeighbours.get(0).AddPlayer(player);
            }
            else if(steppableNeighbours.size() == 2) {
                int number = new Random().nextInt(2);
                steppableNeighbours.get(number).SetRemovable(false);
                steppableNeighbours.get(number).AddPlayer(player);
            }
            else {
                SetRemovable(false);
                AddPlayer(player);
            }
        }
    }

    /**
     * visszaadja, hogy tud-e a cso jatekost fogadni
     * @return true, ha 0 jatekos all rajta, false ha 1
     */
    public Boolean CanAcceptPlayer(){
        return GetPlayer().size()==0;
    }

    /**
     * beallitja slippery erteket a parameterul kapottra
     * @param slip slippery uj erteke
     */
    public void SetSlippery(int slip) {
        slippery = slip;
    }

    /**
     * visszadja slippery erteket
     * @return hany korig slippery meg
     */
    public int GetSlippery() {
        return slippery;
    }

    /**
     * beallitja sticky erteket a parameterul kapottra
     * @param sticky sticky uj erteke
     */
    public void SetSticky(int sticky) {
        this.sticky = sticky;
        madeStickyBy = Game.Get().GetActivePlayer();
    }
    /**
     * visszadja sticky erteket
     * @return hany korig sticky meg
     */
    public int GetSticky() {
        return sticky;
    }
    /**
     * visszadja unDestroyable erteket
     * @return hany korig unDestroyable meg
     */
    public int GetUnDestroyable() {
        return unDestroyable;
    }

    /**
     * kilistazza a szabotor altal elvegezheto akciokat
     * @param saboteur szabotor, aki vegzi az akciokat
     */
    public void SaboteurOptions(Saboteur saboteur){
        saboteur.ClearOptions();
        List<Field> neighbours = Game.Get().GetMap().GetNeighbours(this);
        List<Field> steppableNeighbours = new ArrayList<Field>();

        for(Field neighbour: neighbours){
            if(neighbour.CanAcceptPlayer()){
                steppableNeighbours.add(neighbour);
            }
        }

        if((sticky == 0 || madeStickyBy == saboteur) && steppableNeighbours.size() != 0){
            saboteur.AddOption("move");
        }
        if(sticky == 0 && slippery == 0){
            saboteur.AddOption("make sticky");
            saboteur.AddOption("make slippery");
        }
        if(!GetIsBroken() && unDestroyable == 0){
            saboteur.AddOption("sabotage pipe");
        }

        GameFrame.Get().EnableActions(saboteur.GetOptions());

        //waiting for button press
        Wait.Get().Wait();

        if(GetPerformAction().equals("move")){
            saboteur.ClearOptions();

            for(Field neighbour: steppableNeighbours){
                saboteur.AddOption(Game.Get().GetMap().GetDirection(this, neighbour));
            }

            GameFrame.Get().EnableActions(saboteur.GetOptions());

            //waiting for button press
            Wait.Get().Wait();

            saboteur.Move(Game.Get().GetMap().GetNeighbourFromDirection(this, GetPerformAction()));
        }
        if(GetPerformAction().equals("make sticky"))
            saboteur.MakeSticky(this);
        if(GetPerformAction().equals("make slippery"))
            saboteur.MakeSlippery(this);
        if(GetPerformAction().equals("sabotage pipe"))
            saboteur.PipeSabotage(this);
        if(GetPerformAction().equals("pass"))
            saboteur.SetActionPoints(0);

    }

    /**
     * kilistazza a szerelo altal elvegezheto akciokat
     * @param fixer szerelo, aki vegzi az akciokat
     */
    public void FixerOptions(Fixer fixer){
        fixer.ClearOptions();
        List<Field> neighbours = Game.Get().GetMap().GetNeighbours(this);
        List<Field> steppableNeighbours = new ArrayList<Field>();
        List<Field> removableNeighbours = new ArrayList<Field>();
        List<Field> replacableNeighbours = new ArrayList<Field>();

        for(Field neighbour: neighbours){
            if(neighbour.CanAcceptPlayer())
                steppableNeighbours.add(neighbour);
            if(neighbour.GetRemovable())
                removableNeighbours.add(neighbour);
            if(neighbour.GetReplacable())
                replacableNeighbours.add(neighbour);
        }

        if((sticky == 0 || madeStickyBy == fixer) && steppableNeighbours.size() != 0)
            fixer.AddOption("move");
        if(!fixer.GetHasActive() && removableNeighbours.size() != 0)
            fixer.AddOption("remove pipe");
        if(fixer.GetHasActive() && replacableNeighbours.size() != 0)
            fixer.AddOption("place active");
        if(GetIsBroken())
            fixer.AddOption("fix");
        if(sticky == 0 && slippery == 0)
            fixer.AddOption("make sticky");
        if(!GetIsBroken() && unDestroyable == 0)
            fixer.AddOption("sabotage pipe");

        GameFrame.Get().EnableActions(fixer.GetOptions());

        //waiting for button press
        Wait.Get().Wait();

        if(GetPerformAction().equals("move")){
            fixer.ClearOptions();

            for(Field neighbour: steppableNeighbours){
                fixer.AddOption(Game.Get().GetMap().GetDirection(this, neighbour));
            }

            GameFrame.Get().EnableActions(fixer.GetOptions());

            //waiting for button press
            Wait.Get().Wait();

            fixer.Move(Game.Get().GetMap().GetNeighbourFromDirection(this, GetPerformAction()));
        }
        if(GetPerformAction().equals("remove pipe")){
            fixer.ClearOptions();

            for(Field neighbour: removableNeighbours){
                fixer.AddOption(Game.Get().GetMap().GetDirection(this, neighbour));
            }

            GameFrame.Get().EnableActions(fixer.GetOptions());

            //waiting for button press
            Wait.Get().Wait();

            fixer.RemoveActivePipe((Pipe)(Game.Get().GetMap().GetNeighbourFromDirection(this, GetPerformAction())));
        }
        if(GetPerformAction().equals("place active")){
            fixer.ClearOptions();

            for(Field neighbour: replacableNeighbours){
                fixer.AddOption(Game.Get().GetMap().GetDirection(this, neighbour));
            }

            GameFrame.Get().EnableActions(fixer.GetOptions());

            //waiting for button press
            Wait.Get().Wait();

            fixer.Place(Game.Get().GetMap().GetNeighbourFromDirection(this, GetPerformAction()));
        }
        if(GetPerformAction().equals("fix"))
            fixer.Fix(this);
        if(GetPerformAction().equals("make sticky"))
            fixer.MakeSticky(this);
        if(GetPerformAction().equals("sabotage pipe"))
            fixer.PipeSabotage(this);
        if(GetPerformAction().equals("pass"))
            fixer.SetActionPoints(0);
    }

    /**
     * beallitja az elem iranyat
     * @param in be, vagy kimenetkent allitjuk be az elemet
     */
    public void SetDirection(boolean in, Field a){
        if(in){
            SetPIn(a);
            String dir = Game.Get().GetMap().GetDirection(this, a);
            if(dir == "up")
                SetPOut(Game.Get().GetMap().GetNeighbourFromDirection(this, "down"));
            if(dir == "down")
                SetPOut(Game.Get().GetMap().GetNeighbourFromDirection(this, "up"));
            if(dir == "left")
                SetPOut(Game.Get().GetMap().GetNeighbourFromDirection(this, "right"));
            if(dir == "right")
                SetPOut(Game.Get().GetMap().GetNeighbourFromDirection(this, "left"));
            if(GetPOut() != null)
                GetPOut().SetDirection(true, this);
        }
        if(!in){
            SetPOut(a);
            String dir = Game.Get().GetMap().GetDirection(this, a);
            if(dir == "up")
                SetPIn(Game.Get().GetMap().GetNeighbourFromDirection(this, "down"));
            if(dir == "down")
                SetPIn(Game.Get().GetMap().GetNeighbourFromDirection(this, "up"));
            if(dir == "left")
                SetPIn(Game.Get().GetMap().GetNeighbourFromDirection(this, "right"));
            if(dir == "right")
                SetPIn(Game.Get().GetMap().GetNeighbourFromDirection(this, "left"));
            if(GetPIn() != null)
                GetPIn().SetDirection(false, this);
        }
        GameFrame.Get().DrawField(this);
    }

    /**
     * Visszaadja a cso allapotat
     * @return a cso allapota
     */
    public ArrayList<String> GetStatus(){
        ArrayList<String> list = new ArrayList<String>();
        list.add("Pipe");
        list.add(Integer.toString(Game.Get().GetMap().GetXIndex(this)));
        list.add(Integer.toString(Game.Get().GetMap().GetYIndex(this)));
        int s_number=0, f_number=0;
        for(Player p: GetPlayer()){
            if(p.GetStatus().get(1).equals("fixer")) f_number++;
            if(p.GetStatus().get(1).equals("saboteur")) s_number++;
        }
        list.add(Integer.toString(f_number));
        list.add(Integer.toString(s_number));
        list.add(Boolean.toString(GetIsBroken()));
        list.add(Boolean.toString(GetHasWater()));
        list.add(Integer.toString(unDestroyable));
        list.add(Integer.toString(slippery));
        list.add(Integer.toString(sticky));
        list.add(Game.Get().GetMap().GetDirection(this, GetPIn()));
        list.add(Game.Get().GetMap().GetDirection(this, GetPOut()));
        return list;
    }
}
