package com.example.thetrueappwen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class WeixinFragment11 extends Fragment {
    private Button comeonqian,outputqian;
    private String username;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tap11, container, false);

        comeonqian=(Button) view.findViewById(R.id.comeonqian);
        outputqian=(Button) view.findViewById(R.id.outputqian);
        //接收传来的参数
        //得到从Activity传来的数据


        outputqian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),WeixinFragment.class);
                intent.putExtra("username",username);
                startActivity(intent);
                getActivity().finish();
            }
        });
        comeonqian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),WeixinFragment12.class);
                intent.putExtra("username",username);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }
    //进行刷新的


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        username = ((AllWord) context).getTitles();
    }
/*
出现了空指针错误
    private void getusername()
    {
        Bundle bundle =this.getArguments();
        if(bundle!=null)
        {
            username = bundle.getString("username");
            Toast.makeText(getActivity(),"欢迎用户"+username+"来到此页面",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getActivity(),"失败了",Toast.LENGTH_SHORT).show();
        }
    }
    */
}
