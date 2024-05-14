package drukmakoriSzerelok;

import java.util.ArrayList;
import java.util.List;

/**
 * A palyan levo dolgok poziciojaert es tarolasaert felelos osztaly,
 * amely dinamikusan frissul, ahogy epul a csohalozat.
 */
public class Map {
    /**
     * osszes mezo
     */
    private ArrayList<ArrayList<Field>> map;

    /**
     * konstruktor
     */
    public Map() {
        map = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            ArrayList<Field> fields = new ArrayList<>();
            for (int j = 0; j < 12; j++) {
                Field p=new BlankField();
                fields.add(p);
            }
            map.add(fields);
        }
        BuildDefaultMap();
    }

    /**
     * Felepiti az alapertelemezett terkepet, amin a jatekosok kezdenek
     */
    public void BuildDefaultMap(){
        //Tanks
        for(int i=1; i<12; i=i+3){
            map.get(0).set(i, new Tank());
        }

        //Fountains
        for(int i=1; i<12; i=i+3){
            map.get(19).set(i, new Fountain());
        }

        //pipes
        for(int i=1; i<19; i++){
            if(i!=12) map.get(i).set(1, new Pipe());
        }
        for(int i=1; i<19; i++){
            if(i!=12 && i!=4) map.get(i).set(4, new Pipe());
        }
        for(int i=1; i<19; i++){
            if(i!=15 && i!=4) map.get(i).set(7, new Pipe());
        }
        for(int i=1; i<19; i++){
            if(i!=15) map.get(i).set(10, new Pipe());
        }

        for(int i=2; i<4; i++){
            map.get(12).set(i, new Pipe());
        }
        for(int i=5; i<7; i++){
            map.get(4).set(i, new Pipe());
        }
        for(int i=8; i<10; i++){
            map.get(15).set(i, new Pipe());
        }

        //pumps
        Pump p1 = new Pump();
        p1.SetPIn(map.get(13).get(1));
        p1.SetPOut(map.get(11).get(1));
        map.get(12).set(1, p1);
        Pump p2 = new Pump();
        p2.SetPIn(map.get(13).get(4));
        p2.SetPOut(map.get(11).get(4));
        map.get(12).set(4, p2);
        Pump p3 = new Pump();
        p3.SetPIn(map.get(5).get(4));
        p3.SetPOut(map.get(3).get(4));
        map.get(4).set(4, p3);
        Pump p4 = new Pump();
        p4.SetPIn(map.get(5).get(7));
        p4.SetPOut(map.get(3).get(7));
        map.get(4).set(7, p4);
        Pump p5 = new Pump();
        p5.SetPIn(map.get(16).get(7));
        p5.SetPOut(map.get(14).get(7));
        map.get(15).set(7, p5);
        Pump p6 = new Pump();
        p6.SetPIn(map.get(16).get(10));
        p6.SetPOut(map.get(14).get(10));
        map.get(15).set(10, p6);
    }

    public void InitDirections(){
        map.get(13).get(1).SetDirection(false, map.get(12).get(1));
        map.get(11).get(1).SetDirection(true, map.get(12).get(1));

        map.get(13).get(4).SetDirection(false, map.get(12).get(4));
        map.get(11).get(4).SetDirection(true, map.get(12).get(4));

        map.get(5).get(4).SetDirection(false, map.get(4).get(4));
        map.get(3).get(4).SetDirection(true, map.get(4).get(4));

        map.get(5).get(7).SetDirection(false, map.get(4).get(7));
        map.get(3).get(7).SetDirection(true, map.get(4).get(7));

        map.get(16).get(7).SetDirection(false, map.get(15).get(7));
        map.get(14).get(7).SetDirection(true, map.get(15).get(7));

        map.get(16).get(10).SetDirection(false, map.get(15).get(10));
        map.get(14).get(10).SetDirection(true, map.get(15).get(10));

        map.get(12).get(2).SetDirection(true, map.get(12).get(1));
        map.get(4).get(5).SetDirection(true, map.get(4).get(4));
        map.get(15).get(8).SetDirection(true, map.get(15).get(7));
    }

    /**
     * kicserel egy mezot egy masikkal
     * @param target regi mezo
     * @param source uj mezo
     */
    public void ReplaceField(Field target, Field source){
        map.get(GetYIndex(target)).set(GetXIndex(target), source);
    }

    /**
     * visszaadja a palyat
     * @return a palya
     */
    public ArrayList<ArrayList<Field>> GetFields(){
        return map;
    }

    /**
     * visszaadja egy mezohoz tartozo szomszedokat
     * @param f az adott mezo
     * @return f szomszedjai
     */
    public List<Field> GetNeighbours(Field f){
        List<Field> a = new ArrayList<>();
        int j = 0,i = 0;
        boolean found = false;
        for (i = 0; i < map.size() && !found; i++){
            for(j = 0; j < map.get(i).size() && !found; j++){
                if(f == map.get(i).get(j)) { found = true;}
            }
        }
        i--; j--;

        try{
            if(j != map.get(i).size())
                a.add(map.get(i).get(j+1));
        }catch(Exception e){

        }
        try{
            if(j != 0)
                a.add(map.get(i).get(j-1));
        }catch(Exception e){

        }
        try{
            if(i != map.size())
                a.add(map.get(i+1).get(j));
        }catch(Exception e){

        }
        try{
            if(i != 0)
                a.add(map.get(i-1).get(j));
        }catch(Exception e){

        }

        return a;
    }

    public String GetDirection(Field f1, Field f2){
        if(f1 == null || f2 == null)
            return "";

        int f1x = 0;
        int f1y = 0;
        int f2x = 0;
        int f2y = 0;

        for(int i = 0; i < map.size(); i++){
            for(int j = 0; j < map.get(i).size(); j++){
                if(map.get(i).get(j) == f1){
                    f1x = j;
                    f1y = i;
                }
                if(map.get(i).get(j) == f2){
                    f2x = j;
                    f2y = i;
                }
            }
        }

        if(f1x > f2x)
            return "up";
        if(f1x < f2x)
            return "down";
        if(f1y > f2y)
            return "left";
        if(f1y < f2y)
            return "right";
        return "";
    }

    /**
     * Az adott iranybol visszaadja a szomszedjat a mezonek
     * @param field
     * @param direction
     * @return
     */
    public Field GetNeighbourFromDirection(Field field, String direction){
        int x = GetXIndex(field);
        int y = GetYIndex(field);
        if(direction.equals("up") && x != 0)
            return map.get(y).get(x - 1);
        if(direction.equals("down") && x != map.get(y).size() - 1)
            return map.get(y).get(x + 1);
        if(direction.equals("left") && y != 0)
            return map.get(y - 1).get(x);
        if(direction.equals("right") && y != map.size())
            return map.get(y + 1).get(x);
        return null;
    }
    /**
     * Az adott mezo Y koordianatajat adja vissza
     * @param field a mezo
     * @return koordianata
     */
    public int GetYIndex(Field field){
        int y = 0;

        for(int i = 0; i < map.size(); i++){
            for(int j = 0; j < map.get(i).size(); j++){
                if(map.get(i).get(j) == field){
                    y = i;
                }
            }
        }

        return y;
    }

    /**
     * Az adott mezo X koordianatajat adja vissza
     * @param field a mezo
     * @return koordianata
     */
    public int GetXIndex(Field field){
        int x = 0;

        for(int i = 0; i < map.size(); i++){
            for(int j = 0; j < map.get(i).size(); j++){
                if(map.get(i).get(j) == field){
                    x = j;
                }
            }
        }

        return x;
    }
}
