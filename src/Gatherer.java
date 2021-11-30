import java.util.ArrayList;

/** Gatherer subclass inherits from RolePlayingActor (second level of inheritance).
 */

public class Gatherer extends RolePlayingActor {

    /** Create a gatherer actor.
     * @param xCoordinate X-coordinate of gatherer's current position
     * @param yCoordinate Y-coordinate of gatherer's current position
     */
    public Gatherer(double xCoordinate, double yCoordinate){
        super("res/images/gatherer.png", xCoordinate, yCoordinate, "Gatherer");
        this.setDirection(3);  // initialising direction to left
        this.setSpeed();
    }

    /** Method that overrides the action() method in Actor.
     */
    @Override
    public void action(){
        // performing gatherer's algorithm

        if (this.isActive()){
            this.move("forward");
        }

        // finding actors on same tile as this gatherer
        ArrayList<Actor> sameTile = ShadowLife.findSameTileActors(this.getCoordinates().x, this.getCoordinates().y);

        this.isOnFence(sameTile);
        this.isOnPool(sameTile);
        this.isOnSign(sameTile);
        this.isOnTree(sameTile);

        // checking if gatherer is on stockpile or hoard
        for (Actor actor: sameTile){
            if (actor.getType().equals("Hoard") || actor.getType().equals("Stockpile")){
                if (this.isCarrying()){
                    this.setCarrying(false);
                    FruitHoldingActor temp = (FruitHoldingActor) actor;
                    temp.increaseCount();
                }
                // direction is from 0 to 3, hence modulus 4
                this.setDirection((this.getDirection() + 2) % 4);
                this.setSpeed();
                break;
            }
        }
    }
}