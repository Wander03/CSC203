import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.awt.geom.Point2D;

class   AStarPathingStrategy
        implements PathingStrategy
{


    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors)
    {
        double g = 0;
        double h = Point2D.distance(start.x, start.y, end.x, end.y);

        Comparator<Node> comp = Comparator.comparingDouble(Node::getF);
        List<Point> path = new LinkedList<Point>();
        Node cur = new Node(g, h, g+h, start, null);
        PriorityQueue<Node> openListPQ = new PriorityQueue<>(comp);
        HashMap<Point, Node> openListHM = new HashMap<>();
        HashMap<Point, Node> closedList = new HashMap<>();
        openListPQ.add(cur);
        openListHM.put(cur.getLoc(), cur);

        while (!withinReach.test(cur.getLoc(), end)) {
            Node finalCur = cur;
            potentialNeighbors
                    .apply(cur.getLoc())                                        //Get neighbors
                    .filter(canPassThrough)                                     //valid neighbors
                    .filter(p -> !closedList.containsKey(p))                    //not in closed list
                    .map(p -> {                                                 //create Node out of each neighbor
                        double g_ = finalCur.getG() + 1;
                        double h_ = Point2D.distance(p.x, p.y, end.x, end.y);
                        return new Node(g_, h_, g_+h_, p, finalCur);
                    })
                    .forEach(n -> {
                        if (openListHM.containsKey(n.getLoc())) {               //If node in open list...
                            double cur_g = openListHM.get(n.getLoc()).getG();   //get old g val
                            if (n.getG() < cur_g) {                             //If new g val is smaller, replace
                                Node old = openListHM.replace(n.getLoc(), n);
                                openListPQ.remove(old);
                                openListPQ.add(n);
                            }
                        } else {                                                //If node not in open list, add it
                            openListHM.put(n.getLoc(), n);
                            openListPQ.add(n);
                        }
                    });
            closedList.put(cur.getLoc(), cur);                                  //Add current node to closed list
            cur = openListPQ.poll();                                            //Get next current node
            if (cur == null)                                                    //Return empty list if all nodes checked and no path
                return path;                                                    // Didn't find path
            openListHM.remove(cur.getLoc());
        }

        if (cur.getLoc() == start) {
            path.add(start);
            return path;
        }

        Node next = cur;
        while (next.getPrior().getLoc() != start) {
            path.add(0, next.getLoc());
            next = next.getPrior();
        }
        path.add(0, next.getLoc());

        return path;
    }
}
