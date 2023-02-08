package modelo.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.entidad.Pasajero;
import modelo.persistencia.interfaces.DaoPasajero;

public class DaoPasajeroMySql implements DaoPasajero{
	
private Connection conexion;
	
	//Método para abrir la conexión con al Base de Datos
	public boolean abrirConexion(){
		String url = "jdbc:mysql://localhost:3306/bbdd";
		String usuario = "root";
		String password = "";
		try {
			conexion = DriverManager.getConnection(url,usuario,password);
		} catch (SQLException e) {
			System.out.println("No se ha podido abrir la conexión");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	//Método para cerrar la conexión con la Base de Datos
	public boolean cerrarConexion(){
		try {
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	//Añadir nuevo pasajero
	@Override
	public boolean altaPasajero(Pasajero p) {
		if(!abrirConexion()) {
			return false;
		}
		
		boolean alta = true;
		String sql = "insert into pasajeros (NOMBRE,EDAD,PESO)" 
				+ "values(?,?,?)";
		
		try {
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setString(1, p.getNombre());
			ps.setInt(2, p.getEdad());
			ps.setDouble(3, p.getPeso());
						
			int numeroFilasAfectadas = ps.executeUpdate();
			if(numeroFilasAfectadas == 0)
				alta = false;
			
		}catch (SQLException e) {
			System.out.println("Pasajero => Error al dar de alta");
			e.printStackTrace();
			alta = false;
		}finally {
			cerrarConexion();
		}
		return alta;
	}

	//Borrar pasajero por id
	@Override
	public boolean bajaPasajero(int id) {
		if(!abrirConexion()){
			return false;
		}
		
		boolean borrado = true;
		String query = "delete from pasajeros where id = ?";
		
		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setInt(1, id);
			
			int numeroFilasAfectadas = ps.executeUpdate();
			if(numeroFilasAfectadas == 0)
				borrado = false;
			
		} catch (SQLException e) {
			borrado = false;
			System.out.println("bajaPasajero => No se ha podido dar de baja"
					+ " el id " + id);
			e.printStackTrace();
		} finally {
			cerrarConexion();
		}
		return borrado; 	
	}
	
	//Consulta pasajero por id
	@Override
	public Pasajero consultaPasajero(int id) {
		if(!abrirConexion()){
			return null;
		}		
		
		Pasajero pasajero = null;
		
		String query = "select ID,NOMBRE,EDAD,PESO,ID_COCHE from pasajeros "
				+ "where id = ?";
		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				pasajero = new Pasajero();
				pasajero.setId(rs.getInt(1));
				pasajero.setNombre(rs.getString(2));
				pasajero.setEdad(rs.getInt(3));
				pasajero.setPeso(rs.getDouble(4));		
				pasajero.setIdCoche(rs.getInt(5));
			}
		} catch (SQLException e) {
			System.out.println("consultaPasajeros => error al obtener el "
					+ "coche con id " + id);
			e.printStackTrace();
		} finally {
			cerrarConexion();
		}		
		
		return pasajero;
	}

	//Listar todos los pasajeros
	@Override
	public List<Pasajero> listarPasajeros() {
		if(!abrirConexion()){
			return null;
		}		
		
		List<Pasajero> listaPasajeros = new ArrayList<>();		
		String query = "select ID,NOMBRE,EDAD,PESO,ID_COCHE from pasajeros";
		
		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				Pasajero pasajero = new Pasajero();
				pasajero.setId(rs.getInt(1));
				pasajero.setNombre(rs.getString(2));
				pasajero.setEdad(rs.getInt(3));
				pasajero.setPeso(rs.getDouble(4));
				pasajero.setIdCoche(rs.getInt(5));
				
				listaPasajeros.add(pasajero);
			}
		} catch (SQLException e) {
			System.out.println("listarPasajeross => error al obtener los "
					+ "pasajeros");
			e.printStackTrace();
		} finally {
			cerrarConexion();
		}		
		
		return listaPasajeros;
	}

	//Añadir pasajero a coche (pasándole ID de pasajero e ID de coche)
	@Override
	public boolean addPasajeroACoche(int idPas, int idCoche) {
		if(!abrirConexion()) {
			return false;
		}
		
		boolean alta = true;
		String sql = "update pasajeros set ID_COCHE=? WHERE ID=?";
		
		try {
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setInt(1, idCoche);
			ps.setInt(2, idPas);
			
						
			int numeroFilasAfectadas = ps.executeUpdate();
			if(numeroFilasAfectadas == 0)
				alta = false;
			
		}catch (SQLException e) {
			System.out.println("AñadirPasajeroACoche => Error al dar de alta al pasajero " 
					+ idPas + " en el coche " + idCoche);
			e.printStackTrace();
			alta = false;
		}finally {
			cerrarConexion();
		}
		return alta;
	}
	
	//Eliminar pasajero de un coche (pasándole ID de pasajero e ID de coche)
	@Override
	public boolean borrarPasajeroDeCoche(int idPas, int idCoche) {
		if(!abrirConexion()) {
			return false;
		}
		
		boolean alta = true;
		String sql = "update pasajeros set ID_COCHE=NULL " 
				+ "WHERE ID_COCHE=? and ID=?";
		
		try {
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setInt(1, idCoche);
			ps.setInt(2, idPas);
			
						
			int numeroFilasAfectadas = ps.executeUpdate();
			if(numeroFilasAfectadas == 0)
				alta = false;
			
		}catch (SQLException e) {
			System.out.println("BorrarPasajeroDeCoche => Error al dar de baja al pasajero "
					+ idPas + " del coche" + idCoche);
			e.printStackTrace();
			alta = false;
		}finally {
			cerrarConexion();
		}
		return alta;
	}

	//Listar todos los pasajeros de un coche con el ID de un coche
	@Override
	public List<Pasajero> listarPasajerosCoche(int idCoche) {
		if(!abrirConexion()){
			return null;
		}		
		
		List<Pasajero> listaPasajeros = new ArrayList<>();		
		String query = "select ID,NOMBRE,EDAD,PESO,ID_COCHE from pasajeros WHERE ID_COCHE=?";
		
		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setInt(1, idCoche);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				Pasajero pasajero = new Pasajero();
				pasajero.setId(rs.getInt(1));
				pasajero.setNombre(rs.getString(2));
				pasajero.setEdad(rs.getInt(3));
				pasajero.setPeso(rs.getDouble(4));
				pasajero.setIdCoche(rs.getInt(5));
				
				listaPasajeros.add(pasajero);
			}
		} catch (SQLException e) {
			System.out.println("listarCoches => error al obtener los "
					+ "pasajeros");
			e.printStackTrace();
		} finally {
			cerrarConexion();
		}
		
		
		return listaPasajeros;
	}

}
