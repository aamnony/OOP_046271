package homework4;
import java.util.Random;

public class RandomStrategy implements ColorChangeStrategy {
    @Override
    public int[] GetColorChangeOrder() {
        int [] order = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24};
        
        Random rgen = new Random();

        for (int i=0; i<order.length; i++) {
            int randomPosition = rgen.nextInt(order.length);
            int temp = order[i];
            order[i] = order[randomPosition];
            order[randomPosition] = temp;
        }
        
        return order;
    }
}
