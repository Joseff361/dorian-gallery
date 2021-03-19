package com.dorian.imagegallery.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dorian.imagegallery.dto.Message;
import com.dorian.imagegallery.entity.Image;
import com.dorian.imagegallery.service.CloudinaryService;
import com.dorian.imagegallery.service.ImageService;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST, RequestMethod.DELETE})
@RequestMapping("cloudinary")
public class CloudinaryController {

	@Autowired
	private CloudinaryService cloudinaryService;
	
	@Autowired
	private ImageService imageService; 
	
	@GetMapping("/images")
	public ResponseEntity<List<Image>> listAll(){
		
		return new ResponseEntity<List<Image>>(this.imageService.getOrderedImages(), HttpStatus.OK);
	}
	
	@PostMapping("/images")
	public ResponseEntity<?> upload(@RequestParam MultipartFile theMultipartFile) throws IOException{
		
		// BufferedImage =>  is used to handle and manipulate the image data
		// ImageIO => you can open/read images in a variety of formats
		BufferedImage bi = ImageIO.read(theMultipartFile.getInputStream());
		
		if(bi == null) {
			return new ResponseEntity<>(new Message("Invalid source"), HttpStatus.BAD_REQUEST);
		}
		
		Map<?, ?> result = cloudinaryService.upload(theMultipartFile);
		
		Image theImage = new Image((String) result.get("original_filename"),
								   (String) result.get("secure_url"), 
								   (String)result.get("public_id"));
		
		imageService.saveImage(theImage);
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@DeleteMapping("/images/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") int theId) throws IOException{
		
		if(!imageService.existImage(theId)) {
			
			return new ResponseEntity<>(new Message("Image not found"), HttpStatus.NOT_FOUND);
			
		}
		
		Image theImage = imageService.getOne(theId).get();
		
		cloudinaryService.delete(theImage.getImageId());
		
		imageService.deleteImage(theId);
		
		return new ResponseEntity<>(new Message("Image deleted"), HttpStatus.OK);
		
		
	}

	

}
