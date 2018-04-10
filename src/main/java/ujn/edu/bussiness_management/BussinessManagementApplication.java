package ujn.edu.bussiness_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@SpringBootApplication
@ComponentScan("ujn.edu.bussiness_management.*")
public class BussinessManagementApplication {

	public static void main(String[] args) {

		SpringApplication.run(BussinessManagementApplication.class, args);

	}

}
