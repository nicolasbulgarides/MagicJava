package projectthree;

import appentry.TurtleTower;
import utility.ImageFinders.CelestialImageFinder;
import utility.ImageFinders.TextureImageFinder;

public class ProjectThreeController {
    private TurtleTower turtleTower;
    private CelestialImageFinder celestialImageFinder;
    private ProjectThreeTeacher projectThreeTeacher;
    private TextureImageFinder textureImageFinder;
    boolean celestialThreadLoading = false;
    boolean textureThreadLoading = false;
    public ProjectThreeController(TurtleTower turtleTower) {
        this.turtleTower = turtleTower;
        runProjectThree();
    }

    private void runProjectThree() {
        this.projectThreeTeacher = new ProjectThreeTeacher();
        this.turtleTower.getChildren().add(this.projectThreeTeacher);
        loadCelestialFinder();
        loadGemstoneFinder();

    }

    private void loadCelestialFinder() {
        Runnable celestialThreadRunnable = new CelestialImageFinder();
        this.celestialImageFinder = (CelestialImageFinder) celestialThreadRunnable;

        Thread celestialThread = new Thread(celestialThreadRunnable);

        celestialThread.start();
        while (celestialThreadLoading == false) {
            if (celestialThread.isAlive() == false) {
                this.projectThreeTeacher.addCelestialDecoration(celestialImageFinder);
                celestialThreadLoading = true;
            }
        }
    }

    private void loadGemstoneFinder(){
        Runnable textureFinder = new TextureImageFinder();
        this.textureImageFinder = (TextureImageFinder) textureFinder;
        Thread textureThread = new Thread(textureFinder);
        textureThread.start();

        while (this.textureThreadLoading == false) {
            if (textureThread.isAlive() == false) {
                this.projectThreeTeacher.addGemstoneDecorations(this.textureImageFinder);
                this.textureThreadLoading = true;
            }
        }
    }

}
