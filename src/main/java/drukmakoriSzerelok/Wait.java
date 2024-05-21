package drukmakoriSzerelok;

/**
 * Várakozast iranyito es vegrehajto osztaly.
 */
public class Wait extends Thread{
    /**
     * Singleton biztositasa.
     */
    private static Wait single_instance = null;

    public Wait(){
        setDaemon(true);
    }
    /**
     * Singleton.
     * @return az osztaly egyetlen peldanya
     */
    public static Wait Get()
    {
        // To ensure only one instance is created
        if (single_instance == null) {
            single_instance = new Wait();
        }
        return single_instance;
    }

    /**
     * Varakozo allapotbol felebresztes.
     */
    public synchronized void WakeUp() {
        notifyAll();
    }
    /**
     * Varaakozas.
     */
    public synchronized void Wait() {
        try {
            wait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Inditas.
     */
    public synchronized void run() {
        Game.Get().StartGame();
    }
}
