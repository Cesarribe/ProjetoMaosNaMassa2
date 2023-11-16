import dao.ClienteDAO;
import dao.IClienteDAO;
import domain.Cliente;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ClienteTest {

    @Test
    public void cadastrarTest() throws Exception {
        IClienteDAO dao = new ClienteDAO();

        Cliente cliente = new Cliente();
        cliente.setCodigo("01");
        cliente.setNome("Rodrigo Pires");

        Integer qtd = dao.cadastrar(cliente);
        assertTrue(qtd == 1);

        Cliente clienteBD = dao.consultar(cliente.getCodigo());
        assertNotNull(clienteBD);
        assertNotNull(clienteBD.getId());
        assertEquals(cliente.getCodigo(), clienteBD.getCodigo());
        assertEquals(cliente.getNome(), clienteBD.getNome());

        Integer qtdDel = dao.excluir(clienteBD);
        assertNotNull(qtdDel);
    }

    @Test
    public void buscarTodosTest() throws Exception {
        IClienteDAO dao = new ClienteDAO();


        Cliente cliente1 = new Cliente();
        cliente1.setCodigo("03");
        cliente1.setNome("Maria Oliveira");
        dao.cadastrar(cliente1);

        Cliente cliente2 = new Cliente();
        cliente2.setCodigo("04");
        cliente2.setNome("Carlos Santos");
        dao.cadastrar(cliente2);

        try {

            List<Cliente> todosClientes = dao.buscarTodos();


            assertFalse("A lista de clientes está vazia", todosClientes.isEmpty());


            assertTrue("Cliente1 não encontrado na lista", containsClienteWithCodigo(todosClientes, cliente1.getCodigo()));
            assertTrue("Cliente2 não encontrado na lista", containsClienteWithCodigo(todosClientes, cliente2.getCodigo()));

        } finally {

            dao.excluir(cliente1);
            dao.excluir(cliente2);
        }
    }


    @Test
    public void alterarTest() throws Exception {
        IClienteDAO dao = new ClienteDAO();


        Cliente cliente = new Cliente();
        cliente.setCodigo("05");
        cliente.setNome("Fernanda Lima");
        dao.cadastrar(cliente);

        try {

            cliente.setNome("Fernanda Oliveira");
            Integer qtd = dao.alterar(cliente);
            assertEquals(1, qtd.intValue());


            Cliente clienteAlterado = dao.consultar(cliente.getCodigo());
            assertNotNull(clienteAlterado);
            assertEquals(cliente.getCodigo(), clienteAlterado.getCodigo());
            assertEquals(cliente.getNome(), clienteAlterado.getNome());

        } finally {

            dao.excluir(cliente);
        }
    }

    private boolean containsClienteWithCodigo(List<Cliente> clientes, String codigo) {
        for (Cliente cliente : clientes) {
            if (cliente.getCodigo().equals(codigo)) {
                return true;
            }
        }
        return false;
    }
}


