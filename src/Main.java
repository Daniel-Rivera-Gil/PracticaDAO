import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        String url = "jdbc:mysql://localhost:3306/libros";
		String user="root";
		String password="pablodani6137";
        int opcion = 0;
        do{
        System.out.println("1-Nuevo libro");
        System.out.println("2-Modificar libro");
        System.out.println("3-Buscar libro por ISBN");
        System.out.println("4-Buscar libro por autor");
        System.out.println("5-Buscar libro por titulo");
        System.out.println("6-Buscar libro por precio");
        System.out.println("7-Eliminar libro(por ISBN)");
        System.out.println("8-Todos los libros");
        System.out.println("9-Número de libros");
        System.out.println("10-Salir");
        System.out.println("Seleccione una opcion");
        opcion = teclado.nextInt();

        switch (opcion) {
            case 1:
                nuevoLibro(url,user,password,teclado);
                break;
            case 2:
                break;
        }
        }while(opcion<1 && opcion > 10);
        
    
    }

    private static void nuevoLibro(String url, String user, String password,Scanner teclado) {
        String isbn,autor,titulo,sql;
        double precio;
        PreparedStatement prepared=null;
        System.out.print("Dime el isbn del libro: ");
        isbn = teclado.nextLine();
        teclado.nextLine();
        System.out.print("Dime el titulo del libro: ");
        titulo = teclado.nextLine();
        System.out.print("Dime el autor del libro: ");
        autor = teclado.nextLine();
        System.out.print("Dime el precio del libro: ");
        precio = teclado.nextDouble();
        try(Connection conexion = DriverManager.getConnection(url,user,password)){
            sql = "INSERT INTO libro (isbn,titulo,autor, precio) VALUES (?,?,?,?)";
            prepared = conexion.prepareStatement(sql);
            //Vamos a establecer los parametros
            prepared.setString(1, isbn);
            prepared.setString(2, titulo);
            prepared.setString(3, autor);
            prepared.setDouble(4, precio);
            int mod = prepared.executeUpdate();
            if(mod == 1 ) {
                System.out.println("Libro añadido con éxito");
            }else {
            }
            
        }catch(SQLException ex) {
            System.out.println(ex.toString());
        }
    }
}
