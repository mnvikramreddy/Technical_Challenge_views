
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

## 📸 Screenshots

> *Add screenshots or screen recording of the app*

---

## 📄 License

MIT License. See the [LICENSE](LICENSE) file for more info.

---

## 👨‍💻 Author

**Your Name**
GitHub: [vikramreddy](https://github.com/mnvikramreddy)

```

---

Would you like:
- A ready-to-use `RoomModule.kt` with DAO and DB setup for Dagger?
- An example of `ItemTouchHelper` for drag & swipe with `ListAdapter`?

Let me know, and I’ll generate those snippets too!
```

## Environment Setup Tips
### Java 11 / 17
[Azul Zulu ARM64 JDK 11](https://www.azul.com/downloads/?version=java-11-lts&os=macos&architecture=arm-64-bit&package=jdk) download for M1 macs.

[Azul Zulu JDK 17](https://www.azul.com/downloads/?version=java-17-lts&os=macos&architecture=arm-64-bit&package=jdk) download for M1 macs.

### jenv
We highly recommend using [jenv](https://www.jenv.be/) to manage your different JDKs.

Follow the instructions below to set up jenv after you’ve installed java above.

1. Install jenv

Linux / OS X
```bash
$ git clone https://github.com/jenv/jenv.git ~/.jenv
```

Mac OS X via [Homebrew](http://brew.sh/)
```bash
$ brew install jenv
```

2. Add to path

```bash
$ echo 'export PATH="$HOME/.jenv/bin:$PATH"' >> ~/.zsh_profile
$ echo 'eval "$(jenv init -)"' >> ~/.zsh_profile
$ source ~/.zsh_profile
```

3. Run plugin to set your `JAVA_HOME` environment variable automatically

```bash
$ jenv enable-plugin export
$ source ~/.zsh_profile
```

4. Add jdk to jenv

```bash
# M1 Mac
jenv add /Library/Java/JavaVirtualMachines/zulu-17.jdk/Contents/Home
# Intel Mac
jenv add /Library/Java/JavaVirtualMachines/adoptopenjdk-11.jdk/Contents/Home
```
Note for M1 Macs: if you don’t have `zulu-17.jdk`, download it from the link below and add it to `/Library/Java/JavaVirtualMachines`
[Azul Downloads](https://www.azul.com/downloads/?version=java-17-lts&os=macos&architecture=arm-64-bit&package=jdk#zulu)

5. Set your global version

```bash
# M1 Mac
$ jenv global 17.0
# Intel Mac
$ jenv global 11.0
```

jenv reads the `.java-version` file for each repo automatically
