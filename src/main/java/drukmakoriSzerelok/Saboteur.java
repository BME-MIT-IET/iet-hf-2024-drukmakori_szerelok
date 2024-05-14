package drukmakoriSzerelok;

import java.util.ArrayList;
import java.util.Random;

/**
 * A szabotor jatekos a szerelohoz hasonloan kepes mozogni a csovek halozatan.
 * Az a celja, hogy minel tobb vizet kifolyasson a sivatagba.
 * Azert, hogy ezt elerje kepes kilyukasztani meglevo csoveket,
 * vagy atallitani egy pumpat oly modon hogy az a vizet a sivatagba pumpalja egy ciszterna helyett.
 */
public class Saboteur extends Player{
    /**
     * Konstruktor
     * @param name a szabotor neve
     * @param picture a szabotor kepe
     */
    public Saboteur(String name, String picture){
        this.SetReferenceID(name);
        SetPictureName(picture);
    }
    /**
     * csuszossa teszi a parameterul kapott csovet
     */
    public void MakeSlippery(Pipe p){
        p.SetSlippery(new Random().nextInt(3)+1);
        DecreaseActionPoints();
    }

    /**
     * Meghivja a mezo, amin all SaboteurOptions metodusat
     */
    public void InteractOptions(){
        GetField().SaboteurOptions(this);

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
     * Visszaadja a jatekos statuszat 
     * @return
     */
    public ArrayList<String> GetStatus(){
        ArrayList<String> list=new ArrayList<String>();
        list.add(GetReferenceID());
        list.add("saboteur");
        list.add(Integer.toString(Game.Get().GetMap().GetXIndex(this.GetField())));
        list.add(Integer.toString(Game.Get().GetMap().GetYIndex(this.GetField())));
        list.add(Integer.toString(GetActionPoints()));

        return list;
    }
}
