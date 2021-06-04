package devops.kindergarten.server.config;

import com.google.common.base.Predicates;
import com.sun.xml.bind.v2.schemagen.xmlschema.Appinfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurationSupport {
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                .title("Demo")
//                .description("API EXAMPLE")
//                .build();
//    }
//    @Bean
//    public Docket api() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .groupName("swg-group1")//빈설정을 여러개 해줄경우 구분하기 위한 구분자.
//                .apiInfo(this.apiInfo())//스웨거 설명
//                .select()//apis, paths를 사용하주기 위한 builder
//                .apis(RequestHandlerSelectors.basePackage("com.sgw.web.controller"))//탐색할 클래스 필터링
//                .paths(PathSelectors.any())//스웨거에서 보여줄 api 필터링
//                .build();
//    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        //enabling swagger-ui part for visual documentation
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
    private static final Contact DEFAULT_CONTACT = new Contact("JerryK",
        "", "kskyung0624@gmail.com");
    private static final ApiInfo DEFAULT_API_INFO = new ApiInfo("DevOps Kindergarten",
            "DevOpsKindergarten API Document", "1.0", "urn:tos",
            DEFAULT_CONTACT, "Apache 2.0",
            "https://www.apache.org/licenses/LICENSE-2.0", new ArrayList<>());

    private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES = new HashSet<>(
            Arrays.asList("application/json","application/xml")
    );

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(DEFAULT_API_INFO)
                .produces(DEFAULT_PRODUCES_AND_CONSUMES)
                .consumes(DEFAULT_PRODUCES_AND_CONSUMES);
    }
}
