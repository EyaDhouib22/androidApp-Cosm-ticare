# 💄 Cosmeticare - Mobile Cosmetics Shopping App

A mobile application dedicated to selling cosmetic products, allowing users to shop for various beauty products easily and quickly online.

## 📱 About the App

Cosmeticare is an Android mobile application that provides a seamless shopping experience for cosmetic and beauty products. The app features user authentication, product browsing, favorites management, and secure purchasing functionality.

## ✨ Key Features

### 🔐 User Authentication
- **Email & Password Login** - Secure user authentication system
- **Registration System** - New user account creation with validation
- **Field Validation** - All fields are mandatory with error handling
- **Email Verification** - Checks for existing email addresses during registration
- **Password Visibility Toggle** - Show/hide password functionality
- **Local Database Storage** - User data stored in SQLite database

### 🛍️ Product Management
- **Product Catalog** - Display products in RecyclerView format
- **Product Details** - Comprehensive product information display
- **Dynamic Loading** - Real-time product data management

### ❤️ Favorites System
- **Add to Favorites** - Save preferred products for later
- **Local Storage** - Favorites stored using SharedPreferences
- **Persistent Data** - Favorites maintained across app sessions
- **Easy Access** - Quick navigation to favorites from user account

### 🛒 Purchase Functionality
- **Instant Purchase** - "Buy Now" button for immediate checkout
- **Purchase Confirmation** - Secure transaction confirmation process
- **SMS Notifications** - Purchase confirmation via text message
- **Permission Handling** - Requests SMS permissions when needed

### 👤 User Account Management
- **Profile Information** - Display current user details
- **Account Settings** - Manage user preferences
- **Logout Functionality** - Secure session termination
- **Favorites Access** - Direct link to saved products

### 📞 Customer Support
- **About Section** - Application information and functionality details
- **Direct Contact** - Phone call functionality to customer service
- **Call Permissions** - Handles phone call permissions
- **Instant Support** - Quick access to help and assistance

### 🔔 Smart Notifications
- **Promotional Alerts** - Special offers and discounts notifications
- **Background Notifications** - Triggered when leaving product pages
- **User Engagement** - Keeps users informed about deals

### 🧭 Intuitive Navigation
- **Toolbar Navigation** - Top screen navigation bar
- **Drawer Menu** - Side navigation panel with all app sections
- **Smooth Transitions** - Seamless page-to-page navigation
- **User-Friendly Interface** - Easy-to-use design patterns

## 🛠️ Technical Stack

| Component | Technology |
|-----------|------------|
| **Platform** | Android (Native) |
| **Language** | Java/Kotlin |
| **Database** | SQLite (Local) |
| **UI Components** | RecyclerView, DrawerLayout, Toolbar |
| **Data Storage** | SharedPreferences |
| **Architecture** | MVC/MVP Pattern |
| **Notifications** | Android Notification System |
| **Permissions** | SMS, Phone Call |

## 📂 Project Structure

```
app/
├── src/main/java/
│   ├── activities/
│   │   ├── MainActivity.java
│   │   ├── LoginActivity.java
│   │   ├── RegisterActivity.java
│   │   └── ProductActivity.java
│   ├── adapters/
│   │   └── ProductAdapter.java
│   ├── models/
│   │   ├── User.java
│   │   └── Product.java
│   ├── database/
│   │   └── DatabaseHelper.java
│   └── utils/
│       └── SharedPrefsManager.java
├── res/
│   ├── layout/
│   ├── drawable/
│   ├── values/
│   └── menu/
└── AndroidManifest.xml
```

## 🚀 Getting Started

### Prerequisites
- Android Studio (Latest Version)
- Android SDK (API Level 21+)
- Java 8+ or Kotlin
- Android Device or Emulator

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/EyaDhouib22/Cosmeticare.git
   cd Cosmeticare
   ```

2. **Open in Android Studio**
   - Launch Android Studio
   - Select "Open an existing project"
   - Navigate to the cloned directory

3. **Sync the project**
   ```bash
   # Android Studio will automatically prompt to sync
   # Or manually: Tools > Sync Project with Gradle Files
   ```

4. **Run the application**
   - Connect an Android device or start an emulator
   - Click the "Run" button or press Shift + F10

## 📱 App Permissions

The app requests the following permissions:

```xml
<uses-permission android:name="android.permission.SEND_SMS" />
<uses-permission android:name="android.permission.CALL_PHONE" />
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

## 🔧 Configuration

### Database Setup
The app uses SQLite for local data storage. Database initialization is handled automatically on first launch.

### SharedPreferences
Favorites and user preferences are stored locally using Android's SharedPreferences system.

## 📱 User Flow

1. **Launch App** → Authentication screen
2. **Login/Register** → Validate credentials
3. **Home Screen** → Browse product catalog
4. **Product Interaction** → Add to favorites or purchase
5. **Purchase Flow** → Confirmation and SMS notification
6. **Account Management** → View profile and favorites
7. **Navigation** → Use drawer menu or toolbar

## 📦 Build & Release

### Debug Build
```bash
./gradlew assembleDebug
```

### Release Build
```bash
./gradlew assembleRelease
```

## 📄 License

This project is developed as part of academic coursework.


