package com.yasser.iteration3;

import android.widget.ImageView;

public class ListItem {
    private String time;
    private String seatsAV;
    private String price;
    private String seatsTotal;
    private int CarIMG;
    private int vehicleid;

    public ListItem(String time, String seatsAV, String price, String seatsTotal, int CarIMG,int vehicleid) {
        this.time = time;
        this.seatsAV = seatsAV;
        this.price = price;
        this.seatsTotal = seatsTotal;
        this.CarIMG = CarIMG;
        this.vehicleid = vehicleid;
    }

    public String getTime() {
        return time;
    }

    public String getSeatsAV() {
        return seatsAV;
    }

    public String getPrice() {
        return price;
    }

    public String getSeatsTotal() {
        return seatsTotal;
    }

    public int getCarIMG() {
        return CarIMG;
    }
    public int getVehicleid() {
        return vehicleid;
    }
}
