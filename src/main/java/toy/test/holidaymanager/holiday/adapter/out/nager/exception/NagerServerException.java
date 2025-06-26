package toy.test.holidaymanager.holiday.adapter.out.nager.exception;

public class NagerServerException extends RuntimeException {
    public NagerServerException() {
        super("Nager Holiday API Fetch Error");
    }

    public NagerServerException(String message) {
        super(message);
    }

    public NagerServerException(Throwable e) {
        super(e);
    }

    public NagerServerException(String message, Throwable e) {
        super(message, e);
    }
}
