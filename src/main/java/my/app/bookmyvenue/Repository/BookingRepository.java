package my.app.bookmyvenue.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import my.app.bookmyvenue.Model.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    // get count of booking details
    @Query("SELECT COUNT(b) FROM Booking b")
    long countOfBooking();

    // get booking details by user Id
    List<Booking> getBookingByUserId(long userId);

    // get booking details by order Id
    Booking getBookingByOrderId(String orderId);

}
