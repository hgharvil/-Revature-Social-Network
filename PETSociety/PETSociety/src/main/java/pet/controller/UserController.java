package pet.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import pet.model.PasswordResetToken;
import pet.model.Profile;
import pet.model.User;
import pet.service.PasswordResetTokenService;
import pet.service.UserService;

/**
 * This controller handles the login/register/forgot password pages operations.
 * 
 * @author Henry
 */
@RestController
@CrossOrigin(origins = "http://localhost:9010")
public class UserController {


	private UserService serv;
	private PasswordResetTokenService pServ;

	public UserController() {
	}

	@Autowired
	public UserController(UserService serv, PasswordResetTokenService pServ) {
		super();
		this.serv = serv;
		this.pServ = pServ;
	}
	
	/**
	 * Method to get a user by their id
	 * @param myid - id of the user to get
	 * @return - User retrieved
	 */
	@GetMapping("/getUserById")
	public User getUserById(@RequestParam("id") int myid){
		User u = serv.findById(myid);
		u = new User(u.getUserId(), u.getPetUsername(), u.getMyProfile());
		return u;
	}
	
	/**
	 * Method to get all users in the system
	 * @return - List<User> of users in the system
	 */
	@GetMapping("/getAllUser")
	public List<User> getUser() {
		List<User> user = serv.findAll();
		for(User u : user) {
			u = new User(u.getUserId(), u.getPetUsername(), u.getMyProfile());
		}
		return user;
	}
	
	/**
	 * Method to get logged in user
	 * @param session - session currently active to get user from
	 * @return - User logged in currently
	 */
	@GetMapping("/getUser")
	public User getUser(HttpSession session) {
		User userSession = (User) session.getAttribute("user");
		User user = serv.findById(userSession.getUserId());
		user = new User(user.getUserId(), user.getPetUsername(), user.getMyProfile());
		return user;
	};
	
	/**
	 * This method registers a user into the database using fields from a form in
	 * index.html
	 * 
	 * @param req expecting String parameters "registerUsername", "registerPassword"
	 *            and "registerEmail"
	 * @throws IOException
	 */
	@PostMapping(value = "/registerUser")
	@ResponseStatus(value = HttpStatus.CREATED)
	public User register(@RequestParam("registerUsername") String username,
			@RequestParam("registerPassword") String password, @RequestParam("registerEmail") String email)
			throws IOException {
		String hashedPassword = passwordHashing(password);

		User newUser = new User(username, hashedPassword, email);
		Profile newProfile = new Profile("Name", "Name", "This User has not set their description yet!");
		newProfile.setProfilePic("https://s3.us-east-2.amazonaws.com/petsocietyproject2/default_user.jpg");
		newUser.setMyProfile(newProfile);

		try {
			User user = serv.register(newUser);
			System.out.println(user);
			if (user.getPetUsername() == null) {
				System.out.println("Registration failure!");
				return new User(null, null, null);
			}
		} catch (Exception e) {
			System.out.println("Registration failure!");
			return new User(null, null, null);
		}

		System.out.println("Registration success!");
		// creates the user and the profile
		serv.register(newUser);

		return new User(username, newProfile); // let us not send back the password nor email
		// validation still needs to be done
	}

	/**
	 * This method hashes passwords
	 * @param password - password to be hashed
	 * @return String - hashed password
	 */
	private String passwordHashing(String password){
		String answerPassword = null;
		// PBKDF2
//		SecureRandom random = new SecureRandom();
		byte[] salt = "9989887877676656".getBytes();
//		random.nextBytes(salt);
		try {
			KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128); // penultimate parameter is the
																						// strength
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			byte[] hash = factory.generateSecret(spec).getEncoded();
			answerPassword = new String(hash);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.getStackTrace();
			
		}
		return answerPassword;
	}

	/**
	 * This method checks if the credentials have a match in the database in order
	 * to log in the user based on the same credentials
	 * 
	 * @param session
	 * @param username
	 * @param password
	 * @return
	 * @throws InvalidKeySpecException
	 * @throws NoSuchAlgorithmException
	 */
	@GetMapping(value = "/loginUser")
	public User loginUser(HttpSession session, @RequestParam("loginUsername") String username,
			@RequestParam("loginPassword") String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
		User user = serv.login(username, passwordHashing(password));
		if (user != null) {
			session.setAttribute("user", user);
			return new User(username, user.getMyProfile());
		} else {
			session.invalidate();
			return new User(null, null, null);
		}
	}
	
	/**
	 * Method to logout a user
	 * @param resp - Response to write back
	 * @param session - session to invalidate for logout
	 * @throws ServletException - Potential exception
	 * @throws IOException - Potential exception
	 */
	@GetMapping(value = "/logout")
	public void logout(HttpServletResponse resp, HttpSession session) throws ServletException, IOException {
		session.invalidate();
		resp.getWriter().write(new ObjectMapper().writeValueAsString("go home"));
	}

	/**
	 * This method checks if the email matches a user email in the database and
	 * sends a mail to them with a password retrieval token.
	 * 
	 * @param session
	 * @param userEmail
	 * @return if successful User with username. Otherwise, empty User.
	 * @throws AddressException
	 * @throws MessagingException
	 * @throws IOException
	 */
	@GetMapping(value = "/verifyAndSendEmail")
	public User verifyAndSendEmail(HttpSession session, @RequestParam("emailField") String userEmail)
			throws AddressException, MessagingException, IOException {
		// verifying we're not logged in
		if ((User) session.getAttribute("user") == null) {
			User myUser = serv.findByEmail(userEmail);
			if (myUser != null) { // if there's a match in the database
				session.setAttribute("forgotPasswordUser", myUser);
				String token = UUID.randomUUID().toString(); // creates a token
				pServ.createPasswordResetTokenForUser(myUser, token); // insert an entry in the
				sendTokenEmail(myUser, token);
				return new User(myUser.getPetUsername(), null, null);
			}
		}
		System.out.println("failure");
		return new User(null, null, null);
	}

	/**
	 * This method sends an email to the user with a randomly generated token
	 * 
	 * @param user  A User object with email address to be used
	 * @param token A randomly generated string of numbers and letters.
	 * @throws AddressException
	 * @throws MessagingException
	 * @throws IOException
	 */
	private void sendTokenEmail(User user, String token) throws AddressException, MessagingException, IOException {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("okpetsocietytokensender@gmail.com", "larxene101001");
			}
		});
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress("okpetsocietytokensender@gmail.com", false));

		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getPetEmail()));
		msg.setSubject("Reset Password");
		msg.setContent("You've been given a password reset token.\n"
				+ "Please provide it to the password reset page.\n Code: " + token, "text/html");
		msg.setSentDate(new Date(System.currentTimeMillis()));
		Transport.send(msg);
	}

	/**
	 * This method obtains a user token and verifies if it matches the previously
	 * generated code for that user.
	 * 
	 * @param code    A String sent by the user to be compared with the randomly
	 *                generated token
	 * @param session
	 * @return
	 */
	@GetMapping(value = "/verifyToken")
	public User verifyToken(@RequestParam("tokenField") String code, HttpSession session) {
		// verifying we're not logged in
		if ((User) session.getAttribute("user") == null) {
			PasswordResetToken token = pServ.verifyToken(code);
			User user2 = (User) session.getAttribute("forgotPasswordUser");
			if (token != null && token.getUser().getUserId() == user2.getUserId()) {
				// this next if checks if the token hasn't expired
				if (token.getExpiryDate().compareTo(new Timestamp(System.currentTimeMillis())) > 0) {
					session.setAttribute("forgotPasswordToken", token);
					return new User("ok", null, null);
				}
			}
		}
		return new User(null, null, null);
	}

	/**
	 * This method resets the user's password and deletes the token used for it.
	 * 
	 * @param newPassword
	 * @param session
	 * @return
	 */
	@PostMapping(value = "/resetPassword")
	public User resetPassword(@RequestParam("passwordField") String newPassword, HttpSession session) {
		// verifying we're not logged in
		if ((User) session.getAttribute("user") == null) {
			User forgotPasswordUser = (User) session.getAttribute("forgotPasswordUser");
			PasswordResetToken forgotPasswordToken = (PasswordResetToken) session.getAttribute("forgotPasswordToken");
			if (forgotPasswordUser != null) {
				User u = serv.changePassword(forgotPasswordUser.getUserId(), passwordHashing(newPassword));
				if (u.getPetUsername() != null) {
					pServ.deleteToken(forgotPasswordToken);
					session.removeAttribute("forgotPasswordUser");
					session.removeAttribute("forgotPasswordToken");
					return new User("ok", null, null);
				}
			}
		}
		return null;
	}
}
