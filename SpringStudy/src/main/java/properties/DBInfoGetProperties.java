package properties;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DBInfoGetProperties {
	public static String propertiesPath = "D:\\ProjectSpring\\DBInfo\\db.properties";
	// public static String propertiesPath = "E:\\ProjectStpring\\DBInfo\\db.properties";
	private Properties properties;
	
	public DBInfoGetProperties() {
		properties = new Properties();
	}

	public Properties getProperties() {
		return properties;
	}
	
	public void loadProp(String path) throws IOException {
        InputStream inputStream = getClass().getResourceAsStream(path);
        properties.load(inputStream);
        inputStream.close();
    }
	public Properties defaultLoadPropFor() throws IOException {
		return loadPropForStatic(propertiesPath);
	}
	
	public Properties loadPropForStatic(String path) throws IOException {
        Properties properties = new Properties();
        FileInputStream fis = new FileInputStream(path);
        properties.load(new BufferedInputStream(fis));
        fis.close();
        return properties;
	}
	
}
