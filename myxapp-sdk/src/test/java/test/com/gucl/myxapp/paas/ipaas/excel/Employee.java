package test.com.gucl.myxapp.paas.ipaas.excel;

import java.sql.Timestamp;
import java.util.Date;

public class Employee {
	private long id;
	private String name;
	private int age;
	private String job;
	private double salery;
	private Date addtime;
	private Timestamp birthtime;

	public Employee() {

	}

	public Employee(long id, String name, int age, String job, double salery) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.job = job;
		this.salery = salery;
		this.addtime = new Date();
		this.birthtime=new Timestamp(new Date().getTime());
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}
	
	

	public Timestamp getBirthtime() {
		return birthtime;
	}

	public void setBirthtime(Timestamp birthtime) {
		this.birthtime = birthtime;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public double getSalery() {
		return salery;
	}

	public void setSalery(double salery) {
		this.salery = salery;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", age=" + age
				+ ", job=" + job + ", salery=" + salery + ", addtime="
				+ addtime  + birthtime +"]";
	}

}
