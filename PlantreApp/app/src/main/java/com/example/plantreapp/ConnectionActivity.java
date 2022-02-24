package com.example.plantreapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.ParcelUuid;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.bluetooth.BluetoothDevice;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.util.UUID;
import androidx.core.app.ActivityCompat;


public class ConnectionActivity extends AppCompatActivity {

    private Button ButtonActive, ButtonInactive, ButtonListPairedDevices, ButtonConnect, BtnSend;
    private TextView textView;
    private TextView connectStat;
    private TextView msg_box;
    BluetoothAdapter bluetoothAdapter;
    private ListView emp;
    private String NAME;
    private String[] permissions = {"android.permission.BLUETOOTH_CONNECT"};
    private boolean permissionGranted = false;
    private EditText textMessage;

    String error = "";
    String address = "";
    BluetoothDevice[] BTArray;

    static final int STATE_LISTENING = 1;
    static final int STATE_CONNECTING = 2;
    static final int STATE_CONNECTED = 3;
    static final int STATE_CONNECTION_FAILED = 4;
    static final int STATE_MESSAGE_RECEIVED = 5;
    static final int STATE_CONNECTION_TEST = 6;

    SendReceive sendReceive;
    //public static final String TAG = "BTTag";

    private static final String APP_NAME = "Plantre";
    private UUID MY_UUID = UUID.fromString("4fafc201-1fb5-459e-8fcc-c5c9c331914b");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);

        ButtonActive = (Button) findViewById(R.id.onB);
        ButtonInactive = (Button) findViewById(R.id.offB);
        ButtonListPairedDevices = (Button) findViewById(R.id.listDevices);
        ButtonConnect = (Button) findViewById(R.id.connectButton);
        textView = (TextView) findViewById(R.id.stat);
        connectStat = (TextView) findViewById(R.id.connectionStatus);
        BtnSend = (Button)findViewById(R.id.sendButton);
        ButtonInactive.setEnabled(false);
        ButtonListPairedDevices.setEnabled(false);
        emp = (ListView) findViewById(R.id.pairedDeviceList);
        msg_box = (TextView)findViewById(R.id.messageBox);
        textMessage = (EditText)findViewById(R.id.typeMessage);
        //permissions[0] = "android.permission.BLUETOOTH_CONNECT";

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (ActivityCompat.checkSelfPermission(ConnectionActivity.this, permissions[0]) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions

            permissionRequestCheck();

        }

        ButtonActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!bluetoothAdapter.isEnabled()) {
                    checkPButtonActive();

                } else {
                    textView.setText("Active");
                }

            }
        });

        ButtonInactive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPButtonInactive();
                Toast.makeText(getApplicationContext(), "got Button Inactive", Toast.LENGTH_SHORT).show();
            }
        });

        ButtonListPairedDevices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list_paired_Devices();
            }
        });

        ButtonConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //ConnectDevice();
                ServerClass serverClass = new ServerClass();
                serverClass.start();
                Toast.makeText(getApplicationContext(), "got Connect", Toast.LENGTH_SHORT).show();
            }
        });

        emp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ClientClass clientClass = new ClientClass(BTArray[i]);
                clientClass.start();
                if (ActivityCompat.checkSelfPermission(ConnectionActivity.this, permissions[0]) != PackageManager.PERMISSION_GRANTED) { }

                address = BTArray[i].getAddress().toString();
                ParcelUuid[] c =  BTArray[i].getUuids();
                MY_UUID = UUID.fromString(c[0].toString());
                connectStat.setText(BTArray[i].getName() + " "+ BTArray[i].getAddress()+"\n"+ MY_UUID.toString() +" Connecting as Client");
            }
        });

        BtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String string = String.valueOf(textMessage.getText());
                sendReceive.write(string.getBytes());
            }
        });

    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            switch (msg.what) {
                case STATE_LISTENING:
                    connectStat.setText("Listening");
                    break;
                case STATE_CONNECTING:
                    connectStat.setText("Connecting");
                    break;
                case STATE_CONNECTED:
                    connectStat.setText("Connected");
                    break;
                case STATE_CONNECTION_FAILED:
                    connectStat.setText("Connection Failed");
                    break;
                case STATE_MESSAGE_RECEIVED:
                    byte[] readBuff = (byte[])msg.obj;
                    String tempMsg = new String(readBuff,0,msg.arg1);
                    msg_box.setText(tempMsg);
                    break;
                case STATE_CONNECTION_TEST:
                    connectStat.setText(error);
                    break;
            }
            return true;
        }
    });

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 80) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Toast.makeText(this,"Download Code", Toast.LENGTH_SHORT).show();

                permissionGranted = true;
            } else {
                permissionGranted = false;
                Toast.makeText(this, "Download Cancel", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void permissionRequestCheck() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, 80);
        }
    }

    private void toastTest(String text) {
        Context context = getApplicationContext();

        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    private void list_paired_Devices() {

        if (ActivityCompat.checkSelfPermission(ConnectionActivity.this, permissions[0]) == PackageManager.PERMISSION_GRANTED) {
            try {
                Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

                ArrayList<String> devices = new ArrayList<>();

                int index = 0;
                BTArray = new BluetoothDevice[pairedDevices.size()];

                for (BluetoothDevice bt : pairedDevices) {
                    BTArray[index] = bt;
                    devices.add(bt.getName() + "\n" + bt.getAddress());
                    index++;
                }


                ArrayAdapter arrayAdapter = new ArrayAdapter(ConnectionActivity.this, android.R.layout.simple_list_item_1, devices);
                emp.setAdapter(arrayAdapter);


            } catch (Exception e) {
                connectStat.setText(e.toString());
            }
            Toast.makeText(getApplicationContext(), "got paired Devices", Toast.LENGTH_SHORT).show();
        }

    }

    private void checkPButtonActive() {
        Intent ak = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        if (ActivityCompat.checkSelfPermission(ConnectionActivity.this, permissions[0]) == PackageManager.PERMISSION_GRANTED) {
            startActivityForResult(ak, 0);
            //Toast.makeText(ConnectionActivity.this,"got Button Permission", Toast.LENGTH_SHORT).show();

            textView.setText("Active");

            if (!bluetoothAdapter.isEnabled()) {
                ButtonInactive.setEnabled(true);
                ButtonListPairedDevices.setEnabled(true);
                Toast.makeText(ConnectionActivity.this, "Bluetooth not Enabled", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void checkPButtonInactive() {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), permissions[0]) != PackageManager.PERMISSION_GRANTED) {
            permissionRequestCheck();
        }
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), permissions[0]) == PackageManager.PERMISSION_GRANTED) {
            bluetoothAdapter.disable();
            textView.setText("Inactive");
        }
    }

    private class ServerClass extends Thread {
        private BluetoothServerSocket serverSocket;

        public ServerClass() {
            try {
                if (ActivityCompat.checkSelfPermission(ConnectionActivity.this, permissions[0]) != PackageManager.PERMISSION_GRANTED) {
                }
                serverSocket = bluetoothAdapter.listenUsingRfcommWithServiceRecord(APP_NAME, MY_UUID);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            BluetoothSocket socket = null;
            while (socket == null) {
                try {
                    Message message = Message.obtain();
                    message.what = STATE_CONNECTING;
                    handler.sendMessage(message);
                    socket = serverSocket.accept();
                } catch (IOException e) {
                    e.printStackTrace();
                    Message message = Message.obtain();
                    message.what = STATE_CONNECTION_FAILED;
                    handler.sendMessage(message);
                }

                if (socket != null) {
                    Message message = Message.obtain();
                    message.what = STATE_CONNECTED;
                    handler.sendMessage(message);
                    sendReceive = new SendReceive(socket);
                    sendReceive.start();
                    //write code for send/ Receive
                    break;
                }
            }
        }

    }

    private class ClientClass extends Thread {
        private BluetoothDevice device;
        private BluetoothSocket socket;

        public ClientClass(BluetoothDevice device1) {
            device = device1;
            try {
                if (ActivityCompat.checkSelfPermission(ConnectionActivity.this, permissions[0]) != PackageManager.PERMISSION_GRANTED) { }
                socket = device.createInsecureRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException e) {
                e.printStackTrace();

            }
        }

        public void run() {
            try {
                if (ActivityCompat.checkSelfPermission(ConnectionActivity.this, permissions[0]) != PackageManager.PERMISSION_GRANTED) { }
                socket.connect();
                if(socket.isConnected())
                {
                    Message message = Message.obtain();
                    message.what = STATE_CONNECTED;
                    handler.sendMessage(message);
                    sendReceive=new SendReceive(socket);
                    sendReceive.start();
                }

            } catch (IOException e) {
                e.printStackTrace();
                error = e.toString();
                Message message = Message.obtain();
                message.what = STATE_CONNECTION_TEST;
                handler.sendMessage(message);
            }
        }
    }

    private class SendReceive extends Thread
    {
        private final BluetoothSocket bluetoothSocket;
        private final InputStream inputStream;
        private final OutputStream outputStream;

        public SendReceive(BluetoothSocket socket)
        {
            bluetoothSocket = socket;
            InputStream tempIn = null;
            OutputStream tempOut = null;

            try {
                tempIn = bluetoothSocket.getInputStream();
                tempOut = bluetoothSocket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            inputStream = tempIn;
            outputStream = tempOut;
        }

        public void run()
        {
            byte[] buffer = new byte[1024];
            int bytes;

            while(true)
            {
                try {
                    bytes = inputStream.read(buffer);
                    handler.obtainMessage(STATE_MESSAGE_RECEIVED,bytes,-1,buffer).sendToTarget();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void write(byte[] bytes)
        {
            try {
                outputStream.write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}