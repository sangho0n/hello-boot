package tobyspring.helloboot;

//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@SpringBootApplication
public class HellobootApplication {

	public static void main(String[] args) {
		//SpringApplication.run(HellobootApplication.class, args);

		ServletWebServerFactory serverFactor = new TomcatServletWebServerFactory();
		WebServer webServer = serverFactor.getWebServer(servletContext -> {

			HelloController helloController = new HelloController();

			servletContext.addServlet("frontController", new HttpServlet() {

				@Override
				protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
					// 인증, 보안, 다국어처리 등 공통기능들을 프론트 컨트롤러에서
					if(req.getRequestURI().equals("/hello") && req.getMethod().equals(HttpMethod.GET.name()))
					{
						String name = req.getParameter("name");

						String ret = helloController.hello(name);

						resp.setStatus(HttpStatus.OK.value());
						resp.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);
						resp.getWriter().println(ret); // binding
					}
					else if(req.getRequestURI().equals("/user"))
					{
						//
					}
					else
					{
						resp.setStatus(HttpStatus.NOT_FOUND.value());
					}
				}
			}).addMapping("/*");


		});
		// create servlet container
		webServer.start();
	}

}
