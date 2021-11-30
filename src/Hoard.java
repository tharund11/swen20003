/** Hoard subclass inherits from FruitHoldingActor (second level of inheritance).
 */
public class Hoard extends FruitHoldingActor{

    /** Create a hoard actor.
     * @param xCoordinate X-coordinate of hoard's current position
     * @param yCoordinate Y-coordinate of hoard's current position
     */
    public Hoard(double xCoordinate, double yCoordinate){
        super("res/images/hoard.png", xCoordinate,yCoordinate, "Hoard", 0);
    }
}