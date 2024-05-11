import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * A pumpa feladata a viz tovabbitasa.
 * A pumpa rendelkezik egy kis tartallyal, amelyet a szallitott viz ideiglenes tarolasara hasznal.
 * Egy pumpa tobb kimenettel, illetve bemenettel is rendelkezik, de mindig egyszerre csak egy iranybol egy iranyba tud vizet pumpalni.
 * A pumpa veletlen idokozonkent elromlik, ilyenkor nem aramlik rajta keresztul a viz.
 * A szabotor jatekosok es a szerelo jatekosok is at tudjak allitani, hogy a pumpa melyik bemeneterol melyik kimenetere pumpalja tovabb a vizet.
 */
public class Pump extends Active{
    /**
     * referencia teszteleshez (nev amit a create fuggvenyben kapott)
     */
    private String referenceID;
    private boolean hasWaterBeforeStep = false;
    public Pump(){
        referenceID= UUID.randomUUID().toString();
    }
    public Pump(String refID){
        referenceID=refID;
    }
    /**
     * tarolt viz mennyiseg
     */
    private int storedWater = 0;

    public String GetReferenceID(){return referenceID;}

    /**
     * Tovabbitja a vizet
     */
    public void ForwardWater(){
        if(storedWater>0 && !GetIsBroken()){
            if(GetPOut()!=null){
                if(GetPOut().CanAcceptWater()){
                    if(GetPOut().AcceptWater(this)){
                        DecreaseStoredWater();
                        if(storedWater == 0)
                            hasWaterBeforeStep = false;
                    }
                }
            }
        }
    }

    /**
     * csokkenti a tarolt viz mennyiseget
     */
    public void DecreaseStoredWater(){
        storedWater--;
    }

    /**
     * pumpa fogadja a vizet
     * @param f mezo, ahonnan fogadja a vizet
     * @return elfogadta e a vizet
     */
    public boolean AcceptWater(Field f){
        if(f == GetPIn() && storedWater < 5){
            if(GetBeenStepped())
                hasWaterBeforeStep = true;
            IncreaseStoredWater();
            return true;
        }
        return false;
    }

    /**
     * Meghibasodik a pumpa veletlenszeru idokozonkent
     */
    public void Malfunction(){
        if(new Random().nextInt(20) == 0){
            SetIsBroken(true);
        }
    }

    /**
     * az adott objektum korvegi lepese, ilyenkor pumpal egyet vagy a sajat tartalyaba, vagy a kimeneti csore
     */
    public void Step(){
        Malfunction();
        if(hasWaterBeforeStep)
            ForwardWater();
        else if(storedWater > 0)
            hasWaterBeforeStep = true;
    }

    /**
     * noveli a tarolt viz mennyiseget
     */
    public void IncreaseStoredWater(){storedWater++;}

    /**
     * beallitja storedWater erteket a kapottra
     * @param n tarolt vizmennyiseg
     */
    public void SetStoredWater(int n){storedWater = n;}

    /**
     * visszaadja storedWater erteket
     * @return tarolt vizmennyiseg
     */
    public int GetStoredWater(){ return storedWater;}

    /**
     * kilistazza a szabotor altal elvegezheto akciokat
     * @param saboteur aki a mezon eppen all
     */
    public void SaboteurOptions(Saboteur saboteur){
        saboteur.ClearOptions();
        List<Field> neighbours = Game.Get().GetMap().GetNeighbours(this);
        List<Field> steppableNeighbours = new ArrayList<>();

        for(Field neighbour: neighbours){
            if(neighbour.CanAcceptPlayer())
                steppableNeighbours.add(neighbour);
        }

        if(steppableNeighbours.size() != 0)
            saboteur.AddOption("move");
        if(!GetIsBroken())
            saboteur.AddOption("set pump");

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
        if(GetPerformAction().equals("set pump")){
            saboteur.ClearOptions();

            for(Field neighbour: neighbours){
                saboteur.AddOption(Game.Get().GetMap().GetDirection(this, neighbour));
            }

            GameFrame.Get().EnableActions(saboteur.GetOptions());

            //waiting for button press
            Wait.Get().Wait();

            String in = GetPerformAction();
            saboteur.ClearOptions();

            for(Field neighbour: neighbours){
                if(neighbour != Game.Get().GetMap().GetNeighbourFromDirection(this, in))
                    saboteur.AddOption(Game.Get().GetMap().GetDirection(this, neighbour));
            }

            GameFrame.Get().EnableActions(saboteur.GetOptions());

            //waiting for button press
            Wait.Get().Wait();

            saboteur.SetPump(Game.Get().GetMap().GetNeighbourFromDirection(this, in), Game.Get().GetMap().GetNeighbourFromDirection(this, GetPerformAction()), this);
        }
        if(GetPerformAction().equals("pass"))
            saboteur.SetActionPoints(0);

    }

    /**
     * kilistazza a szerelo altal elvegezheto akciokat
     * @param fixer szerelo aki rajta all
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

        if(steppableNeighbours.size() != 0)
            fixer.AddOption("move");
        if(!fixer.GetHasActive() && removableNeighbours.size() != 0)
            fixer.AddOption("remove pipe");
        if(fixer.GetHasActive() && replacableNeighbours.size() != 0)
            fixer.AddOption("place active");
        if(GetIsBroken())
            fixer.AddOption("fix");
        if(!GetIsBroken())
            fixer.AddOption("set pump");

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
        if(GetPerformAction().equals("set pump")){
            fixer.ClearOptions();

            for(Field neighbour: neighbours){
                fixer.AddOption(Game.Get().GetMap().GetDirection(this, neighbour));
            }

            GameFrame.Get().EnableActions(fixer.GetOptions());

            //waiting for button press
            Wait.Get().Wait();

            String in = GetPerformAction();
            fixer.ClearOptions();

            for(Field neighbour: neighbours){
                if(neighbour != Game.Get().GetMap().GetNeighbourFromDirection(this, in))
                    fixer.AddOption(Game.Get().GetMap().GetDirection(this, neighbour));
            }

            GameFrame.Get().EnableActions(fixer.GetOptions());

            //waiting for button press
            Wait.Get().Wait();

            fixer.SetPump(Game.Get().GetMap().GetNeighbourFromDirection(this, in), Game.Get().GetMap().GetNeighbourFromDirection(this, GetPerformAction()), this);
        }
        if(GetPerformAction().equals("pass"))
            fixer.SetActionPoints(0);
    }

    /**
     * beallitja a ki es bemeneti csoveket nullra
     * @param in be, vagy kimenetkent allitjuk be az elemet
     * @param a cso
     */
    public void SetDirection(boolean in, Field a){}

    public ArrayList<String> GetStatus(){
        ArrayList<String> list = new ArrayList<String>();
        list.add("main.java.Pump");
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
        list.add(Integer.toString(storedWater));
        list.add(Game.Get().GetMap().GetDirection(this, GetPIn()));
        list.add(Game.Get().GetMap().GetDirection(this, GetPOut()));
        return list;
    }
}
