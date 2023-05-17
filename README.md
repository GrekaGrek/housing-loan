## Task

 Create an application which can be used for calculation of the cost from a housing loan.

The application should have a simple user interface (web or console) where the user can
specify the desired amount and the payback time in years. 

For simplicity we assume a fixed interest of 3.5% per year during the complete payback time. 

The interest should be connected to the loan type in such a manner that different loan types can have different
interests. 

When selecting amount and payback time, the application should generate a
monthly payback plan based on the series loan principle, i.e. you pay back an equal amount
each month and add the generated interest. 

The interest is calculated every month.

The application should be made in such a manner that it can easily be extended to calculate
a payment plan for other types of loans, for instance car loan, spending loan etc. with
different interests, nominal value and payback time. 

Also bear in mind that it should be easy to extend the application to cover other payback schemes as well. 

We do not expect this to be implemented.


!NB

For very quick check can use curl command from terminal passing different values into Json:

curl -d ' { "termsInYears" : "10", "loanAmount" : "10000", "loanType" : "HOUSING" } ' 
-H "Content-Type: application/json" -X POST http://localhost:8089/api/payments