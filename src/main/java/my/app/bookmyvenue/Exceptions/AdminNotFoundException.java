package my.app.bookmyvenue.Exceptions;


public class AdminNotFoundException extends Exception{
    String msg;

    public AdminNotFoundException(String msg){
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "AdminNotFoundException [msg=" + msg + "]";
    }

    

}
