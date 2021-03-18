package com.dorian.imagegallery.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dorian.imagegallery.dao.ImageRepository;
import com.dorian.imagegallery.entity.Image;

@Service
@Transactional
public class ImageService {

	@Autowired
	private ImageRepository imageRepository; 
	
	public List<Image> getOrderedImages(){
		
		return imageRepository.findByOrderById();
		
	}
	
	public Optional<Image> getOne(int theId) {
		
		return imageRepository.findById(theId);
	}
	
	public void saveImage(Image theImage) {
		
		imageRepository.save(theImage);
		
	}
	
	public void deleteImage(int theId) {
		
		imageRepository.deleteById(theId);
	}
	
	public boolean existImage(int theId) {
		
		return imageRepository.existsById(theId);
	}
}
