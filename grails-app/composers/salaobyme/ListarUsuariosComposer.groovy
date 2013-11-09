package salaobyme

import org.zkoss.zk.grails.composer.*
import org.zkoss.zk.ui.*;
import org.zkoss.zk.ui.event.*
import org.zkoss.zk.ui.select.annotation.*
import org.zkoss.zul.*

import salaobyme.Pessoa

class ListarUsuariosComposer extends zk.grails.Composer {

	@Wire
	Textbox txtBuscarUsuarios
	@Wire
	Button btnBuscarUsuarios
	@Wire
	Listbox lstUsuarios

	Pessoa pessoa
	Usuario usuario

	@Listen("onClick = #btnBuscarUsuarios")
	void BuscarUsuario(){
		ListarUsuarios()
	}

	void ListarUsuarios() {
		lstUsuarios.items.clear()
		Pessoa.list(sort:"nome").each{ pessoa ->
			lstUsuarios.append{
				listitem(value:pessoa){
					if(txtBuscarUsuarios.value=="") {
						listcell(label:pessoa.nome)
						listcell(label:pessoa.email)
						listcell(label:pessoa.telefone)
						listcell(label:pessoa.celular)
					}else if (pessoa.nome.toLowerCase() .contains(txtBuscarUsuarios.value.toLowerCase() )) {
						listcell(label:pessoa.nome)
						listcell(label:pessoa.email)
						listcell(label:pessoa.telefone)
						listcell(label:pessoa.celular)
					}
				}
			}
		}
	}


	def afterCompose = { window -> ListarUsuarios() }
}