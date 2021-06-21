package com.example.thetrueappwen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AllMessage extends AppCompatActivity
{
    private Context context;
    private Intent intent;
    private String username,usermoney,userkind ,userdata,usertime,userevent,userchoice;
    private Message message;
    private DBOpenMessage dbOpenMessage;
    private EditText jine5,neirong5;
    private TextView data5,time5,choice5,kind5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_message);
        context=this;
        intent=getIntent();
        username=intent.getStringExtra("username");//经检验已经成功

        jine5=(EditText)findViewById(R.id.jine5);
        neirong5=(EditText)findViewById(R.id.neirong5);
        data5=(TextView)findViewById(R.id.data5);
        time5=(TextView)findViewById(R.id.time5);
        choice5=(TextView)findViewById(R.id.choice5);
        kind5=(TextView)findViewById(R.id.kind5);

        dbOpenMessage=new DBOpenMessage(AllMessage.this,"db_wen2",null,1);
        Intent intent = this.getIntent();
        message=(Message)intent.getSerializableExtra("message");

        usermoney=message.getUsermoney();
        jine5.setText(usermoney);
        userevent=message.getUserevent();
        neirong5.setText(userevent);
        userdata=message.getUserdata();
        data5.setText(userdata);
        usertime=message.getUsertime();
        time5.setText(usertime);
        userchoice=message.getUserchoice();
        choice5.setText(userchoice);
        userkind=message.getUserkind();
        kind5.setText(userkind);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        if(dbOpenMessage!=null)
            dbOpenMessage.close();
    }
}
