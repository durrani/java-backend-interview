
java -version

#Powershell
$env:JAVA_HOME="D:\InstalledApps\Java\jdk-17.0.3.1"
$env:Path="$env:JAVA_HOME\bin;$env:Path"

#Command Prompt
set JAVA_HOME=D:\InstalledApps\Java\jdk-17.0.3.1
set PATH=%JAVA_HOME%\bin;%PATH%

java -version

git checkout -b "mohammed_durrani_branch"

gradlew clean build test

gradlew test

gradlew.bat test --tests "com.nutrition.controller.NutritionControllerTest.should_return_all_items"

gradlew bootRun



