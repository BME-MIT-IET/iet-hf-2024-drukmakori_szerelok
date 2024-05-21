package drukmakoriSzerelok;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 * A jatek beallitasait lehet itt megadni, mint a jatekosok szama, az elerehto pontok szama,
 * a jatekosok nevei egyesevel beallithatoak.
 */
public class SettingsFrame extends JFrame implements ActionListener{
    private JLabel settings;
    private JLabel points;
    private JLabel points_error;
    private JButton setPointsToWin;
    private JButton setPlayerCount;
    private JLabel players;
    private JLabel players_error;
    private JLabel saboteur;
    private JLabel fixer;
    private JTextField players_tb;
    private JTextField name;
    private JTextField name2;
    private JTextField name3;
    private JTextField name4;
    private JTextField name5;
    private JTextField name6;
    private JTextField name7;
    private JTextField name8;
    private JButton set3;
    private JButton set4;
    private JButton set5;
    private JButton set6;
    private JButton set7;
    private JButton set8;
    private JButton set9;
    private JButton set10;
    private JTextField points_tb;
    /**
     * A beallitasokat tartalmazo ablak konstruktora.
     */
    public SettingsFrame() {
        super("Raid: Shadow Deserts");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocation(490, 200);
        initComponents();
        pack();
        setVisible (true);
    }

    private void initComponents(){
        JButton background=new JButton();
        JButton back;
        JButton s1;
        JButton s2;
        JButton s4;
        JButton s3;
        JButton f1;
        JButton f2;
        JButton f3;
        JButton f4;

        JPanel jp=new JPanel();
        settings = new JLabel ("Settings");
        points = new JLabel ("Points to win");
        points_error = new JLabel ("Between 50 and 500");
        setPointsToWin = new JButton ("");
        setPlayerCount = new JButton ("");
        players = new JLabel ("Players/team");
        players_error = new JLabel ("Between 2 and 4");
        saboteur = new JLabel ("Saboteur names");
        fixer = new JLabel ("Fixer names");
        players_tb = new JTextField ("2",5);
        name = new JTextField (5);
        name2 = new JTextField (5);
        name3 = new JTextField (5);
        name4 = new JTextField (5);
        name5 = new JTextField (5);
        name6 = new JTextField (5);
        name7 = new JTextField (5);
        name8 = new JTextField (5);
        set3 = new JButton ("");
        set4 = new JButton ("");
        set5 = new JButton ("");
        set7 = new JButton ("");
        set6 = new JButton ("");
        set8 = new JButton ("");
        set10 = new JButton ("");
        set9 = new JButton ("");
        points_tb = new JTextField ("100", 5);
        back = new JButton ("");
        s1 = new JButton ("");
        s2 = new JButton ("");
        s4 = new JButton ("");
        s3 = new JButton ("");
        f1 = new JButton ("");
        f2 = new JButton ("");
        f3 = new JButton ("");
        f4 = new JButton ("");

        s1.setEnabled (false);
        s2.setEnabled (false);
        s4.setEnabled (false);
        s3.setEnabled (false);
        f1.setEnabled (false);
        f2.setEnabled (false);
        f3.setEnabled (false);
        f4.setEnabled (false);
        s1.setIcon(new ImageIcon("res/saboteur1.png"));
        s1.setDisabledIcon(new ImageIcon("res/saboteur1.png"));
        s2.setIcon(new ImageIcon("res/saboteur2.png"));
        s2.setDisabledIcon(new ImageIcon("res/saboteur2.png"));
        s3.setIcon(new ImageIcon("res/saboteur3.png"));
        s3.setDisabledIcon(new ImageIcon("res/saboteur3.png"));
        s4.setIcon(new ImageIcon("res/saboteur4.png"));
        s4.setDisabledIcon(new ImageIcon("res/saboteur4.png"));
        f1.setIcon(new ImageIcon("res/fixer1.png"));
        f1.setDisabledIcon(new ImageIcon("res/fixer1.png"));
        f2.setIcon(new ImageIcon("res/fixer2.png"));
        f2.setDisabledIcon(new ImageIcon("res/fixer2.png"));
        f3.setIcon(new ImageIcon("res/fixer3.png"));
        f3.setDisabledIcon(new ImageIcon("res/fixer3.png"));
        f4.setIcon(new ImageIcon("res/fixer4.png"));
        f4.setDisabledIcon(new ImageIcon("res/fixer4.png"));

        jp.setPreferredSize (new Dimension (930, 647));
        jp.setLayout (null);
        jp.add (settings);
        jp.add (points);
        jp.add (points_error);
        jp.add (setPointsToWin);
        jp.add (setPlayerCount);
        jp.add (players);
        jp.add (players_error);
        jp.add (saboteur);
        jp.add (fixer);
        jp.add (players_tb);
        jp.add (name);
        jp.add (name2);
        jp.add (name3);
        jp.add (name4);
        jp.add (name5);
        jp.add (name6);
        jp.add (name7);
        jp.add (name8);
        jp.add (set3);
        jp.add (set4);
        jp.add (set5);
        jp.add (set7);
        jp.add (set6);
        jp.add (set8);
        jp.add (set10);
        jp.add (set9);
        jp.add (points_tb);
        jp.add (back);
        jp.add (s1);
        jp.add (s2);
        jp.add (s4);
        jp.add (s3);
        jp.add (f1);
        jp.add (f2);
        jp.add (f3);
        jp.add (f4);
        jp.add(background);

        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                MainMenu j =new MainMenu();
            }
        });

        setPointsToWin.addActionListener(this);
        setPlayerCount.addActionListener(this);
        set3.addActionListener(this);
        set4.addActionListener(this);
        set5.addActionListener(this);
        set6.addActionListener(this);
        set7.addActionListener(this);
        set8.addActionListener(this);
        set9.addActionListener(this);
        set10.addActionListener(this);

        //set component bounds (only needed by Absolute Positioning)
        background.setBounds (0, 0, 930, 647);
        settings.setBounds (390, 5, 240, 100);
        points.setBounds (160, 110, 100, 25);
        points_error.setBounds (415, 112, 580, 25);
        setPointsToWin.setBounds (325, 110, 70, 30);
        setPlayerCount.setBounds (325, 190, 70, 30);
        players.setBounds (160, 190, 100, 25);
        players_error.setBounds (415, 194, 580, 25);
        saboteur.setBounds (130, 245, 200, 25);
        fixer.setBounds (645, 245, 200, 25);
        players_tb.setBounds (260, 190, 60, 30);
        name.setBounds (90, 290, 130, 25);
        name2.setBounds (90, 355, 130, 25);
        name3.setBounds (90, 420, 130, 25);
        name4.setBounds (90, 485, 130, 25);
        name5.setBounds (580, 290, 130, 25);
        name6.setBounds (580, 355, 130, 25);
        name7.setBounds (580, 420, 130, 25);
        name8.setBounds (580, 485, 130, 25);
        set3.setBounds (225, 290, 55, 25);
        set4.setBounds (225, 355, 55, 25);
        set5.setBounds (225, 420, 55, 25);
        set7.setBounds (715, 290, 55, 25);
        set6.setBounds (225, 485, 55, 25);
        set8.setBounds (715, 355, 55, 25);
        set10.setBounds (715, 485, 55, 25);
        set9.setBounds (715, 420, 55, 25);
        points_tb.setBounds (260, 110, 60, 30);
        back.setBounds (305, 565, 360, 50);
        s1.setBounds (295, 270, 60, 60);
        s2.setBounds (295, 335, 60, 60);
        s4.setBounds (295, 465, 60, 60);
        s3.setBounds (295, 400, 60, 60);
        f1.setBounds (785, 270, 60, 60);
        f2.setBounds (785, 335, 60, 60);
        f3.setBounds (785, 400, 60, 60);
        f4.setBounds (785, 465, 60, 60);

        getContentPane().add(jp);
        buttonsDisabler();

        background.setIcon(new ImageIcon("res/2.png"));
        background.setDisabledIcon(new ImageIcon("res/2.png"));
        background.setEnabled(false);
        background.setOpaque(false);
        background.setContentAreaFilled(false);
        background.setBorderPainted(false);
        background.setFocusable(false);
        SetButtonStyle(back, "mainmenu");
        SetButtonStyle(setPlayerCount, "setbig");
        SetButtonStyle(setPointsToWin, "setbig");
        SetButtonStyle(set3, "set");
        SetButtonStyle(set4, "set");
        SetButtonStyle(set5, "set");
        SetButtonStyle(set6, "set");
        SetButtonStyle(set7, "set");
        SetButtonStyle(set8, "set");
        SetButtonStyle(set9, "set");
        SetButtonStyle(set10, "set");

        float[] blue=Color.RGBtoHSB(6, 21, 90, null);
        float[] white=Color.RGBtoHSB(255, 233, 206, null);
        fixer.setForeground(Color.getHSBColor(blue[0], blue[1], blue[2]));
        saboteur.setForeground(Color.getHSBColor(white[0], white[1], white[2]));
        fixer.setFont(new Font("DIALOG", Font.BOLD, 18));
        saboteur.setFont(new Font("DIALOG", Font.BOLD, 18));
        players_error.setForeground(Color.getHSBColor(white[0], white[1], white[2]));
        players_error.setFont(new Font("DIALOG", Font.BOLD, 13));
        points_error.setFont(new Font("DIALOG", Font.BOLD, 13));
        players.setForeground(Color.getHSBColor(white[0], white[1], white[2]));
        points.setForeground(Color.getHSBColor(blue[0], blue[1], blue[2]));
        points_error.setForeground(Color.getHSBColor(blue[0], blue[1], blue[2]));
        players.setFont(new Font("DIALOG", Font.BOLD, 15));
        points.setFont(new Font("DIALOG", Font.BOLD, 15));
        TextFieldStlye(points_tb, blue, white);
        TextFieldStlye(players_tb, blue, white);
        TextFieldStlye(name, blue, white);
        TextFieldStlye(name2, blue, white);
        TextFieldStlye(name3, blue, white);
        TextFieldStlye(name4, blue, white);
        TextFieldStlye(name5, blue, white);
        TextFieldStlye(name6, blue, white);
        TextFieldStlye(name7, blue, white);
        TextFieldStlye(name8, blue, white);

        settings.setFont(new Font("DIALOG", Font.BOLD, 55));
        settings.setForeground(Color.getHSBColor(blue[0], blue[1], blue[2]));
    }

    public void TextFieldStlye(JTextField field, float[] color, float[] color2){
        field.setFont(new Font("DIALOG", Font.BOLD, 13));
        field.setBackground(Color.getHSBColor(color[0], color[1], color[2]));
        field.setForeground(Color.getHSBColor(color2[0], color2[1], color2[2]));
    }
    public void SetButtonStyle(JButton button, String img){
        button.setIcon(new ImageIcon("res/"+img+".png"));
        button.setOpaque(false);
        button.setContentAreaFilled(false);
    }
    /**
     * A gombnyomasokat kezelo metodus
     * @param e gombnyomas
     */
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton)e.getSource();
        //Gyozelemhez szukseges pontok beallitasa
        if(button == setPointsToWin){
            try {
                Game.Get().SetPointsToWin(Integer.parseInt(points_tb.getText()));
                points_error.setText("Between 50 and 500, set to "+points_tb.getText()+" points");
            } catch (Exception ex) {
                points_error.setText("Must be between 50 and 500, please enter numbers only!");
            }
        }
        //Jatekosszam beallitasa
        if(button == setPlayerCount) {
            try {
                Game.Get().ResetPlayers();
                Game.Get().SetPlayerCount(Integer.parseInt(players_tb.getText()));
            } catch (Exception ex) {
                players_error.setText("Must be between 2 and 4, please enter numbers only!");
            }
            buttonsDisabler();
            name.setText("");
            name2.setText("");
            name3.setText("");
            name4.setText("");
            name5.setText("");
            name6.setText("");
            name7.setText("");
            name8.setText("");
        }

        if(button == set3) {
            boolean used=false;
            if(name.getText().equals("")){
                used=true;
                name.setText("must not be empty!");
            }
            ArrayList<Player> playerList=Game.Get().GetPlayers();
            for(int i=0;i<playerList.size();i++){
                if(playerList.get(i).GetReferenceID().equals(name.getText()) && i!=1)
                {
                    name.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 10));
                    name.setText("Name already in use");
                    used=true;
                }
            }
            if(used==false)
                Game.Get().GetPlayers().get(1).SetReferenceID(name.getText());
        }

        if(button == set4) {
            boolean used=false;
            if(name2.getText().equals("")){
                used=true;
                name2.setText("must not be empty!");
            }
            ArrayList<Player> playerList=Game.Get().GetPlayers();
            for(int i=0;i<playerList.size();i++){
                if(playerList.get(i).GetReferenceID().equals(name2.getText()) && i!=3)
                {
                    name2.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 10));
                    name2.setText("Name already in use");
                    used=true;
                }
            }
            if(used==false)
                Game.Get().GetPlayers().get(3).SetReferenceID(name2.getText());
        }

        if(button == set5) {
            boolean used=false;
            if(name3.getText().equals("")){
                used=true;
                name3.setText("must not be empty!");
            }
            ArrayList<Player> playerList=Game.Get().GetPlayers();
            for(int i=0;i<playerList.size();i++){
                if(playerList.get(i).GetReferenceID().equals(name3.getText()) && i!=5)
                {
                    name3.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 10));
                    name3.setText("Name already in use");
                    used=true;
                }
            }
            if(!used)
                Game.Get().GetPlayers().get(5).SetReferenceID(name3.getText());
        }

        if(button == set6) {
            boolean used=false;
            if(name4.getText().equals("")){
                used=true;
                name4.setText("must not be empty!");
            }
            ArrayList<Player> playerList=Game.Get().GetPlayers();
            for(int i=0;i<playerList.size();i++){
                if(playerList.get(i).GetReferenceID().equals(name4.getText()) && i!=7)
                {
                    name4.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 10));
                    name4.setText("Name already in use");
                    used=true;
                }
            }
            if(used==false)
                Game.Get().GetPlayers().get(7).SetReferenceID(name4.getText());
        }
        if(button == set7) {
            boolean used=false;
            if(name5.getText().equals("")){
                used=true;
                name5.setText("must not be empty!");
            }
            ArrayList<Player> playerList=Game.Get().GetPlayers();
            for(int i=0;i<playerList.size();i++){
                if(playerList.get(i).GetReferenceID().equals(name5.getText()) && i!=0)
                {
                    name5.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 10));
                    name5.setText("Name already in use");
                    used=true;
                }
            }
            if(used==false)
                Game.Get().GetPlayers().get(0).SetReferenceID(name5.getText());
        }
        if(button == set8) {
            boolean used=false;
            if(name6.getText().equals("")){
                used=true;
                name6.setText("must not be empty!");
            }
            ArrayList<Player> playerList=Game.Get().GetPlayers();
            for(int i=0;i<playerList.size();i++){
                if(playerList.get(i).GetReferenceID().equals(name6.getText()) && i!=2)
                {
                    name6.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 10));
                    name6.setText("Name already in use");
                    used=true;
                }
            }
            if(used==false)
                Game.Get().GetPlayers().get(2).SetReferenceID(name6.getText());
        }
        if(button == set9) {
            boolean used=false;
            if(name7.getText().equals("")){
                used=true;
                name7.setText("must not be empty!");
            }
            ArrayList<Player> playerList=Game.Get().GetPlayers();
            for(int i=0;i<playerList.size();i++){
                if(playerList.get(i).GetReferenceID().equals(name7.getText()) && i!=4)
                {
                    name7.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 10));
                    name7.setText("Name already in use");
                    used=true;
                }
            }
            if(used==false)
                Game.Get().GetPlayers().get(4).SetReferenceID(name7.getText());
        }
        if(button == set10) {
            boolean used=false;
            if(name8.getText().equals("")){
                used=true;
                name8.setText("must not be empty!");
            }
            ArrayList<Player> playerList=Game.Get().GetPlayers();
            for(int i=0;i<playerList.size();i++){
                if(playerList.get(i).GetReferenceID().equals(name8.getText()) && i!=6)
                {
                    name8.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 10));
                    name8.setText("Name already in use");
                    used=true;
                }
            }
            if(used==false)
                Game.Get().GetPlayers().get(6).SetReferenceID(name8.getText());
        }
    }

    private void buttonsDisabler() {
        if(Game.Get().getPlayerCount()==2){
           set5.setEnabled(false);
           set6.setEnabled(false);
           set9.setEnabled(false);
           set10.setEnabled(false);
           name3.setEditable(false);
           name4.setEditable(false);
           name7.setEditable(false);
           name8.setEditable(false);
        }
        if(Game.Get().getPlayerCount()==3){
            set5.setEnabled(true);
            set6.setEnabled(false);
            set9.setEnabled(true);
            set10.setEnabled(false);
            name3.setEditable(true);
            name4.setEditable(false);
            name7.setEditable(true);
            name8.setEditable(false);
        }
        if(Game.Get().getPlayerCount()==4){
            set5.setEnabled(true);
            set6.setEnabled(true);
            set9.setEnabled(true);
            set10.setEnabled(true);
            name3.setEditable(true);
            name4.setEditable(true);
            name7.setEditable(true);
            name8.setEditable(true);
        }
    }
}