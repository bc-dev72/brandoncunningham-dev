# Brandon Cunningham site source code

## Analytics 

This is the spring boot backend service, that interfaces  with an MySql database and only has 2 endpoints: 

### POST /api/analytics || Body: {"type":"string"}

> Used for storing an action that happens on the website, such as a PAGE_LOAD, RESUME_DOWNLOAD or VIEW_SOURCE. 

### GET /api/analytics || Params: type (string, valid option = "lite")

> Used for returning the summary of data from the stored analytics, will return a 30 day history, current live users, total unique page loads and total resume downloads. If the type is entered as lite, we will not return the 30 day history. 


## Website

This is the front end code of the website, written in plain html, css, javascript, no frameworks. Calls out to the API to get the analytics summary and when the user takes an action such as: page loading, clicking on the view source code link or the download resume button. 

## Docker

Contains the docker compose file for deploying the MySql database (with an attached volume) and deploying the spring boot service. Also have an environment file with dummy values for dev testing locally. 

The static content is served through a nginx server on a linux machine hosted on digital ocean. The docker containers are also running on that same machine and nginx handles the routing of the requests. 
