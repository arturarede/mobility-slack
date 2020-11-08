package com.mobility.configuration;

import com.mobility.MobilitySlackApplication;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.restdocs.restassured3.RestDocumentationFilter;
import org.springframework.restdocs.snippet.Snippet;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestDocs
@AutoConfigureWireMock(port = 0)
@TestPropertySource(locations = "classpath:application-test.properties", properties = {"com.mobility.ip.base-url=http://localhost:${wiremock.server.port}"})
@ContextConfiguration(classes = {MobilitySlackApplication.class})
public abstract class AbstractTestConfiguration {

    @LocalServerPort
    protected int port;

    protected static final String HOST = "localhost";

    @Autowired
    protected RequestSpecification documentationSpec;

    protected OperationRequestPreprocessor modifyUrisPreprocessing() {
        return preprocessRequest(
                modifyUris()
                        .scheme("http")
                        .host(HOST)
                        .port(8080),
                prettyPrint());
    }

    protected OperationResponsePreprocessor formatResponse() {
        return preprocessResponse(
                modifyUris()
                        .scheme("http")
                        .host(HOST)
                        .port(8080),
                prettyPrint());
    }

    protected RestDocumentationFilter document(String name, Snippet... snippers){
        return com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.document(name, null, null, true, false, modifyUrisPreprocessing(), formatResponse(), snippers);
    }
}
