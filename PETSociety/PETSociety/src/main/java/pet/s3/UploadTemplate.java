package pet.s3;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


/**
 * Class with a template of how to upload data to the s3 instance
 * @author Emmanuel
 *
 */
public class UploadTemplate {
	
	@PostMapping("/upload")
	public String handleUploadForm(@RequestParam("file") MultipartFile multipart) {
		String fileName = multipart.getOriginalFilename();
		
		try {
			S3Util.uploadFile(fileName, multipart.getInputStream());
		} catch (Exception ex) {
			System.out.println("error uploading file");
		}
		
		return "demo";				
	}
}