package fpt.com.rest_full_api.request;

public class LoginRequest {
	
	private String email;
	private String password;

	public LoginRequest() {

	}

	public LoginRequest(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	//getter and setter

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}

}
