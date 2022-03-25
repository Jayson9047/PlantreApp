package com.example.plantreapp.connection;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plantreapp.R;
import com.example.plantreapp.myPlants.MyPlantsActivity;
import com.example.plantreapp.search.SearchActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Random;


public class ConnBtnActivity extends AppCompatActivity implements WaterInfoAdapter.WaterInfoInterface {

    //Button ButtonConnectionPage;

    /*Button ButtonPump, ButtonPump2;
    ProgressBar circular_pro, circular_pro2;

    TextView status, status2;
    private int progressStatus;
    private Handler handler = new Handler();
    private int soilMoisture = 0;
    private int soilMoisture2 = 0;

    private final static String TAG = MainActivity.class.getSimpleName();

    //TextView textViewPrompt;
    boolean pumpOn, secondPumpOn;
    static final int UdpServerPORT = 4445;
    UdpServerThread udpServerThread;
    boolean udpConnected = false;*/



    //Declare Recyclerview , Adapter and ArrayList
    private RecyclerView recyclerView;
    private WaterInfoAdapter adapter;
    private ArrayList<WaterInfo> waterInfoArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conn_btn);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setSelectedItemId(R.id.home_item);


        // init recycler view
        initView();



        /*OkHttpClient httpClient = new OkHttpClient();
        String firstWaterPumpUrl = "http://blynk-cloud.com/ihbYhRnEL8H3lw84v8fyU-CPtH-BJs00/update/V1?value=1";
        String secondWaterPumpUrl = "http://blynk-cloud.com/ihbYhRnEL8H3lw84v8fyU-CPtH-BJs00/update/V2?value=1";
        Request request1 = new Request.Builder().url(firstWaterPumpUrl).build();
        Request request2 = new Request.Builder().url(secondWaterPumpUrl).build();*/

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

        /*circular_pro = (ProgressBar)findViewById(R.id.progessbar_circular);
        circular_pro2 = (ProgressBar)findViewById(R.id.progessbar_circular2);

        status= (TextView)findViewById(R.id.text_status);
        status2= (TextView)findViewById(R.id.text_status2);
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
                }
                else
                {
                    httpClient.newCall(request1);
                }
            }
        });

        ButtonPump2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(udpConnected == true)
                {
                    secondPumpOn = true;
                }
                else
                {
                    httpClient.newCall(request2);
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
                                udpConnected = true;
                            }
                            catch (Exception e)
                            {
                                //UDP not receiving yet
                                status.setText("Not receiving");
                                status2.setText("Not receiving");
                                udpConnected = false;
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
        }).start();*/
/*
        clickme_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while(true){
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
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
                            });
                            try {
                                Thread.sleep(200);

                            }catch (InterruptedException e){
                                e.printStackTrace();
                            }
                        }

                    }
                }).start();
            }
        });
*/

    }

    /*@Override
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
    }*/

    /*private void updatePrompt(final String prompt){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textViewPrompt.append(prompt);
            }
        });
    }*/

    /*private class UdpServerThread extends Thread{

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

                    if(pumpOn == true)
                    {
                        if(udpConnected)
                        {
                            String dString = "5";
                            buf = dString.getBytes();
                            packet = new DatagramPacket(buf, buf.length, address, port);
                            socket.send(packet);
                            pumpOn = false;
                        }
                        else
                        {

                        }

                    }

                    if(secondPumpOn == true)
                    {
                        if(udpConnected) {
                            String dString = "4";
                            buf = dString.getBytes();
                            packet = new DatagramPacket(buf, buf.length, address, port);
                            socket.send(packet);
                            secondPumpOn = false;
                        }
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
    }*/

    private void initView() {
        // Initialize RecyclerView and set Adapter
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        waterInfoArrayList = new ArrayList<>();
        adapter = new WaterInfoAdapter(this, waterInfoArrayList, this);
        recyclerView.setAdapter(adapter);
        initList(2);
    }

    private void initList(int numItems) {
        for (int i = 0; i < numItems; i++) {
            //data to be shown in list
            waterInfoArrayList.add(new WaterInfo(0, "Water Plant", "Percentage"));
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBtnClick(int position, WaterInfo waterInfo)
    {
        Random r = new Random();
        int low = 10;
        int high = 100;
        int result = r.nextInt(high-low) + low;

        Toast.makeText(getApplicationContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();
        waterInfo.setPercentage(result);
        adapter.notifyDataSetChanged();
    }
}