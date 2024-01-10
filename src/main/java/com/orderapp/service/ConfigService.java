package com.orderapp.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.Properties;

public class ConfigService {

	public void UpdateProp(Map<String,Object>inputParams) {
		try{
			String propertiesFilePath = (String) inputParams.get("propertiesFilePath");
			OutputStream output = new FileOutputStream(propertiesFilePath);
            Properties prop = new Properties();
            String db_type = (String) inputParams.get("db_type");
            
            if(db_type.equalsIgnoreCase("mongodb")) {
            	 prop.setProperty("spring.data.mongodb.host", (String) inputParams.get("db_url"));
                 prop.setProperty("spring.data.mongodb.username",(String) inputParams.get("db_username"));
                 prop.setProperty("spring.data.mongodb.password", (String) inputParams.get("db_pwd"));
                 prop.setProperty("spring.datasource.port", "27017");
            }else {
            	// set the properties value
                prop.setProperty("spring.datasource.url", (String) inputParams.get("db_url"));
                prop.setProperty("spring.datasource.username",(String) inputParams.get("db_username"));
                prop.setProperty("spring.datasource.password", (String) inputParams.get("db_pwd"));
                prop.setProperty("spring.jpa.hibernate.ddl-auto", "update");
                prop.setProperty("spring.jpa.show-sql", "true");
            }

            

            // save properties to project root folder
            prop.store(output, null);

            System.out.println(prop);

        } catch (IOException io) {
            io.printStackTrace();
        }

	}
	
	/*create docker file*/
	public void generateDockerFile(Map<String,Object>inputParams) throws IOException {
		FileOutputStream fileOutputStream = null;
	    try{
	    	String projectName = (String)inputParams.get("projectName");
	    	String projectDownloadPath = (String) inputParams.get("projectDownloadPath");
	    	String dockerPath = projectDownloadPath+"\\"+projectName+"\\Dockerfile";
	    	fileOutputStream = new FileOutputStream(dockerPath);
	    	String text= "FROM openjdk:17\nADD target/"+projectName+"-0.0.1-SNAPSHOT.jar "+projectName+"-0.0.1-SNAPSHOT.jar\nENTRYPOINT [\"java\",\"-jar\",\"/"+projectName+"-0.0.1-SNAPSHOT.jar\"]";
	    	byte[] strToBytes = text.getBytes();
	    	fileOutputStream.write(strToBytes);
	    }catch (IOException e) {
            // Display the exception/s
            System.out.print(e.getMessage());
        }
	    finally {
			if (fileOutputStream != null) {
				try {
 
					fileOutputStream.close();
				}
 
				catch (IOException e) {
					System.out.print(e.getMessage());
				}
			}
	    }
	}
}
