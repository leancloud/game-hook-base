package cn.leancloud.play.utils;

public class CastTypeException extends RuntimeException {
    public CastTypeException(){
        super();
    }

    public CastTypeException(String message){
        super(message);
    }

    public CastTypeException(String message, Throwable cause){
        super(message, cause);
    }
}
