# Lottoland
The Test Challenge from Lottoland
# Developer Guide
1. Build and Start application: `mvn clean spring-boot:run`
2. Run Test Report: `mvn clean site` and report should be in `../target/site/index.html`
3. Run Maven command with _tests_ but without _checkstyle_: `mvn clean spring-boot:run -Pdev-test`
4. Run Maven command without _tests_ and _checkstyle_: `mvn clean spring-boot:run -Pdev`
> *Test Server starts on _8080_ port!*
