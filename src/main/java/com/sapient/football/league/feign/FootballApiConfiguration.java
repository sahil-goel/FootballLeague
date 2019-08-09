package com.sapient.football.league.feign;

import java.io.IOException;
import java.lang.reflect.Type;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import feign.Client;
import feign.Feign;
import feign.Response;
import feign.codec.Decoder;
import feign.codec.Encoder;

@Configuration
@Import(FeignClientsConfiguration.class)
public class FootballApiConfiguration {
	@Value("${footballapi.url}")
	private String url;

	@Bean
	public FootballApiClient footballApiClient(Decoder decoder) {
		return Feign
					.builder()
					.client(feignClient())
					.contract(new SpringMvcContract())
					.encoder(feignEncoder())
					.decoder(responseDecoder(decoder))
					.errorDecoder(new FeignErrorDecoder())
					.target(FootballApiClient.class, url);
	}
	
	private Encoder feignEncoder() {
		ObjectFactory<HttpMessageConverters> objectFactory = () -> new HttpMessageConverters(new MappingJackson2HttpMessageConverter());
		return new SpringEncoder(objectFactory);
	}

	private Decoder responseDecoder(final Decoder delegate) {
		return new Decoder() {
			@Override
			public Object decode(Response response, Type type) throws IOException {
				return delegate.decode(response, type);
			}
		};
	}

	private Client feignClient() {
		return new SimpleHttpClient(new Client.Default(null, null));
	}
}
