package com.is4tech.sql.demo;

import com.is4tech.sql.demo.models.User;
import com.is4tech.sql.demo.services.UserService;
import com.is4tech.sql.demo.services.dto.IUserDTO;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
class UserServiceTest {

    @MockBean
    private IUserDTO userService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void test() throws Exception {
        var user = new User();
        user.setCode("1234G");
        user.setPassword("password");
        user.setEmail("chris@gmail.com");
        user.setEmail_alert(user.getEmail());
        Mockito.when(this.userService.save(new User())).thenReturn(new User());

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/channel/")
                .accept(MediaType.APPLICATION_JSON)
                .content(String.valueOf(user))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        Assertions.assertEquals(403, response.getStatus());
    }

    @Test
    void userfind() throws Exception {
        List<User> users = new ArrayList<User>();
        User[] usr = new User[2];
        for (User user:usr) {
            user = new User();
            user.setCode("1234G");
            user.setPassword("password");
            user.setEmail("chris@gmail.com");
            user.setEmail_alert(user.getEmail());
            users.add(user);
        }
        Mockito.when(this.userService.findAll()).thenReturn(users);
        List<User> empList = (List<User>) this.userService.findAll();

        Assertions.assertEquals(2, empList.size());
    }

    @Test
    void userupdate() throws Exception {
        var user = new User();
        user.setCode("1234G");
        user.setPassword("password");
        user.setEmail("chris@gmail.com");
        user.setEmail_alert(user.getEmail());

        Mockito.when(this.userService.updateById(1L, user)).thenReturn(user);
        var usr = this.userService.updateById(1L, user);

        Assertions.assertEquals(user.getEmail(), usr.getEmail());
        Assertions.assertEquals(user.getCode(), usr.getCode());
        Assertions.assertEquals(user.getEmail(), usr.getEmail_alert());
    }
    @Test
    void userupdatefailed() throws Exception {
        var user = new User();
        user.setCode("1234G");
        user.setPassword("password");
        user.setEmail("chris@gmail.com");
        user.setEmail_alert(user.getEmail());

        Mockito.when(this.userService.updateById(1L, user)).thenReturn(null);
        var usr = this.userService.updateById(1L, user);

        Assertions.assertEquals(usr, null);
    }

    @Test
    void usersabe() throws Exception {
        var user = new User();
        user.setCode("1234G");
        user.setPassword("password");
        user.setEmail("chris@gmail.com");
        user.setEmail_alert(user.getEmail());

        Mockito.when(this.userService.save(user)).thenReturn(user);
        var usr = this.userService.save(user);

        Assertions.assertEquals(user.getEmail(), usr.getEmail());
        Assertions.assertEquals(user.getCode(), usr.getCode());
        Assertions.assertEquals(user.getEmail(), usr.getEmail_alert());
    }

    @Test
    void userfindbyid() throws Exception {
        List<User> users = new ArrayList<User>();
        var user = new User();
        user.setCode("1234G");
        user.setPassword("password");
        user.setEmail("chris@gmail.com");
        user.setEmail_alert(user.getEmail());
        users.add(user);

        Mockito.when(this.userService.findById(1L)).thenReturn(Optional.ofNullable(users.get(0)));
        var usr1 = this.userService.findById(1L);

        Assertions.assertEquals(user.getEmail(), usr1.get().getEmail());
        Assertions.assertEquals(user.getCode(), usr1.get().getCode());
        Assertions.assertEquals(user.getEmail(), usr1.get().getEmail_alert());
    }

}
