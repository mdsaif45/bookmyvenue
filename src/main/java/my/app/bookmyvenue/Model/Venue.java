package my.app.bookmyvenue.Model;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Venue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long venueId;
    private String venueName;
    private String overview;
    private String amenities;
    private long capacity;
    private String eventList;
    private int responseTime;
    private long venuePrice;
    private long vegPrice;
    private long nonvegPrice;
    @Column(name = "email")
    private String emailId;
    private long phoneNo;
    private String Address;
    private String city;
    private String map="0,0";
    private String thumbnail;
	private String status="No";
	private String addedOn;
    private String venueOwner;
    private String venueType;
    private float rating=4.1f;
        
    @Transient
    public String getPhotosImagePath() {
        if (thumbnail == null) return null;
         
        return "/user-photos/" + venueId + "/" + thumbnail;
    }
    
}



