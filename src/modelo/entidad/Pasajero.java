package modelo.entidad;

public class Pasajero {
	private int id;
	private String nombre;
	private int edad;
	private double peso;
	private int idCoche;
	
	
	public Pasajero() {
		super();
	}


	public Pasajero(int id, String nombre, int edad, double peso, int idCoche) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.edad = edad;
		this.peso = peso;
		this.idCoche = idCoche;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public int getEdad() {
		return edad;
	}


	public void setEdad(int edad) {
		this.edad = edad;
	}


	public double getPeso() {
		return peso;
	}


	public void setPeso(double peso) {
		this.peso = peso;
	}


	public int getIdCoche() {
		return idCoche;
	}


	public void setIdCoche(int idCoche) {
		this.idCoche = idCoche;
	}


	@Override
	public String toString() {
		return "Pasajeros [id=" + id + ", nombre=" + nombre + ", edad=" + edad + ", peso=" + peso + ", idCoche=" + idCoche
				+ "]";
	}


	
}
