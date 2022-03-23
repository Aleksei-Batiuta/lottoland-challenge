# Lottoland
The Test Challenge from Lottoland
![](docs/images/web.gif)
# Developer Guide
1. Build and Start application: `mvn spring-boot:run`
2. Run Test Report: `mvn site` and report should be in `../target/site/index.html`. The actual report of the last release is on [Project Page](https://aleksei-batiuta.github.io/lottoland-challenge/) ![](docs/images/site.gif)
3. Run Maven command with _tests_ but without _checkstyle_: `mvn spring-boot:run -Pdev-test`
4. Run Maven command without _tests_ and _checkstyle_: `mvn spring-boot:run -Pdev`
> *Test Server starts on **8080** port!*
5. Project has correct _maven_ `release:prepare` and `release:perform` goals since 'v1.10'
6. Docker
   * Build image `mvn spring-boot:build-image`
   * Run Docker image `docker-compose -f target/docker/docker-compose.yml up`
# Test Cases
1. Open 'http://localhost:8080/' to view the rounds' statistics, 'Play Round' and 'Restart Game'
2. Open 'Statistics' page by click to corresponding menu item and verify the data for all users
# TODO
1. There are test cases with `null` description
2. Some classes have no author in javadoc
3. No general description of major parts of functionality
4. No Test cases of HTML pages validation
5. No Application Load Test
6. No redirect to default page when URL/Path was not found
7. The error page should be more informative
8. After removing the @Data annotation from Entity/VO classes those Objects have not friendly `toString()` method