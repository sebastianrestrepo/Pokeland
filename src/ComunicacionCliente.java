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
				System.out.println("Conexión iniciada");
				// -----------Cambiar a IP correspondiente-----------//
				//192.168.0.11
				s = new Socket(InetAddress.getByName("172.30.183.136"), 5000);
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
					System.out.println("Esperando msj");
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
			setChanged();
			notifyObservers(m);
			clearChanged();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
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


	// -----------FINAL DE LA CLASE COMUNICACIÓN CLIENTE---------//
}
