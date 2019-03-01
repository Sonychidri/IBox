package com.assignment.ibox.service;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Files.List;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

@Service
public class GoogleDriveSyncService {
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private Drive drive;

	public boolean onAdd(java.io.File localFile) {
		boolean status = false;
		logger.debug("File added on local system fileName {} ", localFile.getAbsolutePath());

		try {
			File body = new File();
			body.setTitle(localFile.getName());
			FileContent mediaContent = new FileContent("*/*", localFile);
			File serverFile = drive.files().insert(body, mediaContent).execute();
			logger.debug("Uploaded file {} and file id is {}: ", serverFile.getTitle(), serverFile.getId());
			status = true;
		} catch (Exception e) {
			logger.error("Error while uploading file {}", localFile.getAbsolutePath(), e);
		}

		return status;
	}

	public boolean onModify(java.io.File localFile) {
		boolean status = false;
		logger.debug("File Modified on local system fileName {} ", localFile.getAbsolutePath());

		String fileId = getFileId(localFile.getName());

		if (StringUtils.isBlank(fileId)) {
			status = onAdd(localFile);
		} else {
			try {
				File body = new File();
				body.setTitle(localFile.getName());
				FileContent mediaContent = new FileContent("*/*", localFile);
				File serverFile = drive.files().update(fileId, body, mediaContent).execute();
				logger.debug("Modified file {} and file id is {}: ", serverFile.getTitle(), serverFile.getId());
				status = true;
			} catch (Exception e) {
				logger.error("Error while updating file {}", localFile.getAbsolutePath(), e);
			}
		}

		return status;
	}

	public boolean onDelete(java.io.File localFile) {
		boolean status = false;
		logger.debug("File deleted on local system fileName {} ", localFile.getAbsolutePath());

		String fileId = getFileId(localFile.getName());
		if (StringUtils.isNotBlank(fileId)) {
			try {
				drive.files().delete(fileId).execute();
				status = true;
			} catch (Exception e) {
				logger.error("Error while deleting the file {}", localFile.getAbsolutePath(), e);
			}
		}

		return status;
	}

	public String getFileId(String fileName) {
		try {
			List request = drive.files().list();
			FileList files = request.execute();
			for (File file : files.getItems()) {
				if (file.getTitle().equals(fileName)) {
					return file.getId();
				}
			}
		} catch (IOException e) {
			logger.error("Error occurred while listing all files ", e);
		}

		logger.error("File fileName {} not found on server " , fileName);
		return null;
	}

}
