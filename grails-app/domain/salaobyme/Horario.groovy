package salaobyme

class Horario {
	
	int horaInicio
	int horaFim
	
	static hasMany = [dias:Dia]
	static belongsTo = [Dia]

	static constraints = {
		id unique:true
		horaInicio nullable:false
		horaFim nullable:false
	}
	static mapping = {
		id generator: "native"
	}

	}
