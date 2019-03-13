package software_masters.business_planner;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

import junit.framework.Assert;
import junit.framework.TestCase;

public class ServerTest extends TestCase
{
	public void createservice() throws RemoteException
	{
		MyRemote service = new MyRemoteIMPL();
		Registry registry;
		try
		{
			//MyRemote stub = (MyRemote) UnicastRemoteObject.exportObject(service,0);
			registry = LocateRegistry.createRegistry(1099);
			registry.rebind("RemoteHello",service);
		}
		catch(Exception ex)
		{
			try 
			{
				registry = LocateRegistry.getRegistry();
				registry.rebind("RemoteHello",service);
				
			}
			catch (Exception ex2)
			{
				System.out.println("Error in connecting to registry");
				System.out.println(ex2);
			}
		}
		
		
		//Stolen from template tester
		Template userVMOSA = VMOSA_builder.generateTemplate();
		userVMOSA.setUserTemplateName("MyBusinessPlan");
		

		TemplateSection vision = userVMOSA.getRoot();
		TemplateSection mission = vision.getChild(0);
		TemplateSection objective = mission.getChild(0).deepCopy();
		mission.addChild(objective);
		objective.setParent(mission);
		
		Template model=VMOSA_builder.generateTemplate();
		Department dept=new Department(model);
		userVMOSA.setEditable(true);
		userVMOSA.setYear(5);
		dept.addNewTemplate(userVMOSA);
		dept.addNewTemplate(OKR.generateTemplate());
		dept.addNewTemplate(Centre_Assess_Builder.generateTemplate());
		
		Template model2=OKR.generateTemplate();
		Department dept2=new Department(model2);
		dept.addNewTemplate(Centre_Assess_Builder.generateTemplate());
		dept.addNewTemplate(model2);
		
		
		Account admin=new Account("admin","you shall not x - word",dept,true);
		Account one=new Account("one","password",dept,false);
		Account two=new Account("two","passcode",dept,false);
		Account admin2=new Account("admin2","hihocherrio",dept2,true);
		Account three=new Account("three","pas",dept2,false);
		ArrayList<Account> accounts=new ArrayList<Account>();
		accounts.add(admin);
		accounts.add(admin2);
		accounts.add(one);
		accounts.add(three);
		service.setAccountList(accounts);
		service.addAccount("two", "password", false, admin);
		accounts.add(two);
		assertEquals(two.getName(),service.getAccount("two","password").getName());
		assertEquals(two.getPassword(),service.getAccount("two", "password"));
		service.addAccount(three, admin2);
		assertEquals(three,service.getAccount("three", "pas"));
		
		
		
	}
}
