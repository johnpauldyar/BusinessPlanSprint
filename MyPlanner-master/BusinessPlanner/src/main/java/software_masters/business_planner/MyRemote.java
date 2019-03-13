package software_masters.business_planner;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface MyRemote extends Remote {

	/**
	 * returns the accountList object
	 * @return ArrayList<Account> accountList
	 * @throws RemoteException
	 */
	public ArrayList<Account> getAccountList() throws RemoteException;
	
	
	/**
	 * 
	 *sets the accountList
	 * @param accountList accountList
	 * @throws RemoteException
	 */
	public void setAccounts(AccountList accountList) throws RemoteException;
	
	/**
	 * Returns the business plan from a given year
	 * 
	 * @param int year
	 * @param Account myAccount
	 * @return Template 
	 * @throws IllegalArgumentException
	 * @throws RemoteException
	 */
	public Template getPlan(int year,Account myAccount) throws IllegalArgumentException, RemoteException;
	
	public void replacePlan(Template newTemp, int year, Account myAccount) throws IllegalArgumentException, RemoteException;;
	
	public void makePlan(String templateName,Account myAccount) throws RemoteException;
	
	public void makePlan(int year, String templateName, Account myAccount) throws RemoteException;
	
	/**
	 * Adds an account to the server if the user adding is an administrator
	 * 
	 * @param String name
	 * @param String password
	 * @param boolean isAdmin
	 * @param Account admin
	 * @throws IllegalArgumentException
	 * @throws RemoteException
	 */
	public void addAccount(String name, String password, boolean isAdmin, Account admin) throws IllegalArgumentException, RemoteException;
	
	public void addAccount(Account account, Account admin) throws IllegalArgumentException, RemoteException;
	
	public void changeEditable(Template temp, boolean edit, Account admin) throws IllegalArgumentException,RemoteException;
	
	public Account getAccount(String username,String password) throws IllegalArgumentException,RemoteException;
	
	/**
	 * Loads the AccountList back from XML
	 * @throws RemoteException
	 */
	public void load() throws RemoteException;
	
}
