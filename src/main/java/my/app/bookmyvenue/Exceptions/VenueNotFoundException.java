package my.app.bookmyvenue.Exceptions;

public class VenueNotFoundException extends Exception{
    String msg;

    public VenueNotFoundException(String msg){
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "VenueNotFoundException [msg=" + msg + "]";
    }
    
}
