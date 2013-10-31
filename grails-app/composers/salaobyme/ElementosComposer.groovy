package salaobyme

import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.grails.composer.*
import org.zkoss.zk.ui.*;
import org.zkoss.zk.ui.event.*
import org.zkoss.zk.ui.select.annotation.*
import org.zkoss.zul.*
import org.zkoss.zul.Label;


class ElementosComposer extends zk.grails.Composer {

	@Wire
	Div logar, deslogar
	
	@Wire
	Label lblNomeUsuario
	
    def afterCompose = { window ->
        // initialize components here
		logados()
    }
	
	void logados(){
		
		if(session.getAttribute("pessoa")==null){
			logar.visible=true
			deslogar.visible=false
			lblNomeUsuario.value="Oie"
		}else{
			logar.visible=false
			deslogar.visible=true
			//lblNomeUsuario.value="Olá , "+session.getAttribute("pessoa").nome
			lblNomeUsuario.value="Oie"
		}
	}
}
