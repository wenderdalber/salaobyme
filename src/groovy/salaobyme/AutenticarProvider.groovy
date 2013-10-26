package salaobyme

import grails.plugins.springsecurity.SpringSecurityService


import salaobyme.Usuario;
import salaobyme.UsuarioPermissao;

import org.springframework.security.*
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.providers.*
import org.springframework.security.userdetails.*
import org.springframework.util.StringUtils;

class AutenticarProvider extends AbstractUserDetailsAuthenticationProvider {

	def dataSource
	
	def springSecurityService

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken token) throws AuthenticationException {
	}

	@Override
	protected UserDetails retrieveUser(String username,
			UsernamePasswordAuthenticationToken token) throws AuthenticationException {

		String password = (String) token.getCredentials();

		if (!StringUtils.hasText(password)) {
			throw new BadCredentialsException("Please enter password");
		}

		password=springSecurityService.encodePassword(password)
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		Usuario usuario=Usuario.findByUsernameAndPassword(username,password)
		
		if (usuario != null){
			
			UsuarioPermissao.findAllByUsuario(usuario).each{ usuarioPermissao ->
				authorities.add(new GrantedAuthorityImpl(usuarioPermissao.permissao.authority))
			}
			
		}else{
			throw new BadCredentialsException("Invalid username or password!");
		}

		return new User(username, password,	true, true, true, true,	authorities);
	}
	
}

