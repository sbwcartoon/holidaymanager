package toy.test.holidaymanager.holiday.adapter.out.nager.exception;

public class NagerClientException extends RuntimeException {
    public NagerClientException() {
        super("Client error");
    }

    public NagerClientException(String message) {
        super(message);
    }

    public NagerClientException(Throwable e) {
        super(e);
    }

    public NagerClientException(String message, Throwable e) {
        super(message, e);
    }
}
