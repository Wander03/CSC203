import processing.core.PImage;

import java.util.List;

public class StumpForest extends Stump{

    public StumpForest(String id,
                       Point position,
                       List<PImage> images)
    {
        super(id, position, images);
    }

    public void transform(WorldModel world, StumpDesert stump) {
        world.removeEntity(this);
        world.addEntity(stump);
    }

}
