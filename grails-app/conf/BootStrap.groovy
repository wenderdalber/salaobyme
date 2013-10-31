import salaobyme.Pessoa;
import salaobyme.Permissao;
import salaobyme.Usuario;
import salaobyme.Proprietario;
import salaobyme.UsuarioPermissao;

class BootStrap {

	def init = { servletContext ->
		
		def permissaoUsuario = Permissao.findByAuthority('ROLE_USER') ?: new Permissao(authority: 'ROLE_USER').save(failOnError: true)
		def permissaoAdmin = Permissao.findByAuthority('ROLE_ADMIN') ?: new Permissao(authority: 'ROLE_ADMIN').save(failOnError: true)
		
		def proprietario = new Proprietario(id: 1, nome: 'Jão', email: 'admin@gmail.com.br', telefone: '(12) 3100-2541', celular: '(12) 98412-2541', cpf: '410.120.144-84', tipo: '0')
		
		def usuarioAdmin = Usuario.findByUsername('admin') ?: new Usuario(
			username: 'admin',
			password: 'admin',
			enabled: true,
			proprietario: proprietario).save(flush:true, failOnError: true)
		
		if (!usuarioAdmin.authorities.contains(permissaoAdmin)) {
			UsuarioPermissao.create usuarioAdmin, permissaoAdmin
		}
		if (!usuarioAdmin.authorities.contains(permissaoUsuario)) {
			UsuarioPermissao.create usuarioAdmin, permissaoUsuario
		}
	}
	def destroy = {
	}
}
