package com.example.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.demo.model.SubUser;

public interface SubUserService {
	List<SubUser> getAllSubUsers();
	void saveSubUser(SubUser subUser);
	SubUser getSubUserById(long id);
	void deleteSubUserById(long id);
	Page<SubUser> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);

}
