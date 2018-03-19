@echo off
echo Starting Financial Stock Rest Service application
echo.
echo.

cd PayconiqStockRestService/
start mvn spring-boot::run
echo started REST API in new window.
echo.
echo.

cd ../FinancialStocksApplication/
echo.
echo.
echo starting Financial Stocks Application in new window
call npm install
start http-server -a localhost -p 8000 -c-1 ./app
echo.
echo.

cd ../PayconiqStockRestService/
set /p isRunTest="DO you also want to execute Junit Test cases for this application (Y/N) ? "
echo %isRunTest%
IF "%isRunTest%" =="Y"  GOTO :run_test_cases
set /p exist="Do you want to exit?: "
GOTO :end
:run_test_cases
echo.
echo Executing test cases for Stock Rest Service application
call mvn test

echo ...........
echo.

:end

