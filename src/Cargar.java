import processing.core.PApplet;
import processing.core.PImage;

public class Cargar {

	private PApplet app;
	private PImage[] arboles, estaciones, pokemon, hover;

	public Cargar(PApplet app) {
		this.app = app;
		cargar();
	}

	public void cargar() {
		// Arboles
		arboles = new PImage[4];
		for (int i = 0; i < arboles.length; i++) {
			arboles[i] = app.loadImage("../data/Arboles/Tree" + i + ".png");
		}
		
		//estaciones
		estaciones = new PImage[4];
		for (int i = 0; i < estaciones.length; i++) {
			estaciones[i] = app.loadImage("../data/Fondos/back" + i + ".png");
		}
		
		//pokemon
		pokemon = new PImage[4];
		for (int i = 0; i < pokemon.length; i++) {
			pokemon[i] = app.loadImage("../data/Pokemones/Poke" + i + ".png");
		}
		//hovers
		hover = new PImage[3];
		for (int i = 0; i < hover.length; i++) {
			hover[i] = app.loadImage("../data/hover/hover" + i + ".png");
		}
	}
	
	//---------------GETTERS Y SETTERS----------------//

	

	public PApplet getApp() {
		return app;
	}

	public void setApp(PApplet app) {
		this.app = app;	
	}

	public PImage[] getArboles() {
		return arboles;
	}

	public void setArboles(PImage[] arboles) {
		this.arboles = arboles;
	}

	public PImage[] getEstaciones() {
		return estaciones;
	}

	public void setEstaciones(PImage[] estaciones) {
		this.estaciones = estaciones;
	}

	public PImage[] getPokemon() {
		return pokemon;
	}

	public void setPokemon(PImage[] pokemon) {
		this.pokemon = pokemon;
	}

	public PImage[] getHover() {
		return hover;
	}

	public void setHover(PImage[] hover) {
		this.hover = hover;
	}


	// ---------------FINAL DE LA CLASE CARGAR----------------//
}
