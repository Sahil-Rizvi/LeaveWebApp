package com.sahil.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sahil.enums.FileType;
import com.sahil.models.Response;
import com.sahil.models.file.response.DownloadFileResponse;
import com.sahil.models.file.uploads.UploadedFile;
import com.sahil.services.UploadFileService;


@Controller
@RequestMapping("/uploader")
public class UploadFileController {

	@Autowired
	private UploadFileService uploadFileService;
	
	private static Logger logger = LoggerFactory.getLogger(UploadFileController.class);
	
	@RequestMapping("/api/list")
	public String getAllFiles(ModelMap map){
		System.out.println("here in uploader");
		String view = "uploads/listfiles";
		map.addAttribute("holidayfiles",uploadFileService.getFiles(FileType.HOLIDAY));
		map.addAttribute("leavepolicyfiles",uploadFileService.getFiles(FileType.LEAVE_POLICY));
		map.addAttribute("noticefiles",uploadFileService.getFiles(FileType.NOTICE));
		map.addAttribute("otherfiles",uploadFileService.getFiles(FileType.OTHERS));
	    return view;	
	}
	
	@RequestMapping("/api/list/holiday")
	public String getHolidayFiles(ModelMap map){
		System.out.println("here in uploader");
		String view = "uploads/holiday/listfiles";
		map.addAttribute("holidayfiles",uploadFileService.getFiles(FileType.HOLIDAY));
		return view;	
	}
	
	@RequestMapping("/api/list/leavepolicy")
	public String getLeaveFiles(ModelMap map){
		System.out.println("here in uploader");
		String view = "uploads/leavepolicy/listfiles";
		map.addAttribute("leavepolicyfiles",uploadFileService.getFiles(FileType.LEAVE_POLICY));
		return view;	
	}
	
	@RequestMapping("/api/list/notice")
	public String getNoticeFiles(ModelMap map){
		System.out.println("here in uploader");
		String view = "uploads/notice/listfiles";
		map.addAttribute("noticefiles",uploadFileService.getFiles(FileType.NOTICE));
		return view;	
	}
	
	@RequestMapping("/api/list/other")
	public String getOtherFiles(ModelMap map){
		System.out.println("here in uploader");
		String view = "uploads/other/listfiles";
		map.addAttribute("otherfiles",uploadFileService.getFiles(FileType.OTHERS));
	    return view;	
	}
	
	
	@RequestMapping("/api/uploadform/{type}")
    public String getUploadForm(@PathVariable("type") String type){
		String formView = "";
		if("holiday".equalsIgnoreCase(type.toLowerCase())){
			formView = "uploads/holiday/uploadform";
		}
		else
	    if("leavepolicy".equalsIgnoreCase(type.toLowerCase())){
	    	formView = "uploads/leavepolicy/uploadform";
	    }
	    else
	    if("notice".equalsIgnoreCase(type.toLowerCase())){
	    	formView = "uploads/notice/uploadform";
	    }
	    else
	    if("other".equalsIgnoreCase(type.toLowerCase())){
	    	formView = "uploads/other/uploadform";
	    }
	    return formView;
	}
	
	
	
	@RequestMapping("/api/upload/holiday")
	@ResponseBody
	public ResponseEntity<?> uploadHolidayFile(@ModelAttribute UploadedFile uploadedFile){
		if(uploadedFile.getMultipartFile().isEmpty()){
			return new ResponseEntity<>("Please select a file!",HttpStatus.OK);
		}
		Response response = uploadFileService.uploadHolidayFile(uploadedFile);
		if(response.getCode()==0){
			return new ResponseEntity<>(response.getMessage(),new HttpHeaders(), HttpStatus.OK);
		}
		return new ResponseEntity<>(response.getMessage(),HttpStatus.OK);
	}
	
	
	@RequestMapping("/api/upload/leavepolicy")
	@ResponseBody
	public ResponseEntity<?> uploadLeavePolicyFile(@ModelAttribute UploadedFile uploadedFile){
		if(uploadedFile.getMultipartFile().isEmpty()){
			return new ResponseEntity<>("Please select a file!",HttpStatus.OK);
		}
		Response response = uploadFileService.uploadLeavePolicyFile(uploadedFile);
		if(response.getCode()==0){
			return new ResponseEntity<>(response.getMessage(),new HttpHeaders(), HttpStatus.OK);
		}
		return new ResponseEntity<>(response.getMessage(),HttpStatus.OK);
	}
	
	@RequestMapping("/api/upload/notice")
	@ResponseBody
	public ResponseEntity<?> uploadNoticeFile(@ModelAttribute UploadedFile uploadedFile){
		if(uploadedFile.getMultipartFile().isEmpty()){
			return new ResponseEntity<>("Please select a file!",HttpStatus.OK);
		}
		Response response = uploadFileService.uploadNoticeFile(uploadedFile);
		if(response.getCode()==0){
			return new ResponseEntity<>(response.getMessage(),new HttpHeaders(), HttpStatus.OK);
		}
		return new ResponseEntity<>(response.getMessage(),HttpStatus.OK);
	}
	
	@RequestMapping("/api/upload/other")
	@ResponseBody
	public ResponseEntity<?> uploadOtherFile(@ModelAttribute UploadedFile uploadedFile){
		if(uploadedFile.getMultipartFile().isEmpty()){
			return new ResponseEntity<>("Please select a file!",HttpStatus.OK);
		}
		Response response = uploadFileService.uploadOtherFile(uploadedFile);
		if(response.getCode()==0){
			return new ResponseEntity<>(response.getMessage(),new HttpHeaders(), HttpStatus.OK);
		}
		return new ResponseEntity<>(response.getMessage(),HttpStatus.OK);
	}
	
	@RequestMapping("/api/delete")
	@ResponseBody
	public ResponseEntity<?> deleteFile(@RequestParam int id){
		Response response = uploadFileService.deleteFile(id);
		if(response.getCode()==0){
			return new ResponseEntity<>(response.getMessage(),new HttpHeaders(), HttpStatus.OK);
		}
		return new ResponseEntity<>(response.getMessage(),HttpStatus.OK);
	}
	
	
	@RequestMapping("/api/download")
	public ResponseEntity<ByteArrayResource> downloadFile(@RequestParam int id){
	    DownloadFileResponse downloadFileResponse = uploadFileService.downloadFile(id);  
	    if(downloadFileResponse.getCode()==0){
	    	return ResponseEntity.ok()
		            .header(HttpHeaders.CONTENT_DISPOSITION,
		                  "attachment;filename=" + downloadFileResponse.getPath().getFileName().toString())
		            .contentType(downloadFileResponse.getMediaType()).contentLength(downloadFileResponse.getData().length)
		            .body(downloadFileResponse.getResource());
	    }
	    return ResponseEntity.badRequest().body(null);
	}
	
}
