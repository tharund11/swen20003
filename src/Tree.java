/** Tree subclass inherits from FruitHoldingActor (second level of inheritance).
 */
public class Tree extends FruitHoldingActor{

    /** Create a tree actor.
     * @param xCoordinate X-coordinate of tree's current position
     * @param yCoordinate Y-coordinate of tree's current position
     */
    public Tree(double xCoordinate, double yCoordinate){
        super("res/images/tree.png", xCoordinate, yCoordinate, "Tree", 3);
    }
}
