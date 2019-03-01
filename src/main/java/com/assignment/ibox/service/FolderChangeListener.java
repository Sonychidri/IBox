package com.assignment.ibox.service;


import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.devtools.filewatch.ChangedFiles;
import org.springframework.boot.devtools.filewatch.FileChangeListener;
import org.springframework.stereotype.Component;

@Component
public class FolderChangeListener implements FileChangeListener {
	@Autowired
	private GoogleDriveSyncService googleDriveSyncService;

	@Override
	public void onChange(Set<ChangedFiles> changeSet) {
		changeSet.stream().forEach(changedFiles -> {
			changedFiles.getFiles().stream().forEach(changedFile -> {
				switch (changedFile.getType()) {
				case ADD:
					googleDriveSyncService.onAdd(changedFile.getFile());
					break;
				case MODIFY:
					googleDriveSyncService.onModify(changedFile.getFile());
					break;
				case DELETE:
					googleDriveSyncService.onDelete(changedFile.getFile());
					break;
				}
			});
		});
	}
}
