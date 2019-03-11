import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class MyRemoteIMPL extends UnicastRemoteObject implements MyRemote {

	public MyRemoteIMPL() throws RemoteException
	{
		super();
	}
	
	public String sayHello() throws RemoteException
	{

		
		return "hello";
	}
	
	public static void main(String [] args) throws RemoteException
	{
		System.out.println(args[0]);
		try
		{
			System.out.println("1");
			MyRemote service = new MyRemoteIMPL();
			System.out.println("2");
			//MyRemote stub = (MyRemote) UnicastRemoteObject.exportObject(service,0);
			System.out.println("3");
			Registry registry = LocateRegistry.getRegistry();
			System.out.println("4");
			registry.rebind("RemoteHello",service);
			System.out.println("5");
		}
		catch(Exception ex)
		{
			System.out.println("no");
			System.out.println(ex);
		}
		System.out.println(args[0]);
	}
}
