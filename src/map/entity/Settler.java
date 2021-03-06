package map.entity;

import control.ActionFailedException;
import control.Game;
import map.asteroid.Asteroid;
import map.resource.Resource;
import view.GameWindow;
import view.ResourceChooser;
import view.SettlerActionsView;
import view.SettlerInventoryView;

import java.util.ArrayList;

/**
 * Class map.entity.Settler
 */
public class Settler extends Entity {

    private static final long serialVersionUID = 4265198911107303976L;
    /**
     * List of resources in the Settler's inventory.
     */
    private final ArrayList<Resource> resources = new ArrayList<>();

    /**
     * List of teleportgates in the Settler's inventory.
     */
    private final ArrayList<TeleportGate> teleports = new ArrayList<>();

    /**
     * Reference to the actions view, where the player can choose from different actions.
     */
    private SettlerActionsView actionsView;

    /**
     * Reference to the inventory view, where data of the settler is shown
     */
    private transient SettlerInventoryView settlerView;

    /**
     * Constructor of Settler.
     */
    public Settler(String name) {
        super(name);
    }

    /**
     * Returns a list of resources.
     *
     * @return list of resources.
     */
    public ArrayList<Resource> getResources() {
        return resources;
    }

    /**
     * Settler mines the asteroid for resource.
     * Asks for item exchange if cargo inventory is full and places the resource from the asteroid to the cargo otherwise.
     */
    public void mine() throws ActionFailedException {
        if (asteroid.getResource() != null) { // if there's any resource to mine
            if (resources.size() < 10) { // if there's space in cargo
                Resource r = asteroid.mined();
                if (r != null) {
                    resources.add(r); // add resource to inventory
                    Game.getInstance().nextPlayer(); // the settler used its only step
                } else throw new ActionFailedException("Mine returned null.");
            } else { // if there is no space in cargo, another resource must be placed back to get the new one
                ResourceChooser rc = new ResourceChooser(resources);
                Resource resourceToExchange = rc.chooseResource();
                if (resources.contains(resourceToExchange)) { // if the resource is owned by the settler
                    resources.add(asteroid.mined());
                    asteroid.placeResource(resourceToExchange); // place back the resource
                    resources.remove(resourceToExchange); // remove the placed back resource from inventory
                    Game.getInstance().nextPlayer(); // the settler used its only step
                } else throw new ActionFailedException("Invalid resource selected.");
            }
        } else throw new ActionFailedException("No resource in the Asteroid.");
    }

    /**
     * Settler moves.
     *
     * @param whereTo The asteroid where the settler should move to.
     */
    @Override
    public void move(Asteroid whereTo) {
        super.move(whereTo);
        if (asteroid != null) {
            asteroid.getAsteroidView().updateView();
        }
        Game.getInstance().nextPlayer();
    }

    /**
     * Places a selected resource from the settlers inventory to the asteroid's core
     *
     * @param resource The resource to be placed
     */
    public void placeBack(Resource resource) throws ActionFailedException {
        if (!resources.contains(resource)) // if the resource is not in the settler's inventory, the action is not valid
            throw new ActionFailedException("Resource is not in players inventory!");
        if (asteroid.placeResource(resource)) { // try to place back the resource
            resources.remove(resource); // remove placed resource
            Game.getInstance().nextPlayer(); // if it was successful, notify the game
        } else throw new ActionFailedException("Couldn't place resource.");
    }

    /**
     * Gets the number of teleport gates a settler has.
     *
     * @return The number of the settler's teleports.
     */
    public int getTeleportNumber() {
        return teleports.size();
    }

    /**
     * Settler drills
     *
     * @throws ActionFailedException When the asteroid couldn't be mined.
     */
    @Override
    public void drill() throws ActionFailedException {
        asteroid.drilled();
        Game.getInstance().nextPlayer();
    }

    /**
     * Settler tries to build a robot.
     */
    public void buildRobot() throws ActionFailedException {
        Robot.create(asteroid, resources);
        Game.getInstance().nextPlayer();
    }

    /**
     * Settler tries to build a teleportgate if there aren't any teleportgates in the cargo hold.
     */
    public void buildTeleport() throws ActionFailedException {
        // can only build new teleportgates if there's room for it in cargo (max capacity is 3).
        if (teleports.size() + 2 <= 3) {
            ArrayList<TeleportGate> teleportGates = TeleportGate.create(resources);
            if (teleportGates != null) {
                for (TeleportGate teleportGate : teleportGates) {
                    teleports.add(teleportGate);
                    teleportGate.setSettler(this);
                }
                Game.getInstance().nextPlayer();
            } else throw new ActionFailedException("Teleportgates have not been built.");
        }
    }

    /**
     * Settler tries to place a teleport which is only successful when the asteroid has none and
     * the Settler has at least one in its inventory.
     */
    public void placeTeleport() {
        if (teleports.size() != 0) {
            boolean success = asteroid.setTeleportGate(teleports.get(0));
            if (success) {
                teleports.get(0).setSettler(null);
                teleports.remove(0);
                Game.getInstance().nextPlayer();
            }
        }
    }

    /**
     * Removes dead teleportgate from cargo.
     *
     * @param t Dead teleportgate to be removed.
     */
    public void removeTeleportGate(TeleportGate t) {
        teleports.remove(t);
    }

    /**
     * Settler dies.
     */
    @Override
    public void die() {
        super.die();
        for (TeleportGate tg : teleports)
            tg.die(); // all teleport gates in storage die
        Game.getInstance().removeSettler(this);
    }

    /**
     * Game calls this method when this settler should move next. Modifies related variables.
     */
    public void yourTurn() {
        actionsView = GameWindow.getActionsView();
        settlerView = GameWindow.getInventoryView();
        actionsView.updateView(this);
        settlerView.updateView(this);
    }
}
