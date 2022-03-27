package com.example.plantreapp.connection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.MenuItem;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.plantreapp.MainActivity;
import com.example.plantreapp.R;
import com.example.plantreapp.myPlants.MyPlantsActivity;
import com.example.plantreapp.search.SearchActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import android.os.Handler;
import android.widget.ProgressBar;

import cz.msebera.android.httpclient.Header;
import okhttp3.OkHttpClient;
import okhttp3.Request;


public class ConnBtnActivity extends AppCompatActivity {

    //Button ButtonConnectionPage;

    Button ButtonPump, ButtonPump2;
    ProgressBar circular_pro, circular_pro2;

    TextView status, status2, test, testbtn;
    private int progressStatus;
    private Handler handler = new Handler();
    private int soilMoisture = 0;
    private int soilMoisture2 = 0;

    private final static String TAG = MainActivity.class.getSimpleName();

    //TextView textViewPrompt;
    boolean pumpOn, secondPumpOn;
    static final int UdpServerPORT = 4445;
    UdpServerThread udpServerThread;
    boolean udpConnected = false;
    InetAddress savedAddress = null;
    int savedPort = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conn_btn);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setSelectedItemId(R.id.home_item);

        String firstWaterPumpUrl = "http://blynk-cloud.com/ihbYhRnEL8H3lw84v8fyU-CPtH-BJs00/update/V1?value=1";
        String secondWaterPumpUrl = "http://blynk-cloud.com/ihbYhRnEL8H3lw84v8fyU-CPtH-BJs00/update/V2?value=1";

        // nav click handler
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home_item:
                        //startActivity(new Intent(getApplicationContext(), ConnBtnActivity.class));
                        return true;
                    case R.id.my_plants_item:
                        startActivity(new Intent(getApplicationContext(), MyPlantsActivity.class));
                        return true;
                    case R.id.search_item:
                        startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                        return true;
                    case R.id.connection_item:
                        startActivity(new Intent(getApplicationContext(), ConnectionActivity.class));
                        return true;
                }
                return false;
            }
        });

        circular_pro = (ProgressBar)findViewById(R.id.progessbar_circular);
        circular_pro2 = (ProgressBar)findViewById(R.id.progessbar_circular2);

        status= (TextView)findViewById(R.id.text_status);
        status2= (TextView)findViewById(R.id.text_status2);
        test = (TextView)findViewById(R.id.testID);
        testbtn = (TextView)findViewById(R.id.testID2);
        //textViewPrompt = (TextView)findViewById(R.id.prompt);
        pumpOn = false;

        ButtonPump = (Button) findViewById(R.id.btnSendPump);
        ButtonPump2 = (Button) findViewById(R.id.btnSendPump2);

        ButtonPump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(udpConnected == true)
                {
                    pumpOn = true;
                    testbtn.setText("Watering via UDP");
                }
                else
                {
                    testbtn.setText("Watering via Cloud");
                    turnOnWaterPumpViaCloud(firstWaterPumpUrl);
                    //status.setText("UDP not Connected");
                    //httpClient.newCall(request1);
                }
            }
        });

        ButtonPump2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(udpConnected == true)
                {
                    secondPumpOn = true;
                    testbtn.setText("Watering via UDP");
                }
                else
                {
                    //status2.setText("UDP not Connected");
                    //httpClient.newCall(request2);
                    testbtn.setText("Watering via Cloud");
                    turnOnWaterPumpViaCloud(secondWaterPumpUrl);
                }
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                circular_pro.setProgress(soilMoisture);
                                if(soilMoisture <0 )
                                {
                                    soilMoisture = 0;
                                }
                                if(soilMoisture > 100)
                                {
                                    soilMoisture = 100;
                                }
                                status.setText(soilMoisture+"%");

                                circular_pro2.setProgress(soilMoisture2);
                                if(soilMoisture2 <0 )
                                {
                                    soilMoisture2 = 0;
                                }
                                if(soilMoisture2 > 100)
                                {
                                    soilMoisture2 = 100;
                                }
                                status2.setText(soilMoisture2+"%");

                            }
                            catch (Exception e)
                            {
                                //UDP not receiving yet
                                status.setText("Not receiving");
                                status2.setText("Not receiving");

                            }

                        }
                    });
                    try {
                        Thread.sleep(200);

                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }

            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {

                    if(savedAddress != null)
                    {
                        try {
                            if(savedAddress.isReachable(savedPort))
                            {
                                test.setText("Got it");
                                udpConnected = true;
                            }
                            else
                            {
                                test.setText("Went out of Home Network");
                                udpConnected = false;
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            udpConnected = false;
                        }
                    }
                    else
                    {
                        test.setText("Not in Home Network");
                        udpConnected = false;
                    }
                }
            }
        }).start();
    }
    @Override
    protected void onStart() {
        udpServerThread = new UdpServerThread(UdpServerPORT);
        udpServerThread.start();
        super.onStart();
    }
    @Override
    protected void onStop() {
        if(udpServerThread != null){
            udpServerThread.setRunning(false);
            udpServerThread = null;
        }

        super.onStop();
    }

    private void turnOnWaterPumpViaCloud(String url)
    {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                //
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                //
            }
        });
    }


    private class UdpServerThread extends Thread{

        int serverPort;
        DatagramSocket socket;

        boolean running;

        public UdpServerThread(int serverPort) {
            super();
            this.serverPort = serverPort;
        }

        public void setRunning(boolean running){
            this.running = running;
        }

        @Override
        public void run() {

            running = true;

            try {
                socket = new DatagramSocket(serverPort);
                Log.e(TAG, "UDP Server is running");

                while(running){
                    byte[] buf = new byte[256];

/*                    if(savedAddress != null)
                    {
                        if(savedAddress.isReachable(savedPort))
                        {
                            test.setText("Got it");
                        }
                        else
                        {
                            test.setText("Went out of Home Network");
                        }
                    }
                    else
                    {
                        test.setText("Not in Home Network");
                    }*/

                    // receive request
                    DatagramPacket packet = new DatagramPacket(buf, buf.length);
                    socket.receive(packet);     //this code block the program flow

                    String msg = new String(packet.getData(), packet.getOffset(), packet.getLength());
                    String[] allValues = msg.split(",");
                    int l = Integer.valueOf(allValues[0]);
                    int m = Integer.valueOf(allValues[1]);



                    circular_pro.setProgress(soilMoisture);

                    if(soilMoisture <0 )
                    {
                        soilMoisture = 0;
                    }
                    else if(soilMoisture > 100)
                    {
                        soilMoisture = 100;
                    }
                    else
                    {
                        soilMoisture = l;
                    }

                    circular_pro2.setProgress(soilMoisture2);

                    if(soilMoisture2 <0 )
                    {
                        soilMoisture2 = 0;
                    }
                    else if(soilMoisture2 > 100)
                    {
                        soilMoisture2 = 100;
                    }
                    else
                    {
                        soilMoisture2 = m;
                    }

                    //int li = ByteBuffer.wrap(packet.getData()).getInt();

                    //String m = String.valueOf(li);
                    // send the response to the client at "address" and "port"
                    InetAddress address = packet.getAddress();
                    int port = packet.getPort();

                    //updatePrompt("Request from: " + address + ":" + port + "\n");
                    //updatePrompt("Message: "+ soilMoisture +"\n");

                    savedAddress = address;
                    savedPort = port;


                    if(pumpOn == true)
                    {
                        String dString = "5";
                        buf = dString.getBytes();
                        packet = new DatagramPacket(buf, buf.length, address, port);
                        socket.send(packet);
                        pumpOn = false;

                    }

                    if(secondPumpOn == true)
                    {
                        String dString = "4";
                        buf = dString.getBytes();
                        packet = new DatagramPacket(buf, buf.length, address, port);
                        socket.send(packet);
                        secondPumpOn = false;
                    }

                }

                Log.e(TAG, "UDP Server ended");

            } catch (SocketException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(socket != null){
                    socket.close();
                    Log.e(TAG, "socket.close()");
                }
            }
        }
    }
}