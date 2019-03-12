package software_masters.business_planner;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MyRemoteClient 
{
	MyRemote stub;
	String username;
	String password;
	public MyRemoteClient()
	{
		try {
			
		Registry registry = LocateRegistry.getRegistry("127.0.0.1");
		stub = (MyRemote) registry.lookup("RemoteHello");
		}
		
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("problem");
		}
	}
	
	public void login(String username,String password)
	{
		this.username=username;
		this.password=password;
		//stub.login()
	}
	public Template viewPlan(int year,String username,String password)
	{
		
	}
	
	
	
	

}
