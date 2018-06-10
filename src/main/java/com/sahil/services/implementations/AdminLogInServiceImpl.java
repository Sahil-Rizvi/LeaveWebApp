package com.sahil.services.implementations;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sahil.entities.AdminEntity;
import com.sahil.entities.RoleEntity;
import com.sahil.repositories.AdminRepository;

@Service("adminLogInService")
public class AdminLogInServiceImpl implements UserDetailsService{

	@Autowired
	private AdminRepository adminRepository;
	
	@Override
	public UserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		System.out.println(emailId);
		AdminEntity admin = adminRepository.findByEmailId(emailId);
		if(admin==null){
			System.out.println("No admin with emailId "+emailId+" exists ");
			throw new UsernameNotFoundException("No admin with emailId "+emailId+" exists ");
		}
	    List<SimpleGrantedAuthority> simpleGrantedAuthorities = buildSimpleGrantedAuthorities(admin);
	    UserDetails userDetails = new org.springframework.security.core.userdetails.User(admin.getName(),admin.getPassword(),true,true
	            , true, true, simpleGrantedAuthorities);
	    return userDetails;
	}


private List<SimpleGrantedAuthority> buildSimpleGrantedAuthorities(final AdminEntity admin) {
    List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
    if (admin.getAdminRoles() != null) {
        for (RoleEntity role : admin.getAdminRoles()) {
        	System.out.println(role.getRoleName());
            simpleGrantedAuthorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        }
    }
    return simpleGrantedAuthorities;
}


	
}
