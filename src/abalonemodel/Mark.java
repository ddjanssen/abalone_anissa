package abalonemodel;

/**
 * The enum <code>Mark</code> is the tui expression of a marble.
 * The possible values: Mark.XX, Mark.B, Mark.C, Mark.W, Mark.R
 * @author Anissa
 */
public enum Mark {
    
    X, B, C, W, R;

    /**
     * Returns the other mark when there are two players.
     * @ensures that the next Mark or XX is returned.
     * @return the next Mark or if the Mark is XX, return XX.
     */
    public Mark getNextPlayerTwo() {
        switch (this) {
            case B: return W;
            case W: return B;
            default: return X;
        }
    }

    /**
     * Returns the other mark when there are two players. The order is B - C - W.
     * @ensures that the next Mark or XX is returned.
     * @return the next Mark or if the Mark is XX, return XX.
     */
    public Mark getNextPlayerThree() {
        switch (this) {
            case B: return C;
            case C: return W;
            case W: return B;
            default: return X;
        }
    }

    /**
     * Returns the other mark when there are two players. The order is B - C - W - R.
     * @ensures that the next Mark or XX is returned.
     * @return the next Mark or if the Mark is XX, return XX.
     */
    public Mark getNextPlayerFour() {
        switch (this) {
            case B: return C;
            case C: return W;
            case W: return R;
            case R: return B;
            default: return X;
        }
    }
}
