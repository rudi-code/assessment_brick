/**
 * 
 */
package com.brick.assessment_brick.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

/**
 * @author rudi_
 * Jun 12, 2022
 */
public interface ProductService {

	/**
	 * @return
	 * @throws IOException 
	 * @throws CsvRequiredFieldEmptyException 
	 * @throws CsvDataTypeMismatchException 
	 */
	void saveToCsv();

}
