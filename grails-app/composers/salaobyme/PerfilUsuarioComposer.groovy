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
		Proprietario proprietario = Proprietario.get(id.value)
		Usuario usuario = new Usuario()
		if (usuario == null) usuario = new Usuario()
		proprietario.id=id.value
		proprietario.nome=nome.value
		proprietario.email=email.value
		proprietario.telefone=telefone.value
		proprietario.celular=celular.value
		
		usuario.username=session.getAttribute("usuario").username
		usuario.password=session.getAttribute("usuario").password
		usuario.proprietario=proprietario
		
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
		//Pessoa pessoa = new Pessoa()
		Proprietario proprietario = e.target.selectedItem.value
		id.value=proprietario.usuario.id
		nome.value=proprietario.nome
		email.value=proprietario.email
		telefone.value=proprietario.telefone
		celular.value=proprietario.celular
		
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
			//Pessoa pessoa = new Pessoa()
			Pessoa.get(id).each{ pessoa ->
				listitem(value: pessoa) { item ->
					listcell(label: pessoa.id)
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
