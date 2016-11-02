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
			student1.setName("����");
			
			Student student2 = new Student();
			student2.setName("����");
			
			Classes classes = new Classes();
			classes.setName("��һ��");
			
			Set students = new HashSet();
			students.add(student1);
			students.add(student2);
			classes.setStudents(students);
			
			//inverse=true���ú󣬲��׳��쳣����ʱҲֻ�ܱ���classes����
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
	 * ������һ��һ����classes������������
	 */
	public void testSave2() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			Student student1 = new Student();
			student1.setName("����");
			session.save(student1);
			
			Student student2 = new Student();
			student2.setName("����");
			session.save(student2);
			
			Classes classes = new Classes();
			classes.setName("��һ��");
			
			Set students = new HashSet();
			students.add(student1);
			students.add(student2);
			classes.setStudents(students);
			
			//ͬ������inverse=true, һ��һ���Ѿ�������ȫ�������ݣ�
			//ֻ�ܱ����Լ������ݣ����ܱ��������ϵ
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
			classes.setName("��һ��");
			session.save(classes);
			
			Student student1 = new Student();
			student1.setName("����");
			student1.setClasses(classes);
			session.save(student1);
			
			Student student2 = new Student();
			student2.setName("����");
			student2.setClasses(classes);
			
			//���������ı������ݣ�ԭ���ǹ�ϵ���ɶ��һ����ά����Ҳ����Student��ά��
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
