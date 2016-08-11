#!/bin/bash

GREEN='\033[0;32m'
NC='\033[0m'

.PHONY: install
install: ## Prepares the environment for running RAML Api Console by MuleSoft
        if [! -d "api-console" ]; then git clone https://github.com/mulesoft/api-console; else echo "api-console already present!"; fi
        cd api-console
#       apt-get update
        apt-get install -y nodejs
        apt-get install -y npm
        apt-get install -y gem
        apt-get install -y ruby
        rm -rf /var/lib/apt/lists/*
        gem install sass
        npm install -g grunt-cli
        npm install -g bower
        npm install
        if [ -f /usr/bin/nodejs ]; then if [ ! -f /usr/bin/node ]; then ln -s /usr/bin/nodejs /usr/bin/node; fi; fi
        if [ -f ${PWD}/api-console/.bowerrc ]; then rm -rf ${PWD}/api-console/.bowerrc; fi
        yes '' | bower init --allow-root
        bower install --allow-root
        chmod 755 ~/.config/configstore/bower-github.json
        echo "${GREEN}Installation Successful !! :)${NC}"

.PHONY: install_test
install_test: ## Prepares the environment for running tests
        gem install sass
        sudo npm install -g grunt-cli
        sudo npm install -g protractor
        sudo npm install
        node_modules/grunt-protractor-runner/node_modules/protractor/bin/webdriver-manager update

.PHONY: test
test: ## Runs regression tests
        grunt regression

.PHONY: run
run: ## Runs the grunt server to serve the Api Console
        grunt
