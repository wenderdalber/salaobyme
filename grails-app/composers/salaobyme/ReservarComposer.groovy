package salaobyme

import javax.swing.ListCellRenderer;

import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.grails.composer.*
import org.zkoss.zk.ui.*;
import org.zkoss.zk.ui.event.*
import org.zkoss.zk.ui.select.annotation.*
import org.zkoss.zul.*

import salaobyme.Salao;

class ReservarComposer extends zk.grails.Composer {

	@Wire
	Listbox listaSaloes

	@Wire
	Datebox dataReserva
	
	@Wire
	Textbox telefone

	@Wire
	Label lblNome, lblNomeSalao, lblServico, lblErro

	@Wire
	Listbox lstServico, lstHorario

	@Wire
	Div divServico, divHorario

	Salao salao
	Servico servico
	Dia dia
	Horario horario
	
	Salao salaoGlobal
	Servico servicoGlobal
	Horario horarioGlobal
	
	
	def afterCompose = { window ->
		// initialize components here
		listarSaloes()
		listarServicos()
		listarHorarios()
	}
	
	void reservar(Listitem item) {
		Messagebox.show("Confirmar a Reserva?", "ConfirmaÃ§Ã£o", Messagebox.OK | Messagebox.IGNORE  | Messagebox.CANCEL, Messagebox.QUESTION,
				new EventListener() {
					void onEvent(Event evt) throws InterruptedException {
						if (evt.getName().equals("onOK")) {

							Reserva reserva = new Reserva()
							Salao salao = new Salao()
							Servico servicos = new Servico()
							Horario horarios = new Horario()
							
							//ArrayList<Servico> servicos = new ArrayList<Servico>()
							//ArrayList<Horario> horarios = new ArrayList<Horario>()
							
							reserva.situacao=1
							reserva.dataReserva=new Date()

							salao = Salao.get(item.value.id)
							
							//Messagebox.show(salao.nome.value.toString())
							
							//servicos = Servico.get(lstServico.selectedItem)
							//horarios = Horario.get(lstHorario.selectedItem)
							
							
							
							Messagebox.show(lstServico.getSelection().value.toString())
							Messagebox.show(lstHorario.selectedIndex.value.toString())

							if(lstServico.selectedItem.value != null) {
								if(lstHorario.selectedItem.value != null) {
									if(salao.servicos.size() > 0) {

										reserva.salao = salao
										
										reserva.add(servicos)
										reserva.add(horarios)
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

	@Listen("onClick = #listaSaloes")
	void servicos() {
		divServico.visible=true
		salao = (Salao) Salao.findById(listaSaloes.getSelectedItem().label.toString())
		listarServicos()
	}

	@Listen("onClick = #lstServico")
	void horarios() {
		divHorario.visible=true
		servicoGlobal = (Servico) Servico.findById(lstServico.getSelectedItem().label.toString())
		
		//alert(lstServico.getIndex().properties)
		
		listarHorarios()
	}
	
	@Listen("onClick = #lstHorario")
	void pegarHorario() {
		horarioGlobal = (Horario) Horario.findById(lstHorario.getSelectedItem().label.toString())
	}

	@Listen("onClick = #btnReservar")
	void reservar() {
		
		Reserva reserva = new Reserva()
		Salao salao = new Salao()
		Servico servicos = new Servico()
		Horario horarios = new Horario()
		
		//ArrayList<Servico> servicos = new ArrayList<Servico>()
		//ArrayList<Horario> horarios = new ArrayList<Horario>()
		
		reserva.situacao=0

		salao = Salao.get(listaSaloes.selectedItem.value.id)
		
		//Messagebox.show(salao.nome.value.toString())
		
		reserva.dataReserva = dataReserva.value
		
		//alert(dataReserva._weekOfYear.toString())
		
		servicos = Servico.get(servicoGlobal.id)
		horarios = Horario.get(horarioGlobal.id)
		
		
			
		//Messagebox.show(servicoGlobal.id.toString())
		//Messagebox.show(horarioGlobal.id.toString())

		if(lstServico.selectedItem.value != null) {
			if(lstHorario.selectedItem.value != null) {
				if(salao.servicos.size() > 0) {

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
				}
			}
		}
	}

	void listarServicos() {
		if(salao!=null){
			lstServico.items.clear()
			lstServico.append {
				salao.servicos.each{ servico ->
					listitem(value: salao.servicos) {
						listcell(label: servico.id)
						listcell(label: servico.descricao)
						listcell(label: servico.preco)
					}
				}
			}
		}
	}

	void listarHorarios() {
		if(salao!=null){
			Horario horario = new Horario()
			horario.dias = salao.refresh().diasAbertos
			//horario.dias.each {dia -> dia.horarios.each { hora -> alert(hora.hora)} }
			lstHorario.items.clear()
			lstHorario.append {

				horario.dias.each { dia ->
					dia.horarios.each{ hora ->
						if (hora.status!="OCUPADO")
						{
							listitem(value: dia.horarios){
								listcell(label: dia.id)
								listcell(label: dia.dia)								
								listcell(label: hora.hora)
								listcell(label: hora.id)
							}
						}
					}
				}
			}
		}
	}
}