package salaobyme

import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.grails.composer.*
import org.zkoss.zk.ui.*;
import org.zkoss.zk.ui.event.*
import org.zkoss.zk.ui.select.annotation.*
import org.zkoss.zul.*

import salaobyme.Salao;

class GerenciarSalaoComposer extends zk.grails.Composer {

	@Wire
	Listbox lstSalao
		
	@Wire
	Intbox id, numero
	@Wire
	Textbox nome, cnpj, logradouro, cidade, bairro
	
	@Wire
	Combobox cbmEstado
	
	@Wire
	Panel panel
	
	@Wire
	Label lblErro
	
	@Wire
	Button btnEditarSalao, btnCancelar
	
    def afterCompose = { window ->
        // initialize components here
		//int id = session.getAttribute("usuario").id
		
		//session.setAttribute("pegarSalao", pegarSalao)
		
		listarSalao()
    }
	
	@Listen("onClick = #btnCancelar")
	void cancelar() {
		
		panel.visible=false
	}
	
	@Listen("onDoubleClick = #lstSalao")
	void carregar(Event e) {
		
		Salao salao = e.target.selectedItem.value
		
		//Messagebox.show(salao.nome.toString())
		
		Endereco end = new Endereco()
		
		if (salao == null) salao = new Salao()
		
		id.value=salao.id
		nome.value=salao.nome
		cnpj.value=salao.cnpj
		logradouro.value=salao.endereco.logradouro
		numero.value=salao.endereco.numero
		cidade.value=salao.endereco.cidade
		bairro.value=salao.endereco.bairro
		cbmEstado.value=salao.endereco.estado
		
		panel.visible=true
	}
	
	@Listen("onClick = #btnSalvar")
	void alterar() {
		
		Salao salao = Salao.get(lstSalao.selectedItem.id.value)
		
		Endereco end = new Endereco()
		
		if (salao == null) salao = new Salao()
		
		salao.id=id.value
		salao.nome=nome.value
		salao.cnpj=cnpj.value
		
		end.logradouro=logradouro.value
		end.numero=numero.value
		end.cidade=cidade.value
		end.estado=cbmEstado.value
		
		salao.endereco = end
		
		if (!salao.hasErrors() && salao.save(flush:true)) {
			Messagebox.show("Salão alterado com sucesso!")
			panel.visible=false
		}else {
			String x=""
			salao.errors.allErrors.each{
				x+=""+message(error:it)
			}
			lblErro.value=x
		}
	}
	
	void listarSalao() {		
		lstSalao.append {
			listhead(sizable:true){
				listheader(label: "ID")
				listheader(label: "Nome")
				listheader(label: "CNPJ")
				listheader(label: "Logradouro")
				listheader(label: "Número")
				listheader(label: "Cidade")
				listheader(label: "Estado")
			}	
			
			ArrayList<Comboitem> saloes = new ArrayList<Comboitem>()
			
			Proprietario prop = Proprietario.findById(session.getAttribute("usuario").proprietario.id)
			
			prop.saloes.each{ salao ->
				listitem(value: salao) {
					listcell(label: salao.id)
					listcell(label: salao.nome)
					listcell(label: salao.cnpj)
					listcell(label: salao.endereco.logradouro)
					listcell(label: salao.endereco.numero)
					listcell(label: salao.endereco.cidade)
					listcell(label: salao.endereco.estado)
				}
			}
		}
	}
	
	void listarServicos() {
		lstServico.append {
			listhead(sizable:true){
				listheader(label: "ID")
				listheader(label: "Descricao")
			}
			Servico.list().each{ servico ->
				listitem(value: servico) {
					listcell(label: servico.descricao)
				}
			}
		}
	}
}
