package com.camilo.tarefas.services;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.camilo.tarefas.model.Tarefa;
import com.camilo.tarefas.model.WebUtil;

@Service
public class TarefasService {
		
	private RestTemplate restTemplate;
	
	private String URI_BASE;
	private String URN_BASE = "/tarefas";
	
	public TarefasService() {
		init("http://localhost:8090", "", "");
	}
	
	public void init(String url, String usuario, String senha) {
		restTemplate = new RestTemplate();
		URI_BASE = url.concat(URN_BASE);
	}
	
	public List<Tarefa> listar() {
		RequestEntity<Void> request = RequestEntity.get(URI.create(URI_BASE)).build();
		ResponseEntity<Tarefa[]> response = restTemplate.exchange(request, Tarefa[].class);
		return Arrays.asList(response.getBody());
	}

	public String salvar(Tarefa tarefa, RedirectAttributes attributes) {
		RequestEntity<Tarefa> request = RequestEntity.post(URI.create(URI_BASE)).body(tarefa);
		attributes.addFlashAttribute("operacao", "insert");

		try {
			ResponseEntity<Void> response = restTemplate.exchange(request, Void.class);
			attributes.addFlashAttribute("mensagemLocation", response.getHeaders().getLocation().toString());
		} catch (HttpClientErrorException e) { 
			attributes.addFlashAttribute("detalhesErro", WebUtil.converteMensagemErroJson(e.getResponseBodyAsString()));
		}
		
		return "redirect:/tarefas/cadastro";
	}

	public String atualizar(Tarefa tarefa, RedirectAttributes attributes) {
		String uri = URI_BASE.concat("/" + tarefa.getId());

		RequestEntity<Tarefa> request = RequestEntity.put(URI.create(uri)).body(tarefa);
		
		attributes.addFlashAttribute("tarefa", tarefa);
		attributes.addFlashAttribute("operacao", "update");
		
		try {
			restTemplate.exchange(request, Void.class);
			attributes.addFlashAttribute("mensagemSucesso", "Tarefa atualizada com sucesso!");
		} catch (HttpClientErrorException e) { 
			attributes.addFlashAttribute("detalhesErro", WebUtil.converteMensagemErroJson(e.getResponseBodyAsString()));
		}
		
		return "redirect:/tarefas/" + tarefa.getId();
	}
	
	public Tarefa buscar(Long id) {
		String uri = URI_BASE.concat("/" + id);
		
		RequestEntity<Void> request = RequestEntity.get(URI.create(uri)).build();
		ResponseEntity<Tarefa> response = restTemplate.exchange(request, Tarefa.class);
		
		return response.getBody();
	}

	public String finalizar(Long id) {
		String uri = URI_BASE.concat("/" + id + "/finalizacao");
		
		RequestEntity<Void> request = RequestEntity.put(URI.create(uri)).build();
		restTemplate.exchange(request, Void.class);
		
		//data da finalizacao
		return new SimpleDateFormat("dd/MM/yyy").format(new Date());
	}

	public void iniciar(Long id) {
		String uri = URI_BASE.concat("/" + id + "/abertura");
		
		RequestEntity<Void> request = RequestEntity.put(URI.create(uri)).build();
		restTemplate.exchange(request, Void.class);
	}

	public void parar(Long id) {
		String uri = URI_BASE.concat("/" + id + "/reset");
		
		RequestEntity<Void> request = RequestEntity.put(URI.create(uri)).build();
		restTemplate.exchange(request, Void.class);
	}

	public void cancelar(Long id) {
		String uri = URI_BASE.concat("/" + id + "/cancelamento");
		
		RequestEntity<Void> request = RequestEntity.put(URI.create(uri)).build();
		restTemplate.exchange(request, Void.class);
	}

	public void deletar(Long id) {
		String uri = URI_BASE.concat("/" + id);
		
		RequestEntity<Void> request = RequestEntity.delete(URI.create(uri)).build();
		restTemplate.exchange(request, Void.class);
	}
}
