import java.util.Arrays;

public class Board {

    private Tile[][] tiles;

    public Board(String boardString) {
        String[] splitByRows = boardString.split("\\|");
        int rows = splitByRows.length;

        if (rows == 1) {
            String[] nums = boardString.split(" ");
            int i = nums.length;
            for (int j = 0; j < i; j++) {
                if (nums[j].equals("_")) {
                    nums[j] = "0";
                }
            }
            int[] intArray = new int[i];
            for (int j = 0; j < i; j++) {
                intArray[j] = Integer.parseInt(nums[j]);
            }
            Tile[][] board = new Tile[1][i];

            for (int j = 0; j < i; j++) {
                Tile tempTile = new Tile(intArray[j]);
                board[0][j] = tempTile;
            }
            this.tiles = board;
        } else {
            int[][] boardArray = new int[rows][rows];

            for (int i = 0; i < rows; i++) {
                String[] elements = splitByRows[i].split("\\s+");
                for (int j = 0; j < rows; j++) {
                    boardArray[i][j] = elements[j].equals("_") ? 0 : Integer.parseInt(elements[j]);
                }
            }

            Tile[][] board = new Tile[rows][rows];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < rows; j++) {
                    Tile tempTile = new Tile(boardArray[i][j]);
                    board[i][j] = tempTile;
                }
            }
            this.tiles = board;
        }
    }

    public Board(Board other) {
        int rows = other.tiles.length;
        int cols = other.tiles[0].length;
        this.tiles = new Tile[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.tiles[i][j] = new Tile(other.tiles[i][j].getValue());
            }
        }
    }

    public int getRows() {
        return tiles.length;
    }

    public int getCols() {
        return tiles[0].length;
    }

    public Tile getTile(int row, int col) {
        return tiles[row][col];
    }

    public void setTile(Tile otherTile, int row, int col) {
        tiles[row][col] = otherTile;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Board)) {
            return false;
        }
        Board board = (Board) other;
        return Arrays.deepEquals(tiles, board.tiles);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(tiles);
    }
}