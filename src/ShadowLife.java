import bagel.*;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

/** This is the ShadowLife class that runs the
 * game. It extends AbstractGame from Bagel.
 * @author Tharun Dharmawickrema
 */

public class ShadowLife extends AbstractGame {

    private static final int DEFAULT_WIDTH = 1024;
    private static final int DEFAULT_HEIGHT = 768;
    private final Image background;
    private static final ArrayList<Actor> actors = new ArrayList<Actor>();
    private static String fileName;

    // variables to hold time related things
    private static long prevTime, newTime;
    private static int TICK_RATE, MAX_TICKS;
    private int tickCounter = 0;

    /** Create the ShadowLife object (game).
     */
    public ShadowLife(){
        super(DEFAULT_WIDTH, DEFAULT_HEIGHT, "Shadow Life");
        background = new Image("res/images/background.png");
        this.readCSV();
    }

    /** Main method that takes the arguments in, creates
     * the ShadowLife object and runs the game.
     * @param args Tick rate, Max ticks and World file location
     */
    public static void main(String[] args){

        // 3 arguments should be provided
        if (args.length != 3){
            System.out.println("usage: ShadowLife <tick rate> <max ticks> <world file>");
            System.exit(-1);
        }

        // Should be non-negative integers
        if ((Integer.parseInt(args[0]) < 0) || (Integer.parseInt(args[1]) < 0)){
            System.out.println("usage: ShadowLife <tick rate> <max ticks> <world file>");
            System.exit(-1);
        }
        TICK_RATE = Integer.parseInt(args[0]);
        MAX_TICKS = Integer.parseInt(args[1]);
        fileName = args[2];

        ShadowLife game = new ShadowLife();
        prevTime = System.currentTimeMillis(); // store current time
        game.run();
    }

    /** Method that adds an actor to the list.
     * @param actor Actor to be added
     */
    public static void addActor(Actor actor){
        actors.add(actor);
    }

    /** Method that removes an actor from the list.
     * @param actor Actor to be removed
     */
    public static void removeActor(Actor actor){
        actors.remove(actor);
    }

    private void readCSV(){

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))){
            // temporary variables
            String line = null;
            String[] dataInLine = new String[3];
            int lineCount = 0;

            // reading file, creating and storing actors
            while((line = br.readLine()) != null){
                lineCount++;
                dataInLine = line.split(",");

                // checking validity of line
                if (dataInLine.length != 3){
                    System.out.println("error: in file \"" + fileName + "\" at line " + lineCount);
                    System.exit(-1);
                }

                if ((Integer.parseInt(dataInLine[1]) < 0) || (Integer.parseInt(dataInLine[1]) > DEFAULT_WIDTH)){
                    System.out.println("error: in file \"" + fileName + "\" at line " + lineCount);
                    System.exit(-1);
                }

                if ((Integer.parseInt(dataInLine[2]) < 0) || (Integer.parseInt(dataInLine[2]) > DEFAULT_HEIGHT)){
                    System.out.println("error: in file \"" + fileName + "\" at line " + lineCount);
                    System.exit(-1);
                }

                switch (dataInLine[0]) {
                    case "Tree":
                        actors.add(new Tree(Double.parseDouble(dataInLine[1]), Double.parseDouble(dataInLine[2])));
                        break;
                    case "Stockpile":
                        actors.add(new Stockpile(Double.parseDouble(dataInLine[1]), Double.parseDouble(dataInLine[2])));
                        break;
                    case "Hoard":
                        actors.add(new Hoard(Double.parseDouble(dataInLine[1]), Double.parseDouble(dataInLine[2])));
                        break;
                    case "GoldenTree":
                        actors.add(new StationaryActor(Double.parseDouble(dataInLine[1]),
                                Double.parseDouble(dataInLine[2]), "GoldenTree"));
                        break;
                    case "Pad":
                        actors.add(new StationaryActor(Double.parseDouble(dataInLine[1]),
                                Double.parseDouble(dataInLine[2]), "Pad"));
                        break;
                    case "Fence":
                        actors.add(new StationaryActor(Double.parseDouble(dataInLine[1]),
                                Double.parseDouble(dataInLine[2]), "Fence"));
                        break;
                    case "SignUp":
                        actors.add(new StationaryActor(Double.parseDouble(dataInLine[1]),
                                Double.parseDouble(dataInLine[2]), "SignUp"));
                        break;
                    case "SignRight":
                        actors.add(new StationaryActor(Double.parseDouble(dataInLine[1]),
                                Double.parseDouble(dataInLine[2]), "SignRight"));
                        break;
                    case "SignDown":
                        actors.add(new StationaryActor(Double.parseDouble(dataInLine[1]),
                                Double.parseDouble(dataInLine[2]), "SignDown"));
                        break;
                    case "SignLeft":
                        actors.add(new StationaryActor(Double.parseDouble(dataInLine[1]),
                                Double.parseDouble(dataInLine[2]), "SignLeft"));
                        break;
                    case "Pool":
                        actors.add(new StationaryActor(Double.parseDouble(dataInLine[1]),
                                Double.parseDouble(dataInLine[2]), "Pool"));
                        break;
                    case "Gatherer":
                        actors.add(new Gatherer(Double.parseDouble(dataInLine[1]), Double.parseDouble(dataInLine[2])));
                        break;
                    case "Thief":
                        actors.add(new Thief(Double.parseDouble(dataInLine[1]), Double.parseDouble(dataInLine[2])));
                        break;
                    default:
                        System.out.println("error: in file \"" + fileName + "\" at line " + lineCount);
                        System.exit(-1);
                }
            }
        }catch (Exception e){
            System.out.println("error: file \"" + fileName + "\" not found");
            System.exit(-1);
        }
    }

    /** Method that runs the game. Overriding the
     * update method in the AbstractGame class.
     * @param input Input object
     */
    @Override
    public void update(Input input){

        background.drawFromTopLeft(0, 0);

        // check if one tick has passed
        if ((newTime = System.currentTimeMillis()) - prevTime >= TICK_RATE) {
            tickCounter++;
            prevTime = newTime;

            if (tickCounter == MAX_TICKS){
                System.out.println("Timed out");
                System.exit(-1);
            }

            // making a copy of actor list to iterate over
            ArrayList<Actor> actorsCopy = new ArrayList<>(actors);

            // do gatherer's tick first, then thief's
            for (Actor actor : actorsCopy) {
                if (actor.getType().equals("Gatherer")){
                    actor.action();
                }
            }

            for (Actor actor : actorsCopy) {
                if (actor.getType().equals("Thief")){
                    actor.action();
                }
            }
        }

        for (Actor actor : actors) {
            actor.drawImage();
        }

        // check if any actors are active
        if (!this.checkActive()){
            this.endSimulation();
        }
    }

    /** Method that finds all the actors in the given tile.
     * @param x X-coordinate of tile
     * @param y Y-coordinate of tile
     * @return List of actors in tile, as an ArrayList
     */
    public static ArrayList<Actor> findSameTileActors(double x, double y){

        ArrayList<Actor> sameTileActors = new ArrayList<Actor>();

        for (Actor actor: actors){
            if ((actor.getCoordinates().x == x) && (actor.getCoordinates().y == y)){
                sameTileActors.add(actor);
            }
        }
        return sameTileActors;
    }

    private boolean checkActive(){
        // check if there is at least one gatherer/thief still active
        for (Actor actor : actors) {
            if (actor.getType().equals("Gatherer") || actor.getType().equals("Thief")){
                RolePlayingActor temp = (RolePlayingActor) actor;
                if (temp.isActive()){
                    return true;
                }
            }
        }
        return false;
    }

    private void endSimulation(){
        System.out.println(tickCounter + " ticks");
        for (Actor actor : actors){
            if (actor.getType().equals("Stockpile") || actor.getType().equals("Hoard")){
                FruitHoldingActor temp = (FruitHoldingActor) actor;
                System.out.println(temp.getFruitCount());
            }
        }
        System.exit(0);
    }
}