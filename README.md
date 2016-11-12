# Spring-Boot-Starter

## Intro
Spring boot makes it easy to get started but putting together features like security, jpa, spring-data, user authentication on both mysql and embedded database , roles and profiles can sometimes take your time away. Main goal is to help start creating backend application to be used as REST API for multiple client applications, like android, iOS , angular and other clients.

Firebase is included in the project, with simple configuration you can allow client apps to authenticate using google token provided in the header. Filter is created and authentication provider that clearly demonstrate how to authenticate user using google token.

## Included spring boot starters

There are several included spring boot starters

* spring-boot-starter-web
* spring-boot-starter-data-jpa
* spring-boot-starter-security
* spring-boot-starter-actuator
* spring-boot-devtools

Additional libraries used are 
* mysql-connector-java
* hsqldb
* org.modelmapper.modelmapper 


## Web

As a example one resource is created and named TestResource that demonstrates how to 

* use @RestController
* inject services
* map methods to URLs
* inject configurational properties
* specify the http status values

Exception Handler annotated with @ControllerAdvice is added to handle exceptions with custom response.

For generating the output for client applications modelmapper is used. It converts for example TestEntity to TestJson. 
Here is the word or two from official website http://modelmapper.org:

"Why ModelMapper?
The goal of ModelMapper is to make object mapping easy, by automatically determining how one object model maps to another, based on conventions, in the same way that a human would - while providing a simple, refactoring-safe API for handling specific use cases."

Using modelMapper is super easy to use, here is how to convert List<TestEntity> to List<TestJson>.
```
	Type listType = new TypeToken<List<TestJson>>() {
		}.getType();

	return modelMapper.map(testService.testRepository(), listType);
```

REST api is split into 3 big sections
* /api/open
* /api/client
* /api/admin

Each of the sections is secured so that users with correct roles can use them


## JPA
All of the data means nothing if it can not be persisted, for this problem JPA is used with JpaRepositories enabled. This allows you to quickly create DAO layer. Initially only one entitiy is created the TestEntity, it is super simple with just generated ID of the entity. 
```
	@Entity
	@Table(name = "PMD_TEST")
	public class TestEntity extends AbstractEntity {
		@GeneratedValue(strategy = GenerationType.AUTO)
		@Id
		private Long id;
	}

```
Spring JpaRepositories are awesome way for creating DAO layer quick and easy, here is how to create JpaRepository for TestEntity that has CRUD abilities.
```
	public interface TestRepository extends CrudRepository<TestEntity, Long> {
		Collection<TestEntity> findAll();
	}
```
To utilize the repository all you need to do is inject it
```
	@Autowired
	private TestRepository testRepository;
```

Additionally two more entities are used UserEntity and RoleEntity, so that spring security can be setuped and users are allowed to login.

Transactions are enabled by default so now you can add @Transactional annotations freely

## Spring Security
Securing the data is important, for this important task spring security is used. Initially there are 3 roles in the system
* Admin Role
* User Role
* Anonymous Role

REST API is secured so that
* Administrators can call only /api/admin
* Users can call only /api/client
* Anonymous users can call only /api/open

Method security is enabled with @EnableGlobalMethodSecurity(securedEnabled = true), this means that you can protect your service layer by annotating the public methods like this
```
	@Secured(value = Roles.ROLE_USER)
	public TestEntity create() {
``` 

Users are authenticated against the database, to setup this service UserService is created, here is the relevant part of the setup where user details service is set to the AuthenticationManagerBuilder.
```
	@Order(Ordered.HIGHEST_PRECEDENCE)
	@Configuration
	protected static class AuthenticationSecurity extends GlobalAuthenticationConfigurerAdapter {
		private static Logger logger = Logger.getLogger(AuthenticationSecurity.class);

		@Autowired
		@Qualifier(value="UserService")
		private UserDetailsService userService;

		@Override
		public void init(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(userService);
		}
	}
```
In the dao layer two entities are created as mentioned above UserEntity and RoleEntity 
```
@Entity(name = "user")
@Table(name = "USER")
public class UserEntity implements UserDetails {

	private static final long serialVersionUID = 4815877135015943617L;

	@Id()
	@Column(name = "ID_")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "USERNAME_", nullable = false, unique = true)
	private String username;

	@Column(name = "PASSWORD_", nullable = false)
	private String password;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<RoleEntity> authorities;
	...
```

```
@Entity(name = "ROLE")
@Table(name = "ROLE")
public class RoleEntity implements GrantedAuthority {

	private static final long serialVersionUID = -8186644851823152209L;

	@Id
	@Column(name = "ID_")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "AUTHORITY_")
	private String authority;
```
All that remains is to query the database and load the user and prefill the roles if user has some
```
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails userDetails = userDao.findByUsername(username);
		if (userDetails == null)
			return null;

		Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
		for (GrantedAuthority role : userDetails.getAuthorities()) {
			grantedAuthorities.add(new SimpleGrantedAuthority(role.getAuthority()));
		}

		return new org.springframework.security.core.userdetails.User(userDetails.getUsername(),
				userDetails.getPassword(), userDetails.getAuthorities());
	}
```

## Spring actuator
Actuator is an interesting part of the app, it allows administrators to use REST api and test if the backend is working and what is the status of it. Here are some examples
```
GET /health
GET /metrics
GET /mappings
GET /beans
GET /trace
GET /dump
GET /heapdump
GET /configprops 
GET /autoconfig
GET /info
```

To demonstrate how to create custom health indicator there is PomodoroHealthIndicator as a demonstration.

Spring actuator allows you to monitor the application by creating counts. In the TestService every time when Pomodoro is created counter service is called like this 
```
counterService.increment("rs.pscode.pomodoro.created");
```
Now when we call /metrics we can see how many pomodors are created.


## Devtools
Devtools can help you develop, one feature is currently used directly so that you do not need to stop start the app all the time during development  
spring.devtools.restart.enabled=true

## Profiles
There are two profiles that are enabled in the system 
* prod
* dev

For each profile application-{env}.properties file is created. In each file you can add your properties that depend of the profile that is enabled. Enabling profile is done in the applicatin.properties file
```
spring.profiles.active=dev
```

application-prod.properties has mysql access specified but application-dev.properties has no mysql access specified, this is a nice way to tell spring to use embedded database for dev environment. Here is the application-prop.properties, this tells spring boot that we want to have datasource that connects to mysql.
```
env=prod 

spring.datasource.url=jdbc:mysql://localhost:3306/pomodorofire
spring.datasource.username=pomodorofire
spring.datasource.password=pomodorofire1

spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=create-drop

```
## Swagger 2
Swagger 2 is enabled only in 'dev' profile.

```
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	@Profile("dev")
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build();
	}
	
	@Bean
	@Profile("prod")
	public Docket apiProd() {
		return new Docket(DocumentationType.SWAGGER_2).enable(false).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build();
	}
}
```

For swagger especial configuration is added in spring security section and WebSecurityConfigurerAdapter
```
		
@Override
   public void configure(WebSecurity web) throws Exception {
	        web.ignoring().antMatchers("/v2/api-docs",
		                           "/configuration/ui", 
					   "/swagger-resources",
					   "/configuration/security",
					   "/swagger-ui.html",
					   "/webjars/**");
	    }
```

Swagger UI is accessible on ``` http://localhost:8080/swagger-ui.html ```


##Swagger code-gen

To generate retrofit2 client code you should download swagger.jar and execute it using next command
```
java -jar swagger.jar generate -i http://localhost:8080/v2/api-docs  -l java --library=retrofit2 -DmodelPackage=rs.pscode.start.model,apiPackage=rs.pscode.start.api -o spring-boot-starter-genereated
```
Therea are several parameters used
* -i Indicates the URL to the api-docs
* -l language, java 
* --library , retrofit2
* modelPacakge and apiPackage
* -o , output folder 



## Firebase integration

Firebase is integrated and it allows clients that are authenticated by firebase to access REST APIs. There are several parts of the integration
* Maven Dependencies
* Filter
* Authentication provider
* Token parsing
* Registration


Firebase can be enabled by setting 
```
rs.pscode.firebase.enabled=true/false
```
in application.properties. This will register especial FirebaseFilter and authentication provider.

### Dependencies 
```
<dependency>
	<groupId>com.google.firebase</groupId>
	<artifactId>firebase-server-sdk</artifactId>
	<version>3.0.1</version>
</dependency>
```

### Firebase filter

Filter checks if  header named "X-Authorization-Firebase" exists, if does not this means that request should not be processed by this filter. When token is found it is parsed using ```firebaseService.parseToken(xAuth)``` and correct Authentication is created ```FirebaseAuthenticationToken``` that is put in the securityContextHolder. Job of the filter is here done and authentication process is being taken over by authentication provider.


```
public class FirebaseFilter extends OncePerRequestFilter {

	private static String HEADER_NAME = "X-Authorization-Firebase";

	private FirebaseService firebaseService;

	public FirebaseFilter(FirebaseService firebaseService) {
		this.firebaseService = firebaseService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String xAuth = request.getHeader(HEADER_NAME);
		if (StringUtil.isBlank(xAuth)) {
			filterChain.doFilter(request, response);
			return;
		} else {
			try {
				FirebaseTokenHolder holder = firebaseService.parseToken(xAuth);

				String userName = holder.getUid();

				Authentication auth = new FirebaseAuthenticationToken(userName, holder);
				SecurityContextHolder.getContext().setAuthentication(auth);

				filterChain.doFilter(request, response);
			} catch (FirebaseTokenInvalidException e) {
				throw new SecurityException(e);
			}
		}
	}

}
```

### Authentication provider
Authentication provider is made so that we map the firebase user to our database. Provider only supports FirebaseAuthenticationToken.

```
@Component
public class FirebaseAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	@Qualifier(value = UserServiceImpl.NAME)
	private UserDetailsService userService;

	public boolean supports(Class<?> authentication) {
		return (FirebaseAuthenticationToken.class.isAssignableFrom(authentication));
	}

	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		if (!supports(authentication.getClass())) {
			return null;
		}

		FirebaseAuthenticationToken authenticationToken = (FirebaseAuthenticationToken) authentication;
		UserDetails details = userService.loadUserByUsername(authenticationToken.getName());
		if (details == null) {
			throw new FirebaseUserNotExistsException();
		}

		authenticationToken = new FirebaseAuthenticationToken(details, authentication.getCredentials(),
				details.getAuthorities());

		return authenticationToken;
	}

}

```

## Firebase Token parser
Firebase parse validates the token and returns instance of especial class FirebaseTokenHolder.

```
public class FirebaseParser {
	public FirebaseTokenHolder parseToken(String idToken) {
		if (StringUtil.isBlank(idToken)) {
			throw new IllegalArgumentException("FirebaseTokenBlank");
		}
		try {
			Task<FirebaseToken> authTask = FirebaseAuth.getInstance().verifyIdToken(idToken);

			Tasks.await(authTask);

			return new FirebaseTokenHolder(authTask.getResult());
		} catch (Exception e) {
			throw new FirebaseTokenInvalidException(e.getMessage());
		}
	}
```

One of the key parts of the firebase integration is the application initialization. To complete the initialization you must downlod json from firebase console , check this link https://firebase.google.com/docs/server/setup .

```
@Configuration
public class FirebaseConfig {

	@Bean
	public DatabaseReference firebaseDatabse() {
		DatabaseReference firebase = FirebaseDatabase.getInstance().getReference();
		return firebase;
	}

	@Value("${rs.pscode.firebase.database.url}")
	private String databaseUrl;

	@Value("${rs.pscode.firebase.config.path}")
	private String configPath;

	@PostConstruct
	public void init() {

		/**
		 * https://firebase.google.com/docs/server/setup
		 * 
		 * Create service account , download json
		 */
		InputStream inputStream = FirebaseConfig.class.getClassLoader().getResourceAsStream(configPath);

		FirebaseOptions options = new FirebaseOptions.Builder().setServiceAccount(inputStream)
				.setDatabaseUrl(databaseUrl).build();
		FirebaseApp.initializeApp(options);
		
	}
}
```


## TODO 
Add unit tests




