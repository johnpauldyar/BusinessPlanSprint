package software_masters.business_planner;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class AccountList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2968753611653978290L;

	
	public ArrayList<Account> accounts;
	
	/**
	 *Empty initialization of the AccountList, for serialization 
	 */
	public AccountList()
	{
		accounts=null;
		
	}
	/**
	 * initializer
	 * 
	 * @param ArrayList<Account> accounts
	 */
	public AccountList(ArrayList<Account> accounts)
	{
		this.accounts=accounts;
	}
	
	/**
	 * returns the accounts object
	 * 
	 * @return ArrayList<Account accounts
	 */
	public ArrayList<Account> getAccounts() 
	{
		return accounts;
	}
	
	/**
	 * sets the parameter to the attribute accounts 
	 * @param ArrayList<Account> accounts
	 * 
	 */
	public void setAccounts(ArrayList<Account> accounts)
	{
		
		this.accounts = accounts;
	}
	
	/**
	 * Saves the array of accounts to an xml file
	 */
	public void save()
	{
		String filename="AccountXMLFile";
		XMLEncoder encoder = null;
		try
		{
			encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(filename)));
		} catch (FileNotFoundException fileNotFound)
		{
			System.out.println("ERROR: While Creating or Opening the File " + filename);
		}
		encoder.writeObject(accounts);
		encoder.close();
	}
}
