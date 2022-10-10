package my.app.bookmyvenue.Model;

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
public class EventList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long eventId;
    private long venueId;
    private boolean wedding;
    private boolean engagement;
    private boolean reception;
    private boolean anniversary;
    private boolean socialGathering;
    private boolean conference;
    private boolean exhibittion;
    private boolean bachelorParty;
    private boolean birthdayParty;


}
