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
		try
		{
			MyRemote service = new MyRemoteIMPL();
			//MyRemote stub = (MyRemote) UnicastRemoteObject.exportObject(service,0);
			Registry registry = LocateRegistry.getRegistry();
			registry.rebind("RemoteHello",service);
		}
		catch(Exception ex)
		{
			System.out.println("Error in connecting to registry");
			System.out.println(ex);
		}
	}
}
