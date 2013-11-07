package salaobyme

import org.zkoss.zk.grails.composer.*
import org.zkoss.zk.ui.*;
import org.zkoss.zk.ui.event.*
import org.zkoss.zk.ui.select.annotation.*
import org.zkoss.zul.*

import java.util.Properties;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

class EsqueceuSenhaComposer extends zk.grails.Composer {

	
	@Wire
	Textbox email
	
	@Wire
	Button btnEnviar
	
    def afterCompose = { window ->
        // initialize components here
    }
	
	public void EnviarEmail(String txtemail, String novaSenha){
		Properties props = new Properties();
		/** Parâmetros de conexão com servidor Gmail */
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props,
					new javax.mail.Authenticator() {
						 protected PasswordAuthentication getPasswordAuthentication()
						 {
							   return new PasswordAuthentication("wenderfatec@gmail.com", "yroehtESUOH11");
						 }
					});

		/** Ativa Debug para sessão */
		session.setDebug(true)

		try {

			  Message message = new MimeMessage(session);
			  message.setFrom(new InternetAddress("wenderfatec@gmail.com")); //Remetente

			  Address[] toUser = InternetAddress //Destinatário(s)
						 .parse(txtemail);

			  message.setRecipients(Message.RecipientType.TO, toUser);
			  message.setSubject("Recuperando minha senha - Salaoby.me");//Assunto
			  message.setText("Você solicitou a alteração de sua senha! Salaoby.me criou a seguinte senha automaticamente: " + novaSenha + " acesse o site e faça login com a nova senha!");
			  /**Método para enviar a mensagem criada*/
			  Transport.send(message);

		 } catch (MessagingException e) {
			  throw new RuntimeException(e);
	  }
  }
	
	@Listen("onClick = #btnEnviar")
	void enviar() {
		
		Usuario usuario = new Usuario()
		Proprietario proprietario = new Proprietario()
		
		proprietario = Proprietario.findByEmail(email.value)
		
		if(proprietario != null)
		{
			int id = proprietario.id
			usuario = Usuario.findById(id)
			
			Random ram = new Random()
			int novaSenha = ram.nextInt(100000) + 10
			
			Messagebox.show(novaSenha.toString())
			
			EnviarEmail(email.value, novaSenha.value.toString())
			usuario.password=novaSenha.value
			
			if(usuario.save(flush:true)){
				Messagebox.show("Nova senha enviada no e-mail "+ email.value)
			}
		}else
		{
			Messagebox.show("Usuário não encontrado!")
		}
	}
}
