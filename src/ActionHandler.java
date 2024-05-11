import java.util.*;

/**
 * A korok inditasaert es vezerleseert felelos osztaly.
 * Egy kor a jatekosok lepeseivel indul, majd utana “mozdul az ido”,
 * ilyenkor folyik a viz, es a kifolyt vizert a szabotorok, a ciszternaba jutott vizert a szerelok kapnak pontot.
 */
public class ActionHandler {

    /**
     * A parameterkent kapott jatekos korenek iranyitasaert felel.
     * @param p soron levo jatekos
     */
    public void PlayerTurn(Player p){
        while(p.GetActionPoints()>0){
            p.InteractOptions();
        }
    }

    /**
     * Egy teljes kor levezenylese, a jatekosok lepeseit es az ido mulasat is beleertve.
     */
    public void HandleTurn(){
        List<Player> players=Game.Get().GetPlayers();
        for(Player p: players){
            if(Game.Get().GetActivePlayer()!=null) GameFrame.Get().GetPlayerLayer().UpdateField(Game.Get().GetActivePlayer().GetField().GetStatus());
            Game.Get().SetActivePlayer(p);
            p.SetActionPoints(3);
            GameFrame.Get().DrawField(p.GetField());
            GameFrame.Get().DrawPlayer(p);
            PlayerTurn(p);
        }
        WorldTurn();
        ArrayList<ArrayList<Field>> field = Game.Get().GetMap().GetFields();
        for(int i = 0; i < field.size(); i++){
            if(field.get(i).size()!=0) {
                for (int j = 0; j < field.get(i).size(); j++) {
                    GameFrame.Get().DrawField(field.get(i).get(j));
                }
            }
        }
        GameFrame.Get().DrawPoints();
    }

    /**
     * A csohalozatban torteno esemenyek iranyitasaert felel.
     */
    public void WorldTurn(){
        ArrayList<ArrayList<Field>> field = Game.Get().GetMap().GetFields();
        for(int i = 0; i < field.size(); i++){
            for (int j = 0; j < field.get(i).size(); j++) {
                if(Game.Get().GetMap().GetDirection(field.get(i).get(j), field.get(i).get(j).GetPOut()) == "left" || Game.Get().GetMap().GetDirection(field.get(i).get(j), field.get(i).get(j).GetPOut()) == "up"){
                    if(field.get(i).get(j).GetBeenStepped() == false){
                        field.get(i).get(j).Step();
                        field.get(i).get(j).SetBeenStepped(true);
                        GameFrame.Get().DrawField(field.get(i).get(j));
                    }
                }
                else if((Game.Get().GetMap().GetDirection(field.get(i).get(j), field.get(i).get(j).GetPIn()) == "right" || Game.Get().GetMap().GetDirection(field.get(i).get(j), field.get(i).get(j).GetPIn()) == "down")
                    && (i == 0 || j == 0)){
                    if(field.get(i).get(j).GetBeenStepped() == false){
                        field.get(i).get(j).Step();
                        field.get(i).get(j).SetBeenStepped(true);
                        GameFrame.Get().DrawField(field.get(i).get(j));
                    }
                }
            }
        }
        for(int i = field.size() - 1; i >= 0; i--){
            for (int j = field.get(i).size() - 1; j >= 0; j--) {
                if ((Game.Get().GetMap().GetDirection(field.get(i).get(j), field.get(i).get(j).GetPOut()) == "right" || Game.Get().GetMap().GetDirection(field.get(i).get(j), field.get(i).get(j).GetPOut()) == "down")){
                    if(field.get(i).get(j).GetBeenStepped() == false) {
                        field.get(i).get(j).Step();
                        field.get(i).get(j).SetBeenStepped(true);
                        GameFrame.Get().DrawField(field.get(i).get(j));
                    }
                }
                else if((Game.Get().GetMap().GetDirection(field.get(i).get(j), field.get(i).get(j).GetPIn()) == "left" || Game.Get().GetMap().GetDirection(field.get(i).get(j), field.get(i).get(j).GetPIn()) == "up")
                    && (i == field.size() - 1 || j == field.get(i).size() - 1)){
                    if(field.get(i).get(j).GetBeenStepped() == false) {
                        field.get(i).get(j).Step();
                        field.get(i).get(j).SetBeenStepped(true);
                        GameFrame.Get().DrawField(field.get(i).get(j));
                    }
                }
            }
        }
        for(int i = 0; i < field.size(); i++){
            for (int j = 0; j < field.get(i).size(); j++) {
                if(field.get(i).get(j).GetBeenStepped() == false) {
                    field.get(i).get(j).Step();
                    GameFrame.Get().DrawField(field.get(i).get(j));
                }
            }
        }
        for(int i = 0; i < field.size(); i++){
            for (int j = 0; j < field.get(i).size(); j++) {
                field.get(i).get(j).SetBeenStepped(false);
            }
        }
    }
}
