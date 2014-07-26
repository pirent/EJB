package security.secureschool.api;

import security.secureschool.impl.Roles;

/**
 * Represent a school holding doors which may be opened by various users. Using
 * EJB security model, access to open a particular door may be locked to certain
 * users.
 * 
 * @author pirent
 *
 */
public interface SecureSchoolLocalBusiness {

	/**
	 * Opens the school for business. At this point, front door will be unlocked
	 * for all. This method may only be called by a user in {@link Roles#ADMIN}
	 */
	void open();

	/**
	 * Closes the school for business. At this point, front door will be locked
	 * for all but users in {@link Roles#ADMIN}. This method may only be called
	 * by admins.
	 */
	void close();

	/**
	 * Opens the front door. While school is open, any authenticated user may
	 * open the door, else only the {@link Roles#ADMIN} may open.
	 * 
	 * @throws SchoolClosedException
	 *             If the current user is not in {@link Roles#ADMIN} and is
	 *             attempting to open the door while
	 *             {@link SecureSchoolLocalBusiness#isOpen()} is false.
	 */
	void openFrontDoor() throws SchoolClosedException;

	/**
	 * Opens the service door. User in {@link Roles#STUDENT} role may not open
	 * this door, but {@link Roles#JANITOR} and {@link Roles#ADMIN} may.
	 */
	void openServiceDoor();

	/**
	 * Return whether or not the school is opened. When closed, only
	 * {@link Roles#ADMIN} is allowed to access all doors. Anyone, even
	 * unauthenticated users, may check if school is open.
	 * 
	 * @return
	 */
	boolean isOpen();
}
