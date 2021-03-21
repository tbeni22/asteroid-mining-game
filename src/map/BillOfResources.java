package map;

import map.asteroid.Resource;
import utility.OutputFormatter;
import java.util.ArrayList;

/**
 * Class map.BillOfResources
 * Used as blueprints for builds
 */
public class BillOfResources {

    private ArrayList<Resource> resourcesNeeded = new ArrayList<>();

    /**
     * Constructor of class
     */
    public BillOfResources() { }
    /*// a much nicer constructor
    public BillOfResources(ArrayList<Resource> resourcesNeeded) {
        OutputFormatter.OutputCall("create - " + this.toString());
        this.resourcesNeeded = resourcesNeeded;
        OutputFormatter.OutputReturn("return");
    }*/

    /**
     * Add a Resource object to the resources list
     * @param resource The resource to add
     */
    public void addResources(Resource resource) {
        this.resourcesNeeded.add(resource);
    }

    /**
     * Remove a Resource object from the resources list
     * @param resource The resource to remove
     */
    public void removeResources(Resource resource) {
        this.resourcesNeeded.remove(resource);
    }

    /**
     * Uses the needed resources for the build (removes them from the owned ones)
     * @param ownedResources The resources that the caller owns. Changed if used.
     * @return boolean Whether the usage was successful or not. If yes, the appropriate resources were removed.
     */
    public boolean use(ArrayList<Resource> ownedResources) {
        OutputFormatter.OutputCall("use("+ ownedResources.toString() +")");
        if (!check(ownedResources)) { // check if there are enough resources, to prevent unnecessary removal
            OutputFormatter.OutputReturn("return - false");
            return false;
        }
        for (Resource rn : resourcesNeeded) {
            for (int i = 0; i < ownedResources.size(); ++i)
                if (rn.isCompatibleWith(ownedResources.get(i))) { // check if resource types match
                    ownedResources.remove(i); // removed the found resource
                    break; // we are done with this one, don't need to remove others of the same type
                }
        }
        OutputFormatter.OutputReturn("return - true");
        return true;
    }

    /**
     * Checks whether there is enough owned resources to build the damn thing
     * @param ownedResources The resources that the caller owns.
     * @return boolean Is there enough resources for the build?
     */
    public boolean check(ArrayList<Resource> ownedResources) {
        OutputFormatter.OutputCall("check("+ ownedResources.toString() +")");

        // create a deep copy of the input array, because we need to modify it, but just locally
        ArrayList<Resource> ownedRes = (ArrayList<Resource>) ownedResources.clone();
        int okCnt = 0; // number of found resources
        for (Resource rn : resourcesNeeded) {
            boolean found = false;
            for (int i = 0; i < ownedRes.size(); ++i)
                if (rn.isCompatibleWith(ownedRes.get(i))) { // check compatibility
                    found = true; // found it :)
                    ownedRes.remove(i); // remove found resource to prevent a problem with duplicate resources
                    break;
                }
            if (!found) break;
            else ++okCnt; // increment the number of found resources
        }
        boolean resultOk = okCnt == resourcesNeeded.size(); // if it found all the necessary resources, then its okay
        OutputFormatter.OutputReturn("return - "+resultOk);
        return resultOk;
    }
}
