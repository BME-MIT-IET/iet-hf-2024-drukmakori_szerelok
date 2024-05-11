import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.*;

/**
 * A játék grafikus felületét megvalósító osztály.
 * Ez az osztaly felel a jatek iranyitasaert.
 */
public class GameFrame extends JFrame implements ActionListener{
    private GameField gamefield_layers[];
    private JMenuBar menu;
    private JButton up;
    private JButton down;
    private JButton right;
    private JButton left;
    private JButton sticky;
    private JButton slippery;
    private JButton pumpdir;
    private JButton fix;
    private JButton sabotage;
    private JButton carrypipe;
    private JButton carrypump;
    private JButton place;
    private JButton carryactpipe;
    private JLabel currentaction;
    private JButton move;
    private JButton pass;
    private JLabel spoints;
    private JLabel fpoints;
    private JLabel towin;
    private JLabel fieldstatus;
    private JLabel finfos;
    private JLabel playerstatus;
    private JLabel pinfos;
    private JButton playericon;
    private JButton []fieldicon;
    private JLabel endSign;
    private static GameFrame single_instance = null;

    public static GameFrame Get()
    {
        // To ensure only one instance is created
        if (single_instance == null) {
            single_instance = new GameFrame();
        }
        return single_instance;
    }

    public GameFrame() {
        super("Raid: Shadow Deserts");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(250, 60);
        setResizable(false);
        initComponents();
        pack();
    }

    public void initComponents(){
        JButton background=new JButton();
        background.setEnabled(false);
        background.setIcon(new ImageIcon("background.png"));
        background.setDisabledIcon(new ImageIcon("background.png"));
        background.setContentAreaFilled(false);
        background.setBorderPainted(false);
        background.setOpaque(false);


        JPanel jp=new JPanel();

        //construct preComponents
        JMenuItem helpMenu = new JMenuItem ("Help");
        helpMenu.setForeground(Color.WHITE);
        helpMenu.setBackground(Color.getHSBColor(0, 0, 0.18f));

        JMenuItem exitItem = new JMenuItem ("Exit");
        exitItem.setForeground(Color.WHITE);
        exitItem.setBackground(Color.getHSBColor(0, 0, 0.18f));

        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        helpMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                HelpFrame hf=new HelpFrame();
            }
        });


        //construct components
        gamefield_layers = new GameField[4];
        for(int i=0; i<4; i++){
            gamefield_layers[i]=new GameField(i);
        }

        JButton playerBackground=new JButton();
        JButton fieldBackground=new JButton();
        JButton pointBackground=new JButton();
        menu = new JMenuBar();
        menu.add (exitItem);
        menu.add (helpMenu);
        up = new JButton ("");
        down = new JButton ("");
        right = new JButton ("");
        left = new JButton ("");
        sticky = new JButton ("Make Sticky");
        slippery = new JButton ("Make Slippery");
        pumpdir = new JButton ("Set Pump Direction");
        fix = new JButton ("Fix");
        sabotage = new JButton ("Sabotage pipe");
        carrypipe = new JButton ("Carry pipe");
        carrypump = new JButton ("Carry pump");
        place = new JButton ("Place");
        carryactpipe = new JButton ("Remove pipe");
        currentaction = new JLabel ("Current action", SwingConstants.CENTER);
        move = new JButton ("Move");
        pass = new JButton ("Pass");
        spoints = new JLabel ("Saboteur points: 0");
        fpoints = new JLabel ("Fixer points: 0");
        towin = new JLabel ("Points to win: " + Game.Get().GetPointsToWin());
        fieldstatus = new JLabel ("Field status: ");
        finfos = new JLabel ("*field infos*");
        playerstatus = new JLabel ("Player status:");
        pinfos = new JLabel ("");
        playericon = new JButton ("");
        fieldicon=new JButton[3];
        for(int i=0; i<3; i++){
            fieldicon[i] = new JButton ("");
        }
        endSign = new JLabel ("", SwingConstants.CENTER);

        sticky.addActionListener(this);
        slippery.addActionListener(this);
        pumpdir.addActionListener(this);
        fix.addActionListener(this);
        sabotage.addActionListener(this);
        carrypipe.addActionListener(this);
        carrypump.addActionListener(this);
        place.addActionListener(this);
        carryactpipe.addActionListener(this);
        move.addActionListener(this);
        pass.addActionListener(this);
        up.addActionListener(this);
        down.addActionListener(this);
        left.addActionListener(this);
        right.addActionListener(this);

        //set components properties
        SetButtonsDisabled();

        //adjust size and set layout
        jp.setPreferredSize (new Dimension (1420, 900));
        jp.setLayout (null);


        jp.add (endSign);
        //add components
        for(int i=0; i<4; i++){
            jp.add (gamefield_layers[i]);
        }
        jp.add (menu);
        jp.add (up);
        jp.add (down);
        jp.add (right);
        jp.add (left);
        jp.add (sticky);
        jp.add (slippery);
        jp.add (pumpdir);
        jp.add (fix);
        jp.add (sabotage);
        jp.add (carrypipe);
        jp.add (carrypump);
        jp.add (place);
        jp.add (carryactpipe);
        jp.add (currentaction);
        jp.add (move);
        jp.add (pass);
        jp.add (spoints);
        jp.add (fpoints);
        jp.add (towin);
        jp.add (fieldstatus);
        jp.add (finfos);
        jp.add (playerstatus);
        jp.add (pinfos);
        jp.add (playericon);
        for(int i=0; i<3; i++){
            jp.add (fieldicon[i]);
        }
        jp.add(playerBackground);
        jp.add(fieldBackground);
        jp.add(pointBackground);

        jp.add(background);
        playericon.setEnabled(false);

        for(int i=0; i<3; i++){
            fieldicon[i].setEnabled(false);
            fieldicon[i].setContentAreaFilled(false);
            fieldicon[i].setBorderPainted(false);
            fieldicon[i].setOpaque(false);
        }

        //set component bounds (only needed by Absolute Positioning)
        for(int i=0; i<4; i++){
            gamefield_layers[i].setBounds (220, 30, 1184, 700);

            gamefield_layers[i].setOpaque(false);
        }

        menu.setBounds (0, 0, 99, 25);
        up.setBounds (81, 620, 56, 56);
        down.setBounds (81, 677, 56, 56);
        right.setBounds (136, 677, 56, 56);
        left.setBounds (26, 677, 56, 56);
        move.setBounds (17, 35, 185, 40);
        pumpdir.setBounds (17, 85, 185, 40);
        sabotage.setBounds (17, 135, 185, 40);
        fix.setBounds (17, 185, 185, 40);
        sticky.setBounds (17, 235, 185, 40);
        slippery.setBounds (17, 285, 185, 40);
        carrypipe.setBounds (17, 335, 185, 40);
        carrypump.setBounds (17, 385, 185, 40);
        carryactpipe.setBounds (17, 435, 185, 40);
        place.setBounds (17, 485, 185, 40);
        pass.setBounds (17, 535, 185, 40);
        currentaction.setBounds (17, 585, 185, 25);
        spoints.setBounds (1155, 800, 165, 25);
        fpoints.setBounds (1186, 830, 140, 25);
        towin.setBounds (1178, 770, 200, 25);
        fieldstatus.setBounds (720, 750, 140, 25);
        finfos.setBounds (815, 752, 270, 180);
        playerstatus.setBounds (185, 770, 140, 25);
        pinfos.setBounds (293, 770, 260, 140);
        playericon.setBounds (45, 750, 130, 130);
        for(int i=0; i<3; i++){
            fieldicon[i].setBounds (580, 750, 130, 130);
        }
        endSign.setBounds (400, 150, 800, 400);
        background.setBounds (0, 0, 1500, 900);
        getContentPane().add(jp);

        ImageIcon leftImg=new ImageIcon("left.png");
        ImageIcon disabledLeftImg=new ImageIcon("leftdisabled.png");
        ImageIcon btn=new ImageIcon("button.png");
        ImageIcon disabledbtn=new ImageIcon("disabledbutton.png");
        SetButtonStyle(left, leftImg, disabledLeftImg, 0);
        SetButtonStyle(right, leftImg, disabledLeftImg, 180);
        SetButtonStyle(up, leftImg, disabledLeftImg, 90);
        SetButtonStyle(down, leftImg, disabledLeftImg, 270);
        SetButtonStyle(move, btn, disabledbtn, 0);
        SetButtonStyle(pumpdir, btn, disabledbtn, 0);
        SetButtonStyle(sabotage, btn, disabledbtn, 0);
        SetButtonStyle(fix, btn, disabledbtn, 0);
        SetButtonStyle(sticky, btn, disabledbtn, 0);
        SetButtonStyle(slippery, btn, disabledbtn, 0);
        SetButtonStyle(carrypipe, btn, disabledbtn, 0);
        SetButtonStyle(carrypump, btn, disabledbtn, 0);
        SetButtonStyle(carryactpipe, btn, disabledbtn, 0);
        SetButtonStyle(place, btn, disabledbtn, 0);
        SetButtonStyle(pass, btn, disabledbtn, 0);
        currentaction.setForeground(Color.ORANGE);
        pinfos.setForeground(Color.ORANGE);
        finfos.setForeground(Color.ORANGE);
        pinfos.setVerticalAlignment(JLabel.TOP);
        playerstatus.setVerticalAlignment(JLabel.TOP);
        playerstatus.setForeground(Color.ORANGE);
        pinfos.setFont(new Font("DIALOG", Font.BOLD, 14));
        playerstatus.setFont(new Font("DIALOG", Font.BOLD, 14));
        fieldstatus.setFont(new Font("DIALOG", Font.BOLD, 14));
        fpoints.setForeground(Color.CYAN);
        spoints.setForeground(Color.YELLOW);
        fpoints.setFont(new Font("DIALOG", Font.BOLD, 15));
        spoints.setFont(new Font("DIALOG", Font.BOLD, 15));
        towin.setForeground(Color.GREEN);
        towin.setFont(new Font("DIALOG", Font.BOLD, 15));
        fieldstatus.setForeground(Color.ORANGE);
        fieldstatus.setVerticalAlignment(JLabel.TOP);
        finfos.setFont(new Font("DIALOG", Font.BOLD, 12));
        finfos.setVerticalAlignment(JLabel.TOP);
        endSign.setFont(new Font("DIALOG", Font.BOLD, 50));
        endSign.setForeground(Color.GREEN);

        playericon.setContentAreaFilled(false);
        playericon.setBorderPainted(false);
        playericon.setOpaque(false);


        playerBackground.setEnabled(false);
        playerBackground.setContentAreaFilled(false);
        playerBackground.setBorderPainted(false);
        playerBackground.setOpaque(false);
        playerBackground.setBounds (37, 742, 470, 146);
        playerBackground.setIcon(new ImageIcon("bigbutton.png"));
        playerBackground.setDisabledIcon(new ImageIcon("bigbutton.png"));
        fieldBackground.setEnabled(false);
        fieldBackground.setContentAreaFilled(false);
        fieldBackground.setBorderPainted(false);
        fieldBackground.setOpaque(false);
        fieldBackground.setBounds (572, 742, 470, 146);
        fieldBackground.setIcon(new ImageIcon("bigbutton.png"));
        fieldBackground.setDisabledIcon(new ImageIcon("bigbutton.png"));
        pointBackground.setEnabled(false);
        pointBackground.setContentAreaFilled(false);
        pointBackground.setBorderPainted(false);
        pointBackground.setOpaque(false);
        pointBackground.setBounds (998, 742, 470, 146);
        pointBackground.setIcon(new ImageIcon("middlebutton.png"));
        pointBackground.setDisabledIcon(new ImageIcon("middlebutton.png"));
    }

    public void SetButtonStyle(JButton button, ImageIcon img, ImageIcon disabledimg, int angle ){
        button.setIcon(rotateImageIcon(img, angle));
        button.setDisabledIcon(rotateImageIcon(disabledimg, angle));
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusable(false);
        button.setHorizontalTextPosition(JButton.CENTER);
        button.setVerticalTextPosition(JButton.CENTER);
        button.setForeground(Color.ORANGE);
    }

    public void DrawPlayer(Player player){
        ArrayList<String> status=player.GetStatus();
        gamefield_layers[0].UpdatePlayer(status);
        pinfos.setText("<html>Name: "+status.get(0)+"<br>"+
                "Role: "+status.get(1)+"<br>"+
                "Remaining action points: "+status.get(4));
        if(status.get(1).equals("fixer")) {
            pinfos.setText(pinfos.getText()+"<br>Has active field: "+status.get(5)+"</html>");
        }
        for(int i=0; i<3; i++){
            DrawFieldIcon(i, player.GetField());
        }

        playericon.setIcon(new ImageIcon(player.GetPictureName()));
        playericon.setDisabledIcon(new ImageIcon(player.GetPictureName()));
    }

    /**
     * Kirajzolja a mezo kepet
     * @param layer_number
     * @param f
     */
    public void DrawFieldIcon(int layer_number, Field f){
        ArrayList<String> field=f.GetStatus();
        if(layer_number==0){
            if(field.get(0).equals("Pipe")){
                if(field.get(10).equals("left") || field.get(10).equals("right")){
                    if(!field.get(7).equals("0")){
                        if(!field.get(8).equals("0")){
                            SetIcon(layer_number, "slipperyundestbig");
                        }
                        else if(!field.get(9).equals("0")){
                            SetIcon(layer_number, "stickyundestbig");
                        }
                        else{
                            SetIcon(layer_number, "undestroyablebig");
                        }
                    }
                    else{
                        if(!field.get(8).equals("0")){
                            SetIcon(layer_number, "slipperybig");
                        }
                        else if(!field.get(9).equals("0")){
                            SetIcon(layer_number, "stickybig");
                        }
                        else{
                            SetIcon(layer_number, "nothingbig");
                        }
                    }
                }
                else{
                    if(!field.get(7).equals("0")){
                        if(!field.get(8).equals("0")){
                            SetIcon(layer_number, "slipperyundestbigrotated");
                        }
                        else if(!field.get(9).equals("0")){
                            SetIcon(layer_number, "stickyundestbigrotated");
                        }
                        else{
                            SetIcon(layer_number, "undestroyablebigrotated");
                        }
                    }
                    else{
                        if(!field.get(8).equals("0")){
                            SetIcon(layer_number, "slipperybigrotated");
                        }
                        else if(!field.get(9).equals("0")){
                            SetIcon(layer_number, "stickybigrotated");
                        }
                        else{
                            SetIcon(layer_number, "nothingbig");
                        }
                    }
                }
            }
            else{
                SetIcon(layer_number, "nothing");
            }

        }
        if(layer_number==1){
            if(field.get(0).equals("Pipe") && field.get(5).equals("true")){
                if(field.get(10).equals("left") || field.get(10).equals("right")) SetIcon(layer_number, "brokenpipebig");
                else SetIcon(layer_number, "brokenpipebigrotated");
            }
            else if(field.get(0).equals("Pipe") && field.get(5).equals("false")){
                if(field.get(10).equals("left") || field.get(10).equals("right")) SetIcon(layer_number, "pipebig");
                else SetIcon(layer_number, "pipebigrotated");
            }
            else if(field.get(0).equals("Pump") && field.get(5).equals("true")) {
                SetIcon(layer_number, "brokenpumpbig");
            }
            else if(field.get(0).equals("Pump") && field.get(5).equals("false")) {
                SetIcon(layer_number, "pumpbig");
            }
            else if(field.get(0).equals("Fountain")) {
                SetIcon(layer_number, "fountainbig");
            }
            else if(field.get(0).equals("Tank")) {
                SetIcon(layer_number, "tankbig");
            }
            else if(field.get(0).equals("BlankField")) {
                SetIcon(layer_number, "blankfieldbig");
            }
        }
        if(layer_number==2){
            SetIcon(layer_number, "blankfieldbig");
            if(field.get(0).equals("Pipe") && field.get(6).equals("true")){
                if(field.get(10).equals("left") || field.get(10).equals("right")) SetIcon(layer_number, "waterbig");
                else SetIcon(layer_number, "waterrotatedbig");
            }
            if(field.get(0).equals("Pipe") && field.get(6).equals("false")){
                SetIcon(layer_number, "blankfieldbig");
            }
        }
    }
    public void DrawField(Field field){
        ArrayList<String> status=field.GetStatus();
        for(int i=0; i<4; i++){
            gamefield_layers[i].UpdateField(status);
        }

        finfos.setText("<html>Type: "+status.get(0)+"<br>"+
                "Number of fixers: "+status.get(3)+"<br>"+
                "Number of saboteurs: "+status.get(4));
        if(status.get(0).equals("Pipe")) {
            finfos.setText(finfos.getText() + "<br>Broken: " + status.get(5) +
                "<br>Has water: " + status.get(6) +
                "<br>Undestroyable for: " + status.get(7) + " round(s)" +
                "<br>Slippery for: " + status.get(8) + " round(s)" +
                "<br>Sticky for: " + status.get(9) + " round(s)" + "</html>");
        }
        else if(status.get(0).equals("Pump")) {
            finfos.setText(finfos.getText() + "<br>Broken: " + status.get(5) +
                    "<br>Stored water: " + status.get(6) +
                    "<br>In direction: " + status.get(7) +
                    "<br>Out direction: " + status.get(8) + "</html>");
        }
        else if(status.get(0).equals("Tank")) {
            finfos.setText(finfos.getText() + "<br>Number of pipes: " + status.get(5) +
                    "<br>Number of pumps: " + status.get(6) + "</html>");
        }
    }

    /**
     * Kiirja a csapatok pontjait
     */
    public void DrawPoints(){
        fpoints.setText("Fixer points: " + Integer.toString(Game.Get().GetFixerPoints()));
        spoints.setText("Saboteur points: " + Integer.toString(Game.Get().GetSaboteurPoints()));
    }
    public void EnableActions(ArrayList<String> actions){
        pass.setEnabled(true);
        if(actions.contains("move"))
            move.setEnabled(true);
        if(actions.contains("remove pipe"))
            carryactpipe.setEnabled(true);
        if(actions.contains("place active"))
            place.setEnabled(true);
        if(actions.contains("fix"))
            fix.setEnabled(true);
        if(actions.contains("make sticky"))
            sticky.setEnabled(true);
        if(actions.contains("make slippery"))
            slippery.setEnabled(true);
        if(actions.contains("sabotage pipe"))
            sabotage.setEnabled(true);
        if(actions.contains("carry pump"))
            carrypump.setEnabled(true);
        if(actions.contains("carry pipe"))
            carrypipe.setEnabled(true);
        if(actions.contains("set pump"))
            pumpdir.setEnabled(true);
        if(actions.contains("up")) {
            up.setEnabled(true);
            pass.setEnabled(false);
        }
        if(actions.contains("down")) {
            down.setEnabled(true);
            pass.setEnabled(false);
        }
        if(actions.contains("left")) {
            left.setEnabled(true);
            pass.setEnabled(false);
        }
        if(actions.contains("right")) {
            right.setEnabled(true);
            pass.setEnabled(false);
        }
    }

    /**
     * A gombok megvalositasa
     * @param e esemeny
     */
    public void actionPerformed(ActionEvent e) {
        GameFrame.Get().ResetFinfos();
        SetButtonsDisabled();
        JButton button = (JButton)e.getSource();
        if(currentaction.getText().equals("Set In direction 1st"))
            currentaction.setText(button.getText() + "Set Out direction");
        else
            currentaction.setText(button.getText());
        if(button == move)
            Game.Get().GetActivePlayer().GetField().SetPerformAction("move");
        if(button == sticky)
            Game.Get().GetActivePlayer().GetField().SetPerformAction("make sticky");
        if(button == slippery)
            Game.Get().GetActivePlayer().GetField().SetPerformAction("make slippery");
        if(button == fix)
            Game.Get().GetActivePlayer().GetField().SetPerformAction("fix");
        if(button == sabotage)
            Game.Get().GetActivePlayer().GetField().SetPerformAction("sabotage pipe");
        if(button == pumpdir){
            Game.Get().GetActivePlayer().GetField().SetPerformAction("set pump");
            currentaction.setText("Set In direction 1st");
        }
        if(button == carrypipe)
            Game.Get().GetActivePlayer().GetField().SetPerformAction("carry pipe");
        if(button == carrypump)
            Game.Get().GetActivePlayer().GetField().SetPerformAction("carry pump");
        if(button == carryactpipe)
            Game.Get().GetActivePlayer().GetField().SetPerformAction("remove pipe");
        if(button == place)
            Game.Get().GetActivePlayer().GetField().SetPerformAction("place active");
        if(button == pass)
            Game.Get().GetActivePlayer().GetField().SetPerformAction("pass");
        if(button == up)
            Game.Get().GetActivePlayer().GetField().SetPerformAction("up");
        if(button == down)
            Game.Get().GetActivePlayer().GetField().SetPerformAction("down");
        if(button == left)
            Game.Get().GetActivePlayer().GetField().SetPerformAction("left");
        if(button == right)
            Game.Get().GetActivePlayer().GetField().SetPerformAction("right");
        Wait.Get().WakeUp();
    }

    /**
     * Kikapcsolja a gombokat
     */
    public void SetButtonsDisabled(){
        sticky.setEnabled(false);
        slippery.setEnabled(false);
        pumpdir.setEnabled(false);
        fix.setEnabled(false);
        sabotage.setEnabled(false);
        carrypipe.setEnabled(false);
        carrypump.setEnabled(false);
        place.setEnabled(false);
        carryactpipe.setEnabled(false);
        move.setEnabled(false);
        pass.setEnabled(false);
        up.setEnabled(false);
        down.setEnabled(false);
        left.setEnabled(false);
        right.setEnabled(false);
    }

    public GameField GetPlayerLayer(){
        return gamefield_layers[0];
    }

    /**
     * Kitorli a finfos szoveget
     */
    public void ResetFinfos(){
        finfos.setText("");
    }

    /**
     * Fuggveny a kep forgatasahoz
     * @param picture a kep amit forgatni kell
     * @param angle a szog amivel forgatni kell
     * @return a forgatott kep
     */
    static private ImageIcon rotateImageIcon(ImageIcon picture, double angle) {
        if(angle==0) return picture;
        int w = picture.getIconWidth();
        int h = picture.getIconHeight();
        int type = BufferedImage.TYPE_INT_RGB;
        BufferedImage image = new BufferedImage(h, w, type);
        Graphics2D g2 = image.createGraphics();
        double x = (h - w)/2.0;
        double y = (w - h)/2.0;
        AffineTransform at = AffineTransform.getTranslateInstance(x, y);
        at.rotate(Math.toRadians(angle), w/2.0, h/2.0);
        g2.drawImage(picture.getImage(), at, null);
        g2.dispose();
        picture = new ImageIcon(image);
        return picture;
    }

    public JButton[] GetFieldIcon(){
        return fieldicon;
    }


    public void SetIcon(int layer, String png){
        ImageIcon img = new ImageIcon(png+".png");
        fieldicon[layer].setIcon(img);
        fieldicon[layer].setDisabledIcon(img);
    }

    /**
     * Beallitja a jatek veget jelzo szoveget
     * @param msg a szoveg
     */
    public void SetEndSign(String msg){
        endSign.setText(msg);
        if(msg.equals("Game won by Fixers."))
            endSign.setForeground(Color.CYAN);
        else if(msg.equals("Game won by Saboteurs."))
            endSign.setForeground(Color.YELLOW);
        else
        endSign.setForeground(Color.darkGray);
    }
}
