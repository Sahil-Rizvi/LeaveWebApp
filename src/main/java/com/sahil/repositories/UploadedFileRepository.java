package com.sahil.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sahil.entities.UploadedFileEntity;
import com.sahil.enums.FileType;

@Repository
public interface UploadedFileRepository extends JpaRepository<UploadedFileEntity,Integer>{
	public UploadedFileEntity findByFileName(String name);
	public List<UploadedFileEntity> findByFileType(FileType fileType);
}
