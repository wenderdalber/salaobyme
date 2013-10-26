package salaobyme

import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.grails.composer.*
import org.zkoss.zk.ui.*;
import org.zkoss.zk.ui.event.*
import org.zkoss.zk.ui.select.annotation.*
import org.zkoss.zul.*

class MeuPerfilComposer extends zk.grails.Composer {

	@Wire
	listaProprietario
	@Wire
	Panel panel
	
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
			Messagebox.show("Usuario alterado com sucesso! Realize login no sistema!")
			panel.visible=false
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
					listheader(label: "")
				}
			}
			
			Proprietario.list().each{ proprietario ->
				listitem(value: proprietario) { item ->
					listcell(label: proprietario.id)
					listcell(label: proprietario.nome)
					listcell(label: proprietario.email)
					listcell(label: proprietario.telefone)
					listcell(label: proprietario.celular)
					listcell(label: ""){
						hlayout{
							toolbarbutton(label: 'Excluir', image: "/images/skin/database_delete.png", onClick: {
								e-> this.excluir(item);
								} )
						}
					}
				}
			}
		}
		
	}
}
