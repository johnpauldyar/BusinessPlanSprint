package software_masters.business_planner;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class MyRemoteProxy implements MyRemote {

	private MyRemoteIMPL server;
	
	public MyRemoteProxy(MyRemoteIMPL server) throws RemoteException
	{
		this.server=server;
	}
	
	public Template getPlan(int year, Account myAccount) throws IllegalArgumentException, RemoteException
	{
		return server.getPlan(year, myAccount);
	}
	
	public void replacePlan(Template newTemp, int year, Account myAccount) throws IllegalArgumentException, RemoteException
	{
		server.replacePlan(newTemp,year,myAccount);
	}
	
	public void makePlan(String templateName, Account myAccount)  throws RemoteException
	{
		server.makePlan(templateName, myAccount);
	}
	
	public void makePlan(int year, String templateName, Account myAccount) throws RemoteException
	{
		server.makePlan(year, templateName, myAccount);
	}
	
	public void addAccount(String name, String password, boolean isAdmin, Account admin) throws IllegalArgumentException, RemoteException
	{
		server.addAccount(name, password, isAdmin, admin);
	}
	
	public void changeEditable(Template temp, boolean edit, Account admin) throws IllegalArgumentException, RemoteException
	{
		server.changeEditable(temp, edit, admin);
	}
	

	public ArrayList<Account> getAccountList() throws RemoteException {
		return server.getAccountList();
	}

	public void setAccountList(ArrayList<Account> accountList) throws RemoteException{
		server.setAccountList(accountList);
	}
	
	public void hello() throws RemoteException
	{
		System.out.println("Works on proxy");
		server.hello();
	}
	

}
