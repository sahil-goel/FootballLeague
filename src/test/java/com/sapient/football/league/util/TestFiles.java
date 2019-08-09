package com.sapient.football.league.util;

import java.io.File;
import java.io.IOException;

import org.junit.Ignore;

import com.google.common.base.Charsets;

@Ignore
public class TestFiles {
	private static final String TEST_JSONS_FOLDER = "testJsons/";
	
	public static String readFile(String fileName) throws IOException {
		return com.google.common.io.Files.toString(getFile(fileName), Charsets.UTF_8);
	}
	
	private static File getFile(String fileName) {
		return new File(TestFiles.class.getClassLoader().getResource(TEST_JSONS_FOLDER + fileName).getFile());
	}
}
