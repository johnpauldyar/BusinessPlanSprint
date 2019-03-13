package software_masters.business_planner;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class MyRemoteClient 
{
	MyRemote stub;
	Account myAccount;
	
	public static void main(String [] args) throws RemoteException
	{
		MyRemoteClient helper = new MyRemoteClient();
		helper.stub.hello();
		
	}
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
		try
		{
		ArrayList<Account> acc=stub.getAccountList();
		for(Account x:acc)
		{
			if(x.getName().equals(username)&&x.getPassword().equals(password))
			{
				myAccount=x;
			}
		}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public Template viewPlan(int year) throws RemoteException
	{
		try
		{
		
			return stub.getPlan(year, myAccount);
		}
		catch (Exception ex)
		{
			System.out.println(ex);
			
		}
		return null;
	}
		
	
	
	
	

}
