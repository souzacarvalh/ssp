/**
 * ############## SSP Functions Javascript ##############
 * # Autor: Felipe F. de Souza Carvalho                 #
 * # Data: 16/08/2014                                   #
 * # Versão: 1.0                                        #
 * ######################################################
 */

//Esconde automaticamente as messages do JSF/Prime Faces (p:messages)
$(document).ajaxStop(function(){
	setTimeout(function(){
		$(".ui-messages").slideUp();
	}, 5000);
});
