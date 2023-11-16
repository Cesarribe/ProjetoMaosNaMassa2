package dao;

import dao.jdbc.ConnectionFactory;
import domain.Produto;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO implements IProdutoDAO {


        @Override
        public Integer cadastrar(Produto produto) throws Exception {
            Connection connection = null;
            PreparedStatement stm = null;
            try {
                connection = ConnectionFactory.getConnection();
                connection = ConnectionFactory.getConnection();
                String sql = "INSERT INTO TB_PRODUTO (ID, NOME, PRECO) VALUES (nextval('seq_tb_produto_id'), ?, ?)";
                stm = connection.prepareStatement(sql);
                stm.setString(1, produto.getNome());
                stm.setDouble(2, produto.getPreco());
                return stm.executeUpdate();
            } catch (Exception e) {
                throw e;
            } finally {
                closeResources(connection, stm, null);
            }
        }

    @Override
    public Produto consultar(String codigo) throws Exception {
        Connection connection = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        Produto produto = null;
        try {
            connection = ConnectionFactory.getConnection();
            String sql = "SELECT * FROM TB_PRODUTO WHERE id = ?";
            stm = connection.prepareStatement(sql);
            stm.setString(1, codigo);
            rs = stm.executeQuery();
            if (rs.next()) {
                produto = new Produto();
                produto.setId(rs.getLong("id"));
                produto.setNome(rs.getString("nome"));
                produto.setPreco(rs.getDouble("preço"));
            }
            return produto;
        } catch (Exception e) {
            throw e;
        } finally {
            closeResources(connection, stm, rs);
        }
    }

    @Override
    public Integer excluir(Produto produtoBD) throws Exception {
        Connection connection = null;
        PreparedStatement stm = null;
        try {
            connection = ConnectionFactory.getConnection();
            String sql = "DELETE FROM TB_PRODUTO WHERE ID = ?";
            stm = connection.prepareStatement(sql);
            stm.setLong(1, produtoBD.getId());
            return stm.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            closeResources(connection, stm, null);
        }
    }


        @Override
        public List<Produto> buscarTodos() throws Exception {
            Connection connection = null;
            PreparedStatement stm = null;
            ResultSet rs = null;
            List<Produto> products = new ArrayList<>();

            try {
                connection = ConnectionFactory.getConnection();
                String sql = "SELECT * FROM TB_PRODUTO";
                stm = connection.prepareStatement(sql);
                rs = stm.executeQuery();

                while (rs.next()) {
                    Produto produto = new Produto();
                    produto.setId(rs.getLong("id"));
                    produto.setNome(rs.getString("nome"));
                    produto.setPreco(rs.getDouble("preco"));
                    products.add(produto);
                }

                return products;
            } catch (Exception e) {
                throw e;
            } finally {
                closeResources(connection, stm, rs);
            }
        }

        @Override
        public Integer alterar(Produto produto) throws Exception {
            Connection connection = null;
            PreparedStatement stm = null;
            Integer rowsAffected = 0;

            try {
                connection = ConnectionFactory.getConnection();
                String sql = "UPDATE TB_PRODUTO SET NOME = ?, PRECO = ? WHERE ID = ?";
                stm = connection.prepareStatement(sql);
                stm.setString(1, produto.getNome());
                stm.setDouble(2, produto.getPreco());
                stm.setLong(3, produto.getId());
                rowsAffected = stm.executeUpdate();
            } catch (Exception e) {
                throw e;
            } finally {
                closeResources(connection, stm, null);
            }

            return rowsAffected;
        }


        private void closeResources(Connection connection, PreparedStatement stm, ResultSet rs) {
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
                if (stm != null && !stm.isClosed()) {
                    stm.close();
                }
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



