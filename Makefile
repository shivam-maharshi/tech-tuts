.PHONY: install
install: ## Prepares the environment for running RAML Api Console by MuleSoft
	if [! -d "api-console" ] ; then git clone https://github.com/mulesoft/api-console ; else echo "api-console already present!" ; fi
	cd api-console
	apt-get update
	apt-get install -y nodejs
	apt-get install -y npm
	apt-get install -y gem
	apt-get install -y ruby
	rm -rf /var/lib/apt/lists/*
	
	gem install sass
	sudo npm install -g grunt-cli
	sudo npm install -g bower
	sudo npm install
	bower install
	sudo chmod 755 /home/shiv0150/.config/configstore/bower-github.json

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
