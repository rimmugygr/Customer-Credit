package springboot.credit.configuration;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("client.api.url")
@Data
@Setter
@Getter
@NoArgsConstructor
public class ClientApiConfig {
    private String customer;
    private String product;
}
