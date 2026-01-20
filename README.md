```md
# 📚 BookAppPro

BookAppPro is a sleek and modern Android application for discovering and tracking books. Powered by the **Google Books API**, it provides a fast and intuitive way to search for books by title or author, explore popular categories, and revisit previously viewed books.

---

## ✨ Features

- 🔍 **Dynamic Book Search**  
  Search a massive library of books using the Google Books API.

- 🏷 **Category Filters**  
  One-tap chips to instantly explore popular genres like **Kotlin**, **Fiction**, and **Science**.

- 📖 **Detailed Book View**  
  Tap on any book to view extended details, including full descriptions and cover images.

- 🕒 **Local Browsing History**  
  Every viewed book is automatically saved locally and shown in a dedicated **History** section.

- 🎨 **Modern UI**  
  Clean, responsive, and user-friendly interface built with modern Android components.

---

## 🛠 Tech Stack

- **Language:** Kotlin  
- **Platform:** Android  
- **API:** Google Books API  
- **Architecture:** MVVM  
- **Local Storage:** Room / SharedPreferences  

---

## 🚀 Getting Started

### Prerequisites

- Android Studio (latest version recommended)
- Google Books API key
- Android device or emulator

---

## 🔑 Step 1: Get a Google Books API Key

1. Go to the **Google Cloud Console**.
2. Create a new project (or select an existing one).
3. Navigate to **APIs & Services > Library**.
4. Search for **Books API** and click **Enable**.
5. Go to **APIs & Services > Credentials**.
6. Click **Create Credentials → API key**.
7. Copy the generated API key (it will look like `AIzaSy...`).

---

## 💻 Step 2: Clone the Repository from GitHub

1. Go to your repository on GitHub.
2. Click the green **<> Code** button and copy the HTTPS URL:
```

[https://github.com/your-username/BookAppPro.git](https://github.com/your-username/BookAppPro.git)

```
3. Open **Android Studio**.
4. On the welcome screen, click **Get from VCS**.
5. Paste the URL, choose a directory, and click **Clone**.

Android Studio will download the complete project.

---

## 🔐 Step 3: Create the `local.properties` File

> ⚠️ **This is the most critical step.**  
> The `local.properties` file is intentionally **not included in GitHub** to protect sensitive data.

### 1. Find the Project
- In Android Studio, switch the left panel to **Project** view  
(not the *Android* view).

### 2. Create the File
- Right-click the root project folder (**BookAppPro**).
- Select **New → File**.
- Name it:
```

local.properties

````

### 3. Add the Content

```properties
# Path to the Android SDK on this computer
sdk.dir=C:\\Users\\<user-name>\\AppData\\Local\\Android\\Sdk

# Google Books API key
google.books.api.key=YOUR_API_KEY_HERE
````

### ❗ Important Notes

* Replace `<user-name>` with the correct username on your computer.
* To find the correct SDK path, open any existing Android project and copy it from its `local.properties`.
* Make sure the API key is valid.

---

## 🔄 Step 4: Sync the Project

* Android Studio may show **“Gradle files have changed”** → click **Sync Now**.
* If not, go to **File > Sync Project with Gradle Files**.

---

## ▶️ Step 5: Run the App

### 1. Select a Device

* Connect a physical Android device (USB Debugging enabled), **or**
* Create an emulator via **Tools > Device Manager**.

### 2. Build and Run

* Select the device from the toolbar.
* Click the green **Run ▶** button.

---

## ✅ Result

You should now see **BookAppPro** running, ready to:

* Search for books 📚
* Explore categories 🔖
* View detailed book information 🔍
* Revisit previously viewed books via History 🕒



