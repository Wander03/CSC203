import processing.core.PImage;

import java.util.List;

public abstract class AnimatedEntity extends Entity{

    private int imageIndex;
    private final int animationPeriod;

    public AnimatedEntity(String id,
                          Point position,
                          List<PImage> images,
                          int imageIndex,
                          int animationPeriod)
    {
        super(id, position, images);
        this.imageIndex = imageIndex;
        this.animationPeriod = animationPeriod;
    }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this,
                Factory.createAnimationAction(this,0),
                this.getAnimationPeriod());
    }
    public void nextImage() {
        imageIndex = (imageIndex + 1) % getImages().size();
    }
    public int getAnimationPeriod() {
        return animationPeriod;
    }
    public PImage getCurrentImage() {
        return getImages().get(imageIndex);
    }
}
