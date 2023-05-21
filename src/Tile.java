public class Tile {

    private int value;

    /**
     * Constructor
     * @param value - the tile number.
     */
    public Tile(int value) {
        this.value = value;
    }

    /**
     * Method that shows the number of specific tile
     * @return int value
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Methon that init value to specific tile
     * @return void
     */

    public void setValue(int value){
        this.value = value;
    }
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Tile)) {
            return false;
        }
        Tile tile = (Tile) other;
        return value == tile.getValue();
    }
    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }

}
