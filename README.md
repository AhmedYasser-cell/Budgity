# Budgeting System

A comprehensive JavaFX-based desktop application designed to help users manage their personal finances effectively. Track expenses, monitor income, set financial goals, and generate insightful reports—all within a sleek, user-friendly interface.

## 🚀 Features

- **User Authentication:** Secure login and signup functionality with individualized user profiles.
- **Transaction Management:**
  - Add expenses and categorize them (e.g., Food, Transport, Utilities, etc.).
  - Add income with various sources (Salary, Investment, Gift, etc.).
- **Financial Goals:** Set and track progress towards your personal financial goals.
- **Reporting & Analytics:**
  - View a visual breakdown of your expenses via interactive Pie Charts.
  - Export your full transaction history to a CSV file for external analysis.
- **Customizable UI:** Toggle between Light and Dark themes to suit your preference.
- **Persistent Data:** All data is securely stored locally using an SQLite database.

## 🛠️ Technologies Used

- **Java:** Core programming language.
- **JavaFX:** GUI framework for building the application interface.
- **FXML & CSS:** Used for layout design and styling the user interface.
- **SQLite:** Lightweight relational database for persistent data storage.

## 📁 Project Structure

- `src/Main.java`: The main entry point of the application.
- `src/FinanceCore/`: Contains core domain models (`Transaction`, `Income`, `Expense`, `Category`).
- `src/Infrastructure/`: Handles database connectivity (`DatabaseManager`) and theme management (`ThemeManager`).
- `src/UserManagement/`: Contains user models and authentication logic.
- `Assets/`: Images, icons, and logos used within the application.
- `budgeting.db`: The local SQLite database file.

## ⚙️ Getting Started

### Prerequisites

- Java Development Kit (JDK) 11 or higher.
- JavaFX SDK installed and configured in your IDE.
- An IDE such as IntelliJ IDEA, Eclipse, or VS Code with Java extensions.

### Installation & Execution

1. Clone or download the repository to your local machine.
2. Open the project in your preferred IDE.
3. Ensure that JavaFX is correctly configured in your project settings (add JavaFX library to your modulepath/classpath).
4. Run the `src/Main.java` file to start the application.

### Default Database

The application will automatically initialize the required SQLite database (`budgeting.db`) upon its first run if it does not already exist.

## 🖼️ Screenshots

<p align="center">
  <img src="Assets/Readme%20Screenshots/Screenshot%202026-05-18%20035801.png" width="400" alt="Screenshot 1"/>
  <img src="Assets/Readme%20Screenshots/Screenshot%202026-05-18%20035910.png" width="400" alt="Screenshot 2"/>
</p>
<p align="center">
  <img src="Assets/Readme%20Screenshots/Screenshot%202026-05-18%20035936.png" width="400" alt="Screenshot 3"/>
  <img src="Assets/Readme%20Screenshots/Screenshot%202026-05-18%20035940.png" width="400" alt="Screenshot 4"/>
</p>

## 🤝 Contributing

Contributions, issues, and feature requests are welcome! Feel free to check the issues page if you want to contribute.

## 📄 License

This project is open-source and available under standard open-source licenses.
