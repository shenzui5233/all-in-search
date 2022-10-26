package work.jimmmy.allinsearch;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication(scanBasePackages = {"work.jimmmy.allinsearch"}) // spring注解扫描
@MapperScan("work.jimmmy.allinsearch.dao") // mybatis dao扫描
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AllinsearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(AllinsearchApplication.class, args);
    }

}

