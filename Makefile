SHELL=/bin/bash
HOME := $(shell pwd)
CUSTOMER_SERVICE := $(HOME)/customer-service
CLIENT_APP := $(HOME)/client

build/customerService:
	pushd ${CUSTOMER_SERVICE} > /dev/null && \
	mvn clean install
	
run/customerService:
	pushd ${CUSTOMER_SERVICE} > /dev/null && \
	mvn spring-boot:run
	
npmInstall/clientApp:
	pushd ${CLIENT_APP} > /dev/null && \
	npm install
	
npmStart/clientApp:
	pushd ${CLIENT_APP} > /dev/null && \
	npm start
