import processing.core.PImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public abstract class DudeNotFull extends Dude{

    private int resourceCount;

    public DudeNotFull(String id,
                    Point position,
                    List<PImage> images,
                    int imageIndex,
                    int animationPeriod,
                    int actionPeriod,
                    int resourceCount,
                    int resourceLimit)
    {
        super(id, position, images, imageIndex, animationPeriod, actionPeriod, resourceLimit);
        this.resourceCount = resourceCount;
    }

    public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler) {
        if (getPosition().adjacent(target.getPosition())) {
            resourceCount += 1;
            ((PlantEntity) target).changeHealth(-1);
            return true;
        }
        else {
           return super.moveTo(world, target, scheduler);
        }
    }

    public void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler,
            Optional<Entity> target)
    {
        if (!target.isPresent() || !moveTo(world,
                target.get(),
                scheduler)
                || !transform(world, scheduler, imageStore))
        {
            scheduler.scheduleEvent(this,
                    Factory.createActivityAction(this, world, imageStore),
                    getActionPeriod());
        }
    }

    public boolean transform(
            WorldModel world,
            EventScheduler scheduler,
            ImageStore imageStore)
    {
        if (resourceCount >= getResourceLimit()) {
            DudeFull miner = transform_(world, scheduler, imageStore);
            super.transform(world, scheduler, imageStore, miner);
            return true;
        }

        return false;
    }

    protected abstract DudeFull transform_(WorldModel world,
                                           EventScheduler scheduler,
                                           ImageStore imageStore);


}
