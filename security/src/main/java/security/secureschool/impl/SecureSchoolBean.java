package security.secureschool.impl;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;
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
@DeclareRoles({Roles.ADMIN, Roles.JANITOR, Roles.STUDENT})
@RolesAllowed({})
public class SecureSchoolBean implements SecureSchoolLocalBusiness {

	public void open() {
		// TODO Auto-generated method stub

	}

	public void close() {
		// TODO Auto-generated method stub

	}

	public void openFrontDoor() throws SchoolClosedException {
		// TODO Auto-generated method stub

	}

	public void openServiceDoor() {
		// TODO Auto-generated method stub

	}

	public boolean isOpen() {
		// TODO Auto-generated method stub
		return false;
	}

}
