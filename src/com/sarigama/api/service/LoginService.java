
package com.sarigama.api.service;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import com.google.gson.Gson ;
import com.sarigama.api.response.LoginResponse;
import com.sarigama.api.response.RegisterResponse;
import com.sarigama.db.exception.DBException;
import com.sarigama.security.authentication.AuthenticationService;
import com.sarigama.security.authentication.data.AuthenticationToken;
import com.sarigama.security.authentication.exception.AuthenticationException;
import com.sarigama.security.authentication.exception.UserServiceException;
import com.sarigama.security.dto.UserProfileDto;
import com.sarigama.security.dto.exception.UserAlreadyExcistException;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/authentication")
public class LoginService
{

	// @QueryParam("email")
	// private String email;

	
	// @GET
	// public Map getAuthentication(@QueryParam("name") String name,
	// 		@DefaultValue("18") @QueryParam("age") int age) {

	// 	Map requestParametersAndValues = new HashMap();
	// 	requestParametersAndValues.put("name", name);
	// 	requestParametersAndValues.put("age", String.valueOf(age));
	// 	requestParametersAndValues.put("email", email);

	// 	return requestParametersAndValues;
	// }

	// public String addNewRegister(@Context UriInfo ui) {
	// 	Map requestParameters = ui.getQueryParameters();


	@POST
	@Path("/register")
	public String addNewRegister(@QueryParam("email") String email, @QueryParam("passKey") String passKey) {
		//Map requestParameters = ui.getQueryParameters();
		RegisterResponse register = new RegisterResponse() ;
		UserProfileDto userProfileDto = new UserProfileDto() ;
        userProfileDto.setEmail(email);
		userProfileDto.setPassword( passKey );
		try {
			userProfileDto.addUserProfile() ;
			register.setErrorCode(0);
			register.setErrorMessage("No Error");
			register.setRegisterMessage("Registration successfull");
			register.setStatus("Success");
		}
		catch ( UserAlreadyExcistException uaee){
			register.setErrorCode(5);
			register.setErrorMessage("Error");
			register.setRegisterMessage("User already exist");
			register.setStatus("Error");
		} 
		catch (DBException e) {
			register.setErrorCode(1);
			register.setErrorMessage("Error");
			register.setRegisterMessage("Registration Failed");
			register.setStatus("Error");
		}catch( UserServiceException use) {
			
		}
		
		Gson gson = new Gson() ;
		return gson.toJson( register );
	}
	
	@POST
	@Path("/login")
	public String authenticateUser(@QueryParam("email") String email, @QueryParam("passKey") String passKey) {
		LoginResponse loginResponse = new LoginResponse();
		try {
			AuthenticationService authService = new AuthenticationService();
			AuthenticationToken authenticatedToken = authService.authenticate(email, passKey);

			loginResponse.setErrorCode(0);
			loginResponse.setErrorMessage("No Error");
			loginResponse.setLoginCode(1);
			loginResponse.setStatus("Authenticated");
			loginResponse.setAuth(authenticatedToken);
		}
		catch ( AuthenticationException uaee){
			uaee.printStackTrace();
			loginResponse.setErrorCode(5);
			loginResponse.setErrorMessage("Authenticated Error");
			loginResponse.setLoginCode(2);
			loginResponse.setStatus("Invalid email or password");
		} 
		catch (DBException e) {
			e.printStackTrace();
			loginResponse.setErrorCode(1);
			loginResponse.setErrorMessage("Error");
			loginResponse.setLoginCode(5);
			loginResponse.setStatus("Error");
		} catch (UserServiceException e) {
			e.printStackTrace();
			loginResponse.setErrorCode(1);
			loginResponse.setErrorMessage("Error");
			loginResponse.setLoginCode(5);
			loginResponse.setStatus("Error");
		}
		
		Gson gson = new Gson() ;
		return gson.toJson( loginResponse );
    }
    
}