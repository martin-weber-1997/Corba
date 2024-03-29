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

			POA rootpoa = (POA) orb.resolve_initial_references ("RootPOA");//getting the RootPOA
			rootpoa.the_POAManager().activate();//activating POAManager so the Client can recieve requests
			CallBack callback = CallBackHelper.narrow(rootpoa.servant_to_reference(new PrintCallback()));//Returns IOR
			s.one_time(callback,"one_time: The Server says 'hello'");//one Time Callback
			s.register(callback,"register: hello",(short)1);//Callback until Client fails
			System.out.println("Client: Press Enter to terminate");
			while(System.in.read() != '\n');
			System.out.println("Client terminated");

		}	catch (Exception e)	{
			System.err.println("Es ist ein Fehler aufgetreten: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
