import processing.core.PImage;

import java.util.List;

public abstract class PlantEntity extends ActionEntity {

    private int health;

    public PlantEntity(String id,
                       Point position,
                       List<PImage> images,
                       int imageIndex,
                       int animationPeriod,
                       int actionPeriod,
                       int health) {
        super(id, position, images, imageIndex, animationPeriod, actionPeriod);
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public void changeHealth(int n) {
        health += n;
    }

    public boolean transformPlant(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (getHealth() <= 0) {

            Stump stump = _createStump(imageStore);

            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(stump);

            return true;
        }

        return _transformPlantHelper(world, scheduler, imageStore);

    }

    protected abstract Stump _createStump(ImageStore imageStore);
    protected abstract boolean _transformPlantHelper(WorldModel world, EventScheduler scheduler, ImageStore imageStore);
}