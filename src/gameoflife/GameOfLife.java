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
    public void generation(GameField currentField, GameField generateTo) {
        if(currentField.width!=generateTo.width || currentField.height!=generateTo.height) {
            //what the holy fuck are you trying to do?
            throw new RuntimeException();
        }
        
        generateTo.lock.writeLock().lock();
        currentField.lock.readLock().lock();
        for(int ix=0;ix<currentField.width;ix++) {
            //TODO
        }
        
            
        generateTo.lock.writeLock().unlock();
        currentField.lock.readLock().unlock();
    }
    
    byte getNumOfNeighbors(GameField f,int x,int y) {
        byte numNeighbors=0;
        
        
        
        return 0;
    }
    
}