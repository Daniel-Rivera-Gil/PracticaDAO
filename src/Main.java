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
		String password="root";
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
            case 3:
                buscarLibroPorISBN(url,user,password,teclado);
                break;
            case 4:
                buscarLibroPorAutor(url,user,password,teclado);
                break;
            case 5:
                buscarLibroPorTitulo(url,user,password,teclado);
                break;
            case 6:
                buscarLibroPorPrecio(url,user,password,teclado);
                break;
            case 7:
                eliminarLibro(url,user,password,teclado);
                break;
            case 8:
                todosLosLibros(url,user,password);
                break;
            case 9:
                numeroLibros(url,user,password);
                break;
            case 10: 
                break;
        }
        }while(opcion<1 && opcion > 10);
        
    
    }


    public static void nuevoLibro(String url, String user, String password,Scanner teclado) {
        String autor,titulo,sql;
        int isbn;
        double precio;
        PreparedStatement prepared=null;
        System.out.print("Dime el isbn del libro: ");
        isbn = teclado.nextInt();
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
            prepared.setInt(1, isbn);
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

    public static void modificarLibro(String url, String user, String password, Scanner teclado) {
        int isbnLibro=0;
        String sql;
        int opcion;
        System.out.println("Dime el isbn del libro que quieras");
        isbnLibro = teclado.nextInt();
       
        sql = "UPDATE libro SET isbn = ? SET titulo = ? SET autor = ? SET precio = ?";
        try(Connection conexion = DriverManager.getConnection(url,user,password)){
            Statement sentencia = conexion.createStatement();
			ResultSet resultados = sentencia.executeQuery("select * from libro");
            String titulo = resultados.getString("titulo");
            int isbnElegido = resultados.getInt("isbn");
            String autor = resultados.getString("autor");
            double precio = resultados.getDouble("precio");
            PreparedStatement prepared = conexion.prepareStatement(sql);
            if(isbnElegido == isbnLibro){
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


    public static void cambiarPrecio(Scanner teclado, String titulo, int isbnElegido, String autor,
        PreparedStatement prepared) throws SQLException {
        String respuesta;
        double precioNuevo;
        do{
        System.out.println("Quieres cambiar el precio?");
        respuesta = teclado.nextLine();
        if(respuesta.equalsIgnoreCase("Si")){
            System.out.println("Dime el nuevo precio");
            precioNuevo = teclado.nextDouble();
            prepared.setInt(1, isbnElegido);
            prepared.setString(2, titulo);
            prepared.setString(3, autor);
            prepared.setDouble(2, precioNuevo);
            prepared.executeUpdate();
            }else {
                break;
            }
        }while(!respuesta.equalsIgnoreCase("Si") || !respuesta.equalsIgnoreCase("No"));
    }


    public static void cambiarAutor(Scanner teclado, String titulo, int isbnElegido, double precio,
        PreparedStatement prepared) throws SQLException {
        String respuesta;
        String autorNuevo;
        do{
            System.out.println("Quieres cambiar el autor?");
            respuesta = teclado.nextLine();
            if(respuesta.equalsIgnoreCase("Si")){
                System.out.println("Dime el nuevo autor");
                autorNuevo = teclado.nextLine();
                prepared.setInt(1, isbnElegido);
                prepared.setString(2, titulo);
                prepared.setString(3, autorNuevo);
                prepared.setDouble(2, precio);
                prepared.executeUpdate();
            }else {
                break;
            }
            }while(!respuesta.equalsIgnoreCase("Si") || !respuesta.equalsIgnoreCase("No"));
    }


    public static void cambiarTitulo(Scanner teclado, int isbnElegido, String autor, double precio,
            PreparedStatement prepared) throws SQLException {
        String respuesta;
        String tituloNuevo;
        do{
            System.out.println("Quieres cambiar el titulo?");
            respuesta = teclado.nextLine();
            if(respuesta.equalsIgnoreCase("Si")){
                System.out.println("Dime el nuevo titulo: ");
                tituloNuevo = teclado.nextLine();
                prepared.setInt(1, isbnElegido);
                prepared.setString(2, tituloNuevo);
                prepared.setString(3, autor);
                prepared.setDouble(2, precio);
                prepared.executeUpdate();
            }else {
                break;
            }
        }while(!respuesta.equalsIgnoreCase("Si") || !respuesta.equalsIgnoreCase("No"));
    }

    
    public static void buscarLibroPorISBN(String url, String user, String password, Scanner teclado) {
        System.out.println("Dime el isbn que quieras buscar");
        int isbnABuscar = teclado.nextInt(); // ISBN a buscar
        String consultaSQL = "SELECT * FROM libro WHERE isbn = ?";

        try(Connection conexion = DriverManager.getConnection(url,user,password)){
            PreparedStatement statement = conexion.prepareStatement(consultaSQL);
            statement.setInt(1, isbnABuscar);

            ResultSet resultado = statement.executeQuery();

            // Mostramos los libros encontrados
           while (resultado.next()) {
                String titulo = resultado.getString("titulo");
                String autor = resultado.getString("autor");
                int isbn = resultado.getInt("isbn");
                double precio = resultado.getDouble("precio");

                System.out.println("Título: " + titulo);
                System.out.println("Autor: " + autor);
                System.out.println("ISBN: " + isbn);
                System.out.println("Precio: " + precio);
            }
        }catch(SQLException ex) {
            System.out.println(ex.toString());
        }
    }

    public static void buscarLibroPorAutor(String url, String user, String password, Scanner teclado) {
        System.out.println("Dime el autor que quieras buscar");
        String autorABuscar = teclado.nextLine();
        String consultaSQL = "select * from libro where autor LIKE ?";
        try(Connection conexion = DriverManager.getConnection(url,user,password)){
            PreparedStatement statement = conexion.prepareStatement(consultaSQL);
            statement.setString(1,"%"+autorABuscar+"%");

            ResultSet resultado = statement.executeQuery();

            while (resultado.next()) {
                String titulo = resultado.getString("titulo");
                String autor = resultado.getString("autor");
                int isbn = resultado.getInt("isbn");
                double precio = resultado.getDouble("precio");

                System.out.println("Título: " + titulo);
                System.out.println("Autor: " + autor);
                System.out.println("ISBN: " + isbn);
                System.out.println("Precio: " + precio);
            }
        }catch(SQLException ex) {
            System.out.println(ex.toString());
        }
    }

    public static void buscarLibroPorTitulo(String url, String user, String password, Scanner teclado) {
        System.out.println("Dime el titulo que quieras buscar");
        String tituloABuscar = teclado.nextLine();
        String consultaSQL = "select * from libro where titulo LIKE ?";
        try(Connection conexion = DriverManager.getConnection(url,user,password)){
            PreparedStatement statement = conexion.prepareStatement(consultaSQL);
            statement.setString(1,"%"+tituloABuscar+"%");

            ResultSet resultado = statement.executeQuery();

            while (resultado.next()) {
                String titulo = resultado.getString("titulo");
                String autor = resultado.getString("autor");
                int isbn = resultado.getInt("isbn");
                double precio = resultado.getDouble("precio");

                System.out.println("Título: " + titulo);
                System.out.println("Autor: " + autor);
                System.out.println("ISBN: " + isbn);
                System.out.println("Precio: " + precio);
            }
        }catch(SQLException ex) {
            System.out.println(ex.toString());
        }
    }

    public static void buscarLibroPorPrecio(String url, String user, String password, Scanner teclado) {
       double precioABuscar;
       String consultaSQL;
        System.out.println("Dime el precio que quieras buscar");
        precioABuscar = teclado.nextInt(); // ISBN a buscar
        consultaSQL = "SELECT * FROM libro WHERE precio = ?";

        try(Connection conexion = DriverManager.getConnection(url,user,password)){
            PreparedStatement statement = conexion.prepareStatement(consultaSQL);
            statement.setDouble(1, precioABuscar);

            ResultSet resultado = statement.executeQuery();

            // Mostramos los libros encontrados
           while (resultado.next()) {
                String titulo = resultado.getString("titulo");
                String autor = resultado.getString("autor");
                int isbn = resultado.getInt("isbn");
                double precio = resultado.getDouble("precio");

                System.out.println("Título: " + titulo);
                System.out.println("Autor: " + autor);
                System.out.println("ISBN: " + isbn);
                System.out.println("Precio: " + precio);
            }
        }catch(SQLException ex) {
            System.out.println(ex.toString());
        }
    }

    public static void eliminarLibro(String url, String user, String password, Scanner teclado) {
        int isbnAEliminar,filasAfectadas;
        String consultaSQL;
        System.out.println("Dime el isbn que quieras buscar");
        isbnAEliminar = teclado.nextInt(); // ISBN a eliminar
        consultaSQL = "DELETE FROM libro WHERE isbn = ?";
        try(Connection conexion = DriverManager.getConnection(url,user,password)){
            PreparedStatement statement = conexion.prepareStatement(consultaSQL);
            statement.setInt(1, isbnAEliminar);

            filasAfectadas = statement.executeUpdate();

            // Verificamos si se eliminó el libro 
            if (filasAfectadas > 0) {
                System.out.println("El libro con ISBN " + isbnAEliminar + " ha sido eliminado de la base de datos.");
            } else {
                System.out.println("No se encontró ningún libro con ISBN " + isbnAEliminar + ".");
            }
        }catch(SQLException ex) {
            System.out.println(ex.toString());
        }
    }

    public static void todosLosLibros(String url, String user, String password) {
        String consultaSQL = "SELECT autor, GROUP_CONCAT(titulo SEPARATOR ', ') AS libros FROM libro GROUP BY autor";
        try(Connection conexion = DriverManager.getConnection(url,user,password)){
            Statement statement = conexion.createStatement();
           ResultSet resultado = statement.executeQuery(consultaSQL);

            while (resultado.next()) {
                String autor = resultado.getString("autor");
                String libros = resultado.getString("libros");

                // Mostramos la información del autor y sus libros
                System.out.println(autor + ":");
                System.out.println(libros);
                System.out.println("----------------------");
            }
        }catch(SQLException ex) {
            System.out.println(ex.toString());
        }
    }

    public static void numeroLibros(String url, String user, String password) {
        String consultaSQL = "SELECT COUNT(*) AS total_libros FROM libro";
        try(Connection conexion = DriverManager.getConnection(url,user,password)){
            Statement statement = conexion.createStatement();
          
            ResultSet resultado = statement.executeQuery(consultaSQL);
            if (resultado.next()) {
                int totalLibros = resultado.getInt("total_libros");
                System.out.println("Número total de libros en la biblioteca: " + totalLibros);
            }
        }catch(SQLException ex) {
            System.out.println(ex.toString());
        }
       }
    }   

