package com.example.thetrueappwen;

import android.accounts.Account;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;
import java.util.List;




public class FrdFragment extends Fragment {
    private DBOpenMessage dbOpenMessage;
    private String username;
    private ListView listview;
    private List<Message> alllistmessage = new ArrayList<Message>();
    private MessageAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab2, container, false);
        dbOpenMessage = new DBOpenMessage(getActivity(), "db_wen2", null, 1);
        listview = (ListView) view.findViewById(R.id.list_view);
        alllistmessage.clear();
        getMessage1(username);

        adapter = new MessageAdapter(getActivity(), alllistmessage);
        //listview.setAdapter(null);
        //adapter.notifyDataSetChanged();

        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {

                //   Intent intent=new Intent(getActivity(),AllMessage.class);
                // intent.putExtra("username",username2);
                //startActivity(intent);
                Message message = (Message) parent.getItemAtPosition(position);

                Intent intent = new Intent();
                intent.setClass(getActivity(), AllMessage.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("message", message);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        return view;
    }

    //碎片和活动建立关联的时候调用
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        username = ((AllWord) context).getTitles();
    }

    /*  private void xianshixinxi()
      {
          Cursor cursor1=dbOpenMessage.getReadableDatabase().query("db_wen2",null,"username=?",new String[]{username},null,null,null);
          ArrayList<Map<String,String>> resultlist=new ArrayList<Map<String,String >>();
      }*/
    private void getMessage1(String username) {
        Cursor cursor = dbOpenMessage.getAllCostData(username);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Message message2 = new Message();
                message2.userkind = cursor.getString(cursor.getColumnIndex("userkind"));
                message2.usermoney = cursor.getString(cursor.getColumnIndex("usermoney"));
                message2.userdata = cursor.getString(cursor.getColumnIndex("userdata"));
                message2.userevent = cursor.getString(cursor.getColumnIndex("userevent"));
                message2.userchoice = cursor.getString(cursor.getColumnIndex("userchoice"));
                message2.usertime = cursor.getString(cursor.getColumnIndex("usertime"));
                message2.username = cursor.getString(cursor.getColumnIndex("username"));
                message2.id = cursor.getInt(cursor.getColumnIndex("_id"));
                alllistmessage.add(message2);
            }
        }
    }

    //实现长按删除listview里的item事件
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final ListView listView = (ListView) getActivity().findViewById(R.id.list_view);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                final Message[] account = {null};
                account[0] = alllistmessage.get(position);

                builder.setTitle("是否确定删除此账单？？？");
                builder.setMessage("账单内容如下：\n"+"用户： "+account[0].username+"   金额： "+account[0].usermoney+"    类型： "+account[0].userkind+"\n日期： "+account[0].userdata);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dbOpenMessage.deletebyid(account[0].userevent,account[0].usermoney, account[0].username);
                        alllistmessage.remove(position);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(view.getContext(), "删除该事件成功", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create().show();
                return true;
            }
        });

    }
}
