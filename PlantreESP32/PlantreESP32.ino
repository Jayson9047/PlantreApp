#include <SPI.h>
#include <Wire.h>
#include <Adafruit_GFX.h>
#include <Adafruit_SSD1306.h>
#include <WiFi.h>
#include <WiFiUdp.h>
#include <String.h>
#include "BluetoothSerial.h"


#define WATERPUMP 5
#define SCREEN_WIDTH 128 // OLED display width, in pixels
#define SCREEN_HEIGHT 64 // OLED display height, in pixels
#define OLED_RESET -1 // Reset pin # (or -1 if sharing Arduino reset pin)


#if !defined(CONFIG_BT_ENABLED) || !defined(CONFIG_BLUEDROID_ENABLED)
#error Bluetooth is not enabled! Please run `make menuconfig` to and enable it
#endif

Adafruit_SSD1306 display(SCREEN_WIDTH, SCREEN_HEIGHT, &Wire, OLED_RESET);

BluetoothSerial SerialBT; 
const int AirValue = 2580;   //you need to replace this value with Value_1
const int WaterValue = 1070;  //you need to replace this value with Value_2
const int SensorPin = 36;
int soilMoistureValue = 0;
int soilmoisturepercent=0;
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

char pktbuf[10];
String x_val;
int randNum = 0;
 
void setup() {
  Serial.begin(115200); // open serial port, set the baud rate to 9600 bps
  
  SerialBT.begin("ESP32test"); //Bluetooth device name
  Serial.println("The device started, now you can pair it with bluetooth!");
  display.begin(SSD1306_SWITCHCAPVCC, 0x3C); //initialize with the I2C addr 0x3C (128x64)
  display.clearDisplay();
  pinMode(WATERPUMP, OUTPUT);
  digitalWrite(WATERPUMP, HIGH); 
  pumpOn = false;
  /*WiFi.begin(ssid,password);
  while(WiFi.waitForConnectResult() != WL_CONNECTED)  {
    Serial.println("Connection Failed! Rebooting...");
    delay(5000);
    ESP.restart();
  }
  Serial.println(WiFi.localIP());
  Serial.println("Status: Connected");
  udp.begin(udpPort);
  Serial.println(udpPort);*/
}
 
 
void loop() 
{
  if(wifiConnected == false)
  {
      connectToWifi();
  }
  else
  {      
      readSoilMoistureValue();
      sendUdpPacket();
      showInOLED();
      if(pumpOn == true)
      {
        runWaterPump();
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
      }
      delay(20);
}

void readSoilMoistureValue()
{
    soilMoistureValue = analogRead(SensorPin);  //put Sensor insert into soil
    Serial.println(soilMoistureValue);
    soilmoisturepercent = map(soilMoistureValue, AirValue, WaterValue, 0, 100);
}

void sendUdpPacket()
{
    int rp = udp.parsePacket();
    if(!rp)
    {
        x_val = String(soilmoisturepercent);
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
      udp.read(pktbuf,1);
      Serial.print("Packet from " + String(udpAddress)+": ");
      Serial.println(pktbuf);
      if(pktbuf[0] == '5')
      {
        pumpOn = true;
      }
      delay(1000);
    }
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

void runWaterPump()
{
  digitalWrite(WATERPUMP, LOW);   // turn the LED on (HIGH is the voltage level)
  delay(10000);                       // wait for a second
  digitalWrite(WATERPUMP, HIGH);    // turn the LED off by making the voltage LOW
  delay(2000);                       // wait for a second
  pumpOn = false;
}
