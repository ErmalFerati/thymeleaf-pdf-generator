# Thymeleaf PDF testing tool

### Summary
A simple test project to quickly generate PDF documents using the Thymeleaf engine

### How to use

* Make a POST request to /api/generate-template/{templateName} or
    * The `templateName` path variable must match the document's name
* Run the generate-template.sh script which does it for you and gets the
    data from the data.json file
    * The first argument must match the document's name 
