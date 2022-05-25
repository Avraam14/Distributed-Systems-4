/*	
 * Avraam Katsigras
 * 	321/2015087
 */

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {												//Main server class

	public static void main(String[] args) {
		try {
			LibraryOperations libop = new LibraryOperations();		//Create remote object
			Registry r = LocateRegistry.createRegistry(1099);		//Initiate object to be used remotely
			Naming.rebind("//localhost/MusicLib", libop);
			System.out.println("Server running...");
		} catch (RemoteException | MalformedURLException e) {
			e.printStackTrace();
		}
	}
}