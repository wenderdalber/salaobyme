package salaobyme

import java.util.Formatter.DateTime;

import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.grails.composer.*
import org.zkoss.zk.ui.*;
import org.zkoss.zk.ui.event.*
import org.zkoss.zk.ui.select.annotation.*
import org.zkoss.zul.*

import salaobyme.Usuario

class PrincipalComposer extends zk.grails.Composer {

	@Wire
	Listbox listaSaloes
	
	Label lblNomeUsuario, lblEmailUsuario, lblErro
	
	@Wire
	Div logar, deslogar
	
	
	
    def afterCompose = { window ->
        // initialize components here
		listarSaloes()
		
		
    }
	
	void reservar(Listitem item) {
		Messagebox.show("Confirmar a Reserva?", "Confirma��o", Messagebox.OK | Messagebox.IGNORE  | Messagebox.CANCEL, Messagebox.QUESTION,
			new EventListener() {
				void onEvent(Event evt) throws InterruptedException {
					if (evt.getName().equals("onOK")) {
						
						Reserva reserva = new Reserva()
						Salao salao = new Salao()
						Servico servicos = new Servico()
						Horario horarios = new Horario()

						reserva.dataReserva=new Date()
						reserva.preco=25
						
						salao = Salao.get(1)
						
						servicos = Servico.get(1)
						horarios = Horario.get(1)
				
						reserva.situacao = 1
						reserva.dataReserva=new Date()
						reserva.preco=25
						
						reserva.salao = salao
						reserva.addToServicos(servicos)
						reserva.addToHorarios(horarios)
						reserva.usuario=session.getAttribute("usuario")
						
						if (/*!proprietario.hasErrors() &&*/ !reserva.hasErrors())
						{
							//proprietario.save(flush:true)
							if(reserva.save(flush:true))
							{
								
								Messagebox.show("Você reservou um serviço no Salão " + salao.nome)
								lblErro.value = ""
							}
							else
							{
								String x=""
								reserva.errors.allErrors.each{
									x+=""+message(error:it)
								}
								lblErro.value=x
							}
						}
						
					}
				}
			}
		)
	}
	
	@Listen("onClick = #btnReservar")
	void reservar() {
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
