public class Action {
    private Tile tile;
    private Direction direction;

    /**
     * Constructor
     * @param tile the tile that we want to do the action
     * @param direction the direction the tile will move
     */

    public Action(Tile tile, Direction direction){
        this.tile = tile;
        this.direction = direction;
    }

    /**
     * Method that gives the direction of the action
     * @return - direction
     */

    public Direction getDirection() {
        return this.direction;
    }

    /**
     * Method that get a tile
     * @return this tile
     */
    public Tile getTile() {
        return this.tile;
    }

    /**
     * Method that prints the action
     * @return the action
     */
    public String toString(){
        return "Move " + this.tile.getValue() +" "+ this.direction.toString();
    }


}
