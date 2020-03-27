package org.mvnsearch.boot.zipkin.demo;

import io.rsocket.RSocket;
import io.rsocket.RSocketFactory;
import io.rsocket.uri.UriTransportRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.sleuth.zipkin2.ZipkinAutoConfiguration;
import org.springframework.context.annotation.Bean;
import zipkin2.reporter.Sender;
import zipkin2.reporter.rsocket.RSocketSender;

@SpringBootApplication
public class SpringBootZipkinDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootZipkinDemoApplication.class, args);
    }

    @Bean
    public RSocket rsocket(@Value("${spring.zipkin.base-url}") String zipkinBaseUrl) {
        return RSocketFactory
                .connect()
                .dataMimeType("application/vnd.google.protobuf")
                .transport(UriTransportRegistry.clientForUri(zipkinBaseUrl))
                .start()
                .block();
    }

    @Bean(ZipkinAutoConfiguration.SENDER_BEAN_NAME)
    public Sender zipkinRSocketSender(RSocket rsocket) {
        return new RSocketSender(rsocket);
    }
}
