package modelo.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.entidad.Coche;
import modelo.persistencia.interfaces.DaoCoche;

public class DaoCocheMySql implements DaoCoche{
	
	private Connection conexion;
	List<Coche> listaCoches = new ArrayList<>();
	
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
	
	//Añadir nuevo coche (El ID lo incrementará automáticamente la base de datos)
	@Override
	public boolean altaCoche(Coche c) {
		if(!abrirConexion()) {
			return false;
		}
		
		boolean alta = true;
		String sql = "insert into coches (MATRICULA,MARCA,MODELO,COLOR)" 
				+ "values(?,?,?,?)";
		
		try {
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setString(1, c.getMatricula());
			ps.setString(2, c.getMarca());
			ps.setString(3, c.getModelo());
			ps.setString(4, c.getColor());
			
			int numeroFilasAfectadas = ps.executeUpdate();
			if(numeroFilasAfectadas == 0)
				alta = false;
			
		}catch (SQLException e) {
			System.out.println("altaCoche => Error al dar de alta");
			e.printStackTrace();
			alta = false;
		}finally {
			cerrarConexion();
		}
		return alta;
	}

	//Borrar coche por ID
	@Override
	public boolean bajaCoche(int id) {
		if(!abrirConexion()){
			return false;
		}
		
		boolean borrado = true;
		String query = "delete from coches where id = ?";
		
		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setInt(1, id);
			
			int numeroFilasAfectadas = ps.executeUpdate();
			if(numeroFilasAfectadas == 0)
				borrado = false;
			
		} catch (SQLException e) {
			borrado = false;
			System.out.println("bajaCoche => No se ha podido dar de baja"
					+ " el id " + id);
			e.printStackTrace();
		} finally {
			cerrarConexion();
		}
		return borrado; 	
	}

	//Modificar coche por ID
	@Override
	public boolean modificarCoche(Coche c) {
		if(!abrirConexion()){
			return false;
		}
		
		boolean modificado = true;
		
		String query = "update coches set MATRICULA=?, MARCA=?, MODELO=?, COLOR=?"
				+ " WHERE ID=?";
		
		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setString(1, c.getMatricula());
			ps.setString(2, c.getMarca());
			ps.setString(3, c.getModelo());
			ps.setString(4, c.getColor());
			ps.setInt(5, c.getId());
						
			int numeroFilasAfectadas = ps.executeUpdate();
			if(numeroFilasAfectadas == 0)
				modificado = false;
			
		} catch (SQLException e) {
			System.out.println("modificarCoche => error al modificar el "
					+ " coche " + c);
			modificado = false;
			e.printStackTrace();
		} finally{
			cerrarConexion();
		}
		
		return modificado;
	}

	//Consulta coche por ID
	@Override
	public Coche consultaCoche(int id) {
		if(!abrirConexion()){
			return null;
		}		
		
		Coche coche = null;
		
		String query = "select ID,MATRICULA,MARCA,MODELO, COLOR from coches "
				+ "where id = ?";
		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				coche = new Coche();
				coche.setId(rs.getInt(1));
				coche.setMatricula(rs.getString(2));
				coche.setMarca(rs.getString(3));
				coche.setModelo(rs.getString(4));
				coche.setColor(rs.getString(5));
			}
		} catch (SQLException e) {
			System.out.println("consultaCoche => error al obtener el "
					+ "coche con id " + id);
			e.printStackTrace();
		} finally {
			cerrarConexion();
		}		
		
		return coche;
	}

	//Listado de coches
	@Override
	public List<Coche> listarCoches() {
		if(!abrirConexion()){
			return null;
		}		
		
		String query = "select ID,MATRICULA,MARCA,MODELO,COLOR from coches";
		
		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				Coche coche = new Coche();
				coche.setId(rs.getInt(1));
				coche.setMatricula(rs.getString(2));
				coche.setMarca(rs.getString(3));
				coche.setModelo(rs.getString(4));
				coche.setColor(rs.getString(5));
				
				listaCoches.add(coche);
			}
		} catch (SQLException e) {
			System.out.println("listarCoches => error al obtener los "
					+ "coches");
			e.printStackTrace();
		} finally {
			cerrarConexion();
		}
		
		
		return listaCoches;
	}
		
}
	


