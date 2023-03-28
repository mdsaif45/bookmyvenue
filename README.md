# BookMyVenue

- web application for booking venue for events like exhibition, engagement etc 
- developed by using java spring boot framework

# BookMyVenue - Venue Booking Web Application

BookMyVenue is a venue booking web application developed using Java/Spring Boot framework/Hibernate with payment integration. It has many features like searching for venues with many filteration options such as location and venue, viewing venue details, booking, and receiving payment receipt through email. The application contains two modules, client and admin.

## Features

-   **Search Venue**: The application allows the user to search for venues based on location, venue type, price range, capacity, and amenities.
-   **View Venue Details**: The user can view detailed information about the venue, including photos, location, capacity, amenities, and availability.
-   **Book Venue**: The user can book a venue by selecting the date, time, and duration. They can make the payment online.
-   **Payment Integration**: The application integrates with a payment gateway to allow the user to make secure online payments.
-   **Email Receipt**: The user receives an email receipt with all the details of the booking, including the payment summary.

## Feature Soon

- **Book Venue**: They can also add additional services, such as catering and decoration, and make the payment online.

## Modules

The application consists of two modules:

### 1. Client Module

The client module is accessible to the users, and it provides the following features:

-   Search for venues
-   View venue details
-   Book a venue
-   View booking history

### 2. Admin Module

The admin module is accessible to the administrators, and it provides the following features:

-   Add new venues
-   Edit venue details
-   Approve or reject booking requests
-   View booking history

## Technologies Used

-   Java
-   Spring Boot Framework
-   Hibernate ORM
-   MySQL Database
-   Thymeleaf Template Engine
-   Bootstrap CSS Framework

## How to Use

To use the application, you need to have Java and MySQL installed on your system. Follow the steps below to run the application:

1.  Clone the repository to your local machine.
2.  Import the project into your preferred IDE (Eclipse, IntelliJ IDEA, or NetBeans).
3.  Create a MySQL database and update the database configuration in the `application.properties` file.
4.  Run the project as a Spring Boot application.
5.  Access the application at `http://localhost:8080`.

## Screenshots

![Homepage](https://github.com/mdsaif45/screenshots/homepage.png)

![Venue Details](https://github.com/mdsaif45/screenshots/venue-details.png)

![Booking Form](https://github.com/mdsaif45/screenshots/booking-form.png)

![Payment Gateway](https://github.com/mdsaif45/screenshots/payment-gateway.png)

## Contributors

-   MD SAIF ([@mdsaif45](https://github.com/mdsaif45))

## License

This project is licensed under the [MIT License](https://opensource.org/licenses/MIT) - see the LICENSE file for details.
