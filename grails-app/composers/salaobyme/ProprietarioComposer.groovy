package salaobyme
import org.zkoss.zk.grails.composer.*
import org.zkoss.zk.ui.*;
import org.zkoss.zk.ui.event.*
import org.zkoss.zk.ui.select.annotation.*
import org.zkoss.zul.*
class ProprietarioComposer extends zk.grails.Composer {
	
	@Wire
	Intbox id
	@Wire
	Textbox nome
	@Wire
	Textbox cpf
	@Wire
	Textbox email
	@Wire
	Textbox telefone
	@Wire
	Textbox celular
	@Wire
	Textbox username
	@Wire
	Textbox password
	@Wire
	Label lblErro
	
	@Wire
	Panel panelOk
	@Wire
	Panel panel
	
	Proprietario proprietario
	
	@Listen("onClick = #btnCancelar")
	void limpar() {
		id.value=0
		nome.value=""
		cpf.value=""
		email.value=""
		telefone.value=""
		celular.value=""
		lblErro.value=""
	}
	
	
	@Listen("onClick = #btnSalvar")
	void salvar() {
		Usuario usuario = new Usuario()
		Proprietario proprietario = new Proprietario ()
		//if (proprietario == null) proprietario = new Proprietario()
		proprietario.id=id.value
		proprietario?.nome=nome.value
		proprietario?.cpf=cpf.value
		proprietario?.email=email.value
		proprietario?.telefone=telefone.value
		proprietario?.celular=celular.value
		proprietario?.tipo = 0
		
		usuario.username = username.value
		usuario.password = password.value
		//proprietario.usuario = usuario
		usuario.proprietario = proprietario
		
		def permissaoAdmin = Permissao.findByAuthority('ROLE_ADMIN') ?: new Permissao(authority: 'ROLE_ADMIN').save(failOnError: true)
		
			if (/*!proprietario.hasErrors() &&*/ !usuario.hasErrors())
			{
				//proprietario.save(flush:true)
				if(usuario.save(flush:true))
				{
					if (!usuario.authorities.contains(permissaoAdmin)) {
						UsuarioPermissao.create usuario, permissaoAdmin
					}
					Messagebox.show("Cadastro de Proprietario efetuado com sucesso! Realize login no sistema!")
					lblErro.value = ""
				}
				else
				{
					String x=""
					proprietario.errors.allErrors.each{
						x+=""+message(error:it)
					}
					lblErro.value=x
				}
			}
	}
	
	
	def afterCompose = { window ->
		// initialize components here
		
	}
}