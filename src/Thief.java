import java.util.ArrayList;

/** Thief subclass inherits from RolePlayingActor (second level of inheritance).
 */

public class Thief extends RolePlayingActor {
    private boolean consuming;

    /** Create a thief actor.
     * @param xCoordinate X-coordinate of thief's current position
     * @param yCoordinate Y-coordinate of thief's current position
     */
    public Thief (double xCoordinate, double yCoordinate){
        super("res/images/thief.png", xCoordinate, yCoordinate,"Thief");
        this.consuming = false;
        this.setDirection(0);  // initialising direction to up
        this.setSpeed();
    }

    /** Method that overrides the action() method in Actor.
     */
    @Override
    public void action(){
        // performing thief's algorithm

        if (this.isActive()){
            this.move("forward");
        }

        // finding actors on same tile as this gatherer
        ArrayList<Actor> sameTile = ShadowLife.findSameTileActors(this.getCoordinates().x, this.getCoordinates().y);

        this.isOnFence(sameTile);
        this.isOnPool(sameTile);
        this.isOnSign(sameTile);

        // checking if thief is on a pad
        for (Actor actor: sameTile){
            if (actor.getType().equals("Pad")){
                    this.consuming = true;
                    break;
            }
        }

        // checking if thief is on a gatherer
        for (Actor actor: sameTile){
            if (actor.getType().equals("Gatherer")){
                // direction is from 0 to 3, hence modulus 4
                this.setDirection((this.getDirection() + 3) % 4);
                this.setSpeed();
                break;
            }
        }

        this.isOnTree(sameTile);

        // checking if thief is on a hoard or a stockpile
        for (Actor actor: sameTile){

            if (actor.getType().equals("Hoard")){
                if (this.consuming){
                    this.consuming = false;

                    if (!this.isCarrying()){
                        Hoard temp = (Hoard) actor;
                        if (temp.getFruitCount() >= 1){
                            this.setCarrying(true);
                            temp.decreaseCount();
                        }else{
                            this.setDirection((this.getDirection() + 1) % 4);
                            this.setSpeed();
                        }
                    }
                }else if (this.isCarrying()){
                    this.setCarrying(false);
                    Hoard temp = (Hoard) actor;
                    temp.increaseCount();
                    this.setDirection((this.getDirection() + 1) % 4);
                    this.setSpeed();
                }

            }else if (actor.getType().equals("Stockpile")){
                if (!this.isCarrying()){
                    Stockpile temp = (Stockpile) actor;

                    if (temp.getFruitCount() >= 1){
                        this.setCarrying(true);
                        this.consuming = false;
                        temp.decreaseCount();
                        this.setDirection((this.getDirection() + 1) % 4);
                        this.setSpeed();
                    }
                }else {
                    this.setDirection((this.getDirection() + 1) % 4);
                    this.setSpeed();
                }
            }
        }
    }
}