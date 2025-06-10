/**
 * Confirmar a exclusão do produto
 * @param id
 */

function confirmar(id){
	let reposta = confirm("Confirmar a exclusão deste produto?")
	if(reposta === true){
		//alert(id)
		window.location.href = "delete?id=" + id
	}
}