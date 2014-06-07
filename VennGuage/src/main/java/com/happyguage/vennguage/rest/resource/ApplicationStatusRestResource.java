package com.happyguage.vennguage.rest.resource;

import org.springframework.hateoas.ResourceSupport;

public class ApplicationStatusRestResource extends ResourceSupport {

	private String applicationStatus ;
	
	public ApplicationStatusRestResource(){
		this.setApplicationStatus("application is running");
	}

	public String getApplicationStatus() {
		return applicationStatus;
	}

	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}
}
