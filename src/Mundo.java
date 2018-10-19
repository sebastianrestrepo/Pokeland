import java.util.Observable;
import java.util.Observer;

import processing.core.PApplet;

public class Mundo implements Observer {

	private PApplet app;
	private Cargar cargar;
	private UI ui;
	private ComunicacionCliente cc;
	private int equipo;

	public Mundo(PApplet app) {
		this.app = app;
		cargar = new Cargar(app);
		ui = new UI(app, this);
		cc = new ComunicacionCliente();
		new Thread(cc).start();
		cc.addObserver(this);
	}

	public void pintar() {
		ui.pintar();
	}

	public void click() {
		ui.click();
		cc.enviarMensaje(new Mensaje("Holap", 1, ui.getSelEquipo()));
	}

	public void keyKeleased() {
		ui.keyReleased();
	}
	
	@Override
	public void update(Observable o, Object arg) {

		if(o instanceof ComunicacionCliente) {
			Mensaje m = (Mensaje) arg;
			System.out.println("Soy el Cliente #:" + m.getIndex());
			equipo = m.getIndex();
		}
		
	}

	// ----------------GETTERS Y SETTERS------------//
	public Cargar getCargar() {
		return cargar;
	}

	public void setCargar(Cargar cargar) {
		this.cargar = cargar;
	}

	public int getEquipo() {
		return equipo;
	}

	public void setEquipo(int equipo) {
		this.equipo = equipo;
	}

	// ----------------FINAL DE LA CLASE MUNDO------------//
}
