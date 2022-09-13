package com.example.demo.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.model.SubUser;
import com.example.demo.repository.SubUserRepository;
import com.example.demo.service.SubUserService;

@Service
public class SubUserServiceImpl implements SubUserService{
	
	@Autowired
	private SubUserRepository subUserRepository;

	@Override
	public List<SubUser> getAllSubUsers() {
		return subUserRepository.findAll();
	}

	@Override
	public void saveSubUser(SubUser subUser) {
		this.subUserRepository.save(subUser);
		
	}

	@Override
	public SubUser getSubUserById(long id) {
		Optional<SubUser> optional = subUserRepository.findById(id);
		SubUser subUser = null;
		if (optional.isPresent()) {
			subUser = optional.get();
		} else {
			throw new RuntimeException(" SubUser not found for id :: " + id);
		}
		return subUser;
	}

	@Override
	public void deleteSubUserById(long id) {
		this.subUserRepository.deleteById(id);
		
	}

	@Override
	public Page<SubUser> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
			Sort.by(sortField).descending();
		
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return this.subUserRepository.findAll(pageable);
	}

}
