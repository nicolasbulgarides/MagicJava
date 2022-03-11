package projectthree;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import utility.ImageFinders.CelestialImageFinder;
import utility.ImageFinders.TextureImageFinder;
import utility.JavaSquire;
import utility.Settings;

import java.util.ArrayList;
import java.util.Timer;

public class ProjectThreeTeacher extends Pane {
    private boolean starEffectsPaused = false;
    private CelestialImageFinder celestialImageFinder;
    private TextureImageFinder textureImageFinder;
    private ImageView startrailView;
    private Pane mountainPane;
    private Timer starTrailTimer;
    private Timer starShimmerTimer;
    private ShimmerView starTrailTask;
    private ShimmerView starShimmerTask;
    private ImageView castleView;
    private Pane cloudPane;
    private CreamCloud cloudPane2;
    private CreamCloud cloudPane3;


    public ProjectThreeTeacher(){
        addBackDrop();
        addMountainValley();
        addCastleBackdrop();
        addCreamCloud();
    }

    private void addBackDrop() {
        ImageView backdropView = new ImageView(new Image("/assets/projectthree/black1.jpg"));
        JavaSquire.resizeImage(backdropView,1920,1080);
        this.getChildren().add(backdropView);
    }

    public void addCelestialDecoration(CelestialImageFinder celestialImageFinder) {
        this.celestialImageFinder = celestialImageFinder;
        addStarTrail();
    }

    private void addStarTrail() {
        Image starTrail = this.celestialImageFinder.getStarTrailByEnum(CelestialImageFinder.StarTrailEnum.RAINBOW);
        this.startrailView = new ImageView(starTrail);
        JavaSquire.resizeImage(startrailView,3000,3000);
        startrailView.setTranslateY(-800);
        startrailView.setTranslateX(-600);
        this.getChildren().add(startrailView);
        this.starTrailTask = new ShimmerView(this.startrailView,this.celestialImageFinder.getAllStarTrails(),true);
        this.starTrailTimer = new Timer();
        this.starTrailTimer.schedule(starTrailTask,0,100);
    }


    public void addGemstoneDecorations(TextureImageFinder textureImageFinder) {
        this.textureImageFinder = textureImageFinder;
        CelestialImageFinder.StarListAndTimer listAndTimer = this.celestialImageFinder.getLotsOfRandomStars(this.textureImageFinder, 30);
        this.starShimmerTimer = listAndTimer.starTimer;
        this.starShimmerTask = listAndTimer.shimmerViewTask;
        ArrayList<ImageView> randomStars = listAndTimer.starList;


        int counter = 0;
        while (counter < randomStars.size()) {
            this.getChildren().add(randomStars.get(counter));
            counter++;
        }
        this.mountainPane.toFront();
        this.castleView.toFront();
        this.cloudPane.toFront();
        this.cloudPane2.toFront();

        this.cloudPane3.toFront();

    }
    private void addMountainValley() {
        ImageView mountainValley = new ImageView(new Image("/assets/projectthree/backdropSkyMountains.png"));
        mountainValley.setFitWidth(Settings.getScreenWidth());
        mountainValley.setPreserveRatio(true);
        this.mountainPane = new Pane();
        mountainPane.getChildren().add(mountainValley);
        this.getChildren().add(mountainPane);
    }

    private void addCastleBackdrop() {
        this.castleView = new ImageView(new Image("/assets/projectthree/castle4K.png"));
        this.castleView.setFitWidth(600);
        this.castleView.setPreserveRatio(true);
        this.castleView.setLayoutX((Settings.getScreenWidth() / 2) - (castleView.getFitWidth() / 2));
        this.castleView.setLayoutY((Settings.getScreenHeight() / 2 ) - (castleView.getFitWidth() / 2));
        this.getChildren().add(castleView);
    }
    private void addCreamCloud() {
        this.cloudPane = new CreamCloud(0.33,  -200, -200);
        this.getChildren().add(this.cloudPane);
        this.cloudPane2 = new CreamCloud(0.45,  -350, -500);
        this.getChildren().add(this.cloudPane2);
        this.cloudPane3 = new CreamCloud(0.38,  -250, -400);
        this.getChildren().add(this.cloudPane3);
    }

}
