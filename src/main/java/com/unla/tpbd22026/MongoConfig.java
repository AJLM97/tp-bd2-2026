package com.unla.tpbd22026;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import io.github.cdimascio.dotenv.Dotenv;

@Configuration
public class MongoConfig {

    @Bean
    public MongoClient mongoClient() {
        Dotenv dotenv = Dotenv.load();

        String connectionString = "mongodb+srv://" + dotenv.get("USER_DB") + ":" +
                dotenv.get("PASSWORD_DB") + "@" +
                dotenv.get("SERVER_DB") +
                "/tp?retryWrites=true&w=majority&appName=TP-BaseDeDatos2";


        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(serverApi)
                .build();

        return MongoClients.create(settings);
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), "tp");
    }
}

