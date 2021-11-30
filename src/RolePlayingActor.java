import bagel.util.Point;
import java.util.ArrayList;

/** Abstract class inherits from Actor (first level of inheritance)
 */
public abstract class RolePlayingActor extends Actor {
    private boolean active, carrying;
    private int direction;   // 0 = UP, 1 = RIGHT, 2 = DOWN, 3 = LEFT
    private int xSpeed;  // 64 = moving right, -64 = moving left
    private int ySpeed;  // 64 = moving down, -64 = moving up

    /** Create a RolePlayingActor.
     * @param imageLocation File location of image
     * @param xCoordinate X-coordinate of actor's current position
     * @param yCoordinate Y-coordinate of actor's current position
     * @param type Type of actor
     */
    public RolePlayingActor(String imageLocation, double xCoordinate, double yCoordinate, String type){
        super(imageLocation, xCoordinate, yCoordinate, type);
        this.active = true;
        this.carrying = false;
    }

    /** @return Whether or not actor is active, as a boolean
     */
    public boolean isActive() {
        return active;
    }

    /** @return Whether or not actor is carrying fruit, as a boolean
     */
    public boolean isCarrying() {
        return carrying;
    }

    /** Sets carrying status for actor.
     * @param carrying New carrying status as a boolean
     */
    public void setCarrying(boolean carrying) {
        this.carrying = carrying;
    }

    /** Sets direction for actor.
     * @param direction New direction as an int
     */
    public void setDirection(int direction) {
        this.direction = direction;
    }

    /** @return Current direction of actor as an int
     */
    public int getDirection() {
        return direction;
    }

    /** Method that sets the x,y speed values for the current direction.
     */
    public void setSpeed() {
        if (this.direction == 0){
            // moving up
            this.ySpeed = -64;
            this.xSpeed = 0;

        } else if (this.direction == 1){
            // moving right
            this.xSpeed = 64;
            this.ySpeed = 0;

        }else if (this.direction == 2){
            // moving down
            this.ySpeed = 64;
            this.xSpeed = 0;

        }else{
            // moving left
            this.xSpeed = -64;
            this.ySpeed = 0;
        }
    }

    /** Method that moves the actor in the current direction,
     * either forward or backward.
     * @param direction forward or backward
     */
    public void move(String direction){
        double newX, newY;

        if (direction.equals("forward")){
            // forward means next position
            newX = this.getCoordinates().x + this.xSpeed;
            newY = this.getCoordinates().y + this.ySpeed;
        }else{
            // backward means previous position
            newX = this.getCoordinates().x - this.xSpeed;
            newY = this.getCoordinates().y - this.ySpeed;
        }
        this.setCoordinates(new Point(newX, newY));
    }

    /** Method that checks if actor is on a fence.
     * @param sameTile list of actors
     */
    public void isOnFence(ArrayList<Actor> sameTile){
        for (Actor actor: sameTile){
            // if on a fence, set to false and move back to prev. position
            if (actor.getType().equals("Fence")){
                this.active = false;
                this.move("backward");
                break;
            }
        }
    }

    /** Method that checks if actor is on a mitosis pool.
     * @param sameTile list of actors
     */
    public void isOnPool(ArrayList<Actor> sameTile){
        for (Actor actor: sameTile){
            if (actor.getType().equals("Pool")){

                if (this.getType().equals("Gatherer")){
                    // make two new gatherers
                    Gatherer temp = new Gatherer(this.getCoordinates().x, this.getCoordinates().y);
                    // direction is from 0 to 3, hence modulus 4
                    temp.setDirection((this.getDirection() + 3) % 4);
                    temp.setSpeed();
                    temp.move("forward");
                    ShadowLife.addActor(temp);

                    Gatherer temp2 = new Gatherer(this.getCoordinates().x, this.getCoordinates().y);
                    temp2.setDirection((this.getDirection() + 1) % 4);
                    temp2.setSpeed();
                    temp2.move("forward");
                    ShadowLife.addActor(temp2);

                } else{
                    // make two new thieves
                    Thief temp = new Thief(this.getCoordinates().x, this.getCoordinates().y);
                    temp.setDirection((this.getDirection() + 3) % 4);
                    temp.setSpeed();
                    temp.move("forward");
                    ShadowLife.addActor(temp);

                    Thief temp2 = new Thief(this.getCoordinates().x, this.getCoordinates().y);
                    temp2.setDirection((this.getDirection() + 1) % 4);
                    temp2.setSpeed();
                    temp2.move("forward");
                    ShadowLife.addActor(temp2);
                }
                // remove the current thief/gatherer
                this.active = false;
                ShadowLife.removeActor(this);
                break;
            }
        }
    }

    /** Method that checks if actor is on a sign.
     * @param sameTile list of actors
     */
    public void isOnSign(ArrayList<Actor> sameTile){
        label:
        for (Actor actor: sameTile){
            // if on a sign, change actor's direction
            switch (actor.getType()) {
                case "SignUp":
                    this.direction = 0;
                    this.setSpeed();
                    break label;
                case "SignRight":
                    this.direction = 1;
                    this.setSpeed();
                    break label;
                case "SignDown":
                    this.direction = 2;
                    this.setSpeed();
                    break label;
                case "SignLeft":
                    this.direction = 3;
                    this.setSpeed();
                    break label;
            }
        }
    }

    /** Method that checks if actor is on a tree.
     * @param sameTile list of actors
     */
    public void isOnTree(ArrayList<Actor> sameTile){
        if (!this.carrying){
            for (Actor actor: sameTile){

                if (actor.getType().equals("Tree")){
                    Tree temp = (Tree) actor;
                    if (temp.getFruitCount() >= 1){
                        this.carrying = true;
                        temp.decreaseCount();

                        // only the gatherer rotates after meeting a tree
                        if (this.getType().equals("Gatherer")){
                            this.direction = (this.direction + 2) % 4;
                            this.setSpeed();
                        }
                    }
                    break;
                }else if (actor.getType().equals("GoldenTree")) {
                    // if golden tree, no fruitCount to update
                    this.carrying = true;
                    if (this.getType().equals("Gatherer")){
                        this.direction = (this.direction + 2) % 4;
                        this.setSpeed();
                    }
                    break;
                }
            }
        }
    }
}