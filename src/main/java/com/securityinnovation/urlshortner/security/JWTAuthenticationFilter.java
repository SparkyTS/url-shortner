package com.securityinnovation.urlshortner.security;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * <h1>JWTAuthenticationFilter</h1>
 * <p>
 *   Filter which validates JWT Token and if valid token exist then sets user into a security context
 * </p>
 *
 * @author Tanay
 * @version 1.0
 */
@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

    private final JWTTokenProvider tokenProvider;
    private final AppUserDetailService appUserDetailService;


    public JWTAuthenticationFilter(JWTTokenProvider tokenProvider, AppUserDetailService appUserDetailService) {
        this.tokenProvider = tokenProvider;
        this.appUserDetailService = appUserDetailService;
    }

    /**
     * <h1>doFilterInternal</h1>
     * <p>
     *   If valid token exist in authorization header this method fetched the user associated with token from database
     *   and sets the user into apps security context
     * </p>
     * @param request - http request
     * @param response - http response
     * @param filterChain - filter chain
     * @throws ServletException - servlet exception
     * @throws IOException - input/output exception
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String accessToken = getJwtFromRequest(request);

            if (StringUtils.hasText(accessToken) && tokenProvider.validateAccessToken(accessToken)) {
                Long userId = tokenProvider.getUserIdFromAccessToken(accessToken);

                UserDetails userDetails = appUserDetailService.loadUserById(userId);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ex) {
            logger.error("Could not set user in security context", ex);
        }
        filterChain.doFilter(request, response);
    }

    /**
     * <h1>getJwtFromRequest</h1>
     * <p>
     *   Trims token type form the token which is passed in authorization header of the http request
     * </p>
     * @param request - http request
     * @return String - returns token after trimming token type
     */
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken)) {
            if(bearerToken.startsWith("Bearer "))
                return bearerToken.substring(7);
            else
                return bearerToken;
        }
        return null;
    }
}