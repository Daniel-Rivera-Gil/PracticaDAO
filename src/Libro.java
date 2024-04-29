public class Libro {
    protected int isbn;
    protected String titulo; 
    protected String autor;
    protected double precio;

    
    public Libro(int isbn, String titulo, String autor, double precio) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.precio = precio;
    }


    public int getIsbn() {
        return isbn;
    }


    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }


    public String getTitulo() {
        return titulo;
    }


    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }


    public String getAutor() {
        return autor;
    }


    public void setAutor(String autor) {
        this.autor = autor;
    }


    public double getPrecio() {
        return precio;
    }


    public void setPrecio(double precio) {
        this.precio = precio;
    }

    
}
