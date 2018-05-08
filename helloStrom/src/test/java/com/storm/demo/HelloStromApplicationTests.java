package com.storm.demo;

import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloStromApplicationTests {

	@Test
	public void contextLoads() throws IOException {

		Document _doc;
		StringBuffer docSB;
		List<Object> _listA = null;
		_doc =  Jsoup.connect("https:www.daum.net").get();
	_doc.select("div.ah_list.PM_CL_realtimeKeyword_list_base");

	}

}
