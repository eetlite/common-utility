package cz.osslite.common.utility;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// @com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand(threadPoolKey = "resourceLoader")
@Component
public class UrlLoader {

	@Autowired
	private Logger log;

	public String get(String url) throws IOException {
		URI resource;
		try {
			resource = new URI(url);
		} catch (URISyntaxException e) {
			log.error("", e);
			return null;
		}
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet method = new HttpGet(resource);

		log.info("downloading http get: {}", resource);

		CloseableHttpResponse response = httpclient.execute(method);
		HttpEntity entity = response.getEntity();
		String html = IOUtils.toString(entity.getContent(), Charset.defaultCharset());

		log.info("downloaded http get: {}", resource);
		return html;
	}

	public String post(String url, Map<String, String> postParams) throws IOException {
		URI resource;
		try {
			resource = new URI(url);
		} catch (URISyntaxException e) {
			log.error("", e);
			return null;
		}
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost method = new HttpPost(resource);

		List<NameValuePair> formParams = new ArrayList<NameValuePair>();
		for (String key : postParams.keySet()) {
			formParams.add(new BasicNameValuePair(key, postParams.get(key)));
		}
		method.setEntity(new UrlEncodedFormEntity(formParams, Consts.UTF_8));

		log.info("downloading http post: {}", resource);
		CloseableHttpResponse response = httpclient.execute(method);
		HttpEntity entity = response.getEntity();
		String html = IOUtils.toString(entity.getContent(), Charset.defaultCharset());
		return html;
	}

}