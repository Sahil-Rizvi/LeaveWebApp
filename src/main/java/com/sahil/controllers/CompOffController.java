package com.sahil.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sahil.enums.ResponseType;
import com.sahil.models.PageResponseDTO;
import com.sahil.models.Response;
import com.sahil.models.emp.compoff.ApprovedCompOffDTO;
import com.sahil.models.emp.compoff.PendingCompOffDTO;
import com.sahil.models.emp.compoff.RejectedCompOffDTO;
import com.sahil.models.input.CompOff;
import com.sahil.models.manager.compoff.UpdateCompOffRequestDTO;
import com.sahil.models.manager.compoff.UpdateCompOffRequestListDTO;
import com.sahil.models.manager.compoff.UpdateCompOffResponseDTO;
import com.sahil.models.request.CompOffRequestDTO;
import com.sahil.services.CompOffService;

@Controller
@RequestMapping("/compOff")
public class CompOffController {


	private static final Logger logger = Logger.getLogger(CompOffController.class);
	
	@Autowired
	private CompOffService compOffService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    logger.info("in initBinder");
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}


	@RequestMapping(value="/emp/compOffHome")
	public String home(ModelMap modelMap,HttpServletRequest request){
		logger.info("in home");
		logger.info("empCode"+request.getUserPrincipal().getName());
		if(request.getUserPrincipal().getName()!=null){
			return "compoffhome";	
		}
		return "redirect:/";
	}

	@RequestMapping(value="/emp/deleteCompOff")
	@ResponseBody
	public String deleteCompOff(@RequestParam(value="id",required=false)int id,HttpServletRequest request){
		if(request.getUserPrincipal().getName()!=null){
			return compOffService.deleteCompOff(id).getMessage();
		}
		return "Please logIn";
	}
	
	
	
	@RequestMapping(value="/emp/addCompOff")
	@ResponseBody
	public String addCompOff(@Valid @ModelAttribute CompOff compOff,BindingResult bindingResult,HttpServletRequest request){
		
		logger.info("in addCompOff with CompOff"+compOff);
		if(bindingResult.hasErrors()){
			logger.error("Error in bindingResult "+bindingResult.getAllErrors());
			System.out.println(bindingResult.getAllErrors());
			return "ERROR IN BINDING DATA";
		}

		System.out.println(compOff);

			String empId = request.getUserPrincipal().getName();
			System.out.println(empId);
			logger.info("empCode"+request.getUserPrincipal().getName());
			Response response = compOffService.addCompOff(compOff, empId);
			System.out.println(response);
			logger.info("Response from addCompOff of Service "+response);
			if(response.getCode()==1){
				return "CompOff Not Added <br> Reason : "+response.getMessage();
			}
			else{
				return response.getMessage();
			}

		
	}


	@RequestMapping(value="/emp/status/{compOffStatus}")
	public String getLeavesForm(@PathVariable String compOffStatus){
		logger.info("in getLeavesForm with CompOffStatus"+compOffStatus);
		String jsp = "emp/compoff/";
		if("pending".equals(compOffStatus.toLowerCase())){
			jsp += "getpendingcompoffsform";
		}
		else
			if("approved".equals(compOffStatus.toLowerCase())){
				jsp += "getapprovedcompoffsform";
			}
			else
				if("rejected".equals(compOffStatus.toLowerCase())){
					jsp += "getrejectedcompoffsform";
				}
				else
					if("teammates".equals(compOffStatus.toLowerCase())){
						jsp += "getteammatescompoffsform";
					}
		logger.info("return view : "+jsp);
		return jsp;
	}


	@RequestMapping(value="/manager/status/{compOffStatus}")
	public String getLeavesFormForManager(@PathVariable String compOffStatus){
	    logger.info(" in getLeavesFormForManager with CompOffStatus"+compOffStatus);
		String jsp = "manager/compoffs/";
	    if("approved".equals(compOffStatus.toLowerCase())){
	    	jsp += "getapprovedcompoffsform";
	    }
	    else
	    if("rejected".equals(compOffStatus.toLowerCase())){
	    	jsp += "getrejectedcompoffsform";
	    }
	    logger.info("return view : "+jsp);
	    return jsp;
	}
	
	
	
	@RequestMapping(value="/manager/getCompOffsForUpdationForManager")
	public String findAllCompOffsForManagerForUpdation(Model model,HttpServletRequest request){
		String leaves = "manager/approveorrejectcompoffs";
		logger.info("in findAllCompOffsForManagerForUpdation");
		System.out.println("in getCompOffsForUpdationForManager");
		String empId = request.getUserPrincipal().getName();
		logger.info("empCode "+request.getUserPrincipal().getName());
		UpdateCompOffResponseDTO updateCompOffResponseDTO = compOffService.findAllCompOffsByManagerForUpdation(empId);
		logger.info("UpdateCompOffResponseDTO of findAllCompOffsByManagerForUpdation from service  "+updateCompOffResponseDTO);
		if(updateCompOffResponseDTO.getCode()!=1){    		
			if(!CollectionUtils.isEmpty(updateCompOffResponseDTO.getCompOffs())){
				System.out.println(updateCompOffResponseDTO.getCompOffs().toString());
				model.addAttribute("compOffResponse",updateCompOffResponseDTO);
				model.addAttribute("enums",ResponseType.values());
			}
			else{
				logger.info("empty page");
				System.out.println("empty page");	
			}
		}
		else{
			System.out.println(updateCompOffResponseDTO.getMessage());	
		}
		logger.info("returning view : "+leaves);
		return leaves;
	}


	@RequestMapping(value="/manager/approveOrReject",method=RequestMethod.POST)
	@ResponseBody
	public String updateLeaves(@ModelAttribute UpdateCompOffRequestListDTO updateCompOffDTOs,HttpServletRequest request){
		logger.info("in updateLeaves with UpdateCompOffRequestListDTO"+updateCompOffDTOs);
		for(UpdateCompOffRequestDTO dto:updateCompOffDTOs.getCompOffs()){
			System.out.println(dto.toString());
		}
		return compOffService.updateCompOffDetails(request.getUserPrincipal().getName(),updateCompOffDTOs).getMessage();

	}


	@RequestMapping(value="/emp/getPendingCompOffsOfEmp/{pageNumber}")
	public String getPendingCompOffsOfEmployee(@PathVariable int pageNumber,@RequestParam(value="emp",required=false)String id,@RequestParam(value="fromWhen",required=false)String fromWhen,@RequestParam(value="toWhen",required=false)String toWhen, HttpServletRequest request,ModelMap model){
		String jsp = "emp/compoff/pending";
		logger.info("in getPendingCompOffsOfEmployee with pageNumber: "+pageNumber+" , emp: "+id+" , fromWhen: "+fromWhen+" toWhen: "+toWhen);
		if(id==null){
			id = request.getUserPrincipal().getName();
			logger.info("taking emp as "+request.getUserPrincipal().getName());
		}
		if(fromWhen.isEmpty() || toWhen.isEmpty()){
			model.addAttribute("message","Please enter valid dates");
			logger.error("Invalid dates");
		}
		else{
			PageResponseDTO<PendingCompOffDTO> pendingCompOffs = compOffService.findAllPendingCompOffsOfEmployee(new CompOffRequestDTO(id,pageNumber,fromWhen,toWhen));
			logger.info("pendingCompOffs : "+pendingCompOffs +" from findAllPendingCompOffsOfEmployee of service");
			if(pendingCompOffs.getCode()!=1){    		
				System.out.println("in getPendingCompOffsOfEmp/"+pageNumber);
				if(!CollectionUtils.isEmpty(pendingCompOffs.getData().getContent())){
					setParam(model, pendingCompOffs, fromWhen, toWhen);
				}
				else{
					logger.info("empty page");
					System.out.println("empty page");	
				}
			}
			else{
				System.out.println(pendingCompOffs.getMessage());	
			}
		}
		logger.info("returning view :"+jsp);
		return jsp;
	}


	@RequestMapping(value="/emp/getApprovedCompOffsOfEmp/{pageNumber}")
	public String getApprovedCompOffsOfEmployee(@PathVariable int pageNumber,@RequestParam(value="emp",required=false)String id,@RequestParam(value="fromWhen",required=false)String fromWhen,@RequestParam(value="toWhen",required=false)String toWhen,HttpServletRequest request,ModelMap model){
		//String empId = request.getSession(false).getAttribute("empId").toString();
		String jsp = "emp/compoff/approved";
		logger.info("in getApprovedCompOffsOfEmployee with pageNumber: "+pageNumber+" , emp: "+id+" , fromWhen: "+fromWhen+" toWhen: "+toWhen);
		
		if(id==null){
			id = request.getUserPrincipal().getName();
			logger.info("taking emp as "+request.getUserPrincipal().getName());
		}
		if(fromWhen.isEmpty() || toWhen.isEmpty()){
			model.addAttribute("message","Please enter valid dates");
			logger.error("Invalid dates");
		}
		else{
			PageResponseDTO<ApprovedCompOffDTO> pageResponseDTO = compOffService.findAllApprovedCompOffsOfEmployee(new CompOffRequestDTO(id,pageNumber,fromWhen,toWhen));
			logger.info("approvedCompOffs : "+pageResponseDTO +" from findAllApprovedCompOffsOfEmployee of service");
			if(pageResponseDTO.getCode()!=1){    		
				System.out.println("in getApprovedCompOffsOfEmp/"+pageNumber);
				if(!CollectionUtils.isEmpty(pageResponseDTO.getData().getContent())){
					setParam(model, pageResponseDTO, fromWhen, toWhen);
				}
				else{
					logger.info("empty page");
					System.out.println("empty page");	
				}
			}
			else{
				System.out.println(pageResponseDTO.getMessage());	
			}
		}	
		logger.info("returing view :"+jsp);
		return jsp;
	}



	@RequestMapping(value="/emp/getRejectedCompOffsOfEmp/{pageNumber}")
	public String getRejectedCompOffsOfEmployee(@PathVariable int pageNumber,@RequestParam(value="emp",required=false)String id,@RequestParam(value="fromWhen",required=false)String fromWhen,@RequestParam(value="toWhen",required=false)String toWhen,HttpServletRequest request,ModelMap model){
		//String empId = request.getSession(false).getAttribute("empId").toString();
		String jsp = "emp/compoff/rejected";
		logger.info("in getRejectedCompOffsOfEmployee with pageNumber: "+pageNumber+" , emp: "+id+" , fromWhen: "+fromWhen+" toWhen: "+toWhen);
		
		if(id==null){
			id = request.getUserPrincipal().getName();
			logger.info("taking emp as "+request.getUserPrincipal().getName());
		}
		if(fromWhen.isEmpty() || toWhen.isEmpty()){
			logger.error("Invalid dates");
			model.addAttribute("message","Please enter valid dates");
		}
		else{
			PageResponseDTO<RejectedCompOffDTO> pageResponseDTO = compOffService.findAllRejectedCompOffsOfEmployee(new CompOffRequestDTO(id,pageNumber,fromWhen,toWhen));
			logger.info("rejectedCompOffs : "+pageResponseDTO +" from findAllRejectedCompOffsOfEmployee of service");
			if(pageResponseDTO.getCode()!=1){    		
				System.out.println("in getApprovedCompOffsOfEmp/"+pageNumber);
				if(!CollectionUtils.isEmpty(pageResponseDTO.getData().getContent())){
					setParam(model, pageResponseDTO, fromWhen, toWhen);
				}
				else{
					logger.info("empty page");
					System.out.println("empty page");	
				}
			}
			else{
				System.out.println(pageResponseDTO.getMessage());	
			}
		}
		logger.info("returning view:"+jsp);
		return jsp;
	}

	


	@RequestMapping(value="/manager/getCompOffsApprovedByManager/{pageNumber}")
	public String getCompOffsApprovedByManager(@PathVariable int pageNumber,@RequestParam(value="emp",required=false)String id,@RequestParam(value="fromWhen",required=false)String fromWhen,@RequestParam(value="toWhen",required=false)String toWhen,HttpServletRequest request,ModelMap model){
		String jsp = "manager/compoffs/approved";
		logger.info("in getCompOffsApprovedByManager with pageNumber: "+pageNumber+" , emp: "+id+" , fromWhen: "+fromWhen+" toWhen: "+toWhen);
		
		if(id==null){
			id = request.getUserPrincipal().getName();
			logger.info("taking emp as "+request.getUserPrincipal().getName());
		}
		if(fromWhen.isEmpty() || toWhen.isEmpty()){
			logger.error("Invalid dates");
			model.addAttribute("message","Please enter valid dates");
		}
		else{
			PageResponseDTO<com.sahil.models.manager.compoff.ApprovedCompOffDTO> responseDTO = compOffService.findCompOffsApprovedByManager(new CompOffRequestDTO(id, pageNumber, fromWhen, toWhen));
			logger.info("approvedCompOffsForManager : "+responseDTO +" from findCompOffsApprovedByManager of service");
			
			if(responseDTO.getCode()!=1){    		
				System.out.println("in getCompOffsApprovedByManager/"+pageNumber);
				if(!CollectionUtils.isEmpty(responseDTO.getData().getContent())){
					setParam(model, responseDTO, fromWhen, toWhen);
				}
				else{
					logger.info("empty page");
					System.out.println("empty page");	
				}
			}
			else{
				System.out.println(responseDTO.getMessage());	
			}
		}
		logger.info("returning view:"+jsp);
		return jsp;
	}



	@RequestMapping(value="/manager/getCompOffsRejectedByManager/{pageNumber}")
	public String getCompOffsRejectedByManager(@PathVariable int pageNumber,@RequestParam(value="emp",required=false)String id,@RequestParam(value="fromWhen",required=false)String fromWhen,@RequestParam(value="toWhen",required=false)String toWhen,HttpServletRequest request,ModelMap model){
		String jsp = "manager/compoffs/rejected";
		logger.info("in getCompOffsRejectedByManager with pageNumber: "+pageNumber+" , emp: "+id+" , fromWhen: "+fromWhen+" toWhen: "+toWhen);
		
		if(id==null){
			id = request.getUserPrincipal().getName();
			logger.info("taking emp as "+request.getUserPrincipal().getName());
		}
		if(fromWhen.isEmpty() || toWhen.isEmpty()){
			logger.error("Invalid dates");
			model.addAttribute("message","Please enter valid dates");
		}
		else{
			PageResponseDTO<com.sahil.models.manager.compoff.RejectedCompOffDTO> responseDTO = compOffService.findCompOffsRejectedByManager(new CompOffRequestDTO(id, pageNumber, fromWhen, toWhen));
			logger.info("rejectedCompOffsForManager : "+responseDTO +" from findCompOffsRejectedByManager of service");
			
			if(responseDTO.getCode()!=1){    		
				System.out.println("in getCompOffsRejectedByManager/"+pageNumber);
				if(!CollectionUtils.isEmpty(responseDTO.getData().getContent())){
					setParam(model, responseDTO, fromWhen, toWhen);
				}
				else{
					logger.info("empty page");
					System.out.println("empty page");	
				}
			}
			else{
				System.out.println(responseDTO.getMessage());	
			}
		}
		logger.info("returning view:"+jsp);
		return jsp;
	}
	
	
	private <T> void setParam(ModelMap model,PageResponseDTO<T> pageResponseDTO,String fromWhen,String toWhen){
		logger.info("in setParam with pageResponseDTO "+pageResponseDTO+", fromWhen"+fromWhen+", toWhen"+toWhen);
		int current = pageResponseDTO.getData().getNumber()+1;
		int begin = Math.max(1, current - 5);
		int end = Math.min(begin + 10, pageResponseDTO.getData().getTotalPages());
		
		logger.info("currentPageNo. "+current);
		logger.info("beginPageNo. "+begin);
		logger.info("endPageNo. "+end);
		
		
		System.out.println(current);	
		logger.info("pages Content"+pageResponseDTO.getData().getContent());
		System.out.println(pageResponseDTO.getData().getContent());
		model.addAttribute("pages", pageResponseDTO.getData());
		model.addAttribute("beginIndex",begin);
		model.addAttribute("endIndex",end);
		model.addAttribute("currentIndex",current);
		model.addAttribute("fromWhen",fromWhen);
		model.addAttribute("toWhen", toWhen);
	}


	
}
