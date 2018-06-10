package com.sahil.services;

import java.util.List;

import com.sahil.entities.UploadedFileEntity;
import com.sahil.enums.FileType;
import com.sahil.models.HolidayResponse;
import com.sahil.models.Response;
import com.sahil.models.file.response.DownloadFileResponse;
import com.sahil.models.file.response.FileDetailsDTO;
import com.sahil.models.file.uploads.UploadedFile;

public interface UploadFileService {

	public Response uploadHolidayFile(UploadedFile uploadedFile);

	public Response uploadNoticeFile(UploadedFile uploadedFile);

	public Response uploadLeavePolicyFile(UploadedFile uploadedFile);

	public Response uploadOtherFile(UploadedFile uploadedFile);

	public DownloadFileResponse downloadFile(int id);

	public Response deleteFile(int id);

	public List<FileDetailsDTO> getFiles(FileType fileType);

	public boolean isHolidayFileUploadedForCurrentYear();

	public UploadedFileEntity getHolidayFileForCurrentYear();

	public HolidayResponse getHolidayDataForCurrentYear();
}
