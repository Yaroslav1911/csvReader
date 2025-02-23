### Step 1

Hi, to run this app first thing you need to do is install Docker tools.
If you already have it, skip this part:

Register to hub.docker.com
Install Docker Desktop via chocolatey PM
(it is not needed to install this package for linux)
`choco install docker-desktop` (Windows)
Install WSL (it is not needed to install this package for linux)
`choco install wsl` (Windows)
Install docker-cli
`choco install docker-cli`
To install Docker on Ubuntu, see: (https://docs.docker.com/engine/install/ubuntu/) 

> ### Post installation. Remove needs of root privileges (linux only)
> To create the docker group and add your user:

> Create the docker group. 'sudo groupadd docker'
> Add your user to the docker group. 'sudo usermod -aG docker $USER'
> Log out and log back in so that your group membership is re-evaluated.

### Step 2

When you copy the repo, launch a cmd from '/docker' dir and run next commands:  

# create docker image
'docker image build -t local/postgres:15.5-alpine3.19 . #-f .\Dockerfile .'
# create container, 'postgres_server' can be changed on what ever you want
'docker container run --name postgres_server -p 5432:5432 -d local/postgres:15.5-alpine3.19'

You can check created container by running the command 'docker container ls'

### Step 3

## Now you need to choose which tool to use. It can be shell, or Git Bash, or Postman.

> Open your cmd in project's root and run this:
> 'curl -F "file=@src/test/resources/sampleTrades.csv" http://localhost:8080/api/v1/enrich' 

>  'sampleTrades.csv' is the file that exists. It can be changed to another one with relevant fields.

> Done.

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


About API:
    - application reads +-5000 lines per minute;
    - it will read large sized file, but it will take some time;
    - I'm pretty shure I can make it read JSON & XML files(and other additional tasks too),
     but I've already lost a lot of time;
    - and also, I think with correct multithread implementation it will be even faster. 
