package my.app.bookmyvenue.Exceptions;

public class UserNotFoundException extends Exception{
    String msg;

    public UserNotFoundException(String msg){
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "UserNotFoundException [msg=" + msg + "]";
    }

    
}
