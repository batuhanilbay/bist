
# Bist Track

The public API available on the [collectapi](https://collectapi.com/tr/) website was used.

My goal is to develop a BIST (Borsa Istanbul) tracking system based on the monthly allowance of 100 free requests provided to us.

Since it is open for 5 days a week and there are 20 business days in a month, we have the right to make 5 requests per day.

The [Redis](https://redis.io/) structure was used to cache the data.

I don't add a Dockerfile to the project. Instead, I utilized the Redis image by pulling it locally on my machine.

You need to install Redis.








## API Usage

#### Change your application.properties

```http
  api.key = YOUR_API_KEY

```

#### Retrieves available stocks within the Public API.

```http
  GET http://localhost:8080/api/stock-info

```




  