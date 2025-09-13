Core Requirements
User Management:
    Register, login, and manage user accounts.
    Different roles (admin vs. regular user).

Movie Catalog:
    Store movie details (title, description, genre, release year, rating).
    List/search/filter movies.

Streaming Service Simulation:
    A user can “play” or “stream” a movie (no real video, just simulate with text or mock endpoint).
    Track watch history per user.

Subscription / Plans (optional):
    Free vs. premium plans (premium unlocks more movies).

Admin Functions:
    Add, update, or remove movies.
    Manage users.

Technical Requirements (Java)

Backend: Java (Spring Boot recommended, but plain Java also works).

Data Layer: Can use an in-memory database (H2) or simple file storage; later upgrade to MySQL/Postgres.

API: Expose REST APIs for user login, movie catalog browsing, streaming, etc.

Build/Run: Use gradlew to build and run, with environment variables for DB connection.