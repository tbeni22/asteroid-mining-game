package map.entity;

import map.asteroid.Asteroid;
import utility.OutputFormatter;

/**
 * Class map.entity.Entity
 */
abstract public class Entity {

    //
    // Fields
    //

    /**
     * Entity's name.
     */
    protected String name;

    /**
     * Asteroid.
     */
    protected Asteroid asteroid;

    /**
     * Constructor.
     */
    public Entity() {
    }


    /**
     * Get the value of name
     *
     * @return the value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the value of name
     *
     * @param name the new value of name
     */
    public void setName(String name) {
        this.name = name;
    }

    //
    // Other methods
    //

    /**
     * @param whereTo
     */
    public void move(Asteroid whereTo) {
        OutputFormatter.OutputCall("move(" + whereTo.toString() + ") - " + name);
        if (asteroid != null) {
            asteroid.removeEntity(this);
        }
        whereTo.acceptEntity(this);
        asteroid = whereTo;
        OutputFormatter.OutputReturn("return");
    }


    /**
     * Entity drills.
     */
    public void drill() {
        OutputFormatter.OutputCall("drill() - " + this.name);
        asteroid.drilled();
        OutputFormatter.OutputReturn("return");
    }


    /**
     * Entity dies.
     */
    public void die() {
        OutputFormatter.OutputCall("die() - " + this.name);
        asteroid.removeEntity(this);
        OutputFormatter.OutputReturn("return");
    }


    /**
     * Asteroid explodes.
     */
    public void asteroidExploded() {
        OutputFormatter.OutputCall("asteroidExploded() - " + this.name);
        die();
        OutputFormatter.OutputReturn("return");
    }


}
