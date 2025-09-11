package ua.edu.ukma.event_management_system.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ua.edu.ukma.event_management_system.filter.JwtFilter;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	private static final String ORGANIZER = "ORGANIZER";
	private static final String USER = "USER";
	private static final String ADMIN = "ADMIN";

	private UserDetailsService userDetailsService;
	private PasswordEncoder passwordEncoder;
	private JwtFilter jwtFilter;

	@Autowired
	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Autowired
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Autowired
	public void setJwtFilter(JwtFilter jwtFilter) {
		this.jwtFilter = jwtFilter;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.csrf(AbstractHttpConfigurer::disable)
				.headers(httpSecurityHeadersConfigurer -> httpSecurityHeadersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
				.authorizeHttpRequests(request -> request
						.requestMatchers("/api/auth/**").permitAll()
						.requestMatchers(HttpMethod.GET, "/api/building/**").hasAuthority(USER)
						.requestMatchers(HttpMethod.POST, "/api/building/**").hasAuthority(ORGANIZER)
						.requestMatchers(HttpMethod.PUT, "/api/building/**").hasAuthority(ORGANIZER)
						.requestMatchers(HttpMethod.DELETE, "/api/building/**").hasAuthority(ORGANIZER)
						.requestMatchers(HttpMethod.GET, "/api/event/**").permitAll()
						.requestMatchers(HttpMethod.POST, "/api/event/**").hasAuthority(ORGANIZER)
						.requestMatchers(HttpMethod.PUT, "/api/event/**").hasAuthority(ORGANIZER)
						.requestMatchers(HttpMethod.DELETE, "/api/event/**").hasAuthority(ORGANIZER)
						.requestMatchers(HttpMethod.GET, "/api/ticket/**").hasAuthority(USER)
						.requestMatchers(HttpMethod.POST, "/api/ticket/**").hasAuthority(USER)
						.requestMatchers(HttpMethod.PUT, "/api/ticket/**").hasAuthority(USER)
						.requestMatchers(HttpMethod.DELETE, "/api/ticket/**").hasAuthority(USER)
						.requestMatchers(HttpMethod.GET, "/api/user/**").hasAuthority(USER)
						.requestMatchers(HttpMethod.POST, "/api/user/**").hasAuthority(ORGANIZER)
						.requestMatchers(HttpMethod.PUT, "/api/user/**").hasAuthority(USER)
						.requestMatchers(HttpMethod.DELETE, "/api/user/**").hasAuthority(ORGANIZER)
						.requestMatchers("/api/**").hasAuthority(ADMIN)
						.anyRequest().authenticated())
//				.httpBasic(Customizer.withDefaults())
				.exceptionHandling(exception -> exception
						.authenticationEntryPoint((request, response, authException) -> {
							response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
							response.setContentType("application/json");
							response.getWriter().write("{\"error\":\"Unauthorized\",\"message\":\"Authentication required\"}");
						}))
				.sessionManagement(session ->
						session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.logout(LogoutConfigurer::permitAll)
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder);
		provider.setUserDetailsService(userDetailsService);
		return provider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

}
