package edu.vanier.ufo.ui;

import edu.vanier.ufo.helpers.ResourcesManager;
import edu.vanier.ufo.engine.*;
import edu.vanier.ufo.game.*;
import javafx.event.EventHandler;
import javafx.scene.CacheHint;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;;
import java.util.concurrent.ThreadLocalRandom;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.Font;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.Random;
import javafx.scene.image.ImageView;

//

/**
 * This is a simple game world simulating a bunch of spheres looking like atomic
 * particles colliding with each other. When the game loop begins the user will
 * notice random spheres (atomic particles) floating and colliding. The user
 * will navigate his/her ship by right clicking the mouse to thrust forward and
 * left click to fire weapon to atoms.
 *
 * @author cdea
 */
public class GameWorld extends GameEngine {
    
    
    Label score = new Label();
    
    Label lives = new Label();
    
    Label level = new Label();
    
    Label eventLabel = new Label();
    
    Label subEventLabel = new Label();
    // mouse pt label
    Label mousePtLabel = new Label();
    // mouse press pt label
    Label mousePressPtLabel = new Label();
    
    Ship spaceShip = new Ship();
    
    
    int levelCounter;
    
    int currentScore = 0;
    
    int hitCounter = 0;
    
    boolean shieldHit = false;
    

    public GameWorld(int fps, String title) {
        super(fps, title);
    }

    /**
     * Initialize the game world by adding sprite objects.
     *
     * @param primaryStage The game window or primary stage.
     */
    @Override
    public void initialize(final Stage primaryStage) {
        // Sets the window title
        primaryStage.setTitle(getWindowTitle());
        //primaryStage.setFullScreen(true);

        // Create the scene
        setSceneNodes(new Group());
        setGameSurface(new Scene(getSceneNodes(), 1200, 800));

        // Change the background of the main scene.
        getGameSurface().setFill(Color.BLACK);

        primaryStage.setScene(getGameSurface());

        // Setup Game input
        setupInput(primaryStage);
        
        


        getSpriteManager().addSprites(spaceShip);
        getSceneNodes().getChildren().add(0, spaceShip.getNode());
        
        // Game over text
        VBox gameEvent = new VBox();
        subEventLabel.setTextFill(Color.WHITE);
        subEventLabel.setFont(new Font("Impact", 20));
        subEventLabel.setTextAlignment(TextAlignment.CENTER);
        eventLabel.setTextFill(Color.WHITE);
        eventLabel.setFont(new Font("Impact", 40));
        eventLabel.setTextAlignment(TextAlignment.CENTER);
        gameEvent.getChildren().add(eventLabel);
        gameEvent.getChildren().add(subEventLabel);
        gameEvent.setTranslateX((getGameSurface().getWidth() / 2) - 100);
        gameEvent.setTranslateY(getGameSurface().getHeight() / 2);
        
        
        HBox HUD = new HBox();
        HUD.setSpacing(10);
        HUD.setTranslateY(5);
        HUD.setTranslateX(5);
        lives.setTextFill(Color.WHITE);
        lives.setFont(new Font("Impact", 20));
        lives.setText("Lives: 0");
        level.setTextFill(Color.WHITE);
        level.setFont(new Font("Impact", 20));
        level.setText("Level: 0");
        score.setTextFill(Color.WHITE);
        score.setFont(new Font("Impact", 20));
        score.setText("Score: 0");
        
        
        
        HUD.getChildren().addAll(lives, level, score);
        
        
        //TODO: Add the HUD here.
        getSceneNodes().getChildren().add(gameEvent);
        getSceneNodes().getChildren().add(HUD);
        
        // load sound files
        getSoundManager().loadSoundEffects("laser", getClass().getClassLoader().getResource(ResourcesManager.SOUND_LASER));
    }
    
    
    
    /**
     * Sets up the mouse input.
     *
     * @param primaryStage The primary stage (app window).
     */
    private void setupInput(Stage primaryStage) {
        System.out.println("Ship's center is (" + spaceShip.getCenterX() + ", " + spaceShip.getCenterY() + ")");

        EventHandler fireOrMove = (EventHandler<MouseEvent>) (MouseEvent event) -> {
            mousePressPtLabel.setText("Mouse Press PT = (" + event.getX() + ", " + event.getY() + ")");
            if (event.getButton() == MouseButton.PRIMARY) {
                
                // Aim
                spaceShip.plotCourse(event.getX(), event.getY(), false);

                
                // fire
                    Missile missile = spaceShip.fire(ResourcesManager.ROCKET_SMALL2, ResourcesManager.ROCKET_SMALL);
                    getSpriteManager().addSprites(missile);
                    // play sound
                    getSoundManager().playSound("laser");

                    getSceneNodes().getChildren().add(0, missile.getNode());
                    
                if (levelCounter == 2) {
                    missile = spaceShip.fire(ResourcesManager.ROCKET_MEDIUM2, ResourcesManager.ROCKET_MEDIUM);
                    getSpriteManager().addSprites(missile);

                    // play sound
                    getSoundManager().playSound("laser");

                    getSceneNodes().getChildren().add(0, missile.getNode());
                }
                if (levelCounter == 3) {
                    missile = spaceShip.fire(ResourcesManager.ROCKET_LARGE2, ResourcesManager.ROCKET_LARGE);
                    getSpriteManager().addSprites(missile);

                    // play sound
                    getSoundManager().playSound("laser");

                    getSceneNodes().getChildren().add(0, missile.getNode());
                }
//                

            } 
//            else if (event.getButton() == MouseButton.SECONDARY) {
//                // determine when all atoms are not on the game surface. Ship should be one sprite left.
//
//                // stop ship from moving forward
//                spaceShip.applyTheBrakes(event.getX(), event.getY());
//                // move forward and rotate ship
//                spaceShip.plotCourse(event.getX(), event.getY(), true);
//            }
        };

 
        // Initialize input
        primaryStage.getScene().setOnMousePressed(fireOrMove);
        // set up stats
        EventHandler keyEvents = (EventHandler<KeyEvent>) (KeyEvent event) -> {
            if (shieldHit == false && levelCounter != 0) {
                if (KeyCode.SPACE == event.getCode()) {
                    spaceShip.shieldToggle();
                    return;
                }
            }
            spaceShip.changeWeapon(event.getCode());
            if (event.getCode() == KeyCode.W) {;
                spaceShip.plotCourse(spaceShip.getCenterX(), spaceShip.getCenterY() - 100, true);
            } else if (event.getCode() == KeyCode.A) {
                spaceShip.plotCourse(spaceShip.getCenterX() - 100, spaceShip.getCenterY(), true);
            } else if (event.getCode() == KeyCode.S) {
                spaceShip.plotCourse(spaceShip.getCenterX(), spaceShip.getCenterY() + 100, true);
            } else if (event.getCode() == KeyCode.D) {
                spaceShip.plotCourse(spaceShip.getCenterX() + 100, spaceShip.getCenterY(), true);
            }
            
            
            if (event.getCode() == KeyCode.ENTER) {
                if (getSpriteManager().getAllSprites().size() == 1 || hitCounter == 3) {
                    levelCounter++;
                    if (levelCounter == 1 || hitCounter == 3) {
                        hitCounter = 0;
                        spaceShip.changeShip(ResourcesManager.SPACE_SHIP_SMALL);
                        generateManySpheres(8);
                        eventLabel.setText(" ");
                        subEventLabel.setText(" ");
                        lives.setText("Lives: " + Integer.toString(3 - hitCounter));
                        level.setText("Level: 1");
                        shieldHit = false;
                        currentScore = 0;
                        
                    }
                    else if (levelCounter == 2 && hitCounter < 3) {
                        hitCounter = 0;
                        spaceShip.changeShip(ResourcesManager.SPACE_SHIP_MEDIUM);
                        generateManySpheres(10);
                        eventLabel.setText(" ");
                        subEventLabel.setText(" ");
                        lives.setText("Lives: " + Integer.toString(3 - hitCounter));
                        level.setText("Level: 2");
                        shieldHit = false;
                    } 
                    else if (levelCounter == 3 && hitCounter < 3) {
                        hitCounter = 0;
                        spaceShip.changeShip(ResourcesManager.SPACE_SHIP_LARGE);
                        generateManySpheres(12);
                        eventLabel.setText(" ");
                        subEventLabel.setText(" ");
                        lives.setText("Lives: " + Integer.toString(3 - hitCounter));
                        level.setText("Level: 3");
                        shieldHit = false;
//                        levelCounter = 0;
                    }

                }
            }
            
        };
        primaryStage.getScene().setOnKeyPressed(keyEvents);

        // set up stats
        EventHandler showMouseMove = (EventHandler<MouseEvent>) (MouseEvent event) -> {
            mousePtLabel.setText("Mouse PT = (" + event.getX() + ", " + event.getY() + ")");
        };

        primaryStage.getScene().setOnMouseMoved(showMouseMove);
    }

    /**
     * Make some more space spheres (Atomic particles)
     *
     * @param numSpheres The number of random sized, color, and velocity atoms
     * to generate.
     */
    private void generateManySpheres(int numSpheres) {
        Random rnd = new Random();
        Scene gameSurface = getGameSurface();
        for (int i = 0; i < numSpheres; i++) {
            
            int randNum = ThreadLocalRandom.current().nextInt(1, 6);
            Atom atom = new Atom(ResourcesManager.getInvaderSprites().get(randNum));
            
            ImageView atomImage = atom.getImageViewNode();
            // random 0 to 2 + (.0 to 1) * random (1 or -1)
            // Randomize the location of each newly generated atom.
            if (levelCounter == 1) {
                atom.setVelocityX((rnd.nextInt(2) + rnd.nextDouble()) * (rnd.nextBoolean() ? 1 : -1));
                atom.setVelocityY((rnd.nextInt(2) + rnd.nextDouble()) * (rnd.nextBoolean() ? 1 : -1));
            }
            else if (levelCounter == 2) {
               atom.setVelocity(3, 3);
            }
            else if (levelCounter == 3) {
                atom.setVelocity(4, 4);
            }
            // random x between 0 to width of scene
            double newX = rnd.nextInt((int) gameSurface.getWidth() - 100);

            if (newX > (gameSurface.getWidth() - (rnd.nextInt(15) + 5 * 2))) {
                newX = gameSurface.getWidth() - (rnd.nextInt(15) + 5 * 2);
            }

            double newY = rnd.nextInt((int) (gameSurface.getHeight() - 300));
            if (newY > (gameSurface.getHeight() - (rnd.nextInt(15) + 5 * 2))) {
                newY = gameSurface.getHeight() - (rnd.nextInt(15) + 5 * 2);
            }

            atomImage.setTranslateX(newX);
            atomImage.setTranslateY(newY);
            atomImage.setVisible(true);
            atomImage.setId("invader");
            atomImage.setCache(true);
            atomImage.setCacheHint(CacheHint.SPEED);
            atomImage.setManaged(false);
            
            //Use this method to change the velocity of invaders
//            atom.setVelocity(newX, newY);

            // add to actors in play (sprite objects)
            getSpriteManager().addSprites(atom);

            // add sprite's 
            getSceneNodes().getChildren().add(atom.getNode());
            
        }
    }

    /**
     * Each sprite will update it's velocity and bounce off wall borders.
     *
     * @param sprite - An atomic particle (a sphere).
     */
    @Override
    protected void handleUpdate(Sprite sprite) {
        // advance object
        sprite.update();
        if (sprite instanceof Missile) {
            removeMissiles((Missile) sprite);
        } else {
            bounceOffWalls(sprite);
        }
    }

    /**
     * Change the direction of the moving object when it encounters the walls.
     *
     * @param sprite The sprite to update based on the wall boundaries. TODO The
     * ship has got issues.
     */
    private void bounceOffWalls(Sprite sprite) {
        // bounce off the walls when outside of boundaries

        Node displayNode;
        if ((sprite instanceof Ship)) {
            displayNode = sprite.getNode();//((Ship)sprite).getCurrentShipImage();
        } else {
            displayNode = sprite.getNode();
        }
        // Get the group node's X and Y but use the ImageView to obtain the width.
        if (sprite.getNode().getTranslateX() > (getGameSurface().getWidth() - displayNode.getBoundsInParent().getWidth())
                || displayNode.getTranslateX() < 0) {
            // bounce the opposite direction
            if (sprite instanceof Ship) {
                sprite.setVelocityX(0);
            }
            sprite.setVelocityX(sprite.getVelocityX() * -1);
        }
        // Get the group node's X and Y but use the ImageView to obtain the height.
        if (sprite.getNode().getTranslateY() > getGameSurface().getHeight() - displayNode.getBoundsInParent().getHeight()
                || sprite.getNode().getTranslateY() < 0) {
            if (sprite instanceof Ship) {
                sprite.setVelocityY(0);
            }
            sprite.setVelocityY(sprite.getVelocityY() * -1);
        }
    }

    /**
     * Remove missiles when they reach the wall boundaries.
     *
     * @param missile The missile to remove based on the wall boundaries.
     */
    private void removeMissiles(Missile missile) {
        // bounce off the walls when outside of boundaries
        if (missile.getNode().getTranslateX() > (getGameSurface().getWidth()
                - missile.getNode().getBoundsInParent().getWidth())
                || missile.getNode().getTranslateX() < 0) {

            getSpriteManager().addSpritesToBeRemoved(missile);
            getSceneNodes().getChildren().remove(missile.getNode());

        }
        if (missile.getNode().getTranslateY() > getGameSurface().getHeight()
                - missile.getNode().getBoundsInParent().getHeight()
                || missile.getNode().getTranslateY() < 0) {

            getSpriteManager().addSpritesToBeRemoved(missile);
            getSceneNodes().getChildren().remove(missile.getNode());
        }
    }

    /**
     * How to handle the collision of two sprite objects. Stops the particle by
     * zeroing out the velocity if a collision occurred. /** How to handle the
     * collision of two sprite objects. Stops the particle by
     *
     *
     * @param spriteA Sprite from the first list.
     * @param spriteB Sprite from the second list.
     * @return boolean returns a true if the two sprites have collided otherwise
     * false.
     */
    // Shield should only be used once per level
    @Override
    protected boolean handleCollision(Sprite spriteA, Sprite spriteB) {
        //Ensure sprites are not equal
        if (spriteA != spriteB) {
            if (spriteA.collide(spriteB)) {
                if ((spriteA == spaceShip || spriteB == spaceShip) && (!(spriteA instanceof Missile) && !(spriteB instanceof Missile) && spaceShip.isShieldOn())) {
                    shieldHit = true;
                    spaceShip.shieldToggle();
                }
                else if (spriteA == spaceShip || spriteB == spaceShip && !(spriteA instanceof Missile) && !(spriteB instanceof Missile) && !(spaceShip.isShieldOn())) {
                    hitCounter++;
                    lives.setText("Lives: " + Integer.toString(3 - hitCounter));
                    if (hitCounter >= 3) {
                        lives.setText("Lives: ");
                        eventLabel.setText("GAME OVER");
                        //TODO: on death, objects should clear
//                        List removeList = (List) getSpriteManager().getAllSprites().stream().filter(Sprite -> (Sprite != spaceShip));
//                        for (int i = 0; i < removeList.size(); i++) {
//                            System.out.println(removeList.get(i));
//                        }
//                        getSpriteManager().getAllSprites().removeIf(Sprite -> (Sprite != spaceShip));
                        subEventLabel.setText("Press 'enter' to continue");
                        currentScore = 0;
                        hitCounter = 3;
                        levelCounter = 0;
                    }
                }
                
                //Ensures that two invaders cannot destroy eachother
                if (spriteA != spaceShip && !(spriteA instanceof Missile) && spriteB != spaceShip && !(spriteB instanceof Missile)) {
                    return false;
                }
                //A missile or ship cannot be destroyed in the event of a collision
                if (spriteA != spaceShip && !(spriteA instanceof Missile)) {
                    spriteA.handleDeath(this);
                    currentScore++;
                    score.setText("Score: " + currentScore);
                }
                if (spriteB != spaceShip && !(spriteB instanceof Missile)) {
                    spriteB.handleDeath(this);
                    score.setText("Score: " + currentScore);
                }
            }
        }
        
        if (getSpriteManager().getAllSprites().size() == 1 && hitCounter < 3 && levelCounter == 0) {
            eventLabel.setText("SPACE INVADERS");
            subEventLabel.setText("Press 'enter' to continue");
        }
        if (getSpriteManager().getAllSprites().size() == 1 && hitCounter < 3 && levelCounter > 0) {
            eventLabel.setText("LEVEL " + levelCounter + " COMPLETE");
            subEventLabel.setText("Press 'enter' to continue");
        }
        if (getSpriteManager().getAllSprites().size() == 1 && hitCounter < 3 && levelCounter == 3) {
            levelCounter = 0;
        }
        return false;
    }
}
