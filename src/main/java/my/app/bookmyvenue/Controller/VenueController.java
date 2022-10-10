package my.app.bookmyvenue.Controller;


import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import my.app.bookmyvenue.Model.Venue;
import my.app.bookmyvenue.Service.VenueService;


// @RequestMapping("/venue")
@Controller
public class VenueController {

    @Autowired
    private VenueService service;
    
    @Autowired
    private HttpSession session;




    // edit venue
    @PostMapping("/venue/edit/{id}")
    public String edit(@PathVariable long id, Model model){
        Venue venue = service.getVenueById(id);
        model.addAttribute("venue", venue);
        return "admin/add-venue";
    }

    // update venue
    @PutMapping("/venueUpdate")
    public String updateVenue(@ModelAttribute Venue venue){
        service.updateVenue(venue);
        session.setAttribute("msg", "Venue data update successfully..");
        return "admin/add-venue";
    }

    // delete venue
    @DeleteMapping("/venue/delete/{id}")
    public String deleteVenue(@PathVariable long id){
        service.deleteVenue(id);
        session.setAttribute("msg", "Venue Data Delete Successfully...");
        return "admin/add-venue";
    }

    // list all venues
    @GetMapping("/listVenuetest")
    public String getVenue(Model model){    
        return findPaginated(0, model);
    }

    // list all venues with pages
    @GetMapping("/page/{pageno}")
    public String findPaginated(@PathVariable int pageno, Model model){
        Page<Venue> venueList = service.getVenueByPaginate(pageno, 2);
        model.addAttribute("venue", venueList);
        model.addAttribute("currentPage", pageno);
        model.addAttribute("totalPages", venueList.getTotalPages());
        model.addAttribute("totalItem", venueList.getTotalElements());
        return "admin/list-venue";
    }













    // search venue
    @GetMapping("/search/{str}")
    public String search(@PathVariable String str, HttpSession session, Model m){
        System.out.println(str);
        session.setAttribute("msg", "HttpSessions..");
        m.addAttribute("msg", "My model..");
        
     
        return "<h1>"+str+"</h1><p>"+session.getAttribute("msg")+"</p><p>"+m.getAttribute("msg")+"</p>";
    }
    
}
