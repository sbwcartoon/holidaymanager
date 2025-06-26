package toy.test.holidaymanager.holiday.adapter.out.nager.exception;

public class NagerClientException extends RuntimeException {
    public NagerClientException() {
        super("Client error");
    }

    public NagerClientException(final String message) {
        super(message);
    }

    public NagerClientException(final Throwable e) {
        super(e);
    }

    public NagerClientException(final String message, final Throwable e) {
        super(message, e);
    }
}
