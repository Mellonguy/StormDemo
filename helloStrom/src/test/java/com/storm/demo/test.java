/*
 * Copyright ⓒ [2018] KTH corp.All rights reserved.
 *
 * This is a proprietary software of KTH corp, and you may not use this file except in
 * compliance with license with license agreement with KTH corp. Any redistribution or use of this
 * software, with or without modification shall be strictly prohibited without prior written
 * approval of KTH corp, and the copyright notice above does not evidence any actual or
 * intended publication of such software.
 */
package com.storm.demo;

import java.io.IOException;
import java.util.Arrays;

import org.apache.storm.shade.org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * test.java
 *
 * @author Kwon Ki Tae
 * @version 1.0.0
 * @see
 * @since 1.0
 */
public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String name = "";
		Document contents = null;
		try {
			contents = Jsoup.connect("http://www.naver.com").get();
			String[] sb = (contents.select("span.ah_k").text()).split(" ");
			String cc = Arrays.toString(sb);
		System.out.println(cc);
			System.out.println( StringUtils.countMatches(cc, "기은세") );

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String a = "2. getRawContents >>>  jsonobject >>> siteName >>>naver\n" +
				"1. @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ https://www.daum.net\n" +
				"2. getRawContents >>>  jsonobject >>> siteName >>>daum\n" +
				"3. WebCrawlerSpout textTuple >>>>>>>>>>>>>>>>>>>>>> naver\n" +
				"3. WebCrawlerSpout textTuple >>>>>>>>>>>>>>>>>>>>>> daum" ;

		System.out.println(a.contains("3."));



	}

}
