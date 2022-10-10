package my.app.bookmyvenue.Exceptions;

public class ImageNameNotFoundException extends Exception{
    String msg;

    public ImageNameNotFoundException(String msg){
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ImageNameNotFoundException [msg=" + msg + "]";
    }

    
}
