package com.dorian.imagegallery.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dorian.imagegallery.entity.Image;

public interface ImageRepository extends JpaRepository<Image, Integer> {

	public List<Image> findByOrderById();
	
}
