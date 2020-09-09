package com.lgcns.ejb.SingletonSessionBean;

import static javax.ejb.ConcurrencyManagementType.CONTAINER;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;

@Singleton
@ConcurrencyManagement(CONTAINER)
public class Counter {
	private int hits = 1;

	// Increment and return the number of hits
	@Lock(LockType.WRITE)
	public int getHits() {
		return hits++;
	}
}