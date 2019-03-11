package software_masters.business_planner;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class MyRemoteIMPL extends UnicastRemoteObject implements MyRemote 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2479310677814285137L;


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
	
	private ArrayList<Account> userList;
	private ArrayList<Department> deptList;
	
	
	/**
	 * @param year
	 * @param myAccount
	 * @return the template of said year held by myAccount's department
	 * @throws IllegalArgumentException if there doesn't exist a template with that year
	 */
	public Template getPlan(int year,Account myAccount) throws IllegalArgumentException
	{
		ArrayList<Template> temps = myAccount.getDept().getOldTemplates();
		for(Template x:temps)
		{
			if(x.getYear()==year)
			{
				return x;
			}
		}
		throw new IllegalArgumentException("Template with year not on record.");
	}

	/**
	 * @param newTemp
	 * @param year
	 * @param myAccount
	 * @throws IllegalArgumentException if there doesn't exist a template with that year
	 * replaces the plan with given year in the given user's department to a new, given plan
	 */
	public void replacePlan(Template newTemp, int year, Account myAccount) throws IllegalArgumentException
	{
		ArrayList<Template> temps = myAccount.getDept().getOldTemplates();
		int index=-1;
		for(int i=0;i<temps.size();i++)
		{
			if(temps.get(i).getYear()==year)
			{
				index=i;
			}
		}
		if(index==-1)
		{
			throw new IllegalArgumentException("Template with year not on record.");
		}
		temps.set(index, newTemp);
	}
	
	public void makePlan()
	{
		Calendar cal=Calendar.getInstance();
		cal.setTime(new Date());
		int year=cal.get(Calendar.DAY_OF_YEAR);
		makePlan(year);
	}
	
	public void makePlan(int year)
	{
		
	}
}
