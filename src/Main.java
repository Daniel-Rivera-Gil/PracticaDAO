import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
                modificarLibro(url,user,password,teclado);
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
                System.out.println("Error al añadir libro");
            }
            
        }catch(SQLException ex) {
            System.out.println(ex.toString());
        }
    }

    private static void modificarLibro(String url, String user, String password, Scanner teclado) {
        String isbnLibro=null,respuesta = null;
        String sql;
        int opcion;
        System.out.println("Dime el isbn del libro que quieras");
        isbnLibro = teclado.nextLine();
       
        sql = "UPDATE libro SET isbn = ? SET titulo = ? SET autor = ? SET precio = ?";
        try(Connection conexion = DriverManager.getConnection(url,user,password)){
            Statement sentencia = conexion.createStatement();
			ResultSet resultados = sentencia.executeQuery("select * from libro");
            String titulo = resultados.getString("titulo");
            String isbnElegido = resultados.getString("isbn");
            String autor = resultados.getString("autor");
            double precio = resultados.getDouble("precio");
            PreparedStatement prepared = conexion.prepareStatement(sql);
            if(isbnElegido.equalsIgnoreCase(isbnLibro)){
                System.out.println("1-Cambiar el titulo");
                System.out.println("2-Cambiar el autor");
                System.out.println("3-Cambiar el precio");
                System.out.println("Seleccione una opcion");
                opcion = teclado.nextInt();
                switch (opcion) {
                    case 1:
                        cambiarTitulo(teclado, isbnElegido, autor, precio, prepared);
                        break;
                    case 2:
                        cambiarAutor(teclado, titulo, isbnElegido, precio, prepared);
                        break;
                    case 3:
                        cambiarPrecio(teclado, titulo, isbnElegido, autor, prepared);
                    break;    
                    }
                }
               
            }catch(SQLException ex) {
                System.out.println(ex.toString());
            }
        }


    private static void cambiarPrecio(Scanner teclado, String titulo, String isbnElegido, String autor,
        PreparedStatement prepared) throws SQLException {
        String respuesta;
        double precioNuevo;
        do{
        System.out.println("Quieres cambiar el precio?");
        respuesta = teclado.nextLine();
        if(respuesta.equalsIgnoreCase("Si")){
            System.out.println("Dime el nuevo precio");
            precioNuevo = teclado.nextDouble();
            prepared.setString(1, isbnElegido);
            prepared.setString(2, titulo);
            prepared.setString(3, autor);
            prepared.setDouble(2, precioNuevo);
            prepared.executeUpdate();
            }else {
                break;
            }
        }while(!respuesta.equalsIgnoreCase("Si") || !respuesta.equalsIgnoreCase("No"));
    }


    private static void cambiarAutor(Scanner teclado, String titulo, String isbnElegido, double precio,
        PreparedStatement prepared) throws SQLException {
        String respuesta;
        String autorNuevo;
        do{
            System.out.println("Quieres cambiar el autor?");
            respuesta = teclado.nextLine();
            if(respuesta.equalsIgnoreCase("Si")){
                System.out.println("Dime el nuevo autor");
                autorNuevo = teclado.nextLine();
                prepared.setString(1, isbnElegido);
                prepared.setString(2, titulo);
                prepared.setString(3, autorNuevo);
                prepared.setDouble(2, precio);
                prepared.executeUpdate();
            }else {
                break;
            }
            }while(!respuesta.equalsIgnoreCase("Si") || !respuesta.equalsIgnoreCase("No"));
    }


    private static void cambiarTitulo(Scanner teclado, String isbnElegido, String autor, double precio,
            PreparedStatement prepared) throws SQLException {
        String respuesta;
        String tituloNuevo;
        do{
            System.out.println("Quieres cambiar el titulo?");
            respuesta = teclado.nextLine();
            if(respuesta.equalsIgnoreCase("Si")){
                System.out.println("Dime el nuevo titulo");
                tituloNuevo = teclado.nextLine();
                prepared.setString(1, isbnElegido);
                prepared.setString(2, tituloNuevo);
                prepared.setString(3, autor);
                prepared.setDouble(2, precio);
                prepared.executeUpdate();
            }else {
                break;
            }
        }while(!respuesta.equalsIgnoreCase("Si") || !respuesta.equalsIgnoreCase("No"));
    }

    }   

