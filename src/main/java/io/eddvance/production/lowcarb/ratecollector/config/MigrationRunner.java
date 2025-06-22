package io.eddvance.production.lowcarb.ratecollector.config;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MigrationRunner {
    @Bean
    ApplicationRunner runMigrations(R2dbcMigration migration) {
        return args -> migration.migrate()
                .doOnSuccess(v -> System.out.println("✅ Base de données prête !"))
                .doOnError(e -> System.err.println("❌ Erreur migration: " + e))
                .subscribe();
    }
}
