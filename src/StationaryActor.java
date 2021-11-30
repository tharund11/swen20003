/** StationaryActor inherits from Actor (first level of inheritance).
 * StationaryActors are GoldenTree, Pad, Fence, Pool, SignUp, SignDown,
 * SignLeft, SignRight.
 */
public class StationaryActor extends Actor {

    /** Create a StationaryActor.
     * @param xCoordinate X-coordinate of actor's current position
     * @param yCoordinate Y-coordinate of actor's current position
     * @param type Type of actor
     */
    public StationaryActor(double xCoordinate, double yCoordinate, String type){

        /* imageLocation is sent as an empty string, as it is dealt with
           in the Actor class' constructor */
        super("", xCoordinate, yCoordinate, type);
    }
}