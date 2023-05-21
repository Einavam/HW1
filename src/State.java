public class State {

    private Board board;


    /**
     * Constructor
     * @param board - the board that the state holds
     */
    public State(Board board){
        this.board = board;
    }
    /**
     * Copy constructor
     * @param other - the state that we want to copy
     */

    public State(State other){
        this.board = new Board(other.board);
    }


    /**
     * Method that shows the board that the state holds
     * @return this board
     */

    public Board getBoard(){
        return this.board;
    }

    /**
     * Method that check if the current board is the goal board
     * @return true if the current board is the goal board, and false if it's not.
     */
    public boolean isGoal() {
        //first we check if the last tile is equal to 0
        if (this.board.getTile(this.board.getRows() - 1, this.board.getCols() -1).getValue() != 0)
            return false;

        int curValue = 1;  //define the current number value
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getCols(); j++) {
                if (this.board.getTile(i, j).getValue() != curValue)
                    return false;

                curValue++; //move to the next tile

                if (i == this.board.getRows() - 1 && j == this.board.getCols() - 2)
                    curValue = 0; //when we arrived to the last number before the empty tile, the curNum will be 0.
            }
        }
        return true;
    }

    /**
     * Method that create array of direction
     * @return array of direction
     */
    private Direction[] initDirection() {
        Direction[] directionArr = new Direction[4];
        directionArr[0] = Direction.UP;
        directionArr[1] = Direction.DOWN;
        directionArr[2] = Direction.RIGHT;
        directionArr[3] = Direction.LEFT;
        return directionArr;
    }


    /**
     * check all the possible movement and return all the possible direction
     * @return array of all the possible direction that we can do
     */
    public Direction[] actions() {
        final int LEGAL_MOVE = 1;
        int counter = 0;
        Direction[] result; // result array
        int[] optionDirection = new int[4];
        Direction[] finalDirection = initDirection();

        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getCols(); j++) {
                if (this.board.getTile(i, j).getValue() == 0) {
                    if (i + 1 < board.getRows()) { // checking if its can move up
                        counter++;
                        optionDirection[0] = LEGAL_MOVE;
                    }
                    if (i - 1 >= 0) { // checking if its can move down
                        counter++;
                        optionDirection[1] = LEGAL_MOVE;
                    }
                    if (j - 1 >= 0) { // checking if its can move right
                        counter++;
                        optionDirection[2] = LEGAL_MOVE;
                    }
                    if (j + 1 < board.getCols()) { // checking if its can move left
                        counter++;
                        optionDirection[3] = LEGAL_MOVE;
                    }

                }
            }
        }

        result = new Direction[counter];
        int counterResult = 0;
        for (int i = 0; i < optionDirection.length; i++){
            if (optionDirection[i] == LEGAL_MOVE) {
                result[counterResult] = finalDirection[i];
                counterResult++;
            }
        }
        return result;
    }

    /**
     * Method that performs actions on a state and returns the new state after the action is done
     * @param action - the action that we need to do
     * @return the new state after the action
     */
    public State result(Action action) {
        int rowT = tileLoc(action.getTile())[0];
        int colT = tileLoc(action.getTile())[1];

        if (legalMove(action)){

            State newState = new State(this);

            //check every possible movement direction
            if (action.getDirection() == Direction.UP) {
                Tile tmpTile = newState.getBoard().getTile(rowT, colT);
                Tile repTile = newState.getBoard().getTile(rowT - 1, colT); //where the empty tile is
                newState.getBoard().setTile(tmpTile , rowT - 1 , colT); //change the empty tile to the action tile
                newState.getBoard().setTile(repTile , rowT , colT); //change the previous tile to the empty

                return newState; //return the new state after the action
            }

            if(action.getDirection() == Direction.DOWN) {
                Tile tmpTile = newState.getBoard().getTile(rowT, colT);
                Tile repTile = newState.getBoard().getTile(rowT + 1 , colT);
                newState.getBoard().setTile(tmpTile,rowT + 1 , colT);
                newState.getBoard().setTile(repTile , rowT , colT);

                return newState;
            }

            if(action.getDirection() == Direction.RIGHT){
                Tile tmpTile = newState.getBoard().getTile(rowT, colT);
                Tile repTile = newState.getBoard().getTile(rowT , colT + 1);
                newState.getBoard().setTile(tmpTile , rowT ,colT + 1);
                newState.getBoard().setTile(repTile , rowT , colT);

                return newState;
            }
            if(action.getDirection() == Direction.LEFT){
                Tile tmpTile = newState.getBoard().getTile(rowT , colT);
                Tile repTile = newState.getBoard().getTile(rowT , colT - 1);
                newState.getBoard().setTile(tmpTile, rowT,colT - 1);
                newState.getBoard().setTile(repTile, rowT , colT);

                return newState;
            }
        }
        return this;
    }



    /**
     * Method that checks in a state if action is legal
     * @param action the current action that we want to commit
     * @return true if the action and false if it is not
     */
    public boolean legalMove(Action action) {
        int rowT = tileLoc(action.getTile())[0]; //the row of the tile that needs to move
        int colT = tileLoc(action.getTile())[1]; //the col of the tile that needs to move
        int emptyRow = emptyLoc()[0]; //the row of the empty tile
        int emptyCol = emptyLoc()[1]; //the col of the empty tile

        //divide into cases - the empty tile is above/ under/ right/ left to the current tile
        if(action.getDirection() == Direction.UP) {
            if (rowT - 1 == emptyRow && colT == emptyCol) {
                return true;
            }
        }
        else if(action.getDirection() == Direction.DOWN) {
            if (rowT + 1 == emptyRow && colT == emptyCol) {
                return true;
            }
        }
        else if(action.getDirection() == Direction.RIGHT) {
            if (rowT == emptyRow && colT + 1 == emptyCol) {
                return true;
            }
        }
        else if(action.getDirection() == Direction.LEFT) {
            if (rowT  == emptyRow && colT - 1 == emptyCol) {
                return true;
            }
        }
        return false;
    }


    /**
     *Method that represents the col and row of a tile
     * @param tile the tile
     * @return array of 2 values - the index of the row and the col.
     */
    public int [] tileLoc(Tile tile) {
        int loc[] = new int[2];
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getCols(); j++) {
                if (board.getTile(i, j).getValue() == tile.getValue()){
                    loc[0] = i;
                    loc[1] = j;
                    return loc;
                }
            }
        }
        return loc;
    }


    /**
     * Method that give us the location (index of row and col) of the empty tile
     * @return the index of the row and the index of the col, in array with 2 values
     */
    public int [] emptyLoc(){
        int[] res = new int[2];
        for(int i = 0; i < board.getRows();i++){
            for(int j = 0; j < board.getCols(); j++){
                if(board.getTile(i,j).getValue() == 0){ //checking if we found the empty tile
                    res[0] = i;
                    res[1] = j;
                    return res;
                }
            }
        }
        return new int [] {0,0};
    }


    @Override
    public boolean equals(Object other) {
        if (!(other instanceof State)) {
            return false;
        }
        State otherState = (State) other;
        return board.equals(otherState.board);
    }

    @Override
    public int hashCode() {
        return board.hashCode();
    }
}