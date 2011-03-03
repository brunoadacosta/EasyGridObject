EasyGridObject
==================

Como Usar
---------

class Objeto {
	private String nome;
	// getters
}

List<Objeto> lista = new ArrayList<Objeto>();

// Adicionado total de registros para paginação
long totalDeRegistros = 

EasyGridAjaxObject ajaxObject = new EasyGridAjaxObjectBuilder<Objeto>(lista, totalDeRegistros).setColumn("nome").create();

//serializar json usando Gson
String json = new Gson().toJson(ajaxObject);

Exemplo de como utilizar com objetos encadeadas
------------------------------------------------
https://github.com/brunoadacosta/EasyGridAjaxObject/blob/master/src/com/cocento/commons/ajax/easygrid/demonstration/Main.java


Direitos e Licença
------------------
Copyright (c) 2011 por Bruno Alvares da Costa. Lançado sob a licença MIT. Veja LICENSE.txt para mais detalhes.