
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static int choice;

    public static int getChoice() {
        return choice;
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        int cutLimit = 1000000000;
        int n;
        int[][] initialBoard;
        Scanner scanner = new Scanner(System.in);
        while (true) {

            n = scanner.nextInt();
            initialBoard = new int[n][];

            for (int i = 0; i < n; i++) {
                initialBoard[i] = new int[n];
            }

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    initialBoard[i][j] = scanner.nextInt();
                }
            }

//		for (int i = 0; i < initialBoard.length; i++) {
//			for (int j = 0; j < initialBoard.length; j++) {
//				System.out.print(initialBoard[i][j]+" ");
//			}
//			
//		}
            Node initNode = new Node(initialBoard, 0, null);

            if (initNode.isSolvable() == false) {
                System.out.println("\nNot solvable \n" + initNode.getInvCount() + "\n" + initNode.getManhattanDistance() + "\n" + initNode.getLinearConflict());
            } else if (initNode.isGoal() == true) {
                System.out.println("Goal node! Nothing to solve!");
            } else {
                System.out.println("choose:  1. Hamming\n2. Manhattan\n3. Linear conflict");
                choice = scanner.nextInt();

                System.out.println("\nsolving....... \n" + initNode.getInvCount() + "\n" + initNode.getManhattanDistance() + "\n" + initNode.getLinearConflict());
                BFS bfs = new BFS();
                Node res = bfs.executeBFS(initNode, cutLimit);
                printSolution(res, bfs.explored, bfs.expanded);
            }
          //  scanner.close();
        }

    }

    public static void printSolution(Node res, int explored, int expanded) {
        if (res == null) {
            System.out.print("\nNo solution found.");
        } else {
            System.out.println("\nCost: " + res.movesMade);
            List<Node> path = new ArrayList<Node>();
            Node s = res;
            while (null != s) {
                path.add(s);
                s = s.parentNode;
            }

            int depth = path.size() - 1;
            for (int i = depth; i >= 0; i--) {
                s = path.get(i);
                if (s.isGoal()) {
                    s.printArray();
                } else {
                    s.printArray();
                    System.out.print("==========> \n");
                }
            }
            System.out.println("\nDepth: " + depth + "\nExplored: " + explored + "\nExpanded: " + expanded + "\n");
        }
    }

}
