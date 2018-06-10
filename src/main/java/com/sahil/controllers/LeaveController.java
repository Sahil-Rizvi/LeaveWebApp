package com.sahil.controllers;

import java.beans.PropertyEditorSupport;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sahil.enums.LeaveType;
import com.sahil.enums.ResponseType;
import com.sahil.models.Event;
import com.sahil.models.LeaveCountResponse;
import com.sahil.models.PageResponseDTO;
import com.sahil.models.Response;
import com.sahil.models.emp.leave.ApprovedLeaveDTO;
import com.sahil.models.emp.leave.PendingLeaveDTO;
import com.sahil.models.emp.leave.RejectedLeaveDTO;
import com.sahil.models.emp.leave.TeamMateLeaveDTO;
import com.sahil.models.input.Leave;
import com.sahil.models.manager.leave.UpdateLeaveRequestDTO;
import com.sahil.models.manager.leave.UpdateLeaveRequestListDTO;
import com.sahil.models.manager.leave.UpdateLeaveResponseDTO;
import com.sahil.models.request.LeaveRequestDTO;
import com.sahil.services.LeaveCountService;
import com.sahil.services.LeaveService;
import com.sahil.services.MyCalendarService;
import com.sahil.utils.LeaveUtil;

@Controller
@RequestMapping("/leave")
public class LeaveController {

	@Autowired
	private LeaveService leaveService; 
	
	@Autowired
	private LeaveCountService leaveCountService;
	
	@Autowired
	private MyCalendarService myCalendarService;
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.registerCustomEditor(ResponseType.class, new PropertyEditorSupport() {
        	@Override
        	public void setAsText(String value) throws IllegalArgumentException {
        	if(StringUtils.isEmpty(value))
        	return;

        	setValue(ResponseType.valueOf(value));
        	}

        	@Override
        	public String getAsText() {
        	if(getValue() == null)
        	return "";

        	return ((ResponseType) getValue()).name();
        	}
        	});

    }

	
	@RequestMapping(value="/emp/deleteLeave")
	@ResponseBody
	public String deleteLeave(@RequestParam(value="id",required=false)int id,HttpServletRequest request){
		if(request.getUserPrincipal().getName()!=null){
			return leaveService.deleteLeave(id).getMessage();
		}
		return "Please logIn";
	}
	
	
	@RequestMapping(value="/emp/leaveHome")
	public String home(ModelMap modelMap,HttpServletRequest request){
		modelMap.addAttribute("leaveTypes",LeaveType.values());
		if(request.getUserPrincipal().getName()!=null){
			return "home";	
		}
		return "redirect:/";
	}
	
	
	@RequestMapping(value="/emp/addLeave")
	@ResponseBody
	public String addLeave(@Valid @ModelAttribute Leave leave,BindingResult bindingResult,HttpServletRequest request){
		if(bindingResult.hasErrors()){
			System.out.println(bindingResult.getAllErrors());
			return "ERROR IN BINDING DATA";
		}
		System.out.println(leave);
		if(request.getUserPrincipal().getName()!=null){
			String empId = request.getUserPrincipal().getName();
			Response response = leaveService.addLeave(leave,empId);
			if(response.getCode()==1){
				return "Leave Not Added <br> Reason : "+response.getMessage();
			}
			else{
				return response.getMessage();
			}
		}
		else{
			return "Please log in again.";
		}
	}
	
	
	@RequestMapping(value="/emp/status/{leaveStatus}")
	public String getLeavesForm(@PathVariable String leaveStatus){
	    String jsp = "emp/";
	    if("pending".equals(leaveStatus.toLowerCase())){
	    	jsp += "getpendingleavesform";
	    }
	    else
	    if("approved".equals(leaveStatus.toLowerCase())){
	    	jsp += "getapprovedleavesform";
	    }
	    else
	    if("rejected".equals(leaveStatus.toLowerCase())){
	    	jsp += "getrejectedleavesform";
	    }
	    else
	    if("teammates".equals(leaveStatus.toLowerCase())){
	    	jsp += "getteammatesleavesform";
	    }
		return jsp;
	}
	
	
	@RequestMapping(value="/manager/status/{leaveStatus}")
	public String getLeavesFormForManager(@PathVariable String leaveStatus){
	    String jsp = "manager/leaves/";
	    if("approved".equals(leaveStatus.toLowerCase())){
	    	jsp += "getapprovedleavesform";
	    }
	    else
	    if("rejected".equals(leaveStatus.toLowerCase())){
	    	jsp += "getrejectedleavesform";
	    }
	    return jsp;
	}
	
	
	
	
	
	@RequestMapping(value="/manager/getLeavesForUpdationForManager")
	public String findAllLeavesForManagerForUpdation(Model model,HttpServletRequest request){
		String leaves = "manager/approveorrejectleaves";
		System.out.println("in getAllLeavesForUpdationForManager");
			String empId = request.getUserPrincipal().getName();
			UpdateLeaveResponseDTO updateLeavesResponseDTO = leaveService.findAllLeavesByManagerForUpdation(empId);
			if(updateLeavesResponseDTO.getCode()!=1){    		
	    		if(!CollectionUtils.isEmpty(updateLeavesResponseDTO.getLeaves())){
	    			System.out.println(updateLeavesResponseDTO.getLeaves().toString());
	    			model.addAttribute("leaveResponse",updateLeavesResponseDTO);
	    			model.addAttribute("enums",ResponseType.values());
	    		}
	    		else{
	    		System.out.println("empty page");	
	    		}
	    	}
	    	else{
	    		System.out.println(updateLeavesResponseDTO.getMessage());	
			}
		return leaves;
	}


	@RequestMapping(value="/manager/approveOrReject",method=RequestMethod.POST)
	@ResponseBody
	public String updateLeaves(@ModelAttribute UpdateLeaveRequestListDTO updateLeavesDTOs,HttpServletRequest request){
		for(UpdateLeaveRequestDTO dto:updateLeavesDTOs.getLeaves()){
			System.out.println(dto.toString());
		}
		return leaveService.updateLeaveDetails(request.getUserPrincipal().getName(),updateLeavesDTOs).getMessage();
	}
	
	
	@RequestMapping(value="/emp/getLeavesOfSpecificEmployeeTeamMates/{pageNumber}")
	public String findAllLeavesOfTeamMates(@PathVariable int pageNumber,@RequestParam(value="emp",required=false)String id,@RequestParam(value="fromWhen",required=false)String fromWhen,@RequestParam(value="toWhen",required=false)String toWhen,HttpServletRequest request,ModelMap model){
		String leaves = "emp/teammatesleaves";
		if(id==null){
    		id = request.getUserPrincipal().getName();
    	}
    	if(fromWhen.isEmpty() || toWhen.isEmpty()){
    		model.addAttribute("message","Please enter valid dates");
    	}
    	else{
    		PageResponseDTO<TeamMateLeaveDTO> approvedTeamMatesPageResponseDTO = leaveService.findLeavesOfTeamMates(new LeaveRequestDTO(id, pageNumber, fromWhen, toWhen));
    		if(approvedTeamMatesPageResponseDTO.getCode()!=1){    		
    		System.out.println("in getAllLeavesOfSpecificEmployeeTeamMates/"+pageNumber);
    			if(!CollectionUtils.isEmpty(approvedTeamMatesPageResponseDTO.getData().getContent())){
    			setParam(model, approvedTeamMatesPageResponseDTO, fromWhen, toWhen);
    			}
    			else{
    				System.out.println("empty page");	
    			}
    		}
    		else{
    		System.out.println(approvedTeamMatesPageResponseDTO.getMessage());	
    		}
    	}
	return leaves;
    }
	
	
	
    @RequestMapping(value="/emp/viewLeaveBalance")
    public String viewLeaveBalance(ModelMap modelMap,HttpServletRequest request){
    	
    	  
    	   String empId = request.getUserPrincipal().getName();
    	   LeaveCountResponse countResponse = leaveCountService.getLeaveCountForEmployee(empId);
    	   System.out.println(empId);
    	   System.out.println(countResponse);
    	   modelMap.addAttribute("lc",countResponse);
    	
    	return "countresponse";
    }
    
    
    @RequestMapping(value="/emp/getPendingLeavesOfEmp/{pageNumber}")
    public String getPendingLeavesOfEmployee(@PathVariable int pageNumber,@RequestParam(value="emp",required=false)String id,@RequestParam(value="fromWhen",required=false)String fromWhen,@RequestParam(value="toWhen",required=false)String toWhen, HttpServletRequest request,ModelMap model){
    	String leaves = "emp/pending";
    	if(id==null){
    		id = request.getUserPrincipal().getName();
    	}
    	if(fromWhen.isEmpty() || toWhen.isEmpty()){
    		model.addAttribute("message","Please enter valid dates");
    	}
    	else{
    		PageResponseDTO<PendingLeaveDTO> pendingLeavesPageImplDTO = leaveService.findAllPendingLeavesOfEmployee(new LeaveRequestDTO(id, pageNumber, fromWhen, toWhen));
        	if(pendingLeavesPageImplDTO.getCode()!=1){    		
        		System.out.println("in gettingPendingLeavesOfEmp/"+pageNumber);
        		if(!CollectionUtils.isEmpty(pendingLeavesPageImplDTO.getData().getContent())){
        			setParam(model, pendingLeavesPageImplDTO, fromWhen, toWhen);
        		}
        		else{
        		System.out.println("empty page");	
        		}
        	}
        	else{
        		System.out.println(pendingLeavesPageImplDTO.getMessage());	
    		}
    	}
	return leaves;
    }
    
    private <T> void setParam(ModelMap model,PageResponseDTO<T> pageResponseDTO,String fromWhen,String toWhen){
    	int current = pageResponseDTO.getData().getNumber()+1;
		int begin = Math.max(1, current - 5);
		int end = Math.min(begin + 10, pageResponseDTO.getData().getTotalPages());
		System.out.println(current);	
		System.out.println(pageResponseDTO.getData().getContent());
		model.addAttribute("pages", pageResponseDTO.getData());
		model.addAttribute("beginIndex",begin);
		model.addAttribute("endIndex",end);
		model.addAttribute("currentIndex",current);
		model.addAttribute("fromWhen",fromWhen);
		model.addAttribute("toWhen", toWhen);
    }
    

    @RequestMapping(value="/emp/getApprovedLeavesOfEmp/{pageNumber}")
    public String getApprovedLeavesOfEmployee(@PathVariable int pageNumber,@RequestParam(value="emp",required=false)String id,@RequestParam(value="fromWhen",required=false)String fromWhen,@RequestParam(value="toWhen",required=false)String toWhen,HttpServletRequest request,ModelMap model,Principal principal){
    	String leaves = "emp/approved";
    	if(id==null){
    		id = request.getUserPrincipal().getName();
    	}
    	if(fromWhen.isEmpty() || toWhen.isEmpty()){
    		model.addAttribute("message","Please enter valid dates");
    	}
    	else{
    		PageResponseDTO<ApprovedLeaveDTO> approvedLeavesEmpPageResponseDTO = leaveService.findAllApprovedLeavesOfEmployee(new LeaveRequestDTO(id, pageNumber, fromWhen, toWhen));
    		if(approvedLeavesEmpPageResponseDTO.getCode()!=1){    		
    			System.out.println("in gettingApprovedLeavesOfEmp/"+pageNumber);
    			if(!CollectionUtils.isEmpty(approvedLeavesEmpPageResponseDTO.getData().getContent())){
    			setParam(model, approvedLeavesEmpPageResponseDTO, fromWhen, toWhen);    		
    			}
    			else{
    				System.out.println("empty page");	
    			}
    		}
    		else{
    		System.out.println(approvedLeavesEmpPageResponseDTO.getMessage());	
    		}
    	}
	return leaves;
    }

    

    @RequestMapping(value="/emp/getRejectedLeavesOfEmp/{pageNumber}")
    public String getRejectedLeavesOfEmployee(@PathVariable int pageNumber,@RequestParam(value="emp",required=false)String id,@RequestParam(value="fromWhen",required=false)String fromWhen,@RequestParam(value="toWhen",required=false)String toWhen,HttpServletRequest request,ModelMap model){
    	String leaves = "emp/rejected";
    	if(id==null){
    		id = request.getUserPrincipal().getName();
    	}
    	if(fromWhen.isEmpty() || toWhen.isEmpty()){
    		model.addAttribute("message","Please enter valid dates");
    	}
    	else{
    		PageResponseDTO<RejectedLeaveDTO> rejectedLeavesEmpPageResponseDTO = leaveService.findAllRejectedLeavesOfEmployee(new LeaveRequestDTO(id, pageNumber, fromWhen, toWhen));
    		if(rejectedLeavesEmpPageResponseDTO.getCode()!=1){    		
    		System.out.println("in gettingRejectedLeavesOfEmp/"+pageNumber);
    		if(!CollectionUtils.isEmpty(rejectedLeavesEmpPageResponseDTO.getData().getContent())){
    			setParam(model, rejectedLeavesEmpPageResponseDTO, fromWhen, toWhen);    		
    		}
    		else{
    		System.out.println("empty page");	
    		}
    		}
    		else{
    		System.out.println(rejectedLeavesEmpPageResponseDTO.getMessage());	
			}
    	}
	return leaves;
    }

    @RequestMapping(value="/calendar")
    public String calendar(){
    	return "calendar";
    }
    
    @RequestMapping(value="/getData1")
    @ResponseBody
    public List<Event> getData1(){
    	List<Event> list = new ArrayList<>();
    	Event e1 = new Event();
    	e1.setTitle("abc");
    	e1.setStart(LeaveUtil.getFirstDateOfWeek());
    	e1.setEnd(LeaveUtil.getFirstDateOfWeek());
    	e1.setAllDay(true);
    	e1.setId("1");
    	
    	
    	Event e2 = new Event();
    	e2.setTitle("bc");
    	e2.setStart(LeaveUtil.getLastDateOfWeek());
    	e2.setEnd(LeaveUtil.getLastDateOfWeek());
    	e2.setAllDay(true);
    	e2.setId("2");
    	list.add(e1);
    	list.add(e2);
    	return list;
    }
    
    
    @RequestMapping(value="/getData")
    @ResponseBody
    public List<Event> getData(){
    	List<Event> list = new ArrayList<>();
    	list = myCalendarService.populateCalendarData(LeaveUtil.getFirstDateOfMonth(),LeaveUtil.getLastDateOfMonth());
    	return list;
    }
    
    
    
    @RequestMapping(value="/manager/getLeavesApprovedByManager/{pageNumber}")
    public String getApprovedLeavesForManager(@PathVariable int pageNumber,@RequestParam(value="emp",required=false)String id,@RequestParam(value="fromWhen",required=false)String fromWhen,@RequestParam(value="toWhen",required=false)String toWhen,HttpServletRequest request,ModelMap model){
    	String leaves = "manager/leaves/approved";
    	System.out.println(id);
    	System.out.println(fromWhen);
    	System.out.println(toWhen);
    	if(id==null){
    		id = request.getUserPrincipal().getName();
    	}
    	if(fromWhen.isEmpty() || toWhen.isEmpty()){
    		model.addAttribute("message","Please enter valid dates");
    	}
    	else{
    		PageResponseDTO<com.sahil.models.manager.leave.ApprovedLeaveDTO> approvedLeavesEmpPageResponseDTO = leaveService.findLeavesApprovedByManager(new LeaveRequestDTO(id, pageNumber, fromWhen, toWhen));
    		if(approvedLeavesEmpPageResponseDTO.getCode()!=1){    		
    		System.out.println("in getLeavesApprovedByManager/"+pageNumber);
    			if(!CollectionUtils.isEmpty(approvedLeavesEmpPageResponseDTO.getData().getContent())){
    				setParam(model, approvedLeavesEmpPageResponseDTO, fromWhen, toWhen);
        		}
    			else{
    				System.out.println("empty page");	
    			}
    		}
    		else{
    		System.out.println(approvedLeavesEmpPageResponseDTO.getMessage());	
    		}
    	}
	return leaves;
    }



    @RequestMapping(value="/manager/getLeavesRejectedByManager/{pageNumber}")
    public String getRejectedLeavesForManager(@PathVariable int pageNumber,@RequestParam(value="emp",required=false)String id,@RequestParam(value="fromWhen",required=false)String fromWhen,@RequestParam(value="toWhen",required=false)String toWhen,HttpServletRequest request,ModelMap model){
    	String leaves = "manager/leaves/rejected";
    	if(id==null){
    		id = request.getUserPrincipal().getName();
    	}
    	if(fromWhen.isEmpty() || toWhen.isEmpty()){
    		model.addAttribute("message","Please enter valid dates");
    	}
    	else{
    		PageResponseDTO<com.sahil.models.manager.leave.RejectedLeaveDTO> approvedLeavesEmpPageResponseDTO = leaveService.findLeavesRejectedByManager(new LeaveRequestDTO(id, pageNumber, fromWhen, toWhen));
    		if(approvedLeavesEmpPageResponseDTO.getCode()!=1){    		
    			System.out.println("in getLeavesRejectedByManager/"+pageNumber);
    			if(!CollectionUtils.isEmpty(approvedLeavesEmpPageResponseDTO.getData().getContent())){
    				setParam(model, approvedLeavesEmpPageResponseDTO, fromWhen, toWhen);
    			}
    			else{
    				System.out.println("empty page");	
    			}
    		}
    		else{
    		System.out.println(approvedLeavesEmpPageResponseDTO.getMessage());	
    		}
    	}
	return leaves;
    }

    
        
    
}

