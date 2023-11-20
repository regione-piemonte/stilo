/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.stream.Stream;

public class Generator {

    private static final String DDL_PREFIX = "db-schema.";
    private static String outputDir;
    private static String dbTarget;
    private static String jdbcDialect;
    private static String jdbcUrl;
    private static String jdbcUser;
    private static String jdbcPassword;
    private static String persistenceUnit;

    public static void main(String[] args) {
        outputDir = "C:/sviluppo/ws-integrazioneActa/stilo-model/target/test/";//args[0];
        dbTarget = "oracle";//args[1];
        jdbcDialect = "oracle.jdbc.driver.OracleDriver";//+args[2];
        jdbcUrl = "jdbc:oracle:thin:@10.138.165.10:1521/STILODBPDB";// args[3];
        jdbcUser = "STILO_OWNER";//args[4];
        jdbcPassword = "MYPASS";//args[5];
        persistenceUnit = "STILOJPA";//args[6];
        createSchema();
    }

    private static void createSchema() {
        final File generatedDir = new File(outputDir);
        generatedDir.mkdir();
        Stream.of(generatedDir.listFiles((file) -> {
            return file.getName().startsWith(DDL_PREFIX + dbTarget.toLowerCase());
        })).forEach(f -> {
            try {
                Files.deleteIfExists(Paths.get(f.toURI()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        EntityManagerFactory factory = null;
        try {
            factory = Persistence.createEntityManagerFactory(persistenceUnit, getSchemaProperties());
            factory.createEntityManager().close();
        } finally {
            if (factory != null)
                factory.close();
        }
    }

    private static Properties getSchemaProperties() {
        Properties props = new Properties();

        props.setProperty("javax.persistence.jdbc.url", jdbcUrl);
        props.setProperty("javax.persistence.jdbc.user", jdbcUser);
        props.setProperty("javax.persistence.jdbc.password", jdbcPassword);
        props.setProperty("hibernate.dialect", jdbcDialect);

        props.setProperty("javax.persistence.schema-generation.scripts.action", "drop-and-create");
        props.setProperty("javax.persistence.schema-generation.scripts.create-target",
                outputDir + "/" + DDL_PREFIX + dbTarget.toLowerCase() + ".create.ddl");
        props.setProperty("javax.persistence.schema-generation.scripts.drop-target",
                outputDir + "/" + DDL_PREFIX + dbTarget.toLowerCase() + ".drop.ddl");

        return props;
    }

}
