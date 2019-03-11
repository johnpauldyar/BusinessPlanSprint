package software_masters.business_planner;

public class Account {
	public String name;
	private String password;
	public Department dept;
	public boolean bAdmin;//determines if the client can call addUser() and CanEdit()
	
	/**
	 * @param name
	 * @param password
	 * @param department
	 * @param bAdmin
	 */
	public Account(String name, String password, Department dept, boolean bAdmin) {
		super();
		this.name = name;
		this.password = password;
		this.dept = dept;
		this.bAdmin = bAdmin;
	}
	
	/**
	 * @param name
	 * @param password
	 * @param department
	 */
	public Account(String name, String password, Department dept)
	{
		this(name,password,dept,false);
	}

	//setters and getters
	
	/**
	 * @return admin boolean
	 */
	public boolean isbAdmin() {
		return bAdmin;
	}

	/**
	 * @param bAdmin
	 */
	public void setbAdmin(boolean bAdmin) {
		this.bAdmin = bAdmin;
	}
	
	/**
	 * @return department
	 */
	public Department getDept() {
		return dept;
	}

	/**
	 * @param dept
	 */
	public void setDept(Department dept) {
		this.dept = dept;
	}

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	
}