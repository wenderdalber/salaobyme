package salaobyme

import salaobyme.Salao;

class Endereco {
	
	String logradouro
	long numero
	String bairro
	String cidade
	String estado
	
	static belongsTo = [salao:Salao]

	static constraints = {
		id unique:true

		logradouro nullable:false
		bairro nullable:false
		cidade nullable:false
		estado nullable:false

	}
	static mapping = {
		id generator: "native"
	}
}
