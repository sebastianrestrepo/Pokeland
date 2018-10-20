import java.util.Observable;
import java.util.Observer;

import processing.core.PApplet;
import processing.core.PImage;

public class UI implements Observer {

	private PApplet app;
	private Mundo m;
	private PImage splash, amarilla, nombre;
	private PImage equipoAmarillo, equipoRojo, equipoAzul, equipoVerde;
	private PImage fondoVerano, fondoInvierno, fondoOtono, fondoPrimavera;
	private PImage[] ins, ui, uiAm, uiAz, uiV, uiR;
	private int pantallas, insSwitch, selEquipo, botones, versionesUI, estacion, turno;
	private PImage[] hover;
	private int equipoTemp;
	private boolean turnoterminado, turnoactivado;

	public UI(PApplet app, Mundo m) {
		this.app = app;
		this.m = m;
		cargar();
		inicializar();

	}

	public void inicializar() {
		estacion = 0;
		versionesUI = 0;
		botones = 0;
		pantallas = 0;
		insSwitch = 0;
		turno = 0;
		turnoterminado = false;
		turnoactivado = false;
	}

	public void seleccionEquipo() {
		selEquipo = m.getEquipo();
		System.out.println("EQUIPO" + selEquipo);
		versionesUI = selEquipo;
	}

	public void cargar() {
		// Inicio
		splash = app.loadImage("../data/ui/splash.png");
		nombre = app.loadImage("../data/ui/nombre.png");
		amarilla = app.loadImage("../data/ui/amarilla.png");
		// Selección de Equipos
		equipoAmarillo = app.loadImage("../data/equipos/amarillo.png");
		equipoAzul = app.loadImage("../data/equipos/azul.png");
		equipoRojo = app.loadImage("../data/equipos/rojo.png");
		equipoVerde = app.loadImage("../data/equipos/verde.png");
		// Instrucciones
		ins = new PImage[11];
		for (int i = 0; i < ins.length; i++) {
			ins[i] = app.loadImage("../data/ins/ins" + i + ".png");
		}
		// Hover
		hover = m.getCargar().getHover();
		// UI Amarillo
		uiAm = new PImage[4];
		for (int i = 0; i < uiAm.length; i++) {
			uiAm[i] = app.loadImage("../data/turnos/yellow" + i + ".png");
		}
		// UI Roja
		uiR = new PImage[4];
		for (int j = 0; j < uiR.length; j++) {
			uiR[j] = app.loadImage("../data/turnos/red" + j + ".png");
		}

		// UI Verde
		uiV = new PImage[4];
		for (int j = 0; j < uiV.length; j++) {
			uiV[j] = app.loadImage("../data/turnos/green" + j + ".png");
		}

		// UI Azul
		uiAz = new PImage[4];
		for (int j = 0; j < uiAz.length; j++) {
			uiAz[j] = app.loadImage("../data/turnos/blue" + j + ".png");
		}
		// Fondo Primavera
		fondoPrimavera = app.loadImage("../data/Fondos/back0.png");
		// Fondo Verano
		fondoVerano = app.loadImage("../data/Fondos/back1.png");
		// Fondo Otono
		fondoOtono = app.loadImage("../data/Fondos/back2.png");
		// Fondo Invierno
		fondoInvierno = app.loadImage("../data/Fondos/back3.png");
	}

	public void pintar() {
		switch (pantallas) {
		case 0:
			app.image(splash, 0, 0);
			break;
		case 2:
			// PANTALLA DE ASIGNACIÓN DE EQUIPO
			switch (selEquipo) {
			case 0:
				app.image(equipoAmarillo, 0, 0);
				break;
			case 1:
				app.image(equipoRojo, 0, 0);
				break;
			case 2:
				app.image(equipoVerde, 0, 0);
				break;
			case 3:
				app.image(equipoAzul, 0, 0);
				break;
			}
			break;
		/// --------------------JUEGOOOOOOOOOOOOOOOO----------------
		case 3:
			// backgrounds de las estaciones
			switch (estacion) {
			case 0:
				app.image(fondoPrimavera, 0, 0);
				break;
			case 1:
				app.image(fondoVerano, 0, 0);
				break;
			case 2:
				app.image(fondoOtono, 0, 0);
				break;
			case 3:
				app.image(fondoInvierno, 0, 0);
				break;
			}
			// Version UI segun equipo
			switch (versionesUI) {
			case 0:
				app.image(uiAm[turno], 0, 0);
				break;
			case 1:
				app.image(uiR[turno], 0, 0);
				break;
			case 2:
				app.image(uiV[turno], 0, 0);
				break;
			case 3:
				app.image(uiAz[turno], 0, 0);
				break;
			}
			// Botones
			switch (botones) {
			// baya
			case 1:
				app.image(hover[0], 0, 0);
				break;
			// arbol
			case 2:
				app.image(hover[1], 0, 0);
				break;
			// pokebola
			case 3:
				app.image(hover[2], 0, 0);
				break;
			}
			// Instrucciones
			if (insSwitch < 10) {
				app.image(ins[insSwitch], 0, 0);
			}

			// Click Bayas
			if (app.dist(app.mouseX, app.mouseY, 921, 629) < 50) {
				botones = 1;
			}
			// Click Arboles
			if (app.dist(app.mouseX, app.mouseY, 1027, 629) < 50) {
				botones = 2;
			}
			// Click Pokebola
			if (app.dist(app.mouseX, app.mouseY, 1132, 629) < 50) {
				botones = 3;
			}
			if (turno == selEquipo) {
				turnoactivado = true;
			
			} else {
				turnoactivado = false;
			}
			
			if (turnoactivado) {
				app.ellipse(600, 600, 50, 50);
				app.fill(255);
			}
			break;

		/// --------------------JUEGOOOOOOOOOOOOOOOO----------------

		// Final Pantallas
		}
		// Final Pintar

	}

	public void click() {
		System.out.println("Pantallas: " + pantallas);
		System.out.println("InsSwitch: " + insSwitch);
		switch (pantallas) {

		case 3:
			if (insSwitch < 11) {
				insSwitch++;

			}
			
			 if (turnoactivado) {
				System.out.println("T" + turno + "e" + selEquipo);
				if (app.dist(app.mouseX, app.mouseY, 600, 600) < 50) {
					turnoterminado = true;
					m.turnoTerminado();
					turnoactivado = false;
				}
			} 

			// Click Bayas
			else if (app.dist(app.mouseX, app.mouseY, 921, 629) < 50) {
			}
			// Click Arboles
			else if (app.dist(app.mouseX, app.mouseY, 1027, 629) < 50) {
			}
			// Click Pokebola
			else if (app.dist(app.mouseX, app.mouseY, 1132, 629) < 50) {
			}

			
			break;
		}
	}

	public boolean isTurnoactivado() {
		return turnoactivado;
	}

	public void setTurnoactivado(boolean turnoactivado) {
		this.turnoactivado = turnoactivado;
	}

	public void keyReleased() {
		switch (pantallas) {
		case 0:
			if (app.key == app.ENTER) {
				pantallas = 2;
			}
			break;
		}
	}

	@Override
	public void update(Observable o, Object arg) {

		if (o instanceof ComunicacionCliente) {
			Mensaje m = (Mensaje) arg;

			if (m.getM().equalsIgnoreCase("equipo")) {
				equipoTemp = m.getIndex() - 1;
				System.out.println("Soy el Cliente #:" + m.getIndex() + selEquipo);
			}

		}

	}

	// ------GETTERS Y SETTERS-----//

	public int getBotones() {
		return botones;
	}

	public boolean isTurnoterminado() {
		return turnoterminado;
	}

	public void setTurnoterminado(boolean turnoterminado) {
		this.turnoterminado = turnoterminado;
	}

	public void setBotones(int botones) {
		this.botones = botones;
	}

	public int getPantallas() {
		return pantallas;
	}

	public void setPantallas(int pantallas) {
		this.pantallas = pantallas;
	}

	public int getSelEquipo() {
		return selEquipo;
	}

	public void setSelEquipo(int selEquipo) {
		this.selEquipo = selEquipo;
	}

	public PImage[] getIns() {
		return ins;
	}

	public void setIns(PImage[] ins) {
		this.ins = ins;
	}

	public int getInsSwitch() {
		return insSwitch;
	}

	public void setInsSwitch(int insSwitch) {
		this.insSwitch = insSwitch;
	}

	public int getVersionesUI() {
		return versionesUI;
	}

	public void setVersionesUI(int versionesUI) {
		this.versionesUI = versionesUI;
	}

	public int getEstacion() {
		return estacion;
	}

	public void setEstacion(int estacion) {
		this.estacion = estacion;
	}

	public int getTurno() {
		return turno;
	}

	public void setTurno(int turno) {
		this.turno = turno;
	}

	// ----------FINAL DE LA CLASE UI---------//
}
