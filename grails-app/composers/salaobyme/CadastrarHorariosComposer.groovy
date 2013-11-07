package salaobyme
import org.zkoss.zk.grails.composer.*
import org.zkoss.zk.ui.*;
import org.zkoss.zk.ui.event.*
import org.zkoss.zk.ui.select.annotation.*
import org.zkoss.zul.*
import com.sun.net.ssl.internal.ssl.Alerts;
class CadastrarHorariosComposer extends zk.grails.Composer {
	@Wire
	Listbox lstHorarios
	@Wire
	Combobox cbmDia
	@Wire
	Combobox cbmSalao
	@Wire
	Button btnAtualizarHorario
	
	Proprietario prop = new Proprietario()
	Dia dia = new Dia()
	Horario horario
	Salao salao 
	int idDoSalao
	ArrayList<Horario> horarios = new ArrayList<Horario>()
	
	@Listen("onClick = #btnAtualizarHorario")
	void AtualizarHorario(){
		lstHorarios.getSelectedItems().each { lista->
			horario = new Horario()
			horario.hora = lista.getLabel().toString()
			//alert(horario.hora)
			//horario.save(true)
			horarios.add(horario)
		}
		dia.dia = cbmDia.value
		dia.horarios = horarios
		dia.status = "ativo"
		
		
		salao = Salao.findByNome(cbmSalao.value)
		salao.diasAbertos.add(dia)
		dia.salao = salao
		if(dia.save(true)){
			alert("Horário Cadastrado com sucesso!")
		}else
		{
			alert("Ocorreu um problema no cadastro")
		}
		
		//salao.save(flush:true)
	}
	void listarSaloes()
	{
		ArrayList<Comboitem> saloes = new ArrayList<Comboitem>()
		
		prop = Proprietario.findById(session.getAttribute("usuario").proprietario.id)
		
		prop.saloes.each { salao ->
			cbmSalao.appendItem(salao.nome)	
		}
	}
	
	def afterCompose = {
		window ->
		// initialize components here
		listarSaloes()
	}
}