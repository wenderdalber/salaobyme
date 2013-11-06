package salaobyme

import java.util.Date;

class Reserva {
	
	int situacao
	Date dataReserva
	Salao salao
	Usuario usuario
	
	static hasMany = [servicos: Servico, horarios: Horario]
	static belongsTo = [Salao, Usuario]
	
	static constraints = {
		id unique:true 
		dataReserva nullable:false
	}
	static mapping = {
		id generator: "native"
	}
}
