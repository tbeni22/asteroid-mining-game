package map.asteroid;

import map.entity.Entity;
import map.entity.TeleportGate;
import utility.OutputFormatter;

import java.util.ArrayList;


/**
 * Class map.asteroid.Asteroid
 */
public class Asteroid {

    //
    // Fields
    //

    private final ArrayList<Entity> entities = new ArrayList<>();
    private final ArrayList<Asteroid> neighbours = new ArrayList<>();
    private int surfaceThickness;
    private boolean inPerihelion;
    private String name;
    private Resource resource;
    private TeleportGate teleportGate;
//    private final ArrayList<map.asteroid.Resource> resourceVector = new ArrayList<>();
//    private final Vector teleportgateVector = new Vector();
//    private map.asteroid.Asteroid m_asteroid;
//    private final ArrayList<map.asteroid.Asteroid> asteroid = new ArrayList<>();

    //
    // Constructors
    //
    public Asteroid() {
    }

    //
    // Methods
    //


    //
    // Accessor methods
    //

    /**
     * Get the value of surfaceThickness
     *
     * @return the value of surfaceThickness
     */
    public int getSurfaceThickness() {
        return surfaceThickness;
    }

    /**
     * Set the value of surfaceThickness
     *
     * @param thickness the new value of surfaceThickness
     */
    public void setSurfaceThickness(int thickness) {
        surfaceThickness = thickness;
    }

    /**
     * Get the value of inPerihelion
     *
     * @return the value of inPerihelion
     */
    public boolean getInPerihelion() {
        return inPerihelion;
    }

    /**
     * Set the value of inPerihelion
     *
     * @param inPerihelion the new value of inPerihelion
     */
    public void setInPerihelion(boolean inPerihelion) {
        this.inPerihelion = inPerihelion;
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

    /**
     * Add a Entities object to the entities List
     */
    private void addEntities(Entity entity) {
        this.entities.add(entity);
    }

    /**
     * Remove a Entities object from entitiesVector List
     */
    private void removeEntities(Entity entity) {
        this.entities.remove(entity);
    }

    /**
     * Get the List of Entities objects held by entitiesVector
     *
     * @return List of Entities objects held by entitiesVector
     */
    private ArrayList<Entity> getEntitiesList() {
        return entities;
    }


    /**
     * Add a map.asteroid.Resource object to the map.asteroid.Asteroid
     */
    public void addResource(Resource resource) {
        this.resource = resource;
        resource.setAsteroid(this);
    }

    /**
     * Remove a map.asteroid.Resource object from resourceVector List
     */
    private void removeResource(/*map.asteroid.Resource new_object*/) {
//        resource.remove(new_object);
        resource = null;
    }

    /**
     * Get the List of map.asteroid.Resource objects held by resourceVector
     *
     * @return List of map.asteroid.Resource objects held by resourceVector
     */
    private Resource getResource() {
        return resource;
    }

    /**
     * Get the List of map.entity.TeleportGate objects held by teleportgateVector
     *
     * @return List of map.entity.TeleportGate objects held by teleportgateVector
     */
    private TeleportGate getTeleportGate() {
        return teleportGate;
    }


    //
    // Other methods
    //

    /**
     *
     */
    public void explode() {
    }


    /**
     *
     */
    public void drilled() {
        OutputFormatter.OutputCall("drilled() - "+this.toString());
        if (surfaceThickness == 0){
            if (inPerihelion) {
                resource.drilledInPerihelion();
                OutputFormatter.OutputReturn("return // drilledInPerihelion");
            }
        } else {
            surfaceThickness--;
            OutputFormatter.OutputReturn("return // surfaceThickness--");
        }
    }


    /**
     * @return map.asteroid.Resource
     */
    public Resource mined() {
        return null; // TODO: implementálni
    }


    /**
     * @return map.asteroid.Neighbours
     */
    public Neighbours getNeighbours() {
        return null; // TODO: implementálni
    }


    /**
     * @param entity
     */
    public void acceptEntity(Entity entity) {
        entities.add(entity);
    }


    /**
     * @param entity
     */
    public void removeEntity(Entity entity) {
    }


    /**
     * @param asteroid
     */
    public void addNeighbour(Asteroid asteroid) {
        this.neighbours.add(asteroid);
    }


    /**
     * @param asteroid
     * @return map.asteroid.Asteroid
     */
    public Asteroid removeAsteroid(Asteroid asteroid) {
        return null; // TODO: implementálni
    }


    /**
     * @param teleportGate
     */
    public void setTeleportGate(TeleportGate teleportGate) {
        this.teleportGate = teleportGate;
    }


    /**
     *
     */
    public void removeTeleportGate() {
        this.teleportGate = null;
    }


    /**
     * @param resource
     */
    public void placeResource(Resource resource) {
    }


    /**
     *
     */
    public void hitBySunflare() {
    }


    /**
     *
     */
    public void changePerihelionState() {
    }


}
