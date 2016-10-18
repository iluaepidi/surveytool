/*(function($){
	
	$(document).ready(function(){
		$('#question-list').on('click', '>li', function(){
			if($(this).hasClass('inactive')){
				$(this).removeClass('inactive').siblings().addClass('inactive').find('.navigation').remove();				
				var act = $(this).index() + 1;
				var total = $(this).parent().children().length;
				if(act != total){
					$(this).append(getDown());
				}
				if(act != 1){
					$(this).prepend(getUp());
				}
			}
		});
		
		$('#question-list').on('focus', ':focusable', function(){
			var $li = $(this).parents('li.question');
			if($li.hasClass('inactive')){
				$li.removeClass('inactive').siblings().addClass('inactive').find('.navigation').remove();
				var act = $li.index() + 1;
				var total = $li.parent().children().length;
				if(act != total){
					$li.append(getDown());
				}
				if(act != 1){
					$li.prepend(getUp());
				}
			}
		});
	});
	
	$(window).load(function(){
		$('#question-list > li:first-child').append(getDown()).siblings().addClass('inactive');
	});
	
	function getUp(){
		var $up = $('<button>').text('Previous question').addClass('navigation up').attr({'aria-hidden': 'true', 'tabIndex': '-1'}).on('click', function(){
			$(this).parent().prev().click();
		});;
		return $up;
	}
	function getDown(){
		var $down = $('<button>').text('Next question').addClass('navigation down').attr({'aria-hidden': 'true', 'tabIndex': '-1'}).on('click', function(){
			$(this).parent().next().click();
		});
		return $down;
	}
	
})(jQuery);*/