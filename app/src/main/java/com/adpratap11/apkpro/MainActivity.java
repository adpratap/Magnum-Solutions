package com.adpratap11.apkpro;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MainActivity extends AppCompatActivity {






  private LinearLayout mylayout = null;

  private RecyclerView chat_layout = null;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        setTitle("Message to Maths sir");

        final RecyclerView msgRecyclerView = findViewById(R.id.chat_recycler_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(linearLayoutManager);

        final List<ChatAppMsgDTO> msgDtoList = new ArrayList<ChatAppMsgDTO>();


        chat_layout = findViewById(R.id.chat_recycler_view);



        chat_layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {




                if(amIconnected()) {


                    if (event.getAction() == MotionEvent.ACTION_UP) {


                        ChatAppMsgDTO msgDto = new ChatAppMsgDTO(ChatAppMsgDTO.MSG_TYPE_RECEIVED, ("reply me answer fast   " + getRandomNumberInRange(5, 100) + " "+ chatdata.title[getRandomNumberInRange(0,3)] +" "+ getRandomNumberInRange(5, 50)));

                        msgDtoList.add(msgDto);


                        int newMsgPosition = msgDtoList.size() - 1;


                        msgRecyclerView.scrollToPosition(newMsgPosition);


                    }
                }


                    else {

                    buildDialog(MainActivity.this).show();

                    Toast.makeText(getApplicationContext(), "network error", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
















        final ChatAppMsgAdapter chatAppMsgAdapter = new ChatAppMsgAdapter(msgDtoList);

        msgRecyclerView.setAdapter(chatAppMsgAdapter);

        final EditText msgInputText = (EditText)findViewById(R.id.chat_input_msg);

        Button msgSendButton = (Button)findViewById(R.id.chat_send_msg);




        msgSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msgContent = msgInputText.getText().toString();
                if(!TextUtils.isEmpty(msgContent))
                {

                    ChatAppMsgDTO msgDto = new ChatAppMsgDTO(ChatAppMsgDTO.MSG_TYPE_SENT, msgContent);
                    msgDtoList.add(msgDto);

                    int newMsgPosition = msgDtoList.size() - 1;


                    chatAppMsgAdapter.notifyItemInserted(newMsgPosition);


                    msgRecyclerView.scrollToPosition(newMsgPosition);


                    msgInputText.setText("");
                }
            }
        });

    }

    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    private  boolean amIconnected(){

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkinfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkinfo != null && activeNetworkinfo.isConnected();
    }




    public AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet Connection");
        builder.setMessage("Please make sure you are connected to interner  press Retry");

        builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(!amIconnected()){

                    buildDialog(MainActivity.this).show();



                }



            }
        });

        return builder;
    }
}
