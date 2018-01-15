package homework4;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

import javax.swing.*;


public class ColorGenerator{


    
    private static ColorGenerator colorGenerator = null;
    private Collection<Billboard> billboards = new HashSet<>();
    private Random random;

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
    
    private Color getRandomColor() {
        float r = random.nextFloat();
        float g = random.nextFloat();
        float b = random.nextFloat();
        Color randomColor = new Color(r, g, b);
        return randomColor;
    }
    
    public static ColorGenerator getInstance() {
        if (colorGenerator == null)
            colorGenerator = new ColorGenerator();
        return colorGenerator;
    } 
    
    public void AddBillboard(Billboard billboard) {
        billboards.add(billboard);
    }
}
