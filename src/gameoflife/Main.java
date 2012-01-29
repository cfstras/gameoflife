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

        GameField life = new GameField(50, 50, "Leben");
        life.insertRandomData();
        SwingGUI gui = new SwingGUI(50,50,"GUI");
        gui.init(life);
        gui.drawField(life);

        //GameField life = new GameField(10, 10, "Leben");
        //life.insertRandomData();
        //life.printGameField();
        //mainLoop();
    }
    
    public static GameField gliderField() {
        GameField f=new GameField(10, 10, "glider1");
        f.aliveCells = new boolean[][] { // this is a x-y-swapped glider
        {false,false,false,false,false,false,false,false,false,false},
        {false,false,false,false,false,false,false,false,false,false},
        {false,false,false,false,false,false,false,false,false,false},
        {false,false,false,false,false,false,false,false,false,false},
        {false,false,false,false,true ,false,false,false,false,false},
        {false,false,true ,false,true ,false,false,false,false,false},
        {false,false,false,true ,true ,false,false,false,false,false},
        {false,false,false,false,false,false,false,false,false,false},
        {false,false,false,false,false,false,false,false,false,false},
        {false,false,false,false,false,false,false,false,false,false}
    };
        
        return f;
    }
    
    public static void mainLoop() {
        GameField current=gliderField();
        GameField next=new GameField(10,10,"glider2");
        
        while(true) {
            //print
            System.out.println();System.out.println();System.out.println();System.out.println();
            current.printGameField();
            
            //generation
            GameOfLife.generation(current, next);
            //swap buffers
            GameField temp=current;
            current=next;
            next=temp;
            
            try {
                //wait
                Thread.sleep(1000);
            } catch (InterruptedException ex) {}
        }
        
    }
    
}
