import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Forras, amely generalja a vizet, amely tovabb folyik csohalozatba.
 */
public class Fountain extends Field implements Steppable{
    /**
     * referencia teszteleshez (nev amit a create fuggvenyben kapott
     */
    private  String referenceID;
    public Fountain(String refID){
        referenceID=refID;
    }
    public Fountain(){
        referenceID= UUID.randomUUID().toString();
    }
    /**
     * generalja a vizet, minden szomszedjara meghivja az AcceptWater metodust, parameterben magaval
     */
    public void GenerateWater(){
        List<Field> neighbour = Game.Get().GetMap().GetNeighbours(this);
        for(Field f: neighbour){
            if(f.CanAcceptWater()){
                f.AcceptWater(this);
            }
        }
    }

    /**
     * @return tesztelesi referencia
     */
    public String GetReferenceID(){return referenceID;}

    /**
     * forras korvegi lepese, meghivja a GenerateWater metodust magan
     */
    public void Step(){
        GenerateWater();
    }

    /**
     * nem fogadhat vizet, visszaad false erteket
     * @param f mezo, ahonnan fogadja a vizet
     * @return elfogata e a vizet
     */
    public boolean AcceptWater(Field f){
        return false;
    }

    /**
     * kilistazza a szabotor altal elvegezheto akciokat
     * @param saboteur szabotor, aki vegzi az akciokat
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

        if(steppableNeighbours.size() != 0)
            fixer.AddOption("move");
        if(!fixer.GetHasActive() && removableNeighbours.size() != 0)
            fixer.AddOption("remove pipe");
        if(fixer.GetHasActive() && replacableNeighbours.size() != 0)
            fixer.AddOption("place active");

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
        if(GetPerformAction().equals("pass"))
            fixer.SetActionPoints(0);

    }

    public ArrayList<String> GetStatus(){
        ArrayList<String> list = new ArrayList<String>();
        list.add("main.java.Fountain");
        list.add(Integer.toString(Game.Get().GetMap().GetXIndex(this)));
        list.add(Integer.toString(Game.Get().GetMap().GetYIndex(this)));
        int s_number=0, f_number=0;
        for(Player p: GetPlayer()){
            if(p.GetStatus().get(1).equals("fixer")) f_number++;
            if(p.GetStatus().get(1).equals("saboteur")) s_number++;
        }
        list.add(Integer.toString(f_number));
        list.add(Integer.toString(s_number));
        return list;
    }
}
