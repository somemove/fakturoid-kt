# Fakturoid.kt

A Kotlin (Java) library for API communication with web-based invoicing service [Fakturoid.cz](https://www.fakturoid.cz/). API v2 is supported only. Please see [API documentation](http://docs.fakturoid.apiary.io/) for more details.

**STATUS: INCOMPLETE**

## Add to your project

```
<dependencies>
<dependency>
<groupId>cz.smmv.fakturoid</groupId>
<artifactId>fakturoid-kt</artifactId>
<version>0.0.1</version>
</dependency>
</dependencies>

<repositories>
<repository>
<id>somemove</id>
<url>http://repo.somemove.cz</url>
</repository>
</repositories>

```

## Example

```
String userAgent = "Example call (example.call@with.email)";
String slug = "example";
String email = "example@email.com";
String apiKey = "1a2b3c...";

Fakturoid f = new Fakturoid(slug, email, apiKey, userAgent);

f.fireEvent(123456, "mark_as_sent");
```
