package salaobyme

import javax.swing.ListCellRenderer;

import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.grails.composer.*
import org.zkoss.zk.ui.*;
import org.zkoss.zk.ui.event.*
import org.zkoss.zk.ui.select.annotation.*
import org.zkoss.zul.*

class ReservarComposer extends zk.grails.Composer {

	@Wire
	Listbox listaSaloes
	
	@Wire
	Textbox telefone
	
	@Wire
	Label lblNome, lblNomeSalao, lblServico, lblErro
	
	@Wire
	Listbox lstServico, lstHorario
	
    def afterCompose = { window ->
        // initialize components here
		listarSaloes()
		listarServicos()
		listarHorarios()
    }
	
	void reservar(Listitem item) {
		Messagebox.show("Confirmar a Reserva?", "Confirmação", Messagebox.OK | Messagebox.IGNORE  | Messagebox.CANCEL, Messagebox.QUESTION,
			new EventListener() {
				void onEvent(Event evt) throws InterruptedException {
					if (evt.getName().equals("onOK")) {
						
						Reserva reserva = new Reserva()
						Salao salao = new Salao()
						Servico servicos = new Servico()
						Horario horarios = new Horario()

						reserva.situacao=1
						reserva.dataReserva=new Date()
						
						salao = Salao.get(item.value.id)
						
						
						if(lstServico.selectedItem.value != null)
						{
							if(lstHorario.selectedItem.value != null)
							{
								if(salao.servicos.size() > 0)
								{
									servicos = lstServico.selectedItem.value
									horarios = lstHorario.selectedItem.value
									
									reserva.salao = salao
									reserva.addToServicos(servicos)
									reserva.addToHorarios(horarios)
									reserva.usuario=session.getAttribute("usuario")
									
									//Servico servico = salao.servicos(lazy:true)
									//Servico servico = salao.servicos.grep(lstServico.selectedItem.value)
									
									if(salao.servicos.size() > 0)
									{
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
								}else
								{
									Messagebox.show("Este Salão não tem esse serviço")
								}
							}else
							{
								Messagebox.show("Selecione um horário")
							}
						}else
						{
							Messagebox.show("Selecione um serviço")
						}
					}
				}
			}
		}
		)
	}
	
	@Listen("onDoubleClick = #listaSaloes")
	void alterar(Event e) {
		
		Reserva reserva = new Reserva()
		Salao salao = new Salao()
		Servico servico = new Servico()
		Horario horario = new Horario()
		//if (proprietario == null) proprietario = new Proprietario()
		reserva.id=1
		
		salao = e.target.selectedItem.value
		
		servico = lstServico.selectedItem.value
		horario = lstHorario.selectedItem.value

		reserva.situacao = 1
		//reserva.dataReserva="2013-11-03 00:00:00"
		reserva.preco=25
		
		reserva.salao = salao;
		reserva.addToServicos(servico)
		reserva.addToHorarios(horario)
		reserva.usuario=session.getAttribute("usuario")
		
		reserva.save(flush:true)
	}

	
	@Listen("onClick = #btnReservar")
	void reservar() {
		
		Reserva reserva = new Reserva()
		Salao salao = new Salao()
		Servico servico = new Servico()
		Horario horario = new Horario()
		//if (proprietario == null) proprietario = new Proprietario()
		reserva.id=1
		
		salao = Salao.get(1)
		
		servico = Servico.get(2)
		horario = Horario.get(1)

		reserva.situacao = 1
		reserva.dataReserva=new Date()
		reserva.preco=25
		
		reserva.salao = salao;
		reserva.AddToServicos(servico)
		reserva.AddToHorarios(horario)
		reserva.usuario=session.getAttribute("usuario")

		
		reserva.save(flush:true)
		
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
	
	void listarServicos() {
		lstServico.append {
			listhead(sizable:true){
				listheader(label: "Descricao")
				listheader(label: "Preço")
			}
			Servico.list().each{ servico ->
				listitem(value: servico) {
					listcell(label: servico.descricao)
					listcell(label: servico.preco)
				}
			}
		}
	}
	
	void listarHorarios() {
		lstHorario.append {
			listhead(sizable:true){
				listheader(label: "Hora de Inicio")
				listheader(label: "Hora de Inicio")
			}
			Horario.list().each{ horario ->
				listitem(value: horario) {
					listcell(label: horario.horaInicio)
					listcell(label: horario.horaFim)
				}
			}
		}
	}

}
