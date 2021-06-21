package com.example.thetrueappwen;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;

public class  ThePieChare3 extends AppCompatActivity {
    private PieChart pieChart;
    private Context context;
    private Intent intent2;
    private String username;
    private int zhichujine,shourujine;
    private DBOpenMessage dbOpenMessage;
    private TextView shourutxt,zhichutxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.piechare3);
        pieChart = (PieChart) findViewById(R.id.wenpie3);


        //关于数据库操作
        context=this;
        intent2=getIntent();
        username=intent2.getStringExtra("username");//经检验已经成功
        dbOpenMessage=new DBOpenMessage(ThePieChare3.this,"db_wen2",null,1);
        getMessage1(username);

        shourutxt=(TextView)findViewById(R.id.wentext32);
        zhichutxt=(TextView)findViewById(R.id.wentext31);

        shourutxt.setText(Integer.toString(shourujine));
        zhichutxt.setText(Integer.toString(zhichujine));



        //关于图标的操作

        pieChart.setUsePercentValues(true);//设置value是否用显示百分数,默认为false
        pieChart.setDescription("所有金额支出收入总情况");//设置描述
        pieChart.setDescriptionTextSize(20);//设置描述字体大小

        pieChart.setExtraOffsets(5, 5, 5, 5);//设置饼状图距离上下左右的偏移量

        pieChart.setDrawCenterText(true);//是否绘制中间的文字
        pieChart.setCenterTextColor(Color.RED);//中间的文字颜色
        pieChart.setCenterTextSize(15);//中间的文字字体大小

        pieChart.setDrawHoleEnabled(true);//是否绘制饼状图中间的圆
        pieChart.setHoleColor(Color.WHITE);//饼状图中间的圆的绘制颜色
        pieChart.setHoleRadius(40f);//饼状图中间的圆的半径大小

        pieChart.setTransparentCircleColor(Color.BLACK);//设置圆环的颜色
        pieChart.setTransparentCircleAlpha(100);//设置圆环的透明度[0,255]
        pieChart.setTransparentCircleRadius(40f);//设置圆环的半径值

        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true);//设置饼状图是否可以旋转(默认为true)
        pieChart.setRotationAngle(10);//设置饼状图旋转的角度

        pieChart.setHighlightPerTapEnabled(true);//设置旋转的时候点中的tab是否高亮(默认为true)

        //右边小方框部分
        Legend l = pieChart.getLegend(); //设置比例图
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_CENTER);//设置每个tab的显示位置（这个位置是指下图右边小方框部分的位置 ）
//        l.setForm(Legend.LegendForm.LINE);  //设置比例图的形状，默认是方形
        l.setXEntrySpace(0f);
        l.setYEntrySpace(0f);//设置tab之间Y轴方向上的空白间距值
        l.setYOffset(0f);

        //饼状图上字体的设置
        // entry label styling
        pieChart.setDrawEntryLabels(true);//设置是否绘制Label
        pieChart.setEntryLabelColor(Color.RED);//设置绘制Label的颜色
        pieChart.setEntryLabelTextSize(20f);//设置绘制Label的字体大小

//        pieChart.setOnChartValueSelectedListener(this);//设值点击时候的回调
        pieChart.animateY(3400, Easing.EasingOption.EaseInQuad);//设置Y轴上的绘制动画
        ArrayList<PieEntry> pieEntries = new ArrayList<PieEntry>();

        pieEntries.add(new PieEntry(zhichujine, "总支出金额"));
        pieEntries.add(new PieEntry(shourujine, "总收入金额"));
        String centerText ;
        if(shourujine>zhichujine)
        {
            centerText = "整体为正资产：\n+¥" + (shourujine-zhichujine);
        }
        else if(shourujine<zhichujine)
        {
            centerText = "整体为负资产：\n-¥" + (zhichujine-shourujine);
        }
        else
        {
            centerText = "整体资产为零";
        }

        pieChart.setCenterText(centerText);//设置圆环中间的文字
        PieDataSet pieDataSet = new PieDataSet(pieEntries, "");
        ArrayList<Integer> colors = new ArrayList<Integer>();

        // 饼图颜色
        colors.add(Color.rgb(205, 205, 205));
        colors.add(Color.rgb(114, 188, 223));
        pieDataSet.setColors(colors);

        pieDataSet.setSliceSpace(0f);//设置选中的Tab离两边的距离
        pieDataSet.setSelectionShift(5f);//设置选中的tab的多出来的
        PieData pieData = new PieData();
        pieData.setDataSet(pieDataSet);

        //各个饼状图所占比例数字的设置
        pieData.setValueFormatter(new PercentFormatter());//设置%
        pieData.setValueTextSize(20f);
        pieData.setValueTextColor(Color.YELLOW);

        pieChart.setData(pieData);
        // undo all highlights
        pieChart.highlightValues(null);
        pieChart.invalidate();
    }
    private void getMessage1(String username) {
        Cursor cursor=dbOpenMessage.getAllCostData(username);
        if(cursor!=null){
            while(cursor.moveToNext()){
                Message message2=new Message();
                message2.userkind=cursor.getString(cursor.getColumnIndex("userkind"));
                message2.usermoney=cursor.getString(cursor.getColumnIndex("usermoney"));
                message2.userchoice=cursor.getString(cursor.getColumnIndex("userchoice"));
                if(message2.userchoice.equals("支出"))
                {
                        zhichujine+=Integer.parseInt(message2.usermoney);
                }
                else if(message2.userchoice.equals("收入"))
                {
                    shourujine+=Integer.parseInt(message2.usermoney);
                }

            }
        }
    }
}
