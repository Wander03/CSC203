import processing.core.PImage;

import java.util.List;

public class SaplingForest extends Sapling{

    public SaplingForest(String id,
                   Point position,
                   List<PImage> images,
                   int imageIndex,
                   int animationPeriod,
                   int actionPeriod,
                   int health,
                   int healthLimit)
    {
        super(id, position, images, imageIndex, animationPeriod, actionPeriod, health, healthLimit);
    }

    protected Tree getTree(ImageStore imageStore) {
        return Factory.createTreeForest("tree_" + getId(),
                    getPosition(),
                    Functions.getNumFromRange(Functions.TREE_ACTION_MAX, Functions.TREE_ACTION_MIN),
                    Functions.getNumFromRange(Functions.TREE_ANIMATION_MAX, Functions.TREE_ANIMATION_MIN),
                    Functions.getNumFromRange(Functions.TREE_HEALTH_MAX, Functions.TREE_HEALTH_MIN),
                    imageStore.getImageList(Functions.TREE_KEY));
    }

    protected Stump _createStump(ImageStore imageStore) {
        return Factory.createStumpForest(getId(),
                getPosition(),
                imageStore.getImageList(Functions.STUMP_KEY));
    }

    public void transform() {

    }

}
