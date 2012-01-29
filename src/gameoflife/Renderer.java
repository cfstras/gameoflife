/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoflife;

/**
 *
 * @author claus
 */
public interface Renderer {
    
    public void init(GameField field);
    
    public void drawField(GameField field);
    
    
}
