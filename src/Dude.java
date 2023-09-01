import processing.core.PImage;

import java.util.List;

public abstract class Dude extends MovingEntity{

    private final int resourceLimit;

    public Dude(String id,
                Point position,
                List<PImage> images,
                int imageIndex,
                int animationPeriod,
                int actionPeriod,
                int resourceLimit)
    {
        super(id, position, images, imageIndex, animationPeriod, actionPeriod);
        this.resourceLimit = resourceLimit;
    }

    public int getResourceLimit() { return resourceLimit; }

    public Point nextPosition(WorldModel world, Point destPos) {
//        PathingStrategy strat = new SingleStepPathingStrategy();
        PathingStrategy strat = new AStarPathingStrategy();

        List<Point> path = strat.computePath(
                getPosition(),
                destPos,
                p -> world.withinBounds(p) &&
                        !(world.isOccupied(p) && world.getOccupancyCell(p).getClass() != Stump.class && world.getOccupancyCell(p).getClass() != StumpDesert.class),
                (p1, p2) -> p1.adjacent(p2),
                PathingStrategy.CARDINAL_NEIGHBORS
        );

        // make sure there is a path, if there is, take a single step to the first spot (first index); ow stay where you are
        Point newPos = getPosition();
        if (path.size() != 0)
            newPos = path.get(0);

        return newPos;
    }

}
