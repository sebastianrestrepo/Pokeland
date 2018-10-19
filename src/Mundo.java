import processing.core.PApplet;

public class Mundo {

	private PApplet app;
	private Cargar cargar;
	private UI ui;
	private ComunicacionCliente cc;

	public Mundo(PApplet app) {
		this.app = app;
		cargar = new Cargar(app);
		ui = new UI(app, this);
		cc = new ComunicacionCliente();
		new Thread(cc).start();
	}

	public void pintar() {
		ui.pintar();
	}

	public void click() {
		ui.click();
		cc.enviarMensaje(new Mensaje("Holap"));
	}

	public void keyKeleased() {
		ui.keyReleased();
	}

	// ----------------GETTERS Y SETTERS------------//
	public Cargar getCargar() {
		return cargar;
	}

	public void setCargar(Cargar cargar) {
		this.cargar = cargar;
	}

	// ----------------FINAL DE LA CLASE MUNDO------------//
}
