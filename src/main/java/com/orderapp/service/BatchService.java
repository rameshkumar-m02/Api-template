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
import java.io.FileReader;
import java.io.FileWriter;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.orderapp.model.CloudConfig;


@Service
public class BatchService {
	
	@Value("${gitCodePath}")
	private String gitCodePath;
	
	@Value("${codegenerateBatch}")
	private String codegenerateBatch;
	
	@Value("${gitprocessbatch}")
	private String gitprocessbatch;
	
	@Value("${springDemo}")
	private String springDemo;
	
	@Value("${demoDestBase}")
	private String demoDestBase;
	
	@Value("${helloTemplate}")
	private String helloTemplate;
	
	@Value("${mainTemplate}")
	private String mainTemplate;
	
	@Value("${variableTemplate}")
	private String variableTemplate;
	
	@Value("${terraformTemplate}")
	private String terraformTemplate;
	
	@Value("${prerequisites}")
	private String prerequisites;
	
	
	public String executeBatchFile(Map<String,Object>inputParams) {
	        String batchFilePath = codegenerateBatch;
	        String batchFileGitPath = gitprocessbatch;
	        String projectName = (String)inputParams.get("projectName");
	        System.out.println("projectName"+projectName);
	        String dependency = (String) inputParams.get("dependency");
	        String projectDownloadPath = (String) inputParams.get("projectDownloadPath")+"\\";
	        String demo_required = (String) inputParams.get("demo_required");
	        String projectPath = projectDownloadPath+"\\"+projectName;
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
	           System.out.println("projectDownloadPath==="+projectDownloadPath);
	         processBuilder.directory(new File(projectDownloadPath));

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
			 
			 try {
				 FileReader fr = new FileReader(helloTemplate);
		         
		            FileWriter fw = new FileWriter(projectDownloadPath+"\\"+projectName+"\\src\\main\\java\\com\\mydomain\\Hello.java");
		            String str = "";
		            int i;
		            while ((i = fr.read()) != -1) {
		                 str += (char)i;
		            }
		            String writeStr =str.replace("serviceName", projectName);
		            fw.write(writeStr);
		         
		            fr.close();
		            fw.close();	 
			 if(demo_required!=null && demo_required.equalsIgnoreCase("Yes")) {
		    	   //Path source = Paths.get("D:\\sts\\api-template\\src\\main\\resources\\spring-demo\\");
		    	 				         
				  //  Path target = Paths.get(projectDownloadPath+"\\"+projectName+"\\src\\main\\java\\com\\");
				  
				    File srcDir = new File(springDemo);  
				    File destDir = new File(demoDestBase+projectName+"/src/main/java/com");

				   
				    
		          // System.out.println("source="+source);
		          // System.out.println("target="+target);
				    
				      //Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
				      FileUtils.copyDirectory(srcDir, destDir);
				      
				    
				    
				    
				} 
			    } catch (IOException e) {
			      e.printStackTrace();
			    }	
			
			cloudConfiguration(inputParams);
			 
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
	
	
	public String buildAndPushDockerImage(Map<String,Object>inputParams) {
		 String dockerPushScriptfilePath = (String) inputParams.get("dockerPushScriptfilePath");
		 String dockerImageName = (String) inputParams.get("projectName");
		 String projectDownloadPath =(String) inputParams.get("projectDownloadPath");
		 String projectPath = (String) inputParams.get("projectDownloadPath")+"\\"+(String) inputParams.get("projectName");
	     System.out.println("docker projectPath--------------"+projectPath); 
		 // Start the process
		try {
			 
			 ProcessBuilder processBuilder2 = new ProcessBuilder("cmd","/c","start","cmd","/c",dockerPushScriptfilePath,dockerImageName.toLowerCase(),projectPath);
		     System.out.println("buildAndPushDockerImage.info()==="+processBuilder2.command()); 
		     processBuilder2.directory(new File(projectDownloadPath));
			 Process process2 = processBuilder2.start();
			 int exitCode2 = process2.waitFor();
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		return "success";
	}
	
	
	public String cloudConfiguration(Map<String,Object>inputParams) {
		
		try {
			 FileReader frmain = new FileReader(mainTemplate);
			 FileReader frvar = new FileReader(variableTemplate);
			 FileReader frtera = new FileReader(terraformTemplate);
			 FileReader frprereq = new FileReader(prerequisites);
			 String projectDownloadPath=(String) inputParams.get("projectDownloadPath")+"\\";
			 String projectName= (String)inputParams.get("projectName");
			 String regione= (String)inputParams.get("regione");
			 String zone= (String)inputParams.get("zone");
			 String accessKey= (String)inputParams.get("accessKey");
			 String secretKey= (String)inputParams.get("secretKey");
			 String serverType= (String)inputParams.get("serverType");
			 String cloudUname= (String)inputParams.get("cloudUname");
			 String masterCount= (String)inputParams.get("masterCount");
			 String nodeCount= (String)inputParams.get("nodeCount");
			 String machineType= (String)inputParams.get("machineType");
			 String nodeType= (String)inputParams.get("nodeType");
			 
			 File f1 = new File(projectDownloadPath+"\\"+projectName+"\\cloud-config"); 
			 f1.mkdir();
	            FileWriter fwmain = new FileWriter(projectDownloadPath+"\\"+projectName+"\\cloud-config\\main.tf");
	            FileWriter fwvar = new FileWriter(projectDownloadPath+"\\"+projectName+"\\cloud-config\\variable.tf");
	            FileWriter fwtera = new FileWriter(projectDownloadPath+"\\"+projectName+"\\cloud-config\\terraform.tfvars");
	            FileWriter fwprereq = new FileWriter(projectDownloadPath+"\\"+projectName+"\\cloud-config\\prerequisites.sh");
	            
	            String strmain = "";
	            int maini;
	            while ((maini = frmain.read()) != -1) {
	            	strmain += (char)maini;
	            }
	           // String writeStr =str.replace("serviceName", projectName);
	            fwmain.write(strmain);
	         
	            frmain.close();
	            fwmain.close();	 
	            
	            String strvar = "";
	            int vari;
	            while ((vari = frvar.read()) != -1) {
	            	strvar += (char)vari;
	            }
	           // String writeStr =str.replace("serviceName", projectName);
	            fwvar.write(strvar);
	         
	            frvar.close();
	            fwvar.close();	 
	            
	            
	            String strtera = "";
	            int terai;
	            while ((terai = frtera.read()) != -1) {
	            	strtera += (char)terai;
	            }
	            String writeStr =strtera.replace("region_value", regione);
	            writeStr =writeStr.replace("access_key_value", accessKey);
	            writeStr =writeStr.replace("secret_key_value", secretKey);
	            writeStr =writeStr.replace("jenkins_server_type_value", serverType);
	            fwtera.write(writeStr);
	         
	            frtera.close();
	            fwtera.close();	 
	            
	            String strprereq = "";
	            int prei;
	            while ((prei = frprereq.read()) != -1) {
	            	strprereq += (char)prei;
	            }
	            String writePre =strprereq.replace("region_value", regione);
	            writePre =writePre.replace("zone_value", zone);
	            writePre =writePre.replace("master_count_value", masterCount);
	            writePre =writePre.replace("machine_type", machineType);
	            writePre =writePre.replace("node_count_value", nodeCount);
	            writePre =writePre.replace("node_type", nodeType);
	            
	            fwprereq.write(writePre);
	         
	            frprereq.close();
	            fwprereq.close();	 
		
		    } catch (IOException e) {
		      e.printStackTrace();
		    }	
		return "success";
		
	}
}
