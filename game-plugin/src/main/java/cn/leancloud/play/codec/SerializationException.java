package cn.leancloud.play.codec;

public class SerializationException extends RuntimeException {
    public SerializationException(){
        super();
    }

    public SerializationException(String message){
        super(message);
    }

    public SerializationException(String message, Throwable cause){
        super(message, cause);
    }
}
