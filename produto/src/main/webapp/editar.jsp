<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
<!DOCTYPE html>
<html lang="pt-br">
 
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="keywords" content="">
    <link rel="shortcut icon" href="img/favicon.png" type="icon">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
    <link rel="stylesheet" href="style.css">
    <title>Cadastro de Produtos</title>
</head>
 
<body class="container py-5">
    <div class="container d-flex justify-content-center align-items-center">
            <div>
                <form id="form_produto" name="frmProduto" action="update" >
                	<h1 class="mb-4 text-center">Editar Produto</h1>
                	<div class="form-group">
                        <label>ID</label>
                        <input type="text" class="form-control" name="id" readonly value="<%out.print(request.getAttribute("id"));%>">
                    </div>
                    <div class="form-group">
                        <label for="nomeproduto">Nome do Produto</label>
                        <input type="text" class="form-control" name="nome_produto" id="nomeproduto" placeholder="Digite o nome do produto" required aria-required="true" value="<%out.print(request.getAttribute("nome"));%>">
                    </div>
                    <div class="form-group">
                        <label for="qtde">Quantidade</label>
                        <input type="number" class="form-control" name="qtde" id="qtde" placeholder="Quantidade" min="1" required aria-required="true" value="<%out.print(request.getAttribute("qtde"));%>">
                    </div>
                    <div class="form-group">
                        <label for="valor_unit">Valor Unitario</label>
                        <input type="text" class="form-control" id="valor_unit" name="valorunit" placeholder="Ex: 12,99"
                            required pattern="^\d+(,\d{1,2})?$" title="Digite o valor com virgula, exemplo: 12,99" aria-required="true" value="<%out.print(request.getAttribute("valorunit"));%>">
                    </div>
                    <div class="form-group">
                        <label for="data">Data de Cadastro</label>
                        <input type="date" class="form-control" id="data" name="data_cad" required aria-required="true" value="<%out.print(request.getAttribute("data_cadastro"));%>">
                    </div>
                    <button type="submit" class="btn btn-primary btn-block">Salvar</button>
                </form>
            </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
 
</html>
 
 