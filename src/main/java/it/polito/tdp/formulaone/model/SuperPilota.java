package it.polito.tdp.formulaone.model;

import java.util.List;

public class SuperPilota {
	
	int idPilota;
	List<Integer> listaTempi;
	int lap;
	int punteggio;
	
	public int idPilota() {
		return idPilota;
	}
	public void setIdPilota(int idPilota) {
		this.idPilota = idPilota;
	}
	public List<Integer> getListaTempi() {
		return listaTempi;
	}
	public void setListaTempi(List<Integer> listaTempi) {
		this.listaTempi = listaTempi;
	}
	public int getLap() {
		return lap;
	}
	public void setLap(int lap) {
		this.lap = lap;
	}
	public int getPunteggio() {
		return punteggio;
	}
	public void setPunteggio(int punteggio) {
		this.punteggio = punteggio;
	}
	public SuperPilota(int idPilota, List<Integer> listaTempi) {
		super();
		this.idPilota = idPilota;
		this.listaTempi = listaTempi;
		this.lap = -1;
		this.punteggio = -1;
	}
	
}
