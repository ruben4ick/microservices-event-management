package ua.edu.ukma.event_management_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ua.edu.ukma.event_management_system.service.interfaces.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthControllerApi {

	private UserService userService;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
		if (loginRequest.getUsername() == null || loginRequest.getPassword() == null) {
			throw new ResponseStatusException(HttpStatusCode.valueOf(401), "Username and password are required");
		}

		String token = userService.verify(loginRequest.getUsername(), loginRequest.getPassword());

		AuthResponse response = new AuthResponse("Login successful", token);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/logout")
	public ResponseEntity<AuthResponse> logout() {
		AuthResponse response = new AuthResponse("Logout successful", null);
		return ResponseEntity.ok(response);
	}

	public static class LoginRequest {
		private String username;
		private String password;

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
	}

	public static class AuthResponse {
		private String message;
		private String token;

		public AuthResponse(String message, String token) {
			this.message = message;
			this.token = token;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}
	}
}
