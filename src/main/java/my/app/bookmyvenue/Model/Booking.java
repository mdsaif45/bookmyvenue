package my.app.bookmyvenue.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "orderId")
    private String orderId;
    private long userId;
    private long venueId;
    private int capacity;
    private String eventFrom;
    private String eventTo;
    private String eventType;
    private String mealType;
    private String status;
    private String bookingTime;
    private String transactionId;
    private String transactionDate;
}
