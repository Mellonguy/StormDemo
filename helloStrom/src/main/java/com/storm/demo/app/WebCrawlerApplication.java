package com.storm.demo.app;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(scanBasePackages={"com.storm.demo.*"})
@EnableAutoConfiguration(exclude= {DataSourceAutoConfiguration.class})
public class WebCrawlerApplication {
	public static void main(String[] args) throws ClientProtocolException, IOException {
		SpringApplication.run(WebCrawlerApplication.class, args);
//		crawlerData() ;

	}
}
