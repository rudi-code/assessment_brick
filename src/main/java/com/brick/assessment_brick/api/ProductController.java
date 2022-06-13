/**
 * 
 */
package com.brick.assessment_brick.api;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.brick.assessment_brick.service.ProductService;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

/**
 * @author rudi_
 * Jun 12, 2022
 */

@RestController
public class ProductController {
	
	@Autowired
	private ProductService productService;

	@GetMapping("/success")
	private String getSuccess() {
		return "Success";
	}
	
	@GetMapping("/get-product")
	private void getClient(HttpServletResponse servletResponse) {
//		servletResponse.setContentType("text/csv");
//		servletResponse.addHeader("Content-Disposition","attachment; filename=\"products.csv\"");
		productService.saveToCsv();
	}
}
