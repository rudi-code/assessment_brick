/**
 * 
 */
package com.brick.assessment_brick.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.brick.assessment_brick.service.ProductService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.ColumnPositionMappingStrategyBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author rudi_
 * Jun 12, 2022
 */

@Service
public class ProductServiceImpl implements ProductService {

	@Value("${website.urls}")
	private String uri;
	
	@Override
	public void saveToCsv() {
		// TODO Auto-generated method stub
		//mapping data 
		List<Smartphone> listSmartphone = mappingData();
		convertToCsv(listSmartphone);
		
//		//convert to csv
//		try {
////			Writer writer = Files.newBufferedWriter(Paths.get("C:/Users/rudi_/Downloads/product.csv"));
//			FileWriter writer = new 
//                    FileWriter("C:/Users/rudi_/Downloads/product.csv");
//			// column name in order
//            ColumnPositionMappingStrategy mappingStrategy=
//                        new ColumnPositionMappingStrategy();
//            mappingStrategy.setType(Smartphone.class);
//		    
//		 // Arrange column name as provided in below array.
//            String[] columns = new String[] 
//                    { "Product Name", "Description", "Product Link", "Price",  "Rating", "Name Of Store"};
//            mappingStrategy.setColumnMapping(columns);
//
//         // Creating StatefulBeanToCsv object
//            StatefulBeanToCsvBuilder<Smartphone> builder=
//                        new StatefulBeanToCsvBuilder(writer);
//            StatefulBeanToCsv beanWriter = 
//          builder.withMappingStrategy(mappingStrategy).build();
//  
//            // Write list to StatefulBeanToCsv object
//            beanWriter.write(listSmartphone);
//  
//            // closing the writer object
//            writer.close();
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
		
		
		 
	}

	/**
	 * @param listSmartphone
	 */
	private void convertToCsv(List<Smartphone> listSmartphone) {
		// TODO Auto-generated method stub
		System.out.println("SIZE  ::: "+listSmartphone.size());
		try {
			FileWriter writer = new FileWriter("C:/Users/rudi_/Downloads/product.csv");
			
			CSVWriter csvWriter = new CSVWriter(writer, 
					CSVWriter.DEFAULT_SEPARATOR, 
					CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);
			
			List<String[]> data = new ArrayList<String[]>();
			String[] header = new String[] { "Product Name", "Description", "Product Link", "Price",  "Rating", "Name Of Store"};
			data.add(header);
			
			
			for(Smartphone smp : listSmartphone) {
				String[] arr = new String[] {smp.product_name, smp.getDescription(), 
						smp.getProduct_link(),smp.getRating(), smp.getRating(), 
						smp.getName_of_store()};
				data.add(arr);
			}
			
			csvWriter.writeAll(data);
            writer.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	/**
	 * @return
	 */
	private List<Smartphone> mappingData() {
		// TODO Auto-generated method stub
		List<Smartphone> listSmartphone = new ArrayList();
		try {

            //loading the HTML to a Document Object
            Document document = Jsoup.connect(uri).get();
            List<Element> listElement = new ArrayList<>();
            //Selecting the element which contains the ad list
            Elements elementParent = document.getElementsByClass("css-jza1fo");   
            for(Element element : elementParent) {
            	Elements elementChild = element.getElementsByClass("css-12sieg3");
            	for(Element element1 : elementChild){
            		listElement.add(element1);
            	}
            }

            for(Element el : listElement) {
            	Elements elements = el.getElementsByTag("a");
            	for (Element ads: elements) {
	                Smartphone response = new Smartphone(); 
	                if (!StringUtils.isEmpty(ads.attr("title")) ) {
	                    //mapping data to the model class
	                	response.setProduct_name(ads.attr("title"));
	                	response.setProduct_link(ads.attr("href"));
	                	response.setPrice(ads.select("[data-testid=\"spnSRPProdPrice\"]").text());
	                	response.setRating(ads.getElementsByClass("css-t70v7i").text());
	                	response.setName_of_store(ads.getElementsByClass("css-1rn0irl").text());
	                    response.setDescription("null");
	                }
	                if (response.getProduct_name() != null) listSmartphone.add(response);
	            }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
		return listSmartphone;
	}
	
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@ToString
	class Smartphone {
		private String product_name;
		private String description;
		private String product_link;
		private String price;
		private String rating;
		private String name_of_store;
				
	}
	



}
