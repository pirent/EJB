package security.secureschool.impl;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;
import javax.ejb.SessionContext;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import security.secureschool.api.SchoolClosedException;
import security.secureschool.api.SecureSchoolLocalBusiness;

/**
 * A secure school which may block requests to open doors depending upon the EJB
 * security model's configuration.
 * 
 * @author pirent
 *
 */
@Singleton
@Startup
@Local(SecureSchoolLocalBusiness.class)
@DeclareRoles({ Roles.ADMIN, Roles.JANITOR, Roles.STUDENT })
@RolesAllowed({})
public class SecureSchoolBean implements SecureSchoolLocalBusiness {

	private static final Logger LOGGER = Logger.getLogger(SecureSchoolBean.class.getName());
	
	/**
	 * Whether or not the school is open.
	 */
	private boolean isOpen;
	
	/**
	 * Hooks to the container to get security information
	 */
	@Resource
	private SessionContext context;

	@PostConstruct
	@RolesAllowed(Roles.ADMIN)
	public void open() {
		// School is open when created.
		this.isOpen = true;
	}

	@RolesAllowed(Roles.ADMIN)
	public void close() {
		isOpen = false;
	}

	// Give everyone access to this method, we may restrict them later
	@RolesAllowed({Roles.ADMIN, Roles.JANITOR, Roles.STUDENT})
	public void openFrontDoor() throws SchoolClosedException {
		
		final String callerName = context.getCallerPrincipal().getName();
		
		if(!isOpen) {
			if(!context.isCallerInRole(Roles.ADMIN)) {
				// Kick'em out
				throw SchoolClosedException
						.newInstance("Attempt to open the front door after hours is prohibited to all but admins, deny to "
								+ callerName);
			}
		}

		LOGGER.info("Opening front door for " + callerName);
	}

	@RolesAllowed({ Roles.ADMIN, Roles.JANITOR })
	public void openServiceDoor() {
		LOGGER.info("Open service door for " + context.getCallerPrincipal().getName());
	}

	@PermitAll
	public boolean isOpen() {
		return isOpen;
	}

}
