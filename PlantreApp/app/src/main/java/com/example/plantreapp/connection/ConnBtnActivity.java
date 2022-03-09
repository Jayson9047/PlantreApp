package com.example.plantreapp.connection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.util.Log;
import android.widget.TextView;

import com.example.plantreapp.MainActivity;
import com.example.plantreapp.R;
import com.example.plantreapp.myPlants.MyPlantsActivity;
import com.example.plantreapp.search.SearchActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class ConnBtnActivity extends AppCompatActivity {

    //Button ButtonConnectionPage;

    Button ButtonPump;

    private final static String TAG = MainActivity.class.getSimpleName();

    TextView textViewPrompt;
    boolean pumpOn;
    static final int UdpServerPORT = 4445;
    UdpServerThread udpServerThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conn_btn);
        textViewPrompt = (TextView)findViewById(R.id.prompt);
        pumpOn = false;

        ButtonPump = (Button) findViewById(R.id.btnSendPump);

        ButtonPump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pumpOn = true;
            }
        });

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

    private void updatePrompt(final String prompt){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textViewPrompt.append(prompt);
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

                    // receive request
                    DatagramPacket packet = new DatagramPacket(buf, buf.length);
                    socket.receive(packet);     //this code block the program flow

                    String msg = new String(packet.getData(), packet.getOffset(), packet.getLength());
                    int l = Integer.valueOf(msg);

                    //int li = ByteBuffer.wrap(packet.getData()).getInt();

                    //String m = String.valueOf(li);
                    // send the response to the client at "address" and "port"
                    InetAddress address = packet.getAddress();
                    int port = packet.getPort();

                    //updatePrompt("Request from: " + address + ":" + port + "\n");
                    updatePrompt("Message: "+ l +"\n");

                    if(pumpOn == true)
                    {
                        String dString = "5";
                        buf = dString.getBytes();
                        packet = new DatagramPacket(buf, buf.length, address, port);
                        socket.send(packet);
                        pumpOn = false;
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