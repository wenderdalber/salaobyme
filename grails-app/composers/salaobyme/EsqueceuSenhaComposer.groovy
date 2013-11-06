package salaobyme

import org.zkoss.zk.grails.composer.*
import org.zkoss.zk.ui.*;
import org.zkoss.zk.ui.event.*
import org.zkoss.zk.ui.select.annotation.*
import org.zkoss.zul.*

class EsqueceuSenhaComposer extends zk.grails.Composer {

	
	@Wire
	Textbox email
	
    def afterCompose = { window ->
        // initialize components here
    }
	
	@Listen("onClick = #btnSalvar")
	void enviar() {
	
	}
}
