import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 * A jatekmezot megjelenito osztaly.
 */
public class GameField extends JPanel{
    private JButton[][] fields;
    private int layer_number;

    public GameField(int ln){
        layer_number=ln;
        setBackground(Color.ORANGE);
        setLayout(new GridLayout(12, 20, 2, 2));

        fields = new JButton[12][20];
        for (int r = 0; r < 12; r++) {
            for (int c = 0; c < 20; c++) {
                fields[r][c] = new JButton();
                UpdateField(Game.Get().GetMap().GetFields().get(c).get(r).GetStatus());

                fields[r][c].setEnabled(false);
                fields[r][c].setOpaque(false);
                fields[r][c].setContentAreaFilled(false);
                fields[r][c].setBorderPainted(false);

                add(fields[r][c]);
            }
        }
    }

    /**
     * A megadott koordinataju mezo hatteret allitja be a megadott png-re.
     * @param x x koordinata
     * @param y y koordinata
     * @param png png neve
     */
    public void SetBackground(int x, int y, String png){
        fields[x][y].setIcon( new ImageIcon("res/"+png+".png"));
        fields[x][y].setDisabledIcon( new ImageIcon("res/"+png+".png"));
    }
    /**
     *
     * @param field
     */
    public void UpdateField(ArrayList<String> field){
        if(layer_number==0){
            if (!field.get(3).equals("0")) {
                SetBackground(Integer.parseInt(field.get(1)), Integer.parseInt(field.get(2)), "fixer");
                if (!field.get(4).equals("0")) {
                    SetBackground(Integer.parseInt(field.get(1)), Integer.parseInt(field.get(2)), "other");
                }
            } else if (!field.get(4).equals("0")) {
                SetBackground(Integer.parseInt(field.get(1)), Integer.parseInt(field.get(2)), "saboteur");
            } else {
                SetBackground(Integer.parseInt(field.get(1)), Integer.parseInt(field.get(2)), "nothing");
            }
        }
        if(layer_number==1){
            if(field.get(0).equals("main.java.Pipe")){
                if(field.get(10).equals("left") || field.get(10).equals("right")){
                    if(!field.get(7).equals("0")){
                        if(!field.get(8).equals("0")){
                            SetBackground(Integer.parseInt(field.get(1)), Integer.parseInt(field.get(2)), "slipperyundest");
                        }
                        else if(!field.get(9).equals("0")){
                            SetBackground(Integer.parseInt(field.get(1)), Integer.parseInt(field.get(2)), "stickyundest");
                        }
                        else{
                            SetBackground(Integer.parseInt(field.get(1)), Integer.parseInt(field.get(2)), "undestroyable");
                        }
                    }
                    else{
                        if(!field.get(8).equals("0")){
                            SetBackground(Integer.parseInt(field.get(1)), Integer.parseInt(field.get(2)), "slippery");
                        }
                        else if(!field.get(9).equals("0")){
                            SetBackground(Integer.parseInt(field.get(1)), Integer.parseInt(field.get(2)), "sticky");
                        }
                        else{
                            SetBackground(Integer.parseInt(field.get(1)), Integer.parseInt(field.get(2)), "nothing");
                        }
                    }
                }
                else{
                    if(!field.get(7).equals("0")){
                        if(!field.get(8).equals("0")){
                            SetBackground(Integer.parseInt(field.get(1)), Integer.parseInt(field.get(2)), "slipperyundestrotated");
                        }
                        else if(!field.get(9).equals("0")){
                            SetBackground(Integer.parseInt(field.get(1)), Integer.parseInt(field.get(2)), "stickyundestrotated");
                        }
                        else{
                            SetBackground(Integer.parseInt(field.get(1)), Integer.parseInt(field.get(2)), "undestroyablerotated");
                        }
                    }
                    else{
                        if(!field.get(8).equals("0")){
                            SetBackground(Integer.parseInt(field.get(1)), Integer.parseInt(field.get(2)), "slipperyrotated");
                        }
                        else if(!field.get(9).equals("0")){
                            SetBackground(Integer.parseInt(field.get(1)), Integer.parseInt(field.get(2)), "stickyrotated");
                        }
                        else{
                            SetBackground(Integer.parseInt(field.get(1)), Integer.parseInt(field.get(2)), "nothing");
                        }
                    }
                }
            }
            else{
                SetBackground(Integer.parseInt(field.get(1)), Integer.parseInt(field.get(2)), "nothing");
            }

        }
        if(layer_number==2){
            if(field.get(0).equals("main.java.Pipe") && field.get(5).equals("true")){
                if(field.get(10).equals("left") || field.get(10).equals("right")) SetBackground(Integer.parseInt(field.get(1)), Integer.parseInt(field.get(2)), "brokenpipe");
                else SetBackground(Integer.parseInt(field.get(1)), Integer.parseInt(field.get(2)), "brokenpiperotated");
            }
            else if(field.get(0).equals("main.java.Pipe") && field.get(5).equals("false")){
                if(field.get(10).equals("left") || field.get(10).equals("right")) SetBackground(Integer.parseInt(field.get(1)), Integer.parseInt(field.get(2)), "pipe");
                else SetBackground(Integer.parseInt(field.get(1)), Integer.parseInt(field.get(2)), "piperotated");
            }
            else if(field.get(0).equals("main.java.Pump") && field.get(5).equals("true")) {
                SetBackground(Integer.parseInt(field.get(1)), Integer.parseInt(field.get(2)), "brokenpump");
            }
            else if(field.get(0).equals("main.java.Pump") && field.get(5).equals("false")) {
                SetBackground(Integer.parseInt(field.get(1)), Integer.parseInt(field.get(2)), "pump");
            }
            else if(field.get(0).equals("main.java.Fountain")) {
                SetBackground(Integer.parseInt(field.get(1)), Integer.parseInt(field.get(2)), "fountain");
            }
            else if(field.get(0).equals("main.java.Tank")) {
                SetBackground(Integer.parseInt(field.get(1)), Integer.parseInt(field.get(2)), "tank");
            }
            else if(field.get(0).equals("main.java.BlankField")) {
                SetBackground(Integer.parseInt(field.get(1)), Integer.parseInt(field.get(2)), "blankfield");
            }
        }
        if(layer_number==3){
            SetBackground(Integer.parseInt(field.get(1)), Integer.parseInt(field.get(2)), "blankfield");
            if(field.get(0).equals("main.java.Pipe") && field.get(6).equals("true")){
                if(field.get(10).equals("left") || field.get(10).equals("right")) SetBackground(Integer.parseInt(field.get(1)), Integer.parseInt(field.get(2)), "water");
                else SetBackground(Integer.parseInt(field.get(1)), Integer.parseInt(field.get(2)), "waterrotated");
            }
            if(field.get(0).equals("main.java.Pipe") && field.get(6).equals("false")){
                SetBackground(Integer.parseInt(field.get(1)), Integer.parseInt(field.get(2)), "blankfield");
            }
        }
    }

    public void UpdatePlayer(ArrayList<String> player) {
        if (layer_number == 0) {
            ArrayList<String> field = Game.Get().GetMap().GetFields().get(Integer.parseInt(player.get(3))).get(Integer.parseInt(player.get(2))).GetStatus();
            if (!field.get(3).equals("0")) {
                SetBackground(Integer.parseInt(field.get(1)), Integer.parseInt(field.get(2)), "fixercurrent");
                if (!field.get(4).equals("0")) {
                    SetBackground(Integer.parseInt(field.get(1)), Integer.parseInt(field.get(2)), "othercurrent");
                }
            } else if (!field.get(4).equals("0")) {
                SetBackground(Integer.parseInt(field.get(1)), Integer.parseInt(field.get(2)), "saboteurcurrent");
            } else {
                SetBackground(Integer.parseInt(field.get(1)), Integer.parseInt(field.get(2)), "nothing");
            }
        }
    }

}
