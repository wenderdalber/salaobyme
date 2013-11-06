package salaobyme

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
	
	void listarReserva() {
		listaReservas.items.clear()
		
		listaReservas.append {
			
			if (listaReservas.heads.size() == 0) {
				listhead(sizable:true){
					listheader(label: "Salão")
					listheader(label: "Data da Reserva")
					listheader(label: "Situação")
					listheader(label: "Preço")
					listheader(label: "")
				}
			}
			
			int id = session.getAttribute("usuario").id
			Usuario usuario = session.getAttribute("usuario")
			//Pessoa pessoa = new Pessoa()
			Reserva.findByUsuario(usuario).each{ reserva ->
				listitem(value: reserva) { item ->
					listcell(label: reserva.salao.nome)
					listcell(label: reserva.dataReserva.format("dd/MM/yyyy"))
					listcell(label: reserva.situacao)
					listcell(label: reserva.preco)
					listcell(label: ""){
						hlayout{
							toolbarbutton(label: 'Cancelar', class:'btn btn-danger' , onClick: {
								e-> this.cancelar(item);
								} )
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
						Reserva reserva=item.value
						reserva.situacao=0
						reserva.save(flush:true)
						item.detach()
					}
				}
			}
		)
	}
}
