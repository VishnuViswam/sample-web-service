
<p align="center">
	<img width="70" height="70" src="OtherFiles/springBoot.png" alt="Spring boot">
  <h1 align="center">Build Web-service Using Spring Boot 2x</h1>
</p>


[![Platform](https://img.shields.io/badge/Java-14-red)](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
[![Framework](https://img.shields.io/badge/Spring%20Boot-2.5.2-green)](https://spring.io/projects/spring-boot)
[![Data Base](https://img.shields.io/badge/SQL%20Server-2012-yellow)](https://www.microsoft.com/en-us/download/details.aspx?id=29062)
[![Security](https://img.shields.io/badge/JWT-Auth-blue)](https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt/0.9.1)
[![License](https://img.shields.io/badge/License-MIT-green)](https://github.com/VishnuViswam/sample-web-service/blob/main/LICENSE)

## About Project
 The industry-level architecture of a web-service application developed in spring boot 2.5.2.

## Architecture Contains

  * MVC Architecture
  * JWT Based Authentication
  * Spring Data (JPA)
  * Application User Password Encryption
  * DB password Encryption.
  * SQL Server
  * Sl4j
  * Swagger For API Doc

## Repository contains

* Application Source code.
* SQl script of Data Base along with key data.
* DB.txt file contains the DB config details.
* Postman JSON script to test all web-services.

## Steps to run applications

* Install JDK 11 or latest. 
* Clone the Project repository into local. 
* Install SQL server 2012.
* Create application DB and user
* Insert the DB key data.
* Add the decoding key of the database password into the system variables. It is present in DB.txt file.
* Sometimes we may need to restart the windows to pick up the updated system variables.
* Run the project source code.
* To call the web-services, import provided postman JSON scripts into the postman client application.

## About project configurations

### Web-service Declaration
Each Web-services of application will be declared in the controller layer.

### Example 
```Java
@RequestMapping("/api/v1/user")
@RestController
@Validated
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private GeneralServices generalServices;

    @Autowired
    private UserService userService;

    /**
     * Web service to create new user
     *
     * @param httpServletRequest
     * @param user
     * @return
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createUser(HttpServletRequest httpServletRequest,
                                             @Valid @RequestBody UserCreateModel user) {
        logger.debug("<--- Service to save new user request : received --->");
        ApiSuccessResponse apiResponse = userService.createUser(user, generalServices.getApiRequestedUserId(httpServletRequest));
        logger.debug("<--- Service to save new user response : given --->");
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);

    }

}


```
* __@RequestMapping("/api/v1/user")__ annotation used to mention the category of web service.
* __@RestController__ annotation will configure the class to receive the rest-full web service call.
* __@PostMapping()__ annotation will decide the HTTP request type.
* __consumes & consumes__ tags will decide the content type of the HTTP request and response.

From this "controller layer" API request will be taken to the service layer. All business logic will be handled here, then it will talk with the database using JPA.

## Common Error Handling
Whenever any exception happened, it will throw from the respective classes and be handled in the "CommonExceptionHandlingController". We have to handle separately for each type of exception. This function is performed with the help of "ControllerAdvice" named annotation.

### Example 
```Java
@ControllerAdvice
public class CommonExceptionHandlingController extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(CommonExceptionHandlingController.class);

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException httpRequestMethodNotSupportedException,
                                                                         HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiErrorResponse(Constants.WRONG_HTTP_METHOD,
                Constants.WRONG_HTTP_METHOD_ERROR_MESSAGE, Calendar.getInstance().getTimeInMillis()));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException methodArgumentNotValidException,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiErrorResponse(Constants.MANDATORY_FIELDS_ARE_NOT_PRESENT_CODE,
                Constants.MANDATORY_FIELDS_ARE_NOT_PRESENT_ERROR_MESSAGE, Calendar.getInstance().getTimeInMillis()));
    }

--------
--------

```

## Spring data (JPA) configuration.
* All interaction of the application with the database will handle by the JPA library.
* JPA will have Entity class and corresponding Repository interface for all logical objects in the application. 

### Entity Class
```java
@Entity
@Table(name = "tbl_users")
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "bigint")
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_account_id", columnDefinition = "bigint", nullable = false)
    private UserAccounts userAccount;

--------
--------
````

### Repository interface
````java
public interface UserRepository extends JpaRepository<Users, Long> {

    /**
     * To find user object using username
     *
     * @param username
     * @return
     */
    Users findByUserAccountUsername(String username);
---------
---------
````
* Other JPA configurations will be done in application.properties named file.

### JPA database configuration in application properties
````properties
spring.jpa.show-sql=false
spring.jpa.hibernate.dialect=org.hibernate.dialect.SQLServer2012Dialect
spring.jpa.hibernate.ddl-auto = update

spring.jpa.properties.hibernate.show_sql=false
spring.jpa.properties.hibernate.format_sql=false
spring.jpa.properties.hibernate.use_sql=true
spring.jpa.open-in-view=false
spring.jpa.properties.hibernate.hbm2ddl.auto=update
spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
spring.jpa.hibernate.connection.provider_class=org.hibernate.hikaricp.internal.HikariCPConnectionProvider

````
## Database Configuration
* Database name, connection URL, user credentials all these kinds of values are configured in the application.properties file.

### application.properties
````properties
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=sample_webservice_db
spring.datasource.username=dbuser
spring.datasource.password=ENC(tZTfehMYyz4EO0F0uY8fZItE7K35RtkA)
spring.datasource.driverClassName=com.microsoft.sqlserver.jdbc.SQLServerDriver // is the Database server which we are using
````
### Database password encryption
* Application database password will be encrypted using __Jasypt __ library with the help of a encryption key.
* This encryption key need to add in the system variables 
````properties
spring.datasource.password=ENC(tZTfehMYyz4EO0F0uY8fZItE7K35RtkA)
````
* Above is the line in the property file, which help the application to decrypt the password using the key which is previously added in the system variables. 

#### SampleWebservice.java 

````java
@SpringBootApplication
@EnableEncryptableProperties
public class SampleWebservice extends SpringBootServletInitializer {
--------
--------
````
* We also provide __@EnableEncryptableProperties__ annotation in the application main class to let the application know about this database password encryption configuration.

## JWT AUthentication Configuration
* We implemented JSON Web Token-based authentication with the help of __spring security__.
* Upon success logged in of a user, we will create two tokens (accessToken && refreshToken) and send back to the client.
* __accessToken__  will be created using a privet key, expiry time (1 hr), user id and role name.   
* __refreshToken__  will be created using a privet key , expiry time (24 hr), user id and role name.
* After success login each API request needs to have this ____accessToken____ in the header under __Authorization__ key.
* A "bearer" named key should be attached at the starting of the access token like follows.
* "bearer accessToken"
* Access token will keep monitor in every web-service request.
* If the validity of the access token expires we revert the request with 401 HTTP status.
* At that moment web-service user (client) needs to call access token renewal request using the refresh token.
* Then we will check the validity of refresh token, if it is not expired we will give a new access token and refresh token.
* Client can continue using these new tokens.
* If the validity of the refresh token also expired we ask them to re login using username and password.
### Process of creating Tokens 
### UnAuthorisedAccessServiceImpl.java

````java

@Override
    public ApiSuccessResponse userLoginService(String username, String password) {
        Tokens tokens = null;
        Users user = userService.findByUsername(username);
        if (user != null) {
            if (passwordEncryptingService.matches(password,
                    user.getUserAccount().getPassword())) {
                if (user.getUserAccount().getStatus() == Constants.ACTIVE_STATUS) {
                    String roleName = user.getUserAccount().getUserRole().getRoleName();
                    // Creating new tokens
                    try {
                        tokens = createTokens(user.getUserAccount().getId().toString(), roleName);
                    } catch (Exception exception) {
                        logger.error("Token creation failed : ", exception);
                        throw new UnknownException();
                    }

                    // Validating tokens
                    if (validationService.validateTokens(tokens)) {
                        tokens.setUserId(user.getUserAccount().getId());
                        return new ApiSuccessResponse(tokens);

                    } else {
                        throw new UnknownException();
                    }

                } else {
                    return new ApiSuccessResponse(new ApiResponseWithCode(Constants.USER_ACCOUNT_IS_INACTIVE_ERROR_CODE,
                            Constants.USER_ACCOUNT_IS_INACTIVE_ERROR_MESSAGE));
                }

            } else {
                return new ApiSuccessResponse(new ApiResponseWithCode(Constants.USERNAME_OR_PASSWORD_IS_INCORRECT_ERROR_CODE,
                        Constants.USERNAME_OR_PASSWORD_IS_INCORRECT_ERROR_MESSAGE));
            }

        } else {
            return new ApiSuccessResponse(new ApiResponseWithCode(Constants.USERNAME_OR_PASSWORD_IS_INCORRECT_ERROR_CODE,
                    Constants.USERNAME_OR_PASSWORD_IS_INCORRECT_ERROR_MESSAGE));
        }
    }

    @Override
    public ApiSuccessResponse createNewAccessTokenUsingRefreshToken(String refreshToken) {
        Tokens tokens = null;
        UserAccounts userAccount = null;
        AppConfigSettings configSettings = appConfigSettingsService.findByConfigKeyAndStatus(Constants.JWT_SECRET_KEY,
                Constants.ACTIVE_STATUS);
        // Validate Refresh token
        userAccount = jwtTokenHandler.validate(configSettings.getConfigValue(), refreshToken);
        if (userAccount != null) {
            // Creating new tokens if provided refresh token is valid
            try {
                tokens = createTokens(userAccount.getId().toString(), userAccount.getRole());
            } catch (Exception exception) {
                logger.error("Token creation failed : ", exception);
                throw new UnknownException();
            }
            if (validationService.validateTokens(tokens)) {
                tokens.setUserId(userAccount.getId());
                return new ApiSuccessResponse(tokens);

            } else {
                throw new UnknownException();
            }
        } else {
            return new ApiSuccessResponse(new ApiResponseWithCode(Constants.REFRESH_TOKEN_EXPIRED_ERROR_CODE,
                    Constants.REFRESH_TOKEN_EXPIRED_ERROR_MESSAGE));
        }
    }

````
* In the above code __userLoginService__ named method will check the credentials of the user and providing tokens if it is valid.
* __createNewAccessTokenUsingRefreshToken__ named method will create the new access token and refresh token upon the success refresh token validation.

### Process of filtering and validating Tokens  
### WebConfig.java
````java
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationProvider authenticationProvider;

    @Autowired
    private JwtAuthenticationEntryPoint entryPoint;

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(Collections.singletonList(authenticationProvider));
    }

    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilter() {
        JwtAuthenticationTokenFilter filter = new JwtAuthenticationTokenFilter();
        filter.setAuthenticationManager(authenticationManager());
        filter.setAuthenticationSuccessHandler(new JwtSuccessHandler());
        return filter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .exceptionHandling().authenticationEntryPoint(entryPoint).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .addFilterBefore(new WebSecurityCorsFilter(), ChannelProcessingFilter.class)
                .addFilterBefore(authenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .headers().cacheControl();

    }

}
````
* This configuration will enable the spring security module using __@EnableWebSecurity AND @EnableGlobalMethodSecurity(prePostEnabled = true)__ named annotations.
* Here we will inject then JWT filter into the HTTP request of the system.

### JwtAuthenticationTokenFilter.java
````java
public class JwtAuthenticationTokenFilter extends AbstractAuthenticationProcessingFilter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GeneralServices generalServices;

    public JwtAuthenticationTokenFilter() {
        super("/api/**");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest,
                                                HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
                                                  -------
                                                  --------
    }

````

* Here in the above class __JwtAuthenticationTokenFilter()__  named method will filter all incoming web-service requests who have "api" named keyword in the URL.
* All filtered web-service requests will reach __attemptAuthentication__ named method.
* And we can do all our business logic in this method.

## Application User Password Encryption
* All passwords of the users in this application will be encrypted for security using __BCrypt__.
### PasswordEncryptingService.java
````java
public class PasswordEncryptingService {

    public String encode(CharSequence rawPassword) {
        return BCrypt.hashpw(rawPassword.toString(), BCrypt.gensalt(6));
    }

    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return BCrypt.checkpw(rawPassword.toString(), encodedPassword);
    }
````
* Here __encode__ named method is used to encrypt the password.
* And __matches__ named method is using to cross-check the provided password and actual password of the user.

## Log Configuration Using Slf4j
* We have one XML file to configure the Log named by __logback-spring.xml__.
* To log information from each class, we need to inject the respective class to Slf4j.

### Example
### UserServiceImpl.java
````java
@Service("UserService")
@Scope("prototype")
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

````
* Above code snippet shows how we inject the class into the logger. 
* Following are the basic methods to log the information.
* logger.error("Error");
* logger.info("Info");
* logger.warn("Warn");

## Swagger For API Doc
* API doc has an important role in the web-service application.
* Previously we used to create API doc using any static excel documents
* This library will help us to create the API doc using some annotations inside the application.

### pom.xml
````xml
         <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-boot-starter</artifactId>
            <version>${springfox.swagger.version}</version>
        </dependency>

        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger-ui</artifactId>
            <version>${springfox.swagger.version}</version>
        </dependency>
````
* These are the libraries we used in the pom file to integrate Swagger.
* We need to do some configurations in the applications to enable the API doc.

### SwaggerAPIDocConfig.java
````java
@Configuration
@EnableSwagger2
public class SwaggerAPIDocConfig {


    public static final Contact DEFAULT_CONTACT = new Contact("Demo", "http://www.demo.ae/",
            "info@demo.ae");

    public static final ApiInfo DEFAUL_API_INFO = new ApiInfo("Sample Application",
            "Sample Application description.",
            "1.0.0",
            "http://www.sampleapplication.ae/",
            DEFAULT_CONTACT, "Open licence",
            "http://www.sampleapplication.ae/#license",
            new ArrayList<VendorExtension>());

    private static final Set<String> DEFAULT_PRODICERS_AND_CONSUMERS =
            new HashSet<>(Arrays.asList("application/json", "application/xml"));


    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(DEFAUL_API_INFO)
                .produces(DEFAULT_PRODICERS_AND_CONSUMERS)
                .consumes(DEFAULT_PRODICERS_AND_CONSUMERS)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build();

    }

}
````
* As we can see in the above class need to add some basic information about our project.
* We need to tell swagger from which class needs to create API docs, and that is configured under __.apis(RequestHandlerSelectors.withClassAnnotation,(RestController.class))__ named line.
* Swagger API doc will be accessible from [http://localhost:8080/sampleWebService/apidoc](http://localhost:8080/sampleWebService/apidoc) this link.

## Postman Script
* We can find 2 Postman JSON script in the repository. Please import both of them into the Postman client application. 
* Execute the login web-service request at first. Then execute the rest of the web-services.


License
-------

    MIT License

Copyright (c) 2020 Vishnu Viswambharan

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

