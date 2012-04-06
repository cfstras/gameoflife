package gameoflife;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Random;
import javax.swing.*;

public class SwingGUI extends JPanel implements Renderer, MouseListener, MouseMotionListener {
    
    private static final long serialVersionUID = -2294685016438617741L;
    private static final Random r = new Random();
    int width, height;
    int pxwidth, pxheight;
    double pixelPerCell=10.0;
    String name;
    GameField field;
    JFrame f;
    JLabel gen;
    ControlGUI controlGUI;
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        synchronized(field.lock){
            if (field != null) {
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
        pxwidth=(int)(width*pixelPerCell);
        pxheight=(int)(height*pixelPerCell);
        setPreferredSize(new Dimension(pxwidth, pxheight));
        invalidate();
        repaint();
        f.setLayout(new BorderLayout(5,5));
        f.add(this,BorderLayout.LINE_START);
        controlGUI= new ControlGUI();
        f.add(controlGUI,BorderLayout.LINE_END);
        f.pack();
        
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        
        f.setVisible(true);
        
    }
    
    @Override
    public void drawField(GameField field) {
        this.field=field;
        f.invalidate();
        f.repaint();
    }
    
    /**
     * calculates the position in the grid a pixel is at.
     * 
     * @param pos the value of the pixel's position
     * @param dimension which dimension the pixel is in (x=0, y=1)
     * @return the grid position or -1 if an invalid argument is supplied
     */
    public int pixelToPosition(int pos, int dimension){
        int gpos;
        switch(dimension){
            case 0:
                gpos=(int)(pos/pixelPerCell);
                break;
            case 1:
                gpos=(int)(pos/pixelPerCell);
                // this switch could be useful.
                break;
            default:
                return -1;
       }
       return gpos;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
        //System.out.println("hit: button"+e.getButton()+" pos:"+e.getPoint());
        if(e.getButton()>1)
            return;
        int x =pixelToPosition(e.getX(), 0);
        int y= pixelToPosition(e.getY(), 1);
        
        if(x>=width||y>=height||x<0||y<0)
            return;
        
        synchronized(field.lock){
            if(e.isShiftDown()){
                field.aliveCells[x][y]=false;
            } else {
                field.aliveCells[x][y]=true;
            }
        }
        e.consume();

        f.invalidate();
        f.repaint();
    }

    boolean mouseDown=false;
    
    
    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton()==1){
            mouseDown=true;
            //System.out.println("MouseDown");
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getButton()==1){
            mouseDown=false;
            //System.out.println("MouseUp");
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseClicked(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(mouseDown){
            mouseClicked(e);
        }
    }
}
