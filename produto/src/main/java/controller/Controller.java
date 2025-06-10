package controller;

import java.io.IOException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.DAO;
import model.JavaBeans;

// TODO: Auto-generated Javadoc
/**
 * The Class Controller.
 */
@WebServlet(urlPatterns = {"/main", "/insert", "/select", "/update", "/delete", "/report"})
public class Controller extends HttpServlet {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The dao. */
    DAO dao = new DAO();
    
    /** The produto. */
    JavaBeans produto = new JavaBeans();

    /**
     * Instantiates a new controller.
     */
    public Controller() {
        super();
    }


    /**
     * Do get.
     *
     * @param request the request
     * @param response the response
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        System.out.println("Ação: " + action);

        switch (action) {
            case "/main":
                listarProdutos(request, response);
                break;
            case "/insert":
                adicionarProduto(request, response);
                break;
            case "/select":
                selecionarProduto(request, response);
                break;
            case "/update":
                atualizarProduto(request, response);
                break;
            case "/delete":
                deletarProduto(request, response);
                break;
            case "/report":
                gerarRelatorio(request, response);
                break;
            default:
                response.sendRedirect("index.html");
                break;
        }
    }


    /**
     * Listar produtos.
     *
     * @param request the request
     * @param response the response
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    protected void listarProdutos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<JavaBeans> lista = dao.listarProdutos();
        request.setAttribute("produtos", lista);
        RequestDispatcher rd = request.getRequestDispatcher("cadastro.jsp");
        rd.forward(request, response);
    }

    /**
     * Adicionar produto.
     *
     * @param request the request
     * @param response the response
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    protected void adicionarProduto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int qtde = Integer.parseInt(request.getParameter("qtde"));
            String valorUnitStr = request.getParameter("valorunit").replace(",", ".");
            float valorUnit = Float.parseFloat(valorUnitStr);
            LocalDate data = LocalDate.parse(request.getParameter("data_cad"));

            produto.setNome(request.getParameter("nome_produto"));
            produto.setQtde(qtde);
            produto.setValor_unit(valorUnit);
            produto.setData_cad(data);

            dao.inserirProduto(produto);

            response.sendRedirect("main");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("index.html");
        }
    }

  
    /**
     * Selecionar produto.
     *
     * @param request the request
     * @param response the response
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    protected void selecionarProduto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        produto.setId(id);
        dao.selecionarProduto(produto);

        NumberFormat formato = NumberFormat.getCurrencyInstance( Locale.of ("pt", "BR"));
        String valorFormatado = formato.format(produto.getValor_unit());

        request.setAttribute("id", produto.getId());
        request.setAttribute("nome", produto.getNome());
        request.setAttribute("qtde", produto.getQtde());
        request.setAttribute("valorunit", valorFormatado);
        request.setAttribute("data_cadastro", produto.getData_cad());

        RequestDispatcher rd = request.getRequestDispatcher("Editar.jsp");
        rd.forward(request, response);
    }

    /**
     * Atualizar produto.
     *
     * @param request the request
     * @param response the response
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    protected void atualizarProduto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String nome = request.getParameter("nome_produto");
            int qtde = Integer.parseInt(request.getParameter("qtde"));

            String valorTexto = request.getParameter("valorunit").replace("R$", "").replace(",", ".").trim();
            float valorUnit = Float.parseFloat(valorTexto);

            LocalDate data = LocalDate.parse(request.getParameter("data_cad"));

            produto.setId(id);
            produto.setNome(nome);
            produto.setQtde(qtde);
            produto.setValor_unit(valorUnit);
            produto.setData_cad(data);

            dao.alterarProduto(produto);

            response.sendRedirect("main");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("index.html");
        }
    }

    /**
     * Deletar produto.
     *
     * @param request the request
     * @param response the response
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    protected void deletarProduto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        produto.setId(id);
        dao.deletarProduto(produto);
        response.sendRedirect("main");
    }

    /**
     * Gerar relatorio.
     *
     * @param request the request
     * @param response the response
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    protected void gerarRelatorio(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Document documento = new Document();

        try {
            response.setContentType("application/pdf");
            response.addHeader("Content-Disposition", "inline; filename=produtos.pdf");

            PdfWriter.getInstance(documento, response.getOutputStream());

            documento.open();
            documento.add(new Paragraph("Lista de Produtos"));
            documento.add(new Paragraph(" "));

            PdfPTable tabela = new PdfPTable(4);
            tabela.addCell(new PdfPCell(new Paragraph("Nome")));
            tabela.addCell(new PdfPCell(new Paragraph("Quantidade")));
            tabela.addCell(new PdfPCell(new Paragraph("Valor Unitário")));
            tabela.addCell(new PdfPCell(new Paragraph("Data de Cadastro")));

            DateTimeFormatter formatData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            NumberFormat formatoMoeda = NumberFormat.getCurrencyInstance(Locale.of("pt", "BR"));

            ArrayList<JavaBeans> lista = dao.listarProdutos();
            for (JavaBeans p : lista) {
                tabela.addCell(p.getNome());
                tabela.addCell(String.valueOf(p.getQtde()));
                tabela.addCell(formatoMoeda.format(p.getValor_unit()));
                tabela.addCell(p.getData_cad().format(formatData));
            }

            documento.add(tabela);
            documento.close();
        } catch (Exception e) {
            e.printStackTrace();
            documento.close();
        }
    }
}
