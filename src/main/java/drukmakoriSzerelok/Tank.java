package drukmakoriSzerelok;

import java.util.*;

/**
 * A ciszterna egy viztarolo egyseg, tovabba ez biztositja a szerelo jatekosok szamara az eszkozoket is a jatekban.
 * Itt termelodnek a csovek es a ciszternanal talalhatoak a pumpak is, amelyekkel a halozatot tovabb lehet boviteni.
 * A ciszternakba beerkezo vizert a szerelok csapata pontot szerez.
 */
public class Tank extends Field{

    private String referenceID;
    /**
     * tarolt csovek szama
     */
    private int numberOfPipes;

    /**
     * tarolt pumpak szama
     */
    private int numberOfPumps;

    /**
     * @return tesztelesi referencia
     */
    public String GetReferenceID(){return referenceID;}

    public Tank(String refID){
        numberOfPipes=5;
        numberOfPumps=1;
        referenceID=refID;
    }
    public Tank(){
        numberOfPipes=5;
        numberOfPumps=1;
        referenceID=UUID.randomUUID().toString();
    }

    /**
     * visszaadja a pumpak szamat
     * @return a pumpak szama a ciszternaban
     */
    public int getNumberOfPumps() {
        return  numberOfPumps;
    }

    /**
     * beallitja a tarolt pumpak szamat
     * @param n a tarolando pumpak szama
     */
    public void setNumberOfPumps(int n) {
        numberOfPumps = n;
    }

    /**
     * visszaadja a csovek szamat
     * @return a csovek szama a ciszternaban
     */
    public int getNumberOfPipes() {
        return numberOfPipes;
    }
    public void setNumberOfPipes(int n) {
        numberOfPipes = n;
    }

    /**
     * general uj csoveket, amelyeket eltarol
     */
    public void GeneratePipe(){
        if(new Random().nextInt(10) < 2){
            numberOfPipes++;
        }
    }

    /**
     * general uj pumpat, amelyet eltarol
     */
    public void GeneratePump(){
        if(new Random().nextInt(10) < 1){
            numberOfPumps++;
        }
    }

    /**
     * odaad egy pumpat egy jatekosnak
     * @param f szerelo akinek adja
     */
    public void GivePump(Fixer f){
        f.DecreaseActionPoints();
        f.SetActive(new Pump());
        f.SetHasActive(true);
        DecreaseNumberOfPumps();
    }

    /**
     * odaad egy csovet egy jatekosnak
     * @param f szerelo akinek adja
     */
    public void GivePipe(Fixer f){
        f.DecreaseActionPoints();
        f.SetActive(new Pipe());
        f.SetHasActive(true);
        DecreaseNumberOfPipes();
    }

    /**
     * csokkenti az eltarolt csovek szamat
     */
    public void DecreaseNumberOfPipes() { numberOfPipes--;}

    /**
     * csokkenti az eltarolt pumpak szamat
     */
    public void DecreaseNumberOfPumps() { numberOfPumps--;}

    /**
     * az adott objektum korvegi lepese, ilyenkor folyik bele a viz
     */
    public void Step(){
        GeneratePipe();
        GeneratePump();
    }

    /**
     * kilistazza a szabotor altal elvegezheto akciokat
     * @param saboteur szabotor jatekos
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
     * @param fixer fixer jatekos
     */
    public void FixerOptions(Fixer fixer){
        fixer.ClearOptions();
        List<Field> neighbours = Game.Get().GetMap().GetNeighbours(this);
        List<Field> steppableNeighbours = new ArrayList<Field>();
        List<Field> removableNeighbours = new ArrayList<Field>();
        List<Field> replacableNeighbours = new ArrayList<Field>();
        //szomszedok listaianak feltoltese
        for(Field neighbour: neighbours){
            if(neighbour.CanAcceptPlayer())
                steppableNeighbours.add(neighbour);
            if(neighbour.GetRemovable())
                removableNeighbours.add(neighbour);
            if(neighbour.GetReplacable())
                replacableNeighbours.add(neighbour);
        }
        //opciok hozzaadasa a szomszedok listai alapjan
        if(steppableNeighbours.size() != 0)
            fixer.AddOption("move");
        if(!fixer.GetHasActive() && removableNeighbours.size() != 0)
            fixer.AddOption("remove pipe");
        if(fixer.GetHasActive() && replacableNeighbours.size() != 0)
            fixer.AddOption("place active");
        if(!fixer.GetHasActive() && numberOfPumps > 0)
            fixer.AddOption("carry pump");
        if(!fixer.GetHasActive() && numberOfPipes > 0)
            fixer.AddOption("carry pipe");

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
        if(GetPerformAction().equals("carry pump"))
            fixer.CarryPump(this);
        if(GetPerformAction().equals("carry pipe"))
            fixer.CarryPipe(this);
        if(GetPerformAction().equals("pass"))
            fixer.SetActionPoints(0);

    }

    /**
     * Fogadja a vizet es pontot ad a szereloknek
     * @param f mezo, ahonnan fogadja a vizet
     * @return igazat ad vissza ha sikeresen elfogadta a vizet
     */
    public boolean AcceptWater(Field f){
        Game.Get().InceraseFixerPoints();
        return true;
    }
    /**
     * Visszaadja a mezo statuszat
     * @return ArrayList<String> a statusz
     */
    public ArrayList<String> GetStatus(){
        ArrayList<String> list = new ArrayList<String>();
        list.add("Tank");
        list.add(Integer.toString(Game.Get().GetMap().GetXIndex(this)));
        list.add(Integer.toString(Game.Get().GetMap().GetYIndex(this)));

        int s_number=0, f_number=0;
        for(Player p: GetPlayer()){
            if(p.GetStatus().get(1).equals("fixer")) f_number++;
            if(p.GetStatus().get(1).equals("saboteur")) s_number++;
        }
        list.add(Integer.toString(f_number));
        list.add(Integer.toString(s_number));

        list.add(Integer.toString(numberOfPipes));
        list.add(Integer.toString(numberOfPumps));
        return list;
    }
}
