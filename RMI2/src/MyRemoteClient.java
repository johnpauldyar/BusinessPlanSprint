import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MyRemoteClient {
	public static void main(String [] args)
	{
		new MyRemoteClient().go();
	}
	public void go()
	{
		
		try
		{
			Registry registry = LocateRegistry.getRegistry("127.0.0.1");
			MyRemote stub = (MyRemote) registry.lookup("RemoteHello");
			
			String s = stub.sayHello();
			System.out.println(s);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("problem");
		}
	}
}
