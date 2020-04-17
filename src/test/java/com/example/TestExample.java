package com.example;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.images.builder.ImageFromDockerfile;


public class TestExample {

   @Test
   public void test() throws InterruptedException {
      List<String> entryPoint = new ArrayList<>();
      entryPoint.add("bin/server.sh");

      ImageFromDockerfile image = new ImageFromDockerfile();
      image.withFileFromFile("bin", new File(System.getProperty("target-dir")));
      image.withDockerfileFromBuilder(builder -> {
         builder.from("jboss/base-jdk:11");
         builder.copy("bin", "/opt/jboss/bin");
         builder.entryPoint(entryPoint.toArray(new String[]{}));
         builder.build();
      });
      GenericContainer container = new GenericContainer(image);
      container.withEnv("MAGIC_NUMBER", "42");
      container.start();
      container.stop();
   }
}
