package com.happyguage.vennguage.rest.resource;

import org.springframework.hateoas.ResourceSupport;

public class TreatmentRestResource extends ResourceSupport {

	private String authenticationStatus ;
	
	public TreatmentRestResource(){
		this.setAuthenticationStatus("authenticated");
	}

	public String getAuthenticationStatus() {
		return authenticationStatus;
	}

	public void setAuthenticationStatus(String applicationStatus) {
		this.authenticationStatus = applicationStatus;
	}
}
