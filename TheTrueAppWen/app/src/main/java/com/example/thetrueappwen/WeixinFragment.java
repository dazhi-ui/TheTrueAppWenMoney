package com.example.thetrueappwen;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class WeixinFragment extends AppCompatActivity implements  View.OnClickListener, DatePicker.OnDateChangedListener, TimePicker.OnTimeChangedListener {

    private DBOpenMessage dbOpenMessage;

    private Intent intent;
    private String username,userchoice;
    private RadioGroup group;


    private LinearLayout llDate, llTime;
    private TextView tvDate, tvTime;
    private Context context;
    private int year, month, day, hour, minute;
    //在TextView上显示的字符
     private StringBuffer date, time;

     private Button back,queding;
     private EditText jine1,neirong1;

     private RadioButton strbut1,strbut2,strbut3,strbut4;
     private String userdata="",usertime="",usermoney="",userevent="",userkind="";
     private String wen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab1);
        context=this;
        intent=getIntent();
        username=intent.getStringExtra("username");//经检验已经成功
        Toast.makeText(WeixinFragment.this,"欢迎用户"+username+"增加支出账单",Toast.LENGTH_SHORT).show();

        llDate = (LinearLayout) findViewById(R.id.ll_date);
        tvDate = (TextView) findViewById(R.id.tv_date);

        llTime = (LinearLayout) findViewById(R.id.ll_time);
        tvTime = (TextView)findViewById(R.id.tv_time);


                                                        //一下用于数据库的相关操作
        queding=(Button)findViewById(R.id.wenbut2);
        back=(Button)findViewById(R.id.wenbut1);
        jine1=(EditText) findViewById(R.id.jine1);
        neirong1=(EditText)findViewById(R.id.neirong1);

        dbOpenMessage=new DBOpenMessage(WeixinFragment.this,"db_wen2",null,1);
        group=(RadioGroup)findViewById(R.id.kind1);
        strbut1=(RadioButton)findViewById(R.id.radBtnAgeRange1);
        strbut2=(RadioButton)findViewById(R.id.radBtnAgeRange2);
        strbut3=(RadioButton)findViewById(R.id.radBtnAgeRange3);
        strbut4=(RadioButton)findViewById(R.id.radBtnAgeRange4);
        tvDate.setText("请设置日期");
        tvTime.setText("请设置时间");

        queding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               switch (group.getCheckedRadioButtonId()) {
                    case R.id.radBtnAgeRange1:
                        userkind = "食品";
                        break;
                    case R.id.radBtnAgeRange2:
                        userkind = "衣物";
                        break;
                    case R.id.radBtnAgeRange3:
                        userkind = "出行";
                        break;
                    case R.id.radBtnAgeRange4:
                        userkind = "其他";
                        break;
                        default:break;
                }
                usermoney=jine1.getText().toString();
                userevent=neirong1.getText().toString();
                userdata=tvDate.getText().toString();
                usertime=tvTime.getText().toString();
                userchoice="支出";

                if(userkind.equals("")||usermoney.equals("")||userevent.equals("")||userdata.equals("")||usertime.equals("")||username.equals("")||userchoice.equals(""))
                {
                    Toast.makeText(WeixinFragment.this,"存在没有填写的项目,请重新输入",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    insterData(dbOpenMessage.getReadableDatabase(),username,usermoney,userevent,userkind,userdata,usertime,userchoice);
                    //进行数据库的数据增加
                    jine1.setText("");
                    neirong1.setText("");
                    tvDate.setText("请设置日期");
                    tvTime.setText("请设置时间");
                    group.clearCheck();
                    Calendar calendar = Calendar.getInstance();
                    year = calendar.get(Calendar.YEAR);
                    month = calendar.get(Calendar.MONTH);
                    day = calendar.get(Calendar.DAY_OF_MONTH);
                    hour = calendar.get(Calendar.HOUR)+8;
                    minute = calendar.get(Calendar.MINUTE);
                    wen="wen";
                    Toast.makeText(WeixinFragment.this,"添加成功请继续操作",Toast.LENGTH_SHORT).show();
                }
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(WeixinFragment.this,AllWord.class);
                intent.putExtra("username",username);
                startActivity(intent);
                finish();
            }
        });


        llDate.setOnClickListener(this);
        llTime.setOnClickListener(this);
        date = new StringBuffer();
        time = new StringBuffer();
        initDateTime();
    }


    /**
     * 获取当前的日期和时间
     */
    private void initDateTime() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR)+12;
        minute = calendar.get(Calendar.MINUTE);
        wen="wen";
    }
      @Override
        public void onClick (View v){
            switch (v.getId()) {
                case R.id.ll_date:
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (date.length() > 0&&!tvDate.equals("请设置日期")) { //清除上次记录的日期
                                date.delete(0, date.length());
                            }
                            month+=1;
                            tvDate.setText(date.append(String.valueOf(year)).append("年").append(String.valueOf(month)).append("月").append(day).append("日"));
                            dialog.dismiss();//取消对话框的作用
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    final AlertDialog dialog = builder.create();//创建对话框
                    View dialogView = View.inflate(context, R.layout.dialog_date, null);
                    /*这个方法可以得到view,但是对view中设置居中等什么属性都是无效的，还有就是设置的宽度也是无效的，默认只能是wrap*/
                    final DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.datePicker);

                    dialog.setTitle("设置日期");
                    dialog.setView(dialogView);//可以通过自定义一个View，设计成自己想要的不同的dialog，(会话)
                    dialog.show();//显示对话框
                    //初始化日期监听事件
                    if(wen.equals("wen"))
                    {
                        datePicker.init(year, month, day, this);
                        wen="xue";
                    }
                    else
                    {
                        datePicker.init(year, month-1, day, this);
                    }

                    break;
                case R.id.ll_time:
                    AlertDialog.Builder builder2 = new AlertDialog.Builder(context);
                    builder2.setPositiveButton("设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (time.length() > 0) { //清除上次记录的日期
                                time.delete(0, time.length());
                            }
                            tvTime.setText(time.append(String.valueOf(hour)).append("时").append(String.valueOf(minute)).append("分"));
                            dialog.dismiss();
                        }
                    });
                    builder2.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog2 = builder2.create();
                    View dialogView2 = View.inflate(context, R.layout.dialog_time, null);

                    TimePicker timePicker = (TimePicker) dialogView2.findViewById(R.id.timePicker);
                    timePicker.setCurrentHour(hour);
                    timePicker.setCurrentMinute(minute);
                    timePicker.setIs24HourView(true); //设置24小时制

                    timePicker.setOnTimeChangedListener(this);
                    dialog2.setTitle("设置时间");
                    dialog2.setView(dialogView2);
                    dialog2.show();
                    break;
            }
            /**
             * 日期改变的监听事件
             *
             * @param view
             * @param year
             * @param monthOfYear
             * @param dayOfMonth
             */

        }

        @Override
        public void onDateChanged (DatePicker view,int year, int monthOfYear, int dayOfMonth){
            this.year = year;
            this.month = monthOfYear;
            this.day = dayOfMonth;
        }
        /**
         * 时间改变的监听事件
         *
         * @param view
         * @param hourOfDay
         * @param minute
         */
        @Override
        public void onTimeChanged (TimePicker view,int hourOfDay, int minute){
            this.hour = hourOfDay;
            this.minute = minute;
        }
    private void insterData(SQLiteDatabase sqLiteDatabase, String username,String usermoney,String userevent,String userkind,String userdata,String usertime,String userchoice)
    {
        ContentValues values=new ContentValues();

        values.put("username",username);
        values.put("usermoney",usermoney);
        values.put("userevent",userevent);
        values.put("userdata",userdata);
        values.put("usertime",usertime);
        values.put("userkind",userkind);
        values.put("userchoice",userchoice);
        sqLiteDatabase.insert("db_wen2",null,values);
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if(dbOpenMessage!=null)
            dbOpenMessage.close();
    }
}