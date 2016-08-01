package com.camilo.tarefas.model;

import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;

import org.springframework.boot.json.JacksonJsonParser;

public class WebUtil {
	
	public static DetalhesErro converteMensagemErroJson(String mensagem) {
		
		JacksonJsonParser parser = new JacksonJsonParser();
		Map<String,Object> map = parser.parseMap(mensagem);
		
		DetalhesErro erro = new DetalhesErro();

		Iterator<Object> itr = map.values().iterator(); 
		
		//descrição
		if (itr.hasNext()) { 
			erro.setTitulo(itr.next().toString()); 
		}

		//código de retorno
		if (itr.hasNext()) { 
			erro.setStatus(Long.valueOf(itr.next().toString())); 
		}

		//data do ocorrido
		if (itr.hasNext()) { 
			String strData = itr.next().toString();
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(Long.valueOf(strData));
			erro.setTimestamp(calendar.getTime());
		}

		//mensagem do desenvolvedor
		if (itr.hasNext()) { 
			erro.setMensagemDesenvolvedor(itr.next().toString()); 
		}		
		
		return erro;
	}
}
