package security.secureschool;

import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.EJBAccessException;
import javax.inject.Inject;
import javax.naming.NamingException;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.prototyping.context.api.ArquillianContext;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import security.secureschool.api.SecureSchoolLocalBusiness;
import security.secureschool.impl.SecureSchoolBean;

/**
 * Test cases to ensure the SecureSchoolEJB is working as contracted with
 * regards to its security model
 * 
 * @author pirent
 *
 */
@RunWith(Arquillian.class)
//@Ignore //TODO Support OpenEJB again w/ new ARQ version
public class SecureSchoolIntegrationTest {

	private static final Logger LOGGER = Logger
			.getLogger(SecureSchoolIntegrationTest.class.getName());

	/**
	 * The EJB JAR to be deployed into the server.
	 * 
	 * @return
	 */
	@Deployment
	public static JavaArchive getDeployment() {
		final JavaArchive jar = ShrinkWrap.create(JavaArchive.class,
				"secureSchool.jar");

		jar.addPackages(false, SecureSchoolLocalBusiness.class.getPackage(),
				SecureSchoolBean.class.getPackage());
		LOGGER.info("-- Archiving: " + jar.toString(true) + "--");

		return jar;
	}
	
	/**
	 * JNDI Name at which we look up the EJB
	 */
	private static final String JNDI_NAME_EJB = "SecureSchoolBeanLocal";
	
	private static String USER_NAME_ADMIN = "admin";
	private static String PASSWORD_ADMIN = "adminPassword";
	private static String USER_NAME_JANITOR = "janitor";
	private static String PASSWORD_JANITOR = "janitorPassword";
	private static String USER_NAME_STUDENT = "student";
	private static String PASSWORD_STUDENT = "studentPassword";
	
	/**
	 * Hook to Arquillian so we can create new JNDI Contexts using supplied properties
	 */
//	@Inject
//	private ArquillianContext arquillianContext;
	
	/**
	 * EJB proxy injected without any explicit login or authentication/authorization.
	 */
	@EJB(mappedName = "java:global/secureSchool/SecureSchoolBean!security.secureschool.api.SecureSchoolLocalBusiness")
	private SecureSchoolLocalBusiness unauthenticatedSchool;
	
	// TODO: inject a proxy fire department
	
	
	//----------------------------------------------------------------------//
	//-------------- Tests -------------------------------------------------//
	//----------------------------------------------------------------------//
	
	@Test(expected = EJBAccessException.class)
	public void unauthenticatedUserCannotOpenFrontDoor() throws NamingException {
		// Try to open the front door before we're authenticated; should fail
		unauthenticatedSchool.openFrontDoor();
	}
}
