# BookFlow

BookFlow is an Android app for managing a personal book collection and keeping track of reading progress.

I built this project to practice Android development with Kotlin and Jetpack Compose, while working with local storage, REST APIs and app architecture.

## Features

- **My Library** — add, edit and delete books in your personal collection.
- **Reading Status** — organize books by their reading status.
- **Reading Progress** — keep track of the number of pages read.
- **Discover** — search for books by title or author using the Open Library API.
- **Book Details** — view book information and add books from Discover to your library.
- **Statistics** — get an overview of your reading activity.

## Tech Stack

- Kotlin
- Jetpack Compose
- Material 3
- Room
- Retrofit
- Kotlin Coroutines and Flow
- ViewModel
- Navigation Compose
- Coil
- Open Library API

## Architecture

The app follows a layered architecture with a separation of responsibilities:

- **UI Layer** — Jetpack Compose screens and UI state.
- **ViewModel** — handles UI logic and exposes screen state.
- **Repository** — provides a single entry point for accessing data.
- **Data Layer** — handles local storage with Room and remote requests through Retrofit.

## Data Sources

- [Open Library API](https://openlibrary.org/developers/api) — used to search for books and retrieve book information.
- Room database — stores the user's personal library locally.

## Getting Started

1. Clone the repository:

   ```bash
   git clone https://github.com/rockeeet/BookFlow.git
   ```

2. Open the project in Android Studio.
3. Let Gradle sync finish.
4. Run the app on an Android device or emulator.

---

This is a personal learning project that I'm developing as I continue to improve my Android development skills.
