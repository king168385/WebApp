package com.lean.usaa.ods;

import javax.ws.rs.core.Response;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ODSServiceImpl{
			

	

	@RequestMapping("/greeting")
	public ODSResponse testGet() {
	    return new ODSResponse("200", "success");
	}
}
