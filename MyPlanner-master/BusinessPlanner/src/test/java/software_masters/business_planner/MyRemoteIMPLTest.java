package software_masters.business_planner;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

import org.junit.*;

import junit.framework.Assert;
import junit.framework.TestCase;

public class MyRemoteIMPLTest extends TestCase
{
	@Test
	public void testCreateService() throws RemoteException
	{
		
		//creates or locates the RMI server
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
				registry = LocateRegistry.getRegistry("10.14.1.80");
				registry.rebind("RemoteHello",service);
				
			}
			catch (Exception ex2)
			{
				System.out.println("Error in connecting to registry");
				System.out.println(ex2);
			}
		}
		
		
		//Creates a VMOSA user template, not a developer template
		Template userVMOSA = VMOSA_builder.generateTemplate();
		userVMOSA.setUserTemplateName("MyBusinessPlan");
		TemplateSection vision = userVMOSA.getRoot();
		TemplateSection mission = vision.getChild(0);
		TemplateSection objective = mission.getChild(0).deepCopy();
		mission.addChild(objective);
		objective.setParent(mission);
		userVMOSA.setEditable(true);
		//Creating model template as per VMOSA_builder
		Template model=VMOSA_builder.generateTemplate();
		//Creates a new department with the model template
		Department dept=new Department(model);
		
		//Creates several user templates from different models
		Template tone=OKR.generateTemplate();
		Template ttwo=Centre_Assess_Builder.generateTemplate();
		
		Template tthree=Centre_Assess_Builder.generateTemplate();
		userVMOSA.setYear(1);
		tone.setYear(2);
		ttwo.setYear(3);
		tthree.setYear(4);
		
		dept.addNewTemplate(userVMOSA);
		dept.addNewTemplate(tone);
		dept.addNewTemplate(ttwo);
		dept.addNewTemplate(tthree);
		
		//creates a second department with the same method as the first
		Template model2=OKR.generateTemplate();
		model2.setYear(6);
		Department dept2=new Department(model2);
		Template x=Centre_Assess_Builder.generateTemplate();
		x.setYear(2018);
		dept.addNewTemplate(x);
		dept.addNewTemplate(model2);
		
		//the server holds an array of templates
		ArrayList<Template> temps=new ArrayList<Template>();
		temps.add(userVMOSA);
		temps.add(tone);
		temps.add(ttwo);
		temps.add(tthree);
		
		//Creates a list of several users
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
		
		//Puts the list of templates and users into the server
		AccountList accountList=new AccountList(accounts);
		service.setAccounts(accountList);
		
		//addAcount() testing
		service.addAccount("two", "passcode", false, admin);
		accounts.add(two);
		Assert.assertEquals(two.getName(),service.getAccount("two","passcode").getName());
		Assert.assertEquals(two.getPassword(),service.getAccount("two", "passcode").getPassword());
		service.addAccount(three, admin2);
		Assert.assertEquals(three,service.getAccount("three", "pas"));
		

		
		//getPlan testing
		Assert.assertEquals(userVMOSA,service.getPlan(1, one));
		
		
		//replacePlan testing
		service.changeEditable(ttwo,true,admin);
		dept2.addNewTemplate(ttwo);
		
		service.replacePlan(userVMOSA, 3, admin2);
		Assert.assertEquals(userVMOSA, service.getPlan(3,admin2));
		
		//makePlan testing
		service.makePlan("testing Template", two);
		Assert.assertEquals("testing Template", service.getPlan(2019, two).getUserTemplateName());

		
		//test Client login
		MyRemoteClient remote = new MyRemoteClient();
		remote.login("admin2", "hihocherrio");
		Assert.assertEquals(remote.myAccount.getName(),"admin2");
		Assert.assertEquals(remote.myAccount.getPassword(), "hihocherrio");
		
		//test Client viewPlan()
		service.getAccount("admin2", "hihocherrio").getDept().addNewTemplate(tthree);
		
		remote.login("admin2", "hihocherrio");
		Template plan = remote.viewPlan(4);
		
		Assert.assertEquals(plan.getYear(),tthree.getYear());
		Assert.assertEquals(plan.getDeveloperTemplateName(), tthree.getDeveloperTemplateName());
		
	
		
		//newPlan testing
		remote.login("admin2", "hihocherrio");
		Template temp=remote.newPlan("d2Plan");
		service.load();
		Assert.assertEquals(temp.getUserTemplateName(), service.getPlan(2019, service.getAccount("admin2", "hihocherrio")).getUserTemplateName());
		
		//load() testing
		service.load();
		Assert.assertEquals("admin", service.getAccountList().get(0).getName());
		Assert.assertEquals("you shall not x - word", service.getAccountList().get(0).getPassword());
		ArrayList<Account> serviceAccounts=service.getAccountList();
		Assert.assertEquals("three", serviceAccounts.get(serviceAccounts.size()-1).getName());
		Assert.assertEquals("pas", service.getAccountList().get(service.getAccountList().size()-1).getPassword());
		
		
	}
}
