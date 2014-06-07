package com.happyguage.vennguage.integration.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("classpath:test-servlet-context.xml")
public class HomeControllerTestIT {
	   @Autowired
	    private WebApplicationContext wac;

	    @Resource
	    private FilterChainProxy springSecurityFilterChain;

	   
	    private MockMvc mockMvc;

	    @Before
	    public void setup() {
	        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
	        		.addFilter(springSecurityFilterChain)
	        		.build();
	    }

	    @Test
	    public void checkHomeControllerGetApplicationStatusWithCorrectCredentials() throws Exception {
	    
	        this.mockMvc.perform(get("/api/rest/")
	        		.accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
	        		.header("Authorization","admin:rest123")
	        		)
	          .andExpect(status().isOk())
	          .andExpect(content().contentType("application/json;charset=UTF-8"))
	          .andExpect(jsonPath("$.applicationStatus").value("application is running"))
	          .andExpect(jsonPath("$.links[0].rel").value("self"))
	          .andExpect(jsonPath("$.links[0].href").value("http://localhost/api/rest/"))
	          .andExpect(jsonPath("$.links[1].rel").value("http://happyguage.com/authenticate"))
	          .andExpect(jsonPath("$.links[1].href").value("http://localhost/api/rest/authenticate"));
	    }
	    
	    @Test
	    public void checkHomeControllerGetApplicationStatusWithIncorrectUsername() throws Exception {
	    
	        this.mockMvc.perform(get("/api/rest/")
	        		.accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
	        		.header("Authorization","no-role:rest123")
	        		)
	          .andExpect(status().isUnauthorized());
	    }
	    
	    @Test
	    public void checkHomeControllerGetApplicationStatusWithIncorrectPassword() throws Exception {
	    
	        this.mockMvc.perform(get("/api/rest/")
	        		.accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
	        		.header("Authorization","admin:restxxx")
	        		)
	          .andExpect(status().isUnauthorized());
	    }
	    
	    @Test
	    public void checkHomeControllerGetApplicationStatusWithNoAuthorisation() throws Exception {
	    
	        this.mockMvc.perform(get("/api/rest/")
	        		.accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
	          .andExpect(status().isUnauthorized());
	    }
	    
	    @Test
	    public void checkHomeControllerGetApplicationStatusWithBadHeaderAuthorisation() throws Exception {
	    
	        this.mockMvc.perform(get("/api/rest/")
	        		.accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
	        		.header("Authorization","admin rest123")
	        		)
	          .andExpect(status().isUnauthorized());
	    }
	    
	    @Test
	    public void checkHomeControllerGetApplicationStatusWithAuthorisationButWrongRole() throws Exception {
	    
	        this.mockMvc.perform(get("/api/rest/")
	        		.accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
	        		.header("Authorization","customer:rest123")
	        		)
	          .andExpect(status().isForbidden());
	    }
	    
}
