package salaobyme

import java.util.Date;

class Reserva {
	
	double preco
	int situacao
	Date dataReserva
	Salao salao
	Usuario usuario
	
	static hasMany = [servicos: Servico, horarios: Horario]
	static belongsTo = [Salao, Usuario]
	
	static constraints = {
		id unique:true
		preco nullable:false
		dataReserva nullable:false
	}
	static mapping = {
		id generator: "native"
	}
}
