package org.py;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.py.util.FilesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import junit.framework.TestCase;
@RunWith(SpringRunner.class)
@SpringBootTest
public class SystemBeansTest {
	@Autowired
	private SystemBeans systemBeans;
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private FilesUtil fileutil;
	
	@Test
	public void test() {
		TestCase.assertNotNull(systemBeans);
	}
	
	@Test
	public void fileUtil() throws IOException {
		TestCase.assertNotNull(fileutil);
	}

}
