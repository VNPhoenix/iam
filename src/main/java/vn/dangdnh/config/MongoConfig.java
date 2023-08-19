package vn.dangdnh.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import vn.dangdnh.converter.zoneddatetime.ZonedDateTimeReadConverter;
import vn.dangdnh.converter.zoneddatetime.ZonedDateTimeWriteConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.ArrayList;
import java.util.List;

@Configuration
@PropertySource("file:config/mongodb.properties")
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Value("${mongodb.connectionString}")
    private String connectionString;

    @Value("${mongodb.databaseName}")
    private String databaseName;

    @Value("#{environment.MONGO_USERNAME}")
    private String username;

    @Value("#{environment.MONGO_PASSWORD}")
    private String password;

    @Override
    protected String getDatabaseName() {
        return databaseName;
    }

    @Override
    public MongoCustomConversions customConversions() {
        List<Converter<?, ?>> converters = new ArrayList<>();
        converters.add(new ZonedDateTimeReadConverter());
        converters.add(new ZonedDateTimeWriteConverter());
        return new MongoCustomConversions(converters);
    }

    @Override
    protected boolean autoIndexCreation() {
        return true;
    }

    @Override
    public MongoClient mongoClient() {
        ConnectionString cns = new ConnectionString(connectionString);
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(cns)
                .build();
        return MongoClients.create(settings);
    }
}
