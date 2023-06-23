    package banco;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Banco {

    private String driver;
    private String database;
    private String user;
    private String senha;
    private Connection conexao;

    public Banco() {
        this.driver = "com.mysql.cj.jdbc.Driver";
        this.database = "db";
        this.user = "root";
        this.senha = "password";
    }

    public Banco(String driver, String database, String user, String senha) {
        this.driver = driver;
        this.database = database;
        this.user = user;
        this.senha = senha;
    }

    public void criarTabelas() throws Exception {
        Class.forName(driver);
        String url = "jdbc:mysql://localhost:3306";
        
        try {
            conexao = DriverManager.getConnection(url, user, senha);
        } catch (SQLException ex) {
            throw new Exception("driver incorreto - " + driver);
        }
        
        ArrayList<String> sqls = this.criarSqlDasTabelas();
        
        try {
            Statement sessao = conexao.createStatement();
            sessao.executeUpdate("CREATE DATABASE " + this.database + ";"); 
            sessao.executeUpdate("USE db;");  
            
            for(String sql : sqls) {
                sessao.executeUpdate(sql);     
            }

        } catch (SQLException e) {
            throw new Exception("Erro das tabelas");
        }
        
        
    }
    
    private ArrayList<String> criarSqlDasTabelas() {
        ArrayList<String> sqls = new ArrayList<String>();
        
        try {
            File myObj = new File("src/banco/tabelas.txt");
            Scanner myReader = new Scanner(myObj);
            
          while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            sqls.add(data);
          }
          myReader.close();
        } catch (FileNotFoundException e) {
          System.out.println("An error occurred.");
          e.printStackTrace();
        }
        
        System.out.print(sqls);
        return sqls;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Criar o banco
        Banco app = new Banco();
        try {
            app.criarTabelas();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}