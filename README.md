MrMutator
=========

MrMutator is a simple toy, which uses a genetic or evolutionary approach to trying to approximate a target image by simply combining polygons of different colors.

Simplest way to get started is to install [Maven](http://maven.apache.org/), and issue the following command in the project directory (the same directory as the `pom.xml` file).

    mvn clean scala:run

Maven will download all the dependencies automatically and run the application.

The application will start by asking you to choose an image file, and after you have chosen an image, will start the process of trying to approximate the image you chose. You will be presented with two frames: one showing the target image, and another one showing the current approximation.
