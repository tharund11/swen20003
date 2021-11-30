/** Stockpile subclass inherits from FruitHoldingActor (second level of inheritance).
 */
public class Stockpile extends FruitHoldingActor{

    /** Create a stockpile actor.
     * @param xCoordinate X-coordinate of stockpile's current position
     * @param yCoordinate Y-coordinate of stockpile's current position
     */
    public Stockpile(double xCoordinate, double yCoordinate){
        super("res/images/cherries.png", xCoordinate,yCoordinate, "Stockpile", 0);
    }
}