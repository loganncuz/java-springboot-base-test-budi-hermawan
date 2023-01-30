package act.base.test.budi.hermawan.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class SwaggerConfig implements WebMvcConfigurer {
    @Autowired
    private ObjectMapper objectMapper;// created elsewhere
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.stream().filter(c -> c instanceof MappingJackson2HttpMessageConverter).forEach(c -> {
            ((MappingJackson2HttpMessageConverter) c).setObjectMapper(this.objectMapper);
        });
    }
//    @Bean
//    public GroupedOpenApi api() {
//        System.out.println("Docket");
//        return GroupedOpenApi.builder()
//                .group("springshop-public")
//                .pathsToMatch("/public/**")
//                .build();
//    }

    @Bean
    public OpenAPI springShopOpenAPI() {
        Contact contact=new Contact();
        contact.setName("Logan Ncuz");
        contact.setUrl("logan_ncuz@yahooo.co.id");
        return new OpenAPI()
                .info(new Info().title("Spring Boot REST API")
                        .description("Spring Boot REST API for Document Application")
                        .version("1.0.0")
                        .contact(contact)
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("Base Test Source Documentation")
                        .url("https://github.com/loganncuz/java-springboot-base-test-budi-hermawan.git"));
    }



    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("**/**")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/swagger-ui/")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");


    }




}
