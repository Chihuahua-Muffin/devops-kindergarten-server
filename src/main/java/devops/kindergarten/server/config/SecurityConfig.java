package devops.kindergarten.server.config;

import com.google.common.collect.ImmutableList;

import devops.kindergarten.server.jwt.JwtAccessDeniedHandler;
import devops.kindergarten.server.jwt.JwtAuthenticationEntryPoint;
import devops.kindergarten.server.jwt.JwtSecurityConfig;
import devops.kindergarten.server.jwt.TokenProvider;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private final TokenProvider tokenProvider;
	private final CorsFilter corsFilter;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

	public SecurityConfig(
		TokenProvider tokenProvider,
		CorsFilter corsFilter,
		JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
		JwtAccessDeniedHandler jwtAccessDeniedHandler
	) {
		this.tokenProvider = tokenProvider;
		this.corsFilter = corsFilter;
		this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
		this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(WebSecurity web) {
		web
			.ignoring()
			.antMatchers(
				"/h2-console/**",
				"/favicon.ico",
				"/v2/api-docs", "/swagger-resources/**", "/swagger-ui.html",
				"/webjars/**", "/swagger/**"
			);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			//                .cors().configurationSource(corsConfigurationSource())
			//                .and()

			.csrf().disable()   // token ????????? ??????????????? csrf ????????? disable ?????????.

			.addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)

			.exceptionHandling()
			.authenticationEntryPoint(jwtAuthenticationEntryPoint)
			.accessDeniedHandler(jwtAccessDeniedHandler)

			.and()
			.headers()
			.frameOptions()
			.sameOrigin()

			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)

			.and()
			.authorizeRequests()    // HttpServletRequest ??? ???????????? ???????????? ?????? ??????????????? ??????????????? ???
			.antMatchers("/api/test").permitAll()   // /api/test ??? ?????? ????????? ???????????? ????????? ?????????????????? ??????
			.antMatchers("/api/login").permitAll()
			.antMatchers("/api/signup").permitAll()
			.antMatchers("/api/**").permitAll() //api ???????????? ????????? ?????? ?????????
			.anyRequest().authenticated()

			.and()
			.apply(new JwtSecurityConfig(tokenProvider));
	}
	//    @Bean
	//    public CorsConfigurationSource corsConfigurationSource() {
	//        final CorsConfiguration configuration = new CorsConfiguration();
	//        //configuration.setAllowedOrigins(ImmutableList.of("http://localhost:8080","http://localhost:8084"));
	//        configuration.setAllowedOriginPatterns(ImmutableList.of("*"));
	//        configuration.setAllowedMethods(ImmutableList.of("GET", "POST", "PUT", "DELETE"));
	//        configuration.setAllowCredentials(true);
	//        configuration.setAllowedHeaders(ImmutableList.of("Authorization", "Cache-Control", "Content-Type"));
	//        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	//        source.registerCorsConfiguration("/**", configuration);
	//        return source;
	//    }
}
