package software_masters.business_planner;
import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

/**
 * @author john.dyar
 *
 */
/**
 * @author john.dyar
 *
 */
public class MyRemoteIMPL extends UnicastRemoteObject implements MyRemote 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2479310677814285137L;
	private AccountList accountList;


	public MyRemoteIMPL() throws RemoteException
	{
		super();
	}
	
	public void setAccounts(AccountList accountList) throws RemoteException
	{
		this.accountList=accountList;
	}
	

	
	public void load() throws RemoteException
	{
		try
		{
		setAccounts(accountLoad());
		}
		catch(Exception ex)
		{
			System.out.println(ex);
		}
	}
	
	public Account getAccount(String username,String password) throws IllegalArgumentException,RemoteException
	{
		for(Account x:accountList.getAccounts())
		{
			if(x.getName().equals(username))
			{
					if( x.getPassword().equals(password))
					{
						return x;
					}
			}
		}
		throw new IllegalArgumentException();
	}
	
	
	
	

	public ArrayList<Account> getAccountList() throws RemoteException 
	{
		try 
		{
			return accountList.getAccounts();
		}
		catch (Exception ex)
		{
			System.out.println(ex);
			
		}
		return null;
	}

	/**
	 * @param year
	 * @param myAccount
	 * @return the template of said year held by myAccount's department
	 * @throws IllegalArgumentException if there doesn't exist a template with that year
	 */
	public Template getPlan(int year,Account myAccount) throws IllegalArgumentException
	{
		try
		{
		ArrayList<Template> temps = getAccount(myAccount.getName(),myAccount.getPassword()).getDept().getOldTemplates();
		for(Template x:temps)
		{
			if(x.getYear()==year)
			{
				return x;
			}
		}
		throw new IllegalArgumentException("Template with year not on record.");
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return null;
	}

	/**
	 * @param newTemp
	 * @param year
	 * @param myAccount
	 * @throws IllegalArgumentException 
	 * 
	 * if there doesn't exist a template with that year
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
		if(year!=newTemp.getYear())
		{
			newTemp.setYear(year);
		}
		if (temps.get(index).isEditable())
		{
			myAccount.getDept().getOldTemplates().set(index, newTemp);
		}
		else
		{
			throw new IllegalArgumentException("This plan is not editable.");
		}
		accountList.save();

	}
	
	
	/**
	 * @param String templateName
	 * @param Account myAccount 
	 * 
	 * Creates an new plan with the current year as the default
	 */
	public void makePlan(String templateName,Account myAccount)
	{
		try
		{
		Template model=myAccount.getDept().getModel();
		TemplateSection root=model.getRoot().deepCopy();
		Template newTemp=new Template(model.getDeveloperTemplateName(), templateName, root, 2019, true);
		this.getAccount(myAccount.getName(),myAccount.getPassword()).getDept().addNewTemplate(newTemp);
		accountList.save();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	/**
	 * @param int year
	 * @param String templateName
	 * @param Account myAccount
	 * 
	 * Creates a new plan with the given year
	 */
	public void makePlan(int year, String templateName, Account myAccount)
	{
		try
		{
		Template newtemp=this.getPlan(year, myAccount);
		TemplateSection root=newtemp.getRoot().deepCopy();
		Template returned=new Template(newtemp.getUserTemplateName(),templateName,root,year,true);
		this.getAccount(myAccount.getName(),myAccount.getPassword()).getDept().addNewTemplate(returned);
		accountList.save();
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
	
	/**
	 * @param Account admin
	 * @param String name
	 * @param String password
	 * @param boolean isAdmin
	 * @throws IllegalArgumentException
	 * 
	 * adds a new account in the department of the administrator adding the account
	 */
	public void addAccount(String name, String password, boolean isAdmin, Account admin) throws IllegalArgumentException
	{
		if(admin.isAdmin())
		{
			Department dept=admin.getDept();
			Account acc=new Account(name, password, dept, isAdmin);
			accountList.getAccounts().add(acc);
		}
		else
		{
			throw new IllegalArgumentException("You are not an Administrator");
			
		}
		accountList.save();

	}
	
	

	public void addAccount(Account account, Account admin) throws IllegalArgumentException, RemoteException
	{
		if(admin.isAdmin())
		{
			accountList.getAccounts().add(account);
		}
		else
		{
			throw new IllegalArgumentException("You are not an Administrator");
			
		}
		accountList.save();

	}
	
	/**
	 * @param Template temp
	 * @param boolean edit
	 * @param Account admin
	 * @throws IllegalArgumentException
	 * 
	 * Allows an administrator to make a plan editable or not
	 */
	public void changeEditable(Template temp, boolean edit, Account admin) throws IllegalArgumentException
	{
		if(admin.isAdmin())
		{
			temp.setEditable(edit);
		}
		else
		{
			throw new IllegalArgumentException("You are not an Administrator");
			
		}
		accountList.save();

		
	}
	
	/**
	 * A private helper method that loads from an XML file
	 * @return AccountList account
	 */
	private static AccountList accountLoad()
	{
		XMLDecoder decoder = null;
		try
		{
			decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream("AccountXMLFile")));
		} catch (Exception e)
		{
			System.out.println(e);
		}
		AccountList accounts=new AccountList((ArrayList<Account>) decoder.readObject());
		return accounts;
		
	}
	
	

	
}
