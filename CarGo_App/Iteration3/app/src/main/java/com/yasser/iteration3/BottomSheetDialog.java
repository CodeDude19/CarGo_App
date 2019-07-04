package com.yasser.iteration3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class BottomSheetDialog extends BottomSheetDialogFragment {

    private BottomSheetListener mListener;
    private SharedPreferences seatsl1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_layout,container,false);
        LinearLayout b1 = (LinearLayout) v.findViewById(R.id.l1);
        LinearLayout b2 = (LinearLayout) v.findViewById(R.id.l2);
        LinearLayout b3 = (LinearLayout) v.findViewById(R.id.l3);
        LinearLayout b4 = (LinearLayout) v.findViewById(R.id.l4);
        LinearLayout b5 = (LinearLayout) v.findViewById(R.id.l5);
        LinearLayout b6 = (LinearLayout) v.findViewById(R.id.l6);
        LinearLayout ids[] ={b1,b2,b3,b4,b5,b6};
        seatsl1=getContext().getSharedPreferences("seatsl",Context.MODE_PRIVATE);
        String sets = seatsl1.getString("SEATS_LEFT",null);
        String tsets = seatsl1.getString("TOTAL_SEATS",null);
        String tsets1 = tsets.substring(tsets.length()-1,tsets.length());
        String sets1 = sets.substring(sets.length()-1,sets.length());
        int Seatsn = Integer.parseInt(sets1);
        int TSeatsn = Integer.parseInt(tsets1);
        int nSeats = Seatsn++;
        if(TSeatsn==6){
            if(nSeats>0){
                for(int i = nSeats;i<TSeatsn;i++){
                    ids[i].setVisibility(View.INVISIBLE);
                }
            }
        }else
        {
            b5.setVisibility(View.INVISIBLE);
            b6.setVisibility(View.INVISIBLE);
            if(nSeats>0){
                for(int i = nSeats;i<=TSeatsn+1;i++){
                    ids[i].setVisibility(View.INVISIBLE);
                }
            }
        }
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onButtonCLicked("1");
                dismiss();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onButtonCLicked("2");
                dismiss();
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onButtonCLicked("3");
                dismiss();
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onButtonCLicked("4");
                dismiss();
            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onButtonCLicked("5");
                dismiss();
            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onButtonCLicked("6");
                dismiss();
            }
        });
        return v;
    }
    public interface BottomSheetListener{
        void onButtonCLicked(String text);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mListener = (BottomSheetListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"must implement BottomSheetListener");
        }
    }
}
