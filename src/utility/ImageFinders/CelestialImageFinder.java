package utility.ImageFinders;

import javafx.concurrent.Task;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import projectthree.ProjectThreeController;
import projectthree.ShimmerView;
import utility.JavaSquire;
import utility.Settings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Timer;

public class CelestialImageFinder implements Runnable {


    private TextureImageFinder textureImageFinder;

    public enum GalaxyEnum {
        BLUR, CLOUDY, LOVE, SMOKY, SMUDGE, SPIRAL, TWINKLES
    }
    public enum ShootingStarEnum {
        STAR1, STAR2, STAR3, STAR4
    }

    public class StarListAndTimer {
        public Timer starTimer;
        public ArrayList<ImageView> starList;
        public ShimmerView shimmerViewTask;
    }
    public enum StarTrailEnum {
        FUSCHIA, GOLD, IRIDESCENT, ORANGE, RAINBOW
    }
    private HashMap<GalaxyEnum, Image> galaxyImageMap;
    private HashMap<StarTrailEnum, Image> starTrailMap;
    private ArrayList<Image> allStarTrails;
    private ProjectThreeController projectThreeController;
    private boolean runningProject = false;
    private ArrayList<Image> starShapeList;
    private Random random;

    public CelestialImageFinder() {
        this.random = new Random();
        initializeCollections();

    }

    public CelestialImageFinder(ProjectThreeController projectThreeController) {
        this.projectThreeController = projectThreeController;
        this.runningProject = true;
    }

    private void initializeCollections() {
        this.galaxyImageMap = new HashMap<GalaxyEnum, Image>();
        this.starTrailMap = new HashMap<StarTrailEnum, Image>();
        this.allStarTrails = new ArrayList<Image>();
        this.starShapeList = new ArrayList<Image>();
    }

    private void loadGalaxyMap() {
        this.galaxyImageMap.put(GalaxyEnum.BLUR, new Image("/assets/celestial/galacticBlur.png"));
        this.galaxyImageMap.put(GalaxyEnum.CLOUDY, new Image("/assets/celestial/galacticCloudy.png"));
        this.galaxyImageMap.put(GalaxyEnum.LOVE, new Image("/assets/celestial/galacticLove.png"));
        this.galaxyImageMap.put(GalaxyEnum.SMOKY, new Image("/assets/celestial/galacticSmoky.png"));
        this.galaxyImageMap.put(GalaxyEnum.SMUDGE, new Image("/assets/celestial/galacticSmudge.png"));
        this.galaxyImageMap.put(GalaxyEnum.SPIRAL, new Image("/assets/celestial/galacticSpiral.png"));
        this.galaxyImageMap.put(GalaxyEnum.TWINKLES, new Image("/assets/celestial/galacticTwinkles.png"));
    }

    private void loadStarTrailMap(){
        this.starTrailMap.put(StarTrailEnum.FUSCHIA, new Image ("/assets/celestial/starTrailFuschia.png"));
        this.starTrailMap.put(StarTrailEnum.GOLD, new Image ("/assets/celestial/starTrailGoldIntricate.png"));
        this.starTrailMap.put(StarTrailEnum.IRIDESCENT, new Image ("/assets/celestial/starTrailIridescent.png"));
        this.starTrailMap.put(StarTrailEnum.ORANGE, new Image ("/assets/celestial/starTrailOrange.png"));
        this.starTrailMap.put(StarTrailEnum.RAINBOW, new Image ("/assets/celestial/starTrailRainbow.png"));

        this.allStarTrails.add(this.starTrailMap.get(StarTrailEnum.FUSCHIA));
        this.allStarTrails.add(this.starTrailMap.get(StarTrailEnum.GOLD));
        this.allStarTrails.add(this.starTrailMap.get(StarTrailEnum.IRIDESCENT));
        this.allStarTrails.add(this.starTrailMap.get(StarTrailEnum.ORANGE));
        this.allStarTrails.add(this.starTrailMap.get(StarTrailEnum.RAINBOW));

    }

    public Image getStarTrailByEnum(StarTrailEnum starTrailEnum) {
        return this.starTrailMap.get(starTrailEnum);
    }
    public Image getGalaxyImageByEnum(GalaxyEnum galaxyEnum) {
        return this.galaxyImageMap.get(galaxyEnum);
    }


    public ImageView getJupiterImageView(int height){
        Image jupiter = new Image("/assets/celestial/Jupiter.gif");
        ImageView jupiterView = new ImageView(jupiter);
        jupiterView.setFitHeight(height);
        jupiterView.setPreserveRatio(true);
        return jupiterView;
    }

    @Override
    public void run() {
        initializeCollections();
        loadGalaxyMap();
        loadStarTrailMap();
        loadStarShapes();
    }

    private void loadStarShapes() {
        this.starShapeList.add(new Image("/assets/shapes/stars/star1.png"));
        this.starShapeList.add(new Image("/assets/shapes/stars/star2.png"));
        this.starShapeList.add(new Image("/assets/shapes/stars/star3.png"));
        this.starShapeList.add(new Image("/assets/shapes/stars/star4.png"));
        this.starShapeList.add(new Image("/assets/shapes/stars/star5.png"));
    }

    public ArrayList<Image> getAllStarTrails(){
        return this.allStarTrails;
    }

    public ImageView getLotsOfRandomStars(TextureImageFinder textureImageFinder, int xPosition, int yPosition, int size) {
        this.textureImageFinder = textureImageFinder;
        Image starClipImage = getRandomStarShape();
        ImageView starViewClip = new ImageView(starClipImage);
        JavaSquire.resizeImage(starViewClip,size,size);

        Image gemstone = this.textureImageFinder.getGemstoneByEnum(TextureImageFinder.GemstoneEnum.DIAMOND);
        ImageView diamondView = new ImageView(gemstone);
        JavaSquire.resizeImage(diamondView,size,size);
        diamondView.setClip(starViewClip);
        ShimmerView shimmerView = new ShimmerView(diamondView,this.textureImageFinder.allGemstoneImages);
        Timer timer = new Timer();
        timer.schedule(shimmerView,0,60);
        diamondView.setLayoutX(xPosition);
        diamondView.setLayoutY(yPosition);

        return diamondView;
    }

    public StarListAndTimer getLotsOfRandomStars(TextureImageFinder textureImageFinder, int numberOfStars) {
    this.textureImageFinder = textureImageFinder;
        ArrayList<ImageView> randomStars = new ArrayList<ImageView>();

        int counter = 0;
        while (counter < numberOfStars) {
            randomStars.add(getRandomStarViewComposite(textureImageFinder));
            counter++;
        }


        ShimmerView shimmerView = new ShimmerView(randomStars,this.textureImageFinder, textureImageFinder.allGemstoneImages);
        Timer timer = new Timer();
        timer.schedule(shimmerView,0,60);

        StarListAndTimer starListAndTimer = new StarListAndTimer();
        starListAndTimer.starList = randomStars;
        starListAndTimer.starTimer = timer;
        starListAndTimer.shimmerViewTask = shimmerView;
        return starListAndTimer;
    }

    private ImageView getRandomStarViewComposite(TextureImageFinder textureImageFinder){
        this.textureImageFinder = textureImageFinder;
        Image starClipImage = getRandomStarShape();
        ImageView starViewClip = new ImageView(starClipImage);
        int randomSize = random.nextInt(150) + 25;
        JavaSquire.resizeImage(starViewClip,randomSize,randomSize);

        int randomTexture = random.nextInt(this.textureImageFinder.allGemstoneImages.size());
        Image gemstone = this.textureImageFinder.allGemstoneImages.get(randomTexture);
        ImageView diamondView = new ImageView(gemstone);
        JavaSquire.resizeImage(diamondView,randomSize,randomSize);
        diamondView.setClip(starViewClip);

        diamondView.setLayoutX(this.random.nextInt(Settings.getScreenWidth()));
        diamondView.setLayoutY(this.random.nextInt(Settings.getScreenHeight()));

        return diamondView;
    }
    private Image getRandomStarShape(){

        int random = this.random.nextInt(this.starShapeList.size());

        Image starImage = this.starShapeList.get(random);

        return starImage;
    }
}
