package salaobyme

import javax.swing.text.DefaultEditorKit.PageAction;

import org.apache.jasper.tagplugins.jstl.core.Redirect;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.grails.composer.*
import org.zkoss.zk.ui.*;
import org.zkoss.zk.ui.event.*
import org.zkoss.zk.ui.select.annotation.*
import org.zkoss.zul.*


class ElementosComposer extends zk.grails.Composer {

	
	@Wire
	Div perfilUsuario, perfilProprietario
	Label lblNomeUsuario, lblEmailUsuario
	//@Wire
	//Div logar, deslogar
	
	//@Wire
	//Label lblNomeUsuario
	
	def springSecurityService
	
    def afterCompose = { window ->
        // initialize components here
		//logados()
		
		Usuario usuario = Usuario.findByUsername(springSecurityService.principal.username)
		
		session.setAttribute("usuario",usuario)
		
		lblNomeUsuario.value="Bem-vindo, "+usuario.username
		
		if (usuario.authorities.find({it.authority == "ROLE_ADMIN"})){
			//lblEmailUsuario.value="Logado como proprietário"
			perfilProprietario.visible=true
			perfilUsuario.visible=false
		}else{
			//lblEmailUsuario.value="Logado como usuário"
			perfilUsuario.visible=true
			perfilProprietario.visible=false
		}
   }
}
