package my.app.bookmyvenue.Exceptions;

public class ImageNotFoundException extends Exception{
    String msg;
    public ImageNotFoundException(String msg){
        this.msg = msg;
    }
    
    @Override
    public String toString() {
        return "ImageNotFoundException [msg=" + msg + "]";
    }
    
}
