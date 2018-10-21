import processing.core.PApplet;
import processing.core.PImage;

public class Pokemon {
	private PImage[] pokemon;
	private PApplet app;
	private float x, y;
	private int pokemonType;

	public Pokemon(PApplet app, float x, float y, int pokemonType) {
		this.app = app;
		this.x = x;
		this.y = y;
		this.pokemonType = pokemonType;

		// Pokemon
		pokemon = new PImage[4];
		for (int j = 0; j < pokemon.length; j++) {
			pokemon[j] = app.loadImage("../data/Pokemones/Poke" + j + ".png");
		}
	}

	public void pintar() {

		app.image(pokemon[pokemonType], x, y);
	}

	public PImage[] getPokemon() {
		return pokemon;
	}

	public void setPokemon(PImage[] pokemon) {
		this.pokemon = pokemon;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public int getPokemonType() {
		return pokemonType;
	}

	public void setPokemonType(int pokemonType) {
		this.pokemonType = pokemonType;
	}
	
	
}
