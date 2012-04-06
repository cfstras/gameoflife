package gameoflife;

import static gameoflife.Tools.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * This implements a "double-buffered" game of life ruleset.
 * @author claus
 */
public class GameOfLife extends Thread{
    static boolean endlessField=true;
    
    GameField currentField;
    GameField nextField;
    
    /**
     * This one applies one generation onto the supplied field,
     * saving the results into the generateTo field.
     * @param currentField the field to read data from.
     * @param generateTo the field where the data will be written
     */
    public void generation(GameField currentField, GameField generateTo) {
        if(currentField.width!=generateTo.width || currentField.height!=generateTo.height) {
            //what the holy fuck are you trying to do?
            throw new RuntimeException();
        }
        
        this.currentField=currentField;
        this.nextField=generateTo;
        
        synchronized(generateTo.lock){
        synchronized(currentField.lock) {
        for(int ix=0;ix<currentField.width;ix++) {
            for(int iy=0;iy<currentField.height;iy++) {
                byte numNeighbours=getNumOfNeighbours(currentField, ix, iy);
                if(currentField.aliveCells[ix][iy]) {
                    //i'm alive!
                    if(numNeighbours<2) {
                        generateTo.aliveCells[ix][iy]=false; //alive, less than 2 neighbours --> DEATH
                    } else if(numNeighbours<=3) {
                        generateTo.aliveCells[ix][iy]=true; // alive, 2/3 neighbours --> lives on
                    } else {
                        generateTo.aliveCells[ix][iy]=false; // alive, 4+ neighbours --> overcrowding
                    }
                } else {
                    //i'm dead!
                    if(numNeighbours==3) {
                        generateTo.aliveCells[ix][iy]=true; // dead, 3 neighbours -> new life!
                    } else {
                        generateTo.aliveCells[ix][iy]=false; // anything else won't do nothing.
                    }
                }
            }
        }
        generateTo.generation=currentField.generation+1;
        } }
    }
    
    /**
     * temp value
     */
    byte numNeighbours;
    
    byte getNumOfNeighbours(GameField f,int x,int y) {
        numNeighbours=0;
        addNeighbour(f, x-1, y-1);
        addNeighbour(f, x-1, y);
        addNeighbour(f, x-1, y+1);
        addNeighbour(f, x, y-1);
        addNeighbour(f, x, y+1);
        addNeighbour(f, x+1, y-1);
        addNeighbour(f, x+1, y);
        addNeighbour(f, x+1, y+1);
        
        return numNeighbours;
    }
    
    private void addNeighbour(GameField f,int x,int y) {
        if(endlessField) {
            x=mod(x,f.width);
            y=mod(y,f.height);
        }
        if(x>=0 && x < f.width && y>=0 && y < f.height ) {
            if(f.aliveCells[x][y]) {
                numNeighbours++;
            }
        }
    }

    @Override
    public void run() {
        while (Main.run) {
            if (Main.running) {
                Main.loop();
                try {
                    //wait
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                }
            } else {
                synchronized (this) {
                    try {
                        wait();
                    } catch (InterruptedException ex) {
                    }
                }
            }
        }

    }

    void clearField() {
        boolean running=Main.running;
        Main.running=false;
        synchronized(nextField.lock){ synchronized(currentField.lock) {
            for(int x=nextField.width-1;x>=0;x--){
                for(int y=nextField.height-1;y>=0;y--){
                    nextField.aliveCells[x][y]=false;
                }
            }
        }}
        Main.running=running;
        Main.redraw();
        synchronized(this){
            notifyAll();
        }
    }

    void randomField() {
        boolean running=Main.running;
        Main.running=false;
        synchronized(nextField.lock){ synchronized(currentField.lock) {
            for(int x=nextField.width-1;x>=0;x--){
                for(int y=nextField.height-1;y>=0;y--){
                    nextField.aliveCells[x][y] = Main.random.nextBoolean();
                }
            }
        }}
        Main.running=running;
        Main.redraw();
        synchronized(this){
            notifyAll();
        }
    }
    
    
}