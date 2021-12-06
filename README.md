# DonutDash
App for ordering donuts

Concept:

An order flow app that saves each completed order as an object in a recyclerview on the homepage.

Orders can be created through the order flow and cancelled at any time, including after completion.

As much as possible, they should mimic real life business situations, with:

- same-day order fees
- large order fees
- business hours for delivery changing based on the day
- logic for selecting different toppings and their prices
- choices for different tips


App utilizes:

- Jetpack navigation component
- Shared Viewmodel / seperation of concerns
- Livedata and Databinding

Current progress:

Logic framework in place up to toppings selection. Currently working through bugs with selecting toppings via chips. 

Application has been worked on prior to any experience with Room/Databases and contains information
in the sharedViewModel that would otherwise be transitioned to a database.
