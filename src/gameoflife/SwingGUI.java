package gameoflife;

import java.awt.*;
import java.util.Random;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.swing.*;

public class SwingGUI extends JPanel implements Renderer {
    
    private static final long serialVersionUID = -2294685016438617741L;
    private static final Random r = new Random();
    int width, height;
    String name;
    GameField field;
    int datax, datay;
    JFrame f;
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        field.lock.readLock().lock();
        if (field != null) {
            try {
                datax = 0;
                datay = 0;
                for (int y = 12; y < 25 * width - 25; y += 30) {
                    for (int x = 12; x < 25 * height - 25; x += 30) {
                        if (field.aliveCells[datax++][datay]) {
                            g.setColor(Color.BLACK);
                        } else {
                            g.setColor(Color.WHITE);
                        }
                        g.fillRect(x, y, 25, 25);
                        g.setColor(Color.WHITE);
                        g.drawRect(x - 1, y - 1, 25, 25);
                    }
                    datay++;
                }
            } finally {
                field.lock.readLock().unlock();
            }
        }
    }
    
    public SwingGUI(int width, int height, String name) {
        this.width = width;
        this.height = height;
        this.name = name;
    }
    
    @Override
    public void init(GameField field) {
        this.field = field;
        invalidate();
        repaint();
    }
    
    @Override
    public void drawField(GameField field) {
        f = new JFrame("GUI: " + name);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(height * 30, width * 30);
        f.add(this);
        f.setVisible(true);
    }
}
