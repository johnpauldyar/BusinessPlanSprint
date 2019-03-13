package software_masters.business_planner;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class MyRemoteClient 
{
	MyRemote stub;
	Account myAccount;
	
	/**
	 * Initializer
	 */
	public MyRemoteClient()
	{
		try {
			
		Registry registry = LocateRegistry.getRegistry("10.14.1.80");
		stub = (MyRemote) registry.lookup("RemoteHello");
		}
		
		catch(Exception ex)
		{
			System.out.println("Have you tried running the rmiRegistry in the target folder?");
			ex.printStackTrace();

		}
	}


	/**
	 * Allows the client to verify its login credentials
	 * 
	 * @param String username
	 * @param String password
	 */
	public void login(String username,String password)
	{
		try
		{
			this.myAccount=stub.getAccount(username, password);
		}
		catch (Exception ex)
		{
			System.out.println("Error logging in");
			ex.printStackTrace();
		}
	}
	
	/**
	 * Returns the business plan of the given year
	 * 
	 * @param int year
	 * @return Template plan
	 * @throws RemoteException
	 */
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
	
	
	/**
	 * allows the client to submit an editted plan to the server
	 * 
	 * @param Template temp
	 * @param int year
	 */
	public void editPlan(Template temp, int year)
	{
		try
		{
			stub.replacePlan(temp, year, myAccount);
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
	
	/**
	 * Lets the client create a new "blank" business plan
	 * 
	 * @param String name
	 * @return Template
	 */
	public Template newPlan(String name)
	{
		try
		{
			stub.makePlan(name, myAccount);
			return stub.getPlan(2019, myAccount);
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		return null;
	}
	
	
	/**
	 * Let's the user create a new business plan using a previous one as a starter
	 * 
	 * @param String name
	 * @param int year
	 * @return Template
	 */
	public Template newPlan(String name,int year)
	{
		try
		{
			stub.makePlan(year, name, myAccount);
			return stub.getPlan(year, myAccount);
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		return null;
	}
	
	
	/**
	 * 
	 * Allows an administrator to add new users
	 * @param String username
	 * @param String password
	 * @param Boolean admin
	 */
	public void addUser(String username,String password,boolean admin)
	{
		try
		{
			stub.addAccount(username,password,admin,myAccount);
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
	
	
	/**
	 * Allows an administrator to toggle a template's editableness
	 * 
	 * @param Template temp
	 * @param boolean edit
	 */
	public void changeEditable(Template temp, boolean edit)
	{
		try
		{
			stub.changeEditable(temp,edit,myAccount);
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
		
	
	
	
	

}
