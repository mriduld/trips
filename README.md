# Trips App

### Requirements
Docker

Execute ```./docker-start.sh```. This does the following.
1. Starts the MySql container. Import the DB. This can take up to 15 minutes.
2. Start the back end application and the rest service at port 8081.
3. Starts a CLI bash.

To interact with the Rest API through the CLI in the bash run the following
```./trips.sh <args>``` where args can be 
1. ```count --date <date in YYYY-mm-DD format> --medallion <list of medallions separated by space> --ignore-cache(this is an optional parameter. Ignores cache if set)```
Example ```/trips.sh --date 2013-12-01 --medallion D7D598CD99978BD012A87A76A7C891B7 0C107B532C1207A74F0D8609B9E092FF```
2. ```invalidate-cache <cache-name>(For the currenct case there is just one cache called tripCounts```
Caches are considered to be resources and there could be more than 1 cache. The current implementation contains just a single tripCountCache. Hence an id is required to identify a cache.
Example ```/trips.sh invalidate-cache --cache-name tripCounts```
   
## Code Modules
1. Service: This is the backend service which talks to the persistent DB. 
2. Rest Service: The http endpoint where clients are expected to hit to interact with the application
3. Rest Client: A wrapper to abstract the http communication between clients and the service. This helps java clients deal with types instead of low level http. 
   This provides stronger compile time safety.
4. Cli: A command line interface which uses the rest client to communicate with the service.

### Tech Debts
1. Unit tests need to be done.
2. Cache Implementation is a simple Guava cache. A proper distributed cache like Redis should be used.

