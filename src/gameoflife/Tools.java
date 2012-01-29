/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoflife;

/**
 *
 * @author claus
 */
public class Tools {
    public static int mod(int x, int y) {
        int result = (( x % y )+ y) % y ;
        //if (result < 0) {
        //    result += y;
        //}
        return result;
    }
}
