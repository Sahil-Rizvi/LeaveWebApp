package com.sahil.services.implementations;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.sahil.constants.ConstantValues;
import com.sahil.entities.UploadedFileEntity;
import com.sahil.enums.FileType;
import com.sahil.models.HolidayResponse;
import com.sahil.models.Response;
import com.sahil.models.file.response.DownloadFileResponse;
import com.sahil.models.file.response.FileDetailsDTO;
import com.sahil.models.file.response.UploadedFileResponse;
import com.sahil.models.file.uploads.UploadedFile;
import com.sahil.repositories.UploadedFileRepository;
import com.sahil.services.UploadFileService;
import com.sahil.utils.CSVUtil;
import com.sahil.utils.LeaveUtil;

@Service
public class UploadFileServiceImpl implements UploadFileService{

	private static final int MAX_ALLOWED_SIZE = 8000000; // 1MB

	
	private static Logger logger = LoggerFactory.getLogger(UploadFileServiceImpl.class);
	
	@Autowired
	private UploadedFileRepository uploadedFileRepository;

	@Override
	public List<FileDetailsDTO> getFiles(FileType fileType){
		List<FileDetailsDTO> fileDetailsDTOs = new ArrayList<>();
		List<UploadedFileEntity> uploadedFileEntities = uploadedFileRepository.findByFileType(fileType);
		if(!CollectionUtils.isEmpty(uploadedFileEntities)){
			fileDetailsDTOs = uploadedFileEntities.stream().map(this::convertToFileDetails).collect(Collectors.toList());
		}
		return fileDetailsDTOs;
	}
	
	
	private FileDetailsDTO convertToFileDetails(UploadedFileEntity uploadedFileEntity){
		FileDetailsDTO fileDetailsDTO = new FileDetailsDTO();
		if(Objects.nonNull(uploadedFileEntity)){
			fileDetailsDTO.setId(uploadedFileEntity.getId());
			fileDetailsDTO.setCreatedOn(LeaveUtil.convertDateToStringInParticularFormat(uploadedFileEntity.getCreatedOn()));
			fileDetailsDTO.setFileType(uploadedFileEntity.getFileType().name());
		    fileDetailsDTO.setDescription(uploadedFileEntity.getDescription());
		    fileDetailsDTO.setName(uploadedFileEntity.getFileName());
		}
		return fileDetailsDTO;
	}
	
	
	@Override
	public Response uploadHolidayFile(UploadedFile uploadedFile) {
		
		
		Response response = new Response();
		response.setCode(1);
		logger.info("In uploadHolidayFile with file : {}",uploadedFile);
		
		Response response2 = isHolidayFileUploadedForYear(Calendar.getInstance().get(Calendar.YEAR));
		if(response2.getCode()==0){
			response.setCode(1);
			response.setMessage("Holiday file already available for year :"+Calendar.getInstance().get(Calendar.YEAR));
			return response;
		}
		
		if(uploadedFile==null || StringUtils.isEmpty(uploadedFile.getName()) || uploadedFile.getDescription()==null || uploadedFile.getMultipartFile().isEmpty() || uploadedFile.getMultipartFile()==null){
			response.setMessage("File not uploaded");
			return response;
		}
		
		if(!uploadedFile.getMultipartFile().getContentType().equalsIgnoreCase("text/csv")){
			response.setMessage("File not uploaded as only .csv files are allowed");
			return response;
		}
		
		Response response3 = CSVUtil.validateCSVFile(uploadedFile.getMultipartFile());
		if(response3.getCode()==1){
			response.setCode(1);
			response.setMessage("Error in validation :"+response3.getMessage());
			return response;
		}
		
		MultipartFile multipartFile = uploadedFile.getMultipartFile();
		
		byte[] bytes = null;
		try{
			bytes = multipartFile.getBytes();
		}
		catch(IOException e){
			logger.error("IOException {} occured",e);
			response.setCode(1);
			response.setMessage("File not uploaded as some exception occured");
			return response;
		}
		
		int dot = multipartFile.getOriginalFilename().lastIndexOf('.');
		if(dot==-1){
			response.setCode(1);
			response.setMessage("File not uploaded as some exception occured");
			return response;
		}
		
		String ext = multipartFile.getOriginalFilename().substring(dot);
		String fileName = generateHolidayFileName(uploadedFile.getName(),ext);
		if(null==fileName){
			response.setCode(1);
			response.setMessage("File not uploaded as some exception <br> while generating fileName");
			return response;
		}
		
		Path path = Paths.get(ConstantValues.HOLIDAY_DIRECTORY+"/"+fileName);
		try {
				if(bytes.length<=MAX_ALLOWED_SIZE){
					Files.write(path, bytes);
					UploadedFileEntity fileEntity = new UploadedFileEntity();
					fileEntity.setFileName(fileName);
					fileEntity.setCreatedOn(LeaveUtil.getTodaysDate());
					fileEntity.setDescription(uploadedFile.getDescription());
					fileEntity.setFileType(FileType.HOLIDAY);
					uploadedFileRepository.save(fileEntity);
					System.out.println("saved");
					logger.info("Holiday File {} uploaded",uploadedFile);
					response.setCode(0);
					response.setMessage(fileName+" uploaded successfully");
					return response;
				}
				else{
					logger.info("File {} not uploaded as fileSize {} exceeded",fileName,bytes.length);
					response.setCode(1);
					response.setMessage("File not uploaded as fileSize:"+bytes.length+" exceeded MAX_ALLOWED_SIZE:"+MAX_ALLOWED_SIZE);
					return response;
				}
			} catch (IOException e) {
				System.out.println(e);
				logger.error("IOException {} occured",e);
				response.setCode(1);
				response.setMessage("File not uploaded as exception occured");
				return response;
			}
		
	}
	
	private String generateHolidayFileName(String name, String ext) {
		if(!StringUtils.isEmpty(name) && !StringUtils.isEmpty(ext)){	
			Calendar calendar = Calendar.getInstance();
			int year = calendar.get(Calendar.YEAR);
			String Syear = String.valueOf(year); 
			return name.trim().toLowerCase().concat("|").concat(Syear).concat(ext);
		}
		return null;
	}


	@Override
	public Response uploadNoticeFile(UploadedFile uploadedFile) {
		
		Response response = new Response();
		response.setCode(1);
		logger.info("In uploadNoticeFile with file : {}",uploadedFile);
		
		
		if(uploadedFile==null || StringUtils.isEmpty(uploadedFile.getName()) || uploadedFile.getDescription()==null || uploadedFile.getMultipartFile().isEmpty() || uploadedFile.getMultipartFile()==null){
			response.setMessage("File not uploaded");
			return response;
		}
		
		if(!uploadedFile.getMultipartFile().getContentType().equalsIgnoreCase("application/pdf")){
			response.setMessage("File not uploaded as only .pdf files are allowed");
			return response;
		}
		
		MultipartFile multipartFile = uploadedFile.getMultipartFile();
		
		byte[] bytes = null;
		try{
			bytes = multipartFile.getBytes();
		}
		catch(IOException e){
			logger.error("IOException {} occured",e);
			response.setCode(1);
			response.setMessage("File not uploaded as some exception occured");
			return response;
		}
		
		int dot = multipartFile.getOriginalFilename().lastIndexOf('.');
		if(dot==-1){
			response.setCode(1);
			response.setMessage("File not uploaded as some exception occured");
			return response;
		}
		
		String ext = multipartFile.getOriginalFilename().substring(dot);
		
		String fileName = uploadedFile.getName().trim().concat(ext);
		Path path = Paths.get(ConstantValues.NOTICE_DIRECTORY+"/"+fileName);
		try {
				if(bytes.length<=MAX_ALLOWED_SIZE){
					Files.write(path, bytes);
					UploadedFileEntity fileEntity = new UploadedFileEntity();
					fileEntity.setFileName(fileName);
					fileEntity.setCreatedOn(LeaveUtil.getTodaysDate());
					fileEntity.setDescription(uploadedFile.getDescription());
					fileEntity.setFileType(FileType.NOTICE);
					uploadedFileRepository.save(fileEntity);
					logger.info("Notice File {} uploaded",uploadedFile);
					response.setCode(0);
					response.setMessage(fileName+" uploaded successfully");
					return response;
				}
				else{
					logger.info("File {} not uploaded as fileSize {} exceeded",fileName,bytes.length);
					response.setCode(1);
					response.setMessage("File not uploaded as fileSize:"+bytes.length+" exceeded MAX_ALLOWED_SIZE:"+MAX_ALLOWED_SIZE);
					return response;
				}
			} catch (IOException e) {
				logger.error("IOException {} occured",e);
				response.setCode(1);
				response.setMessage("File not uploaded as exception occured");
				return response;
			}
		
	}

@Override
public Response uploadLeavePolicyFile(UploadedFile uploadedFile) {
		
		Response response = new Response();
		response.setCode(1);
		logger.info("In uploadLeavePolicyFile with file : {}",uploadedFile);
		
		
		if(uploadedFile==null || StringUtils.isEmpty(uploadedFile.getName()) || uploadedFile.getDescription()==null || uploadedFile.getMultipartFile().isEmpty() || uploadedFile.getMultipartFile()==null){
			response.setMessage("File not uploaded");
			return response;
		}
		
		if(!uploadedFile.getMultipartFile().getContentType().equalsIgnoreCase("application/pdf")){
			response.setMessage("File not uploaded as only .pdf files are allowed");
			return response;
		}
		
		MultipartFile multipartFile = uploadedFile.getMultipartFile();
		
		byte[] bytes = null;
		try{
			bytes = multipartFile.getBytes();
		}
		catch(IOException e){
			logger.error("IOException {} occured",e);
			response.setCode(1);
			response.setMessage("File not uploaded as some exception occured");
			return response;
		}
		
		int dot = multipartFile.getOriginalFilename().lastIndexOf('.');
		if(dot==-1){
			response.setCode(1);
			response.setMessage("File not uploaded as some exception occured");
			return response;
		}
		
		String ext = multipartFile.getOriginalFilename().substring(dot);
		
		String fileName = uploadedFile.getName().trim().concat(ext);
		Path path = Paths.get(ConstantValues.LEAVE_POLICY_DIRECTORY+"/"+fileName);
		try {
				if(bytes.length<=MAX_ALLOWED_SIZE){
					Files.write(path, bytes);
					UploadedFileEntity fileEntity = new UploadedFileEntity();
					fileEntity.setFileName(fileName);
					fileEntity.setCreatedOn(LeaveUtil.getTodaysDate());
					fileEntity.setDescription(uploadedFile.getDescription());
					fileEntity.setFileType(FileType.LEAVE_POLICY);
					uploadedFileRepository.save(fileEntity);
					logger.info("LeavePolicy File {} uploaded",uploadedFile);
					response.setCode(0);
					response.setMessage(fileName+" uploaded successfully");
					return response;
				}
				else{
					logger.info("File {} not uploaded as fileSize {} exceeded",fileName,bytes.length);
					response.setCode(1);
					response.setMessage("File not uploaded as fileSize:"+bytes.length+" exceeded MAX_ALLOWED_SIZE:"+MAX_ALLOWED_SIZE);
					return response;
				}
			} catch (IOException e) {
				logger.error("IOException {} occured",e);
				response.setCode(1);
				response.setMessage("File not uploaded as exception occured");
				return response;
			}
		
	}

private Response isHolidayFileUploadedForYear(int year){
	
	Response response = new Response();
	if(year<=1970){
		response.setCode(1);
		response.setMessage("Invalid Year");
	    return response;
	}
	List<UploadedFileEntity> uploadedFileEntities = uploadedFileRepository.findByFileType(FileType.HOLIDAY);
	if(CollectionUtils.isEmpty(uploadedFileEntities)){
		response.setCode(1);
		response.setMessage("No Holiday File Available");
		return response;
	}
	for(UploadedFileEntity uploadedFileEntity:uploadedFileEntities){
		Date createdOn = uploadedFileEntity.getCreatedOn();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(createdOn);
		String fileNameWithYear = uploadedFileEntity.getFileName();
		int pipe = fileNameWithYear.lastIndexOf('|');
		int dot = fileNameWithYear.lastIndexOf('.',pipe+1);
		System.out.println(pipe);
		System.out.println(dot);
		
		
		System.out.println("Uploaded year:"+calendar.get(Calendar.YEAR));
		if(calendar.get(Calendar.YEAR)==year){
			response.setCode(0);
			response.setMessage("Holiday file available for year "+year+"\n file :"+convert(uploadedFileEntity));
			return response;
		}
	}
	
	response.setCode(1);
	response.setMessage("No Holiday File Found for year "+year);
	return response;
	
}

@Override
public boolean isHolidayFileUploadedForCurrentYear(){
	
	List<UploadedFileEntity> uploadedFileEntities = uploadedFileRepository.findByFileType(FileType.HOLIDAY);
	if(CollectionUtils.isEmpty(uploadedFileEntities)){
		return false;
	}
	for(UploadedFileEntity uploadedFileEntity:uploadedFileEntities){
		Date createdOn = uploadedFileEntity.getCreatedOn();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(createdOn);
		if(calendar.get(Calendar.YEAR)==Calendar.getInstance().get(Calendar.YEAR)){
			return true;
		}
	}
	
	return false;
}



private UploadedFileResponse convert(UploadedFileEntity uploadedFileEntity){
	UploadedFileResponse uploadedFileResponse = new UploadedFileResponse();
	if(Objects.nonNull(uploadedFileEntity)){
		uploadedFileResponse.setName(uploadedFileEntity.getFileName());
		uploadedFileResponse.setDescription(uploadedFileEntity.getDescription());
		uploadedFileResponse.setFileType(uploadedFileEntity.getFileType());
		uploadedFileResponse.setCreatedOn(LeaveUtil.convertDateToStringInParticularFormat(uploadedFileEntity.getCreatedOn()));
	}
	return uploadedFileResponse;
}


@Override
public Response uploadOtherFile(UploadedFile uploadedFile) {
		
		Response response = new Response();
		response.setCode(1);
		logger.info("In uploadOtherFile with file : {}",uploadedFile);
		
		System.out.println(uploadedFile);
		if(uploadedFile==null || StringUtils.isEmpty(uploadedFile.getName()) || uploadedFile.getDescription()==null || uploadedFile.getMultipartFile().isEmpty() || uploadedFile.getMultipartFile()==null){
			response.setMessage("File not uploaded");
			return response;
		}
		
		if(!uploadedFile.getMultipartFile().getContentType().equalsIgnoreCase("application/pdf")){
			response.setMessage("File not uploaded as only .pdf files are allowed");
			return response;
		}
		
		MultipartFile multipartFile = uploadedFile.getMultipartFile();
		
		byte[] bytes = null;
		try{
			bytes = multipartFile.getBytes();
		}
		catch(IOException e){
			logger.error("IOException {} occured",e);
			response.setCode(1);
			response.setMessage("File not uploaded as some exception occured");
			return response;
		}
		
		int dot = multipartFile.getOriginalFilename().lastIndexOf('.');
		if(dot==-1){
			response.setCode(1);
			response.setMessage("File not uploaded as some exception occured");
			return response;
		}
		
		String ext = multipartFile.getOriginalFilename().substring(dot);
		
		String fileName = uploadedFile.getName().trim().concat(ext);
		Path path = Paths.get(ConstantValues.OTHERS_DIRECTORY+"/"+fileName);
		try {
				if(bytes.length<=MAX_ALLOWED_SIZE){
					Files.write(path, bytes);
					UploadedFileEntity fileEntity = new UploadedFileEntity();
					fileEntity.setFileName(fileName);
					fileEntity.setCreatedOn(LeaveUtil.getTodaysDate());
					fileEntity.setDescription(uploadedFile.getDescription());
					fileEntity.setFileType(FileType.OTHERS);
					uploadedFileRepository.save(fileEntity);
					logger.info("Other File {} uploaded",uploadedFile);
					response.setCode(0);
					response.setMessage(fileName+" uploaded successfully");
					return response;
				}
				else{
					logger.info("File {} not uploaded as fileSize {} exceeded",fileName,bytes.length);
					response.setCode(1);
					response.setMessage("File not uploaded as fileSize:"+bytes.length+" exceeded MAX_ALLOWED_SIZE:"+MAX_ALLOWED_SIZE);
					return response;
				}
			} catch (IOException e) {
				logger.error("IOException {} occured",e);
				response.setCode(1);
				response.setMessage("File not uploaded as exception occured");
				return response;
			}
		
	}

    @Override
	public Response deleteFile(int id){
		Response response = new Response();
		if(id<=0){
			response.setCode(1);
			response.setMessage("Invalid fileId");
			return response;
		}
		UploadedFileEntity uploadedFileEntity = uploadedFileRepository.findOne(id);
		if(Objects.nonNull(uploadedFileEntity)){
			
			String prefix = generatePrefix(uploadedFileEntity.getFileType());
			
			if(StringUtils.isEmpty(prefix)){
				response.setCode(1);
				response.setMessage("Error in prefix generation");
				return response;
			}
			
			String fileName = prefix+"/"+uploadedFileEntity.getFileName();
			try{
	    		File file = new File(fileName);
	    		if(file.delete()){
	    			uploadedFileRepository.delete(uploadedFileEntity);
	    			System.out.println(file.getName() + " is deleted!");
	    			response.setCode(0);
	    			response.setMessage(file.getName() + " is deleted!");
	    			return response;
	    		}else{
	    			System.out.println("Delete operation is failed.");
	    			response.setCode(1);
	    			response.setMessage("Delete operation is failed.");
	    			return response;
	    		}
	    	}catch(Exception e){
	    		System.out.println(e.getMessage());
	    		response.setCode(1);
				response.setMessage("Exception Occurred");
				return response;
	    	}
		}
		response.setCode(1);
		response.setMessage("No file found with fileId"+id);
		return response;
	}

	private String generatePrefix(FileType fileType) {
		String prefix = null;
		switch(fileType){
		case HOLIDAY : prefix = ConstantValues.HOLIDAY_DIRECTORY;
	               break;
		case LEAVE_POLICY : prefix = ConstantValues.LEAVE_POLICY_DIRECTORY;
           break;
		case NOTICE : prefix = ConstantValues.NOTICE_DIRECTORY;
           break;
		case OTHERS : prefix = ConstantValues.OTHERS_DIRECTORY;
           break;
		}
		return prefix;
	}


	@Override
	public DownloadFileResponse downloadFile(int id){
		DownloadFileResponse downloadFileResponse = new DownloadFileResponse();
		if(id<=0){
			downloadFileResponse.setCode(1);
			downloadFileResponse.setMessage("Invalid Id");
			return downloadFileResponse;
		}
		UploadedFileEntity uploadedFileEntity = uploadedFileRepository.findOne(id);
		if(Objects.nonNull(uploadedFileEntity)){
			System.out.println(uploadedFileEntity.getFileName());
			String prefix = generatePrefix(uploadedFileEntity.getFileType());
			if(StringUtils.isEmpty(prefix)){
				downloadFileResponse.setCode(1);
				downloadFileResponse.setMessage("Error in prefix generation");
				return downloadFileResponse;
			}
			System.out.println(prefix);
			Path path = Paths.get(prefix+"/"+uploadedFileEntity.getFileName());
		    System.out.println(path);
			byte[] data;
			try {
				data = Files.readAllBytes(path);
				ByteArrayResource resource = new ByteArrayResource(data);
				downloadFileResponse.setCode(0);
				downloadFileResponse.setMessage("Successful");
				downloadFileResponse.setData(data);
				downloadFileResponse.setPath(path);
				downloadFileResponse.setResource(resource);
				downloadFileResponse.setMediaType(getMediaType(uploadedFileEntity.getFileName()));
				return downloadFileResponse;
			} catch (IOException e) {
				System.out.println(e.getMessage());
				downloadFileResponse.setCode(1);
				downloadFileResponse.setMessage("IOException Occured");
				return downloadFileResponse;				
			}
		}
		System.out.println("No file found with id"+id);
		downloadFileResponse.setCode(1);
		downloadFileResponse.setMessage("No file found with id"+id);
		return downloadFileResponse;						
	}
	
	private MediaType getMediaType(String fileName){
		if(StringUtils.isEmpty(fileName)){
			return null;
		}

		int dot = fileName.lastIndexOf('.');
		if(dot==-1){
			return null;
		}
		
		String ext = fileName.substring(dot+1);
		System.out.println(ext);
		if(ext.equalsIgnoreCase("csv")){
			System.out.println("here in media type");
			return MediaType.parseMediaType("text/csv");
		}
		else
		if(ext.equalsIgnoreCase("pdf")){
			return MediaType.parseMediaType("application/pdf");
		}
		return MediaType.ALL;
	}


	@Override
	public UploadedFileEntity getHolidayFileForCurrentYear() {
		// TODO Auto-generated method stub
		List<UploadedFileEntity> uploadedFileEntities = uploadedFileRepository.findByFileType(FileType.HOLIDAY);
		if(CollectionUtils.isEmpty(uploadedFileEntities)){
			return null;
		}
		for(UploadedFileEntity uploadedFileEntity:uploadedFileEntities){
			Date createdOn = uploadedFileEntity.getCreatedOn();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(createdOn);
			System.out.println("Uploaded year:"+calendar.get(Calendar.YEAR));
			if(calendar.get(Calendar.YEAR)==Calendar.getInstance().get(Calendar.YEAR)){
				return uploadedFileEntity;
			}
		}
		
		return null;
	}


	@Override
	public HolidayResponse getHolidayDataForCurrentYear() {
		// TODO Auto-generated method stub
		HolidayResponse response = new HolidayResponse();
		response.setCode(1);
		response.setMessage("Error");
		if(isHolidayFileUploadedForCurrentYear()){
			List<UploadedFileEntity> uploadedFileEntities = uploadedFileRepository.findByFileType(FileType.HOLIDAY);
			
			for(UploadedFileEntity uploadedFileEntity:uploadedFileEntities){
				Date createdOn = uploadedFileEntity.getCreatedOn();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(createdOn);
				if(calendar.get(Calendar.YEAR)==Calendar.getInstance().get(Calendar.YEAR)){
					
				  String name = uploadedFileEntity.getFileName();	
				  String filePath = ConstantValues.HOLIDAY_DIRECTORY.concat("/").concat(name);
				  
				  try {
					HashMap<String,String> data = CSVUtil.CSVReaderWithDateInString(filePath);
					response.setCode(0);
					response.setMessage("Successful");
					response.setMap(data);
				} catch (IOException | ParseException e) {
					// TODO Auto-generated catch block
					System.out.println("Exception :"+e.getMessage());
					response.setMessage("Exception occured");
				}
				  
				}
			}
			
			return response;	
		}
		return response;
	}
}
