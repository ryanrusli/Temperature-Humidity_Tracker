#include "RTClib.h"
#include "DHT.h"
#include <SD.h>
#include <SPI.h>
#include <TimeLib.h>

#define DHT11_PIN 7
#define DHTTYPE DHT11
#define CS_PIN 10

#define TIME_HEADER 'T'
#define TIME_REQUEST 7

//real time clock module
RTC_DS1307 rtc;
//temperature/humidity sensor
DHT dht(DHT11_PIN, DHTTYPE);
File file;

//initializes arduino and all modules
void setup() {
  Serial.begin(9600);
  //lcd.begin(16,2);
  rtc.begin();
  dht.begin();
  
  initializeSD();
  //opens text file
  file = SD.open("data.TXT",FILE_WRITE);
  file.close();

}

//initialize variables
int oldHour = -1;
float totalTemp = 0;
float totalHum = 0;
float avgTemp,avgHum;
int counter = 0;

void loop() {

  //5 minute delay
  delay(300000);

  //reads temperature readings
  float temperature = dht.readTemperature();
  float humidity = dht.readHumidity();

//  Serial.print("\nTemperature : ");
//  Serial.print(temperature);
//  Serial.print("\nHumidity : "); 
//  Serial.println(humidity);

  //add total readings
  totalTemp += temperature;
  totalHum += humidity;
  counter += 1;

  //retrieve current time
  DateTime now = rtc.now();
  String Year = String(now.year());
  String Month = String(now.month());
  String Day = String(now.day());
  String Hour = String(now.hour());
  
//  Serial.print(Year);
//  Serial.print('/');
//  Serial.print(Month);
//  Serial.print('/');
//  Serial.print(Day);
//  Serial.print(" ");
//  Serial.print(Hour);
//  Serial.print(':');
//  Serial.print(now.minute(), DEC);
//  Serial.print(':');
//  Serial.println(now.second(), DEC);


  //if it is a new hour
  if(now.hour() != oldHour){

    // open text file
    file = SD.open("data.txt",FILE_WRITE);
    //open sucess
    if (file){
       avgTemp = totalTemp/counter;
       avgHum = totalHum/counter;
       String temp = String(avgTemp);
       String hum  = String(avgHum);
       Serial.print("Data Entered!");
       String datum = Year + ',' + Month + ',' + Day + ',' + Hour + ',' + temp + ',' + hum;
       file.println(datum);
       file.close();
    }
    //open fails
    else{

      Serial.print("Error opening file!");
    }
    //char buff[50];
    //datum.toCharArray(buff,50);
   
    oldHour = now.hour();

    //resets total readings
    avgTemp = 0;
    avgHum = 0;
    totalTemp = 0;
    totalHum = 0;
    counter = 0;
  }

  
}

//initalizes SD card
void initializeSD()
{
  Serial.println("Initializing SD card...");
  pinMode(CS_PIN, OUTPUT);

  if (SD.begin())
  {
    Serial.println("SD card is ready to use.");
  } else
  {
    Serial.println("SD card initialization failed");
    return;
  }
}
