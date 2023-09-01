import processing.core.PImage;

import java.util.List;

public class TreeForest extends Tree{

    public TreeForest(String id,
                Point position,
                List<PImage> images,
                int imageIndex,
                int animationPeriod,
                int actionPeriod,
                int health)
    {
        super(id, position, images, imageIndex, animationPeriod, actionPeriod, health);
    }

    protected Stump _createStump(ImageStore imageStore) {
        return Factory.createStumpForest(getId(),
                getPosition(),
                imageStore.getImageList(Functions.STUMP_KEY));
    }

}
