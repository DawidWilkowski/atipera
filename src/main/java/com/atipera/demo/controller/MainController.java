package com.atipera.demo.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MainController {
	@GetMapping(value = "/{userName}")
	public ResponseEntity<JSONArray> getUserInfo(@PathVariable(value = "userName") String userName)
			throws JSONException, MalformedURLException, IOException {

		JSONArray json = new JSONArray(IOUtils.toString(new URL("https://api.github.com/users/" + userName + "/repos"),
				Charset.forName("UTF-8")));
		List<String> namesOfRepos = new ArrayList<>();
		for (int i = 0; i < json.length(); i++) {
			JSONObject innInnerObj = json.getJSONObject(i);
			Iterator<String> InnerIterator = innInnerObj.keys();
			while (InnerIterator.hasNext()) {
				String key = InnerIterator.next();
				Object value = innInnerObj.get(key);

				if (key.equals("full_name")) {
					System.out.println("++++++++++++" + value);
					namesOfRepos.add(value.toString());

				}

				System.out.println("[" + key + "][" + value + "]      ");

			}
		}
//		List<JSONArray> branches = new ArrayList<>();
//		for (String repo : namesOfRepos) {
//			branches.add(new JSONArray(IOUtils.toString(new URL("https://api.github.com/repos/" + userName + "/branches"),
//					Charset.forName("UTF-8"))));
//		}
//		

		System.out.println(namesOfRepos);
		return new ResponseEntity<JSONArray>(json, HttpStatus.OK);
	}
}
