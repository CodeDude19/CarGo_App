package com.yasser.iteration3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;
public class CarSelectionAdapter extends RecyclerView.Adapter<CarSelectionAdapter.ViewHolder>{
    private OnImageClickListener onImageClickListener;
    private String[] data_important = {"","","","","","",""};
    private List<ListItem> listItems;
    private Context context;


    public interface OnImageClickListener {
        void onImageClick(String[] imageData);
    }
    public CarSelectionAdapter(List<ListItem> listItems, Context context,OnImageClickListener onImageClickListener) {
        this.listItems = listItems;
        this.context = context;
        this.onImageClickListener = onImageClickListener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final ListItem listItem = listItems.get(position);
        holder.time.setText(listItem.getTime());
        holder.price.setText(listItem.getPrice());
        holder.seatsTotal.setText(listItem.getSeatsTotal());
        int imgID;
        if(listItem.getCarIMG() == 1){
            imgID = R.drawable.car1;
        }
        else imgID = R.drawable.car0;
        holder.seatsAV.setText(listItem.getSeatsAV());
        holder.carIMG.setImageResource(imgID);
        holder.BookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data_important[0] = listItem.getTime();
                data_important[1] = listItem.getPrice();
                data_important[2] = listItem.getSeatsTotal();
                data_important[3] = listItem.getSeatsAV();
                data_important[6] = String.valueOf(listItem.getVehicleid());
                data_important[4] = String.valueOf(listItem.getCarIMG());
//                Toast.makeText(context,"You Clicked Vehicle price : "+ listItem.getPrice(),Toast.LENGTH_LONG).show();
                onImageClickListener.onImageClick(data_important);
            }
        });
    }
    @Override
    public int getItemCount() {
        return listItems.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView time;
        public TextView seatsAV;
        public TextView price;
        public TextView seatsTotal;
        public ImageView carIMG;
        public TextView BookButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            time = (TextView) itemView.findViewById(R.id.time);
            seatsAV = (TextView) itemView.findViewById(R.id.seatsAV);
            price = (TextView) itemView.findViewById(R.id.price);
            seatsTotal = (TextView) itemView.findViewById(R.id.seatsTotal);
            carIMG = (ImageView) itemView.findViewById(R.id.carImg);
            BookButton = itemView.findViewById(R.id.BookButton);
        }
    }
}
