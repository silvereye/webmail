package webmail.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import webmail.webservice.client.CalendarClient;
import webmail.webservice.client.FileClient;
import webmail.webservice.client.FolderClient;
import webmail.webservice.client.WebmailClient;

@Configuration
public class WebServiceConfiguration {

	@Bean
	public Jaxb2Marshaller marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setContextPath("webmail.wsdl");
		return marshaller;
	}

	@Bean
	public WebmailClient webmailClient(Jaxb2Marshaller marshaller) {
		WebmailClient client = new WebmailClient();
		client.setDefaultUri("http://localhost:11235/ws/webmailFolder.wsdl");
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;
	}

	@Bean
	public FolderClient folderClient(Jaxb2Marshaller marshaller) {
		FolderClient client = new FolderClient();
		client.setDefaultUri("http://localhost:8080/ws/documentModule.wsdl");
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;
	}
	@Bean
	public FileClient fileClient(Jaxb2Marshaller marshaller) {
		FileClient client = new FileClient();
		client.setDefaultUri("http://localhost:8080/ws/documentModule.wsdl");
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;
	}
	@Bean
	public CalendarClient calendarClient(Jaxb2Marshaller marshaller) {
		CalendarClient client = new CalendarClient();
		client.setDefaultUri("http://localhost:11235/ws/calendar.wsdl");
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;
	}
}