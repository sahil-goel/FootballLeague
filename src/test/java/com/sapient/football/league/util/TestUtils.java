package com.sapient.football.league.util;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import org.junit.Ignore;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jayway.jsonpath.JsonPath;

@Ignore
public class TestUtils {
	private final static TypeReference<List<Map<String, String>>> MAP_TYPE_REFERENCE_1 = new TypeReference<List<Map<String, String>>>() {
	};
	private final static TypeReference<List<Map<String, Object>>> MAP_TYPE_REFERENCE_2 = new TypeReference<List<Map<String, Object>>>() {
	};
	
	public static List<Map<String, String>> allCompetitions() throws IOException {
		String fileContents = TestFiles.readFile("allCompetitions.json");
		return Json.parse(fileContents, MAP_TYPE_REFERENCE_1);
	}
	
	public static List<Map<String, Object>> teamsInCompetition() throws IOException {
		String fileContents = TestFiles.readFile("teamsInCompetition.json");
		return Json.parse(fileContents, MAP_TYPE_REFERENCE_2);
	}
	
	public static List<Map<String, String>> standingsForCompetition() throws IOException {
		String fileContents = TestFiles.readFile("standingsForCompetition.json");
		return Json.parse(fileContents, MAP_TYPE_REFERENCE_1);
	}

	public static List<Map<String, String>> allCountries() throws IOException {
		String fileContents = TestFiles.readFile("allCountries.json");
		return Json.parse(fileContents, MAP_TYPE_REFERENCE_1);
	}
	
	public static List<Map<String, String>> competionsForCountry() throws IOException {
		String fileContents = TestFiles.readFile("competitionForCountry.json");
		return Json.parse(fileContents, MAP_TYPE_REFERENCE_1);
	}
	
	public static String hitAndGetContents(String url, MockMvc mockMvc) throws UnsupportedEncodingException, Exception {
		return hitAndGetContents(url,  mockMvc, 200);
	}
	
	public static String hitAndGetContents(String url, MockMvc mockMvc, int status) throws UnsupportedEncodingException, Exception {
		return mockMvc
				.perform(get(url))
				.andDo(print())
				.andExpect(status().is(status))
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andReturn()
				.getResponse()
				.getContentAsString();
	}
	
	public static <K> void verifyJsons(String content, List<Map<String, K>> testCompetitions) throws Exception {
		int counter = 0;
		for (Map<String, K> map: testCompetitions) {
			Map<String, K> fetched = JsonPath.read(content, "$[" + counter + "].results");
			assertThat(map, equalTo(fetched));
	    	counter++;
		}
	}
	
	public static void verifyLinks(String content,  List<List<LinkDetail>> allLinks) {
		int counter = 0;
		for (List<LinkDetail> links: allLinks) {
			for (int i = 0; i < links.size(); i++) {
				Map<String, String> fetchedLinks = JsonPath.read(content, "$[" + counter + "].links["+i+"]");	
				assertThat(fetchedLinks.get("rel"), equalTo(links.get(i).getRel()));
				assertThat(fetchedLinks.get("href"), containsString(links.get(i).getHref()));
			}
			counter++;
		}
	}

	
}
