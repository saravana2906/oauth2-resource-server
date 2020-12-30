# Spring Security Resource Server
Spring boot security - Learning project for Resource server Authentication and Authorization - using OAuth Resource Server starter project. <br>

These are learnings from pluralsight https://github.com/jzheaux/resolutions from Jos Cumings, <br>
- Spring Security has own classes for UserDetails , in order to overwrite/custom implement it by implmenting UserDetailsService interface.
- JDBCUserDetailsManager is available to provide own implementation for loading authorities and UserDetails values from Database.
- Using WebSecurityConfigurerAdapter and adding ANT/MVC matchers to excersie authorities control.<br>

JWT Token - Authentication + Authorisation using Spring Security OAuth2 Resource Server
- Dependencies needed for 
  ```
	<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-oauth2-resource-server</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.security</groupId>
			<artifactId>spring-security-oauth2-jose</artifactId>
		</dependency>
    ```
- No other annotations needed other than this to enable resource server ,except to enable global pre post authorize only 
```
@EnableGlobalMethodSecurity(prePostEnabled = true)
```
- Keycloak starting , understanding and setting it up took considerable amount of time 
- Realm creation
![ScreenShot](https://github.com/saravana2906/oauth2-resource-server/blob/master/keycloak-realm.PNG)
- roles can be placed at two levels - realm level and client level.
- Relam level is good one , as irrespective of client id , user will get roles information . If we create at client level its specific to that client id .<br> Think scenario like we are using two different client ids for same application for two entry points like mobile and desktop. :- (
- Client Creation 
![ScreenShot](https://github.com/saravana2906/oauth2-resource-server/blob/master/clients.PNG)
- Enabling client secret by having access type to confidential and enabling of various OAuth flows.
![ScreenShot](https://github.com/saravana2906/oauth2-resource-server/blob/master/client-secret-flow-enabled.PNG)
- Roles created will come in complex object, we can map it to custom simple/array object by using client level mappers.
![ScreenShot](https://github.com/saravana2906/oauth2-resource-server/blob/master/mapper-client-level.PNG)
![ScreenShot](https://github.com/saravana2906/oauth2-resource-server/blob/master/client-id-based-roles.PNG)
- Postman - latest postman OAuthorisation code grant enabled - settings 
![ScreenShot](https://github.com/saravana2906/oauth2-resource-server/blob/master/postman.PNG)
- Complex object - simple custom object mapping - 
![ScreenShot](https://github.com/saravana2906/oauth2-resource-server/blob/master/decoded-token.PNG)
- Spring Security provides converters to provide custom implementation of roles and authority by overriding defaults provided by **JwtAuthenticationConverter** , **JwtGrantedAuthoritiesConverter**
```
@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceserverApplication extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests(a -> a.anyRequest().authenticated()).oauth2ResourceServer(
				o -> o.jwt(j -> j.jwtAuthenticationConverter(jwtAuthenticationConverter()))
		);
	}

	public static void main(String[] args) {
		SpringApplication.run(ResourceserverApplication.class, args);
	}

	private JwtAuthenticationConverter jwtAuthenticationConverter(){
		JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
		JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
		jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("user-roles");
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
		return jwtAuthenticationConverter;
	}

}
```
