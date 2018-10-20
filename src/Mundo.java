import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import processing.core.PApplet;

public class Mundo implements Observer, Runnable {

	private PApplet app;
	private Cargar cargar;
	private UI ui;
	private ComunicacionCliente cc;
	private int equipo;
	private boolean start;

	public Mundo(PApplet app) {
		this.app = app;
		cargar = new Cargar(app);
		start = false;
		ui = new UI(app, this);
	
	}

	public void pintar() {
		ui.pintar();

		try {
			if (ui.getPantallas() == 2) {
				if (start) {
					Thread.sleep(5000);
					ui.setPantallas(3);
				}
			}

		} catch (InterruptedException e) {

			e.printStackTrace();
		}

	}

	public void click() {
		ui.click();

	}

	public void keyKeleased() {
		switch (ui.getPantallas()) {
		case 0:
			if (app.key == app.ENTER) {
				cc = new ComunicacionCliente();
				new Thread(cc).start();
				cc.addObserver(this);
				cc.enviarMensaje(new Mensaje("Holap", 1, ui.getSelEquipo()));

				System.out.println("CLICK EQUIPO: " + ui.getSelEquipo());
			}
			break;
		}
		ui.keyReleased();


	}

	@Override
	public void update(Observable o, Object arg) {

		if (o instanceof ComunicacionCliente) {
			Mensaje m = (Mensaje) arg;
			equipo = m.getIndex() - 1;

			if (m.getM().equalsIgnoreCase("equipo")) {
				ui.seleccionEquipo();
			}
			System.out.println("Soy el Cliente #:" + m.getIndex() + equipo);

			if (m.getM().equalsIgnoreCase("start")) {
				start = true;

			}

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

	public PApplet getApp() {
		return app;
	}

	public void setApp(PApplet app) {
		this.app = app;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

	// ----------------FINAL DE LA CLASE MUNDO------------//
}
