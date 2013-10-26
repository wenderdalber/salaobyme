package salaobyme

class Dia {
	
	String dia
	
	static hasMany = [horarios:Horario]

	static constraints = {
		id unique:true
	}
	static mapping = {
		id generator: "native"
	}
}
