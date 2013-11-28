package ee.ut.math.tvt.salessystem.domain.exception;

/**
 * Thrown when a stock item quantity is less than the requested quantity.
 */
public class OverLimitException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs new <code>OverLimitException</code>.
	 */
	public OverLimitException() {
		super();
	}

	/**
	 * Constructs new <code>OverLimitException</code> with with the specified
	 * detail message.
	 * 
	 * @param message
	 *            the detail message.
	 */
	public OverLimitException(final String message) {
		super(message);
	}
}