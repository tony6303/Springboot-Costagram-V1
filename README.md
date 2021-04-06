# 인스타그램 클론 코딩

### 의존성

- Spring Boot DevTools
- Lombok
- Spring data JPA
- MYSQL Driver
- Spring Web
- Spring Security
- oauth2-client

```xml
<!-- 시큐리티 태그 라이브러리 -->
<dependency>
	<groupId>org.springframework.security</groupId>
	<artifactId>spring-security-taglibs</artifactId>
</dependency>

<!-- JSP 템플릿 엔진 -->
<dependency>
	<groupId>org.apache.tomcat</groupId>
	<artifactId>tomcat-jasper</artifactId>
	<version>9.0.22</version>
</dependency>

<!-- JSTL -->
<dependency>
	<groupId>javax.servlet</groupId>
	<artifactId>jstl</artifactId>
	<version>1.2</version>
</dependency>

<!-- OAuth2 Client -->
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-oauth2-client</artifactId>
</dependency>

```


``` DB
create user 'costa'@'%' identified by 'costa1234';
GRANT ALL PRIVILEGES ON *.* TO 'costa'@'%';
create database costa;

```  

```apllication.yml  
file:
  path: projectPath\src\main\resources\upload\
  #자신의 경로에 맞게 세팅
```
###Configuration , EnableWebSecurity  
![image](https://user-images.githubusercontent.com/42068811/113676275-34b07480-96f7-11eb-9592-48eacc93764c.png)  
  
###JPA (Java Persistence Api)   
![image](https://user-images.githubusercontent.com/42068811/113677195-45adb580-96f8-11eb-8ea8-73bece1a37f4.png)  

###ERD
![image](https://user-images.githubusercontent.com/42068811/113685338-19e2fd80-9701-11eb-942e-0dad2c012bea.png)
  
###File Upload
![image](https://user-images.githubusercontent.com/42068811/113678336-7f32f080-96f9-11eb-9b2e-077785290d9d.png)



