package com.happyguage.vennguage.rest.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.happyguage.vennguage.rest.resource.TreatmentRestResource;

@Controller
public class TreatmentController {
private static final Logger logger = LoggerFactory.getLogger(TreatmentController.class);
	
    @Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/api/rest/treatment", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<TreatmentRestResource> getTreatments() {
        logger.info("get a list of treatments ");
		
		TreatmentRestResource trr = new TreatmentRestResource() ;
		
		trr.add(linkTo(methodOn(TreatmentController.class).getTreatments()).withSelfRel());
		trr.add(linkTo(methodOn(HomeController.class).getApplicationStatus()).withRel("http://happyguage.com/home") );

        return new ResponseEntity<TreatmentRestResource>(trr, HttpStatus.OK);
		
	}

}
