package com.happyguage.vennguage.rest.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;



















import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.GenericFilterBean;

public class VennGuageAuthenticationTokenProcessingFilter extends GenericFilterBean {

	

    AuthenticationManager authManager;

    public VennGuageAuthenticationTokenProcessingFilter(AuthenticationManager authManager) {
        this.authManager = authManager;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
       
    	HttpServletRequest httpRequest = (HttpServletRequest) request ;
    	String[] credentials = null ;
    	String credentialsString = httpRequest.getHeader("Authorization");
    	
    	if(credentialsString != null){credentials = credentialsString.split(":");}

    	if(credentials != null && credentials.length == 2){
         
                // build an Authentication object with the user's info
                UsernamePasswordAuthenticationToken authentication = 
                        new UsernamePasswordAuthenticationToken(credentials[0], credentials[1]);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails((HttpServletRequest) request));
                // set the authentication into the SecurityContext
                Authentication au ;
                try{
                 au = authManager.authenticate(authentication);
                } catch(Exception e){
                	List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
                	list.add(new GrantedAuthorityImpl("none"));
                 au = new  AnonymousAuthenticationToken("anon", "none" ,  list);
                }
                
                SecurityContextHolder.getContext().setAuthentication(au);         
    	}
        
        // continue thru the filter chain
        chain.doFilter(request, response);
    }

	
}
	

