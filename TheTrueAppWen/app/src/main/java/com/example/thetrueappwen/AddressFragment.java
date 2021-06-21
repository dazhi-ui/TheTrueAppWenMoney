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

import java.sql.BatchUpdateException;

public class AddressFragment extends Fragment {
    private String username;
    private Button button1,button2,button3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab3, container, false);

        button1=(Button)view.findViewById(R.id.button1);
        button2=(Button)view.findViewById(R.id.button2);
        button3=(Button)view.findViewById(R.id.button3);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ThePieChare1.class);
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ThePieChare2.class);
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ThePieChare3.class);
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });
        return view;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        username = ((AllWord) context).getTitles();
    }
}