/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 *
 * @author rafae
 */
public class TestaAmbiente {
    private final String URL="jdbc:mysql://localhost/bancorrw";
    private final String USER="root";
    private final String PWD="root";
    
    public TestaAmbiente() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testaJunit() {
        
    }
    
    @Test
    public void testaDriverJDBCeConexao() throws SQLException {
        DriverManager.getConnection(URL,USER,PWD);
    }
    
        @Test
    public void testaExisteTabelasBD() throws SQLException {
        Connection con = DriverManager.getConnection(URL,USER,PWD);
        PreparedStatement stmt = con.prepareStatement("SHOW TABLES");
        ResultSet rs = stmt.executeQuery();
        rs.next();
        assertEquals("clientes",rs.getString(1));
        rs.next();
        assertEquals("contas",rs.getString(1));
        rs.next();
        assertEquals("contas_corrente",rs.getString(1));
        rs.next();
        assertEquals("contas_investimento",rs.getString(1));        

        
    }

}
