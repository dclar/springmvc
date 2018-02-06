import org.dclar.controller.c01auth.vo.UserLoginVo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
// 默认是src/main/weapp 所以可以不显式声明
@WebAppConfiguration(value = "src/main/webapp")
//@ContextConfiguration(locations = {"classpath*:springmvc/SpringMVC-servlet.xml", "classpath*:org/dclar/shiro/spring-shiro.xml",})
@ContextHierarchy({
        @ContextConfiguration(name = "parent", locations = "classpath:springmvc/applicationContext.xml"),
        @ContextConfiguration(name = "child", locations = "classpath:springmvc/SpringMVC-servlet.xml"),
        @ContextConfiguration(name = "child", locations = "classpath:org/dclar/shiro//spring-shiro.xml")
        //@ContextConfiguration(name = "child", locations = "classpath:mybatis/mybatis-config.xml")
})
public class HomeTest {


    Logger log = LoggerFactory.getLogger(HomeTest.class);

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

//    @Mock
//    privateMailService mailService;
//
//    @InjectMocks
//    Home homeController;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void should_return_status_success_when_send_mail_success() throws Exception {

        UserLoginVo vo = new UserLoginVo();

        String email = "darcula@gmail.com";
        String password = "password";

        vo.setInputEmail(email);
        vo.setInputPassword(password);

//        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/home")
//                .param("inputEmail", "darcula@gmail.com")
//                .param("inputPassword", "password"))
//                .andExpect(MockMvcResultMatchers.view().name("home"))
//
//                // 判断是否存在attribute
////                .andExpect(MockMvcResultMatchers.model().attributeExists("userLoginVo"))
////                .andExpect(MockMvcResultMatchers.model().attribute("userLoginVo", hasProperty("inputEmail", is(email))))
////                .andExpect(MockMvcResultMatchers.model().attribute("userLoginVo", hasProperty("inputPassword", is(password))))
//                // 其中hasProperty("inputPassword")指userLoginVo对象中包括inputPassword的字段
//                // is(password)指指userLoginVo对.inputPassword的内容和定义的password一致
//
//                //.andExpect(MockMvcResultMatchers.model().attributeExists("user"))
//                // 打印输出内容 如页面html
//                //.andDo(MockMvcResultHandlers.print())
//                .andReturn();

        //Assert.assertNotNull(result.getModelAndView().getModel().get("user"));
    }

}
