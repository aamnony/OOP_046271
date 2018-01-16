package homework4;

/**
 * Implement a color change by two rounds order
 * first round - all even Panels.
 * second round - all uneven Panels. 
 */
public class TwoRoundsStrategy implements ColorChangeStrategy {    
    @Override
    public int[] GetColorChangeOrder() {
        int [] order = {0,2,4,6,8,10,12,14,16,18,20,22,24,1,3,5,7,9,11,13,15,17,19,21,23};
        return order;
    }
}