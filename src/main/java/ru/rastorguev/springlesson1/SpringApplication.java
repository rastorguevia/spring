package ru.rastorguev.springlesson1;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import ru.rastorguev.springlesson1.aop.service.Flow;

@SpringBootApplication
@EnableAspectJAutoProxy
@PropertySource("classpath:application.properties")
public class SpringApplication {

    public static void main(String[] args) {

        ApplicationContext ac = new AnnotationConfigApplicationContext(SpringApplication.class);

        Flow flow = ac.getBean(Flow.class);
        flow.run(1);
        flow.run(2);
        flow.run(2);
        flow.run(3);
        flow.run(4);

        flow.run(5);

    }

}
