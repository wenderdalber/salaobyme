package salaobyme

import org.zkoss.zk.grails.composer.*
import org.zkoss.zk.ui.*;
import org.zkoss.zk.ui.event.*
import org.zkoss.zk.ui.select.annotation.*
import org.zkoss.zul.*

import salaobyme.Servico

class CadastrarServicoComposer extends zk.grails.Composer {

	@Wire
	Combobox cmbCadastrarServico
	@Wire
	Listbox lstServicos
	
	Servico servico

	@Listen("onClick = #btnAdicionarServico")
	void adicionarServico(){
		servico = new Servico()
		servico.descricao = cmbCadastrarServico.value;
		servico.status=1
		servico.save(true)
		listarServico()
	}
	
	void excluir(Listitem item) {
		Messagebox.show("Confirma?", "Confirma��o", Messagebox.OK | Messagebox.IGNORE  | Messagebox.CANCEL, Messagebox.QUESTION,
			new EventListener() {
				void onEvent(Event evt) throws InterruptedException {
					if (evt.getName().equals("onOK")) {
						servico=item.value
						servico.delete(flush:true)
						item.detach()
					}
				}
			}
		)
	}

	void listarServico(){

		lstServicos.items.clear()
		Servico.list(sort:"descricao").each{ servico ->
			lstServicos.append{
				listitem(value:servico){
					listcell(label:servico.descricao)
					listcell(label:("Sim"))
					listcell(label: ""){
						hlayout{
							button(label: 'Excluir', class: 'btn btn-danger', onClick: {
								e-> this.excluir(item);
								} )
						}
					}
				}
			}
		}
	}
	def afterCompose = { window ->
		// initialize components here
		listarServico()
	}
}
