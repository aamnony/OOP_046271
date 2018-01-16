package homework4;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

import javax.swing.*;

/**
 * ColorGenerator is a Singelton Observeable by Different BillBoards.
 * every 20 seconds it will change its generate a random color and update
 * all of its BillBoards that will in turn update their respective Panels. 
 */
public class ColorGenerator{

    private static ColorGenerator colorGenerator = null;
    private Collection<Billboard> billboards = new HashSet<>();
    private Random random;

    // Abstraction Function:
    // Represents the ColorGenerator for the system.
    // will generate a random color every 20 seconds and update
    // all observers
    
    // Representation Invariant:
    // this class doesn't have any fields that change value during its life. 
    
    /**
     * @effects Constructs a new ColorGenetaor, notice this is a protected method,
     *          can only be called from GetInstance
     * @returns the ColorGenerator instance. 
     */
    protected ColorGenerator() {
        random = new Random();
        
        Timer timer = new Timer(2000, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                Color color = getRandomColor();
                for (Iterator<Billboard> iterator = billboards.iterator(); iterator.hasNext();) {
                    Billboard billboard = iterator.next();
                    billboard.UpdatePanels(color);
                }
            }
        });
        
        timer.start();
    }
    
    /**
     * @returns a random color. 
     */
    private Color getRandomColor() {
        float r = random.nextFloat();
        float g = random.nextFloat();
        float b = random.nextFloat();
        Color randomColor = new Color(r, g, b);
        return randomColor;
    }

    /**
     * @returns the ColorGenerator instance. 
     */
    public static ColorGenerator getInstance() {
        if (colorGenerator == null)
            colorGenerator = new ColorGenerator();
        return colorGenerator;
    } 
    
    /**
     * @effects Add a billboard to the list of BillBoards that
     *          observe ColorGenerator. 
     */
    public void AddBillboard(Billboard billboard) {
        billboards.add(billboard);
    }
}
