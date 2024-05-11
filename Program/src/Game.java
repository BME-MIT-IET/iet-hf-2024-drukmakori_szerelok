import java.util.ArrayList;

/**
 * A jatek vezerleseert felelos osztaly, elinditja a jatekot,
 * vezerli a jatekosok koreit, figyeli a pontokat, es ha osszegyulnek pontok, befejezi a jatekot.
 */
public class Game implements Steppable {
    /**
     * pontszam, amit a gyozelemert el kell erni
     */
    private int pointsToWin = 100;
    /**
     * szerelok pontszama
     */
    private int fixerPoints = 0;
    /**
     * szabotorok pontszama
     */
    private int saboteurPoints = 0;
    /**
     * a palya
     */
    private Map map;
    /**
     * singleton game
     */
    private static Game single_instance = null;
    /**
     * jelenlegi aktiv jatekos
     */
    private Player activePlayer;
    /**
     * a jatekban szereplo jatekosok listaja
     */
    private final ArrayList<Player> players;
    /**
     * kezeli a koroket
     */
    private final ActionHandler actionHandler = new ActionHandler();
    /**
     * a jatek vegi uzenet
     */
    private String gameEndMessage="";
    /**
     * jatekosok szama
     */
    private int PlayerCount=2;
    /**
     * jatekoslista letrehozasa
     */
    private Game(){
        players = new ArrayList<Player>();
        map=new Map();
        int fixerNumber=1;
        int fixerSecond=1;
        int saboteurNumber=1;
        int saboteurSecond=1;
        int playerNumber=0;
        for(int i=0;i<PlayerCount;i++){
            players.add(new Fixer("Fixer"+Integer.toString(fixerNumber++), "fixer" + Integer.toString(i + 1) + "big.png"));
            map.GetFields().get(0).get(fixerSecond).AddPlayer(players.get(playerNumber));
            playerNumber++;
            players.add(new Saboteur("Saboteur"+Integer.toString(saboteurNumber++), "saboteur" + Integer.toString(i + 1) + "big.png"));
            map.GetFields().get(19).get(saboteurSecond).AddPlayer(players.get(playerNumber));
            playerNumber++;
            fixerSecond+=3;
            saboteurSecond+=3;
        }
    }

    public void ResetPlayers(){
        for(Player p: players){
            ArrayList<Player> list=new ArrayList<Player>();
            p.GetField().SetPlayer(list);
        }
        players.clear();
    }
    public void SetPlayers(int playerCount){
        PlayerCount=playerCount;
    }

    public void SetPlayerCount(int count) throws Exception{
        System.out.println(count);
        if(count<2 || count>4) {
            SetPlayerCount(2);
            throw new Exception("OUT_OF_RANGE");
        }
        players.clear();
        PlayerCount=count;
        int fixerNumber=1;
        int fixerSecond=1;
        int saboteurNumber=1;
        int saboteurSecond=1;
        int playerNumber=0;
        for(int i=0;i<PlayerCount;i++){
            players.add(new Fixer("Fixer"+Integer.toString(fixerNumber++), "fixer" + Integer.toString(i + 1) + "big.png"));
            map.GetFields().get(0).get(fixerSecond).AddPlayer(players.get(playerNumber));
            playerNumber++;
            players.add(new Saboteur("Saboteur"+Integer.toString(saboteurNumber++), "saboteur" + Integer.toString(i + 1) + "big.png"));
            map.GetFields().get(19).get(saboteurSecond).AddPlayer(players.get(playerNumber));
            playerNumber++;
            fixerSecond+=3;
            saboteurSecond+=3;
        }
    }

    public int getPlayerCount() { return PlayerCount;}

    public void SetPointsToWin(int points) throws Exception{
        if (points<50 || points>500)
            throw new Exception("OUT_OF_RANGE");
        pointsToWin=points;
    }

    public int GetPointsToWin(){
        return pointsToWin;
    }

    /**
     * atadja a singleton game peldanyt
     * @return sajat maga
     */
    public static Game Get()
    {
        // To ensure only one instance is created
        if (single_instance == null) {
            single_instance = new Game();
        }
        return single_instance;
    }

    /**
     * visszaadja a jatekosok listajat
     * @return a jatekosok listaja
     */
    public ArrayList<Player> GetPlayers(){
        return players;
    }

    /**
     * beallitja a jelenlegi soron levo aktiv jatekost
     * @param p az uj aktiv jatekos
     */
    public void SetActivePlayer(Player p){
        activePlayer=p;
    }

    /**
     * elinditja a jatekot
     */
    public void StartGame(){
        map.InitDirections();
        while(fixerPoints < pointsToWin && saboteurPoints < pointsToWin){
            Game.Get().Step();
        }
        EndGame();
    }
    /**
     * befejezi a jatekot
     */
    public void EndGame(){
        if(fixerPoints>=pointsToWin && saboteurPoints<pointsToWin){
            gameEndMessage="Game won by Fixers.";
        }
        else if(fixerPoints<pointsToWin && saboteurPoints>=pointsToWin){
            gameEndMessage="Game won by Saboteurs.";
        }
        else if(fixerPoints>=pointsToWin && saboteurPoints>=pointsToWin){
            gameEndMessage="Draw, game won by both teams.";
        }
        GameFrame.Get().SetEndSign(gameEndMessage);
    }

    /**
     * az adott objektum korvegi lepese, ez vegzi a jatek koreinek inditasat
     */
    public void Step(){
        actionHandler.HandleTurn();
    }

    /**
     * Noveli a szerelok pontjainak szamat
     */
    public void InceraseFixerPoints(){ fixerPoints++; }

    /**
     * Noveli a szabotorok ponjainak szamat
     */
    public void InceraseSaboteurPoints(){ saboteurPoints++; }

    /**
     * Visszaadja az eppen soron levo jatekost
     * @return az eppen soron levo jatekos
     */
    public Player GetActivePlayer(){ return activePlayer; }

    /**
     * Visszaadja a terkepet
     * @return a terkep
     */
    public Map GetMap(){
        return map;
    }

    /**
     * beallitja a terkepet
     * @param m ujterkep
     */
    public void SetMap(Map m){
        map=m;
    }
    /**
     * hozzaad a jatekhoz egy jatekost
     * @param p jatekos
     */
    public void AddPlayer(Player p){
        players.add(p);
    }

    /**
     * visszaadja a parameterul kapott ID-val rendelkezo jatekost
     * @param refID keresett jatekos ID-ja
     * @return keresett jatekos
     */
    public Player searchPlayer(String refID){
        Player temp=null;
        for (Player player : players) {
            if (player.GetReferenceID().equals(refID)) {
                temp = player;
            }
        }
        return temp;
    }

    /**
     * visszaadja GameEndMessage erteket
     * @return jatek vege uzenet
     */
    public String GetGameEndMessage(){
        return gameEndMessage;
    }

    /**
     * beallitja fixer csapat pontszamat a kapott ertekre
     * @param p uj pontszam
     */
    public void SetFixerPoints(int p){
        fixerPoints=p;
    }
    /**
     * beallitja saboteur csapat pontszamat a kapott ertekre
     * @param p uj pontszam
     */
    public void SetSaboteurPoints(int p){
        saboteurPoints=p;
    }
    /**
     * visszaadja fixer csapat pontszamat
     * @return fixer csapat pontszama
     */
    public int GetFixerPoints(){
        return fixerPoints;
    }
    /**
     * visszaadja saboteur csapat pontszamat
     * @return saboteur csapat pontszama
     */
    public int GetSaboteurPoints(){
        return saboteurPoints;
    }
}
