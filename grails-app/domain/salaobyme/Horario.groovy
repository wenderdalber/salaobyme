package salaobyme

class Horario {
	
	String hora
	
	static hasMany = [dias:Dia]
	static belongsTo = [Dia]

	static constraints = {
		id unique:true
		hora nullable:false
	}
	static mapping = {
		id generator: "native"
	}

	}
