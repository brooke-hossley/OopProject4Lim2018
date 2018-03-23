import java.util.*;
import java.io.*;
/**
 * Write a description of class LineReader here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class LineReader
{
    public static void main(String args[]) throws FileNotFoundException{
        Scanner sc = new Scanner(System.in);
        System.out.print("File Name: ");
        String file = sc.nextLine();
        File x = new File(file);
        Scanner scan1 = new Scanner(x);
        int lines = 0;
        while(scan1.hasNext()){
            String line = scan1.nextLine();
            lines++;
            if(line.length() > 80){
                System.out.println("Line: " + lines);
                System.out.println("Length: " + line.length());
            }
        }
        System.out.println(lines);
    }
}
