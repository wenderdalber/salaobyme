package salaobyme

import salaobyme.Endereco;
import salaobyme.Proprietario;

class Salao {
	
	String nome
	String cnpj
	Endereco endereco
	Proprietario proprietario
	
	static belonsgTo = [Proprietario]
	static hasMany = [servicos:Servico]

	static constraints = {
		id unique:true
		nome nullable:false
		cnpj nullable:false
		endereco nullable:true
	}
	static mapping = {
		id generator: "native"
	}
}
