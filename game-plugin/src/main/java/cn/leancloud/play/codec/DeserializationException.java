package cn.leancloud.play.codec;

public class DeserializationException extends RuntimeException {
    public DeserializationException(){
        super();
    }

    public DeserializationException(String message){
        super(message);
    }

    public DeserializationException(String message, Throwable cause){
        super(message, cause);
    }
}