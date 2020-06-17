package com.tyss.cg.springbootdatajpa.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tyss.cg.springbootdatajpa.entity.Applyloan;
import com.tyss.cg.springbootdatajpa.entity.LoanPrograms;
import com.tyss.cg.springbootdatajpa.exception.ApplicationNotFoundException;
import com.tyss.cg.springbootdatajpa.response.Response;
import com.tyss.cg.springbootdatajpa.services.ApplyLoanServices;
import com.tyss.cg.springbootdatajpa.services.LoanProgramsServices;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class LADController {
	
	@Autowired
	private LoanProgramsServices loanProgramsServices;
	
	@Autowired
	private ApplyLoanServices service;
	
	//Applications
	@GetMapping("/application")
	public Response<List<Applyloan>> getAllApplications(){
		return new Response<>(false, "List Retrieved!!!", service.findAllApplications());
	}
	
	@GetMapping("/application/{appId}")
	public Response<Applyloan> getApplicationById(@PathVariable int appId){
		Applyloan application=service.findApplicationById(appId);
		System.out.println("id is" + application.getUser().getUserid());
		return new Response<Applyloan>(false, "Application found!!!", application);
	}
	
	@DeleteMapping("/application/{appId}")
	public Response<Applyloan> deleteProgram(@PathVariable int appId) {
		Applyloan application= service.findApplicationById(appId);
		if(application!=null) {
			service.deleteApplication(appId);
			return new Response<Applyloan>(false, "Application Deleted sucessfully!!!", application);
		}
		else
			throw new ApplicationNotFoundException("Application not found");
	}
	
	@GetMapping("/application/requested/")
	public Response<List<Applyloan>> requestedApplications(){
		List<Applyloan> applyloans = service.requestedApplications();
		if (applyloans != null) {
			return new Response<>(false, "list found", applyloans);
		} else {
			return new Response<>(true, "no status with this status", null);
		}
	}
	
	@GetMapping("/application/requested/{pageNo}/{itemsPerPage}")
	public Page<Applyloan> requestedApplication(@PathVariable int pageNo, @PathVariable int itemsPerPage){
		return service.requestedApplication(pageNo, itemsPerPage);
	}

	@GetMapping("/application/requested/{pageNo}/{itemsPerPage}/{fieldname}")
	public Page<Applyloan> sortRequestedApplication(@PathVariable int pageNo, @PathVariable int itemsPerPage, @PathVariable String fieldname){
		return service.sortRequestedApplication(pageNo, itemsPerPage, fieldname);
	}
	
	

	@GetMapping("/application/rejected/")
	public Response<List<Applyloan>> rejectedApplications(){
		List<Applyloan> applyloans = service.rejectedApplications();
		if (applyloans != null) {
			return new Response<>(false, "list found", applyloans);
		} else {
			return new Response<>(true, "no status with this status", null);
		}
	}
	
	@GetMapping("/application/rejected/{pageNo}/{itemsPerPage}")
	public Page<Applyloan> rejectedApplications(@PathVariable int pageNo, @PathVariable int itemsPerPage){
		return service.rejectedApplications(pageNo, itemsPerPage);
	}

	@GetMapping("/application/rejected/{pageNo}/{itemsPerPage}/{fieldname}")
	public Page<Applyloan> sortRejectedApplications(@PathVariable int pageNo, @PathVariable int itemsPerPage, @PathVariable String fieldname){
		return service.sortRejectedApplications(pageNo, itemsPerPage, fieldname);
	}

	@GetMapping("/application/approved/")
	public Response<List<Applyloan>> approvedApplications(){
		List<Applyloan> applyloans = service.approvedApplications();
		if (applyloans != null) {
			return new Response<>(false, "list found", applyloans);
		} else {
			return new Response<>(true, "no status with this status", null);
		}
	}
	
	@GetMapping("/application/approved/{pageNo}/{itemsPerPage}")
	public Page<Applyloan> approvedApplication(@PathVariable int pageNo, @PathVariable int itemsPerPage){
		return service.approvedApplications(pageNo, itemsPerPage);
	}

	@GetMapping("/application/approved/{pageNo}/{itemsPerPage}/{fieldname}")
	public Page<Applyloan> sortApprovedApplication(@PathVariable int pageNo, @PathVariable int itemsPerPage, @PathVariable String fieldname){
		return service.sortApprovedApplications(pageNo, itemsPerPage, fieldname);
	}
	
	@PutMapping("/application/setapprove/{loanid}")
	public Response<Applyloan> setApproved(@PathVariable int loanid){
		Applyloan applyloan = service.setApproved(loanid);
		if (applyloan != null) {
			return new Response<Applyloan>(false, "Status changed to approved", applyloan);
		} else {
			throw new ApplicationNotFoundException("Application not found");
		}
	}
	
	@PutMapping("/application/setreject/{loanid}")
	public Response<Applyloan> setRejected(@PathVariable int loanid){
		Applyloan applyloan = service.setRejected(loanid);
		if (applyloan != null) {
			return new Response<Applyloan>(false, "Status changed to rejected", applyloan);
		} else {
			throw new ApplicationNotFoundException("Application not found");
		}
	}
	
	
	//Loan Programs
	@GetMapping("/loanprograms")
	public Response<List<LoanPrograms>> findAll() {
		List<LoanPrograms> lists = loanProgramsServices.findAll();
		return new Response<>(false, "list retrieved", lists);
	}
	
	@GetMapping("/loanprograms/{pageNo}/{itemsPerPage}")
	public Page<LoanPrograms> getLoans(@PathVariable int pageNo, @PathVariable int itemsPerPage){
		return loanProgramsServices.getLoans(pageNo, itemsPerPage);
	}
	
	@GetMapping("/loanprograms/{pageNo}/{itemsPerPage}/{fieldname}")
	public Page<LoanPrograms> getLoans(@PathVariable int pageNo, @PathVariable int itemsPerPage, @PathVariable String fieldname){
		return loanProgramsServices.getSortLoans(pageNo, itemsPerPage, fieldname);
	}
	
}	
