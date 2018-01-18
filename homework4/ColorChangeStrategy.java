package homework4;

/**
 * This interface is for implementing different orders for color changing in the
 * Billboard class.
 */
public interface ColorChangeStrategy {
    public int[] getColorChangeOrder();
}
