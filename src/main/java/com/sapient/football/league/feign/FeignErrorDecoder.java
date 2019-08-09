package com.sapient.football.league.feign;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sapient.football.league.util.exception.BadRequestException;
import com.sapient.football.league.util.exception.NotFoundException;

import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;

public class FeignErrorDecoder implements ErrorDecoder {
	private static final Logger logger = LoggerFactory.getLogger(FeignErrorDecoder.class);
	@Override
    public Exception decode(String methodKey, Response response) {
	 	String message = message(response);
        switch (response.status()) {
            case 400:
                return new BadRequestException(message);
            case 404:
                return new NotFoundException(message);
            default:
                return new Exception("Generic error");
        }
    }
	
	private String message(Response response) {
		try {
			return Util.toString(response.body().asReader());
		} catch (IOException e) {
			logger.error("Exception while fetching response body", e);
			return e.getMessage();
		}
	}
}
