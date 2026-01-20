# 📚 BookAppPro

BookAppPro is a sleek and modern Android application for discovering and tracking books. Powered by the **Google Books API**, it offers a fast and intuitive way to search for books by title or author, explore popular categories, and keep track of previously viewed books.

---

## ✨ Features

* 🔍 **Dynamic Book Search**
  Search a massive library of books using the Google Books API.

* 🏷 **Category Filters**
  One-tap chips to instantly explore popular genres like **Kotlin**, **Fiction**, and **Science**.

* 📖 **Detailed Book View**
  Tap on any book to view extended details, including a full description and cover image.

* 🕒 **Local Browsing History**
  Every viewed book is automatically saved locally and displayed in a dedicated **History** section for easy access later.

* 🎨 **Modern UI**
  Clean, responsive, and user-friendly interface built with modern Android components.

---

## 🛠 Tech Stack

* **Language:** Kotlin
* **Platform:** Android
* **API:** Google Books API
* **Architecture:** MVVM (if applicable)
* **Local Storage:** Room / SharedPreferences (based on implementation)

---

## 🚀 Getting Started

### Prerequisites

* Android Studio (latest version recommended)
* A Google Books API key
* Android device or emulator

---

## 🔑 Step 1: Get a Google Books API Key

1. Go to the **Google Cloud Console**.
2. Create a new project (or select an existing one).
3. Navigate to **APIs & Services > Library**.
4. Search for **Books API** and click **Enable**.
5. Go to **APIs & Services > Credentials**.
6. Click **Create Credentials** → **API key**.
7. Copy the generated API key (it will look like `AIzaSy...`).

---

## 🧩 Step 2: Set Up the Project in Android Studio

### 1. Open the Project

* Open **Android Studio**.
* Select **File > Open**.
* Navigate to the project root folder:

  ```
  C:/Users/name/Desktop/BookAppPro
  ```

### 2. Add Your API Key

* In the root directory, find or create a file named `local.properties`.
* Add your API key in the following format:

```properties
GOOGLE_BOOKS_API_KEY="YOUR_API_KEY_GOES_HERE"
```

* Replace `YOUR_API_KEY_GOES_HERE` with your actual API key.
* ⚠️ This file is intentionally private and should **not** be committed to version control.

### 3. Sync Gradle

* Android Studio may prompt you to **Sync Now** — click it.
* If not, go to:
  **File > Sync Project with Gradle Files**.

---

## ▶️ Step 3: Run the App

### 1. Select a Device

* Connect a physical Android device via USB (ensure **USB Debugging** is enabled), **or**
* Create an emulator via **Tools > Device Manager**.

### 2. Build and Run

* Select your device from the toolbar dropdown.
* Click the green **Run ▶** button.
* Android Studio will build the project, install the APK, and launch the app.

---

## ✅ Result

You should now see **BookAppPro** running on your device or emulator, ready for you to:

* Search for books 📚
* Explore categories 🔖
* View detailed book info 🔍
* Revisit previously viewed books via History 🕒

---

## 📌 Notes

* Ensure you have an active internet connection for API calls.
* API usage limits depend on your Google Cloud configuration.

---

Happy reading! 🚀📖
