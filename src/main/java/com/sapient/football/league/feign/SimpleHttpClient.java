package com.sapient.football.league.feign;

import static java.lang.String.format;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;

import com.google.common.base.Charsets;
import com.sapient.football.league.util.Json;

import feign.Client;
import feign.Request;
import feign.Request.Options;
import feign.Response;

public class SimpleHttpClient implements Client {

	private final Client.Default client;

	public SimpleHttpClient(Client.Default client) {
		this.client = client;
	}
	
	public Response convertResponse(HttpURLConnection connection, Request request) throws IOException {
	      int status = connection.getResponseCode();
	      String reason = connection.getResponseMessage();
	      if (status < 0) {
	    	  throw new IOException(format("Invalid status(%s) executing %s %s", status, connection.getRequestMethod(), connection.getURL()));
	      }

	      Map<String, Collection<String>> headers = new LinkedHashMap<String, Collection<String>>();
	      for (Map.Entry<String, List<String>> field : connection.getHeaderFields().entrySet()) {
	    	  if (field.getKey() != null) {
	    		  headers.put(field.getKey(), field.getValue());
	    	  }
	      }

	      Integer length = connection.getContentLength();
	      if (length == -1) {
	    	  length = null;
	      }
	      InputStream stream;
	      if (status >= 400) {
	    	  stream = connection.getErrorStream();
	      } else {
	    	  stream = connection.getInputStream();
	      }
	      return Response.builder()
				          .status(status)
				          .reason(reason)
				          .headers(headers)
				          .request(request)
				          .body(stream, length)
				          .build();
	    }

	@Override
	public Response execute(Request request, Options options) throws IOException {
		Response response = client.execute(request, options);
		return validate(response);
	}
	
	private Response validate(Response response) throws IOException {
 	   if (response.status() >= 200 && response.status() < 300) {
 		   byte[] body = body(response);
 		   Response cloned = response.toBuilder().body(body).build();
    	   String responseBody = new String(body);
    	   List<Map<String, Object>> values = Json.parse(responseBody);
    	   if (values.size() == 1) {
    		   Map<String, Object> map = values.get(0);
	    	   if (map.containsKey("error")) {
	    		   Object errorCode = map.get("error");
	    		   if (errorCode instanceof Integer && HttpStatus.resolve((int) errorCode) != null) {
	    			   Response.Builder builder = response.toBuilder();
	    			   return builder.status((int) errorCode).body((String)map.get("message"), Charsets.UTF_8).build();
	    		   }
	    	   }
    	   }
    	   return cloned;
 	   }
 	  return response;
    }
	
	private byte[] body(Response response) throws IOException { 
		InputStream is = response.body().asInputStream();
		return IOUtils.toByteArray(is);
	}
}
