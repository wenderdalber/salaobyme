package salaobyme

import org.zkoss.zk.grails.composer.*
import org.zkoss.zk.ui.*;
import org.zkoss.zk.ui.event.*
import org.zkoss.zk.ui.select.annotation.*
import org.zkoss.zul.*

class SalaoComposer extends zk.grails.Composer {

	@Wire
	Intbox id
	@Wire
	Textbox nome
	@Wire
	Textbox cnpj
	@Wire
	Textbox logradouro
	@Wire
	Intbox numero
	@Wire
	Textbox bairro
	@Wire
	Textbox cidade
	@Wire
	Label lblErro
	
	@Wire
	Combobox cbmEstado
	
	Salao salao
	Endereco endereco
	
	@Listen("onClick = #btnSalvar")
	void salvar() {
		Proprietario prop = new Proprietario()
		Endereco end = new Endereco()
		Salao salao = Salao.get(id.value)
		if (salao == null) salao = new Salao()
		salao?.nome=nome.value
		salao?.cnpj=cnpj.value
		
		end?.logradouro=logradouro?.value
		end?.numero=numero?.value
		end?.bairro=bairro?.value
		end?.cidade=cidade?.value
		end?.estado=cbmEstado?.value
		
		//Messagebox.show(session.getAttribute("usuario").id.toString())
				
		//prop = session.getAttribute("usuario").proprietario		
		int id = session.getAttribute("usuario").id
		
		
		prop = Proprietario.findById(id)
		
		salao.proprietario = prop
		salao.endereco = end
		
		//salao.proprietario=1
		
		if (!salao.hasErrors()) {
			
			end.save(flush:true)
			salao.save(flush:true)
			
			Messagebox.show("Cadastro efetuado com sucesso!")
		}else {
			String x=""
			salao.errors.allErrors.each{
				x+=""+message(error:it)
			}
			lblErro.value=x
		}
	}
	
    def afterCompose = { window ->
        // initialize components here
    }
}
