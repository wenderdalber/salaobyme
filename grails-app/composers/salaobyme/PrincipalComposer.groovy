package salaobyme

import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.grails.composer.*
import org.zkoss.zk.ui.*;
import org.zkoss.zk.ui.event.*
import org.zkoss.zk.ui.select.annotation.*
import org.zkoss.zul.*
import org.zkoss.zul.Label;

import salaobyme.Usuario

class PrincipalComposer extends zk.grails.Composer {

	@Wire
	Listbox listaSaloes
	
	Label lblNomeUsuario, lblEmailUsuario
	
	def springSecurityService
	
    def afterCompose = { window ->
        // initialize components here
		listarSaloes()
		
		Usuario usuario = Usuario.findByUsername(springSecurityService.principal.username)
		
		lblNomeUsuario.value="Bem-vindo, "+usuario.nome
		
		if (usuario.authorities.find({it.authority == "ROLE_ADMIN"})){
			lblEmailUsuario.value="Seu cargo é "+usuario.email
		}
    }
	
	void listarSaloes() {
		listaSaloes.items.clear()
		
		listaSaloes.append {
			
			if (listaSaloes.heads.size() == 0) {
				listhead(sizable:true){
					listheader(label: "Número")
					listheader(label: "Nome")
					listheader(label: "Logradouro")
					listheader(label: "Número")
					listheader(label: "Bairro")
					listheader(label: "Cidade")
					listheader(label: "Estado")
					listheader(label: "")
				}
			}
			
			Salao.list().each{ salao ->
				listitem(value: salao) { item ->
					listcell(label: salao.id)
					listcell(label: salao.nome)
					listcell(label: salao.endereco.logradouro)
					listcell(label: salao.endereco.numero)
					listcell(label: salao.endereco.bairro)
					listcell(label: salao.endereco.cidade)
					listcell(label: salao.endereco.estado)
					listcell(label: ""){
						hlayout{
							button(label: 'Reservar', class: 'btn btn-primary', onClick: {
								e-> this.reservar(item);
								} )
						}
					}
				}
			}
		}
		
	}
}
