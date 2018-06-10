package com.sahil.services.implementations;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sahil.entities.EmployeeEntity;
import com.sahil.entities.RoleEntity;
import com.sahil.repositories.EmployeeRepository;

@Service("logInService")
public class LogInServiceImpl implements UserDetailsService{

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Override
	public UserDetails loadUserByUsername(String employeeCode) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		System.out.println(employeeCode);
		EmployeeEntity user = employeeRepository.findOne(employeeCode); 
		if(user==null){
			System.out.println("No user with empCode "+employeeCode+" exists ");
			throw new UsernameNotFoundException("No user with empCode "+employeeCode+" exists ");
		}
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = buildSimpleGrantedAuthorities(user);
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getEmployeeCode(), user.getPassword(),true,true
                , true, true, simpleGrantedAuthorities);
        return userDetails;
	}
	
	
    private List<SimpleGrantedAuthority> buildSimpleGrantedAuthorities(final EmployeeEntity user) {
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        if (user.getUserRoles() != null) {
            for (RoleEntity role : user.getUserRoles()) {
            	System.out.println(role.getRoleName());
                simpleGrantedAuthorities.add(new SimpleGrantedAuthority(role.getRoleName()));
            }
        }
        return simpleGrantedAuthorities;
    }
	
	

}
