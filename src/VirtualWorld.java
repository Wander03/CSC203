import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import processing.core.*;

public final class VirtualWorld extends PApplet
{
    private static final int TIMER_ACTION_PERIOD = 100;

    private static final int VIEW_WIDTH = 640;
    private static final int VIEW_HEIGHT = 480;
    private static final int TILE_WIDTH = 32;
    private static final int TILE_HEIGHT = 32;
    private static final int WORLD_WIDTH_SCALE = 2;
    private static final int WORLD_HEIGHT_SCALE = 2;

    private static final int VIEW_COLS = VIEW_WIDTH / TILE_WIDTH;
    private static final int VIEW_ROWS = VIEW_HEIGHT / TILE_HEIGHT;
    private static final int WORLD_COLS = VIEW_COLS * WORLD_WIDTH_SCALE;
    private static final int WORLD_ROWS = VIEW_ROWS * WORLD_HEIGHT_SCALE;

    private static final String IMAGE_LIST_FILE_NAME = "imagelist";
    private static final String DEFAULT_IMAGE_NAME = "background_default";
    private static final String CHANGED_IMAGE_NAME = "background_change";
    private static final String CHANGED_IMAGE_NAME_2 = "background_change_2";
    private static final int DEFAULT_IMAGE_COLOR = 0x808080;

    private final static String LOAD_FILE_NAME = "world.sav";

    private static final String FAST_FLAG = "-fast";
    private static final String FASTER_FLAG = "-faster";
    private static final String FASTEST_FLAG = "-fastest";
    private static final double FAST_SCALE = 0.5;
    private static final double FASTER_SCALE = 0.25;
    private static final double FASTEST_SCALE = 0.10;

    private static double timeScale = 1.0;

    private ImageStore imageStore;
    private WorldModel world;
    private WorldView view;

    private EventScheduler scheduler;

    private long nextTime;

    private final Random random = new Random();

    public void settings() {
        size(VIEW_WIDTH, VIEW_HEIGHT);
    }

    /*
       Processing entry point for "sketch" setup.
    */
    public void setup() {
        this.imageStore = new ImageStore(
                createImageColored(TILE_WIDTH, TILE_HEIGHT,
                                   DEFAULT_IMAGE_COLOR));
        this.world = new WorldModel(WORLD_ROWS, WORLD_COLS,
                                    createDefaultBackground(imageStore));
        this.view = new WorldView(VIEW_ROWS, VIEW_COLS, this, world, TILE_WIDTH,
                                  TILE_HEIGHT);
        this.scheduler = new EventScheduler(timeScale);

        loadImages(IMAGE_LIST_FILE_NAME, imageStore, this);
        loadWorld(world, LOAD_FILE_NAME, imageStore);

        scheduleActions(world, scheduler, imageStore);

        nextTime = System.currentTimeMillis() + TIMER_ACTION_PERIOD;
    }

    public static double getTimeScale() {
        return timeScale;
    }

    public ImageStore getImageStore() {
        return imageStore;
    }

    public WorldModel getWorld() {
        return world;
    }

    public WorldView getView() {
        return view;
    }

    public EventScheduler getScheduler() {
        return scheduler;
    }

    public long getNextTime() {
        return nextTime;
    }

    public void draw() {
        long time = System.currentTimeMillis();
        if (time >= nextTime) {
            scheduler.updateOnTime(time);
            nextTime = time + TIMER_ACTION_PERIOD;
        }

        view.drawViewport();
    }

    // Just for debugging and for P5
    public void mousePressed() {
        Point pressed = mouseToPoint(mouseX, mouseY);
        System.out.println("CLICK! " + pressed.x + ", " + pressed.y);

        transformArea(pressed);

        Optional<Entity> entityOptional = world.getOccupant(pressed);
        if (entityOptional.isPresent())
        {
            Entity entity = entityOptional.get();
            System.out.println(entity.getId() + ": " + entity.getClass());
        }

    }

    private void transformArea(Point pressed) {
        int randRemove;

        // Randomly remove tiles so each area isn't the same
        List<Point> tiles = pressed.getArea(world);
        for (int i = 0; i < 5; i++) {
            randRemove = random.nextInt(tiles.size());
            tiles.remove(randRemove);
        }

        transformDesert(tiles);

    }

    private void transformDesert(List<Point> tiles) {
        int randSpawn;
        int randImage;
        int spawnNum;

        // Randomly choose a few tiles to add new Dudes
        List<Point> newDudes = new ArrayList<>();
        spawnNum = random.nextInt(1, 3);
        for (int i = 0; i < spawnNum; i++) {
            randSpawn = random.nextInt(tiles.size());
            newDudes.add(tiles.get(randSpawn));
        }

        // Randomly choose a few tiles to add new Saplings
        List<Point> newSaps = new ArrayList<>();
        spawnNum = random.nextInt(1, 3);
        for (int i = 0; i < spawnNum; i++) {
            randSpawn = random.nextInt(tiles.size());
            newSaps.add(tiles.get(randSpawn));
        }

        // Randomly choose a few tiles to add new Fairies
        List<Point> newFar = new ArrayList<>();
        spawnNum = random.nextInt(2);
        for (int i = 0; i < spawnNum; i++) {
            randSpawn = random.nextInt(tiles.size());
            newFar.add(tiles.get(randSpawn));
        }

        for (Point p : tiles) {
            // Randomly select a new image for the tile
            randImage = random.nextInt(3) + 1;
            world.setBackgroundCell(p,
                    new Background(
                            CHANGED_IMAGE_NAME + randImage,
                            imageStore.getImageList(CHANGED_IMAGE_NAME + randImage)
                    )
            );

            // Transform any Dudes or Fairies
            Entity occupied = world.getOccupancyCell(p);
            if (occupied != null) {

                if (world.getOccupancyCell(p).getClass() == DudeNotFullForest.class) {

                    DudeNotFullDesert miner = Factory.createDudeNotFullDesert(occupied.getId(),
                            p,
                            random.nextInt(700, 900),
                            100,
                            4,
                            imageStore.getImageList(Functions.DUDE_DESERT_KEY));

                    ((ActionEntity) occupied).transform(world, scheduler, imageStore, miner);

                } else if (world.getOccupancyCell(p).getClass() == DudeFullForest.class) {

                    DudeFullDesert miner = Factory.createDudeFullDesert(occupied.getId(),
                            p,
                            random.nextInt(700, 900),
                            100,
                            4,
                            imageStore.getImageList(Functions.DUDE_DESERT_KEY));

                    ((ActionEntity) occupied).transform(world, scheduler, imageStore, miner);

                } else if (world.getOccupancyCell(p).getClass() == FairyForest.class) {

                    FairyDesert fairy = Factory.createFairyDesert(occupied.getId(),
                            p,
                            51,
                            51,
                            imageStore.getImageList(Functions.FAIRY_DESERT_KEY));

                    ((ActionEntity) occupied).transform(world, scheduler, imageStore, fairy);

                } else if (world.getOccupancyCell(p).getClass() == StumpForest.class) {

                    StumpDesert stump = Factory.createStumpDesert(occupied.getId(),
                            p,
                            imageStore.getImageList(Functions.STUMP_DESERT_KEY));

                    ((StumpForest) occupied).transform(world, stump);

                } else if (world.getOccupancyCell(p).getClass() == SaplingForest.class) {

                    SaplingDesert sapling = Factory.createSaplingDesert(occupied.getId(),
                            p,
                            imageStore.getImageList(Functions.SAPLING_DESERT_KEY));

                    ((ActionEntity) occupied).transform(world, scheduler, imageStore, sapling);

                } else if (world.getOccupancyCell(p).getClass() == TreeForest.class) {

                    TreeDesert tree = Factory.createTreeDesert(occupied.getId(),
                            p,
                            random.nextInt(100, 300),
                            random.nextInt(1000, 1400),
                            1,
                            imageStore.getImageList(Functions.TREE_DESERT_KEY));

                    ((ActionEntity) occupied).transform(world, scheduler, imageStore, tree);

                } else if (world.getOccupancyCell(p).getClass() == FairyDesert.class) {

                    House house = Factory.createHouse(occupied.getId(),
                            p,
                            imageStore.getImageList(Functions.HOUSE_KEY));

                    ((FairyDesert) occupied).transform(world, scheduler, imageStore, house);
                }
            }

            // Add new entity if applicable ORDER MATTERS
            if (newFar.contains(p)) {
                addNewFairy(p);
            }

            if (newSaps.contains(p)) {
                addNewPlant(p);
            }

            if (newDudes.contains(p)) {
                addNewDude(p);
            }

        }
    }

    private void addNewDude(Point pt) {
        DudeNotFullDesert miner = Factory.createDudeNotFullDesert("dude_new",
                pt,
                random.nextInt(700, 900),
                100,
                4,
                imageStore.getImageList(Functions.DUDE_DESERT_KEY));
        if (world.tryAddEntity(miner))
            miner.scheduleActions(scheduler, world, imageStore);
    }

    private void addNewPlant(Point pt) {
        TreeDesert tree = Factory.createTreeDesert("tree_new",
                pt,
                random.nextInt(100, 300),
                random.nextInt(1000, 1400),
                1,
                imageStore.getImageList(Functions.TREE_DESERT_KEY));
        if (world.tryAddEntity(tree))
            tree.scheduleActions(scheduler, world, imageStore);
    }

    private void addNewFairy(Point pt) {
        FairyDesert fairy = Factory.createFairyDesert("fairy_new",
                pt,
                51,
                51,
                imageStore.getImageList(Functions.FAIRY_DESERT_KEY));
        if (world.tryAddEntity(fairy))
            fairy.scheduleActions(scheduler, world, imageStore);
    }

    private Point mouseToPoint(int x, int y)
    {
        return view.getViewport().viewportToWorld(mouseX/TILE_WIDTH, mouseY/TILE_HEIGHT);
    }
    public void keyPressed() {
        if (key == CODED) {
            int dx = 0;
            int dy = 0;

            switch (keyCode) {
                case UP:
                    dy = -1;
                    break;
                case DOWN:
                    dy = 1;
                    break;
                case LEFT:
                    dx = -1;
                    break;
                case RIGHT:
                    dx = 1;
                    break;
            }
            view.shiftView(dx, dy);
        }
    }

    public static Background createDefaultBackground(ImageStore imageStore) {
        return new Background(DEFAULT_IMAGE_NAME,
                imageStore.getImageList(DEFAULT_IMAGE_NAME));
    }

    public static PImage createImageColored(int width, int height, int color) {
        PImage img = new PImage(width, height, RGB);
        img.loadPixels();
        for (int i = 0; i < img.pixels.length; i++) {
            img.pixels[i] = color;
        }
        img.updatePixels();
        return img;
    }

    static void loadImages(
            String filename, ImageStore imageStore, PApplet screen)
    {
        try {
            Scanner in = new Scanner(new File(filename));
            imageStore.loadImages(in, screen);
        }
        catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void loadWorld(
            WorldModel world, String filename, ImageStore imageStore)
    {
        try {
            Scanner in = new Scanner(new File(filename));
            Functions.load(in, world, imageStore);
        }
        catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void scheduleActions(
            WorldModel world, EventScheduler scheduler, ImageStore imageStore)
    {
        for (Entity entity : world.getEntities()) {
            if (entity instanceof AnimatedEntity)
                ((AnimatedEntity) entity).scheduleActions(scheduler, world, imageStore);
        }
    }

    public static void parseCommandLine(String[] args) {
        if (args.length > 1)
        {
            if (args[0].equals("file"))
            {

            }
        }
        for (String arg : args) {
            switch (arg) {
                case FAST_FLAG:
                    timeScale = Math.min(FAST_SCALE, timeScale);
                    break;
                case FASTER_FLAG:
                    timeScale = Math.min(FASTER_SCALE, timeScale);
                    break;
                case FASTEST_FLAG:
                    timeScale = Math.min(FASTEST_SCALE, timeScale);
                    break;
            }
        }
    }

    public static void main(String[] args) {
        parseCommandLine(args);
        PApplet.main(VirtualWorld.class);
    }
}
