package gameoflife;

import static gameoflife.Tools.*;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
/**
 *
 * @author claus
 */
public class Main {
    
    public static boolean running=false;
    public static boolean run=true;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //GameField life = new GameField(50, 50, "Leben");
        //life.insertRandomData();

        //GameField life = new GameField(10, 10, "Leben");
        //life.insertRandomData();
        //life.printGameField();
        test();
        mainLoop();
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
    
    static GameField current;
    static GameField next;
    static SwingGUI gui;
    static GameOfLife gol;
    static Random random=new Random();
    public static void mainLoop() {
        current= new GameField(50, 50, "Random1");//gliderField();
        next=new GameField(50,50,"Random2");
        try {
            //l&f
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        gui = new SwingGUI(50,50,"GUI");
        gui.init(current);
        
        gol = new GameOfLife();
        gol.currentField=next;
        gol.nextField=current;
        gol.randomField();
        gol.start();
    }
    
    public static void loop() {
        //current.printGameField();
        
        //generation
        gol.generation(current, next);
        //swap buffers
        GameField temp=current;
        current=next;
        next=temp;
        
        gui.controlGUI.genpp();
        gui.drawField(current);
            
    }
    
    public static void test() {
        int x=-1,y=-1;
        
        x=mod(x,10);
        y=mod(y,10);
        System.out.println("x="+x+" y="+y);
        
        
    }

    static void redraw() {
        gui.drawField(current);
    }
    
}
