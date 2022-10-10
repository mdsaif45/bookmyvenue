package my.app.bookmyvenue.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import my.app.bookmyvenue.Model.Booking;
import my.app.bookmyvenue.Repository.BookingRepository;


@Service
public class BookingService implements BookingInterface{

    @Autowired
    private BookingRepository bookingRepository;

    // save all booking records
    @Override
    public String save(Booking booking){
        bookingRepository.save(booking);
        return "booking entry added";
    }
    
    // get booking records
    @Override
    public List<Booking> getBookingDetails(){
        return bookingRepository.findAll();
    }

    // get no of booking 
    @Override
    public long countOfBooking(){
        return bookingRepository.countOfBooking();
    }
    
    // get booking details by order Id
    @Override
    public Booking getBookingByOrderId(String orderId){
        return bookingRepository.getBookingByOrderId(orderId);
    }

    // get booking details by user Id
    @Override
    public List<Booking> getBookingByUserId(long userId){
        return bookingRepository.getBookingByUserId(userId);
    }
}
