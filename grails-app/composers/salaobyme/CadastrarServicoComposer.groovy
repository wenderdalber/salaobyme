package salaobyme

import org.zkoss.zk.grails.composer.*
import org.zkoss.zk.ui.*;
import org.zkoss.zk.ui.event.*
import org.zkoss.zk.ui.select.annotation.*
import org.zkoss.zul.*

import salaobyme.Servico

class CadastrarServicoComposer extends zk.grails.Composer {

	@Wire
	Textbox descricao
	@Wire
	Listbox lstServicos
	
	@Wire
	Doublebox preco
	
	@Wire
	Label lblErro
	
	@Wire
	Combobox cbmSalao
	
	Servico servico

	Proprietario prop = new Proprietario()
	
	@Listen("onClick = #btnAdicionarServico")
	void adicionarServico(){
		servico = new Servico()
		servico.descricao = descricao.value
		servico.preco=preco.value
		servico.status=1
		
		Salao salao = Salao.findByNome(cbmSalao.value)
				
		salao.addToServicos(servico)
		
		servico.saloes.add(salao)
		
		if (!servico.hasErrors() && servico.save(flush:true)) {
			Messagebox.show("Serviço cadastrado com sucesso!")
			listarServico()
		}else {
			String x=""
			servico.errors.allErrors.each{
				x+=""+message(error:it)
			}
			lblErro.value=x
		}
	}

	void listarServico(){

		lstServicos.items.clear()
		Servico.list(sort:"descricao").each{ servico ->
			lstServicos.append{
				listitem(value:servico){
					listcell(label:servico.descricao)
					listcell(label:servico.preco)
					listcell(label:("Sim"))
				}
			}
		}
	}
	def afterCompose = { window ->
		// initialize components here
		
		ArrayList<Comboitem> saloes = new ArrayList<Comboitem>()
		
		prop = Proprietario.findById(session.getAttribute("usuario").proprietario.id)
		
		prop.saloes.each { salao ->
			cbmSalao.appendItem(salao.nome)
		
		listarServico()
	}
  }
}