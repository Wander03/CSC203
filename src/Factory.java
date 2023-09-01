import java.util.*;
import processing.core.PImage;

public class Factory {

    private static final int SAPLING_ACTION_ANIMATION_PERIOD = 1000; // have to be in sync since grows and gains health at same time
    private static final int SAPLING_HEALTH_LIMIT = 5;

    public static House createHouse(
            String id, Point position, List<PImage> images)
    {
        return new House(id, position, images);
    }

    public static Obstacle createObstacle(
            String id, Point position, int animationPeriod, List<PImage> images)
    {
        return new Obstacle(id, position, images, 0, animationPeriod);
    }

    public static TreeForest createTreeForest(
            String id,
            Point position,
            int actionPeriod,
            int animationPeriod,
            int health,
            List<PImage> images)
    {
        return new TreeForest(id, position, images, 0, animationPeriod,
                actionPeriod, health);
    }

    public static TreeDesert createTreeDesert(
            String id,
            Point position,
            int actionPeriod,
            int animationPeriod,
            int health,
            List<PImage> images)
    {
        return new TreeDesert(id, position, images, 0, animationPeriod,
                actionPeriod, health);
    }

    public static StumpForest createStumpForest(
            String id,
            Point position,
            List<PImage> images)
    {
        return new StumpForest(id, position, images);
    }

    public static StumpDesert createStumpDesert(
            String id,
            Point position,
            List<PImage> images)
    {
        return new StumpDesert(id, position, images);
    }

    // health starts at 0 and builds up until ready to convert to Tree
    public static SaplingForest createSaplingForest(
            String id,
            Point position,
            List<PImage> images)
    {
        return new SaplingForest(id, position, images, 0, SAPLING_ACTION_ANIMATION_PERIOD,
                SAPLING_ACTION_ANIMATION_PERIOD, 0, SAPLING_HEALTH_LIMIT);
    }

    public static SaplingDesert createSaplingDesert(
            String id,
            Point position,
            List<PImage> images)
    {
        return new SaplingDesert(id, position, images, 0, SAPLING_ACTION_ANIMATION_PERIOD,
                SAPLING_ACTION_ANIMATION_PERIOD, 0, SAPLING_HEALTH_LIMIT);
    }

    public static FairyForest createFairyForest(
            String id,
            Point position,
            int actionPeriod,
            int animationPeriod,
            List<PImage> images)
    {
        return new FairyForest(id, position, images, 0, animationPeriod,
                actionPeriod);
    }

    public static FairyDesert createFairyDesert(
            String id,
            Point position,
            int actionPeriod,
            int animationPeriod,
            List<PImage> images)
    {
        return new FairyDesert(id, position, images, 0, animationPeriod,
                actionPeriod);
    }

    // need resource count, though it always starts at 0
    public static DudeNotFullForest createDudeNotFullForest(
            String id,
            Point position,
            int actionPeriod,
            int animationPeriod,
            int resourceLimit,
            List<PImage> images)
    {
        return new DudeNotFullForest(id, position, images, 0, animationPeriod,
                actionPeriod,0, resourceLimit);
    }

    public static DudeNotFullDesert createDudeNotFullDesert(
            String id,
            Point position,
            int actionPeriod,
            int animationPeriod,
            int resourceLimit,
            List<PImage> images)
    {
        return new DudeNotFullDesert(id, position, images, 0, animationPeriod,
                actionPeriod,0, resourceLimit);
    }

    // don't technically need resource count ... full
    public static DudeFullForest createDudeFullForest(
            String id,
            Point position,
            int actionPeriod,
            int animationPeriod,
            int resourceLimit,
            List<PImage> images)
    {
        return new DudeFullForest(id, position, images, 0, animationPeriod,
                actionPeriod, resourceLimit);
    }

    public static DudeFullDesert createDudeFullDesert(
            String id,
            Point position,
            int actionPeriod,
            int animationPeriod,
            int resourceLimit,
            List<PImage> images)
    {
        return new DudeFullDesert(id, position, images, 0, animationPeriod,
                actionPeriod, resourceLimit);
    }

    public static Action createAnimationAction(AnimatedEntity entity, int repeatCount) {
        return new Animation(entity, repeatCount);
    }

    public static Action createActivityAction(ActionEntity entity, WorldModel world, ImageStore imageStore) {
        return new Activity(entity, world, imageStore);
    }

}
