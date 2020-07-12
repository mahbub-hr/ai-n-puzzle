
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Node implements Comparable<Node> {

    public int[][] board;
    public int movesMade;
    public Node parentNode;

    public Node(int[][] arr, int movesMade, Node parentNode) {
        board = new int[arr.length][];
        for (int i = 0; i < arr.length; i++) {
            board[i] = new int[arr.length];
            for (int j = 0; j < arr.length; j++) {
                board[i][j] = arr[i][j];
            }
        }

        this.movesMade = movesMade;
        this.parentNode = parentNode;
    }

    public boolean isGoal() {

        if (board[board.length - 1][board.length - 1] != 0) {
            return false;
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                int a = i * board.length + j + 1;

                if (board[i][j] != 0 && board[i][j] != a) {
                    return false;
                }
            }
        }

        return true;

    }

    public boolean isSolvable() {

        int boardSize = board.length * board.length;
        int invCount = getInvCount();
        int zeroRow = 0;

        outerloop:
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == 0) {
                    zeroRow = i;
                    break outerloop;
                }
            }
        }

        if (((boardSize % 2) == 0) && ((invCount + zeroRow) % 2) == 1) {

            return true;
        } else if (((boardSize % 2) == 1) && ((invCount % 2) == 0)) {
            return true;
        }

        return false;
    }

    public int getInvCount() {
        int inv_count = 0;
        int size = board.length * board.length;
        int arr[] = new int[size];

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                // System.out.println(i+" "+j);
                arr[(i * board.length) + j] = board[i][j];

            }
        }

        for (int i = 0; i < size - 1; i++) {
            for (int j = i + 1; j < size; j++) {
                // count pairs(i, j) such that i appears
                // before j, but i > j.
                if ((arr[j] != 0) && (arr[i] != 0) && (arr[i] > arr[j])) {
                    inv_count++;
                }
            }
        }

        return inv_count;
    }

    public int getHammingDistance() {
        int hammingDistace = 0;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if ((board[i][j] != 0) && (board[i][j] != (i * board.length + j + 1))) {
                    hammingDistace++;
                }
            }
        }

        return hammingDistace;
    }

    public int getManhattanDistance() {
        int distance = 0;
        int row = -1;
        int col = -1;
        int value = 0;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {

                if (board[i][j] != 0) {

                    value = board[i][j];
                    row = (value - 1) / board.length;
                    col = (value - 1) % board.length;
                    //System.out.println(value+": "+Math.abs(row - i) +"," +Math.abs(col - j));
                    distance += (Math.abs(row - i) + Math.abs(col - j));

                }
            }
        }
        return distance;
    }

    public int getLinearConflict() {
        int conf = 0;
        conf = getManhattanDistance();
        int dim = board.length;
        for (int i = 0; i < dim; i++) {

            int max = -1;

            for (int j = 0; j < dim; j++) {

                int cellValue = board[i][j];

                if (cellValue != 0 && ((cellValue - 1) / dim) == i) {

                    if (cellValue > max) {
                        max = cellValue;
                    } else {
                        conf += 2;
                    }
                }
            }
        }

        for (int column = 0; column < dim; column++) {

            int max = -1;
            for (int row = 0; row < dim; row++) {

                int cellValue = board[row][column];

                if (cellValue != 0 && cellValue % dim == column + 1) {
                    if (cellValue > max) {
                        max = cellValue;
                    } else {
                        conf += 2;
                    }
                }

            }

        }
        return conf;
    }

    public List<Node> getChildren() {

        List<Node> children = new ArrayList<Node>();

        int row = -1;
        int column = -1;

        outerloop:
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == 0) {
                    row = i;
                    column = j;
                    break outerloop;
                }
            }
        }

        if (row == -1 || column == -1) {
            System.out.println("returning null children");
            return null;
        }

        // System.out.println("Row "+row+" column "+column+"\n board
        // "+Arrays.toString(board));
        if ((row + 1) < board.length) {
            int[][] arr = new int[board.length][];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = new int[board.length];
            }

            arrayCopy(board, arr);
            // System.out.println("why null "+row+" hi"+arr[row+1][column]);
            arr[row][column] = arr[row + 1][column];
            arr[row + 1][column] = 0;
            Node node = new Node(arr, movesMade + 1, this);
            children.add(node);
        }

        if ((column + 1) < board.length) {
            int[][] arr = new int[board.length][];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = new int[board.length];
            }

            arrayCopy(board, arr);
            arr[row][column] = arr[row][column + 1];
            arr[row][column + 1] = 0;
            Node node = new Node(arr, movesMade + 1, this);
            children.add(node);
        }

        if ((column - 1) >= 0) {
            int[][] arr = new int[board.length][];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = new int[board.length];
            }

            arrayCopy(board, arr);
            arr[row][column] = arr[row][column - 1];
            arr[row][column - 1] = 0;
            Node node = new Node(arr, movesMade + 1, this);
            children.add(node);
        }

        if ((row - 1) >= 0) {
            int[][] arr = new int[board.length][];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = new int[board.length];
            }

            arrayCopy(board, arr);

            // printArray();
            arr[row][column] = arr[row - 1][column];
            arr[row - 1][column] = 0;
            Node node = new Node(arr, movesMade + 1, this);
            children.add(node);
        }
        return children;
    }

    public int getCost() {
        switch (Main.getChoice()) {
            case 1:
                return movesMade + getHammingDistance();
            case 2:
                return movesMade + getManhattanDistance();
            case 3:
                return movesMade + getLinearConflict();
            default:
                System.out.println("Invalid choice!");
                return -1;
        }
    }

    @Override
    public int compareTo(Node o) {
        // TODO Auto-generated method stub
        if (this.getCost() > o.getCost()) {
            return 1;
        } else if (this.getCost() < o.getCost()) {
            return -1;
        } else {
            return 0;
        }
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Node)) {
            return false;
        }
        Node other = (Node) obj;

        for (int i = 0; i < other.board.length; i++) {
            for (int j = 0; j < other.board.length; j++) {
                if (board[i][j] != other.board[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + Arrays.deepHashCode(this.board);
        hash = 13 * hash + this.movesMade;
        hash = 13 * hash + Objects.hashCode(this.parentNode);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        String separator = ",";
        
        for (int i = 0; i < board.length; ++i) {
            result.append('[');
            for (int j = 0; j < board[i].length; ++j) {
                if (j > 0) {
                    result.append(board[i][j]);
                } else {
                    result.append(board[i][j]).append(separator);
                }
            }
            result.append(']');
        }
        
        return result.toString();
    }

    public void arrayCopy(int[][] src, int[][] dst) {
        for (int i = 0; i < dst.length; i++) {
            for (int j = 0; j < dst.length; j++) {
                dst[i][j] = src[i][j];
            }
        }
    }

    public void printArray(int[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                System.out.print(arr[i][j] + "		");
            }
            System.out.println();
        }
    }

    public void printArray() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                System.out.print(board[i][j] + "  ");
            }
            System.out.println();
        }
    }

}
