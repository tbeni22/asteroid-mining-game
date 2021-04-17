package map.entity;

import map.BillOfResources;
import map.asteroid.*;

import java.util.ArrayList;

/**
 * Class TeleportGate
 */
public class TeleportGate implements Steppable {

    /**
     * The bill to build a teleportgate(pair).
     */
    private static BillOfResources billToBuild;
    /**
     * The asteroid, where the teleportgate placed on.
     */
    private Asteroid currentAsteroid;
    /**
     * The pair of this teleportgate.
     */
    private TeleportGate otherGate;

    /**
     * Determines that the teleportgate has been hitted by sunflare.
     */
    private boolean crazy;

    /**
     * Constructor of the TeleportGate, which initialize the resources to build a gate(pair).
     */
    public TeleportGate() {
        initBillToBuild();
        crazy=false;
    }

    /**
     * Creates a teleportgate(pair) if there is enough resources to build.
     *
     * @param ownedResources The resources of settler, whereof the gate can be built.
     * @return with the gate-pair.
     */
    public static ArrayList<TeleportGate> create(ArrayList<Resource> ownedResources) {
        boolean hasResourcesToBuildTeleport = billToBuild.use(ownedResources);
        if (hasResourcesToBuildTeleport) {
            ArrayList<TeleportGate> gates = new ArrayList<>();
            TeleportGate t1 = new TeleportGate();
            TeleportGate t2 = new TeleportGate();
            t1.otherGate = t2;
            t2.otherGate = t1;
            gates.add(t1);
            gates.add(t2);
            return gates;
        }
        return null;
    }


    /**
     * Getter of the current asteroid, not used in the test.
     *
     * @return currentAsteroid.
     */
    private Asteroid getCurrentAsteroid() {
        return currentAsteroid;
    }

    /**
     * Sets the current asteroid, not used in the test.
     */
    public void setCurrentAsteroid(Asteroid asteroid) {
        currentAsteroid = asteroid;
    }

    /**
     * Initialize the resources to the bill, which is required to build a gate(pair).
     */
    private void initBillToBuild() {
        billToBuild = new BillOfResources();
        billToBuild.addResources(new Iron());
        billToBuild.addResources(new Iron());
        billToBuild.addResources(new Ice());
        billToBuild.addResources(new Uranium());
    }


    /**
     * Returns an asteroid, where the other gate is placed on.
     *
     * @return The asteroid, where the other gate is placed on.
     */
    public Asteroid getOtherSide() {
        return otherGate.getCurrentAsteroid();
    }

    public void hitBySunflare(){
        crazy=true;
    }

    /**
     * At the finish of the round the teleportgate move to anoteher asteroid (which hasn't got teleportgate),
     * if the telport gate has been hitted by sunflare before.
     */
    private void move(){
        ArrayList<Asteroid> neighbours = currentAsteroid.getNeighboursWithoutTeleportGate();
        currentAsteroid.removeTeleportGate();
        neighbours.get(neighbours.size()-1).setTeleportGate(this);
    }

    /**
     * Overrides the Steppable interface's step method.
     */
    @Override
    public void step() {
        if(crazy){
            move();
        }
    }




    /*
     * TODO: MUST BE REMOVED after tests.
     */
    private String name;

    @Override
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
