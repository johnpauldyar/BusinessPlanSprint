package software_masters.business_planner;
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
			System.out.println("hello");
			Registry registry = LocateRegistry.getRegistry("CS-UNICORN");
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
