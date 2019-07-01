
package arduino_project;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.text.ParseException;

/**
 *
 * @author Ryan Rusli 2201832446
 */
       
public class driver {

    public static void main(String[] args) throws SQLException, FileNotFoundException, ParseException {
              
        User_Interface ui = new User_Interface();      
        ui.setVisible(true);             
    }
}
