import bagel.*;
import bagel.util.Point;

/** Abstract superclass, all types of actors inherit from this class.
 */
public abstract class Actor {
    private final Image image;
    private Point coordinates; // holds x, y coordinates of actor
    private final String type;

    /** Create an actor.
     * @param imageLocation File location of image
     * @param xCoordinate X-coordinate of actor's current position
     * @param yCoordinate Y-coordinate of actor's current position
     * @param type Type of actor
     */
    public Actor(String imageLocation, double xCoordinate, double yCoordinate, String type){
        this.coordinates = new Point(xCoordinate, yCoordinate);
        this.type = type;

        // Images of StationaryActors have to be dealt with separately
        switch (type) {
            case "GoldenTree":
                this.image = new Image("res/images/gold-tree.png");
                break;
            case "Pad":
                this.image = new Image("res/images/pad.png");
                break;
            case "Fence":
                this.image = new Image("res/images/fence.png");
                break;
            case "Pool":
                this.image = new Image("res/images/pool.png");
                break;
            case "SignLeft":
                this.image = new Image("res/images/left.png");
                break;
            case "SignRight":
                this.image = new Image("res/images/right.png");
                break;
            case "SignUp":
                this.image = new Image("res/images/up.png");
                break;
            case "SignDown":
                this.image = new Image("res/images/down.png");
                break;
            default:
                this.image = new Image(imageLocation);
                break;
        }
    }

    /** @return coordinates of Actor as a Point
     */
    public Point getCoordinates() {
        return coordinates;
    }

    /** @return type of Actor as String
     */
    public String getType() {
        return type;
    }

    /** Sets new coordinate value for Actor.
     * @param coordinates New coordinates as a Point
     */
    public void setCoordinates(Point coordinates) {
        this.coordinates = coordinates;
    }

    /** Method that draws the image at the actor's current position.
     */
    public void drawImage(){
        this.image.drawFromTopLeft(this.coordinates.x, this.coordinates.y);
    }

    /** Method that performs actor's corresponding action.
     */
    public void action() {
        // May or may not be implemented at subclass level.
    }
}