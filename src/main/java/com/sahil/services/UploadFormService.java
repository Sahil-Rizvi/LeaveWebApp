package com.sahil.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.sahil.enums.FileType;

public interface UploadFormService {

	public String getUploadedFileNameOfHolidayType();

	public void saveHolidayFile(MultipartFile file);
	
	public List<String> getUploadedFileNamesByType(FileType fileType);

	public void saveUploadedFiles(List<MultipartFile> files);

	public boolean isHolidayFileUploaded();

}
