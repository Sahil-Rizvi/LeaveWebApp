package com.sahil.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sahil.services.TesterService;

@Controller
@RequestMapping("/tester")
public class TesterController {
	
	@Autowired
	private TesterService testerService;
	
	@RequestMapping(value="/reporthr")
	public void reportForHR(){
		testerService.reportForHr();
	}
	
	@RequestMapping(value="/reportmanager")
	public void reportForManeger(){
		testerService.reportForManager();
	}
	
	@RequestMapping(value="/forward")
	public void forwardLeaves(){
		System.out.println("tester forwardLeaves ");
		testerService.forwardLeaves();
	}

}
