import java.io.IOException;
import java.util.Observable;
import java.util.Observer;import javax.management.BadBinaryOpValueExpException;

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
				if (!ui.isBayasTurno()) {
					// Click Bayas
					if (app.dist(app.mouseX, app.mouseY, 921, 629) < 50) {
						button.trigger();
						ui.setMostrarMenuBayas(true);
					}
					// UI BAYAS
					if (ui.isMostrarMenuBayas()) {
						int consumoexacto =  ui.getConsumo();
						if (app.mouseX > 421 && app.mouseX < 586 && app.mouseY > 278 && app.mouseY < 356) {
							int tempPedido = consumoPedido(consumoexacto);
							ui.setBayas( ui.getBayas() + tempPedido);
							ui.setBayasAddedTurno(ui.getConsumo());
							ui.setMostrarMenuBayas(false);
							System.out.println("Chao, gracias");
							ui.setBayasTurno(true);

		}

						if (app.mouseX > 615 && app.mouseX < 781 && app.mouseY > 278 && app.mouseY < 356) {
							int tempPedido = consumoPedido(consumoexacto + (int)(ui.getConsumo() / 2));
							ui.setBayas(ui.getBayas() + tempPedido);
							ui.setBayasAddedTurno(ui.getConsumo() + (int)(ui.getConsumo() / 2));
							ui.setMostrarMenuBayas(false);
							ui.setBayasTurno(true);

						}

						if (app.mouseX > 417 && app.mouseX < 584 && app.mouseY > 382 && app.mouseY < 456) {
							int tempPedido = consumoPedido(consumoexacto*2 + consumoexacto/2);
							ui.setBayas(tempPedido);
							ui.setBayasAddedTurno((ui.getConsumo() * 2) + (int)(ui.getConsumo() / 2));
							ui.setMostrarMenuBayas(false);
							ui.setBayasTurno(true);

						}

						if (app.mouseX > 613 && app.mouseX < 782 && app.mouseY > 382 && app.mouseY < 456) {
							int tempPedido = consumoPedido(consumoexacto*3);
							ui.setBayas(ui.getBayas() + tempPedido);
							ui.setBayasAddedTurno((ui.getConsumo() * 3));
							ui.setMostrarMenuBayas(false);
							ui.setBayasTurno(true);
						}

					}
				} else if (ui.isBayasTurno() && app.dist(app.mouseX, app.mouseY, 921, 629) < 50) {
					error.trigger();
				}

				// Click Arboles
				if (app.dist(app.mouseX, app.mouseY, 1027, 629) < 50) {
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
			}
			break;

		}

	}
	private int consumoPedido(int consumoPedido) { 
		if (ui.getBosque() < consumoPedido) {
			int faltante = consumoPedido - ui.getBosque();
			int consumoDado =  consumoPedido - faltante;
			ui.setBosque(0);
			return consumoDado;
		} else {
			int consumoDado = consumoPedido;
			return consumoDado;
		}
	}
	public void turnoTerminado() {
		int tempTaken = ui.getBayasAddedTurno();
		System.out.println("Bayas tomadas" + tempTaken);
		calcularPokemonAlimentado();
		if (!ui.isBayasTurno()) {
			ui.setBayas(ui.getBayas());
			ui.setBayasAddedTurno(0);
		}
		cc.enviarMensaje(new Mensaje("turnoterminado", tempTaken , ui.getSelEquipo(),
				ui.getBayas() + "," + ui.getNumArboles() + "," + ui.getNumPokemon() + "," + ui.getBayasAddedTurno()
						+ "," + ui.getTreeAddedTurno() + "," + ui.getPokeAddedTurno()));
		ui.setBayasAddedTurno(0);
		ui.setPokeAddedTurno(0);
		ui.setBayasAddedTurno(0);
		System.out.println("turnoterminado" + ui.getBayas() + "," + ui.getNumArboles() + ", " + ui.getNumPokemon() + ","
				+ ui.getBayasAddedTurno() + "," + ui.getTreeAddedTurno() + "," + ui.getPokeAddedTurno());

	}

	private void calcularPokemonAlimentado() {
		if (ui.getBayas() < ui.getConsumo()) {
			int pokeVivos = ui.getBayas() / 5;
			int pokeEscape = ui.getNumPokemon() - pokeVivos;
			for (int j = 0; j < pokeEscape; j++) {
				ui.pokemonRunAway();
			}
			ui.setConsumo(ui.getNumPokemon()*5);
			ui.setBayas(ui.getBayas() - ui.getConsumo());
		} else {
			ui.setConsumo(ui.getNumPokemon()*5);
			ui.setBayas(ui.getBayas() - ui.getConsumo());
		}
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

			String[] tempM = PApplet.splitTokens(m.getM(), ",");

			if (m.getM().equalsIgnoreCase("turno")) {
				ui.animation();
				ui.setBosque(Integer.parseInt(m.getValor()));
				ui.setTurno(m.getIndex());
				if (ui.getSelEquipo() == m.getIndex()) {
					turno.trigger();
				}
				System.out.println("Turno" + m.getIndex());
			}

			if (m.getM().equalsIgnoreCase("turnoMes")) {
				ui.setAcciones(0);
				ui.setBayasTurno(false);
				if (ui.getEstacion() <= 2) {
					ui.setEstacion(m.getEquipo());
				} else {
					ui.setEstacion(3);
				}
				ui.setBosque(Integer.parseInt(m.getValor()));
				return;
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

	public void run() {
		// TODO Auto-generated method stub

	}

	// ----------------FINAL DE LA CLASE MUNDO------------//
}
