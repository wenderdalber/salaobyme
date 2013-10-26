package salaobyme

import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.grails.composer.*
import org.zkoss.zk.ui.*;
import org.zkoss.zk.ui.event.*
import org.zkoss.zk.ui.select.annotation.*
import org.zkoss.zul.*

class UsuarioComposer extends zk.grails.Composer {
	
	@Wire
	Intbox id
	@Wire
	Textbox nome
	@Wire
	Textbox email
	@Wire
	Textbox telefone
	@Wire
	Textbox celular
	@Wire
	Textbox username
	@Wire
	Textbox password
	@Wire
	Label lblErro
	
    def afterCompose = { window ->
        // initialize components here
		
    }
	
	@Listen("onClick = #btnSalvar")
	void salvar() {
		Proprietario proprietario = new Proprietario()
		Usuario usuario = Usuario.get(id.value)
		if (usuario == null) usuario = new Usuario()
		proprietario.id=id.value
		proprietario?.nome=nome.value
		proprietario?.email=email.value
		proprietario?.telefone=telefone.value
		proprietario?.celular=celular.value
		proprietario?.tipo = 1
		
		usuario.username=username.value
		usuario.password=password.value
		usuario.proprietario = proprietario;
		
		if (!proprietario.hasErrors() && !usuario.hasErrors())
		{
			proprietario.save(flush:true)
			if(usuario.save(flush:true))
			{
				Messagebox.show("Cadastro de Proprietario efetuado com sucesso! Realize login no sistema!")
				lblErro.value = ""
			}
			else
			{
				String x=""
				proprietario.errors.allErrors.each{
					x+=""+message(error:it)
				}
				lblErro.value=x
			}
		}
	}
	
}
