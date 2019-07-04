package com.yasser.iteration3;

public class ListTrack {
    private String traveldate;
    private String bookingdate;
    private String seats;
    private String price;
    private String vehicleType;
    private String StoD;

    public ListTrack(String traveldate, String bookingdate, String seats, String price, String vehicleType, String StoD) {
        this.traveldate = traveldate;
        this.bookingdate = bookingdate;
        this.seats = seats;
        this.price = price;
        this.vehicleType = vehicleType;
        this.StoD = StoD;
    }

    public String getTraveldate() {
        return traveldate;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public String getBookingdate() {
        return bookingdate;
    }

    public String getSeats() {
        return seats;
    }

    public String getPrice() {
        return price;
    }

    public String getStoD() {
        return StoD;
    }
}
