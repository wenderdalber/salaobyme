// Place your Spring DSL code here
import salaobyme.AutenticarProvider

beans = {
	autenticacao(AutenticarProvider) {
		springSecurityService = ref("springSecurityService")
	}
}
