import bagel.*;

/** Abstract class inherits from Actor (first level of inheritance).
 */
public abstract class FruitHoldingActor extends Actor {
    private int fruitCount;
    private final Font font;

    /** Create a FruitHoldingActor.
     * @param imageLocation File location of image
     * @param xCoordinate X-coordinate of actor's current position
     * @param yCoordinate Y-coordinate of actor's current position
     * @param type Type of actor
     * @param fruitCount Number of fruits held by actor
     */
    public FruitHoldingActor(String imageLocation, double xCoordinate, double yCoordinate,
                             String type, int fruitCount){
        super(imageLocation, xCoordinate, yCoordinate, type);
        this.fruitCount = fruitCount;
        this.font = new Font("res/VeraMono.ttf", 24);
    }

    /** @return Number of fruit as an int
     */
    public int getFruitCount() {
        return fruitCount;
    }

    /** Method that increments the number of fruit.
     */
    public void increaseCount(){
        this.fruitCount++;
    }

    /** Method that decrements the number of fruit.
     */
    public void decreaseCount(){
        this.fruitCount--;
    }

    /** Method that draws the image and the current number of fruit.
     */
    @Override
    public void drawImage(){
        super.drawImage();
        font.drawString(String.valueOf(this.fruitCount), this.getCoordinates().x, this.getCoordinates().y);
    }
}