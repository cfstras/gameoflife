package gameoflife;

/**
 * This implements a "double-buffered" game of life ruleset.
 * @author claus
 */
public class GameOfLife {
    
    /**
     * This one applies one generation onto the supplied field,
     * saving the results into the generateTo field.
     * @param currentField the field to read data from.
     * @param generateTo the field where the data will be written
     */
    public static void generation(GameField currentField, GameField generateTo) {
        if(currentField.width!=generateTo.width || currentField.height!=generateTo.height) {
            //what the holy fuck are you trying to do?
            throw new RuntimeException();
        }
        
        generateTo.lock.writeLock().lock();
        currentField.lock.readLock().lock();
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
        
        generateTo.lock.writeLock().unlock();
        currentField.lock.readLock().unlock();
    }
    
    /**
     * temp value
     */
    static byte numNeighbours;
    static byte getNumOfNeighbours(GameField f,int x,int y) {
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
    
    private static void addNeighbour(GameField f,int x,int y) {
        if(x>=0 && x < f.width && y>=0 && y < f.height ) {
            if(f.aliveCells[x][y]) {
                numNeighbours++;
            }
        }
    }
    
    
}