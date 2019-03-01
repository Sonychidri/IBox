package com.assignment.ibox;

import java.io.File;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.assignment.ibox.service.GoogleDriveSyncService;

import static org.junit.Assert.assertEquals;

public class IntegrationTestGoogleDriveService extends IboxBaseTest {

	@Autowired
	GoogleDriveSyncService googleDriveSyncService;

	@Test
	public void testSuccessFlow() {
		File tempFile = getTempFile();

		boolean status = googleDriveSyncService.onAdd(tempFile);
		assertEquals(true, status);

		status = googleDriveSyncService.onModify(tempFile);
		assertEquals(true, status);

		status = googleDriveSyncService.onDelete(tempFile);
		assertEquals(true, status);
	}

	@Test
	public void testFailureFlow() {
		File tempFile = getTempFile();

		boolean status = googleDriveSyncService.onDelete(tempFile);
		assertEquals(false, status);
	}
}
