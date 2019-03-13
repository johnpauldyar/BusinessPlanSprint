package software_masters.business_planner;

import java.io.Serializable;
import java.util.ArrayList;

public class Department implements Serializable
{

	public void setModel(Template model) {
		this.model = model;
	}

	public void setOldTemplates(ArrayList<Template> oldTemplates) {
		this.oldTemplates = oldTemplates;
	}

	private static final long serialVersionUID = -850551282099065281L;
	private Template model;
	private ArrayList<Template> oldTemplates;
	
	/**
	 * initializer 
	 */
	public Department()
	{
		this.model=null;
		this.oldTemplates=null;
	}
	
	/**
	 * Initializer
	 * 
	 * @param Template model
	 */
	public Department(Template model)
	{
		this.model=model;
		oldTemplates=new ArrayList<Template>();
	}
	
	
	/**
	 * 
	 * returns the model template
	 * @return Template model
	 */
	public Template getModel()
	{
		return this.model;
	}
	
	/**
	 * Returns the list of old business plans
	 * 
	 * @return ArrayList<Template> oldTemplates
	 */
	public ArrayList<Template> getOldTemplates()
	{
		return oldTemplates;
	}
	
	/**
	 * adds a new business plan to the list of old business plans
	 * 
	 * @param Template temp
	 */
	public void addNewTemplate(Template temp) throws IllegalArgumentException
	{
		for(Template t:oldTemplates)
		{
			if(t.getYear()==temp.getYear())
			{
				throw new IllegalArgumentException("There's already a template with this year.");
			}
		}
		oldTemplates.add(temp);
	}
}
