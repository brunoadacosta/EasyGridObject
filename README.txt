EasyGridAjaxObject

class Objeto {
	private String nome;
	// getters
}

List<Objeto> lista = new ArrayList<Objeto>();

EasyGridAjaxObject ajaxObject = new EasyGridAjaxObjectBuilder<Objeto>(lista).setColumn("nome").create();

//serializar json usando Gson
String json = new Gson().toJson(ajaxObject);

Exemplo de como utilizar com objetos encadeadas
https://github.com/brunoadacosta/EasyGridAjaxObject/blob/master/src/com/cocento/commons/ajax/easygrid/demonstration/Main.java