package com.example.thetrueappwen;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class MessageAdapter extends BaseAdapter
{
    private List<Message> mlist;
    private Context mContext;
    private LayoutInflater mlayoutInflater;
    public MessageAdapter(Context context, List<Message> list){
        mContext=context;
        mlist=list;
        mlayoutInflater= LayoutInflater.from(context);
    }

    @Override
    public int getCount() {//返回一共有多少条记录
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {//返回当前的item对象
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {//返回当前item的id
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null)
        {
            viewHolder=new ViewHolder();
            convertView=mlayoutInflater.inflate(R.layout.list_viewlayout,null);
            viewHolder.kindtxt=convertView.findViewById(R.id.kindtxt);
            viewHolder.datatxt=convertView.findViewById(R.id.datatxt);
            viewHolder.jinetxt=convertView.findViewById(R.id.jinetxt);
            // viewHolder.eventtxt=convertView.findViewById(R.id.eventtxt);
            // viewHolder.choicetxt=convertView.findViewById(R.id.choicetxt);
            // viewHolder.timetxt=convertView.findViewById(R.id.timetxt);

            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder) convertView.getTag();
        }
        Message message=mlist.get(position);
        viewHolder.kindtxt.setText(message.userkind);
        viewHolder.datatxt.setText(message.userdata);
        viewHolder.jinetxt.setText(message.usermoney);
        viewHolder=new ViewHolder();
        viewHolder.listwen=convertView.findViewById(R.id.list_view);
        if(message.userchoice.equals("收入"))
        {
            convertView.setBackgroundColor(Color.parseColor("#008577"));//背景色
        }
        else
        {
            convertView.setBackgroundColor(Color.parseColor("#D81B60"));//背景色
        }
        //   viewHolder.eventtxt.setText(message.userevent);
        //  viewHolder.timetxt.setText(message.usertime);
        //   viewHolder.choicetxt.setText(message.userchoice);

        return convertView;
    }

    private static class ViewHolder{//该类中包括item文件（activity_news_list_view）中所有需要显示内容的组件
        public TextView kindtxt;
        public TextView datatxt;
        public TextView jinetxt;
        public ListView listwen;
       // public TextView eventtxt;
        //public TextView choicetxt;
        // public TextView timetxt;
    }
}
