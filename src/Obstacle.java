import processing.core.PImage;

import java.util.List;

public class Obstacle extends AnimatedEntity{

    public Obstacle(String id,
                    Point position,
                    List<PImage> images,
                    int imageIndex,
                    int animationPeriod)
    {
        super(id, position, images, imageIndex, animationPeriod);
    }

}
