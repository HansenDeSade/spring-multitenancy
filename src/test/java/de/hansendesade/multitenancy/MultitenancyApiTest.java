package de.hansendesade.multitenancy;

import de.hansendesade.multitenancy.auth.WebSecurityConfig;
import de.hansendesade.multitenancy.web.TenantInfoController;
import org.junit.*;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import javax.servlet.Filter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {MultitenancyApplication.class})
@WebAppConfiguration
public class MultitenancyApiTest {

    @Resource
    private WebApplicationContext context;

    private MockMvc mvc;
    private String userAAuth = "{\n" +
            "  \"username\": \"userA\",\n" +
            "  \"password\": \"test\"\n" +
            "}";
    private String userBAuth = "{\n" +
            "  \"username\": \"userB\",\n" +
            "  \"password\": \"test\"\n" +
            "}";

    @Autowired
    private Filter springSecurityFilterChain;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilters(springSecurityFilterChain)
                .build();
    }

    @Test
    public void testMultitenancy() throws Exception {
        MvcResult result = mvc.perform(post("/authenticate").content(userAAuth)).andExpect(status().isOk()).andReturn();
        String tokenUserA = result.getResponse().getHeader("Authorization");


        result = mvc.perform(post("/authenticate").content(userBAuth)).andExpect(status().isOk()).andReturn();
        String tokenUserB = result.getResponse().getHeader("Authorization");

        String contentUserB = mvc.perform(get("/tenantinfo").header(HttpHeaders.AUTHORIZATION, tokenUserB)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        String contentUserA = mvc.perform(get("/tenantinfo").header(HttpHeaders.AUTHORIZATION, tokenUserA)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        Assert.assertTrue(contentUserB.contains("tenant 2"));
        Assert.assertTrue(contentUserA.contains("tenant 1"));
    }
}
