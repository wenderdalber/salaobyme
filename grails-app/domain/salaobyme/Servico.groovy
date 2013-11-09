package salaobyme

class Servico {
	
	int id
	String descricao
	int status
	double preco
	
	static hasMany = [saloes:Salao]
	static belongsTo = [Salao]

	static constraints = {
		id unique:true
		descricao nullable:false
		status nullable:false
	}
	static mapping = {
		id generator: "native"
	}
}
