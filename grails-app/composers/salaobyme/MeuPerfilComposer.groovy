package salaobyme

import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.grails.composer.*
import org.zkoss.zk.ui.*;
import org.zkoss.zk.ui.event.*
import org.zkoss.zk.ui.select.annotation.*
import org.zkoss.zul.*

import salaobyme.Usuario;

class MeuPerfilComposer extends zk.grails.Composer {

	@Wire
	listaProprietario
	@Wire
	Panel panel
	@Wire
	Div divAdmin, divNotAdmin
	
	@Wire
	Intbox id
	@Wire
	Textbox nome
	@Wire
	Textbox cpf
	@Wire
	Textbox email
	@Wire
	Textbox telefone
	@Wire
	Textbox celular
	@Wire
	Textbox senha
	@Wire
	Label lblErro
	
	def springSecurityService
	
	//Usuario usuario = session.getAttribute("usuario")
	
	@Listen("onClick = #btnCancelar")
	void limpar() {
		panel.visible=false
	}
	
	@Listen("onDoubleClick = #listaProprietario")
	void alterar(Event e) {
		Proprietario proprietario = e.target.selectedItem.value
		id.value=proprietario.id
		nome.value=proprietario.nome
		cpf.value=proprietario.cpf
		email.value=proprietario.email
		telefone.value=proprietario.telefone
		celular.value=proprietario.celular
		
		panel.visible=true
	}
	
	@Listen("onClick = #btnAlterar")
	void salvar() {
		Proprietario proprietario = Proprietario.get(id.value)
		if (proprietario == null) proprietario = new Proprietario()
		proprietario.id=id.value
		proprietario.nome=nome.value
		proprietario.cpf=cpf.value
		proprietario.email=email.value
		proprietario.telefone=telefone.value
		proprietario.celular=celular.value
		
		if (!proprietario.hasErrors() && proprietario.save(flush:true)) {
			Messagebox.show("Usuario alterado com sucesso!")
			panel.visible=false
			listarProprietario()
		}else {
			String x=""
			proprietario.errors.allErrors.each{
				x+=""+message(error:it)
			}
			lblErro.value=x
		}
	}
	
    def afterCompose = { window ->
        // initialize components here
		
		//Usuario usuario = Usuario.findByUsername(springSecurityService.principal.username)
		
		//session.setAttribute("usuario",usuario)
		//session.getAttribute("usuario", usuario)
		
		listarProprietario()
    }
	
	void listarProprietario() {
		listaProprietario.items.clear()
		
		listaProprietario.append {
			
			if (listaProprietario.heads.size() == 0) {
				listhead(sizable:true){
					listheader(label: "ID")
					listheader(label: "Nome")
					listheader(label: "E-mail")
					listheader(label: "Telefone")
					listheader(label: "Celular")
				}
			}
			
			int id = session.getAttribute("usuario").id
			Proprietario.get(id).each{ proprietario ->
				listitem(value: proprietario) { item ->
					listcell(label: proprietario.id)
					listcell(label: proprietario.nome)
					listcell(label: proprietario.email)
					listcell(label: proprietario.telefone)
					listcell(label: proprietario.celular)
				}
			}
		}
		
	}

	void atualizarUsuarios(){
		
		Usuario usuario = session.getAttributes("usuario")
		
		if (usuario.authorities.find({it.authority == "ROLE_ADMIN"})){
			divAdmin.visible=true
			divNotAdmin.visible=false
		}else{
			divAdmin.visible=false
			divNotAdmin.visible=true
		}
		
		}
	
}
