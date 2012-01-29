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
    final ReentrantReadWriteLock lock;
    Random random = new Random();

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
        lock.writeLock().lock();
        try {
            aliveCells = new boolean[width][height];
        } finally {
            lock.writeLock().unlock();
        }
    }

    public boolean useDataFrom(GameField template) {

        if (template != null) {
            lock.writeLock().lock();
            try {
                template.lock.readLock().lock();
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
            } finally {
                lock.writeLock().unlock();
            }
            template.lock.readLock().unlock();
            return true;
        } else {
            return false;
        }
    }

    public void printGameField() {
        lock.readLock().lock();
        try {
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (aliveCells[x][y]) {
                        System.out.println("#");
                    } else {
                        System.out.println(" ");
                    }
                }
                System.out.println("\n");
            }
        } finally {
            lock.readLock().unlock();
        }
    }

    public void insertRandomData() {
        lock.writeLock().lock();
        try {
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    aliveCells[x][y] = random.nextBoolean();
                }
            }
        } finally {
            lock.writeLock().unlock();
        }
    }
}