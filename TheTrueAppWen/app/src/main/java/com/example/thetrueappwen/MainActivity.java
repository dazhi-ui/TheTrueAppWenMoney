package com.example.thetrueappwen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.service.autofill.SaveInfo;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private DBOpenMessageUser messageuser;
    private EditText username,password;
    private Button login,register;
    private String passwordstr="";
    private String usernamestr="";
    private String passwordstr2;
    private CheckBox checkbox;
    private String setpassword,setusercheck,setuserpicture="";
    private ImageView picture2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //创建数据库对象
        messageuser=new DBOpenMessageUser(MainActivity.this,"db_wen",null,1);
        //putusername();

        username=(EditText)findViewById(R.id.username1);
        password=(EditText)findViewById(R.id.password1);
        login=(Button)findViewById(R.id.button_login);
        register=(Button)findViewById(R.id.button_register);
        checkbox=(CheckBox)findViewById(R.id.checkBox);
        picture2=(ImageView)findViewById(R.id.picture2);



        //判断是否设置过保存密码
        username.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){//获得焦点
                }
                else{//失去焦点
                    usernamestr="";
                    usernamestr=username.getText().toString();
                    if(!usernamestr.equals(""))
                    {
                        Cursor cursor=messageuser.getAllCostData(usernamestr);
                            if(cursor.moveToNext())
                            {
                                setpassword=cursor.getString(cursor.getColumnIndex("password"));
                                setusercheck=cursor.getString(cursor.getColumnIndex("usercheck"));
                                setuserpicture=cursor.getString(cursor.getColumnIndex("userpicture"));
                                if(setusercheck.equals("1"))
                                {
                                    password.setText(setpassword);
                                    checkbox.setChecked(true);
                                }
                                if(!setuserpicture.equals(""))
                                {
                                    Bitmap bitmap= BitmapFactory.decodeFile(setuserpicture);
                                    picture2.setImageBitmap(bitmap);
                                }
                            }
                        else
                        {
                            Toast.makeText(MainActivity.this,"该账户不存在.建议先注册",Toast.LENGTH_SHORT).show();
                            username.setText("");
                            picture2.setImageResource(R.mipmap.ic_launcher_round);
                            username.setHint("账户不存在，请重新输入");
                        }


                    }
                }
            }
        });

        //点击登录后的判断
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernamestr=username.getText().toString();
                passwordstr=password.getText().toString();
                Cursor cursor1=messageuser.getReadableDatabase().query("db_wen",null,"username=?",new String[]{usernamestr},null,null,null);
                //显示表名、限制列、条件、值、其他的默认
                if(usernamestr.equals("")||passwordstr.equals(""))
                {
                    Toast.makeText(MainActivity.this,"请填写齐全",Toast.LENGTH_SHORT).show();
                }
                if(cursor1.moveToFirst())
                {
                    do {
                         passwordstr2=cursor1.getString(cursor1.getColumnIndex("password"));
                    }while(cursor1.moveToNext());
                }

                cursor1.close();
                if(password.getText().toString().equals(passwordstr2)&&!usernamestr.equals("")&&!passwordstr.equals(""))
                {
                    if(checkbox.isChecked())
                    {
                        String usercheck="1";
                        messageuser.updatauser(usernamestr,usercheck);
                    }
                    else
                    {
                        String usercheck="0";
                        messageuser.updatauser(usernamestr,usercheck);
                    }
                    Toast.makeText(MainActivity.this,"恭喜登录成功",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(MainActivity.this,AllWord.class);
                    //传递参数在intent中
                    intent.putExtra("username",usernamestr);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(MainActivity.this,"密码错误，不能进行登录",Toast.LENGTH_SHORT).show();
                    username.setText("");
                    password.setText("");
                }
            }
        });
        //注册页面的相关操作
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(MainActivity.this,Register.class);
                intent.putExtra("username",usernamestr);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if(messageuser!=null)
            messageuser.close();
    }
     /*
    private void putusername()
    {
        //
        //传递数值
        WeixinFragment11 myFragment4 = new WeixinFragment11();
        Bundle bundle4 = new Bundle();
        bundle4.putString("username",usernamestr);
        myFragment4.setArguments(bundle4);
    }*/

}
