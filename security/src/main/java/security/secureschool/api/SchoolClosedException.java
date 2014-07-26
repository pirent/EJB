package security.secureschool.api;

import javax.ejb.ApplicationException;
import javax.ejb.EJBAccessException;

import security.secureschool.impl.Roles;

/**
 * Throws when a user in role other than {@link Roles#ADMIN} attempts to open
 * the front door to school while it's closed.
 * 
 * @author pirent
 *
 */
@ApplicationException(rollback = true)
// So this isn't wrapped in EJBException
public class SchoolClosedException extends EJBAccessException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private SchoolClosedException(final String message) {
		super(message);
	}

	/**
	 * Constructs a new exception with a specified, required message.
	 * 
	 * @param message
	 * @return
	 * @throws IllegalArgumentException
	 *             if message is not specified.
	 */
	public static SchoolClosedException newInstance(String message)
			throws IllegalArgumentException {
		if (message == null) {
			throw new IllegalArgumentException("Message must be specified");
		}
		return new SchoolClosedException(message);
	}
}
