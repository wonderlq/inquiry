package com.merlin.inquery;

import com.merlin.inquery.model.WfQueryModel;
import com.merlin.inquery.service.SendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {
/*
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }*/

    @Autowired
    SendService sendService;

    @Override
    public void run(String... args) throws Exception {
        WfQueryModel wfQueryModel = new WfQueryModel();
        wfQueryModel.setCode("111");
        wfQueryModel.setDy("1");
        wfQueryModel.setFdjh6w("ss");
        wfQueryModel.setHplex("cc");
        wfQueryModel.setVerifyCode("ccc");
        sendService.getIndexCookie(wfQueryModel);

        sendService.getVerifyCode(wfQueryModel);
        System.out.println(wfQueryModel.getVerifyCode());
    }


    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args).close();
    }
}

