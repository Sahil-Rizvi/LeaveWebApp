package com.sahil.models.file.response;

public class FileDetailsDTO {

	private int id;
	
	private String name;
	
	private String fileType;
	
	private String createdOn;

	private String description;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "FileDetailsDTO [id=" + id + ", name=" + name + ", fileType=" + fileType + ", createdOn=" + createdOn
				+ ", description=" + description + "]";
	}

	
}
