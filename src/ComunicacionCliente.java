import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Observable;

public class ComunicacionCliente extends Observable implements Runnable {

	private Socket s;
	private ObjectInputStream entrada;
	private ObjectOutputStream salida;
	private boolean conectado;

	public ComunicacionCliente() {

	}

	@Override
	public void run() {
		if (!conectado) {

			try {
				System.out.println("Conexi�n iniciada");
				// -----------Cambiar a IP correspondiente-----------//
				s = new Socket(InetAddress.getByName("192.168.0.11"), 5000);
				salida = new ObjectOutputStream(s.getOutputStream());
				entrada = new ObjectInputStream(s.getInputStream());
				System.out.println("Flujos enlazados");
				conectado = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		while (true) {
			try {
				if (s.isConnected()) {
					recibirMensaje();
				}
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void recibirMensaje() {
		try {
			Mensaje m = (Mensaje) entrada.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		setChanged();
		notifyObservers("I");
		clearChanged();
	}
	
    public void enviarMensaje(final Object obj){

        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {

                try {
                    Mensaje m = (Mensaje) obj;
                    if(conectado) {
                        if (s.isConnected()) {
                            salida.writeObject(m);
                            salida.flush();
                            System.out.println("Mensaje enviado: " + m);
                        }
                    }
                } catch (IOException e) {

                    e.printStackTrace();
                }

            }

        });
        t.start();
    }


	// -----------FINAL DE LA CLASE COMUNICACI�N CLIENTE---------//
}
