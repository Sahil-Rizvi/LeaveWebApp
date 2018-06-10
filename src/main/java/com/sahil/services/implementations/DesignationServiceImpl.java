package com.sahil.services.implementations;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.sahil.entities.DesignationEntity;
import com.sahil.entities.EmployeeEntity;
import com.sahil.models.DesigList;
import com.sahil.models.Designation;
import com.sahil.models.Response;
import com.sahil.repositories.DesignationRepository;
import com.sahil.repositories.EmployeeRepository;
import com.sahil.services.DesignationService;
import com.sahil.utils.LeaveUtil;

@Service
public class DesignationServiceImpl implements DesignationService{

	@Autowired
	private DesignationRepository designationRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;

	
	@Override
	public boolean anyDesignationAdded(){
		return designationRepository.count()>0 ?true:false;
	}
	
	
	@Override
	public Response addDesignations(List<String> designations) {
		Response response = new Response();
		if(CollectionUtils.isEmpty(designations)){
			response.setCode(1);
			response.setMessage("Please enter some designation");
            return response;
		}
		
		List<String> list = new ArrayList<>();
		
		for(String designation:designations){
			DesignationEntity designationEntity = new DesignationEntity();
			if(!StringUtils.isEmpty(designation)){
				designationEntity.setName(LeaveUtil.toTitleCase(designation));
				designationRepository.save(designationEntity);
				list.add(LeaveUtil.toTitleCase(designation));
			}
		}
		
		if(!list.isEmpty()){
			response.setCode(0);
			response.setMessage(" ADDED DESIGNATIONS "+list);
		}
	    return response;

	}

	@Override
	public Response editDesignation(int id, String oldDesignation, String newDesignation) {
		// TODO Auto-generated method stub
		Response response = new Response();
		String oldDesig = LeaveUtil.toTitleCase(oldDesignation);
		String newDesig = LeaveUtil.toTitleCase(newDesignation);
		if(id<=0 || StringUtils.isEmpty(oldDesig) || StringUtils.isEmpty(newDesig)){
			response.setCode(1);
			response.setMessage("INVALID DATA");
			return response;
		}
		
		DesignationEntity designationEntity = designationRepository.findOne(id);
		response.setCode(1);
		response.setMessage("NO DESIGNATION EXISTS WITH ID "+id);
		
		if(designationEntity!=null){
			if(designationEntity.getName().equals(oldDesig)){
				designationEntity.setName(newDesig);
				designationRepository.save(designationEntity);
				response.setCode(0);
				response.setMessage("DESIGNATION ID: "+id+" EDITED FROM "+oldDesig+" TO "+newDesig);
				return response;
			}
			response.setMessage("DESIGNATION ID: "+id+" DOES NOT HAVE NAME AS "+oldDesig);
		}
		
		return response;

	}

	@Override
	public Response deleteDesignation(int id, String designation) {
		// TODO Auto-generated method stub
		Response response = new Response();
		String desig = LeaveUtil.toTitleCase(designation);
		if(id<=0 || StringUtils.isEmpty(desig)){
			response.setCode(1);
			response.setMessage("INVALID DATA");
			return response;
		}
		
		DesignationEntity designationEntity = designationRepository.findOne(id);
		response.setCode(1);
		response.setMessage("NO DEPARTMENT EXISTS WITH ID "+id);
		
		if(designationEntity!=null){
			if(designationEntity.getName().equals(desig)){
				List<EmployeeEntity> employeeEntities = employeeRepository.findByDesignationEntity(designationEntity);
				for(EmployeeEntity employeeEntity:employeeEntities){
					employeeEntity.setDesignationEntity(null);
					employeeRepository.save(employeeEntity);
				}
				designationRepository.delete(designationEntity);
				response.setCode(0);
				response.setMessage("DESIGNATION ID: "+id+" WITH NAME "+desig+" DELETED SUCCESSFULLY ");
				return response;
			}
			response.setMessage("DESIGNATION ID: "+id+" DOES NOT HAVE NAME AS "+desig);
		}
		
		return response;

	}

	@Override
	public DesigList getAllDesignations() {
		// TODO Auto-generated method stub
		DesigList desigList = new DesigList();
		desigList.setCode(1);
		desigList.setMessage("NO DESIGNATION EXISTS");
		List<Designation> list = new ArrayList<>();
		desigList.setDesignations(list);
      
		List<DesignationEntity> designationEntities = designationRepository.findAll();
		if(!CollectionUtils.isEmpty(designationEntities)){
			list = designationEntities.stream().map(this::convert).collect(Collectors.toList());
			desigList.setCode(0);
			desigList.setMessage("SUCCESSFUL");
			desigList.setDesignations(list);
		}
		return desigList;
	}
	
	private Designation convert(DesignationEntity designationEntity){
		Designation designation = new Designation();
		if(Objects.nonNull(designationEntity)){
			designation.setId(designationEntity.getDesignationId());
			designation.setName(designationEntity.getName());
		}
		return designation;
	}

	@Override
	public List<String> getAllDesignationNames() {
		// TODO Auto-generated method stub
		List<String> list = new ArrayList<>();
		List<DesignationEntity> departmentEntities = designationRepository.findAll();
		if(!CollectionUtils.isEmpty(departmentEntities)){
			list = departmentEntities.stream().map(e->e.getName()).collect(Collectors.toList());
		}
		return list;
	}
	


}
