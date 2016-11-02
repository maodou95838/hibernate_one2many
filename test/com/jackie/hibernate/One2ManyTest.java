package com.jackie.hibernate;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.hibernate.Session;

import com.jackie.hibernate.Classes;
import com.jackie.hibernate.HibernateUtils;
import com.jackie.hibernate.Student;

import junit.framework.TestCase;

public class One2ManyTest extends TestCase {

	public void testSave1() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			Student student1 = new Student();
			student1.setName("张三");
			
			Student student2 = new Student();
			student2.setName("李四");
			
			Classes classes = new Classes();
			classes.setName("中一班");
			
			Set students = new HashSet();
			students.add(student1);
			students.add(student2);
			classes.setStudents(students);
			
			//inverse=true设置后，不抛出异常，当时也只能保存classes对象
			session.save(classes);
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
	}
	
	/**
	 * 测试由一的一方（classes）来保存数据
	 */
	public void testSave2() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			Student student1 = new Student();
			student1.setName("张三");
			session.save(student1);
			
			Student student2 = new Student();
			student2.setName("李四");
			session.save(student2);
			
			Classes classes = new Classes();
			classes.setName("中一班");
			
			Set students = new HashSet();
			students.add(student1);
			students.add(student2);
			classes.setStudents(students);
			
			//同样由于inverse=true, 一的一方已经不能完全保存数据，
			//只能保存自己的数据，不能保存关联关系
			session.save(classes);
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
	}	
	
	public void testSave3() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();

			Classes classes = new Classes();
			classes.setName("中一班");
			session.save(classes);
			
			Student student1 = new Student();
			student1.setName("张三");
			student1.setClasses(classes);
			session.save(student1);
			
			Student student2 = new Student();
			student2.setName("李四");
			student2.setClasses(classes);
			
			//可以正常的保存数据，原因是关系交由多的一方来维护，也就是Student来维护
			session.save(student2);
			
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
	}		
	public void testLoad1() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			Classes classes = (Classes)session.load(Classes.class, 6);
			System.out.println("classes.name=" + classes.getName());
			Set students = classes.getStudents();
			for (Iterator iter=students.iterator(); iter.hasNext();) {
				Student student = (Student)iter.next();
				System.out.println("student.name=" +student.getName());
			}
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
	}	
	
	public void testLoad2() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			Student student = (Student)session.load(Student.class, 1);
			System.out.println("student.name=" + student.getName());
			System.out.println("student.classes.name=" + student.getClasses().getName());
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
	}		
	
}
