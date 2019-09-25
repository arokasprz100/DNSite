# DNSite

## About the project
DNSite is a management website for PostgreSQL based database used by PowerDNS. Its main purpose is to allow making multiple changes to the data stored in database in a simple, fast and comfortable way. If you would like to learn more please read attached documentation.

This project has been tested on **Windows 10** and **Ubuntu**.

## Documentation
Documentation (in polish) can be found here:
* [User Documentation](https://github.com/agh-ki-io/DNSite/blob/master/documentation/userGuide/dokumentacja_uzytkownika.pdf)
* [Developer Documentation](https://github.com/agh-ki-io/DNSite/blob/master/documentation/technicalDocumentation/dokumentacja_techniczna.pdf)


## Installation

### For users
In order to successfully use DNSite please follow those steps:
 * install **Java 8** - but not using OpenJDK as it will probably not work (if You are using Windows, after installing Java set **JAVA_HOME** and update **PATH** - more information can be found [here](https://javatutorial.net/set-java-home-windows-10))
 * download *.zip* file from [here](https://drive.google.com/drive/folders/1Og7IyqD11dZidbaHRg74Lt1glxVRp3ls) and unpack it
 * run `java -jar dnsite-0.0.1-SNAPSHOT.war` on downloaded *.war* file
 * if You are using the application for the first time, complete configuration 
 * visit https://localhost:8001

This instruction assumes that You have prepared PostgreSQL database that will interact with both DNSite and PowerDNS.

### For developers

#### Using IntelliJ IDEA
Clone this repository and open project using IntelliJ. Run *clean* and *install* with *Maven*.
Then just click *run* button (the class that contains *main* function is called *DNSiteApplication*).

If You are using the application for the first time, complete configuration and You will find login page under https://localhost:8001.

For more details, please consider reading documentation.

#### Using console
Clone this repository and go into *DNSite* directory. Run
`mvn clean install`
After it finishes, **from DNSite directory** run 
`java -jar target/dnsite-0.0.1-SNAPSHOT.war`

If You are using the application for the first time, complete configuration and You will find login page under https://localhost:8001.

For more details, please consider reading documentation.

## Authors
* [Jarosław Cierpich](https://github.com/Loniowsky)
* [Jakub Kowalski](https://github.com/JakubKowalski1997)
* [Arkadiusz Kasprzak](https://github.com/arokasprz100)
* [Konrad Pasik](https://github.com/Pasik97)
* [Krystian Molenda](https://github.com/pierwiastekzminusjeden)
