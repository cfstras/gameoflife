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
    
    
    /**
     * Inits the renderer and gives it a field,
     * just to set the right size parameters and draw an initial image.
     * 
     * @param field 
     */
    public void init(GameField field);
    
    /**
     * The drawField method is invoked once for each generation that should be rendered
     * and passes the GameField containing the new generation along.
     * Notice that these get overwritten every other generation (since we are using a so-called "double-buffer")
     * @param field 
     */
    public void drawField(GameField field);
    
    
}
