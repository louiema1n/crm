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

		// ȡ���������й����ͻ�
		String hql2 = "update Customer set decidedzone_id=null where decidedzone_id=?";
		session.createQuery(hql2).setParameter(0, decidedZoneId).executeUpdate();

		// ���й���
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
		//��ȡsession
		Session session = HibernateUtils.openSession();
		//��������
		session.beginTransaction();
		//��дhql
		String hql = "from Customer where telephone = ?";
		//���ò�������ѯ
		List<Customer> customers = session.createQuery(hql).setParameter(0, telephone).list();
		//�ύ����
		session.getTransaction().commit();
		session.close();
		if (customers != null && customers.size() > 0) {
			return customers.get(0);
		}
		return null;

	}

	@Override
	public String findDecidedzoneIdByAddr(String address) {
		//��ȡsession
		Session session = HibernateUtils.openSession();
		//��������
		session.beginTransaction();
		//��дhql
		String hql = "select decidedzone_id from Customer where address = ?";
		//���ò�������ѯ
		List<String> decidedzone_ids = session.createQuery(hql).setParameter(0, address).list();
		//�ύ����
		session.getTransaction().commit();
		session.close();
		if (decidedzone_ids != null && decidedzone_ids.size() > 0) {
			return decidedzone_ids.get(0);
		}
		return null;
	}

}
