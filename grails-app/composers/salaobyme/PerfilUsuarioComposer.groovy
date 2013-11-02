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
		Pessoa pessoa = new Pessoa()
		Usuario usuario = Usuario.get(id.value)
		if (usuario == null) usuario = new Usuario()
		usuario.id=id.value
		pessoa.nome=nome.value
		pessoa.email=email.value
		pessoa.telefone=telefone.value
		pessoa.celular=celular.value
		
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
		Pessoa pessoa = new Pessoa()
		Usuario usuario = e.target.selectedItem.value
		id.value=usuario.id
		nome.value=pessoa.nome
		email.value=pessoa.email
		telefone.value=pessoa.telefone
		celular.value=pessoa.celular
		
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
			
			int id = session.getAttribute("usuario").id
			Pessoa pessoa = new Pessoa()
			Usuario.get(id).each{ usuario ->
				listitem(value: usuario) { item ->
					listcell(label: usuario.id)
					listcell(label: pessoa.nome)
					listcell(label: pessoa.email)
					listcell(label: pessoa.telefone)
					listcell(label: pessoa.celular)
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
