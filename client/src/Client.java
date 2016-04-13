package client;

import cb.*;
import org.omg.CosNaming.*;
import org.omg.CORBA.*;
import org.omg.CORBA.Object;
import org.omg.PortableServer.*;

/**
 * @author Hagen Aad Fock <hagen.fock@gmail.com>
 * @version 13.03.2015
 * 
 * Ruft die Echo Methode des C++ Servers auf und gibt einen String auf der Konsole aus.
 * Sollte ein Fehler aufgetreten sein, so wird eine Exception geworfen und eine Fehlermeldung zusammen mit dem Stracktrace auf der Konsole ausgegeben.
 */
public class Client {
	public static void main(String[] args)  {
		Server s;
		try {

			/* Erstellen und intialisieren des ORB */
			ORB orb = ORB.init(args, null);

			/* Erhalten des RootContext des angegebenen Namingservices */
			Object o = orb.resolve_initial_references("NameService");

			/* Verwenden von NamingContextExt */
			NamingContextExt rootContext = NamingContextExtHelper.narrow(o);

			/* Angeben des Pfades zum Echo Objekt */
			NameComponent[] name = new NameComponent[2];
			name[0] = new NameComponent("test","my_context");
			name[1] = new NameComponent("Echo", "Object");

			/* Aufloesen der Objektreferenzen  */
			s = ServerHelper.narrow(rootContext.resolve(name));

			POA root_poa = (POA) orb.resolve_initial_references ("RootPOA");
			root_poa.the_POAManager().activate();
			CallBack callback = CallBackHelper.narrow(root_poa.servant_to_reference(new PrintCallback()));
			s.one_time(callback,"one_time: The Server says 'hello'");
			s.register(callback,"register: hello",(short)1);
			System.out.println("Client: running; waiting for callback execution; press [Enter] to terminate");
			while(System.in.read() != '\n');
			System.out.println("Client: terminated");

		}	catch (Exception e)	{
			System.err.println("Es ist ein Fehler aufgetreten: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
