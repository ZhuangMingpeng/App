package com.bgxt.urldemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private TextView tv1;
    private TextView tv2;
    private final String path ="https://www.themealdb.com/api/json/v1/1/search.php?f=a";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv1= findViewById(R.id.tv1);
        tv2= findViewById(R.id.tv2);
        button = findViewById(R.id.btn_get);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        String str = get();
                        try {
                            if (str != null) {
                                JSONObject jsonObject = new JSONObject(str);
                                final String name = jsonObject.getJSONArray("meals").getJSONObject(0).getString("idMeal");
                                final int length = jsonObject.getJSONArray("meals").length();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        tv1.setText("    "+length);
                                        tv2.setText(name);
                                    }
                                });
                            }
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });
    }
    
    private String get(){
        try {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(10*1000);
            //todo POST传输方式
            connection.setRequestMethod("GET");
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                //TODO
                InputStream in = connection.getInputStream();
//                byte[] b = new byte[1024];
//                int len;
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                while ((len = in.read(b)) != -1) {
//                    baos.write(b,0,len);
//                }

                InputStreamReader isr = new InputStreamReader(in);
                BufferedReader bfr = new BufferedReader(isr);
                String line;
                StringBuilder stringBuilder = new StringBuilder();
                while ((line = bfr.readLine()) != null) {
                    stringBuilder.append(line);
                }

                String msg = stringBuilder.toString();
                System.out.println(msg.length());
                Log.e("MainActivityTAG",msg);
                connection.disconnect();
                return msg;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String post(){
        try {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setConnectTimeout(10*1000);
            connection.setDoOutput(true);

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                //TODO
                InputStream in = connection.getInputStream();

                InputStreamReader isr = new InputStreamReader(in);
                BufferedReader bfr = new BufferedReader(isr);
                String line;
                StringBuilder stringBuilder = new StringBuilder();
                while ((line = bfr.readLine()) != null) {
                    stringBuilder.append(line);
                }

                String msg = stringBuilder.toString();
                System.out.println(msg.length());
                Log.e("MainActivityTAG",msg);
                connection.disconnect();
                return msg;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



}