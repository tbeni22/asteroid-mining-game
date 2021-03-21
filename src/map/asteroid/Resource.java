package map.asteroid;

import utility.OutputFormatter;

/**
 * Class map.asteroid.Resource
 * Represents a resource object, which is used to build things
 */
public abstract class Resource {

    protected Asteroid asteroid;

    /**
     * Set the asteroid field of the resource
     * This represents the asteroid to which this resource is attached
     */
    public void setAsteroid(Asteroid asteroid) {
        this.asteroid = asteroid;
    }

    /**
     * Called when the asteroid with this resource in its core is drilled in perihelion
     * Default implementation
     */
    public void drilledInPerihelion() {
        OutputFormatter.OutputCall("drilledInPerihelion() - " + this.toString());
        OutputFormatter.OutputReturn("return");
    }

    /**
     * Returns whether the parameter is the same type of resource as this resource
     * @param res The other resource object
     * @return True, if the 2 resources are the same type
     */
    public boolean isCompatibleWith(Resource res) {
        OutputFormatter.OutputCall("isCompatibleWith("+res.toString()+")");
        boolean result = res.getClass().equals(this.getClass()); // check
        OutputFormatter.OutputReturn("return - " + result);
        return result;
    }
}
