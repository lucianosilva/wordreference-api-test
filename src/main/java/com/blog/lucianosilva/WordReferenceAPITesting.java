/**
 * Copyright (C) IT Convergence.  All rights reserved.
 * IT Convergence - http://www.itconvergence.com
 */
package com.blog.lucianosilva;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.util.logging.Logger;

import com.google.gson.Gson;

/**
 * @author luciano
 * 
 */
public class WordReferenceAPITesting {

	private static Logger logger = Logger.getLogger("WordReferenceAPITesting");

	/**********************************************
	 * 
	 **********************************************/
	private static final String APIKEY 		= "7354b";
	private static final String APIVERSION 	= "0.8";
	//
	private static final String urlWordReferenceApi = "http://api.wordreference.com/"+ APIVERSION + "/" + APIKEY +"/json";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		logger.info("Iniciando...");
		//
		String langFrom 	= "en";
		String langTo 		= "pt";
		String word 		= "blow";

		logger.info("From: " + langFrom);
		logger.info("To: " + langTo);
		//
		String lang 		= langFrom + langTo;

		//
		WordReferenceAPITesting test = new WordReferenceAPITesting();

		try {
			test.translate(lang, word);
		} catch (Exception e) {
			logger.info("ERROR: " + e.getMessage() );
		}

	}

	/**
	 * 
	 * @param lang
	 * @param word
	 * @throws Exception
	 */
	public void translate( String lang, String word ) throws Exception{

		String url = urlWordReferenceApi + "/" + lang + "/" + word;
		logger.info("URL API call : " + url);
		
		this.run(url);
	}
	
	private void run( String path ){
		
		InputStream source = retrieveStream(path);
		Gson gson = new Gson();
		Reader reader = new InputStreamReader(source);

		Object response = gson.fromJson(reader, Object.class);

		logger.info( "response: ");
		logger.info(" "+  response );
	}
	
	
	/**
	 * 
	 * @param url
	 * @return
	 */
	private InputStream retrieveStream(String url) {

		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet getRequest = new HttpGet(url);

		try {

			HttpResponse getResponse = client.execute(getRequest);
			final int statusCode = getResponse.getStatusLine().getStatusCode();

			if (statusCode != HttpStatus.SC_OK) {
				// Log.w(getClass().getSimpleName(), "Error " + statusCode +
				// " for URL " + url);
				return null;
			}

			HttpEntity getResponseEntity = getResponse.getEntity();
			return getResponseEntity.getContent();

		} catch (IOException e) {
			getRequest.abort();
			// Log.w(getClass().getSimpleName(), "Error for URL " + url, e);
		}

		return null;
	}

}
