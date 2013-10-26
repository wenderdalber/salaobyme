import salaobyme.Permissao;
import salaobyme.Usuario;
import salaobyme.UsuarioPermissao;

class BootStrap {

    def init = { servletContext ->
		
		/*def permissaoUsuario = Permissao.findByAuthority('ROLE_USER') ?: new Permissao(authority: 'ROLE_USER').save(failOnError: true)
		def permissaoAdmin = Permissao.findByAuthority('ROLE_ADMIN') ?: new Permissao(authority: 'ROLE_ADMIN').save(failOnError: true)
		
		def usuario = new Usuario(id: 1, nome: 'Jão', cargo: 'Bombril').save(flush:true)
		
		def usuarioAdmin = Usuario.findByUsername('admin') ?: new Usuario(
			username: 'admin',
			password: 'admin',
			enabled: true,
			usuario: usuario).save(failOnError: true)
		
		if (!usuarioAdmin.authorities.contains(permissaoAdmin)) {
			UsuarioPermissao.create usuarioAdmin, permissaoAdmin
		}
		if (!usuarioAdmin.authorities.contains(permissaoUsuario)) {
			UsuarioPermissao.create usuarioAdmin, permissaoUsuario
		}*/
    }
    def destroy = {
    }
}
