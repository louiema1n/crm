package com.lm.crm.service.impl;

import java.util.List;

import org.hibernate.Session;

import com.lm.crm.domain.Customer;
import com.lm.crm.service.CustomerService;
import com.lm.crm.utils.HibernateUtils;


public class CustomerServiceImpl implements CustomerService {

	public List<Customer> findnoassociationCustomers() {
		Session session = HibernateUtils.openSession();
		session.beginTransaction();

		String hql = "from Customer where decidedzone_id is null";
		List<Customer> customers = session.createQuery(hql).list();

		session.getTransaction().commit();
		session.close();

		return customers;
	}

	public List<Customer> findhasassociationCustomers(String decidedZoneId) {
		Session session = HibernateUtils.openSession();
		session.beginTransaction();

		String hql = "from Customer where decidedzone_id = ?";
		List<Customer> customers = session.createQuery(hql).setParameter(0, decidedZoneId).list();

		session.getTransaction().commit();
		session.close();

		return customers;
	}

	public void assignCustomersToDecidedZone(Integer[] customerIds, String decidedZoneId) {
		Session session = HibernateUtils.openSession();
		session.beginTransaction();

		// 取消定区所有关联客户
		String hql2 = "update Customer set decidedzone_id=null where decidedzone_id=?";
		session.createQuery(hql2).setParameter(0, decidedZoneId).executeUpdate();

		// 进行关联
		String hql = "update Customer set decidedzone_id=? where id =?";
		if (customerIds != null) {
			for (Integer id : customerIds) {
				session.createQuery(hql).setParameter(0, decidedZoneId).setParameter(1, id).executeUpdate();
			}
		}
		session.getTransaction().commit();
		session.close();
	}

	@Override
	public Customer findCustomerByTel(String telephone) {
		//获取session
		Session session = HibernateUtils.openSession();
		//开启事务
		session.beginTransaction();
		//书写hql
		String hql = "from Customer where telephone = ?";
		//设置参数并查询
		List<Customer> customers = session.createQuery(hql).setParameter(0, telephone).list();
		//提交事务
		session.getTransaction().commit();
		session.close();
		if (customers != null && customers.size() > 0) {
			return customers.get(0);
		}
		return null;

	}

	@Override
	public String findDecidedzoneIdByAddr(String address) {
		//获取session
		Session session = HibernateUtils.openSession();
		//开启事务
		session.beginTransaction();
		//书写hql
		String hql = "select decidedzone_id from Customer where address = ?";
		//设置参数并查询
		List<String> decidedzone_ids = session.createQuery(hql).setParameter(0, address).list();
		//提交事务
		session.getTransaction().commit();
		session.close();
		if (decidedzone_ids != null && decidedzone_ids.size() > 0) {
			return decidedzone_ids.get(0);
		}
		return null;
	}

}
