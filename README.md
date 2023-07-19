# Template jdbcTemplate con HikariCp and feature toggles

***
# Notes

This `Feature Toggle Sdk` repository contains an SDK developed in Java 8 that provides classes and configurations for using
Unleash with toggle names, tenants, companies, and even with custom fields. To set up the `Feature Toggle Sdk` repository, follow
these steps:

**if you want to compile and use the SDK in local:**
1. Clone the repository.
2. Open a terminal or command prompt and navigate to the cloned repository's directory.
3. Build and publish the SDK to the local Maven repository by running the following command:
**don't forget to set java8 for build sdk**

```shell
# If you have sdk man:

sdk use java 8.332.08.1 // or similar
```

Load the wrapper:
```shell
gradle wrapper
```

Publish sdk to local repo
```shell
./gradlew clean build publishToMavenLocal --info
```

Publish sdk to nexus repo
```shell
./gradlew clean build publish --info
```

This command will create a JAR file in the local `.m2` repository.


## 

***

## Installation of the Workia feature flag sdk in a microservice with java:

1. Add Unleash client into your build.gradle file:
```groovy
dependencies {
    implementation 'io.getunleash:unleash-client-java:8.2.0'
}
```

2. Example adding Workia sdk from nexus for maven applications:

Add this dependency in your pom configuration:

```xml
<dependency>
   <groupId>com.hcwork.feature-toggles</groupId>
   <artifactId>hcwork-feature-toggles-sdk</artifactId>
   <version>1.0-SNAPSHOT</version>
</dependency>
```

For gradle
```groovy
dependencies {
    implementation 'com.hcwork.feature-toggles:hcwork-feature-toggles-sdk:1.0-SNAPSHOT'
}
```

**For Maven: you should to configure a settings.xml with the nexus repository access:**

link to [settings.xml]()

Run build application (mvn or gradle) and check if the sdk is already downloaded

```File Path:
~/.m2/repository
└── com
    └── hcwork
        └── feature-toggles
            └── hcwork-feature-toggles-sdk
                └── 1.0-SNAPSHOT
        
```

## Mode of Use

1. Set the `scanBasePackages` in your `MainApplication` class:

Note: Note that  com.project is the actual applicaiton not the sdk

```java
@SpringBootApplication(scanBasePackages = {"com.project", "com.hcwork"})
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}
```

2. Set the following properties in your application properties or application.yml.
   Set the necessary configuration for db, unleash etc:

```yaml
feature:
  apiUrl: http://URL-SERVER-UNLEASH:4242/api
  appName: APP-NAME
  clientSecret: "default:development.HASH"
  instanceId: "1"
  toggle:
    service: "unleash"
```

Example:
link to [application-local.yml](src/main/resources/application-dev.yml)

3. Recommended use of the service:

a. Inject the FeatureToggleService and UnleashConfiguration into your service class:

```java
@Service
public class YourServiceClass {

    private final FeatureToggleService featureToggleService;
    private final UnleashConfiguration unleashConfiguration;
    
    public YourServiceClass(RepositoryX repo,
                            FeatureToggleService toggleService,
                            UnleashConfiguration unleashConfiguration) {
        this.movieRepository = movieRepository;
        this.featureToggleService = toggleService;
        this.unleashConfiguration = unleashConfiguration;
    }

    public List<Algo> getByTenantAndCompany(String tenantId, String companyId) {
        boolean toggleIsActive = featureToggleService.isFeatureToggleActive(
                TogglesNamesEnum.TENANT_COMPANY_TOGGLE,
                new FeatureContextImpl(tenantId, companyId));

        return process(toggleIsActive);
    }
}
```

b. If needed, you can create your own ToggleEnum for custom properties and toggles names:

```java
public enum ToggleAdHoc implements TogglesNames {
    CUSTOM_TENANT_COMPANY_TOGGLE_NAME("premiumByTenant"),
    CUSTOM_TENANT_TOGGLE_NAME("otherTenantToggle");

    private final String toggleName;

    ToggleAdHoc(String toggleName) {
        this.toggleName = toggleName;
    }

    @Override
    public String getToggleName() {
        return toggleName;
    }
}
```

c. Use the custom toggle properties in your service:

```java
public List<Algo> getByTenantAndCompanyWithCustomProperties(String tenantId, String companyId) {
    boolean toggleIsActive = unleashConfiguration.unleash().isFeatureToggleActive(
            ToggleAdHoc.CUSTOM_TENANT_COMPANY_TOGGLE_NAME,
            Map.of("someProperty1", tenantId, "someProperty2", companyId));

    return process(toggleIsActive);
}
```

**_Note: Replace YourServiceClass with the actual name of your service class._**

***
## Optional: Special Cases
_**If you plan to extend your configuration or you need to adapt it to another feature flag tool**_

(**Optional**) In your application create a config folder with:

```Folder Structure:
└── config
    ├── toggles
    │   └── FeatureToggleConfiguration.java
    └── unleash
        └── UnleashConfiguration.java
```

link to [FeatureToggleConfiguration.java](/src/main/java/com/hcwork/config/toggles/FeatureToggleConfiguration.java)

link to [UnleashConfiguration.java](/src/main/java/com/hcwork/config/unleash/UnleashConfiguration.java)

## Compile application (download the sdk from Nexus repository):

```shell
./mvnw clean package 
# or 
./mvnw clean package -X -Dile=hcwork-feature-toggles-sdk-1.0.jar -DgroupId=com.hcwork.feature-toggles -DartifactId=hcwork-feature-toggles-sdk -Dversion=1.0-SNAPSHOT -Dpackaging=jar -DrepositoryId=nexus -Durl=http://nexus.sde.workia.solutions:8081/repository/maven-snapshots/ -s ~/.m2/settings.xml
```

**For Gradle: you should modify your build.gradle:**

```groovy
repositories {
   mavenCentral() // This is the default Maven Central repository, you can remove it if you want to use only the custom Nexus repository
   maven {
      url 'http://nexus.example:7777/repository/maven-snapshots/'
   }
}
// ...
dependencies {
   implementation 'com.hcwork:feature-toggle-sdk:1.0-SNAPSHOT'
}

...
repositories {
   maven {
      name = 'nexus'
      allowInsecureProtocol = true
      url = repoUrl
      credentials {
         username = repoUser //'your-nexus-username'
         password = repoPassword //'your-nexus-password'
      }
      // Uncomment the following line to disable SSL verification (not recommended for production use)
      // sslVerificationEnabled = false
   }
}
```

And run

```shell
# if you dont have gradlew
gradle wrapper
# and then
./gradlew clean build --info
```


If everything is ok, you should see the sdk already downloaded

```File Path:
~/.m2/repository
└── com
    └── hcwork
        └── feature-toggles
            └── hcwork-feature-toggles-sdk
                └── 1.0-SNAPSHOT
                    └── hcwork-feature-toggles-sdk-1.0-20230719.134601-2.jar
```




# Diagram
![diagram](img/img.png)

# Exercise
- Add the ability to edit movies
- Add `actor` table and associate them with movies
  You will need to create a new migration called: `V1__ActorTable.sql` and the following sql to create the actor table

```sql
CREATE TABLE actor
(
    id    bigserial primary key,
    name  TEXT NOT NULL,
    movie bigint REFERENCES movie (id),
    unique (name, movie)
);
```

- Hikari
- Flyway: You must create the data base at first time, then any needed table is automatically created when run the app


```xml
<dependency>
    <groupId>org.flywaydb</groupId>
    <artifactId>flyway-core</artifactId>
</dependency>
```

Wrapper JDBC:

```xml
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jdbc</artifactId>
		</dependency>
```

Curl :

```shell
curl -X 'POST' \
  'http://localhost:8080/api/v1/movies/add' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": 1,
  "name": "matrix",
  "releaseDate": "2019-01-30"
}'
```

docker build -t movies . 
docker compose up -d