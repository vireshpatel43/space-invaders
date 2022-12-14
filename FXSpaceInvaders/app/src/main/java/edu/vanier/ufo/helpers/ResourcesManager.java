package edu.vanier.ufo.helpers;

import java.util.HashMap;


/**
 * A resource manager providing useful resource definitions used in this game.
 *
 * @author Sleiman
 */
public class ResourcesManager {

    /**
     * Used to control the speed of the game.
     */
    public static final int FRAMES_PER_SECOND = 85;
    private static final String RESOURCES_FOLDER = "";
    private static final String IMAGES_FOLDER = RESOURCES_FOLDER + "images/";
    private static final String SOUNDS_FOLDER = RESOURCES_FOLDER + "sounds/";
    // Ship images.
    public static final String SPACE_SHIP_SMALL = IMAGES_FOLDER + "ship_C.png";
    public static final String SPACE_SHIP_MEDIUM = IMAGES_FOLDER + "ship_E.png";
    public static final String SPACE_SHIP_LARGE = IMAGES_FOLDER + "ship_sidesC.png";
    // Rocket images
    public static final String ROCKET_SMALL = IMAGES_FOLDER + "star_small.png";
    public static final String ROCKET_MEDIUM = IMAGES_FOLDER + "star_tiny.png";
    public static final String ROCKET_LARGE = IMAGES_FOLDER + "ship_B.png";
    public static final String ROCKET_SMALL2 = IMAGES_FOLDER + "tank_explosion6.png";
    public static final String ROCKET_MEDIUM2 = IMAGES_FOLDER + "tank_explosion2.png";
    public static final String ROCKET_LARGE2 = IMAGES_FOLDER + "tank_explosion7.png";
    // Invader sprites.
    public static final String INVADER_LARGE_SHIP = IMAGES_FOLDER + "large_invader_b.png";
    public static final String INVADER_SMALL_SHIP = IMAGES_FOLDER + "small_invader_b.png";
    public static final String INVADER_UFO = IMAGES_FOLDER + "ufo.png";
    public static final String INVADER_CHICKEN = IMAGES_FOLDER + "rounded-chicken.png";
    public static final String INVADER_BEE = IMAGES_FOLDER + "small-bee.png";
    public static final String INVADER_SCI_FI = IMAGES_FOLDER + "sci-fi.png";
    

    // Sound effect files
    //public static final String SOUND_LASER = SOUNDS_FOLDER + "laser_2.mp3";    
    public static final String SOUND_LASER_LARGE = SOUNDS_FOLDER + "laser2.wav";  
    public static final String SOUND_LASER_MEDIUM = SOUNDS_FOLDER + "laser_0.wav";  
    public static final String SOUND_LASER_SMALL = SOUNDS_FOLDER + "laser1.wav";
    public static final String SOUND_EXPLOSION = SOUNDS_FOLDER + "explosion_1.wav";
    
    // Explosion effect
    public static final String EXPLOSION_EFFECT = IMAGES_FOLDER + "fireworks.gif";
    
    public static final String[] INADER_SPRITES_PATH = {
        INVADER_UFO, INVADER_CHICKEN, INVADER_BEE, INVADER_SCI_FI
    };

    public static HashMap<Integer, String> getInvaderSprites() {
        HashMap<Integer, String> invaders = new HashMap<Integer, String>();
        invaders.put(1, ResourcesManager.IMAGES_FOLDER + "tanks_barrelGreen.png");
        invaders.put(2, ResourcesManager.IMAGES_FOLDER + "tanks_barrelGrey.png");
        invaders.put(3, ResourcesManager.IMAGES_FOLDER + "tanks_barrelRed.png");
        invaders.put(4, ResourcesManager.IMAGES_FOLDER + "meteor_large.png");
        invaders.put(5, ResourcesManager.IMAGES_FOLDER + "meteor_small.png");
        invaders.put(6, ResourcesManager.IMAGES_FOLDER + "meteor_squareLarge.png");
        return invaders;
    }

}
