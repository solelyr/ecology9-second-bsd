package base;


import org.junit.Before;
import weaver.general.GCONST;

import java.io.File;

public class BaseTest {

	@Before
	public void before() {
		GCONST.setServerName("ecology");
		String fileSeparator = File.separator;
		String sysPath = System.getProperty("user.dir")+fileSeparator+"ecology"+fileSeparator;
		System.out.println("sysPath===>"+sysPath);
		GCONST.setRootPath(sysPath);
	}

}
