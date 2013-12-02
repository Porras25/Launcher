package com.adquem.launcher;

import java.sql.Blob;

public class Bean_AppFavoritas {
	public int id; //identificador
	
	public String nombre;
	public String paquete;
	public Blob icon;
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * @return the paquete
	 */
	public String getPaquete() {
		return paquete;
	}
	/**
	 * @param paquete the paquete to set
	 */
	public void setPaquete(String paquete) {
		this.paquete = paquete;
	}
	/**
	 * @return the icon
	 */
	public Blob getIcon() {
		return icon;
	}
	/**
	 * @param icon the icon to set
	 */
	public void setIcon(Blob icon) {
		this.icon = icon;
	}
	

}
