import processing.core.PApplet;

public class PokelandMain extends PApplet {

	private Mundo m;

	public static void main(String[] args) {
		PApplet.main("PokelandMain");
	}

	@Override
	public void settings() {
		size(1200, 700);
	}

	@Override
	public void setup() {
		m = new Mundo(this);
	}

	@Override
	public void draw() {
		m.pintar();
	}

	@Override
	public void mouseClicked() {
	//	System.out.println("click: " + mouseX + ", " + mouseY);
		m.click();
	}
	
	@Override
	public void keyReleased() {
		m.keyKeleased();
	}

	@Override
	public void mouseDragged() {

	}

}
