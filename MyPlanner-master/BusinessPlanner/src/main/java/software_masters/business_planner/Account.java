package software_masters.business_planner;

import java.io.Serializable;

public class Account implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8022211860631546060L;
	public String name;
	private String password;
	public Department dept;
	public boolean bAdmin;//determines if the client can call addUser() and CanEdit()
	
	public Account()
	{
		this.name=null;
		this.password=null;
		this.dept=null;
		this.bAdmin=false;
	}
	
	/**
	 * 
	 * 
	 * @param String name
	 * @param String password
	 * @param Department department
	 * @param boolean bAdmin
	 */
	public Account(String name, String password, Department dept, boolean bAdmin) {
		super();
		this.name = name;
		this.password = password;
		this.dept = dept;
		this.bAdmin = bAdmin;
	}
	
	/**
	 * @param String name
	 * @param String password
	 * @param Department department
	 */
	public Account(String name, String password, Department dept)
	{
		this(name,password,dept,false);
	}

	//setters and getters
	
	/**
	 * returns true if the user is an administrator
	 * 
	 * @return boolean badmin 
	 */
	public boolean isAdmin() {
		return bAdmin;
	}

	/**
	 * sets the value to true if user is administrator, false if not
	 * @param boolean bAdmin
	 */
	public void setbAdmin(boolean bAdmin) {
		this.bAdmin = bAdmin;
	}
	
	/**
	 * returns the department of user
	 * 
	 * @return Department department
	 */
	public Department getDept() {
		return dept;
	}

	public boolean isbAdmin() {
		return bAdmin;
	}

	/**
	 * sets the department of the user
	 * @param Department dept
	 */
	public void setDept(Department dept) {
		this.dept = dept;
	}

	/**
	 * returns the name of the user
	 * @return String name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * sets the name of the user
	 * @param String name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * returns the password of the user
	 * 
	 * @return String password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * sets the password of the user
	 * @param String password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	
}