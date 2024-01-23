package com.orderapp.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.RemoteAddCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.RemoteConfig;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.nio.file.*;
import com.orderapp.bo.TemplateRequest;
import com.orderapp.model.ServiceDetails;
import com.orderapp.model.Template;
import com.orderapp.repository.TemplateRepo;
import com.orderapp.service.TemplateService;
import com.orderapp.service.BatchService;
import com.orderapp.service.ConfigService;
import com.orderapp.model.CloudConfig;
import com.orderapp.repository.ConfigRepo;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class TemplateServiceImpl implements TemplateService{

	@Autowired
    private TemplateRepo templateRepo;
	
	@Autowired
    private ConfigRepo configRepo;
	
	@Autowired
	private BatchService batchsvc;
	@Value("${projectDownloadPath}")
	private String projectcreatePath;

	@Value("${gitPullbatchFilePath}")
	private String gitPullbatchFilePath;
	
	@Value("${gitCodePath}")
	private String gitCodePath;
	
	@Value("${codegenerateBatch}")
	private String codegenerateBatch;
	
	@Value("${gitprocessbatch}")
	private String gitprocessbatch;
	
	@Value("${springDemo}")
	private String springDemo;
	
	@Value("${dockerPushScriptfilePath}")
	private String dockerPushScriptfilePath;
	
	@Value("${gitprocessCloud}")
	private String gitprocessCloud;
	
	
	
	@Override
	public Template createTemplate(Template template) {
		//String projectDownloadPath = "C:\\Users\\rameshkumar.m\\Desktop";
		String projectDownloadPath = projectcreatePath;
      //  String propertiesFilePath = projectDownloadPath+"\\"+serviceDetails.getProject_name()+"\\src\\main\\resources\\application.properties";
		/*calling batch file for creating a spring project*/
		//BatchService batchsvc = new BatchService();
		ConfigService config = new ConfigService();
		List<ServiceDetails> serDetails = template.getServiceDetails();
		  //String batchFileGitPath = "C:/Users/rameshkumar.m/Desktop/Spring/gitpull.bat";
		Process process = null;
		String batchFileGitPath = gitPullbatchFilePath;
		String processbatch = gitprocessbatch;
		String projectName= null;
		String projectPath = null;
		 ProcessBuilder processBuilder = new ProcessBuilder("cmd","/c","start","cmd","/c",batchFileGitPath,template.getRepo_user_name(),template.getRepo_user_email(),template.getRepo_user_pwd(),template.getRepo_url(),template.getTemplateName());
		 try {
			 System.out.println("procee.info()==="+processBuilder.command()); 
	         //processBuilder.directory(new File("C:\\Users\\rameshkumar.m\\Desktop\\code"));
	         processBuilder.directory(new File(gitCodePath));
			 process = processBuilder.start();
			int exitCode = process.waitFor();
			 System.out.println("clone process.info()exitCode==="+exitCode);
		} catch (IOException e) {
				e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 for (ServiceDetails serviceDetails : serDetails) {
		 String propertiesFilePath = projectDownloadPath+"\\"+serviceDetails.getProject_name()+"\\src\\main\\resources\\application.properties";	
		Map<String,Object> inputParams = new HashMap<String,Object>();
		projectName = serviceDetails.getProject_name();
		projectPath = projectDownloadPath+"\\"+projectName;
		inputParams.put("projectName", serviceDetails.getProject_name());
		if(serviceDetails.getDb_type().equalsIgnoreCase("mongodb")) {
			inputParams.put("dependency", serviceDetails.getDependencyList()+",data-mongodb");
		}else {
			inputParams.put("dependency", serviceDetails.getDependencyList()+","+serviceDetails.getDb_type());
		}
		inputParams.put("db_type", serviceDetails.getDb_type());
		inputParams.put("db_url", serviceDetails.getDb_url());
		inputParams.put("db_username", serviceDetails.getDb_username());
		inputParams.put("db_pwd", serviceDetails.getDb_pwd());
		inputParams.put("propertiesFilePath", propertiesFilePath);
		inputParams.put("projectDownloadPath", projectDownloadPath);
		inputParams.put("demo_required", serviceDetails.getDemo_required());
		inputParams.put("dockerPushScriptfilePath", dockerPushScriptfilePath);
		inputParams.put("regione", template.getRegione());
		inputParams.put("zone", template.getZone());
		inputParams.put("accessKey", template.getAccessKey());
		inputParams.put("secretKey", template.getSecretKey());
		inputParams.put("serverType", template.getServerType());
		inputParams.put("cloudUname", template.getCloudUname());
		inputParams.put("masterCount", template.getMasterCount());
		inputParams.put("nodeCount", template.getNodeCount());
		inputParams.put("machineType", template.getMachineType());
		inputParams.put("nodeType", template.getNodeType());
		
		String status = batchsvc.executeBatchFile(inputParams);
		
		
	       
		/*updating the properties file*/
		if(status.equalsIgnoreCase("success")) {
			try { 
				System.out.println( "Invoking the wait() method processbatch"+processbatch);
				Thread.sleep(6000);
				config.UpdateProp(inputParams);
				config.generateDockerFile(inputParams);
				
				 
				 ProcessBuilder processBuilder2 = new ProcessBuilder("cmd","/c","start","cmd","/c",processbatch,template.getRepo_user_name(),template.getRepo_user_email(),template.getRepo_user_pwd(),template.getRepo_url(),projectPath,projectName,template.getTemplateName());
			        // Set the working directory if needed
			           System.out.println("procee.info()==="+processBuilder2.command()); 
			           processBuilder2.directory(new File(projectcreatePath));

			        // Start the process
			        Process process2 = processBuilder2.start();
					 int exitCode2 = process2.waitFor();
					 System.out.println("procee.info() exitCode2==="+exitCode2);
					//pushing the docker image to docker hub
					 batchsvc.buildAndPushDockerImage(inputParams);
				 
				 
            } 
		            catch (InterruptedException | IOException e) { 
                e.printStackTrace(); 
            } 
			
			/*
			 * if(template.getDemo_required()!=null &&
			 * template.getDemo_required().toString().equalsIgnoreCase("Yes")) { Path source
			 * = Paths.get("D:\\sts\\api-template\\src\\main\\resources\\spring-demo\\");
			 * 
			 * Path target =
			 * Paths.get(projectDownloadPath+"\\"+template.getProject_name()+"\\src\\main\\
			 * java\\com\\");
			 * 
			 * File srcDir = new File("D:/sts/api-template/src/main/resources/spring-demo");
			 * File destDir = new
			 * File("C:/Users/rameshkumar.m/Desktop/"+template.getProject_name()+
			 * "/src/main/java/com");
			 * 
			 * 
			 * 
			 * System.out.println("source="+source); System.out.println("target="+target);
			 * try { //Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
			 * FileUtils.copyDirectory(srcDir, destDir);
			 * 
			 * 
			 * } catch (IOException e) { e.printStackTrace(); }
			 * 
			 * }
			 */
		}
		
		/*
		 * FileOutputStream fos; try { fos = new
		 * FileOutputStream(projectDownloadPath+"\\"+serviceDetails.getProject_name()+".
		 * zip"); ZipOutputStream zipOut = new ZipOutputStream(fos);
		 * System.out.println("fos===="+fos); File fileToZip = new
		 * File(projectDownloadPath+"\\"+serviceDetails.getProject_name()+"\\");
		 * System.out.println("fileToZip===="+fileToZip); zipFile(fileToZip,
		 * fileToZip.getName(), zipOut);
		 * 
		 * 
		 * 
		 * 
		 * System.out.println("zipOut===="+zipOut); zipOut.close();
		 * System.out.println("close===="); fos.close(); } catch (IOException e) { //
		 * TODO Auto-generated catch block e.printStackTrace(); }
		 */
		}
		       
		return templateRepo.save(template);
	}


	@Override
	public List<Template> getTemplateList() {
		
		return templateRepo.findAll();
	}

	@Override
	public Template getTemplateDetail(Integer templateId) {
		
		return templateRepo.getById(templateId);
	}

	@Override
	public ResponseEntity<Resource> generateDynamicTemplate(TemplateRequest template, HttpServletRequest request) {
		String repoUrl = "https://github.com/rameshkumar-m02/spring-postgres.git";
		if(template.getTechnology().equalsIgnoreCase("django")) {
			repoUrl ="https://github.com/rameshkumar-m02/django-postgres.git";
		} else if(template.getTechnology().equalsIgnoreCase("flask")) {
			repoUrl ="https://github.com/rameshkumar-m02/flask-postgres.git";
		}
		Resource resource =null;
		
		try {
				/*Git git = Git.init().setDirectory(Paths.get("D:\\mysample\\sample").toFile()).call()){
			git.remoteAdd().setName("origin");
			RemoteAddCommand remoteAdd = git.remoteAdd();
			  remoteAdd.setName("origin");
			  remoteAdd.setUri(new URIish("https://github.com/rameshkumar-m02/sample.git"));
			  System.out.println("remoteAdd"+remoteAdd.toString());
			  RemoteConfig config = remoteAdd.call();
			 System.out.println("config"+config.getURIs());*/
				
		    Git.cloneRepository().setURI("https://github.com/rameshkumar-m02/sample.git")
		        .setDirectory(Paths.get("D:\\mysample\\sample\\").toFile())
		        .call();		
			 Path source = Paths.get("D:\\sts\\api-template\\src\\main\\resources\\"+template.getTechnology()+"-"+template.getDatabase()+"\\userOperation.py");
			    
			    Path target = Paths.get("D:\\mysample\\sample\\"+"userOperation.py");
			  
	           
			    try {
			      Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
			    
			    
			    } catch (IOException e) {
			      e.printStackTrace();
			    }	
			 pushChanges();
			}catch(GitAPIException ex){
			  ex.printStackTrace();
			} 
			/*
			 * catch (URISyntaxException e) {
			 * 
			 * e.printStackTrace(); }
			 */
		
		long locaTime = System.currentTimeMillis();
		String dirName=template.getTechnology()+"-"+template.getDatabase()+locaTime;
		String cloneDirectoryPath = "D:\\sts\\codebase\\"+template.getTechnology()+"-"+template.getDatabase()+locaTime+"\\"; // Ex.in windows c:\\gitProjects\SpringBootMongoDbCRUD\
		if(template.getRequireGraphql() !=null && template.getRequireGraphql().equalsIgnoreCase("Yes")) {
			 cloneDirectoryPath = "D:\\sts\\codebase\\"+template.getTechnology()+"-"+template.getDatabase()+"-graphql"+locaTime+"\\";
			 dirName=template.getTechnology()+"-"+template.getDatabase()+"-graphql"+locaTime;
		}
		try {
		    System.out.println("Cloning "+repoUrl+" into "+repoUrl);
		    Git.cloneRepository()
		        .setURI(repoUrl)
		        .setDirectory(Paths.get(cloneDirectoryPath).toFile())
		        .call();
		    //File file = new File("D:\\sts\\api-template\\src\\main\\resources\\"+template.getTechnology()+"-"+template.getDatabase()+"\\pom1.xml");
		   // if(file.renameTo
		     //       (new File(cloneDirectoryPath+"pom2.xml")))
		    if(template.getTechnology().equalsIgnoreCase("spring")) {
		    Path source = Paths.get("D:\\sts\\api-template\\src\\main\\resources\\"+template.getTechnology()+"-"+template.getDatabase()+"\\pom.xml");
		    if(template.getRequireGraphql() !=null && template.getRequireGraphql().equalsIgnoreCase("Yes")) {
		    	source = Paths.get("D:\\sts\\api-template\\src\\main\\resources\\"+template.getTechnology()+"-"+template.getDatabase()+"-graphql\\pom.xml");
		    }
		    Path target = Paths.get(cloneDirectoryPath+"pom.xml");
		    Path propSource = Paths.get("D:\\sts\\api-template\\src\\main\\resources\\"+template.getTechnology()+"-"+template.getDatabase()+"\\application.properties");
		    Path propTarget = Paths.get(cloneDirectoryPath+"src\\main\\resources\\application.properties");
             
		    try {
		      Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
		      Files.copy(propSource, propTarget, StandardCopyOption.REPLACE_EXISTING);
		      if(template.getRequireGraphql() !=null && template.getRequireGraphql().equalsIgnoreCase("Yes")) {
		    	  Path graphSource = Paths.get("D:\\sts\\api-template\\src\\main\\resources\\"+template.getTechnology()+"-"+template.getDatabase()+"-graphql\\schema.graphqls");
				  Path graphTarget = Paths.get(cloneDirectoryPath+"src\\main\\resources\\graphql\\schema.graphqls");
				  File dir=new File(cloneDirectoryPath+"src\\main\\resources\\graphql");
			       dir.mkdir();
				  
				  Files.copy(graphSource, graphTarget, StandardCopyOption.REPLACE_EXISTING);
				  Path controllerSource = Paths.get("D:\\sts\\api-template\\src\\main\\resources\\"+template.getTechnology()+"-"+template.getDatabase()+"-graphql\\ProductController.java");
				  Path controllerTarget = Paths.get(cloneDirectoryPath+"src\\main\\java\\com\\orderapp\\controller\\ProductController.java"); 
				  Files.copy(controllerSource, controllerTarget, StandardCopyOption.REPLACE_EXISTING);
		      }
		    
		    } catch (IOException e) {
		      e.printStackTrace();
		    }	
		    System.out.println("Completed Cloning");
		
		
		} else if(template.getTechnology().equalsIgnoreCase("django")) {
			
		  Path source = Paths.get("D:\\sts\\api-template\\src\\main\\resources\\"+template.getTechnology()+"-"+template.getDatabase()+"\\settings.py");
		  Path urlSource = Paths.get("D:\\sts\\api-template\\src\\main\\resources\\"+template.getTechnology()+"-"+template.getDatabase()+"\\urls.py"); 
		  Path apiurlSource = Paths.get("D:\\sts\\api-template\\src\\main\\resources\\"+template.getTechnology()+"-"+template.getDatabase()+"\\api\\urls.py"); 
		
		
		  if(template.getRequireGraphql() !=null && template.getRequireGraphql().equalsIgnoreCase("Yes")) {
		    	source = Paths.get("D:\\sts\\api-template\\src\\main\\resources\\"+template.getTechnology()+"-"+template.getDatabase()+"-graphql\\settings.py");
		    	urlSource = Paths.get("D:\\sts\\api-template\\src\\main\\resources\\"+template.getTechnology()+"-"+template.getDatabase()+"-graphql\\urls.py"); 
		    	
		    	apiurlSource = Paths.get("D:\\sts\\api-template\\src\\main\\resources\\"+template.getTechnology()+"-"+template.getDatabase()+"-graphql\\api\\urls.py"); 
		  }
		    Path target = Paths.get(cloneDirectoryPath+"product_api\\settings.py");
		    Path urlTarget = Paths.get(cloneDirectoryPath+"product_api\\urls.py");
		    Path apiurlTarget = Paths.get(cloneDirectoryPath+"api\\urls.py");
           
		    try {
		      Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
		      Files.copy(urlSource, urlTarget, StandardCopyOption.REPLACE_EXISTING);
		      Files.copy(apiurlSource, apiurlTarget, StandardCopyOption.REPLACE_EXISTING);
		      if(template.getRequireGraphql() !=null && template.getRequireGraphql().equalsIgnoreCase("Yes")) {
		    	  Path schemaSource = Paths.get("D:\\sts\\api-template\\src\\main\\resources\\"+template.getTechnology()+"-"+template.getDatabase()+"-graphql\\api\\schema.py");
		    	  Path mutationSource = Paths.get("D:\\sts\\api-template\\src\\main\\resources\\"+template.getTechnology()+"-"+template.getDatabase()+"-graphql\\api\\mutations.py");
		    	  
		    	 
			      Path  schemaTarget = Paths.get(cloneDirectoryPath+"api\\schema.py");
			      Path mutationTarget = Paths.get(cloneDirectoryPath+"api\\mutations.py");
				  
				  Files.copy(schemaSource, schemaTarget, StandardCopyOption.REPLACE_EXISTING);
				  
				  Files.copy(mutationSource, mutationTarget, StandardCopyOption.REPLACE_EXISTING);
		      }
		    
		    } catch (IOException e) {
		      e.printStackTrace();
		    }	
		    System.out.println("Completed Cloning");
		} 
		    
		
		 else if(template.getTechnology().equalsIgnoreCase("flask")) {
				
			  Path source = Paths.get("D:\\sts\\api-template\\src\\main\\resources\\"+template.getTechnology()+"-"+template.getDatabase()+"\\userOperation.py");
			    
			    Path target = Paths.get(cloneDirectoryPath+"userOperation.py");
			  
	           
			    try {
			      Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
			    
			    
			    } catch (IOException e) {
			      e.printStackTrace();
			    }	
			    System.out.println("Completed Cloning");
			} 
		    
		    //String sourceFile = "zipTest";
	        FileOutputStream fos = new FileOutputStream("D:\\sts\\codebase\\"+dirName+".zip");
	        ZipOutputStream zipOut = new ZipOutputStream(fos);
	        System.out.println("fos===="+fos);
	        File fileToZip = new File(cloneDirectoryPath);
	        System.out.println("fileToZip===="+fileToZip);
	        zipFile(fileToZip, fileToZip.getName(), zipOut);
	        
	        
	        
	        
	        System.out.println("zipOut===="+zipOut);
	        zipOut.close();
	        System.out.println("close====");
	        fos.close();  
	        
	        Path filePath = Paths.get("D:\\sts\\codebase\\"+dirName+".zip");
             resource = new UrlResource(filePath.toUri());
            
	        
	        
		} catch (GitAPIException e) {
		    System.out.println("Exception occurred while cloning repo");
		    e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
           
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        System.out.println("resource.getFilename()"+resource.getFilename());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);

		
		
	  
		
		//return "Application Generated";
	}


	 private static void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
	        if (fileToZip.isHidden()) {
	            return;
	        }
	        if (fileToZip.isDirectory()) {
	            if (fileName.endsWith("/")) {
	            	System.out.println("fileName============"+fileName);
	                zipOut.putNextEntry(new ZipEntry(fileName));
	                zipOut.closeEntry();
	            } else {
	                zipOut.putNextEntry(new ZipEntry(fileName + "/"));
	                zipOut.closeEntry();
	            }
	            File[] children = fileToZip.listFiles();
	            for (File childFile : children) {
	                zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
	            
	            }
	            return;
	        }
	        FileInputStream fis = new FileInputStream(fileToZip);
	        ZipEntry zipEntry = new ZipEntry(fileName);
	        System.out.println("zipEntry-------------"+zipEntry);
	        zipOut.putNextEntry(zipEntry);
	        byte[] bytes = new byte[1024];
	        int length;
	        while ((length = fis.read(bytes)) >= 0) {
	            zipOut.write(bytes, 0, length);
	            System.out.println("zipOut-------------"+zipOut);
	        }
	        fis.close();
	    }

	@Override
	public String deleteOldCodebase() {
		 boolean delSource = false;
		 File dirertory = new File("D:\\sts\\codebase");
		 File[] listOfFiles = dirertory.listFiles();
	        long purgeTime = System.currentTimeMillis() - (4 * 24 * 60 * 60 * 1000);
	        for (File dir : listOfFiles) {
	        	
	        if (dir.lastModified()< purgeTime) delSource = true;
	        if (dir.isDirectory()) {
	            File[] files = dir.listFiles();
	            for (File aFile : files) {
	                if (aFile.lastModified() < purgeTime) {
	                    aFile.delete();
	                }
	            }
	        }
	        if(delSource) dir.delete();
	        }
		return "Older files deleted";
	}
	

	private void pushChanges() {
		   try (Git git = Git.open(Paths.get("D:\\mysample\\sample").toFile())) {
			   git.branchCreate().setName("master").call();
		       git.add().addFilepattern(".").call();
		       git.commit().setMessage( "updated for changes on: " + LocalDate.now()).call();
		       PushCommand push = git.push();
		       push.setRemote( "https://github.com/rameshkumar-m02/sample.git" ).setRefSpecs( new RefSpec( "refs/heads/master:refs/heads/master" ) );
		       UsernamePasswordCredentialsProvider credentialsProvider = new UsernamePasswordCredentialsProvider("rameshkumar-m02", "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");
		       push.setCredentialsProvider(credentialsProvider);
		       //git.push().setRemote( "https://github.com/rameshkumar-m02/my-demo2.git" ).setRefSpecs( new RefSpec( "refs/heads/*:refs/remotes/origin/*" ) ).call();
		      // preparePushCommand(git).setRemote("origin").setRemote("https://github.com/rameshkumar-m02/my-demo1.git").setRefSpecs(new RefSpec("refs/heads/main:refs/heads/main")).call();
		       push.call();
		   } catch (IOException | GitAPIException exception) {
		      // log.error("exception occurred while pulling changes from remote repo");
			   exception.printStackTrace();
		   }
		}
		private PushCommand preparePushCommand(Git git) {
		   PushCommand push = git.push();
		   System.out.println("Code PUSH"+push.getReceivePack());
		   //UsernamePasswordCredentialsProvider credentialsProvider = new UsernamePasswordCredentialsProvider("<useName>", "<passWord>");
		  // push.setCredentialsProvider(credentialsProvider);
		   return push;
		}
		
		@Override
		public CloudConfig createCloudConfig(CloudConfig template) {
			
			Map<String,Object> inputParams = new HashMap<String,Object>();
		    inputParams.put("regione", template.getRegione());
			inputParams.put("zone", template.getZone());
			inputParams.put("accessKey", template.getAccessKey());
			inputParams.put("secretKey", template.getSecretKey());
			inputParams.put("serverType", template.getServerType());
			inputParams.put("cloudUname", template.getCloudUname());
			inputParams.put("masterCount", template.getMasterCount());
			inputParams.put("nodeCount", template.getNodeCount());
			inputParams.put("machineType", template.getMachineType());
			inputParams.put("nodeType", template.getNodeType());
			inputParams.put("templateName", template.getTemplateName());
			inputParams.put("gitCodePath", gitCodePath);
			inputParams.put("clusterName", template.getClusterName());
			inputParams.put("bucketName", template.getBucketName());
			
			String status = batchsvc.cloudConfiguration(inputParams);
			if(status.equalsIgnoreCase("success")) {
				try { 
					
					Thread.sleep(6000);
										
					 
					 ProcessBuilder processBuilder2 = new ProcessBuilder("cmd","/c","start","cmd","/c",gitprocessCloud,template.getRepo_user_name(),template.getRepo_user_email(),template.getRepo_user_pwd(),template.getRepo_url(),template.getTemplateName());
				        // Set the working directory if needed
				           System.out.println("procee.info()==="+processBuilder2.command()); 
				           processBuilder2.directory(new File(gitCodePath));

				        // Start the process
				        Process process2 = processBuilder2.start();
						 int exitCode2 = process2.waitFor();
						 System.out.println("procee.info() exitCode2==="+exitCode2);
						//pushing the docker image to docker hub
						 
					 
					 
	            } 
			            catch (InterruptedException | IOException e) { 
	                e.printStackTrace(); 
	            } 
			}
			
			return configRepo.save(template);
			
		}
		
		@Override
		public List<Template> findByCreatedBy(String createdBy) {
			
			return templateRepo.findByCreatedBy(createdBy);
		}


}
