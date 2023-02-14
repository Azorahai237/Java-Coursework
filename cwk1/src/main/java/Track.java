/**
 * Represents a point in space and time, recorded by a GPS sensor.
 *
 * @author Ting Hang Nathan Lam
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

public class Track {
  private ArrayList<Point> PointList = null;
  public Track(){
    PointList = new ArrayList<Point>();
    //defining list for the points
  }

  
  public Track(String filename) throws IOException{
    //initializing list
    PointList = new ArrayList<Point>();
    readFile(filename);
  }
  public void readFile(String filename) throws IOException{
    //creating file object and error checking for whether file exists
    File FileData = new File(filename);
    if (FileData.exists() != true){
      throw new FileNotFoundException("File does not exist!");
      //file not found exit program
    }
    //making scanner for the file
    Scanner Reader = new Scanner(FileData);
    //skipping the title columns
    Reader.skip("Time,Longitude,Latitude,Elevation");
    //looping through the lines
    System.out.print(Reader.nextLine());
    while (Reader.hasNextLine()){
      //adding each line to array then parsing it with a line scanner for values
      String LineValue = Reader.nextLine();
      //parsing through the line with a comma delimiter
      StringTokenizer values = new StringTokenizer(LineValue, ",");

      //checking if data has correct number of columns
      if (values.countTokens() != 4){
        throw new GPSException("Incorrect amount of arguments for a point");
      }

      // saving values to create point
      ZonedDateTime Time = ZonedDateTime.parse(values.nextToken());
      double Longitude = Double.parseDouble(values.nextToken());
      double Latitude = Double.parseDouble(values.nextToken());
      double Elevation = Double.parseDouble(values.nextToken());

      //creating new point from values and adding to list
      Point NewPoint = new Point(Time, Longitude, Latitude, Elevation);
      System.out.printf("Point created");
      PointList.add(NewPoint);
    }
    Reader.close();

  }
  //adding point to a list
  public void add(Point point){
    PointList.add(point);
  }

  public Point get(int index){
    // exception if index out of range
    if (index < 0 || index > PointList.size() - 1){
      throw new GPSException("Index out of range!");
    }
    
    return PointList.get(index);
  }
  //returns size of list
  public int size(){
    return PointList.size();
  }
  //method to return point with lowest elevation
  public Point lowestPoint(){
    //track length checking
    if (size() < 2){
      throw new GPSException("Not enough points in track");
    }
    //setting variable for lowest element as first element of array
    Point lowest = PointList.get(0);
    //looping to compare against first element
    for (Point point : PointList) {
      //if new element lower, set as new lowest variabke
      if (lowest.getElevation() > point.getElevation()){
        lowest = point;
      }
    }
    return lowest;
  }
 
  public Point highestPoint(){
    //track length checking
    if (size() < 2){
      throw new GPSException("Not enough points in track");
    }
    //setting first variable 
    Point highest = PointList.get(0);
    //looping through list and comparing against first element
    for (Point point : PointList){
      //if current highest element smaller than index element, set new highest point
      if (highest.getElevation() < point.getElevation()){
        highest = point;
      }
    }
    return highest;
  }
  
  public double totalDistance(){
    //track checking 
    if (size() < 2){
      throw new GPSException("Not enough points in track");
    }
    double total = 0;
    //lopping through the list of points until the second last element
    for (int index = 0; index < PointList.size() - 1; index++){

      //creating the 2 points to calculate their distance
      Point point1 = PointList.get(index);
      Point point2 = PointList.get(index + 1);
      double PointsDistance = Point.greatCircleDistance(point1, point2);
      total += PointsDistance;
    }
    return total;
  }
  
  public double averageSpeed(){
    //track checking
    if (size() < 2){
      throw new GPSException("Not enough points in track");
    }
    //finding the times of the first and last points
    ZonedDateTime FirstPointTime = PointList.get(0).getTime();
    ZonedDateTime LastPointTime = PointList.get(PointList.size() - 1).getTime();
    //calculating seconds between 2 timestamps
    double SecondsBetween = ChronoUnit.SECONDS.between(FirstPointTime, LastPointTime);
    //retrieving total distance
    double distance = totalDistance();
    //calculating average speed
    double AverageSpeed = distance / SecondsBetween;
    return AverageSpeed;
  }
}
