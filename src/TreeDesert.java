import processing.core.PImage;

import java.util.List;

public class TreeDesert extends Tree{

    public TreeDesert(String id,
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
        return Factory.createStumpDesert(getId(),
                getPosition(),
                imageStore.getImageList(Functions.STUMP_DESERT_KEY));
    }

}
