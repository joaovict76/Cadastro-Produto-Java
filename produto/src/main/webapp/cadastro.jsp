<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="model.JavaBeans"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Locale"%>
<%@ page import="java.time.format.DateTimeFormatter"%>
<%@ page import="java.text.NumberFormat"%>


<%
@ SuppressWarnings ("unchecked")
ArrayList<JavaBeans> lista = (ArrayList<JavaBeans>) request.getAttribute("produtos");
NumberFormat moeda = NumberFormat.getCurrencyInstance(Locale.of ("pt", "BR"));
 
%>



<!DOCTYPE html>
<html lang="pt-br">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="keywords" content="">
<link rel="shortcut icon" href="img/favicon.png" type="image/x-icon">
<link rel="stylesheet" href="style.css">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
<title>Cadastro de produtos!</title>
</head>

<body>
	<div class="container card text-center">
		<div class="card-body">
			<h1>Cadastro de Produtos</h1>
			<a href="new.html" class="btn btn-primary">Novo Produto</a> <a
				href="report" class="btn btn-danger">Relatório</a>
		</div>

		<table class="table table-bordered table-striped text-center">
			<thead class="bg-info text-white">
				<tr>
					<th scope="col">ID</th>
					<th scope="col">Nome Produto</th>
					<th scope="col">Quantidade</th>
					<th scope="col">Valor Unitario</th>
					<th scope="col">Data Cadastro</th>
					<th scope="col">Opções</th>
				</tr>
			</thead>
			<tbody>
				<%
				for (int i = 0; i < lista.size(); i++) {
				%>
				<tr>
					<td><%=lista.get(i).getId()%></td>
					<td class="text-left"><%=lista.get(i).getNome()%></td>
					<td><%=lista.get(i).getQtde()%></td>
					<td><%=moeda.format(lista.get(i).getValor_unit())%></td>
					<td><%=lista.get(i).getData_cad().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))%></td>

					<td><a href="select?id=<%=lista.get(i).getId()%>"
						class="btn btn-sm btn-primary">EDITAR</a> <a
						href="javascript:confirmar(<%=lista.get(i).getId()%>)"
						class="btn btn-sm btn-danger">EXCLUIR</a></td>
				</tr>
				<%
				}
				%>
			</tbody>
		</table>
	</div>
	<script src="confirmar.js"></script>
</body>

</html>


