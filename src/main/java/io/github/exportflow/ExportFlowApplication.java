package io.github.exportflow;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("io.github.exportflow.mapper")
public class ExportFlowApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExportFlowApplication.class, args);
    }

}
