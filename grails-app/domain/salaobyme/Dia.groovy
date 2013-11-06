package salaobyme

class Dia {
	
	String dia
	Salao salao
	
	static hasMany = [horarios:Horario]
	static belongsTo = [Salao]

	static constraints = {
		id unique:true
		salao nullable:true
	}
	static mapping = {
		id generator: "native"
	}
}
