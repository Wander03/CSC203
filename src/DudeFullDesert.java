import processing.core.PImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class DudeFullDesert extends DudeFull {

    public DudeFullDesert(String id,
                          Point position,
                          List<PImage> images,
                          int imageIndex,
                          int animationPeriod,
                          int actionPeriod,
                          int resourceLimit) {
        super(id, position, images, imageIndex, animationPeriod, actionPeriod, resourceLimit);
    }

    protected DudeNotFull transform_(
            WorldModel world,
            EventScheduler scheduler,
            ImageStore imageStore)
    {
        return Factory.createDudeNotFullDesert(
                getId(),
                getPosition(), getActionPeriod(),
                getAnimationPeriod(),
                getResourceLimit(),
                getImages());

    }

}