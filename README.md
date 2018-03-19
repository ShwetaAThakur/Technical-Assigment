# Technical-Assigment
Repository created for technical assignment for 'Stock Rest Service'

This project consist of following components 

1. PayconiqStockRestService
This is REST API built in Java8 using Spring boot. Service provides various operations for 'stock'. Refer swagger documentation for more information about this API.
REST api will be available at http://localhost:8081/api/stocks
 


2. FinancialStocksApplication
This is angular js application which provides functionality to view list of all stocks available in the System and also details of each stock item. 
Once server is up application will be available at https://localhost:8000/index.html 

3. loadApplication.bat
Batch file which starts all required applications. 
Once applications are started, it asks whether test cases needs to be executed for 'PayconiqStockRestService'. Answer with 'Y' for Yes or 'N' for 'No'

How to Start

1. Clone This repository 
2. PayconiqStockRestService API runs on '8081' port by defult. FinancialStocksApplication runs on port 8000 bu default. Make sure these ports are available before moving to next steps. 
3. Run loadApplication.bat

