package arduino_project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Ryan Rusli 2201832446
 */
public class DataManager {
    
    SQLHandler handler = new SQLHandler();
    
    public void retrieveTXTData() throws SQLException, FileNotFoundException{
        
        handler.connect();
        List<String> newData = new ArrayList<>();
        String[] parts;
             
        String year,month,day,hour;
        float temp,hum;
        try{
            BufferedReader reader;
            //File file = new File("/D:/DATA.txt");
            reader = new BufferedReader(new FileReader("E:/DATA.txt"));
            //Scanner scan = new Scanner(file);
            String line = reader.readLine();
            int currID = 0;
            while (line != null){
              
              System.out.println(currID);
              System.out.println(line);
              if (currID == 0){
                  
                  currID = Integer.parseInt(line);
              }
              else{
                  
                parts = line.split(",");
                year = parts[0];
                month = parts[1];
                day = parts[2];
                hour = parts[3];

                temp = Float.parseFloat(parts[4]);
                hum = Float.parseFloat(parts[5]);

                datum Newdata = new datum(currID, year,month,day,hour,temp,hum);
                currID++;
                handler.uploadData(Newdata);

                Newdata = null;
              }
              line = reader.readLine();
            }
            PrintWriter writer = new PrintWriter("/E:/DATA.txt");
            writer.print(currID);
            reader.close();
            writer.close();
        }
        catch (Exception e){
         
          System.out.println("Exception occurred trying to read /E:/DATA.txt \nError: " + e);

         }
        
    }
    
    public ArrayList<datum> convertToList(String year1, String month1, String day1, String hour1, 
                                          String year2, String month2, String day2, String hour2) 
                                          throws SQLException, ParseException{
        /*     
        String year1 = "2019";
        String year2 = "2019";
        
        String month1 = "6";
        String month2 = "6";
                
        String day1 = "24";
        String day2 = "24";
        
        String hour1 = "0";
        String hour2 = "8";
        */
        
        int newID;
        String newYear, newMonth, newDay, newHour; 
        float newTemp, newHum;
        datum newData;
        ResultSet rs = handler.getData(year1, month1, day1, hour1, year2, month2, day2, hour2);
        ArrayList<datum> values = new ArrayList<>();
        
        while(rs.next()){
            
            //System.out.println("a");
            System.out.println("NEW ID: " + rs.getInt(1));
            newID = rs.getInt(1);
            newYear = rs.getString(2);
            newMonth = rs.getString(3);
            newDay = rs.getString(4);
            newHour = rs.getString(5);
            newTemp = rs.getFloat(6);
            newHum = rs.getFloat(7);
            
            newData = new datum(newID, newYear, newMonth, newDay, newHour, newTemp, newHum);
            values.add(newData);
            newData = null;
            
        }
        
        return values;
        
    }
    
}
