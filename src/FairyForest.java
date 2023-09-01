import processing.core.PImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class FairyForest extends Fairy{

    public FairyForest(String id,
                 Point position,
                 List<PImage> images,
                 int imageIndex,
                 int animationPeriod,
                 int actionPeriod)
    {
        super(id, position, images, imageIndex, animationPeriod, actionPeriod);
    }

    protected void executeActivity_(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> fairyTarget =  world.findNearest(getPosition(), new ArrayList<>(Arrays.asList(StumpForest.class)));

        if (fairyTarget.isPresent()) {
            Point tgtPos = fairyTarget.get().getPosition();

            if (moveTo(world, fairyTarget.get(), scheduler)) {
                SaplingForest sapling = Factory.createSaplingForest("sapling_" + getId(), tgtPos,
                        imageStore.getImageList(Functions.SAPLING_KEY));

                world.addEntity(sapling);
                sapling.scheduleActions(scheduler, world, imageStore);
            }
        }
    }

    public void transform(WorldModel world,
                          EventScheduler scheduler,
                          ImageStore imageStore,
                          FairyDesert fairy)
    {

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        world.addEntity(fairy);
        fairy.scheduleActions(scheduler, world, imageStore);
    }

}
