# Net Price Calculator Service
This is a Spring Boot service that allows consumers to calculate the net price from a gross price, based on the VAT (Value Added Tax) rate of a given country.

## Installation
1. Make sure you have Java 17 or later installed.
2. Build the project using Gradle: 
```bash
    ./gradlew clean build 
```
3. Starting the service
You can start the service by running the following command:

``` bash
./gradlew bootRun 
```
This will start the service on port 8080.

### Calculating net price
To calculate the net price, you can send a GET request to the `/netprice` endpoint with the following query parameters:

- `grossPrice`: the gross price of the product.
- `countryIso`: the ISO code of the country where the product is sold.

#### Example request:

```http request
GET /netPrice?grossPrice=100&countryIso=DE HTTP/1.1
Host: localhost:8080
```
#### Example response:
```http request
HTTP/1.1 200 OK
Content-Type: application/json

81.00

```
### Configuration
The tax rates used by the service are stored in a json in the resource folder. You can modify `vat.json` to add or change tax rates.
