package com.happyguage.vennguage.rest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import com.happyguage.vennguage.rest.resource.ApplicationStatusRestResource;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * return application status
	 */
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/api/rest/", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ApplicationStatusRestResource> getApplicationStatus() {
		logger.info("Retrieve application status");
		
		ApplicationStatusRestResource asrr = new ApplicationStatusRestResource() ;
		
		asrr.add(linkTo(methodOn(HomeController.class).getApplicationStatus()).withSelfRel());
		asrr.add(linkTo(methodOn(TreatmentController.class).getTreatments()).withRel("http://happyguage.com/authenticate") );

        return new ResponseEntity<ApplicationStatusRestResource>(asrr, HttpStatus.OK);
		
	}
	
}
