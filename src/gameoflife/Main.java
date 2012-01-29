package gameoflife;

/**
 *
 * @author claus
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        GameField life = new GameField(100, 100, "Leben");
        life.insertRandomData();
        life.printGameField();
    }
}
