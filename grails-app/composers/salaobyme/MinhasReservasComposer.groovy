package salaobyme

import java.util.ArrayList;

import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.grails.composer.*
import org.zkoss.zk.ui.*;
import org.zkoss.zk.ui.event.*
import org.zkoss.zk.ui.select.annotation.*
import org.zkoss.zul.*

class MinhasReservasComposer extends zk.grails.Composer {

	@Wire
	Listbox listaReservas
	
    def afterCompose = { window ->
        // initialize components here
		listarReserva()
    }
	
	@Listen("onClick = #btnCancelarTodos")
	void cancelarTodos()
	{
		if(listaReservas.selectedItem.value != null)
		{
			Reserva reserva = new Reserva()
			ArrayList<Reserva> reservas = new ArrayList<Reserva>()
			
			reservas = listaReservas.selectedItems.value
			
			//Messagebox.show(reservas.id.value.toString())
			
			reserva.(reservas)
			reserva.situacao=1
			//reserva.situacao=1
			
			if(reserva.save(flush:true))
			{
				Messagebox.show("Todas as reservas foram canceladas!")
			}
		}else
		{
			Messagebox.show("Selecione uma reserva!")
		}
	}
	
	void listarReserva() {
		listaReservas.items.clear()
		
		listaReservas.append {
			
			if (listaReservas.heads.size() == 0) {
				listhead(sizable:true){
					listheader(label: "Salão")
					listheader(label: "Data da Reserva")
					listheader(label: "Preço")
					listheader(label: "Situação")
					listheader(label: "")
				}
			}
			
			int id = session.getAttribute("usuario").id
			Usuario usuario = session.getAttribute("usuario")
			//Pessoa pessoa = new Pessoa()
			Reserva.get(id).list().each{ reserva ->
				listitem(value: reserva) { item ->
					listcell(label: reserva.salao.nome)
					listcell(label: reserva.dataReserva.format("dd/MM/yyyy"))
					listcell(label: reserva.servicos.preco)
					if(reserva.situacao == 0){
						listcell(label: "Ativa")
					}else{
					listcell(label: "Cancelada")
					}
					listcell(label: ""){
						if(reserva.situacao == 0){
							hlayout{
								button(label: 'Cancelar', class:'btn btn-danger' , onClick: {
									e-> this.cancelar(item);
									} )
							}
						}else{
						hlayout{
							button(label: 'Ativar', class:'btn btn-success' , onClick: {
								e-> this.ativar(item);
								} )
						}
						}
						
					}
				}
			}
		}
	}
	
	void cancelar(Listitem item) {
		Messagebox.show("Confirma cancelamento da reserva?", "Confirma��o", Messagebox.OK | Messagebox.IGNORE  | Messagebox.CANCEL, Messagebox.QUESTION,
			new EventListener() {
				void onEvent(Event evt) throws InterruptedException {
					if (evt.getName().equals("onOK")) {
						Reserva reserva = new Reserva()
						
						reserva = Reserva.get(item.value.id)
						
						reserva.situacao=1
						
						reserva.save(flush:true)
						listarReserva()
						item.detach()
					}
				}
			}
		)
	}
	
	void ativar(Listitem item) {
		Messagebox.show("Confirma o ativamento da reserva?", "Confirma��o", Messagebox.OK | Messagebox.IGNORE  | Messagebox.CANCEL, Messagebox.QUESTION,
			new EventListener() {
				void onEvent(Event evt) throws InterruptedException {
					if (evt.getName().equals("onOK")) {
						Reserva reserva = new Reserva()
						
						reserva = Reserva.get(item.value.id)
						
						reserva.situacao=0
						
						reserva.save(flush:true)
						listarReserva()
						item.detach()
					}
				}
			}
		)
	}
}
