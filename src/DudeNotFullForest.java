import processing.core.PImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class DudeNotFullForest extends DudeNotFull{

    public DudeNotFullForest(String id,
                             Point position,
                             List<PImage> images,
                             int imageIndex,
                             int animationPeriod,
                             int actionPeriod,
                             int resourceCount,
                             int resourceLimit) {
        super(id, position, images, imageIndex, animationPeriod, actionPeriod, resourceCount, resourceLimit);
    }

    public void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler)
    {
        Optional<Entity> target =
                world.findNearest(getPosition(), new ArrayList<>(Arrays.asList(TreeForest.class, SaplingForest.class)));

        super.executeActivity(world, imageStore, scheduler, target);
    }

    protected DudeFull transform_(
            WorldModel world,
            EventScheduler scheduler,
            ImageStore imageStore)
    {
        return Factory.createDudeFullForest(
                getId(),
                getPosition(), getActionPeriod(),
                getAnimationPeriod(),
                getResourceLimit(),
                getImages());

    }



}
