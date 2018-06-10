package com.sahil.services.implementations;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.sahil.entities.UploadedFileEntity;
import com.sahil.enums.FileType;
import com.sahil.repositories.UploadedFileRepository;
import com.sahil.services.UploadFormService;

@Service
public class UploadFormServiceImpl implements UploadFormService{

	private static final int MAX_ALLOWED_SIZE = 8000000; // 1MB

	private static final String UPLOADED_DIR = "/home/sahil/lwa/uploads/";
	
	private static Logger logger = LoggerFactory.getLogger(UploadFormServiceImpl.class);
	
	@Autowired
	private UploadedFileRepository uploadedFileRepository;
	
	@Override
	public List<String> getUploadedFileNamesByType(FileType fileType){
		
		logger.info("Getting uploaded filenames of fileType {}",fileType);
		List<String> fileNames = new ArrayList<>();
		fileNames = uploadedFileRepository.findByFileType(fileType).stream().map(e->e.getFileName()).collect(Collectors.toList());
		logger.info("Filenames of fileType {} are {}",fileNames,fileType);
		return fileNames;
	}
	
	@Override
	public String getUploadedFileNameOfHolidayType(){
		logger.info("Getting uploaded filename of fileType : Holiday");
		
		String file = "";
		if(!CollectionUtils.isEmpty(uploadedFileRepository.findByFileType(FileType.HOLIDAY))){
			file = uploadedFileRepository.findByFileType(FileType.HOLIDAY).get(0).getFileName();
			logger.info("Filename of fileType Holiday : {} ",file);
		}
		return file;
	}

	
	@Override
	public boolean isHolidayFileUploaded(){
		List<UploadedFileEntity>  files =uploadedFileRepository.findByFileType(FileType.HOLIDAY);
		for(UploadedFileEntity file:files){
			if(FileType.HOLIDAY.equals(file.getFileType())){
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(file.getCreatedOn());
					int year = calendar.get(Calendar.YEAR);
					System.out.println(year);
					if(year==Calendar.getInstance().get(Calendar.YEAR)){
						return true;
					}
			}
		}
		return false;
	}
	
	@Override
	public void saveHolidayFile(MultipartFile file) {
		logger.info("In saving Holiday File with file : {}",file);
		if(!file.isEmpty() && "text/csv".equalsIgnoreCase(file.getContentType())){
			byte[] bytes = null;
			try {
					bytes = file.getBytes();
			} catch (IOException e) {
				logger.error("IOException {} occured",e);	
			}
			System.out.println(Calendar.getInstance().get(Calendar.YEAR));
			int dot =  file.getOriginalFilename().lastIndexOf('.');
			if(dot==-1){
				return ;
			}
			String name = file.getOriginalFilename().substring(0,dot);
			String ext = file.getOriginalFilename().substring(dot);
			
			String fileName = name+ "|"+Calendar.getInstance().get(Calendar.YEAR)+""+ext;
			Path path = Paths.get(UPLOADED_DIR+fileName);
			try {
				if(bytes.length<=MAX_ALLOWED_SIZE){
					Files.write(path, bytes);
					UploadedFileEntity fileEntity = new UploadedFileEntity();
					fileEntity.setFileName(fileName);
					fileEntity.setFileType(FileType.HOLIDAY);
					uploadedFileRepository.save(fileEntity);
					logger.info("Holiday File {} uploaded",fileName);
				}
				else{
					logger.info("File {} not uploaded as fileSize {} exceeded",file,bytes.length);
				}
			} catch (IOException e) {
				logger.error("IOException {} occured",e);
			}
		
		}
		else
		if(!"text/csv".equalsIgnoreCase(file.getContentType())){
			System.out.println("Only text/csv file can be uploaded");
		}
		
	}
	
	@Override
	public void saveUploadedFiles(List<MultipartFile> files){
		// TODO Auto-generated method stub
		logger.info("Saving files :{} "+files);
		for(MultipartFile file:files){
			if(file.isEmpty()){
				continue;
			}
			if("text/csv".equalsIgnoreCase(file.getContentType())){
			    logger.error("Only text/csv file can be uploaded");
				continue;
			}
			byte[] bytes = null;
			try {
					bytes = file.getBytes();
			} catch (IOException e) {
				logger.error("IOException {} occured",e);	
			}
			
			System.out.println("here"+Calendar.getInstance().get(Calendar.YEAR));
			int dot =  file.getOriginalFilename().lastIndexOf('.');
			if(dot==-1){
				return ;
			}
			String name = file.getOriginalFilename().substring(0,dot);
			String ext = file.getOriginalFilename().substring(dot);
			
			String fileName = name+ "|"+Calendar.getInstance().get(Calendar.YEAR)+""+ext;
			Path path = Paths.get(UPLOADED_DIR+fileName);
			
			try {
				if(bytes.length<=MAX_ALLOWED_SIZE){
					Files.write(path, bytes);
					UploadedFileEntity fileEntity = new UploadedFileEntity();
					fileEntity.setFileName(fileName);
					fileEntity.setFileType(FileType.OTHERS);
					uploadedFileRepository.save(fileEntity);
					logger.info("File {} uploaded",fileName);
				}
				else{
					logger.info("File {} not uploaded as fileSize {} exceeded",file,bytes.length);
				}
			} catch (IOException e) {
				logger.error("IOException {} occured",e);
			}
		}
	}
	
	
}
