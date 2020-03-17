package com.galvanize;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.galvanize.entities.Officer;
import com.galvanize.entities.Rank;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.transaction.Transactional;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class RestOfficerControllerTest {

    @Autowired
    MockMvc mvc;

    ObjectMapper mapper = new ObjectMapper();

    @BeforeEach // Don't need to declare "throws" here.  Exception is never thrown.
    public void setup() { //throws Exception {
        List<Officer> officerList = new ArrayList<>();
        // rlw - again, setting up your own tests data is the RIGHT way to test
        officerList.add(new Officer(Rank.CAPTAIN,"Krishna", "Karki"));
        officerList.add(new Officer(Rank.ENSIGN,"Mark", "Laco"));
        officerList.add(new Officer(Rank.COMMANDER,"Mike", "Cole"));
        officerList.add(new Officer(Rank.ADMIRAL,"Kirk", "Colmen"));
        officerList.add(new Officer(Rank.COMMODORE,"Michael", "Johnson"));

        // rlw - Functional java in action!  Great job!
        officerList.forEach( officer -> {
            try {
                mvc.perform(post("/api/officers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(officer)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id").exists())
                        .andExpect(jsonPath("$.firstName").exists())
                        .andExpect(jsonPath("$.lastName").exists())
                        .andExpect(jsonPath("$.rank").exists())
                        .andDo(print());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    void getOfficersTest() throws Exception {
        mvc.perform(get("/api/officers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()",is(5)))
                .andDo(print());
    }

    @Test
    void getOfficerByRankTest() throws Exception {
        String rank = String.valueOf(Rank.CAPTAIN);
        String url = "/api/officers/"+rank;
        mvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()",is(1)))
                .andExpect(content().string(containsString("CAPTAIN")))
                .andDo(print());
    }

    @Test
    void saveOfficerTest() throws Exception {
        String officer = mapper.writeValueAsString(new Officer(Rank.CAPTAIN,"KK", "KK"));
        mvc.perform(post("/api/officers").content(officer).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(content().string(containsString("KK")))
                .andDo(print());
    }

    @Test
    public void removeOfficerTest() throws Exception {
        String url = "/api/officers/";
        MvcResult result = mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        Integer id = JsonPath.parse(result.getResponse().getContentAsString()).read("$[0].id");
        url = "/api/officers/" + id;
        mvc.perform(delete(url).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        url = "/api/officers/";
        mvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()",is(4)));
    }

    @Test
    public void updateOfficerTest() throws Exception {
        String url = "/api/officers/";
        MvcResult result = mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        Integer id = JsonPath.parse(result.getResponse().getContentAsString()).read("$[0].id");
        url = url + id;
        // rlw - Don't need the String.valueOf here.  Rank's toString() will take care of it you could call
        // Rank.ADMRIAL.toString if you wanted to be explicit, but it's not necessary
        // String body = "{ \"rank\":" + "\"" + String.valueOf(Rank.ADMIRAL) + "\"}";
        String body = "{ \"rank\":" + "\"" + Rank.ADMIRAL + "\"}";
        mvc.perform(patch(url).contentType(MediaType.APPLICATION_JSON)
                .content(Rank.ADMIRAL.name()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("ADMIRAL")))
                .andDo(print());
    }

}
