/*
 * Copyright ⓒ [2018] KTH corp.All rights reserved.
 *
 * This is a proprietary software of KTH corp, and you may not use this file except in
 * compliance with license with license agreement with KTH corp. Any redistribution or use of this
 * software, with or without modification shall be strictly prohibited without prior written
 * approval of KTH corp, and the copyright notice above does not evidence any actual or
 * intended publication of such software.
 */
package com.storm.demo.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloStormController {

	@RequestMapping("/hello")
	public String index(Model model,@RequestParam(value = "age", defaultValue = "Unknown", required = false) String age) {

		model.addAttribute("name", "SpringBlog from Millky");
		model.addAttribute("age", age);
		System.out.println("test");
		return "hello";
	}
}
