package software_masters.business_planner;
import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

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
	private ArrayList<Account> accountList;

	public MyRemoteIMPL() throws RemoteException
	{
		super();
	}
	
	public Account getAccount(String username,String password) throws IllegalArgumentException,RemoteException
	{
		for(Account x:accountList)
		{
			if(x.getName().equals(username)&&x.getPassword().equals(password));
			{
				return x;
			}
		}
		throw new IllegalArgumentException();
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
	
	
	
	
	public ArrayList<Account> getAccountList() throws RemoteException 
	{
		try 
		{
			return accountList;
		}
		catch (Exception ex)
		{
			System.out.println(ex);
			
		}
		return null;
	}

	public void setAccountList(ArrayList<Account> accountList) throws RemoteException
	{
		this.accountList = accountList;
	}

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
		if (temps.get(index).isEditable())
		{
			temps.set(index, newTemp);
		}
		else
		{
			throw new IllegalArgumentException("This plan is not editable.");
		}
		save();

	}
	
	
	/**
	 * @param String templateName
	 * @param Account myAccount 
	 * 
	 * Creates an new plan with the current year as the default
	 */
	public void makePlan(String templateName,Account myAccount)
	{
		Calendar cal=Calendar.getInstance();
		cal.setTime(new Date());
		int year=cal.get(Calendar.DAY_OF_YEAR);
		makePlan(year, templateName, myAccount);
		save();
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
		Template model=myAccount.getDept().getModel();
		TemplateSection root=model.getRoot().deepCopy();
		Template newTemp=new Template(model.getDeveloperTemplateName(), templateName, root, year, true);
		myAccount.getDept().addNewTemplate(newTemp);
		save();
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
			accountList.add(acc);
		}
		else
		{
			throw new IllegalArgumentException("You are not an Administrator");
			
		}
		save();
	}
	public void addAccount(Account account, Account admin) throws IllegalArgumentException, RemoteException
	{
		if(admin.isAdmin())
		{
			accountList.add(account);
		}
		else
		{
			throw new IllegalArgumentException("You are not an Administrator");
			
		}
		save();
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
		save();
		
	}
	
	public void login(String username, String password)
	{
		
	}
	
	public void save()
	{
		String filename="ServerXMLFile";
		XMLEncoder encoder = null;
		try
		{
			encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(filename)));
		} catch (FileNotFoundException fileNotFound)
		{
			System.out.println("ERROR: While Creating or Opening the File " + filename);
		}
		encoder.writeObject(accountList);
		encoder.close();
	}
}
