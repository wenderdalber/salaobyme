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
	Listbox lstServico
	@Wire
	Listbox lstHorario
	
	@Wire
	Intbox id, numero
	@Wire
	Textbox nome, cnpj, logradouro, cidade, estado
	
	@Wire
	Panel panel
	
	@Wire
	Button btnEditarSalao, btnCancelar
	
    def afterCompose = { window ->
        // initialize components here
		//int id = session.getAttribute("usuario").id
		
		//session.setAttribute("pegarSalao", pegarSalao)
		
		listarSalao()
		listarServicos()
    }
	
	@Listen("onClick = #btnCancelar")
	void cancelar() {
		
		panel.visible=false
	}
	
	@Listen("onClick = #btnEditarSalao")
	void editar() {
		
		Salao salao = Salao.get(id.value)
		Endereco end = new Endereco()
		if (salao == null) salao = new Salao()
		salao.id=id.value
		salao.nome=nome.value
		salao.cnpj=cnpj.value
		salao.endereco = end
		end.logradouro=logradouro.value
		end.numero=numero.value
		end.cidade=cidade.value
		end.estado=estado.value
		
		panel.visible=true
	}
	
	@Listen("onClick = #btnSalvar")
	void alterar() {
		Salao salao = lstSalao.selectedItem.value
		Endereco end = new Endereco()
		if (salao == null) salao = new Salao()
		salao.id=id.value
		salao.nome=nome.value
		salao.cnpj=cnpj.value
		end.logradouro=logradouro.value
		end.numero=numero.value
		end.cidade=cidade.value
		end.estado=estado.value
		
		if (!salao.hasErrors() && salao.save(flush:true)) {
			Messagebox.show("Usuario alterado com sucesso!")
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
			}	
			Salao.list().each{ salao ->
				listitem(value: salao) {
					listcell(label: salao.nome)
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
