package ru.rastorguev.springlesson1;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.rastorguev.springlesson1.ioc.Flow;

@SpringBootApplication
public class SpringApplication {

    public static void main(String[] args) {

        ApplicationContext ac = new AnnotationConfigApplicationContext(SpringApplication.class);

        Flow flow = ac.getBean(Flow.class);
        flow.run(1);
        flow.run(2);
        flow.run(2);
        flow.run(3);
        flow.run(4);

    }

}
