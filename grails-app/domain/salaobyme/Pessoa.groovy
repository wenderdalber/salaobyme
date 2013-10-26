package salaobyme

class Pessoa {

	String nome
	String email
	String telefone
	String celular
	int tipo
	
    static constraints = {
		nome blank: false
		email unique:true, email:true
    }
}
