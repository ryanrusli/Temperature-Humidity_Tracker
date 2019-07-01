    
package arduino_project;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Ryan Rusli 2201832446
 */
public class SQLHandler {

    public SQLHandler() {
    }
    
    public Connection connect(){        
        
        Connection conn = null;
        
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/weather_data","root","");
            return conn;
        }
        catch (SQLException ex){    
            System.out.println("CONNECTION TO DATABASE ERROR!");
            return conn;
        }
    }
    
    public ResultSet getData(String year1, String month1, String day1, String hour1,
                             String year2, String month2, String day2, String hour2) 
                             throws SQLException, ParseException{
        try{
            Connection conn = this.connect();

            Statement statement = conn.createStatement();
            String command = "SELECT * FROM data WHERE (Year = '"+ year1 + "' AND "
                    + "Month = '" + month1 + "' AND Day = '" + day1 + "' AND Hour ='" + hour1 + "');"; 
            ResultSet rs = statement.executeQuery(command);
            rs.next();
            int theID = rs.getInt(1);

            SimpleDateFormat dateformat = new SimpleDateFormat("dd MM yyyy HH");

            String inp1 = day1 + " " + month1 + " " + year1 + " " + hour1;
            String inp2 = day2 + " " + month2 + " " + year2 + " " + hour2;

            java.util.Date date1 = dateformat.parse(inp1);
            java.util.Date date2 = dateformat.parse(inp2);

            System.out.println(inp2); 
            long diff = date2.getTime() - date1.getTime();
            System.out.println(diff);
            int hours = (int) TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS);
            int numOfRows = hours+1;  

            command = "SELECT * FROM data WHERE ID >= '" + theID + "' LIMIT " + numOfRows + ";";

            statement  = conn.createStatement();
            rs = statement.executeQuery(command);

            return rs;  
        }
        catch (Exception e){
            
            System.out.println("ERROR: " + e);
        }
        return null;
    }
    
    public void uploadData(datum values) throws SQLException{
        
        String command = "INSERT INTO data(ID, Year, Month, Day, Hour, Temperature, Humidity) VALUES(";
        command += values.getID() + ", '" + values.getYear() + "', '" + values.getMonth() + "', '" + values.getDay() + "', '" + 
                   values.getHour() + "', " + values.getTemperature() + ", " + values.getHumidity() +");";
        
        Connection conn = this.connect();
        Statement statement = conn.createStatement();
        statement.executeUpdate(command);
    } 
    
    public String getStartData() throws SQLException{
        
        String startDate = "";
        
        String command = "SELECT * FROM data WHERE ID = 1;";
        Connection conn = this.connect();
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(command);
        
        rs.next();
        
        startDate += rs.getString(2);
        startDate += " ";
        startDate += rs.getString(3);
        startDate += " ";
        startDate += rs.getString(4);
        startDate += " ";
        startDate += rs.getString(5);

        return startDate;
    }
    
    public String getEndData() throws SQLException{
        
        String command = "SELECT * FROM data ORDER BY ID DESC LIMIT 1;";
        String endDate = "";
        
        Connection conn = this.connect();
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(command);
        
        rs.next();
        
        endDate += rs.getString(2);
        endDate += " ";
        endDate += rs.getString(3);
        endDate += " ";
        endDate += rs.getString(4);
        endDate += " ";
        endDate += rs.getString(5);

        return endDate;
    }
}
    
