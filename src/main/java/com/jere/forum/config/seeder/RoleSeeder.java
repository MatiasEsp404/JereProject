package com.jere.forum.config.seeder;

import com.jere.forum.config.security.common.Role;
import com.jere.forum.model.RoleEntity;
import com.jere.forum.repository.IRoleRepository;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class RoleSeeder {

  @Autowired
  private IRoleRepository roleRepository;

  @EventListener
  public void seed(ContextRefreshedEvent event) {
    createRoleTable();
  }

  private void createRoleTable() {
    if (roleRepository.count() < 2) {
      roleRepository.saveAll(Arrays.asList(buildRole(Role.USER), buildRole(Role.ADMIN)));
    }
  }

  private RoleEntity buildRole(Role role) {
    return RoleEntity.builder().name(role.getFullRoleName()).build();
  }

}
