package my.app.bookmyvenue.Controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;



import my.app.bookmyvenue.Model.Venue;
import my.app.bookmyvenue.Service.VenueService;

// all public pages handle here
@Controller
public class PageController {


    @Autowired
    private VenueService venueService;

    // return home page
    @GetMapping("/")
    public String index(Model model) {
        List<Venue> bestVenue = venueService.getBestVenue();
        for(Venue venue : bestVenue){
            String imagePath = "img" + File.separator + venue.getThumbnail();
            venue.setThumbnail(imagePath);

        }
        model.addAttribute("bestvenues", bestVenue);
        bestVenue.forEach(e->System.out.println(e));

        model.addAttribute("title", "Home");
        return "index";
    }

    // listing all venues
    @PostMapping("/venueList")
    public String listVenue(@ModelAttribute("event") String event,
                            @ModelAttribute("venueType") String venueType,
                            @ModelAttribute("city") String city,
                            Model model) {

                                
        List<Venue> filterVenues = new ArrayList<Venue>();
        List<Venue> allVenue = venueService.getVenueByTypeAndCity(venueType, city);
        
        allVenue.forEach(e->{
            if(e.getEventList().contains(event)){
                System.out.println(e);
                String imagePath = "img"+File.separator+e.getThumbnail();
                e.setThumbnail(imagePath);
                filterVenues.add(e);
            }
        });

        model.addAttribute("venue", filterVenues);
        model.addAttribute("title", "All Venues");
        
        return "list-venues";
    }

    // list all venue by type
    @GetMapping("/venue")
    public String getVenueByType(@RequestParam("venueType") String venueType, Model model) {
        System.out.println(venueType);
        List<Venue> venueByVenueType = venueService.getVenueByType(venueType);
        venueByVenueType.forEach(e->{
            String imagePath = "img"+File.separator+e.getThumbnail();
            e.setThumbnail(imagePath);
        });

        model.addAttribute("venue", venueByVenueType);
        model.addAttribute("title", venueType+" List");
        return "list-venues";
    }

    // list all venue by city
    @GetMapping("/venuecity")
    public String getVenueByCity(@RequestParam("venueCity") String venueCity, Model model) {
        System.out.println(venueCity);
        List<Venue> venueByCity = venueService.getVenueByCity(venueCity);
        venueByCity.forEach(e->{
            String imagePath = "img"+File.separator+e.getThumbnail();
            e.setThumbnail(imagePath);
        });
        model.addAttribute("venue", venueByCity);
        model.addAttribute("title", venueCity+" List");
        return "list-venues";
    }

    // search fucntion for venue
    @GetMapping("/venuesearch")
    public String searchVenue(@ModelAttribute("key") String key, Model model){
        String venueName = key;
        
        List<Venue> searchVenue = venueService.searchVenue(venueName);
        searchVenue.forEach(e->{
            String imagePath = "img"+File.separator+e.getThumbnail();
            e.setThumbnail(imagePath);
        });


        model.addAttribute("venue", searchVenue);
        model.addAttribute("title", key+" result");
        
        return "list-venues";

    }


    // venue detail dynamic page
    // url : https://localhost:8080/venuedetails?id=value
    @GetMapping("/venuedetails")
    public String venueDetails(@RequestParam long id, Model model) {
        Venue venue = venueService.getVenueById(id);
        String imagePath = "img"+File.separator+venue.getThumbnail();
        venue.setThumbnail(imagePath);
        System.out.println(venue);
        model.addAttribute("venue", venue);
        model.addAttribute("title", venue.getVenueName() + " details");
        return "venue-detail";
    }


    // user contact page
    @GetMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("title", "Contact Us");
        return "contact";
    }

    // user about page
    @GetMapping("/about")
    public String aboutus(Model model) {
        model.addAttribute("title", "About Us");
        return "about";
    }

    // user login page
    @GetMapping("/loginpage")
    public String loginPage(Model model){
        model.addAttribute("title", "Login to BookMyVenue");
        return "signin";
    }
    
    // user signup page
    @GetMapping("/signup")
    public String registerPage(Model model){
        model.addAttribute("title", "Sign Up");
        return "signup";
    }

    // admin login page
    @GetMapping("/signin")
    public String signIn(Model model){
        model.addAttribute("title", "Sign In");
        return "admin/sign-in";
    }
    




    // for testing--------------------------------------------------=>

    // @GetMapping("/filter")
    // public String testingFilter() {
        // String venueType="Restaurant";
        // String venueType="Hotel";
        // String venueCity="Pune";
        // String venueCity="Bhopal";
        // List<Venue> venueByVenueType = venueService.getVenueByVenueType(venueType);
        // venueByVenueType.forEach(e->System.out.println(e));

        // venueService.getVenueByVenueTypeAndCity(venueType, venueCity).forEach(e->System.out.println(e));
        // String venueName = "oM";
        
        // venueService.searchVenue(venueName).forEach(e->System.out.println(e));
    //     return "list-venues";
    // }
    


    // learning Stage------------------------------------------------=>

    // @GetMapping("/home")
    // public String home() {
    //     return "home";
    // }

    // @GetMapping("/gallery")
    // public String gallery() {
    //     return "pages/venue/gallery";
    // }

    // @GetMapping("/venue-list")
    // public String venue() {
    //     return "venuecard";
    // }

    // @GetMapping("/addVenuetest")
    // public String addVenue() {
    //     return "pages/venue/addVenue";
    // }

    // @GetMapping("/test1")
    // public String test1() {
    //     return "pages/test";
    // }

    // @GetMapping("/test")
    // public String test() {
    //     return "test";
    // }


    // @GetMapping("/userlist")
    // public String listUser(){
    // return "pages/user/list_user";
    // }

}
