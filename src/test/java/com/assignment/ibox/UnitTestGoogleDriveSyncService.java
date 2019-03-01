package com.assignment.ibox;

import java.io.File;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.assignment.ibox.service.GoogleDriveSyncService;
import com.google.api.services.drive.Drive;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

public class UnitTestGoogleDriveSyncService extends IboxBaseTest {

	@MockBean
	GoogleDriveSyncService googleDriveSyncService;

	@MockBean
	Drive drive;

	@Test
	public void testAddSuccess() {
		File testFile = null;
		Mockito.when(googleDriveSyncService.onAdd(any())).thenReturn(true);

		boolean status = googleDriveSyncService.onAdd(testFile);

		assertEquals(true, status);
	}

	@Test
	public void testAddFailure() {
		File testFile = null;
		Mockito.when(googleDriveSyncService.onAdd(any())).thenReturn(false);

		boolean status = googleDriveSyncService.onAdd(testFile);

		assertEquals(false, status);
	}

	@Test
	public void testModifySuccess() {
		File testFile = null;
		Mockito.when(googleDriveSyncService.onModify(any())).thenReturn(true);

		boolean status = googleDriveSyncService.onModify(testFile);

		assertEquals(true, status);
	}

	@Test
	public void testModifyFailure() {
		File testFile = null;
		Mockito.when(googleDriveSyncService.onModify(any())).thenReturn(false);

		boolean status = googleDriveSyncService.onAdd(testFile);

		assertEquals(false, status);
	}
}
