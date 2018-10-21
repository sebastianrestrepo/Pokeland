import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import ddf.minim.AudioSample;
import ddf.minim.Minim;
import processing.core.PApplet;

public class Mundo implements Observer, Runnable {

	private PApplet app;
	private Cargar cargar;
	private UI ui;
	private ComunicacionCliente cc;
	private int equipo;
	private boolean start;
	private Minim minim;
	private AudioSample turno, button, error;

	public Mundo(PApplet app) {
		this.app = app;
		cargar = new Cargar(app);
		start = false;
		ui = new UI(app, this);
		minim = new Minim(app);
		turno = minim.loadSample("/data/sound/turn.mp3");
		button = minim.loadSample("/data/sound/button.mp3");
		error = minim.loadSample("/data/sound/error.mp3");
	}

	public void pintar() {
		ui.pintar();

		try {
			if (ui.getPantallas() == 2) {
				if (start) {
					Thread.sleep(1000);
					ui.setPantallas(3);
				}
			}

		} catch (InterruptedException e) {

			e.printStackTrace();
		}

	}

	public void click() {
		ui.click();

		switch (ui.getPantallas()) {

		case 3:
			if (ui.getInsSwitch() == 9) {
				cc.enviarMensaje(new Mensaje("listo", 1, ui.getSelEquipo(), null));
				System.out.println("Envio mensaje listo: ");
			}

			if (ui.isTurnoactivado()) {

				// Click Bayas
				if (app.dist(app.mouseX, app.mouseY, 921, 629) < 50) {
					ui.setMostrarMenuBayas(true);
				}
				if (ui.isMostrarMenuBayas()) {

					if (app.mouseX > 421 && app.mouseX < 586 && app.mouseY > 278 && app.mouseY < 356) {
						System.out.println("Consumo Exacto");
						ui.setBayas(ui.getConsumo());
						ui.setMostrarMenuBayas(false);
						cc.enviarMensaje(new Mensaje("bayas", ui.getBayas(), ui.getSelEquipo(), null));
					}

					if (app.mouseX > 615 && app.mouseX < 781 && app.mouseY > 278 && app.mouseY < 356) {
						System.out.println("Consumo Exacto + mitad");
						ui.setBayas(ui.getConsumo()+(ui.getConsumo()/2));
						ui.setMostrarMenuBayas(false);
						cc.enviarMensaje(new Mensaje("bayas", ui.getBayas(), ui.getSelEquipo(), null));
					}

					if (app.mouseX > 417 && app.mouseX < 584 && app.mouseY > 382 && app.mouseY < 456) {
						System.out.println("Consumo Exacto + consumo + mitad");
						ui.setBayas(ui.getConsumo()+ui.getConsumo()+(ui.getConsumo()/2));
						ui.setMostrarMenuBayas(false);
						cc.enviarMensaje(new Mensaje("bayas", ui.getBayas(), ui.getSelEquipo(), null));
					}

					if (app.mouseX > 613 && app.mouseX < 782 && app.mouseY > 382 && app.mouseY < 456) {
						System.out.println("Consumo Exacto + consumo  + consumo");
						ui.setBayas(ui.getConsumo()+ui.getConsumo()+ui.getConsumo());
						ui.setMostrarMenuBayas(false);
						cc.enviarMensaje(new Mensaje("bayas", ui.getBayas(), ui.getSelEquipo(), null));
					}
					
				}
				// Click Arboles
				else if (app.dist(app.mouseX, app.mouseY, 1027, 629) < 50) {
					switch (ui.getAcciones()) {
					case 0:
						button.trigger();
						ui.setPedirArbol(true);
						ui.addArbol();
						cc.enviarMensaje(new Mensaje("nuevoArbol", 1, ui.getSelEquipo(), null));

						break;
					case 1:
						button.trigger();
						ui.setPedirArbol(true);
						ui.addArbol();
						cc.enviarMensaje(new Mensaje("nuevoArbol", 1, ui.getSelEquipo(), null));

						break;
					default:
						ui.setPedirArbol(false);
						error.trigger();
						break;
					}
				}
				// Click Pokebola
				else if (app.dist(app.mouseX, app.mouseY, 1132, 629) < 50) {
					switch (ui.getAcciones()) {
					case 0:
						button.trigger();
						ui.setPedirPokemon(true);
						ui.addPoke();
						cc.enviarMensaje(new Mensaje("nuevoPoke", 1, ui.getSelEquipo(), null));
						break;
					case 1:
						button.trigger();
						ui.setPedirPokemon(true);
						ui.addPoke();
						cc.enviarMensaje(new Mensaje("nuevoPoke", 1, ui.getSelEquipo(), null));

						break;
					default:
						ui.setPedirPokemon(false);
						error.trigger();
						break;
					}
				}

				break;
			}
		}

	}

	public void turnoTerminado() {
		//cc.enviarMensaje(new Mensaje("turnoterminado", 1, ui.getSelEquipo()));
		System.out.println("turnoterminado");
		cc.enviarMensaje(new Mensaje ("turnoterminado", ui.getBayas(), ui.getSelEquipo(), ui.getBayas() + "," + ui.getNumArboles() + ", " + ui.getNumPokemon()));
	}

	public void keyKeleased() {
		switch (ui.getPantallas()) {
		case 0:
			if (app.key == app.ENTER) {
				cc = new ComunicacionCliente();
				new Thread(cc).start();
				cc.addObserver(this);
				cc.enviarMensaje(new Mensaje("Holap", 1, ui.getSelEquipo(), null));

				System.out.println("Hola soy el equipo: " + ui.getSelEquipo());
			}
			break;

		case 3:

			break;
		}
		ui.keyReleased();

	}

	@Override
	public void update(Observable o, Object arg) {

		if (o instanceof ComunicacionCliente) {
			Mensaje m = (Mensaje) arg;

			if (m.getM().equalsIgnoreCase("equipo")) {
				equipo = m.getIndex() - 1;
				ui.seleccionEquipo();
				System.out.println("Soy el Cliente #:" + m.getIndex() + equipo);

			}

			else if (m.getM().equalsIgnoreCase("start")) {
				start = true;

			}

			if (m.getM().equalsIgnoreCase("turno")) {
				ui.setTurno(m.getIndex());
				if (ui.getSelEquipo() == m.getIndex()) {
					turno.trigger();
				}
				System.out.println("Turno" + m.getIndex());
			}

			if (m.getM().equalsIgnoreCase("nuevoMes")) {
				ui.setAcciones(0);
				ui.setBayasTurno(false);
				if (ui.getEstacion() <= 2) {
					ui.setEstacion(m.getIndex());
				} else {
					ui.setEstacion(3);
				}

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
