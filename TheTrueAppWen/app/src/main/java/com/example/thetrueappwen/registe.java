package com.example.thetrueappwen;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class registe extends AppCompatActivity
{
    private DBOpenMessageUser dbOpenMessageUser;
    private EditText mima1,mima2;
    private Button registe,but;
    private String pass1="",pass2="";
    private String username="";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registe_wen);
        //创建数据库对象

        mima1=(EditText)findViewById(R.id.mima111);
        mima2=(EditText)findViewById(R.id.mima333);
        registe=(Button)findViewById(R.id.buttonreg11);
        but=(Button)findViewById(R.id.buttonreg22);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(registe.this,AllWord.class);
                intent.putExtra("username",username);
                finish();
            }
        });

        registe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbOpenMessageUser=new DBOpenMessageUser(registe.this,"db_wen",null,1);

                pass1=mima1.getText().toString();
                pass2=mima2.getText().toString();

                if(pass1.equals("")||pass2.equals(""))
                {
                    Toast.makeText(registe.this,"存在没有填写的项目,请重新输入",Toast.LENGTH_SHORT).show();
                }
                else if(pass1.equals(pass2))
                {
                    Intent intent=getIntent();
                    intent=getIntent();
                    username=intent.getStringExtra("username");//经检验已经成功
                    dbOpenMessageUser.updatapassword(username,pass1);
                    Toast.makeText(registe.this,"修改成功,请重新登录",Toast.LENGTH_SHORT).show();
                    Intent intent2=new Intent(registe.this,MainActivity.class);
                    startActivity(intent2);
                    AllWord.wen.finish();
                    finish();
                }
                else
                {
                    Toast.makeText(registe.this,"输入的两次密码不一致",Toast.LENGTH_LONG).show();
                    mima1.setText("");
                    mima2.setText("");
                }
            }
        });
    }
}
