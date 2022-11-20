package com.proyecto.app.classes;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.proyecto.app.entities.Usuarios;

public class CustomUserDetails implements UserDetails{

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Usuarios user;
	 
	 public CustomUserDetails(Usuarios user) {
	        this.user = user;
	    }
	 
	 @Override
	    public Collection<? extends GrantedAuthority> getAuthorities() {
	        return null;
	    }
	 
	    @Override
	    public String getPassword() {
	        return user.getClave();
	    }
	 
	    @Override
	    public String getUsername() {
	        return user.getEmail();
	    }
	    
	    public Long getIdUser() {
	    	
	    	return user.getIdUsuario();
	    }
	 
	    @Override
	    public boolean isAccountNonExpired() {
	        return true;
	    }
	 
	    @Override
	    public boolean isAccountNonLocked() {
	        return true;
	    }
	 
	    @Override
	    public boolean isCredentialsNonExpired() {
	        return true;
	    }
	 
	    @Override
	    public boolean isEnabled() {
	        return true;
	    }
	     
	
}
