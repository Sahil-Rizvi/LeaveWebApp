package com.sahil.models.file.uploads;

import org.springframework.web.multipart.MultipartFile;

public class UploadedFile {

	private String name;
	
	private MultipartFile multipartFile;
	
	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MultipartFile getMultipartFile() {
		return multipartFile;
	}

	public void setMultipartFile(MultipartFile multipartFile) {
		this.multipartFile = multipartFile;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "UploadedFile [name=" + name + ", multipartFile=" + multipartFile + ", description=" + description + "]";
	}
	
	
}
