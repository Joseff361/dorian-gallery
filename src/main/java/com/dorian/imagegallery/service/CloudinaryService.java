package com.dorian.imagegallery.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public class CloudinaryService {

	Cloudinary cloudinary;
	
	private Map<String, String> valuesMap = new HashMap<>();

	public CloudinaryService() {
		
		valuesMap.put("cloud_name", "dq5dstude");
		
		valuesMap.put("api_key", "485594828486725");
		
		valuesMap.put("api_secret", "1L1G-P_uXcvdlUVeRI-qDE4qAyo");
		
		cloudinary = new Cloudinary(valuesMap);
	}
	
	// Map == Map<?, ?>
	public Map<?, ?> upload(MultipartFile theMultipartFile) throws IOException {
		
		File theFile = convert(theMultipartFile);
		
		// ObjectUtils => this class tries to handle null input gracefully
		Map<?, ?> result = cloudinary.uploader().upload(theFile, ObjectUtils.emptyMap());
		
		// Deleting the file and the outputStream from the back-end
		theFile.delete(); 
		
		return result;
		
	}
	
	public Map<?, ?> delete(String theId) throws IOException {
		
		Map<?, ?> result = cloudinary.uploader().destroy(theId, ObjectUtils.emptyMap());
		
		return result;
		
	}
	
	private File convert(MultipartFile theMultipartFile) throws IOException {
		
		File theFile = new File(theMultipartFile.getOriginalFilename());
		
		// It's an output stream for writing data to a File
		FileOutputStream fos = new FileOutputStream(theFile);
		
		// Return the contents of the file as an array of bytes.
		fos.write(theMultipartFile.getBytes());
		
		fos.close();
		
		return theFile;
		
	}

}
