package com.assignment.ibox;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.assignment.ibox.domain.GoogleDriveProps;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;

@Configuration
public class ApplicationConfig {
	@Autowired
	private GoogleDriveProps googleDriveProps;

	@Bean
	public Drive createDrive() throws GeneralSecurityException, IOException {
		JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
		FileDataStoreFactory dataStoreFactory;
		HttpTransport httpTransport;
		Drive drive = null;

		try {
			httpTransport = GoogleNetHttpTransport.newTrustedTransport();
			dataStoreFactory = new FileDataStoreFactory(new java.io.File("./store/drive_sample"));

			GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(httpTransport, JSON_FACTORY, googleDriveProps.getClientId(),
					googleDriveProps.getClientSecret(), Collections.singleton(DriveScopes.DRIVE_FILE)).setDataStoreFactory(dataStoreFactory).build();
			// authorize
			Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
			drive = new Drive.Builder(httpTransport, JSON_FACTORY, credential).setApplicationName(googleDriveProps.getApplicationName()).build();
		} catch (Exception e) {
			System.err.println(e);
			throw e;
		}

		return drive;
	}
}
