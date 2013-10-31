package salaobyme

class Pessoa {

	String nome
	String email
	String telefone
	String celular
	int tipo
	Usuario usuario
	
	static belongsTo = [usuario:Usuario]
	
    static constraints = {
		nome blank: false
		email unique:true, email:true
		usuario nullable:true
    }
}
