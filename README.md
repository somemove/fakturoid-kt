# Fakturoid.kt

[![Release](https://jitpack.io/v/somemove/fakturoid-kt.svg?style=flat-square)](https://jitpack.io/#somemove/fakturoid-kt)

A Kotlin (Java) library for API communication with web-based invoicing service [Fakturoid.cz](https://www.fakturoid.cz/). API v2 is supported only. Please see [API documentation](http://docs.fakturoid.apiary.io/) for more details.

**STATUS: INCOMPLETE**

## Install

```groovy
// Define the dependency version
buildscript {
	ext {
		fakturoidVersion = 'THE_VERSION'
	}
}
// Add the Jitpack repository
repositories {
	maven { url 'https://jitpack.io' }
}
// Add the dependency
dependencies {
	compile "com.github.somemove:fakturoid-kt:$fakturoidVersion"
}
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
