import processing.core.PApplet;

public abstract class UI {

	private PApplet app;
	private Mundo m;
	
	public UI(PApplet app, Mundo m) {
		this.app = app;
		this.m = m;
	}
	
}
