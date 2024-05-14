package drukmakoriSzerelok;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.swing.*;

/**
 * A jatek indulasakor megjeleno menu, amelybol a beallitasok es a jatek inditasa valaszthato.
 */
public class MainMenu extends JFrame {
    /**
     * Jatekinditas gomb
     */
    private JButton start;
    /**
     * Beallitasok gomb
     */
    private JButton settings;
    /**
     * Kilepes gomb
     */
    private JButton exit;
    /**
     * A jatek cime
     */
    private JLabel game_name;

    /**
     * konsruktor
     */
    public MainMenu() {
        super("Raid: Shadow Deserts");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(730, 250);
        initComponents();
        pack();
        setVisible (true);
    }

    /**
     * inicializalja a komponenseket
     */
    private void initComponents(){
        JPanel jp=new JPanel();

        JButton background=new JButton();
        start = new JButton ("");
        settings = new JButton ("");
        exit = new JButton ("");
        game_name = new JLabel ("Raid: Shadow Deserts");

        jp.setPreferredSize (new Dimension (512, 512));
        jp.setLayout (null);

        jp.add (start);
        jp.add (settings);
        jp.add (exit);
        jp.add (game_name);
        jp.add(background);
        //inditas gomb listenere
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                GameFrame.Get().setVisible(true);
                Wait.Get().start();
            }
        });
        //beallitasok gomb listenere
        settings.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                SettingsFrame j =new SettingsFrame();
            }
        });
        //kilepes gomb listenere
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    Wait.Get().join();
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        background.setIcon(new ImageIcon("res/"+"1.png"));
        background.setDisabledIcon(new ImageIcon("res/"+"1.png"));
        background.setEnabled(false);
        background.setOpaque(false);
        background.setContentAreaFilled(false);
        background.setBorderPainted(false);
        background.setFocusable(false);
        start.setIcon(new ImageIcon("res/"+"startgame.png"));
        start.setDisabledIcon(new ImageIcon("res/"+"startgame.png"));
        start.setOpaque(false);
        start.setContentAreaFilled(false);
        settings.setIcon(new ImageIcon("res/"+"settings.png"));
        settings.setDisabledIcon(new ImageIcon("res/"+"settings.png"));
        settings.setOpaque(false);
        settings.setContentAreaFilled(false);
        exit.setIcon(new ImageIcon("res/"+"exit.png"));
        exit.setDisabledIcon(new ImageIcon("res/"+"exit.png"));
        exit.setOpaque(false);
        exit.setContentAreaFilled(false);
        background.setBounds (0, 0, 512, 512);
        start.setBounds (146, 180, 220, 50);
        settings.setBounds (146, 295, 220, 50);
        exit.setBounds (146, 410, 220, 50);
        game_name.setBounds (5, 90, 780, 60);
        getContentPane().add(jp);

        Font font=new Font("Consolas", Font.BOLD, 60);
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("res/"+"Desert.ttf"));
        } catch (FontFormatException e) { e.printStackTrace(); } catch (IOException e) { e.printStackTrace(); }
        font = font.deriveFont(Font.BOLD,45);

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(font);

        game_name.setFont(font);
        float[] f=Color.RGBtoHSB(249, 82, 50, null);
        game_name.setForeground(Color.getHSBColor(f[0], f[1], f[2]));
    }
}