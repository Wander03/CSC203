import java.util.Objects;

public class Node {
// g: Distance of current node from start + Distance from current node to adjacent node
// f: g + h
// h: Heuristic distance to end node (dist formula)

    private double g;
    private double f;
    private double h;
    private Node prior;
    private final Point loc;

    public Node(double g, double h, double f, Point loc, Node prior) {
        this.g = g;
        this.f = f;
        this.h = h;
        this.loc = loc;
        this.prior = prior;
    }

    public double getG() {return g;}
    public double getF() {return f;}
    public double getH() {return h;}
    public Point getLoc() {return loc;}
    public Node getPrior() {return prior;}

    public void setG(double g) {this.g = g;}
    public void setF(double f) {this.f = f;}
    public void setH(double h) {this.h = h;}
    public void setPrior(Node prior) {this.prior = prior;}

    public String toString()
    {
        return "Node(g:" + g + ", h:" + h + ", f:" + f + ", loc: " + loc + ")\n\t\tprior: " + prior + "\n";
    }

    public boolean equals(Object other)
    {
        if (other == null)
            return false;
        if (other.getClass() != this.getClass())
            return false;

        Node n = (Node)other;
        return g == n.g && h == n.h && f == n.f && Objects.equals(loc, n.loc) && Objects.equals(prior, n.prior);
    }

    public int hashCode()
    {
        return Objects.hash(g, h, f, loc, prior);
    }

}
