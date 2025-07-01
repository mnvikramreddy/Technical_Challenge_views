
# 📱 Android Kotlin MVVM App

A robust **Kotlin Android app** built with **MVVM architecture**, **Room Database**, and **Jetpack Navigation** using **XML layouts**, **DataBinding**, and **modern best practices**. This project features drag-and-drop & swipe-to-delete in RecyclerView, image loading via Glide, and offline persistence with Room.

---

## 🚀 Features

- 🖼️ **XML-based UI** with `DataBinding`
- 🔄 **LiveData & MediatorLiveData** for reactive UI updates
- 🔗 **MVVM Architecture** (ViewModel, Repository, UseCase)
- 🧩 **Jetpack Navigation Component** with Safe Args
- 📋 **RecyclerView** using:
  - `ListAdapter` + `DiffUtil`
  - Drag-and-drop reordering
  - Swipe-to-delete
- 🗃️ **Room Database** for local storage & caching
- 🧠 **Dagger 2** for Dependency Injection
- 🖼️ **Glide** for efficient image loading
- ✅ **Offline-first support**

---

## 🧱 Architecture Overview

```

📁 app/
│
├── di/                  # Dagger Modules & Component
├── model/               # Data models (UI + Room Entities)
├── repository/          # Repository interfaces and implementations
├── data/                # DAO, Room DB, and Local data source
├── ui/
│   ├── MainActivity     # Host activity
│   ├── fragments/       # Screens for list and detail views
│   ├── adapters/        # RecyclerView Adapters
│   └── viewmodels/      # ViewModels for screen logic
├── utils/               # Helper functions, constants, extensions
└── navigation/          # Navigation graph and route arguments

```

---

## 🧩 Core Libraries

| Layer         | Tech                              |
|---------------|------------------------------------|
| UI            | XML Views + DataBinding            |
| Architecture  | MVVM + Repository + UseCase        |
| Navigation    | Jetpack Navigation (Safe Args)     |
| DB            | Room + DAO                         |
| State         | LiveData, MediatorLiveData         |
| DI            | Dagger 2                           |
| Images        | Glide                              |
| Lists         | RecyclerView + ListAdapter         |

---

## 🔁 Data Flow (Clean MVVM)

```

\[Fragment] ⇄ \[ViewModel] ⇄ \[Repository] ⇄ \[Room / API]
↑         ↓
LiveData   UiEvent, State

````

- ViewModel exposes LiveData for UI state
- Room used to cache and persist data locally
- Repository abstracts data source (local or remote)
- MediatorLiveData combines multiple data streams

---

---

## 💡 Highlights

### ✅ Room Database

* Entity, DAO, and `RoomDatabase` class
* CRUD operations for items
* Fetched data cached in Room
* UI observes LiveData from Room DAO

### ✅ RecyclerView with ListAdapter

* Efficient list handling using `DiffUtil`
* Swipe to delete with `ItemTouchHelper`
* Drag to reorder items and persist new order

### ✅ Navigation with Safe Args

* Type-safe fragment argument passing
* Back stack management handled automatically

### ✅ Dagger 2 DI

* DI setup for ViewModel, Repository, DAO, and DB
* Singleton `RoomDatabase` via Dagger module

---

## 🧪 Testing

```bash
./gradlew test
```

* [ ] ViewModel unit tests
* [ ] DAO tests with in-memory Room
* [ ] UI tests with Espresso

---
