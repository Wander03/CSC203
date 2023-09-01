import processing.core.PImage;

import java.util.List;

public abstract class Sapling extends PlantEntity{

    private final int healthLimit;

    public Sapling(String id,
                   Point position,
                   List<PImage> images,
                   int imageIndex,
                   int animationPeriod,
                   int actionPeriod,
                   int health,
                   int healthLimit)
    {
        super(id, position, images, imageIndex, animationPeriod, actionPeriod, health);
        this.healthLimit = healthLimit;
    }

    public void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler)
    {
        changeHealth(1);
        if (!transformPlant(world, scheduler, imageStore))
        {
            scheduler.scheduleEvent(this,
                    Factory.createActivityAction(this, world, imageStore),
                    getActionPeriod());
        }
    }

    protected boolean _transformPlantHelper(WorldModel world, EventScheduler scheduler, ImageStore imageStore)
    {
        if (getHealth() >= healthLimit)
        {
            Tree tree = getTree(imageStore);
            super.transform(world, scheduler, imageStore, tree);

            return true;
        }

        return false;
    }

    protected abstract Tree getTree(ImageStore imageStore);

}
