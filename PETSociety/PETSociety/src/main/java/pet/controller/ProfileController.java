package pet.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import pet.model.Profile;
import pet.model.User;
import pet.s3.S3Util;
import pet.service.ProfanityFilterService;
import pet.service.ProfileService;

/**
 * This is a Controller that handles any endpoints regarding Profile objects
 * @author Tyler
 */
@RestController
public class ProfileController {

	private ProfileService serv;
	private ProfanityFilterService pfServ;

	public ProfileController() {
	}

	@Autowired
	public ProfileController(ProfileService serv, ProfanityFilterService pfServ) {
		this.serv = serv;
		this.pfServ = pfServ;
	}
	
	/**
	 * Method called when the /getAllProfiles endpoint is hit which gets a List of all profiles and returns it
	 * @return - List<Profile> of all profiles
	 */
	@PostMapping("/getAllProfiles")
	public List<Profile> getAll(){
		return serv.getAll();
	}
	
	/**
	 * Method to get the logged in profile's id
	 * @param ses - current session to get the logged in user / profile from
	 * @return - Id of logged in user's profile
	 */
    @GetMapping("/getProfileId")
    public Profile getId(HttpSession ses){
        User login = (User) ses.getAttribute("user");
        return serv.getById(login.getMyProfile().getProfileId());
    }
    
    /**
     * Method to update a profile
     * @param req - HttpServletRequest to get parameters to update from
     * @param file - profile picture to update
     * @return - String representing success or failure
     * @throws IOException 
     */
    @PostMapping(value="/updateProfileById")
    public String updateProfile(HttpSession session, MultipartHttpServletRequest req, HttpServletResponse resp,
    		@RequestParam("profileImage") MultipartFile image,
    		@RequestParam("profileFirstName") String firstName,
    		@RequestParam("profileLastName") String lastName,
    		@RequestParam("profileDescription") String profileDescription) throws IOException{
       
        String profilePic = null;
        try {
        	profilePic = S3Util.uploadFile(image.getOriginalFilename(), image.getInputStream());
        }catch (Exception e) {
        	System.out.println("File could not be uploaded");
        	profilePic = null;
        	
        }
        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        
//        System.out.println(id);
//        System.out.println("profile picture url : " + profilePic);
        User currentUser = (User) session.getAttribute("user");
        Profile currentProfile = currentUser.getMyProfile();
        if(!firstName.equals("")) { 
        	System.out.println("firstName: "+firstName);
        	currentProfile.setFirstName(pfServ.run(firstName));
        }
        if(!lastName.equals("")) {
        	System.out.println("lastName: "+lastName);
        	currentProfile.setLastName(pfServ.run(lastName));
        }
        if(!profileDescription.equals("")) {
        	System.out.println("profileDescription: "+profileDescription);
        	currentProfile.setDescription(pfServ.run(profileDescription));
        }
        if(profilePic != null)
        currentProfile.setProfilePic(profilePic);

        serv.updateProfile(currentProfile);
        
        resp.sendRedirect("html/user.html");
		return "ok";
        
    }
}