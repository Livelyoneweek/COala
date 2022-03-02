package com.clone.finalProject.controller;


import com.clone.finalProject.domain.Greeting;
import com.clone.finalProject.domain.HelloMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class GreetingController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(500); // simulated delay
        return new Greeting("Message, " + message.getName());
    }


    @MessageMapping("/hello2")
    @SendTo("/queue/greetings2")
    public Greeting greeting2(HelloMessage message) throws Exception {
        Thread.sleep(500); // simulated delay
        return new Greeting("Message, " + message.getName());
    }

    @MessageMapping("/hello3")
    @SendTo("/queue/greetings3")
    public Greeting greeting3(HelloMessage message) throws Exception {
        Thread.sleep(500); // simulated delay
        return new Greeting("Message, " + message.getName());
    }


}
