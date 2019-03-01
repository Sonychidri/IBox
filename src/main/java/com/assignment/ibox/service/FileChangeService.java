 package com.assignment.ibox.service;

import java.io.File;
import java.time.Duration;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.devtools.filewatch.FileSystemWatcher;
import org.springframework.stereotype.Component;

@Component
public class FileChangeService {

	@Value("${spring.folder.path}")
	String folderPath;

	@Autowired
	FolderChangeListener folderChangeListener;

	@PostConstruct
	private void registerService(){
		FileSystemWatcher watcher = new FileSystemWatcher(false, Duration.ofMillis(1000L), Duration.ofMillis(400L));
		watcher.addSourceFolder(new File(folderPath));
		watcher.addListener(folderChangeListener);
		watcher.start();
	}
}
