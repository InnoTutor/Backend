/*
MIT License

Copyright (c) 2021 InnoTutor

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
package innotutor.innotutor_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class InnotutorBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(InnotutorBackendApplication.class, args);
    }

    /*
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/card/cv-card").allowedOrigins("https://innotutor.herokuapp.com");
                registry.addMapping("/card/request-card").allowedOrigins("https://innotutor.herokuapp.com");
                registry.addMapping("/session/session-formats").allowedOrigins("https://innotutor.herokuapp.com");
                registry.addMapping("/session/session-types").allowedOrigins("https://innotutor.herokuapp.com");
                registry.addMapping("/session/subjects").allowedOrigins("https://innotutor.herokuapp.com");
                registry.addMapping("/students-list").allowedOrigins("https://innotutor.herokuapp.com");
                registry.addMapping("/tutors-list").allowedOrigins("https://innotutor.herokuapp.com");
            }
        };
    }
     */
}
