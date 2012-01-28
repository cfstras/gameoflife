/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoflife;

import java.io.Serializable;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *
 * @author claus
 */
public class GameField implements Serializable, Cloneable {
    
    int width, height;
    int generation;
    String name;
    
    boolean [][] aliveCells;
    
    final ReentrantReadWriteLock lock;
    
    
    public GameField(int width, int height, String name, GameField template) {
        this.width=width;
        this.height=height;
        this.name=name;
        lock=new ReentrantReadWriteLock(true);
        initField();
        useDataFrom(template);
    }
    
    public GameField(int width, int height, String name) {
        this(width, height, name, null);
    }
    
    public void initField() {
        lock.writeLock().lock();
        try {
            aliveCells=new boolean[width][height];
        } finally {
            lock.writeLock().unlock();
        }
    }
    
    public boolean useDataFrom(GameField template) {
        
        if(template!=null) {
            lock.writeLock().lock();
            try {
                template.lock.readLock().lock();
                int minWidth=width;
                int minHeight=height;
                if (template.width<width) {
                    minWidth=template.width;
                }
                if (template.height>height) {
                    minWidth=template.height;
                }
                for (int ix=0; ix<minWidth; ix++) {
                    System.arraycopy(template.aliveCells[ix], 0, aliveCells[ix], 0, minHeight);
                }
            } finally {
                lock.writeLock().unlock();
            }
            template.lock.readLock().unlock();
            return true;
        } else {
            return false;
        }
    }
    
}
