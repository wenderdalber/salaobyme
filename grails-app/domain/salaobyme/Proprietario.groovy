package salaobyme

import salaobyme.Salao;

class Proprietario extends Pessoa {
	
	
	String cpf
	
	static hasMany = [saloes:Salao]
	static belongsTo = [usuario:Usuario]

	static constraints = {
		id unique:true
		cpf nullable: true
	}
	static mapping = {
		id generator: "native"
	}
}
