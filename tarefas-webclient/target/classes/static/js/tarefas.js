$(function() {
	$('[rel = "tooltip"]').tooltip();
	
	$('.js-finaliza').on('click', function(event) {
		event.preventDefault();
		
		var botao = $(event.currentTarget);
		var urlOperacao = botao.attr('href');
		
		var response = $.ajax({
			url: urlOperacao,
			type: 'PUT'
		});
		
		response.done(function(event) {
			var idTarefa = botao.data('id');
			$('[data-role=' + idTarefa + ']').html(
					'<span class="label cr-label label-success">concluída</span>');
			$('[data-finaliza=' + idTarefa + ']').html(event);
						
			//só mostra os que podem ser usados
			$('#bt-edita_' + idTarefa).addClass('hidden');
			$('#bt-inicia_' + idTarefa).addClass('hidden');
			$('#bt-para_' + idTarefa).addClass('hidden');
			$('#bt-finaliza_' + idTarefa).addClass('hidden');
			$('#bt-cancela_' + idTarefa).addClass('hidden');

		});		
	});
	
	$('.js-inicia').on('click', function(event) {
		event.preventDefault();
		
		var botao = $(event.currentTarget);
		var urlOperacao = botao.attr('href');
		
		var response = $.ajax({
			url: urlOperacao,
			type: 'PUT'
		});
		
		response.done(function(event) {
			var idTarefa = botao.data('id');
			$('[data-role=' + idTarefa + ']').html('<span class="label cr-label label-warning">' + event + '</span>');
			
			//só mostra os que podem ser usados
			$('#bt-edita_' + idTarefa).removeClass('hidden');
			$('#bt-inicia_' + idTarefa).addClass('hidden');
			$('#bt-para_' + idTarefa).removeClass('hidden');
			$('#bt-finaliza_' + idTarefa).removeClass('hidden');
			$('#bt-cancela_' + idTarefa).removeClass('hidden');
		});		
	});	
	
	$('.js-para').on('click', function(event) {
		event.preventDefault();

		var botao = $(event.currentTarget);
		var urlOperacao = botao.attr('href');
		
		var response = $.ajax({
			url: urlOperacao,
			type: 'PUT'
		});
		
		response.done(function(event) {
			var idTarefa = botao.data('id');
			$('[data-role=' + idTarefa + ']').html(
					'<span class="label cr-label label-primary">' + event + '</span>');
			
			//só mostra os que podem ser usados
			$('#bt-edita_' + idTarefa).removeClass('hidden');
			$('#bt-inicia_' + idTarefa).removeClass('hidden');
			$('#bt-para_' + idTarefa).addClass('hidden');
			$('#bt-finaliza_' + idTarefa).addClass('hidden');
			$('#bt-cancela_' + idTarefa).removeClass('hidden');
		});	
	});	

	$('.js-cancela').on('click', function(event) {
		event.preventDefault();

		var botao = $(event.currentTarget);
		var urlOperacao = botao.attr('href');
		
		var response = $.ajax({
			url: urlOperacao,
			type: 'PUT'
		});
		
		response.done(function(event) {
			var idTarefa = botao.data('id');
			$('[data-role=' + idTarefa + ']').html(
					'<span class="label cr-label label-danger">' + event + '</span>');
			
			//só mostra os que podem ser usados
			$('#bt-edita_' + idTarefa).addClass('hidden');
			$('#bt-inicia_' + idTarefa).addClass('hidden');
			$('#bt-para_' + idTarefa).addClass('hidden');
			$('#bt-finaliza_' + idTarefa).addClass('hidden');
			$('#bt-cancela_' + idTarefa).addClass('hidden');
		});	
	});	
});	


$('#confirmacaoModal').on('show.bs.modal', function(event) {
	var button = $(event.relatedTarget);
	var id = button.data('id');
	var nome = button.data('descricao');
	var modal = $(this);
	var form = modal.find('form');
	var action = form.data('url-base');
	
	action = (!action.endsWith('/')) ? (action + '/') : action;
	
	form.attr('action', action + id);
	modal.find('.modal-body span').html('Tem certeza que deseja excluir a tarefa <strong>' + nome + '</strong> ?');
});