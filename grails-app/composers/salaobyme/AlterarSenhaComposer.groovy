package salaobyme

import org.apache.jasper.tagplugins.jstl.core.Redirect;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.grails.composer.*
import org.zkoss.zk.ui.*;
import org.zkoss.zk.ui.event.*
import org.zkoss.zk.ui.select.annotation.*
import org.zkoss.zul.*

class AlterarSenhaComposer extends zk.grails.Composer {

	@Wire
	Panel panel
	
	@Wire
	Textbox senhaAtual, senha, senhaConfirmar
	@Wire
	Label lblErro
	
	transient springSecurityService
	
    def afterCompose = { window ->
        // initialize components here
    }
	
	@Listen("onClick = #btnSalvar")
	void salvar() {
		Usuario usuario = new Usuario()
		
		senhaAtual.value = springSecurityService.encodePassword(senhaAtual.value)
		
		usuario=Usuario.findByPassword(senhaAtual.value)
		
		if(usuario != null)
		{
			if(senha.value == senhaConfirmar.value){
				
				usuario.password = senha.value
				
				if(usuario.save(flush:true))
				{
					Messagebox.show("Senha alterada com sucesso!")
					//lblErro.value = ""
				}
				else
				{
					String x=""
					usuario.errors.allErrors.each{
						x+=""+message(error:it)
					}
					lblErro.value=x
				}
			}else{
				Messagebox.show("Senhas não combinam")
			}
		}else
		{
			Messagebox.show("Senha inválida!")
		}
}
}