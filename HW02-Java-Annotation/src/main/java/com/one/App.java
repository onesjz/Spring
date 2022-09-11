package com.one;

import com.one.service.QuizService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
public class App {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext
            = new AnnotationConfigApplicationContext(App.class);
        applicationContext.getBean(QuizService.class).startQuiz();
    }
}
