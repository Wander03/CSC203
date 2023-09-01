import processing.core.PImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public abstract class DudeFull extends Dude {

    public DudeFull(String id,
                    Point position,
                    List<PImage> images,
                    int imageIndex,
                    int animationPeriod,
                    int actionPeriod,
                    int resourceLimit) {
        super(id, position, images, imageIndex, animationPeriod, actionPeriod, resourceLimit);
    }

    public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler) {
        if (getPosition().adjacent(target.getPosition())) {
            return true;
        } else {
            return super.moveTo(world, target, scheduler);
        }
    }

    public void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler) {
        Optional<Entity> fullTarget =
                world.findNearest(getPosition(), new ArrayList<>(Arrays.asList(House.class)));

        if (fullTarget.isPresent() && moveTo(world, fullTarget.get(), scheduler)) {
            transform(world, scheduler, imageStore);
        } else {
            scheduler.scheduleEvent(this,
                    Factory.createActivityAction(this, world, imageStore),
                    getActionPeriod());
        }
    }

    public void transform(
            WorldModel world,
            EventScheduler scheduler,
            ImageStore imageStore)
    {
        DudeNotFull miner = transform_(world, scheduler, imageStore);
        super.transform(world, scheduler, imageStore, miner);
    }

    protected abstract DudeNotFull transform_(WorldModel world,
                                           EventScheduler scheduler,
                                           ImageStore imageStore);

}