package com.yasser.iteration3;

import android.app.LauncherActivity;
import android.content.Context;
import android.icu.util.Calendar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TrackRidesAdapter extends RecyclerView.Adapter<TrackRidesAdapter.ViewHolder> {

    private List<ListTrack> ListTracks;
    private Context context;

    public TrackRidesAdapter(List<ListTrack> listTracks, Context context) {
        ListTracks = listTracks;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_track,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ListTrack Listtrack = ListTracks.get(position);
        holder.traveller.setText(Listtrack.getTraveldate());
        holder.seater.setText(Listtrack.getStoD());
        holder.pricer.setText(Listtrack.getPrice());
        holder.booker.setText(Listtrack.getBookingdate());
        holder.newseating.setText(Listtrack.getSeats());
        String ai = Listtrack.getVehicleType();
        if(ai.equals("2")){
            holder.imager.setImageResource(R.drawable.car0);
        }else holder.imager.setImageResource(R.drawable.car1);

        Calendar c=Calendar.getInstance();
        int day=c.get(java.util.Calendar.DAY_OF_MONTH);
        int month=c.get(java.util.Calendar.MONTH);
        int year=c.get(java.util.Calendar.YEAR);

        String sysdate=year+"-";

        if(month<9)
            sysdate+=(("0"+(month+1)+"-"));
        else
            sysdate+=((month+1)+"-");

        if(day<9)
            sysdate+=("0"+(day));
        else
            sysdate+=(day);

        Date date1,date2;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            String sexytemp = Listtrack.getTraveldate().substring(9,19);
            date1 = sdf.parse(sexytemp);
//            Toast.makeText(context,"Here!!!!!!!!!!!!!!!",Toast.LENGTH_LONG).show();
            date2 = sdf.parse(sysdate);
            if((date1.compareTo(date2)>0)){
                holder.imagerCheck.setImageResource(R.drawable.activenew);
            }
            else {
                holder.imagerCheck.setImageResource(R.drawable.tick);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return ListTracks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView booker,traveller,seater,pricer,newseating;
        public ImageView imager,imagerCheck;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            booker = itemView.findViewById(R.id.booking);
            traveller = itemView.findViewById(R.id.travelling);
            seater = itemView.findViewById(R.id.seating);
            pricer = itemView.findViewById(R.id.pricing);
            imager = itemView.findViewById(R.id.carImaging);
            imagerCheck = itemView.findViewById(R.id.imagerCheck);
            newseating = itemView.findViewById(R.id.newseating);
        }
    }
}
