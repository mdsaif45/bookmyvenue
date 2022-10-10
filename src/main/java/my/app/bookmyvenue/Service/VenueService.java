package my.app.bookmyvenue.Service;

// import java.nio.file.Files;
// import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import my.app.bookmyvenue.Model.Venue;
import my.app.bookmyvenue.Repository.VenueRepository;

@Service
public class VenueService implements VenueInterface{

    @Autowired
    private VenueRepository repo;

    @Value("${project.image}")
	private String path;

    // save venue
    @Override
    public void addVenue(Venue venue){
        repo.save(venue);
    }
    
    // venue update
    @Override
    public void updateVenue(Venue venue){
        repo.save(venue);
    }

    // get all venues
    @Override
    public List<Venue> getAllVenue(){
        return repo.findAll();
    }
    
    // get venue by ID
    @Override
    public Venue getVenueById(long id){
        Optional<Venue> venue = repo.findById(id);
        if(venue.isPresent()){
            return venue.get();
        }
        return null;
    }

    // get best rating venues
    @Override
    public List<Venue> getBestVenue(){
        return repo.getBestVenue();
    }

    // searching venue
    @Override
    public List<Venue> searchVenue(String venueName){
        return repo.findByVenueNameContaining(venueName);
    }

    // get venue by venue city
    @Override
    public List<Venue> getVenueByCity(String venueCity){
        return repo.findVenueByCity(venueCity);
    }

    // get venue by venue type
    @Override
    public List<Venue> getVenueByType(String venueType){
        return repo.findVenueByVenueType(venueType);
    }

    // get venue by venue type and venue city
    @Override
    public List<Venue> getVenueByTypeAndCity(String venueType, String venueCity){

        return repo.findVenueByVenueTypeAndCity(venueType, venueCity);
    }

    // delete venue by ID
    @Override
    public void deleteVenue(long id){
        repo.deleteById(id);
    }

    // get page of venues
    @Override
    public Page<Venue> getVenueByPaginate(int currentPage, int size){
        return repo.findAll(PageRequest.of(currentPage, size));
    }

    // total count for venues
    @Override
    public long countOfVenues(){
        return repo.countOfVenues();
    }
}
