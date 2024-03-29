package com.briup.hdfs.io;

import java.util.Comparator;
import java.util.TreeSet;

//List Set Map [ ]
public class TreeSetTest {
	public static void main(String[] args) {
		TreeSet<Teacher> set = new TreeSet<Teacher>(new MyComparator());
		Teacher t1 = new Teacher(1, "jack", 8000);
		Teacher t2 = new Teacher(2, "rose", 6000);
		Teacher t3 = new Teacher(3, "smith", 10000);
		Teacher t4 = new Teacher(4, "kali", 12000);
		set.add(t4);
		set.add(t2);
		set.add(t1);
		set.add(t3);
		for (Object object : set) {
			System.out.println(object);
		}
	}
}

class Teacher {
//class Teacher implements Comparable{
	private int id;
	private String name;
	private double salary;

	public Teacher(int id, String name, double salary) {
		this.id = id;
		this.name = name;
		this.salary = salary;
	}

	@Override
	public String toString() {
		return "Teacher [id=" + id + ", name=" + name + ", salary=" + salary + "]";
	}

	/*
	 * public int compareTo(Object o) { Teacher t=(Teacher)o; //return this.id-t.id;
	 * return (int)(this.salary-t.salary); }
	 */
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}
}

class MyComparator implements Comparator<Teacher> {

	public int compare(Teacher o1, Teacher o2) {
		return (o1.getId() - o2.getId());
	}

}