package my.app.bookmyvenue.Controller;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.paytm.pg.merchant.PaytmChecksum;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import my.app.bookmyvenue.Model.Booking;
import my.app.bookmyvenue.Model.PaytmDetailPojo;
import my.app.bookmyvenue.Model.Users;
import my.app.bookmyvenue.Model.Venue;
import my.app.bookmyvenue.Service.BookingService;
import my.app.bookmyvenue.Service.UserService;
import my.app.bookmyvenue.Service.VenueService;


@Tag(name = "User controller", description = "contains all methods to controller opertaion related to Users")
@Controller
// @RequestMapping("/user")
public class UserController {

        @Autowired
        private UserService userService;
        @Autowired
        private VenueService venueService;
        @Autowired
        private BookingService bookingService;
        @Autowired
        private HttpSession session;
        @Autowired
        private PaytmDetailPojo paytmDetailPojo;
        
        @Autowired
        private Environment env;


        // register user
        @PostMapping("/reg")
        public String register(@ModelAttribute Users user, Model model){
            // System.out.println(user.getUserId() +user.getFirstName()+user.getLastName()+user.getEmailId()+user.getPassword()+user.getPhoneNo());
            String msg = "";
            boolean flag = false;
            // System.out.println(user.getEmailId());
            Users exitingId = userService.getUsersByEmailId(user.getEmailId());
            // System.out.println(exitingId.getEmailId());
            if(exitingId != null){
                
                if(exitingId.getEmailId().equals(user.getEmailId())){
                    
                    model.addAttribute("msg", "email already registered");
                    return "pages/user/register";
                }
                else{

                }    
            }
            else{
                if(user.getPassword().equals(user.getConfirmPassword()) ){
                    msg = "account created...Please login";
                    flag = true;
                }
                else{
                    msg = "confirm password doesn't match";
                }
            }
            
            if(flag){
                // BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                // String encodedPassword = passwordEncoder.encode(user.getPassword());
                // user.setPassword(encodedPassword);
                user.setRole("ROLE_USER");
                user.setStatus("No");
                userService.saveUsers(user);
                model.addAttribute("msg", msg);
                // return "pages/user/login";
                return "signin";
            }
            else{
                model.addAttribute("msg", msg);
                // return "pages/user/register";
                return "signup";
            }
        }

        // login user
        @PostMapping("/login")
        public String login(@ModelAttribute Users user, Model model){
            System.out.println(user.getEmailId());
            System.out.println(user.getPassword());
            boolean flag = false;
            String msg="";
            Users userData = userService.getUsersByEmailId(user.getEmailId());
            System.out.println(userData);
            if(userData==null){
                model.addAttribute("msg", " User not exist");
                return "signin";
            }
            else if(userData.getRole().equals("ROLE_ADMIN")){
                model.addAttribute("msg", "Login with correct user account");
                return "signin";
            }
            else if(userData.getStatus().equals("Yes")){
                model.addAttribute("msg", "Block!! Authentication fails");
                return "signin";
            }
            else{

                if(userData.getEmailId().equals(user.getEmailId())){
                    
                    if(user.getPassword().equals("123")){
                        // msg = "login success";
                        System.out.println("login success");
                        session.setAttribute("userId", userData.getUserId());
                        session.setAttribute("name", userData.getFirstName()+" "+userData.getLastName());

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
                List<Venue> bestVenue = venueService.getBestVenue();
                for(Venue venue : bestVenue){
                    String imagePath = "img" + File.separator + venue.getThumbnail();
                    venue.setThumbnail(imagePath);

                }
                model.addAttribute("bestvenues", bestVenue);
                bestVenue.forEach(e->System.out.println(e));

                model.addAttribute("title", "Home");
                // model.addAttribute("msg", msg);
                return "index";
            }
            else{
                model.addAttribute("msg", msg);
                return "signin";
            }
            
        }
       
    // make user logout and delete session    
    @GetMapping("/logout")    
    public String logout(){
        session.removeAttribute("userId");
        session.removeAttribute("name");
        return "signin";
    }    

    // take booking details from users
    @GetMapping("/bookingdetail")    
    public String bookingDetails(@RequestParam("venueId") long venueId, Model model ){
        System.out.println(venueId);
        Venue venue = venueService.getVenueById(venueId);
        System.out.println(session.getAttribute("userId"));
        if(session.getAttribute("userId")==null){
            return "signin";
        }
        else{
            Users user = userService.getUserById((long)session.getAttribute("userId"));
            model.addAttribute("user", user);
            System.out.println(user);
        }

        System.out.println(venue);
        model.addAttribute("venueId", venueId);
        model.addAttribute("venue", venue);
        model.addAttribute("title", "booking details");

        return "booking-details";
    }    


    // venue booking review
    // url : https://localhost:8080/venueBooking?id=value
    @PostMapping("/reviewdetails")
    public String reviewBooking(ModelMap model,
                                @RequestParam("venueId") long venueId, 
                                @ModelAttribute Booking booking,
                                @ModelAttribute Users user) {
                          
        Venue venue = venueService.getVenueById(venueId);
        Users userData = userService.getUsersByEmailId(user.getEmailId());
        // System.out.println("this testing data");
        // System.out.println(venue);
        // System.out.println(booking);
        // System.out.println(user);
        booking.setUserId(userData.getUserId());
        // System.out.println(booking.getEventType()); 
        // generating order ID
        // String orderID = "RFDA"+(long)Math.floor(Math.random()*1000000);  
        // booking.setOrderId(orderId);                         
        // bookingService.save(booking);

        long totalPrice = 0;
        if(booking.getMealType().equals("Veg")){
            totalPrice = venue.getVegPrice() + venue.getVenuePrice();
        }
        else{
            totalPrice = venue.getNonvegPrice() + venue.getVenuePrice();
        }
        // System.out.println(booking);
        model.addAttribute("venueName", venue.getVenueName());
        model.addAttribute("user", userData);
        model.addAttribute("venue", venue);
        session.setAttribute("booking", booking);
        model.addAttribute("Booking", booking);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("title", venue.getVenueName() + " Booking");
        return "review-booking";
    }

    // for testing
    @GetMapping("/paymenttest")
    public String payment(@ModelAttribute Booking booking,
                          @RequestParam("userId") long userid, 
                          @RequestParam("venueId") long venueid,
                          Model model) {
        // generating order ID
        // String orderID = "RFD"+(long)Math.floor(Math.random()*10000000);
        // get all venue Id
        venueService.getVenueById(userid);
        // get user details
        userService.getUserById(venueid);
                            
        // booking.set;

        // Random rand = new Random();
        // String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        // int rand_int1 = rand.nextInt(1000000);

        return "user/orderdetails";
    }

    // payment API (Paytm)
    @PostMapping(value = "/payment")
    public ModelAndView getRedirect(ModelMap model,
                                    @RequestParam(name = "CUST_ID") String customerId,
                                    @RequestParam(name = "TXN_AMOUNT") String transactionAmount,
                                    @RequestParam("venueId") String venueId) throws Exception {
        // Booking booking = (Booking) model.getAttribute("Booking");  
        String orderId = "RFD"+(long)Math.floor(Math.random()*10000000);
        
        Booking booking = (Booking) session.getAttribute("booking");
        booking.setOrderId(orderId);

        session.setAttribute("booking", booking);

        bookingService.save(booking);
        // System.out.println(customerId);        
        // System.out.println(transactionAmount);        
        // System.out.println(booking);        
        // System.out.println(venueId); 
        // System.out.println(orderId);               

        ModelAndView modelAndView = new ModelAndView("redirect:" + paytmDetailPojo.getPaytmUrl());
        TreeMap<String, String> parameters = new TreeMap<>();
        paytmDetailPojo.getDetails().forEach((k, v) -> parameters.put(k, v));
        parameters.put("MOBILE_NO", env.getProperty("paytm.mobile"));
        parameters.put("EMAIL", env.getProperty("paytm.email"));
        parameters.put("ORDER_ID", orderId);
        parameters.put("TXN_AMOUNT", transactionAmount);
        parameters.put("CUST_ID", customerId);
        String checkSum = getCheckSum(parameters);
        parameters.put("CHECKSUMHASH", checkSum);
        modelAndView.addAllObjects(parameters);

        // System.out.println(date);
        // System.out.println(time);
        
        return modelAndView;
    }

    // return receipt
    @PostMapping(value = "/pgresponse")
    public String getResponseRedirect(HttpServletRequest request, Model model) {

        Map<String, String[]> mapData = request.getParameterMap();
        TreeMap<String, String> parameters = new TreeMap<String, String>();
        String paytmChecksum = "";
        for (Entry<String, String[]> requestParamsEntry : mapData.entrySet()) {
            if ("CHECKSUMHASH".equalsIgnoreCase(requestParamsEntry.getKey())) {
                paytmChecksum = requestParamsEntry.getValue()[0];
            } else {
                parameters.put(requestParamsEntry.getKey(), requestParamsEntry.getValue()[0]);
            }
        }
        String result;

        boolean isValideChecksum = false;
        System.out.println("RESULT : " + parameters.toString());
        
        try {
            isValideChecksum = validateCheckSum(parameters, paytmChecksum);
            if (isValideChecksum && parameters.containsKey("RESPCODE")) {
                if (parameters.get("RESPCODE").equals("01")) {
                    result = "Payment Successful";
                } else {
                    result = "Payment Failed";
                }
            } else {
                result = "Checksum mismatched";
            }
        } catch (Exception e) {
            result = e.toString();
        }

        String receipt = "RFD"+(long)Math.floor(Math.random()*10000000);

        Booking bookingDetails = bookingService.getBookingByOrderId(parameters.get("ORDERID"));
        // String orderId = parameters.get("ORDERID");
        // String status = parameters.get("STATUS");
        // String txnid = parameters.get("TXNID");
        // String txndate = parameters.get("TXNDATE");
        bookingDetails.setOrderId(parameters.get("ORDERID"));
        bookingDetails.setStatus(parameters.get("STATUS"));
        bookingDetails.setTransactionId(parameters.get("TXNID"));
        bookingDetails.setTransactionDate(parameters.get("TXNDATE"));
        System.out.println(bookingDetails);
        bookingService.save(bookingDetails);


        model.addAttribute("result", result);
        model.addAttribute("receipt", receipt);
        parameters.remove("CHECKSUMHASH");
        model.addAttribute("parameters", parameters);

        System.out.println(parameters.get("TXNDATE"));
        System.out.println(parameters.get("TXNID"));
        System.out.println(parameters.get("STATUS"));
        return "user/report";
    }
    // Parameter : {BANKNAME=State Bank of India,
    // BANKTXNID=17331146788,
    // CURRENCY=INR,
    // GATEWAYNAME=SBI,
    // MID=PxROjD79144821616294,
    // ORDERID=132,
    // PAYMENTMODE=NB,
    // RESPCODE=01,
    // RESPMSG=Txn Success,
    // STATUS=TXN_SUCCESS,
    // TXNAMOUNT=100.00,
    // TXNDATE=2022-09-25 13:47:13.0,
    // TXNID=20220925111212800110168889004078291}

    // validate the checksum data to varify
    private boolean validateCheckSum(TreeMap<String, String> parameters, String paytmChecksum) throws Exception {
        return PaytmChecksum.verifySignature(parameters,
                paytmDetailPojo.getMerchantKey(), paytmChecksum);
    }

    // get chechsum for payment authentication
    private String getCheckSum(TreeMap<String, String> parameters) throws Exception {
        return PaytmChecksum.generateSignature(parameters, paytmDetailPojo.getMerchantKey());
    }

    // return reset page
    @GetMapping("/reset")
    public String reset(Model model){
        if(session.getAttribute("userId")==null){
            return "signin";
        }
        else{
            Users user = userService.getUserById((long)session.getAttribute("userId"));
            model.addAttribute("user", user);
            System.out.println(user);
        }
        model.addAttribute("title", "Reset Password");
        return "reset-password";

    }

    // return booking history page with data
    @GetMapping("/bookinghistory")
    public String bookingHistory(Model model){
        if(session.getAttribute("userId")==null){
            return "signin";
        }
        else{
            Users user = userService.getUserById((long)session.getAttribute("userId"));
            model.addAttribute("user", user);
            System.out.println(user);
        }

        // System.out.println(session.getAttribute("userId"));
        long userId = (long) session.getAttribute("userId");
        List<Booking> bookinghistory = bookingService.getBookingByUserId(userId);
        // System.out.println(bookinghistory);
        List<String> myvenue = new ArrayList<String>();
        bookinghistory.forEach(e->{
            // System.out.println(e.getVenueId());
            Venue venue = venueService.getVenueById(e.getVenueId());
            myvenue.add(venue.getVenueName());
        });
        model.addAttribute("venuename", myvenue);
        model.addAttribute("booking", bookinghistory);
        model.addAttribute("title", "Booking History");
        return "booking-history";

    }

    // @Autowired
    // private BCryptPasswordEncoder bCryptPasswordEncoder;

    // fucntion to handle reset password with server side validation
    @PostMapping("/passwordreset")
    public String reset(@RequestParam("opassword") String opassword,
                        @RequestParam("npassword") String npassword,
                        @RequestParam("cpassword") String cpassword, 
                        Model model){
        Users myuser = userService.getUserById((long) session.getAttribute("userId"));
        System.out.println(opassword);
        System.out.println(npassword);
        System.out.println(cpassword);
        String msg="";

        if(opassword.equals(myuser.getPassword())){
            if(npassword.equals(cpassword)){
                // BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                // String encodedPassword = passwordEncoder.encode(npassword);
                // myuser.setPassword(encodedPassword);
                // System.out.println(myuser.getPassword());
                myuser.setPassword(cpassword);
                userService.saveUsers(myuser);
                msg = "password reset successfully.";
            }
            else{
                msg = "confirm password mismatch..";
            }
        }
        else{
            msg = "previous password is wrong";
        }   
        
        model.addAttribute("msg", msg);
        model.addAttribute("title", "Reset Password");
        return "reset-password";
    }


    // Learning stage
    // ------------------------------------------------------>

        // edit user
        @GetMapping("/userEdit/{id}")
        public String edit(@PathVariable long id, Model model){
            Users user = userService.getUserById(id);
            model.addAttribute("user", user);
            // session.setAttribute("user", user);
            return "../admin/list-user";
        }
    
        // update user
        @GetMapping("/userUpdate")
        public String updateUser(@ModelAttribute Users user){
            // userService.updateUser(user);
            System.out.println(user);
            session.setAttribute("msg", "Users data update successfully..");
            return "../admin/list-user";
        }
    

        




















        // testing
        // @GetMapping("/userlist")
        // public String listUser(Model model){
        //     List<Users> users = userService.getUsers();
        //     model.addAttribute("users", users);
        //     return "/pages/user/list_user";
        // }


        // list all user
        // @GetMapping("/userlist")
        // public String getUser(Model model){    
        //     return findPaginated(0, model);
        // }
    
        // list all user with pages
        // @GetMapping("user/page/{pageno}")
        // public String findPaginated(@PathVariable int pageno, Model model){
        //     Page<Users> userList = userService.getUserByPaginate(pageno, 5);
        //     System.out.println(userList);
        //     model.addAttribute("users", userList);
        //     model.addAttribute("currentPage", pageno);
        //     model.addAttribute("totalPages", userList.getTotalPages());
        //     model.addAttribute("totalItems", userList.getTotalElements());
        //     return "pages/user/list_user";
        // }


        // testing or learning
        @Operation(summary = "check")
        @ApiResponse(responseCode = "200", description = " request has succeeded")
        @ResponseStatus(HttpStatus.OK)
        @GetMapping("/check")
        public String check(){
            // userService.getUsersByName("xyzHero");
            // userService.getUsersByEmailId("xyzmail@mail.com");
            // userService.getUserByName("Hero", "Hamada");
            // userService.searchWithName("H%");
            // userService.getAllUsers();
            userService.getAllPhone();
            return "home";
        }
}
