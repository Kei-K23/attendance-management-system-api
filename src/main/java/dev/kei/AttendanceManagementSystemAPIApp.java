package dev.kei;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("file:${user.dir}/.env")
public class AttendanceManagementSystemAPIApp
{
    public static void main( String[] args )
    {
        SpringApplication.run(AttendanceManagementSystemAPIApp.class, args);
    }
}
