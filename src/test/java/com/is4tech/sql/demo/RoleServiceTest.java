package com.is4tech.sql.demo;

import com.is4tech.sql.demo.models.Roles;
import com.is4tech.sql.demo.services.dto.IRolesDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class RoleServiceTest {
  @MockBean
  private IRolesDTO roleDTO;

  @Test
  void create() throws Exception {
    var rol = new Roles();
    rol.setId(1L);
    rol.setAuthority("ADMIN");

    Mockito.when(this.roleDTO.save(rol)).thenReturn(rol);

    var role = this.roleDTO.save(rol);
    Assertions.assertEquals(rol.getAuthority(), role.getAuthority());
    Assertions.assertEquals(rol.getId(), role.getId());
  }

  @Test
  void find() throws Exception {
    var rol = new Roles();
    rol.setId(1L);
    rol.setAuthority("ADMIN");
    List<Roles> roles = new ArrayList<Roles>();
    roles.add(rol);

    Mockito.when(this.roleDTO.findByAuthority("ADMIN")).thenReturn(roles);

    var role = this.roleDTO.findByAuthority("ADMIN");
    Assertions.assertEquals(1, role.size());
    Assertions.assertEquals(rol.getAuthority(), role.get(0).getAuthority());
    Assertions.assertEquals(rol.getId(), role.get(0).getId());
  }
}
