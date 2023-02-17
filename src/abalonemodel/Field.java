package abalonemodel;

/**
 * Class <code>Field</code> is one of the 61 fields.
 * @author aniss
 *
 */
public class Field {

    private Mark mark;
    private int index;

    /**
     * Creates a new instance of <code>Field</code>.
     * @param index - the position on the field
     * @param mark - the marble on the field. if empty then XX
     */
    public Field(int index, Mark mark) {
        this.index = index;
        this.mark = mark;
    }

    /**
     * returns the mark of field.
     */
    public Mark getMark() {
        return mark;
    }

    /**
     * changes the mark of the field to given Mark m.
     * @param m - the mark that the field must get
     */
    public void setMark(Mark m) {
        this.mark = m;
    }

    /**
     * returns the index, which is the position on the board.
     */
    public int getIndex() {
        return index;
    }

    /**
     * checks if the field is empty.
     * the field.getMark = XX
     */
    public boolean isEmptyField() {
        return getMark() == Mark.X;
    }
}
