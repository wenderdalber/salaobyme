package salaobyme

class Horario {
	
	String hora
	String status
	
	
	static hasMany = [dias:Dia]
	static belongsTo = [Dia]

	static constraints = {
		id unique:true
		hora nullable:false
		status nullable:true
	}
	static mapping = {
		id generator: "native"
	}

	}