package gameoflife;

import java.awt.*;
import java.util.Random;
import javax.swing.*;

public class SwingGUI extends JPanel implements Renderer {
    
    private static final long serialVersionUID = -2294685016438617741L;
    private static final Random r = new Random();
    int width, height;
    String name;
    GameField field;
    JFrame f;
    JLabel gen;
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        field.lock.readLock().lock();
        if (field != null) {
            try {
                for (int y = 0; y < width; y++) {
                    for (int x = 0; x < height ; x++) {
                        if (field.aliveCells[x][y]) {
                            g.setColor(Color.BLACK);
                        } else {
                            g.setColor(Color.WHITE);
                        }
                        g.fillRect(x*10, y*10, 10, 10);
                        g.setColor(Color.WHITE);
                    }
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
        f = new JFrame("GUI: " + name);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //f.setSize(height * 10 + 10, width*10 + 30);
        
        setPreferredSize(new Dimension(height * 10, width*10));
        invalidate();
        repaint();
        f.setLayout(new BorderLayout(5,5));
        f.add(this,BorderLayout.LINE_START);
        f.add(new ControlGUI(),BorderLayout.LINE_END);
        f.pack();
        f.setVisible(true);
    }
    
    @Override
    public void drawField(GameField field) {
        this.field=field;
        f.invalidate();
        f.repaint();
    }
}
