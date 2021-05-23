package org.sadoke;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sadoke.entities.User;
import org.sadoke.repository.UserRepository;
import org.sadoke.service.TokenService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final TokenService tokenService;
	private final UserRepository userRepos;

	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			FilterChain filterChain) throws IOException, ServletException {
		String authorizationHeader = httpServletRequest.getParameter("apiToken");

		if (authorizationHeaderIsInvalid(authorizationHeader)) {
			filterChain.doFilter(httpServletRequest, httpServletResponse);
			return;
		}

		UsernamePasswordAuthenticationToken token = createToken(authorizationHeader);
		SecurityContextHolder.getContext().setAuthentication(token);
		filterChain.doFilter(httpServletRequest, httpServletResponse);
	}

	private boolean authorizationHeaderIsInvalid(String authorizationHeader) {
		return authorizationHeader == null || !tokenService.validateToken(authorizationHeader).booleanValue();
	}

	private UsernamePasswordAuthenticationToken createToken(String authorizationHeader) {
		String token = authorizationHeader;
		log.info("token= {}", token);
		User userPrincipal = userRepos.findById(tokenService.getUsernameFromToken(authorizationHeader)).get();

		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

		return new UsernamePasswordAuthenticationToken(userPrincipal, null, authorities);
	}

}
