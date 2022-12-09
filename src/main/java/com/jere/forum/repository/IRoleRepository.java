package com.jere.forum.repository;

import com.jere.forum.model.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<RoleEntity, Integer> {

  RoleEntity findByName(String name);

}
