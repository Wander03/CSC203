import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A simple class representing a location in 2D space.
 */
public final class Point
{
    public final int x;
    public final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "(" + x + "," + y + ")";
    }

    public boolean equals(Object other) {
        return other instanceof Point && ((Point)other).x == this.x
                && ((Point)other).y == this.y;
    }

    public int hashCode() {
        int result = 17;
        result = result * 31 + x;
        result = result * 31 + y;
        return result;
    }

    public boolean adjacent(Point p2) {
        return (x == p2.x && Math.abs(y - p2.y) == 1) || (y == p2.y
                && Math.abs(x - p2.x) == 1);
    }

    public List<Point> getArea(WorldModel world) {
        return AREA_POINTS
                .apply(this)
                .filter(world::withinBounds)
                .collect(Collectors.toList());
    }

    private final Function<Point, Stream<Point>> AREA_POINTS =
            point ->
                    Stream.<Point>builder()
                            .add(new Point(point.x, point.y))
                            .add(new Point(point.x - 1, point.y - 1))
                            .add(new Point(point.x + 1, point.y + 1))
                            .add(new Point(point.x - 1, point.y + 1))
                            .add(new Point(point.x + 1, point.y - 1))
                            .add(new Point(point.x, point.y - 1))
                            .add(new Point(point.x, point.y + 1))
                            .add(new Point(point.x - 1, point.y))
                            .add(new Point(point.x + 1, point.y))
                            .add(new Point(point.x + 2, point.y))
                            .add(new Point(point.x - 2, point.y))
                            .add(new Point(point.x, point.y + 2))
                            .add(new Point(point.x, point.y - 2))
                            .add(new Point(point.x + 1, point.y + 2))
                            .add(new Point(point.x + 1, point.y - 2))
                            .add(new Point(point.x - 1, point.y + 2))
                            .add(new Point(point.x - 1, point.y - 2))
                            .add(new Point(point.x + 2, point.y + 1))
                            .add(new Point(point.x + 2, point.y - 1))
//                            .add(new Point(point.x + 2, point.y + 2))
//                            .add(new Point(point.x + 2, point.y - 2))
//                            .add(new Point(point.x - 2, point.y + 2))
//                            .add(new Point(point.x - 2, point.y - 2))
                            .add(new Point(point.x - 2, point.y + 1))
                            .add(new Point(point.x - 2, point.y - 1))
                            .build();
}
