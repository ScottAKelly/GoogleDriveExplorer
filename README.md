# Google Drive Explorer

Overview
--------
This application is designed as a tool to explore documents and folders hosted in Google Drive. It provides navigation features for exploring any directory structure in a specific Drive. It also allows searching documents and folders by name. Searching can either be done by exact match or by containing name.

Setting up the application
=====================================================

* **Prerequisites**
  * At least java 8, 11 preferred. Install here [Java Installation Help](https://java.com/en/download/help/download_options.html)
    * To check your current version, use command: `java --version`
  * Latest version of [Gradle](https://gradle.org/install/). Check version by using command: `gradle -v`
  * [Apache Tomcat](http://tomcat.apache.org/) You can find the version of Tomcat at the installation directory.

**Setting up the project**
* You will need to set up OAuth2 Credentials for the Google project. You can create an OAuth2 Credential by following [this link](https://developers.google.com/drive/api/v3/about-auth). I you already know how, you can go straight to the [Google API Console](https://console.developers.google.com/)
  * When creating the credential, you will need to add a redirect url for `http://localhost:8080/drive-auth/receive-code`. 
  * Once finished, you will need to get a json file of the credentials. It will need to be renamed 'credentials.json' once downloaded.
  * If it does not already exist, create a directory in the root of the project `src/main/resources` and place the credentials.json in this directory.
* For now, a root directory id is needed. You can get this by going to the specific Google Drive, and going to the root directory. Once in the root directory, you can get the id from the url. And example URL may be, drive.google.com/drive/u/2/folders/1l3QbzLedEHl_FKp-hWJTzvWFFtH4kxZF, where the root id is `1l3QbzLedEHl_FKp-hWJTzvWFFtH4kxZF`.
  * Once you have the root id, find the file 'DriveService.java'. It should be located at `src/main/java/com/example/driveexplorerassessment/services`
  * In the DriveService.java file, find the getter method for `driveRootFolderId`. Replace the return string with this root id from Drive.
* On the first run, the server will get hung up on the Google Authentication pipeline. This is because the flow in the code has not been changed to work with a UI for a web app. It came from a console (CLI) app.
  * When running the first time, there will be a log in the server log. It will be a URL to go to Google Authentication. Open the url in a browser and consent to the project access.
  * Once this is done, the build will complete, and you should be up and running.
  * The auth pipeline will save the encoded auth token to a tokens in the Apache Tomcat directory where the container is being served from. You will not need to do this step again unless this token file is deleted.

