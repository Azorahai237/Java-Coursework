import java.io.IOException;

/**
 * Program to provide information on a GPS track stored in a file.
 *
 * @author Ting Hang Nathan Lam
 */
public class TrackInfo {
  public static void main(String[] args) throws IOException {
    //checking if filename provided
    if (args.length != 1){
      System.err.println("No filename, correct usage: java TrackInfo <filename>");
    }
    String filename = args[0];
    Track walk = new Track(filename);
    System.out.printf("%d points in track\n",walk.size());
    System.out.printf("Lowest Point is %s\n", walk.lowestPoint().toString());
    System.out.printf("Highest point is %s\n", walk.highestPoint().toString());
    System.out.printf("Total distance = %.3f km\n", walk.totalDistance()/1000);
    System.out.printf("Average speed = %.3f m/s\n", walk.averageSpeed());
  }
}
