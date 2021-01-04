package src;

public class NullOrEmptyArgumentException extends IllegalArgumentException {
    public final String paramString;

    public NullOrEmptyArgumentException(String paramString) {
        super("The string of parameter is null or empty");
        this.paramString = paramString;
    }
}
