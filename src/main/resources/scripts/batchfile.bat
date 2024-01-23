set paramString=%2
echo paramString : %paramString%
spring init %1 -n=%1 --package-name=com.mydomain --build=maven -d=%2 --groupId=com.mydomain --java-version=17