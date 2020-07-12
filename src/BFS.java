
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class BFS {

    public int explored = 0;
    public int expanded = 0;

    public Node executeBFS(Node iNode, int cutLimit) {

        if (iNode.isGoal()) {
            return iNode;
        }

        PriorityQueue<Node> openList = new PriorityQueue<>();
        //List<Node> closedList = new LinkedList<>();
       // Set<Node> closedList = new HashSet<>();
        HashMap<String, Node> closedList = new HashMap<>();
        openList.add(iNode);
        //closedList.add(iNode);
        closedList.put(iNode.toString(), iNode);
        int num = 0;

        while (true) {

            if (openList.isEmpty()) {
                return null;	// failure
            }

            //System.out.println("running ..........");
            Node Node = openList.poll();
            //closedList.add(Node);
            expanded++;

            num++;

            if (num > cutLimit) {
                return null;
            }

            List<Node> children = Node.getChildren();
            for (Node s : children) {
                if (closedList.containsKey(s.toString()) == false ) {
                    if (s.isGoal() == true) {
                        return s;
                    }
                    openList.add(s);
                    closedList.put(s.toString(),s);
                    explored++;
                }
            }
        }
    }
}
