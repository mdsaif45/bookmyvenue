package my.app.bookmyvenue.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import my.app.bookmyvenue.Model.Venue;


@Repository
public interface VenueRepository extends JpaRepository<Venue, Long> {
    
    // get all venue havin rate more than 4.1
    @Query("Select v from Venue v where v.rating > 4.1")
    public List<Venue> getBestVenue();

    // count for total venues in database
    @Query("SELECT COUNT(v) FROM Venue v")
    public long countOfVenues();

    // @Query("from Venue where venueName like %:venueName%")
    // public List<Venue> search(String venueName);

    // to search with venue name
    public List<Venue> findByVenueNameContaining(String venueName); 
    
    // to search for city
    public List<Venue> findVenueByCity(String venueCity);

    // filter venue
    public List<Venue> findVenueByVenueType(String venueType);
    public List<Venue> findVenueByVenueTypeAndCity(String venueType, String venueCity);

}
