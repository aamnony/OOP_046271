package homework4;

public class ColumsStrategy implements ColorChangeStrategy {   
    @Override
    public int[] GetColorChangeOrder() {
        int [] order = {0,5,10,15,20,1,6,11,16,21,2,7,12,17,22,3,8,13,18,23,4,9,14,19,24};
        return order;
    }
}
