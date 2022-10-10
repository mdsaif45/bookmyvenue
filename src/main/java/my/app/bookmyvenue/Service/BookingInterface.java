package my.app.bookmyvenue.Service;

import java.util.List;

import my.app.bookmyvenue.Model.Booking;

public interface BookingInterface {
    
    // save all booking records
    public String save(Booking booking);

    // get booking records
    public List<Booking> getBookingDetails();

    // get no of booking 
    public long countOfBooking();

    // get booking details by order Id
    public Booking getBookingByOrderId(String orderId);

    // get booking details by user Id
    public List<Booking> getBookingByUserId(long userId);
}
