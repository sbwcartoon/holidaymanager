package toy.test.holidaymanager.holiday.adapter.out.nager.exception;

public class NagerServerException extends RuntimeException {
    public NagerServerException() {
        super("Nager Holiday API Fetch Error");
    }

    public NagerServerException(final String message) {
        super(message);
    }

    public NagerServerException(final Throwable e) {
        super(e);
    }

    public NagerServerException(final String message, final Throwable e) {
        super(message, e);
    }
}
