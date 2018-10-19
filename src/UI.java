import processing.core.PApplet;
import processing.core.PImage;

public class UI {

	private PApplet app;
	private Mundo m;
	private PImage splash, amarilla, nombre;
	private PImage equipoAmarillo, equipoRojo, equipoAzul, equipoVerde;
	private PImage fondoVerano, fondoInvierno, fondoOtono, fondoPrimavera;
	private PImage[] ins, ui;
	private int pantallas, insSwitch, selEquipo, botones, versionesUI, estacion;
	private PImage[] hover;

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
	}

	public void seleccionEquipo() {
		selEquipo = (int) app.random(0, 4);
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
		ins = new PImage[10];
		for (int i = 0; i < ins.length; i++) {
			ins[i] = app.loadImage("../data/ins/ins" + i + ".png");
		}
		// Hover
		hover = m.getCargar().getHover();
		// UI Azul
		ui = new PImage[16];
		for (int i = 0; i < 3; i++) {
			ui[i] = app.loadImage("../data/turnos/blue" + i + ".png");
		}
		// UI Verde
		for (int i = 4; i < 6; i++) {
			for (int j = 0; j < 3; j++) {
				ui[i] = app.loadImage("../data/turnos/green" + j + ".png");
			}
		}
		// UI Roja
		for (int i = 7; i < 9; i++) {
			for (int j = 0; j < 3; j++) {
				ui[i] = app.loadImage("../data/turnos/red" + j + ".png");
			}
		}
		// UI Amarilla
		for (int i = 9; i < 12; i++) {
			for (int j = 0; j < 3; j++) {
				ui[i] = app.loadImage("../data/turnos/yellow" + j + ".png");
			}
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
		case 1:
			app.image(nombre, 0, 0);
			break;
		case 2:
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
		case 3:
			// Estacion
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
			// Version UI
			switch (versionesUI) {
			case 0:
				app.image(ui[0], 0, 0);
				break;
			case 1:
				app.image(ui[1], 0, 0);
				break;
			case 2:
				app.image(ui[2], 0, 0);
				break;
			case 3:
				app.image(ui[3], 0, 0);
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

			break;

		// Final Pantallas
		}
//Final Pintar
	}

	public void click() {
		System.out.println("Pantallas: " + pantallas);
		switch (pantallas) {
		case 1:
			if (app.mouseX > 527 && app.mouseX < 753 && app.mouseY > 447 && app.mouseY < 495) {
				pantallas = 2;
				System.out.println(true);
			}
			break;
		case 2:
			pantallas = 3;
			break;
		case 3:
			if (insSwitch < 10) {
				insSwitch++;
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
			break;
		}
	}

	public void keyReleased() {
		switch (pantallas) {
		case 0:
			if (app.key == app.ENTER) {
				pantallas = 1;
			}
			break;
		}
	}

	// ------GETTERS Y SETTERS-----//
	public int getBotones() {
		return botones;
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
	
//----------FINAL DE LA CLASE UI---------//
}
