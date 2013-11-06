package salaobyme

class Dia {
	
	String dia
	
	static hasMany = [horarios:Horario]
	static belongsTo = [Salao]

	static constraints = {
		id unique:true
	}
	static mapping = {
		id generator: "native"
	}
}
