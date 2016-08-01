package com.camilo.tarefas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.camilo.tarefas.model.StatusTarefa;
import com.camilo.tarefas.model.Tarefa;
import com.camilo.tarefas.services.TarefasService;

@Controller
@RequestMapping("/tarefas")
public class TarefasController {
	
	@Autowired
	private TarefasService tarefasService;
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index() {
		return "index";
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("lista-tarefas");
		mv.addObject("tarefas", tarefasService.listar());
		return mv;
	}
	
	@RequestMapping(value = "/cadastro", method = RequestMethod.GET)
	public ModelAndView cadastrarForm(@ModelAttribute Tarefa tarefa) {
		ModelAndView mv = new ModelAndView("cadastro-tarefa");
		mv.addObject("operacao", "insert");
		return mv;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ModelAndView atualizarForm(@PathVariable Long id) {
		ModelAndView mv = new ModelAndView("cadastro-tarefa");
		mv.addObject("tarefa", tarefasService.buscar(id));
		mv.addObject("operacao", "update");
		return mv;
	}

	@RequestMapping(value = "/{id}/abertura", method = RequestMethod.PUT)
	public @ResponseBody String iniciar(@PathVariable Long id) {
		tarefasService.iniciar(id);
		return StatusTarefa.INICIADA.getDescricao();
	}

	@RequestMapping(value = "/{id}/reset", method = RequestMethod.PUT)
	public @ResponseBody String parar(@PathVariable Long id) {
		tarefasService.parar(id);
		return StatusTarefa.PENDENTE.getDescricao();
	}

	@RequestMapping(value = "/{id}/finalizacao", method = RequestMethod.PUT)
	public @ResponseBody String finalizar(@PathVariable Long id) {
		return tarefasService.finalizar(id);
	}

	@RequestMapping(value = "/{id}/cancelamento", method = RequestMethod.PUT)
	public @ResponseBody String cancelar(@PathVariable Long id) {
		tarefasService.cancelar(id);
		return StatusTarefa.CANCELADA.getDescricao();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public String deletar(@PathVariable Long id) {
		tarefasService.deletar(id);
		return "redirect:/tarefas";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String executarCadastro(Tarefa tarefa, RedirectAttributes attributes) {
		return tarefasService.salvar(tarefa, attributes);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public String executarAtualizacao(Tarefa tarefa, RedirectAttributes attributes) {
		return tarefasService.atualizar(tarefa, attributes);
	}
}
