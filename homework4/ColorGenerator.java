package homework4;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Observable;

import javax.swing.*;

/**
 * ColorGenerator is a Singleton Observable by Different BillBoards. every 20
 * seconds it will generate a random color and update all of its BillBoards that
 * will in turn update their respective Panels.
 */
public class ColorGenerator extends Observable{

    private static ColorGenerator colorGenerator = null;
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
    private ColorGenerator() {
        random = new Random();
        
        Timer timer = new Timer(2000, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                Color color = getRandomColor();
                setChanged();
                notifyObservers(color);
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
        if (colorGenerator == null) colorGenerator = new ColorGenerator();
        return colorGenerator;
    }
}
