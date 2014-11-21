# Public Suffix List API

This is a thread-safe Java API for Mozilla's [Public Suffix List](https://publicsuffix.org/):

> A "public suffix" is one under which Internet users can directly register names.
> Some examples of public suffixes are .com, .co.uk and pvt.k12.ma.us.


# Installation

This package is available in Maven central:

```xml
<dependency>
	<groupId>de.malkusch.whois-server-list</groupId>
	<artifactId>public-suffix-list</artifactId>
	<version>2.0.1</version>
</dependency>
```


# Usage

Create a
[`PublicSuffixList`](http://whois-server-list.github.io/public-suffix-list/apidocs/de/malkusch/whoisServerList/publicSuffixList/PublicSuffixList.html)
with a
[`PublicSuffixListFactory`](http://whois-server-list.github.io/public-suffix-list/apidocs/de/malkusch/whoisServerList/publicSuffixList/PublicSuffixListFactory.html):

* [`PublicSuffixList.getRegistrableDomain()`](http://whois-server-list.github.io/public-suffix-list/apidocs/de/malkusch/whoisServerList/publicSuffixList/PublicSuffixList.html#getRegistrableDomain%28java.lang.String%29):
Gets the registrable domain or null. E.g. `"www.example.net"` and `"example.net"` will return `"example.net"`.
Null, an empty string or domains with a leading dot will return null.

* [`PublicSuffixList.isRegistrable()`](http://whois-server-list.github.io/public-suffix-list/apidocs/de/malkusch/whoisServerList/publicSuffixList/PublicSuffixList.html#isRegistrable%28java.lang.String%29):
Returns whether a domain is registrable. E.g. `"example.net"` is registrable, `"www.example.net"` and `"net"` are not.

* [`PublicSuffixList.isPublicSuffix()`](http://whois-server-list.github.io/public-suffix-list/apidocs/de/malkusch/whoisServerList/publicSuffixList/PublicSuffixList.html#isPublicSuffix%28java.lang.String%29):
Returns whether a domain is a public suffix or not. E.g. `"com"` is a public suffix, `"example.com"` isn't.

* [`PublicSuffixList.getPublicSuffix()`](http://whois-server-list.github.io/public-suffix-list/apidocs/de/malkusch/whoisServerList/publicSuffixList/PublicSuffixList.html#getPublicSuffix%28java.lang.String%29):
Returns the public suffix from a domain or null. If the domain is already a public suffix, it will be returned unchanged.
E.g. `"www.example.net"` will return `"net"`.

All methods are case insensitive.

## IDN

You can use the API's methods with UTF-8 domain names or Punycode encoded ASCII domain names.
The API will return the results in the same format as the input was. I.e. if you use an UTF-8
string the result will be an UTF-8 String as well. Same for Punycode.

## Example

```java
PublicSuffixListFactory factory = new PublicSuffixListFactory();
PublicSuffixList suffixList = factory.build();

assertTrue(suffixList.isPublicSuffix("net"));
assertFalse(suffixList.isPublicSuffix("example.net"));

assertEquals("net", suffixList.getPublicSuffix("www.example.net"));
assertEquals("net", suffixList.getPublicSuffix("net"));

assertTrue(suffixList.isRegistrable("example.net"));
assertFalse(suffixList.isRegistrable("www.example.net"));
assertFalse(suffixList.isRegistrable("net"));

assertNull(suffixList.getRegistrableDomain("net"));
assertEquals("example.net", suffixList.getRegistrableDomain("example.net"));
assertEquals("example.net", suffixList.getRegistrableDomain("www.example.net"));
assertEquals("example.co.uk", suffixList.getRegistrableDomain("example.co.uk"));
assertEquals("example.co.uk", suffixList.getRegistrableDomain("www.example.co.uk"));

assertEquals("食狮.com.cn", suffixList.getRegistrableDomain("食狮.com.cn"));
assertEquals("xn--85x722f.com.cn", suffixList.getRegistrableDomain("xn--85x722f.com.cn"));
```


# Public Suffix List update

This library comes with a bundled list which is most likely out dated. You are
encouraged to follow Mozilla's [Atom change feed ](http://hg.mozilla.org/mozilla-central/atom-log/default/netwerk/dns/effective_tld_names.dat)
and use the latest [`effective_tld_names.dat`](https://publicsuffix.org/list/effective_tld_names.dat).
You can specify a custom path to your latest list by setting the property [`PROPERTY_LIST_FILE`](http://whois-server-list.github.io/public-suffix-list/apidocs/de/malkusch/whoisServerList/publicSuffixList/PublicSuffixListFactory.html#PROPERTY_LIST_FILE):

```java
PublicSuffixListFactory factory = new PublicSuffixListFactory();

Properties properties = factory.getDefaults();
properties.setProperty(
        PublicSuffixListFactory.PROPERTY_LIST_FILE, "/effective_tld_names.dat");

PublicSuffixList suffixList = factory.build(properties);
```

## Download Public Suffix List with Maven

You can integrate the download of the latest list in your maven build process:

```xml
<build>
    <plugins>
        <plugin>
            <groupId>com.googlecode.maven-download-plugin</groupId>
            <artifactId>download-maven-plugin</artifactId>
            <version>1.2.0</version>
            <executions>
                <execution>
                    <id>package-psl</id>
                    <phase>generate-resources</phase>
                    <goals>
                        <goal>wget</goal>
                    </goals>
                    <configuration>
                        <url>https://publicsuffix.org/list/effective_tld_names.dat</url>
                        <outputDirectory>${project.build.outputDirectory}</outputDirectory>
                        <outputFileName>effective_tld_names.dat</outputFileName>
                    </configuration>
                </execution>
            <executions>
        </plugin>
    </plugins>
</build>
```

# License and author

Markus Malkusch <markus@malkusch.de> is the author of this project. This project is free and under the WTFPL.

## Donations

If you like this project and feel generous donate a few Bitcoins here:
[1335STSwu9hST4vcMRppEPgENMHD2r1REK](bitcoin:1335STSwu9hST4vcMRppEPgENMHD2r1REK)

[![Build Status](https://travis-ci.org/whois-server-list/public-suffix-list.svg?branch=master)](https://travis-ci.org/whois-server-list/public-suffix-list)
