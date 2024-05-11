import java.awt.*;
import javax.swing.*;

/**
 * A jatekszabalyokat, a jatekmenetet, valamint a jatek keszitoit is bemutato ablak.
 */
public class HelpFrame extends JFrame {
    /**
     * A jatek cime
     */
    private JLabel game;
    /**
     * A jatekmenetet, a jatekszabalyokat bemutato szoveg.
     */
    private JLabel infos;
    /**
     * A jatek keszitoinek nevei.
     */
    private JLabel credits;
    /**
     * Az ablak konstruktora.
     */
    public HelpFrame() {
        super("Raid: Shadow Deserts");
        setResizable(false);
        setLocation(540, 400);
        initComponents();
        pack();
        setVisible (true);
    }

    /**
     * Az ablak komponenseinek inicializalasa.
     */
    private void initComponents() {
        JPanel jp = new JPanel();
        // Construct components
        game = new JLabel("Raid: shadow deserts");
        infos = new JLabel("<html>" +
                "A jatekot ket csapatban, 4-8 fo jatszhatja osszesen." +
                "<br>A szabotorok dolga, hogy minel tobb viz jusson a sivatagba, a szereloke pedig, hogy a ciszternakba." +
                "<br>Az a csapat nyer, amelyik elobb eleri a cel pontszamot." +
                "<br>Egy korben felvaltva jonnek a szerelok es szabotorok. Minden jatekosnak 3 akcioja van osszesen." +
                "<br>Egy korben egy egysegnyi, azaz egy csonyi viz folyik tovabb a kor vegen. " +
                "<br>Ha a kor vegen van viz kilyukasztott, vagy nem csatlakoztatott csoben, akkor az kifolyik." +
                "<br>" +
                "<br>Alul lathato a jelenlegi jatekos es a mezo, amin all statusza, illetve a pontok." +
                "<br>Bal oldalt a 11 akciogomb van, es alatta a 4 irany gomb." +
                "<br>Csak azok a gombok aktivak, amely akciok az aktiv jatekos altal jelenleg elvegezhetok." +
                "<br>A kovetkezo akciok azon a mezon vegzodnek el, amelyiken a jatekos all:" +
                "<br>Sabotage pipe, Fix, Make sticky, Make slippery, Carry pipe es Carry pump." +
                "<br>Move, Set pump direction, Remove pipe, Place megnyomasa utan aktivak lesznek azok az iranygombok," +
                "<br>amely iranyokban elvegezheto az adott akcio. Set pump direction eseteben 2 iranyt is valasztani kell." +
                "<br>" +
                "<br>Move: atleptet az adott iranyban levo mezore, homokra nem lehet lepni." +
                "<br>Set pump direction: az elso valasztott irany lesz a pumpa bemenete, a masodik a kimenete." +
                "<br>Sabotage pipe: kilyukasztja a csovet, ha a kor vegen van benne viz, a szabotorok pontot kapnak." +
                "<br>Fix: csak szereloknek, megjavitja a csovet, par korig kilyukaszthatatlanna valik a cso." +
                "<br>Make sticky: aki belelep par korig nem mozdulhat el, az akcio vegrehajtojat nem erinti." +
                "<br>Make slippery: csak szabotoroknek, aki ralep, az egy szomszedos mezore csuszik, ha tud." +
                "<br>Carry pipe: csak szereloknek, elvesz egy csovet a tankbol, lerakhato homok helyere." +
                "<br>Carry pump: csak szereloknek, elvesz egy pumpat a tankbol, lerakhato homok es cso helyere." +
                "<br>Remove pipe: csak szereloknek, felvesz egy lent levo csovet az adott iranybol, ujbol lerakhato." +
                "<br>Place: csak szereloknek, lerakja a nala levo elemet az adott iranyba." +
                "<br>Pass: lenullazod a hatralevo akcioid szamat, a kovetkezo jatekos jon." +
                "<br>" +
                "<br>Ciszterna: itt kezdenek a szerelok, ha viz folyik bele, azert a szerelok pontot kapnak." +
                "<br>Alapbol van benne 5 cso és 1 pumpa, kor vegen kis esellyel termel ujakat." +
                "<br>Forras: itt kezdenek a szabotorok. Kor vegen vizet tovabbit a szomszedaiba, kiveve homokba." +
                "<br>Pumpa: random el tud romlani, ilyenkor nem tovabbit vizet, es nem allithato az iranya." +
                "<br>Cso: Viz tovabbitas iranya a legutobb az adott csovonalhoz tartozo pumpan beallitotttol fugg." +
                "<br>Egyszerre csak egy jatekos allhat rajta." +
                "</html>");
        credits = new JLabel("Credits: Sinkler Szilveszter, Karsai Artur, Horvath Dominik, Andras Gergely, Schnellein Gabor");

        // Adjust size and set layout
        jp.setPreferredSize(new Dimension(600, 230));
        jp.setLayout(new BorderLayout());

        // Add components to scroll pane
        JScrollPane scrollPane = new JScrollPane(infos);
        scrollPane.setPreferredSize(new Dimension(550, 200));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        jp.add(game, BorderLayout.NORTH);
        jp.add(scrollPane, BorderLayout.CENTER);
        jp.add(credits, BorderLayout.SOUTH);

        getContentPane().add(jp);
    }

}