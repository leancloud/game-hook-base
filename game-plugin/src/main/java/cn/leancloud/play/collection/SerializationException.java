package cn.leancloud.play.collection;

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
