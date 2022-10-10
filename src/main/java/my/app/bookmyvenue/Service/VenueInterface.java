package my.app.bookmyvenue.Service;

import java.util.List;

import org.springframework.data.domain.Page;

import my.app.bookmyvenue.Model.Venue;

public interface VenueInterface {
    
    // save Venue
    public void addVenue(Venue venue);

    // Update Venue
    public void updateVenue(Venue venue);
    
    // get all list of Venue
    public List<Venue> getAllVenue();
    
    // delete Venue
    public void deleteVenue(long id);
    
    // get Venue by Id
    public Venue getVenueById(long id);

    // get best rating venues
    public List<Venue> getBestVenue();

    // searching venue
    public List<Venue> searchVenue(String venueName);

    // filter Venue 
    public List<Venue> getVenueByCity(String venueCity);
    public List<Venue> getVenueByType(String venueType);
    public List<Venue> getVenueByTypeAndCity(String venueType, String venueCity);

    // total count for venues
    public long countOfVenues();

    // get page of venues
    public Page<Venue> getVenueByPaginate(int currentPage, int size);
}
