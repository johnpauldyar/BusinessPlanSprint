package software_masters.business_planner;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class MyRemoteProxy implements MyRemote {

	private MyRemoteIMPL server;
	private Account myAccount;
	
	public MyRemoteProxy(MyRemoteIMPL server)
	{
		this.server=server;
	}
	
	public void login(String username, String password)
	{
		ArrayList<Account> users = server.getAccountList();
		for (int i =0; i< users.size(); i++)
		{
			if (users.get(i).getName()==username)
			{
				if (users.get(i).getPassword() == password)
				{
					myAccount = users.get(i);
				}
			}
		}	
	}
	
	public Template getPlan(int year) throws IllegalArgumentException
	{
		return server.getPlan(year, myAccount);
	}
	
	public void replacePlan(Template newTemp, int year) throws IllegalArgumentException
	{
		server.replacePlan(newTemp,year,myAccount);
	}
	
	public void makePlan(String templateName)
	{
		server.makePlan(templateName, myAccount);
	}
	
	public void makePlan(int year, String templateName)
	{
		server.makePlan(year, templateName, myAccount);
	}
	
	public void addAccount(Account admin, String name, String password, boolean isAdmin) throws IllegalArgumentException
	{
		server.addAccount(admin, name, password, isAdmin);
	}
	
	public void changeEditable(Template temp, boolean edit, Account admin) throws IllegalArgumentException
	{
		server.changeEditable(temp, edit, admin);
	}
	public String sayHello() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

}
