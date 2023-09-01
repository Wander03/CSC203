import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public abstract class MovingEntity extends ActionEntity{

    public MovingEntity(String id,
                        Point position,
                        List<PImage> images,
                        int imageIndex,
                        int animationPeriod,
                        int actionPeriod)
    {
        super(id, position, images, imageIndex, animationPeriod, actionPeriod);
    }

    public boolean moveTo(WorldModel world,
                          Entity target,
                          EventScheduler scheduler)
    {
        Point nextPos = nextPosition(world, target.getPosition());

        if (!getPosition().equals(nextPos)) {
            Optional<Entity> occupant = world.getOccupant(nextPos);
            if (occupant.isPresent()) {
                scheduler.unscheduleAllEvents(occupant.get());
            }

            world.moveEntity(this, nextPos);
        }
        return false;
    }
    abstract Point nextPosition(WorldModel world, Point destPos);

}
