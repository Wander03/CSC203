import processing.core.PImage;

import java.util.List;

public abstract class Tree extends PlantEntity{

    public Tree(String id,
                   Point position,
                   List<PImage> images,
                   int imageIndex,
                   int animationPeriod,
                   int actionPeriod,
                   int health)
    {
        super(id, position, images, imageIndex, animationPeriod, actionPeriod, health);
    }

    public void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler)
    {
        if (!transformPlant(world, scheduler, imageStore)) {

            scheduler.scheduleEvent(this,
                    Factory.createActivityAction(this, world, imageStore),
                    getActionPeriod());
        }
    }

    protected boolean _transformPlantHelper(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        return false;
    }
}
