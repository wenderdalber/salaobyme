package salaobyme

class Servico {
	
	String descricao
	int status
	
	static hasMany = [saloes:Salao]
	static belongsTo = [Salao]

	static constraints = {
		id unique:true
		descricao nullable:false, unique:true
		status nullable:false
	}
	static mapping = {
		id generator: "native"
	}
}
