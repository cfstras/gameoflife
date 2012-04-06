package gameoflife;

import java.io.Serializable;
import java.util.Random;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *
 * @author claus, morth
 */
public class GameField implements Serializable, Cloneable {

    int width, height;
    int generation;
    String name;
    boolean[][] aliveCells;
    final Object lock;
    
    char alive = '\u25A0';
    char dead = '\u25A1';

    public GameField(int width, int height, String name, GameField template) {
        this.width = width;
        this.height = height;
        this.name = name;
        lock = new ReentrantReadWriteLock(true);
        initField();
        useDataFrom(template);
    }

    public GameField(int width, int height, String name) {
        this(width, height, name, null);
    }

    public void initField() {
        synchronized(lock) {
            aliveCells = new boolean[width][height];
        }
    }

    public boolean useDataFrom(GameField template) {

        if (template != null) {
            synchronized(lock) {
                synchronized(template.lock){
                    int minWidth = width;
                    int minHeight = height;
                    if (template.width < width) {
                        minWidth = template.width;
                    }
                    if (template.height > height) {
                        minWidth = template.height;
                    }
                    for (int ix = 0; ix < minWidth; ix++) {
                        System.arraycopy(template.aliveCells[ix], 0, aliveCells[ix], 0, minHeight);
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public void printGameField() {
        synchronized(lock){
            System.out.println("Printing " + name + " to System.out");
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (aliveCells[x][y]) {
                        System.out.print(alive+ " ");
                    } else {
                        System.out.print(dead + " ");
                    }
                }
                System.out.println();

            }
        }
    }

    
}