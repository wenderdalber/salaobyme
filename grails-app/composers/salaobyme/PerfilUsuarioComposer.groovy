package salaobyme

import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.grails.composer.*
import org.zkoss.zk.ui.*;
import org.zkoss.zk.ui.event.*
import org.zkoss.zk.ui.select.annotation.*
import org.zkoss.zul.*

class PerfilUsuarioComposer extends zk.grails.Composer {

	@Wire
	listaUsuario
	@Wire
	Panel panel
	
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
	Textbox senha
	
	def springSecurityService
	
	Label lbUsuario, lbCargo
	
	@Listen("onClick = #btnCancelar")
	void limpar() {
		panel.visible=false
	}
	
	@Listen("onClick = #btnAlterar")
	void salvar() {
		Usuario usuario = Usuario.get(id.value)
		if (usuario == null) usuario = new Usuario()
		usuario.id=id.value
		usuario.nome=nome.value
		usuario.email=email.value
		usuario.telefone=telefone.value
		usuario.celular=celular.value
		
		if (!usuario.hasErrors() && usuario.save(flush:true)) {
			Messagebox.show("Usuario alterado com sucesso!")
			panel.visible=false
			listarUsuario()
		}else {
			String x=""
			usuario.errors.allErrors.each{
				x+=""+message(error:it)
			}
			lblErro.value=x
		}
	}
	
	@Listen("onDoubleClick = #listaUsuario")
	void alterar(Event e) {
		Usuario usuario = e.target.selectedItem.value
		id.value=usuario.id
		nome.value=usuario.nome
		email.value=usuario.email
		telefone.value=usuario.telefone
		celular.value=usuario.celular
		
		panel.visible=true
	}
	
    def afterCompose = { window ->
        // initialize components here
		listarUsuario()
		//Usuario usuario = Usuario.findByUsername(springSecurityService.principal.nome)
		
		//lbUsuario.value="Bem-vindo, "+usuario.nome
		
		//if (usuario.authorities.find({it.authority == "ROLE_ADMIN"})){
			//lbCargo.value="Seu email é "+usuario.email
		//}
    }
	
	void listarUsuario() {
		listaUsuario.items.clear()
		
		listaUsuario.append {
			
			if (listaUsuario.heads.size() == 0) {
				listhead(sizable:true){
					listheader(label: "ID")
					listheader(label: "Nome")
					listheader(label: "E-mail")
					listheader(label: "Telefone")
					listheader(label: "Celular")
					listheader(label: "")
				}
			}
			
			Usuario.list().each{ usuario ->
				listitem(value: usuario) { item ->
					listcell(label: usuario.id)
					listcell(label: usuario.nome)
					listcell(label: usuario.email)
					listcell(label: usuario.telefone)
					listcell(label: usuario.celular)
					listcell(label: ""){
						hlayout{
							toolbarbutton(label: 'Cancelar', image: "/images/skin/database_delete.png", onClick: {
								e-> this.excluir(item);
								} )
						}
					}
				}
			}
		}
	}
}
