package software_masters.business_planner;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface MyRemote extends Remote {

	public ArrayList<Account> getAccountList() throws RemoteException;
	
	public void setAccountList(ArrayList<Account> accountList) throws RemoteException;
	
	public Template getPlan(int year,Account myAccount) throws IllegalArgumentException, RemoteException;
	
	public void replacePlan(Template newTemp, int year, Account myAccount) throws IllegalArgumentException, RemoteException;;
	
	public void makePlan(String templateName,Account myAccount) throws RemoteException;
	
	public void makePlan(int year, String templateName, Account myAccount) throws RemoteException;
	
	public void addAccount(String name, String password, boolean isAdmin, Account admin) throws IllegalArgumentException, RemoteException;
	
	public void changeEditable(Template temp, boolean edit, Account admin) throws IllegalArgumentException,RemoteException;
	
	public void hello() throws RemoteException;
	
	
}
