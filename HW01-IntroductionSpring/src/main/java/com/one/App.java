package com.one;

import com.one.service.QuizService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context
            = new ClassPathXmlApplicationContext("/spring-context.xml");
        context.getBean(QuizService.class).startQuiz();
    }
}
