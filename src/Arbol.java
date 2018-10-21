import processing.core.PApplet;
import processing.core.PImage;

public class Arbol {
	private PImage[] arboles;
	private PApplet app;
	private float x, y;
	private int estacion;

	public Arbol(PApplet app, float x, float y) {
		this.app = app;
		this.x = x;
		this.y = y;
		// Arboles
		arboles = new PImage[4];
		for (int j = 0; j < arboles.length; j++) {
			arboles[j] = app.loadImage("../data/Arboles/Tree" + j + ".png");
		}
	}

	public void pintar(int estacion) {
		this.estacion = estacion;
		app.image(arboles[estacion], x, y);
	}

	public PImage[] getArboles() {
		return arboles;
	}

	public void setArboles(PImage[] arboles) {
		this.arboles = arboles;
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

	public int getEstacion() {
		return estacion;
	}

	public void setEstacion(int estacion) {
		this.estacion = estacion;
	}

}
