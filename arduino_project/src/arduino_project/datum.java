/*
 * 
 * 
 * 
 */
package arduino_project;

/**
 *
 * @author Ryan Rusli 2201832446
 */
public class datum {
    
    private int ID;
    private String year;
    private String month;
    private String day;

    private String hour;
    private float temperature;
    private float humidity;

    public datum() {
    }
    
    public datum(int ID, String year, String month, String day, String hour, float temperature, float humidity) {
        
        this.ID = ID;
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.temperature = temperature;
        this.humidity = humidity;
    }
    
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }
    
    
}
