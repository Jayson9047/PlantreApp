#include <pthread.h>
#include <SPI.h>
#include <Wire.h>
#include <Adafruit_GFX.h>
#include <Adafruit_SSD1306.h>
#include <WiFi.h>
#include <WiFiUdp.h>
#include <String.h>
#include "BluetoothSerial.h"
#include <BlynkSimpleEsp32.h>


#define WATERPUMP 5
#define SECONDWATERPUMP 19
#define SCREEN_WIDTH 128 // OLED display width, in pixels
#define SCREEN_HEIGHT 64 // OLED display height, in pixels
#define OLED_RESET -1 // Reset pin # (or -1 if sharing Arduino reset pin)


#if !defined(CONFIG_BT_ENABLED) || !defined(CONFIG_BLUEDROID_ENABLED)
#error Bluetooth is not enabled! Please run `make menuconfig` to and enable it
#endif

Adafruit_SSD1306 display(SCREEN_WIDTH, SCREEN_HEIGHT, &Wire, OLED_RESET);

BluetoothSerial SerialBT; 
//this is for first sensor
const int AirValue = 2580;   //you need to replace this value with Value_1
const int WaterValue = 1070;  //you need to replace this value with Value_2
int soilMoistureValue = 0;
int soilmoisturepercent=0;
const int SensorPin1 = 36;

//this is for second sensor
const int AirValue2 = 3380;   //you need to replace this value with Value_1
const int WaterValue2 = 1370;  //you need to replace this value with Value_2
const int SensorPin2 = 35;
int soilMoistureValue2 = 0;
int soilmoisturepercent2=0;
char auth[] = "ihbYhRnEL8H3lw84v8fyU-CPtH-BJs00";

bool wifiConnected = false;


String allInfo;
String strs[5];
int StringCount = 0;

String ssidStr;
String passwordStr;
String Ip;
String port;
const char* ssid;
const char* password;

const char * udpAddress;
WiFiUDP udp;
unsigned int udpPort;
bool pumpOn;
bool secondPumpOn;

char pktbuf[30];
String x_val;
int randNum = 0;

//--------------------------------------------------------------------------------------------------------------------------

 
void setup() {
 
   Serial.begin(115200);
   SerialBT.begin("ESP32test"); //Bluetooth device name
   Serial.println("The device started, now you can pair it with bluetooth!");
   //display.begin(SSD1306_SWITCHCAPVCC, 0x3C); //initialize with the I2C addr 0x3C (128x64)
   //display.clearDisplay();
   pinMode(WATERPUMP, OUTPUT);
   pinMode(SECONDWATERPUMP, OUTPUT);
   digitalWrite(WATERPUMP, HIGH); 
   digitalWrite(SECONDWATERPUMP, HIGH); 
   pumpOn = false;
   secondPumpOn = false;

   display.begin(SSD1306_SWITCHCAPVCC, 0x3C); //initialize with the I2C addr 0x3C (128x64)
   display.clearDisplay();
   wifiConnected = false;
   //Serial.println("Got it");
   pthread_t threads[3];
   int returnValue;
 
   for( int i = 0; i< 3; i++ ) {
      if(i == 0)
      {
        returnValue = pthread_create(&threads[i], NULL, printThreadId, (void *)i);
      }
      if(i == 1)
      {
        returnValue = pthread_create(&threads[i], NULL, printThreadId1, (void *)i);
      }
      if(i == 2)
      {
        returnValue = pthread_create(&threads[i], NULL, printThreadId2, (void *)i);
      }
      
 
      if (returnValue) {
         Serial.println("An error has occurred");
      }
   }
 
}


void connectToWifi()
{
  if (SerialBT.available()) 
      {
        allInfo =SerialBT.readString();
        //Serial.println(allInfo + " This was the string array\n\n"); 
        
    
        // Split the string into substrings
        while (allInfo.length() > 0)
        {
          int index = allInfo.indexOf('\n');
          if (index == -1) // No space found
          {
            strs[StringCount++] = allInfo;
            break;
          }
          else
          {
            strs[StringCount++] = allInfo.substring(0, index);
            allInfo = allInfo.substring(index+1);
          }
        }
      
        ssid = strs[0].c_str();
        password = strs[1].c_str();
        udpAddress = strs[2].c_str();
        port = strs[3];
        udpPort = strs[3].toInt();
        Serial.println(ssid);
        Serial.println(password);
        Serial.println(udpAddress);
        Serial.println(port);
        
    
        WiFi.begin(ssid,password);
        
        while(WiFi.waitForConnectResult() != WL_CONNECTED)  
        {
          Serial.println("Wrong Credentials given");
          SerialBT.print("1");
          delay(1000);
          ESP.restart();
        }
        wifiConnected = true;
        Serial.println(WiFi.localIP());
        Serial.println("Status: Connected");
        SerialBT.print("0");
        udp.begin(udpPort);
        Serial.println(udpPort);
        Blynk.begin(auth,ssid,password);
      }
      delay(20);
}
 
void loop() 
{
   if(wifiConnected == false)
   {
      connectToWifi();
   }
   else
   {
      Blynk.run();
   }
}

void readSoilMoistureValue()
{
    soilMoistureValue = analogRead(SensorPin1);  //put Sensor insert into soil
    soilmoisturepercent = map(soilMoistureValue, AirValue, WaterValue, 0, 100);
    Serial.println("pin1 percentage:");
    Serial.println(soilmoisturepercent);
    Serial.println("pin1 value:");
    Serial.println(soilMoistureValue);
    
    soilMoistureValue2 = analogRead(SensorPin2);  //put Sensor insert into soil
    soilmoisturepercent2 = map(soilMoistureValue2, AirValue2, WaterValue2, 0, 100);
    Serial.println("pin2 percentage:");
    Serial.println(soilmoisturepercent2);
    Serial.println("pin2 value:");
    Serial.println(soilMoistureValue2);
}

void sendUdpPacket()
{
    int rp = udp.parsePacket();
    if(!rp)
    {
        x_val = (String)soilmoisturepercent + "," + soilmoisturepercent2;
        Serial.print("udp_send: ");
        Serial.println(x_val);
        udp.beginPacket(udpAddress, udpPort);
        //udp.write(rx_val);
        udp.print(x_val);
        udp.endPacket();
        delay(1000);
    }
    else
    {
      udp.read(pktbuf,30);
      Serial.print("Packet from " + String(udpAddress)+": ");
      String packet = pktbuf;
      Serial.println(packet);
      if(strcmp(pktbuf,"5") == 0)
      {
        pumpOn = true;
      }
      else if(strcmp(pktbuf,"4") == 0)
      {
        secondPumpOn = true;
      }
      delay(1000);
    }
}

void *printThreadId(void *threadid) {
   
   while(true)
   {
     
     int i = 0;
     if(pumpOn == true)
     {
       runWaterPump(WATERPUMP);
     }
     else
     {
       delay(1000);
     }
   }
   return NULL;
}

void *printThreadId2(void *threadid) {
   
   while(true)
   {    
     int i = 0;
     if(secondPumpOn == true)
     {
       runWaterPump(SECONDWATERPUMP);
     }
     else
     {
       delay(1000);
     }
   }
   return NULL;
}

void *printThreadId1(void *threadid) {
   
   while(true)
   {
     int i = 0;
     
     if(wifiConnected == true)
     {
       readSoilMoistureValue();
       sendUdpPacket();
       showInOLED();
     }
     else
     {
       delay(1000);
     }
   }                     // wait for a second
   return NULL;
}

void runWaterPump(int waterPumpPin)
{
  digitalWrite(waterPumpPin, LOW);   // turn the LED on (HIGH is the voltage level)
  delay(10000);                       // wait for a second
  digitalWrite(waterPumpPin, HIGH);    // turn the LED off by making the voltage LOW
  
  pumpOn = false;
  secondPumpOn = false;
}

void showInOLED()
{
    if(soilmoisturepercent > 100)
    {
      Serial.println("100 %");
      
      display.setCursor(45,0);  //oled display
      display.setTextSize(2);
      display.setTextColor(WHITE);
      display.println("Soil");
      display.setCursor(20,15);  
      display.setTextSize(2);
      display.setTextColor(WHITE);
      display.println("Moisture");
      
      display.setCursor(30,40);  //oled display
      display.setTextSize(3);
      display.setTextColor(WHITE);
      display.println("100 %");
      display.display();
      
      delay(250);
      display.clearDisplay();
    }
    else if(soilmoisturepercent <0)
    {
      Serial.println("0 %");
      
      display.setCursor(45,0);  //oled display
      display.setTextSize(2);
      display.setTextColor(WHITE);
      display.println("Soil");
      display.setCursor(20,15);  
      display.setTextSize(2);
      display.setTextColor(WHITE);
      display.println("Moisture");
      
      display.setCursor(30,40);  //oled display
      display.setTextSize(3);
      display.setTextColor(WHITE);
      display.println("0 %");
      display.display();
     
      delay(250);
      display.clearDisplay();
    }
    else if(soilmoisturepercent >=0 && soilmoisturepercent <= 100)
    {
      Serial.print(soilmoisturepercent);
      Serial.println("%");
      
      display.setCursor(45,0);  //oled display
      display.setTextSize(2);
      display.setTextColor(WHITE);
      display.println("Soil");
      display.setCursor(20,15);  
      display.setTextSize(2);
      display.setTextColor(WHITE);
      display.println("Moisture");
      
      display.setCursor(30,40);  //oled display
      display.setTextSize(3);
      display.setTextColor(WHITE);
      display.println(soilmoisturepercent);
      display.setCursor(70,40);
      display.setTextSize(3);
      display.println(" %");
      display.display();
     
      delay(250);
      display.clearDisplay();
    }  
}

BLYNK_WRITE(V1)
{
  int pinValue = param.asInt();
  runWaterPump(WATERPUMP);
}

BLYNK_WRITE(V2)
{
  int pinValue = param.asInt();
  runWaterPump(SECONDWATERPUMP);
}
