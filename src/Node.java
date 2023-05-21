public class Node {
    private State state;
    private Node preNode;
    private Action action;

    private int depth = 0;
    private int distance = 0;

    /**
     * Constructor
     * @param state -  holding the current state
     * @param preNode - the previous node
     * @param action - the action that change the preNode to current node
     */
    public Node(State state, Node preNode, Action action) {
        this.state = state;
        this.preNode = preNode;
        this.action = action;

        calculateHeuristic();
    }

    /**
     * getter that get the action
     * @return - action
     */
    public Action getAction() {
        return action;
    }

    /**
     * getter that get the preNode
     * @return - preNode
     */
    public Node getParent() {
        return preNode;
    }

    /**
     * getter that gets the state
     * @return - this state
     */
    public State getState() {
        return state;
    }

    /**
     * Method that gets a node and create the "sons" of the node
     * @return - Node arr that contains the node "sons"
     */
    public Node[] expand() {
        int[] locEmpty = state.emptyLoc();
        int rowEmpty = locEmpty[0];
        int colEmpty = locEmpty[1];
        Direction[] opt = state.actions(); // [UP],[DOWN],[LEFT]
        Node[] expandNode = new Node[opt.length]; // [0],[0],[0]


        for (int i = 0; i < opt.length; i++) {
            if (opt[i] == Direction.UP) {
                Action newAction = new Action(this.state.getBoard().getTile(rowEmpty + 1,colEmpty),Direction.UP);
                State newState = this.state.result(newAction);
                Node newNode = new Node(newState, this, newAction);

                expandNode[i] = newNode;
            }
            if (opt[i] == Direction.DOWN) {
                Action newAction = new Action(this.state.getBoard().getTile(rowEmpty - 1,colEmpty),Direction.DOWN);
                State newState = this.state.result(newAction);
                Node newNode = new Node(newState, this, newAction);

                expandNode[i] = newNode;
            }
            if (opt[i] == Direction.LEFT) {
                Action newAction = new Action(this.state.getBoard().getTile(rowEmpty,colEmpty + 1),Direction.LEFT);
                State newState = this.state.result(newAction);
                Node newNode = new Node(newState, this, newAction);

                expandNode[i] = newNode;
            }
            if (opt[i] == Direction.RIGHT) {
                Action newAction = new Action(this.state.getBoard().getTile(rowEmpty,colEmpty - 1),Direction.RIGHT);
                State newState = this.state.result(newAction);
                Node newNode = new Node(newState, this, newAction);

                expandNode[i] = newNode;
            }
        }

        return expandNode;
    }
    private int myAbs(int a) {
        if (a < 0)
            return a * -1;

        return a;
    }

    private void calculateHeuristic() {
        Node tmp = this.preNode;
        while (tmp != null) {
            depth++;
            tmp = tmp.preNode;
        }

        Board board = this.state.getBoard();
        int rows = board.getRows();
        int cols = board.getCols();

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                int value = board.getTile(r, c).getValue();
                if (value == 0)
                    continue;

                value -= 1;
                int realCol = value % cols;
                int realRow = value / cols;

                distance += Math.abs(c - realCol) + Math.abs(r - realRow);
            }
        }
    }


    public int heuristicValue() {
        return depth + distance;
    }

}