package homework1;

import java.awt.Dimension;

/**
 * Thrown to indicate that a {@link Shape} cannot be resized to a specified
 * dimension. <br>
 * Contains an optional alternative dimension that is supported by the
 * {@link Shape}.
 */
public class ImpossibleSizeException extends IllegalArgumentException {

    private static final long serialVersionUID = -2878406534744720082L;
    
    private Dimension alternativeDimension;

    /**
     * @effects Initializes this with no alternative {@link Dimension}.
     */
    public ImpossibleSizeException() {
        this.alternativeDimension = null;
    }

    /**
     * @effects Initializes this with a given alternative {@link Dimension}.
     */
    public ImpossibleSizeException(Dimension alternativeDimension) {
        this.alternativeDimension = alternativeDimension;
    }

    public Dimension getAlternativeDimension() {
        return alternativeDimension;
    }
}
