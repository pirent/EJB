package security.secureschool.impl;

/**
 * Holds the list of roles with which users of the school may be affiliated. EJB
 * security is role-based, so this is how we'll determine access.
 * 
 * @author pirent
 *
 */
public interface Roles {

	String ADMIN = "Administrator";

	String STUDENT = "Student";

	String JANITOR = "Janitor";
}
