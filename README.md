# Public Suffix List API

This is a Java API for the [Public Suffix List](https://publicsuffix.org/).


# Installation

This package will be available in Maven central.


# Usage

Create a `PublicSuffixList` with a `PublicSuffixListFactory`. Read more
at the API documentation.

```java
PublicSuffixListFactory factory = new PublicSuffixListFactory();
PublicSuffixList suffixList = factory.build();

System.out.println(suffixList.getRegistrableDomain("www.example.net"));

```


# License and author

Markus Malkusch <markus@malkusch.de> is the author of this project. This project is free and under the GPL.

## Donations

If you like this project and feel generous donate a few Bitcoins here:
[1335STSwu9hST4vcMRppEPgENMHD2r1REK](bitcoin:1335STSwu9hST4vcMRppEPgENMHD2r1REK)

[![Build Status](https://travis-ci.org/whois-server-list/public-suffix-list.svg?branch=master)](https://travis-ci.org/whois-server-list/public-suffix-list)