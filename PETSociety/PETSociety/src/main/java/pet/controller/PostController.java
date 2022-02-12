package pet.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import pet.model.Post;
import pet.model.User;
import pet.s3.S3Util;
import pet.service.PostService;
import pet.service.ProfanityFilterService;

/**
 * This is a Controller that handles any endpoints regarding Post objects
 * 
 * @author Tyler
 */
@RestController
public class PostController {

	private PostService serv;
	private ProfanityFilterService pfServ;

	public PostController() {
	}

	@Autowired
	public PostController(PostService serv, ProfanityFilterService pfServ) {
		this.serv = serv;
		this.pfServ = pfServ;
	}

	/**
	 * Method that catches /getAllPost endpoint to return all posts in the database
	 * 
	 * @return - List of all posts within the database
	 */
	@PostMapping(value = "/getAllPost")
	public List<Post> getAllPost() {
		List<Post> postList = serv.selectAllPost();
		return postList;
	}

	/**
	 * Method that catches /getUserPost endpoint to return all posts tied to the
	 * logged in profile
	 * 
	 * @param session - session to get the profile from
	 * @return - list of posts tied to the given profile
	 */
	@PostMapping(value = "/getUserPost")
	public List<Post> getUserPost(HttpSession session) {
		User loggedIn = (User) session.getAttribute("user");
		List<Post> posts = serv.selectUserPost(loggedIn.getMyProfile());
		return posts;
	}

	@PostMapping(value = "/createPost2")
	@ResponseStatus(value = HttpStatus.CREATED)
	public User createPostFromForm(HttpServletRequest req, HttpServletResponse resp,
			HttpSession session, @RequestParam("postImage") MultipartFile image,
			@RequestParam("postText") String text) throws IOException {
		User loggedIn = (User) session.getAttribute("user");
		Post myPost = new Post(pfServ.run(text), 0, loggedIn.getMyProfile());
		String img = null;
		String fileName = image.getOriginalFilename();
		
		
		System.out.println("This is the text: "+text);
		System.out.println("This is the text: "+fileName);
		

        try {
        	img = S3Util.uploadFile(image.getOriginalFilename(), image.getInputStream());
        	
        }catch (Exception e) {
        	System.out.println("File could not be uploaded");
        	img = null;
        }
        
        System.out.println("This is the image url string: "+img);
        if(img != null)
        	myPost.setImg(img);
		System.out.println("createPostFromForm INITIATING POSTING INTO DB.......");
		serv.createPost(myPost);
		
		resp.sendRedirect("/html/mainPage1.html");
		return new User("ok", null, null);
	}
	
	/**
	 * Method to updated the number of likes on a Post
	 * @param req - Servlet Request to get parameters from
	 */
	@PostMapping(value="/updateLikes")
	public void updateLikes(HttpServletRequest req){
		int id = Integer.parseInt(req.getParameter("id"));
	    System.out.println(id);
	        
	    int newLikes =serv.selectPostById(id).getLikes()+1;
	    Post currentPost = serv.selectPostById(id);
	    currentPost.setLikes(newLikes);
	    
	    serv.updatePost(currentPost);
	    System.out.println( "Updated like succesfully");
	}
}
