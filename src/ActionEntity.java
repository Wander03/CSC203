import processing.core.PImage;

import java.util.List;

public abstract class ActionEntity extends AnimatedEntity{

    private final int actionPeriod;

    public ActionEntity(String id,
                        Point position,
                        List<PImage> images,
                        int imageIndex,
                        int animationPeriod,
                        int actionPeriod)
    {
        super(id, position, images, imageIndex, animationPeriod);
        this.actionPeriod = actionPeriod;
    }

    public int getActionPeriod() { return actionPeriod; }

    abstract void executeActivity(WorldModel world,
                         ImageStore imageStore,
                         EventScheduler scheduler);

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this,
                Factory.createActivityAction(this, world, imageStore),
                getActionPeriod());
        super.scheduleActions(scheduler, world, imageStore);
    }

    public void transform(WorldModel world,
                          EventScheduler scheduler,
                          ImageStore imageStore,
                          ActionEntity newEntity)
    {

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        world.addEntity(newEntity);
        newEntity.scheduleActions(scheduler, world, imageStore);
    }

}
