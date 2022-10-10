package my.app.bookmyvenue.Controller;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import my.app.bookmyvenue.Exceptions.ImageNameNotFoundException;
import my.app.bookmyvenue.Exceptions.ImageNotFoundException;
import my.app.bookmyvenue.Exceptions.UserNotFoundException;
import my.app.bookmyvenue.Exceptions.VenueNotFoundException;
import my.app.bookmyvenue.Model.Booking;
import my.app.bookmyvenue.Model.Users;
import my.app.bookmyvenue.Model.Venue;
import my.app.bookmyvenue.Service.BookingService;
import my.app.bookmyvenue.Service.UserService;
import my.app.bookmyvenue.Service.VenueService;

@Tag(name = "Admin controller", description = "contains all methods to controller opertaion related to Admin")
@Controller
// @RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private VenueService venueService;
    @Autowired
    private BookingService bookingService;
    @Autowired
    private HttpSession session;
    @Value("${project.image}")
	private String path;

    // login admin
    @PostMapping("/alogin")
    public String login(@ModelAttribute Users user, Model model){
        System.out.println(user.getEmailId());
        System.out.println(user.getPassword());
        boolean flag = false;
        String msg="";
        Users userData = userService.getUsersByEmailId(user.getEmailId());
        System.out.println(userData);


        if(userData==null){
            model.addAttribute("msg", "id not exist");
            return "admin/sign-in";
        }
        else{
            if(!userData.getRole().equals("ROLE_ADMIN")){
                model.addAttribute("msg", "Login with Admin account");
                return "admin/sign-in";
            }
            if(userData.getEmailId().equals(user.getEmailId())){
                
                if(user.getPassword().equals(userData.getPassword())){
                    // msg = "login success";
                    System.out.println("login success");
                    session.setAttribute("userid", userData.getUserId());
                    session.setAttribute("name", userData.getFirstName()+" "+userData.getLastName());
                    session.setAttribute("firstname", userData.getFirstName());
                    session.setAttribute("username", userData.getEmailId());

                    flag = true;
                }
                else{
                    msg = "password incorrect";
                }
            }
            else{
                msg = "wrong email -"+user.getEmailId();
                System.out.println("wrong email");
            }
        }     
        if(flag){
            // model.addAttribute("msg", msg);
 
            dashboard(model, user);
            return "admin/dashboard";
        }
        else{
            model.addAttribute("msg", msg);
            return "admin/sign-in";
        }
        
    }
    // logout
    @GetMapping("/alogout")
    public String logout(){
        session.removeAttribute("userid");
        session.removeAttribute("name");
        session.removeAttribute("firstname");
        session.removeAttribute("username");
        
        return "admin/sign-in";
    }

    // add new venue
    @GetMapping("/addVenue")
    public String addVenue(Model model){
        if(session.getAttribute("userid")==null){
            model.addAttribute("msg", " Your get logout out. plese login again..");
            return "admin/sign-in";
        }
        model.addAttribute("title", "Add Venue");
        return "admin/add-venue";
    }
    
    // admin side dashboard
    @GetMapping("/admindash")
    public String dashboard(Model model, Users user){
        // checkLogin();
        if(session.getAttribute("userid")==null){
            model.addAttribute("msg", " Your get logout out. plese login again..");
            return "admin/sign-in";
        }
        // else{
        //     Users myuser = userService.getUserById((long)session.getAttribute("userid"));
        //     model.addAttribute("user", user);
        //     System.out.println(user);
        // }
        long adminCount = userService.countOfAdmins();
        long userCount = userService.countOfUsers();
        long venueCount = venueService.countOfVenues();
        long bookingCount = bookingService.countOfBooking();
        // if(bookingCount == null) bookingCount = 0;
        model.addAttribute("adminCount", adminCount);
        model.addAttribute("userCount", userCount);
        model.addAttribute("venueCount", venueCount);
        model.addAttribute("bookingCount", bookingCount);
        model.addAttribute("title", "admin dashboard");
        
        System.out.println(session.getAttribute("username"));
        // System.out.println(user.getEmailId());
        // String username = user.getEmailId();
        // sessionCheck("admin/dashboard");
        // return "admin/dashboard";
        // if(session.getAttribute("username").equals(username)){
        //     return "admin/dashboard"; 
        // }
        // else{
        //     return "admin/sign-in";
        // }

        return "admin/dashboard";
    }

    // add venue
    @Operation(summary = "add venue")
    @PostMapping("/addVenueDetails")
    public String addVenue(@ModelAttribute Venue venue, @RequestParam("image") MultipartFile image, Model model) throws IOException, ImageNameNotFoundException{
        if(session.getAttribute("userid")==null){
            model.addAttribute("msg", " Your get logout out. plese login again..");
            return "admin/sign-in";
        }
        
        // System.out.println(file.getSize()/1024);
        // System.out.println(file.getOriginalFilename());
        // System.out.println(file.getBytes());
        // System.out.println(file.getResource());

        // file uploading process and save
        if(image.isEmpty()){
            try {
                throw new ImageNotFoundException("Image not found");
            } catch (ImageNotFoundException e) {
                System.out.println(e);
            }

            System.out.println("file is empty...");
            session.setAttribute("msg", "file is empty...");
            return "admin/add-venue";
        }
        else{
            // fetch file name
            String name = image.getOriginalFilename();     
            // generate randomname for file
            String randomID = UUID.randomUUID().toString();

            if(name==null){
                throw new ImageNameNotFoundException("name error");
            }else{

                String fileName = randomID.concat(name.substring(name.lastIndexOf(".")));
                System.out.println("filename : "+fileName);
                // create folder if not created
                String imagePath = "src"+File.separator+"main"+File.separator+"resources"+File.separator+"static"+File.separator+"img";
                File folder=new File(imagePath);
                // System.out.println("imagePath : "+imagePath);
                if(!folder.exists()){
                    folder.mkdir();
                }
                // create full path
                String filePath=imagePath+ File.separator +fileName;
                // System.out.println("filePath : "+filePath);
    
                // copy file
                Files.copy(image.getInputStream(), Path.of(filePath), StandardCopyOption.REPLACE_EXISTING);        
                venue.setThumbnail(fileName);
            }
        }

        // System.out.println(venue);
        venue.setStatus("No");
        venue.setMap("17.3356245,78.4986046");
        venue.setRating(4.0f);
        venueService.addVenue(venue);
        session.setAttribute("msg", "Venue added sucessfully...");
        model.addAttribute("title", "Add Venue");
        return "admin/add-venue";
    }

    // list all venues
    @GetMapping("/listVenue")
    public String getVenue(Model model) throws VenueNotFoundException{    
        if(session.getAttribute("userid")==null){
            model.addAttribute("msg", " Your get logout out. plese login again..");
            return "admin/sign-in";
        }
        List<Venue> venues = venueService.getAllVenue();
        if(venues == null){
            throw new VenueNotFoundException("venue not found");
        } 
            
        model.addAttribute("venues", venues);
        model.addAttribute("title", "List Venue");
        return "admin/list-venue";
    }

    @GetMapping("/bookingdetails")
    public String getBookingDetails(Model model){
        if(session.getAttribute("userid")==null){
            model.addAttribute("msg", " Your get logout out. plese login again..");
            return "admin/sign-in";
        }
        List<Booking> bookingDetails = bookingService.getBookingDetails();
        System.out.println(bookingDetails);
        model.addAttribute("booking", bookingDetails);
        model.addAttribute("title", "Booking Details");
        
        return "admin/booking-details";
    }

    @GetMapping("/listUser")
    public String listUser(Model model) throws UserNotFoundException{
        if(session.getAttribute("userid")==null){
            model.addAttribute("msg", " Your get logout out. plese login again..");
            return "admin/sign-in";
        }
        // List<Users> users = userService.getUsers();
        List<Users> users = userService.getUsersRole();
        if(users == null){
            throw new UserNotFoundException("User not found");
        }
        model.addAttribute("users", users);
        model.addAttribute("title", "Manage User");
        return "admin/list-user";
    }

    @GetMapping("/listAdmin")
    public String listAdmin(Model model) throws UserNotFoundException{
        if(session.getAttribute("userid")==null){
            model.addAttribute("msg", " Your get logout out. plese login again..");
            return "admin/sign-in";
        }
        // List<Users> users = userService.getUsers();
        List<Users> users = userService.getAdminRole();
        if(users == null){
            throw new UserNotFoundException("Admin not found");
        }
        model.addAttribute("users", users);
        model.addAttribute("title", "Manage Admin");
        return "admin/list-admin";
    }

    @GetMapping("/addGallery")
    public String addGallery(Model model){
        if(session.getAttribute("userid")==null){
            model.addAttribute("msg", " Your get logout out. plese login again..");
            return "admin/sign-in";
        }
        model.addAttribute("title", "Add Gallery");
        return "admin/gallery";
    }

    @GetMapping("/myprofile")
    public String myProfile(Model model){
        if(session.getAttribute("userid")==null){
            model.addAttribute("msg", " Your get logout out. plese login again..");
            return "admin/sign-in";
        }
        model.addAttribute("title", "Password reset");
        return "admin/password-reset";
    }

    @GetMapping("/addAdmin")
    public String addAdmin(Model model){
        if(session.getAttribute("userid")==null){
            model.addAttribute("msg", " Your get logout out. plese login again..");
            return "admin/sign-in";
        }
        model.addAttribute("title", "admin added");
        return "admin/add-admin";
    }

    @PostMapping("/reset")
    public String reset(@RequestParam("opassword") String opassword,
                        @RequestParam("npassword") String npassword,
                        @RequestParam("cpassword") String cpassword, 
                        Model model){
       if(session.getAttribute("userid")==null){
            model.addAttribute("msg", " Your get logout out. plese login again..");
            return "admin/sign-in";
        }        
        // String attribute = (String) session.getAttribute("email");
        Users myuser = userService.getUsersByEmailId((String) session.getAttribute("username"));
        System.out.println(opassword);
        System.out.println(npassword);
        System.out.println(cpassword);
        String msg="";

        if(opassword.equals(myuser.getPassword())){
            if(npassword.equals(cpassword)){
                myuser.setPassword(cpassword);
                msg = "password reset successfully.";
            }
            else{
                msg = "confirm password mismatch..";
            }
        }
        else{
            msg = "previous password is wrong";
        }   
        userService.saveUsers(myuser);
        session.setAttribute("msg", msg);
        model.addAttribute("title", "Reset Password");
        return "admin/password-reset";
    }

    @PostMapping("/addAccount")
    public String addAdminAccount(@ModelAttribute Users user,
                                  @RequestParam("cpassword") String cpassword, 
                                  Model model){
                                      
        if(session.getAttribute("userid")==null){
            model.addAttribute("msg", " Your get logout out. plese login again..");
            return "admin/sign-in";
        }                              
        // System.out.println(user);                                    
        // System.out.println(cpassword);
        String msg = "";                            
        Users myuser = userService.getUsersByEmailId(user.getEmailId());

        if(myuser != null){
            if(!myuser.getEmailId().equals(user.getEmailId())){
                if(cpassword.equals(user.getPassword())){
                    user.setRole("ROLE_ADMIN");
                    user.setStatus("No");
                    userService.saveUsers(user);
                    msg = "Account added successfully..";
                }
                else{
                    msg = "confirm password missmatch..";
                }
            }
            else{
                msg = "email already registered";
            }
        }
        else{
            if(cpassword.equals(user.getPassword())){
                user.setRole("ROLE_ADMIN");
                userService.saveUsers(user);
                msg = "Account added successfully..";
            }
            else{
                msg = "confirm password missmatch..";
            } 
        }    
        System.out.println(user);
        session.setAttribute("msg", msg);
        model.addAttribute("title", "Admin Registration");
        return "admin/add-admin";
    }

    // options inside pages

        // delete user
        @GetMapping("/userdelete")
        public String deleteUser(@RequestParam long id, Model model){
            if(session.getAttribute("userid")==null){
                model.addAttribute("msg", " Your get logout out. plese login again..");
                return "admin/sign-in";
            }
            userService.deleteUserById(id);
            System.out.println("running deleting function");
            session.setAttribute("msg", "Users Data Delete Successfully...");
            
            model.addAttribute("users", userService.getUsersRole());
            model.addAttribute("title", "User acount list");
            return "admin/list-user";
        }
        // block user
        @GetMapping("/blockUser")
        public String blockUser(@RequestParam long id, Model model){
            if(session.getAttribute("userid")==null){
                model.addAttribute("msg", " Your get logout out. plese login again..");
                return "admin/sign-in";
            }
            Users user = userService.getUserById(id);
            user.setStatus("Yes");
            userService.saveUsers(user);
            session.setAttribute("msg", user.getFirstName()+" is blocked..");
            
            model.addAttribute("users", userService.getUsersRole());
            model.addAttribute("title", "User acount list");
            return "admin/list-user";
        }
        // unblock user
        @GetMapping("/unblockUser")
        public String unblockUser(@RequestParam long id, Model model){

            Users user = userService.getUserById(id);
            user.setStatus("No");
            userService.saveUsers(user);
            session.setAttribute("msg", user.getFirstName()+ " is unblocked..");

            model.addAttribute("users", userService.getUsersRole());
            model.addAttribute("title", "User list");
            return "admin/list-user";
        }
        
        // delete admin
        @GetMapping("/admindelete")
        public String deleteAdmin(@RequestParam long id, Model model){
            
            Users user = userService.getUserById(id);
            String msg = "";
            if(user.getRole().equals("ROLE_ADMIN")){
                if(user.getUserId() != 8){
                    userService.deleteUserById(id);
                    msg = user.getFirstName() +" is deleted";
                }
                else{
                    msg = "Master Admin. operation not allowed";
                }
            }
            else{
                msg = "cannot delete.";
            }
            // System.out.println("running deleting function");
            session.setAttribute("msg", msg);
            
            model.addAttribute("users", userService.getAdminRole());
            model.addAttribute("title", "Admin acount list");
            return "admin/list-admin";
        }        

        // edit venue
        @GetMapping("/venueEdit")
        public String updateVenue(@RequestParam long id, Model model){
            Venue venue = venueService.getVenueById(id);
            model.addAttribute("venue", venue);
            model.addAttribute("title", "Edit Venue");
            return "admin/edit-venue";
        }

        // delete user
        @GetMapping("/venueDelete")
        public String deleteVenue(@RequestParam long id, Model model){
            venueService.deleteVenue(id);
            session.setAttribute("msg", "Venue Data Delete Successfully...");
            
            model.addAttribute("title", "Venue list");

            // getVenue(model);
            return "admin/list-venue";
        }
    
    public String sessionCheck(String page){

        System.out.println(session.getAttribute("username"));
        if(session.getAttribute("username")!=null){
            return "admin/sign-in";
        }
        else{
            return page;
        }

    }

    public String checkLogin(){
        if(session.getAttribute("username")==null){
            System.out.println("null hai");
            return "sign-in";
        }
        else{
            System.out.println("null nahi hai");
            return "forward:/admindash";
        }
    }


        

}
