import processing.core.PImage;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public abstract class Fairy extends MovingEntity{

    public Fairy(String id,
                 Point position,
                 List<PImage> images,
                 int imageIndex,
                 int animationPeriod,
                 int actionPeriod)
    {
        super(id, position, images, imageIndex, animationPeriod, actionPeriod);
    }

    public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler) {
        if (getPosition().adjacent(target.getPosition())) {
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);
            return true;
        }
        else {
            return super.moveTo(world, target, scheduler);
        }
    }

    public Point nextPosition(WorldModel world, Point destPos) {

//        PathingStrategy strat = new SingleStepPathingStrategy();
        PathingStrategy strat = new AStarPathingStrategy();

        List<Point> path = strat.computePath(
                getPosition(),
                destPos,
                p -> world.withinBounds(p) && !(world.isOccupied(p)),
                (p1, p2) -> p1.adjacent(p2),
                PathingStrategy.CARDINAL_NEIGHBORS
        );

        // make sure there is a path, if there is, take a single step to the first spot (first index); ow stay where you are
        Point newPos = getPosition();
        if (path.size() != 0)
            newPos = path.get(0);

        return newPos;
    }

    public void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler)
    {
        executeActivity_(world, imageStore, scheduler);

        scheduler.scheduleEvent(this,
                Factory.createActivityAction(this, world, imageStore),
                getActionPeriod());
    }

    protected abstract void executeActivity_(WorldModel world, ImageStore imageStore, EventScheduler scheduler);

}
