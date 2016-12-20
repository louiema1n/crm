package com.lm.crm.service;

import java.util.List;

import com.lm.crm.domain.Customer;


// �ͻ�����ӿ� 
public interface CustomerService {
	// δ���������ͻ�
	public List<Customer> findnoassociationCustomers();

	// ��ѯ�Ѿ�����ָ�������Ŀͻ�
	public List<Customer> findhasassociationCustomers(String decidedZoneId);

	// ��δ���������ͻ�������������
	public void assignCustomersToDecidedZone(Integer[] customerIds, String decidedZoneId);
}
