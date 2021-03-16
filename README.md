# Human Resources System
## Project concept
The job applicant fills in information about himself. The HR employee records 
the application and makes a decision on hiring, and the applicant receives
a notification about the result by email. The HR employee creates and manages vacancies.
The admin creates and manages companies.
## Application architecture: Model-View-Controller
## Project Features
- Database
    - Dynamic thread-safe connection pool
    - Protection from SQL injection
    - Proxy connection
    - Number of tables : 8
- Message sending using Gmail mail server
- Pagination
- Filters
    - Command access filter
    - Filter for transitions between pages
    - Encoding filter
    - Cross-site scripting protection filter
- Localization: EN, RU, BE
- Available 4 user roles
- Available 4 custom tags
- Passwords hashing
- Accompanying user actions with messages
- Validating the information on a client and server
- Used design patterns: Command, Proxy, Dao, State, Singleton, MVC
## Guest Features
- Locale changing
- Signing in
- Registering
- Automatic activation after clicking on a link from the mail
## Common Authorized Users Features
- Avatar uploading
- Company info viewing
- Vacancies viewing
- Log out
## Admin Features
- A new company adding
- A new specialty adding
- User blocking
- User unblocking
- Viewing all users information
## HR Features
- A new vacancy adding
- Confirming application
  - Then automatic deletion of application and vacancy
- Rejecting application
  - Then automatic deletion of application, but vacancy remains open for submission 
- Viewing all applications to his company
- New specialties reloading
- Applicant profile viewing
## Applicant Features
- Info adding
- Profile updating
- Applying for a vacancy
### &copy; Sergey Galian, 2021