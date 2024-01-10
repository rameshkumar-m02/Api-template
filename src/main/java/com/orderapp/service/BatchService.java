package com.orderapp.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.io.File;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

public class BatchService {
	
	public String executeBatchFile(Map<String,Object>inputParams) {
	        String batchFilePath = "C:/Users/rameshkumar.m/Desktop/Spring/batchfile.bat";
	        String batchFileGitPath = "C:/Users/rameshkumar.m/Desktop/Spring/gitoperation.bat";
	        String projectName = (String)inputParams.get("projectName");
	        System.out.println("projectName"+projectName);
	        String dependency = (String) inputParams.get("dependency");
	        String projectDownloadPath = (String) inputParams.get("projectDownloadPath");
	        String demo_required = (String) inputParams.get("demo_required");
	        String projectPath = "C:\\Users\\rameshkumar.m\\Desktop\\"+projectName;
	        //String propertiesFilePath = projectDownloadPath+"/"+projectName+"/src/main/resources/application.properties";
	        
	      //String[] dependencyList=new String[] {"web","jpa","thymeleaf"};
			/*dependency as a array list*/
			/*List<String> depList = new ArrayList<>();
			depList.add("web");
	        depList.add("jpa");
	        depList.add("thymeleaf");
			String listString = String.join(",", depList);
			String depbatch =  "\""+listString+"\"";
			System.out.println("depbatch------"+depbatch);*/
			
			//String dep = convertStringArrayToString(dependencyList, ",");
			String dependencyParam =  "\""+dependency+"\"";
			System.out.println("dependency------"+dependencyParam);
			//String dependency= "\"web,jpa,\"";

	        try {
	           ProcessBuilder processBuilder = new ProcessBuilder("cmd","/c","start","cmd","/c",batchFilePath,projectName,dependencyParam);
	        // Set the working directory if needed
	           System.out.println("procee.info()==="+processBuilder.command()); 
	         processBuilder.directory(new File("C:\\Users\\rameshkumar.m\\Desktop"));

	        // Start the process
	        Process process = processBuilder.start();
			 int exitCode = process.waitFor();
			 System.out.println("procee.info()==="+process);
			 try { 
					System.out.println( "Invoking the wait() method");
					Thread.sleep(5000);
	            } 
	            catch (InterruptedException e) { 
	                e.printStackTrace(); 
	            } 
			 
			 if(demo_required!=null && demo_required.equalsIgnoreCase("Yes")) {
		    	   //Path source = Paths.get("D:\\sts\\api-template\\src\\main\\resources\\spring-demo\\");
		    	 				         
				  //  Path target = Paths.get(projectDownloadPath+"\\"+projectName+"\\src\\main\\java\\com\\");
				  
				    File srcDir = new File("D:/sts/api-template/src/main/resources/spring-demo");  
				    File destDir = new File("C:/Users/rameshkumar.m/Desktop/"+projectName+"/src/main/java/com");

				   
				    
		          // System.out.println("source="+source);
		          // System.out.println("target="+target);
				    try {
				      //Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
				      FileUtils.copyDirectory(srcDir, destDir);
				      
				    
				    } catch (IOException e) {
				      e.printStackTrace();
				    }	
				    
				} 
			 
			 ProcessBuilder processBuilder2 = new ProcessBuilder("cmd","/c","start","cmd","/c",batchFileGitPath,"rameshkumar-m021","rameshkumar.m@sonata-software.com","Bangalore1234","https://github.com/rameshkumar-m02/sample.git",projectPath,projectName);
		        // Set the working directory if needed
		           System.out.println("procee.info()==="+processBuilder2.command()); 
		           processBuilder2.directory(new File("C:\\Users\\rameshkumar.m\\Desktop"));

		        // Start the process
		        Process process2 = processBuilder2.start();
				 int exitCode2 = process.waitFor();
				 System.out.println("procee.info() exitCode2==="+exitCode2);
			 
			 if (exitCode == 0) {
				      				       
						return "success";
	            } else {
	                System.err.println("Error creating Spring project. Exit code: " + exitCode);
	                return "error";
	            }
	        } catch (IOException |InterruptedException e) {
	            System.err.println("Error executing batch file: " + e.getMessage());
	        }
	        
	        
	        
	        
			return "success";
	}

	private static String convertStringArrayToString(String[] strArr, String delimiter) {
		StringBuilder sb = new StringBuilder();
		for (String str : strArr)
			sb.append(str).append(delimiter);
		return sb.substring(0, sb.length() - 1);
	}
}
