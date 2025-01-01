# Cronos - Administrator App for Android

Cronos is a native Android application designed for administrators to efficiently manage products and stores within a centralized e-commerce platform. It leverages Jetpack Compose for a modern, intuitive interface and integrates advanced backend technologies to ensure seamless operation and scalability.

## Features

### Product Management
- Create, read, update, and delete (CRUD) operations for products.
- Maintain centralized control of catalog data to ensure consistency.

### Store Management
- Manage store profiles, including updates and deactivations.
- Synchronize changes across all dependent systems in real-time.

### Real-Time Communication
- Integrated with Firebase Firestore for instant messaging capabilities.
- Monitor and manage interactions between stores and customers.

### Security
- API Key-based authentication for secure access.
- Encrypted storage of sensitive data for robust protection.

### Scalability and Maintainability
- Implements Clean Architecture and MVVM patterns.
- Ensures ease of scaling and efficient code management.

## Technologies Used

- **Frontend:** Jetpack Compose for declarative UI design.
- **Backend:** Ktor server for handling operations and secure communications.
- **Database:** MongoDB for scalable and reliable data storage.
- **Messaging:** Firebase Firestore for real-time communication.

## Getting Started

### Prerequisites
- Android Studio Bumblebee or later.
- Kotlin 1.6 or later.
- Firebase account for integration.

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/ldgomm/cronos-android.git
   ```
2. Open the project in Android Studio.
3. Sync the project with Gradle files to install dependencies.

### Configuration
1. Set up Firebase:
   - Download your `google-services.json` file from the Firebase Console.
   - Add it to the `app` directory of the project.
2. Configure API Keys in the `BuildConfig` file or a secure location.

### Running the App
1. Build and run the app on your emulator or device:
   ```bash
   Shift + F10
   ```

## Contributing

We welcome contributions! To contribute:
1. Fork the repository.
2. Create a new branch for your feature or bugfix.
   ```bash
   git checkout -b feature-name
   ```
3. Commit your changes.
   ```bash
   git commit -m "Description of your changes"
   ```
4. Push to your fork.
   ```bash
   git push origin feature-name
   ```
5. Submit a pull request.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Contact

For questions or support, please reach out to:
- **Email:** admin@cronosapp.com
- **GitHub Issues:** [Issue Tracker](https://github.com/ldgomm/cronos-android/issues)
