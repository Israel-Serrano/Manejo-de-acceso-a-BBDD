package modelo.persistencia.interfaces;

import java.util.List;

import modelo.entidad.Pasajero;

public interface DaoPasajero {
	/**
	 * Metodo que da de alta un pasajero en la BBDD. Se generará el ID de manera
	 * automática.
	 * @param p el pasajero a dar de alta
	 * @return true en caso de que se haya dado de alta. false en caso de error
	 * con la BBDD.
	 */
	public boolean altaPasajero(Pasajero p);
	/**
	 * Metodo que da de baja un pasajero en la BBDD. 
	 * @param id del pasajero a dar de baja.
	 * @return true en caso de que se haya dado de alta. false en caso de error
	 * con la BBDD.
	 */
	public boolean bajaPasajero(int id);
	/**
	 * Metodo para consultar un pasajero en la BBDD. 
	 * @param id del pasajero a consultar.
	 * @return el coche consultado o mensaje de error en caso de error
	 * con la BBDD.
	 */
	public Pasajero consultaPasajero(int id);
	/**
	 * Metodo para listar todos los pasajeros de la BBDD. 
	 * @return la lista de pasajeros de la BBDD o mensaje de error en caso de error
	 * con la BBDD.
	 */
	public List<Pasajero> listarPasajeros();
	/**
	 * Metodo que añade un pasajero a un coche en la BBDD. Se generará el ID de manera
	 * automática.
	 * @param idPas el id del pasajero a añadir.
	 * @param idCoche el id del coche en el que se añade el pasajero.
	 * @return true en caso de que se haya añadido. False en caso de error
	 * con la BBDD.
	 */
	public boolean addPasajeroACoche(int idPas, int idCoche);
	/**
	 * Metodo que borra un pasajero de un coche en la BBDD. 
	 * @param idPas el id del pasajero a eliminar.
	 * @param idCoche el id del coche del que se borra el pasajero.
	 * @return true en caso de que se haya borrado. False en caso de error
	 * con la BBDD.
	 */
	public boolean borrarPasajeroDeCoche(int idPas, int idCoche);
	/**
	 * Metodo para listar todos los pasajeros de un coche de la BBDD. 
	 * @param idCoche el id del coche del que mostramos los pasajeros.
	 * @return la lista de pasajeros del coche o mensaje de error en caso de error
	 * con la BBDD.
	 */
	public List<Pasajero> listarPasajerosCoche(int idCoche);
	

}
