# products-java-api
[![Build Status](https://travis-ci.com/leandrocezar/products-java-api.svg?branch=feature%2Fproducts)](https://travis-ci.com/leandrocezar/products-java-api)
![GitHub forks](https://img.shields.io/github/forks/leandrocezar/products-java-api?style=social) 
![GitHub release (latest by date)](https://img.shields.io/github/v/release/leandrocezar/products-java-api) 
![GitHub language count](https://img.shields.io/github/languages/count/leandrocezar/products-java-api)
![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/leandrocezar/products-java-api) 
![GitHub repo size](https://img.shields.io/github/repo-size/leandrocezar/products-java-api) 
![GitHub last commit](https://img.shields.io/github/last-commit/leandrocezar/products-java-api)


## About the API

An API for product management. This project is built with Java, Spring Boot, and Spring Framework. 
The API main URL `/products`.

## Features

This API provides HTTP endpoint's and tools for the following:

* Create a product: `POST/products`
* Update a product: `PUT/products/1`
* Delete a product (by id): `DELETE/products/1`
* Find a unique product by id: `GET/products/1`
* Get all products: `GET/products`
* Get product by name or description, min price and max price products: `GET/products/search?q=productname&min_price=0&maxprice=10`

To test the application import `src/main/resources/insomnia_collection.json`  file on Insomnia App

### Details

`POST/products`

This end-point is called to add a new Product.

**Body:**

```json
{
  "name": "Product name",
  "description": "Product description",
  "price": 99.5
}
```

**Where:**

`name` - product name (required)

`description` - product description (required)

`price` – product price(parsable as a BigDecimal (required)


**Return:** Returns all info about the added product including the generated id:

```json
{
  "id": "2018795b-3537-4ddc-a22f-69e90116c6c4",
  "name": "Product name",
  "description": "Product description",
  "price": 99.5
}
```

* 201 - Created: Everything worked as expected.
* 400 - Bad Request: the request was unacceptable. Reason: missing a required parameter.
* 500- Server Error: something went wrong on API.

`PUT/products/:id`

This end-point is called to update a existing Product.

**Path param:**

`id` - product id to update (required)


**Body:**

```json
{
  "name": "Product name",
  "description": "Product description",
  "price": 99.5
}
```

**Where:**

`name` - product name (required)

`description` - product description (required)

`price` – product price(parsable as a BigDecimal (required)


**Return:** Returns all info about the updated product including the generated id:

```json
{
  "id": "2018795b-3537-4ddc-a22f-69e90116c6c4",
  "name": "Product name",
  "description": "Product description",
  "price": 99.5
}
```

* 200 - OK: Everything worked as expected.
* 400 - Bad Request: the request was unacceptable. Reason: missing a required parameter.
* 404 - Not Found: The product with path param id not exists.
* 500- Server Error: something went wrong on API.

`DELETE/products/:id`

This end-point is called to delete a existing Product.

**Path param:**

`id` - product id to delete (required)


**Return:** Returns the http status code to operation:

* 200 - OK: Everything worked as expected.
* 404 - Not Found: The product with path param id not exists.
* 500- Server Error: something went wrong on API.

`GET/products/:id`

This end-point is called to find a unique product Product.

**Path param:**

`id` - product id to find (required)


**Return:** Returns all about the product:

```json
{
  "id": "2018795b-3537-4ddc-a22f-69e90116c6c4",
  "name": "Product name",
  "description": "Product description",
  "price": 99.5
}
```

* 200 - OK: Everything worked as expected.
* 400 - Bad Request: the request was unacceptable. Reason: missing a required parameter.
* 404 - Not Found: The product with path param id not exists.
* 500- Server Error: something went wrong on API.


`GET/products`

This end-point is called to find all Products.

**Return:** Returns the list of products:

```json
[
	{
	  "id": "2018795b-3537-4ddc-a22f-69e90116c6c4",
	  "name": "Product name",
	  "description": "Product description",
	  "price": 99.5
	},
	{
	  "id": "2018795b-3537-4ddc-a22f-69e901163364",
	  "name": "Product name 2",
	  "description": "Product description 2",
	  "price": 10.5
	},
]
```

* 200 - OK: Everything worked as expected.
* 500- Server Error: something went wrong on API.

`GET/products/search?q=:expression&min_price=:min_price&max_price=:max_price`

This end-point is called to find products by some cryteria.

**Query params:**

`q` - product name or description

`min_price` - Minimum product price

`max_price` - Maximum product price


**Return:** Returns the list of products:

```json
[
	{
	  "id": "2018795b-3537-4ddc-a22f-69e90116c6c4",
	  "name": "Product name",
	  "description": "Product description",
	  "price": 99.5
	},
	{
	  "id": "2018795b-3537-4ddc-a22f-69e901163364",
	  "name": "Product name 2",
	  "description": "Product description 2",
	  "price": 10.5
	},
]
```

* 200 - OK: Everything worked as expected.
* 500- Server Error: something went wrong on API.


### Technologies used

This project was developed with:

* **Java 8**
* **Spring Boot 2.5.2**
* **Maven**
* **JUnit 5**
* **H2**
* **Swagger 3.0.0**
* **Model Mapper 2.3.9**

### Compile and Package

The API also was developed to run with an `jar`. In order to generate this `jar`, you should run:

```bash
mvn package
```

This command will clean, compile and generate a `jar` at target directory, e.g. `products-java-api-1.0.0-SNAPSHOT.jar`

### Execution

This project uses **H2 database**. This database run in memory and create database, tables and populate it on turn on the application. This database run in the memory of the machine.

### Test

* For unit test phase, you can run:

```bash
mvn test
```

### Run

In order to run the API, run the jar as following:

```bash
java -jar products-java-api-1.0.0-SNAPSHOT.jar --spring.profiles.active=dev
```
    
or

```bash
mvn spring-boot:run -Dspring.profiles.active=dev
```

By default, the API will be available at [http://localhost:999](http://localhost:9999)

### Documentation

* Swagger (development environment): [http://localhost:9999/swagger-ui/index.html](http://localhost:9999/swagger-ui/index.html)