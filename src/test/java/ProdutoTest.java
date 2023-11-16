import dao.ClienteDAO;
import dao.IClienteDAO;
import dao.IProdutoDAO;
import dao.ProdutoDAO;
import domain.Cliente;
import domain.Produto;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

public class ProdutoTest {

    @Test
    public void cadastrarTest() throws Exception {
        IProdutoDAO dao = new ProdutoDAO();

        Produto produto = new Produto();
        produto.setNome("Product Name");
        produto.setPreco(20.0);

        Integer qtd = dao.cadastrar(produto);
        assertTrue(qtd == 1);

        Produto produtoBD = dao.consultar(produto.getNome());
        assertNotNull(produtoBD);
        assertNotNull(produtoBD.getId());
        assertEquals(produto.getNome(), produtoBD.getNome());
        assertEquals(produto.getPreco(), produtoBD.getPreco());

        Integer qtdDel = dao.excluir(produtoBD);
        assertNotNull(qtdDel);

    }
    @Test
    public void buscarTodosTest() throws Exception {
        IProdutoDAO dao = new ProdutoDAO();


        Produto produto1 = new Produto();
        produto1.setNome("Arroz");
        produto1.setPreco(5.90);
        produto1.setId(Long.valueOf(1));
        dao.cadastrar(produto1);

        Produto produto2 = new Produto();
        produto2.setNome("feijao");
        produto2.setPreco(6.90);
        produto2.setId(Long.valueOf(2));
        dao.cadastrar(produto2);

        try {

            List<Produto> todosProdutos = dao.buscarTodos();


            assertFalse("A lista de clientes está vazia", todosProdutos.isEmpty());


            assertTrue("Produto1 não encontrado na lista", containsClienteWithCodigo(todosProdutos, produto1.getNome()));
            assertTrue("Produto2 não encontrado na lista", containsClienteWithCodigo(todosProdutos, produto2.getNome()));

        } finally {

            dao.excluir(produto1);
            dao.excluir(produto2);
        }
    }

    private boolean containsClienteWithCodigo(List<Produto> todosProdutos, String nome) {
        for (Produto produto : todosProdutos) {
            if (produto.getNome().equals(nome)) {
                return true;
            }
        }
        return false;
    }

    @Test
    public void alterarTest() throws Exception {
        IProdutoDAO dao = new ProdutoDAO();


        Produto produto = new Produto();
        produto.setNome("Feijao");
        produto.setPreco(11.50);
        produto.setId(Long.valueOf(05));
        dao.cadastrar(produto);

        try {
            produto.setNome("FAVA Verde");
            Integer qtd = dao.alterar(produto);
            assertEquals(1, qtd.intValue());


            Produto produtoAlterado = dao.consultar(produto.getNome());
            assertNotNull(produtoAlterado);
            assertEquals(produto.getNome(), produtoAlterado.getNome());
            assertEquals(produto.getPreco(), produtoAlterado.getPreco());

        } finally {

            dao.excluir(produto);
        }
    }

}

