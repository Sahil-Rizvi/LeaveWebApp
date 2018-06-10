package com.sahil.models.file.response;

import com.sahil.enums.FileType;

public class UploadedFileResponse {

	private String name;
	
	private FileType fileType;
	
	private String createdOn;

	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public FileType getFileType() {
		return fileType;
	}

	public void setFileType(FileType fileType) {
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
		return "UploadedFileResponse [name=" + name + ", fileType=" + fileType + ", createdOn=" + createdOn
				+ ", description=" + description + "]";
	}

}
