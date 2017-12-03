package homework1;

import java.awt.Dimension;

/**
 * Thrown to indicate that a {@link Shape} cannot be resized to a specified
 * {@link Dimension}. <br>
 * Contains an optional alternative {@link Dimension} that is supported by the
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

    /**
     * @return the optional alternative {@link Dimension}.
     */
    public Dimension getAlternativeDimension() {
        return alternativeDimension;
    }
}
