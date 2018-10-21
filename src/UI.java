import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

public class UI implements Observer {

	private PApplet app;
	private Mundo m;
	private PImage splash, amarilla, nombre;
	private PImage equipoAmarillo, equipoRojo, equipoAzul, equipoVerde;
	private PImage fondoVerano, fondoInvierno, fondoOtono, fondoPrimavera;
	private PImage[] ins, ui, uiAm, uiAz, uiV, uiR;
	private int pantallas, insSwitch, selEquipo, botones, versionesUI, estacion, turno, acciones;
	private PImage[] hover;
	private int equipoTemp;
	private boolean turnoterminado, turnoactivado, bayasTurno, pedirArbol, pedirPokemon;
	private PImage turnoBoton, menuBayas;
	private int consumo, bayas, opcionesBayas, bosque;
	private int pokeAddedTurno, treeAddedTurno, bayasAddedTurno;
	private boolean mostrarMenuBayas;
	private int opacity;
	private PFont font;
	private ArrayList<Arbol> arbolesList;

	private ArrayList<Pokemon> pokeList;
	private int pokeType;

	private int totalturnos;

	public UI(PApplet app, Mundo m) {
		this.app = app;
		this.m = m;
		cargar();
		inicializar();
		arbolesList.add(new Arbol(app, 55, 55));
		pokeType = (int) app.random(0, 4);
		pokeList.add(new Pokemon(app, 550, 300, pokeType));

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
		bayasTurno = false;
		pedirArbol = false;
		pedirPokemon = false;
		acciones = 0;
		arbolesList = new ArrayList<>();
		pokeList = new ArrayList<>();
		totalturnos = 1;
		mostrarMenuBayas = false;
		bosque = 250;
		bayas= 20;
		font = app.createFont("../data/fonts/PixelOperator-Bold.ttf", 32);
		app.textFont(font);
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
		// Boton turno
		turnoBoton = app.loadImage("../data/turnos/turnoboton.png");
		// menubayas
		menuBayas = app.loadImage("../data/pedidos/pedidos.png");
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

			for (int i = 0; i < arbolesList.size(); i++) {
				arbolesList.get(i).pintar(estacion);
			}
			for (int i = 0; i < pokeList.size(); i++) {
				pokeList.get(i).pintar();
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
			app.fill(0);

			// bayas del jugador
			app.fill(51, 54, 58);
			app.textSize(35);
			app.text(bayas, 103, 70);
			// bayas del bosque
			app.text(bosque, 246, 70);
			// Consumo
			app.text(consumo, 990, 70);
			// Turno
			app.text(totalturnos, 1128, 70);

		
			if (turno == selEquipo) {
				turnoactivado = true;

			} else {
				turnoactivado = false;
			}

			if (turnoactivado) {

				// actualizar consumo
				consumo = pokeList.size() * 5;

				// Mostrar Menú Bayas
				if (mostrarMenuBayas) {
					app.imageMode(app.CENTER);
					app.image(menuBayas, app.width / 2, app.height / 2);
					app.imageMode(app.CORNER);
				}

				app.tint(255, opacity);
				app.image(turnoBoton, 0, 0);
				app.tint(255, 255);
				// Click Bayas
				if (app.dist(app.mouseX, app.mouseY, 921, 629) < 50) {
					botones = 1;
				}
				// Click Arboles
				else if (app.dist(app.mouseX, app.mouseY, 1027, 629) < 50) {
					botones = 2;
				}
				// Click Pokebola
				else if (app.dist(app.mouseX, app.mouseY, 1132, 629) < 50) {
					botones = 3;
				} else {
					botones = 0;
				}

				if (pedirArbol) {
					if (!arbolesList.isEmpty()) {
						arbolesList.get(arbolesList.size() - 1).setX(app.mouseX);
						arbolesList.get(arbolesList.size() - 1).setY(app.mouseY);
					}

				}

				if (pedirPokemon) {
					if (!pokeList.isEmpty()) {
						pokeList.get(pokeList.size() - 1).setX(app.mouseX);
						pokeList.get(pokeList.size() - 1).setY(app.mouseY);
					}

				}
			}
			// Instrucciones
			if (insSwitch < 10) {
				app.image(ins[insSwitch], 0, 0);
			}

			break;

		/// --------------------JUEGOOOOOOOOOOOOOOOO----------------

		// Final Pantallas
		}
		// Final Pintar

	}

	public void animation() {
		Thread t = new Thread(new Runnable() {

			public void run() {
				if (opacity < 255) {
					for (int i = 0; i < 5; i++) {
						opacity += 51;
					}
					
				} else {
					opacity = 255;
				}
			}
		});
		t.start();
	}

	public void click() {
		// System.out.println("Pantallas: " + pantallas);
		switch (pantallas) {

		case 3:
			if (insSwitch < 11) {
				insSwitch++;
			}

			///// ------------- TURNOOOOOOOO -----------------//////
			if (turnoactivado) {
				// System.out.println("T" + turno + "e" + selEquipo);
				if (app.mouseX > 552 && app.mouseY > 616 && app.mouseX < 800 && app.mouseY < 677) {
					turnoterminado = true;
					m.turnoTerminado();
					totalturnos++;
					turnoactivado = false;
				}

				if (app.mouseX > 55 && app.mouseY > 20 && app.mouseX < 1055 && app.mouseY < 536) {
					if (pedirArbol) {
						arbolesList.get(arbolesList.size() - 1).setX(app.mouseX);
						arbolesList.get(arbolesList.size() - 1).setY(app.mouseY);
						acciones++;
						pedirArbol = false;
					}
					if (pedirPokemon) {
						pokeList.get(pokeList.size() - 1).setX(app.mouseX);
						pokeList.get(pokeList.size() - 1).setY(app.mouseY);
						acciones++;
						pedirPokemon = false;
					}
				}
			}

			break;
		}
	}

	public void addArbol() {
		arbolesList.add(new Arbol(app, app.mouseX, app.mouseY));
		treeAddedTurno++;
	}

	public void addPoke() {
		pokeType = (int) app.random(0, 4);
		pokeList.add(new Pokemon(app, app.mouseX, app.mouseY, pokeType));
		pokeAddedTurno++;

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

	public void pokemonRunAway() {
		pokeList.remove(pokeList.size() - 1);
	}

	// ------GETTERS Y SETTERS-----//
	

	public int getBotones() {
		return botones;
	}

	public int getAcciones() {
		return acciones;
	}

	public void setAcciones(int acciones) {
		this.acciones = acciones;
	}

	public boolean isBayasTurno() {
		return bayasTurno;
	}

	public void setBayasTurno(boolean bayasTurno) {
		this.bayasTurno = bayasTurno;
	}

	public boolean isPedirArbol() {
		return pedirArbol;
	}

	public void setPedirArbol(boolean pedirArbol) {
		this.pedirArbol = pedirArbol;
	}

	public boolean isPedirPokemon() {
		return pedirPokemon;
	}

	public void setPedirPokemon(boolean pedirPokemon) {
		this.pedirPokemon = pedirPokemon;
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

	public int getConsumo() {
		return consumo;
	}

	public void setConsumo(int consumo) {
		this.consumo = consumo;
	}

	public int getBayas() {
		return bayas;
	}

	public void setBayas(int bayas) {
		this.bayas = bayas;
	}

	public int getOpcionesBayas() {
		return opcionesBayas;
	}

	public void setOpcionesBayas(int opcionesBayas) {
		this.opcionesBayas = opcionesBayas;
	}

	public boolean isMostrarMenuBayas() {
		return mostrarMenuBayas;
	}

	public void setMostrarMenuBayas(boolean mostrarMenuBayas) {
		this.mostrarMenuBayas = mostrarMenuBayas;
	}

	public int getNumPokemon() {
		return pokeList.size();
	}

	public int getNumArboles() {
		return arbolesList.size();
	}

	public int getPokeAddedTurno() {
		return pokeAddedTurno;
	}

	public void setPokeAddedTurno(int pokeAddedTurno) {
		this.pokeAddedTurno = pokeAddedTurno;
	}

	public int getTreeAddedTurno() {
		return treeAddedTurno;
	}

	public void setTreeAddedTurno(int treeAddedTurno) {
		this.treeAddedTurno = treeAddedTurno;
	}

	public int getBayasAddedTurno() {
		return bayasAddedTurno;
	}

	public void setBayasAddedTurno(int bayasAddedTurno) {
		this.bayasAddedTurno = bayasAddedTurno;
	}

	public ArrayList<Pokemon> getPokeList() {
		return pokeList;
	}

	public void setPokeList(ArrayList<Pokemon> pokeList) {
		this.pokeList = pokeList;
	}

	public int getBosque() {
		return bosque;
	}

	public void setBosque(int bosque) {
		this.bosque = bosque;
	}

	// ----------FINAL DE LA CLASE UI---------//
}
