import java.io.IOException;

/**
 * Program to general a KML file from GPS track data stored in a file,
 * for the Advanced task of COMP1721 Coursework 1.
 *
 * @author Ting Hang Nathan Lam
 */
public class ConvertToKML {
  public static void main(String[] args) throws IOException {
    //error checking for correct number of arguments
    if (args.length !=2){
      System.err.println("No filename, correct usage: java ConvertToKML <filename of pointdata> <filename of newkml file");
    }
    try{
      Track PointsCSV = new Track(args[0]);
      PointsCSV.writeKML(args[1]);
    }
      catch(IOException IOError){
      System.err.println(IOError);
      System.exit(1);
    } catch (GPSException GPSError){
      System.err.println(GPSError);
      System.exit(1);
    }
  }
}
