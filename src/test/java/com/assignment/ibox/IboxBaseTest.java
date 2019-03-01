package com.assignment.ibox;

import java.io.File;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
abstract class IboxBaseTest {

	protected File getTempFile(){
		File testFile = null;

		try {
			testFile = File.createTempFile("ibox-test1-", ".tmp");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return testFile;
	}

	protected void deleteTempFile(File testFile){
		if (null != testFile && !testFile.isDirectory()) {
			testFile.delete();
		}
	}
}
