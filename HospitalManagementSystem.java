package com.gqt;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

// Main Hospital Management System Class
public class HospitalManagementSystem {
    private static Scanner scanner = new Scanner(System.in);
    private static Database database;
    private static User currentUser = null;

    public static void main(String[] args) {
        System.out.println("=== HOSPITAL MANAGEMENT SYSTEM ===");
        System.out.println("Welcome to HMS - Your Health Management Solution");

        String dbFile = "hospital_data.ser";
        
        // Load database from file if it exists
        database = Database.loadFromFile(dbFile);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // Save database before program exits
            database.saveToFile(dbFile);
        }));

        while (true) {
            showMainMenu();
            int choice = getIntInput();
            switch (choice) {
                case 1:
                    loginAsPatient();
                    break;
                case 2:
                    loginAsDoctor();
                    break;
                case 3:
                    registerPatient();
                    break;
                case 4:
                    System.out.println("Thank you for using Hospital Management System!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    private static void showMainMenu() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("        MAIN MENU");
        System.out.println("=".repeat(40));
        System.out.println("1. Login as Patient");
        System.out.println("2. Login as Doctor");
        System.out.println("3. Register as Patient");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void loginAsPatient() {
        System.out.println("\n=== PATIENT LOGIN ===");
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        currentUser = database.authenticateUser(username, password);
        if (currentUser != null && currentUser instanceof Patient) {
            System.out.println("Login successful! Welcome, " + currentUser.getName());
            patientDashboard();
        } else {
            System.out.println("Invalid credentials or not a patient account!");
        }
    }

    private static void loginAsDoctor() {
        System.out.println("\n=== DOCTOR LOGIN ===");
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        currentUser = database.authenticateUser(username, password);
        if (currentUser != null && currentUser instanceof Doctor) {
            System.out.println("Login successful! Welcome, Dr. " + currentUser.getName());
            doctorDashboard();
        } else {
            System.out.println("Invalid credentials or not a doctor account!");
        }
    }

    private static void registerPatient() {
        System.out.println("\n=== PATIENT REGISTRATION ===");
        System.out.print("Enter Full Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        System.out.print("Enter Age: ");
        int age = getIntInput();
        scanner.nextLine(); // consume newline
        System.out.print("Enter Phone Number: ");
        String phone = scanner.nextLine();
        System.out.print("Enter Address: ");
        String address = scanner.nextLine();
        if (database.registerPatient(name, username, password, age, phone, address)) {
            System.out.println("Registration successful! You can now login.");
        } else {
            System.out.println("Username already exists! Please choose a different username.");
        }
    }

    private static void doctorDashboard() {
        Doctor doctor = (Doctor) currentUser;
        while (true) {
            System.out.println("\n" + "=".repeat(40));
            System.out.println("     DOCTOR DASHBOARD - " + doctor.getName());
            System.out.println("=".repeat(40));
            System.out.println("1. View My Appointments");
            System.out.println("2. View Patient Details");
            System.out.println("3. Update Appointment Status");
            System.out.println("4. View My Profile");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");
            int choice = getIntInput();
            switch (choice) {
                case 1:
                    viewDoctorAppointments(doctor);
                    break;
                case 2:
                    viewPatientDetails();
                    break;
                case 3:
                    updateAppointmentStatus(doctor);
                    break;
                case 4:
                    viewDoctorProfile(doctor);
                    break;
                case 5:
                    currentUser = null;
                    System.out.println("Logged out successfully!");
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    private static void patientDashboard() {
        Patient patient = (Patient) currentUser;
        while (true) {
            System.out.println("\n" + "=".repeat(40));
            System.out.println("     PATIENT DASHBOARD - " + patient.getName());
            System.out.println("=".repeat(40));
            System.out.println("1. Book Appointment");
            System.out.println("2. View My Appointments");
            System.out.println("3. Cancel Appointment");
            System.out.println("4. View Available Doctors");
            System.out.println("5. View My Profile");
            System.out.println("6. Logout");
            System.out.print("Enter your choice: ");
            int choice = getIntInput();
            switch (choice) {
                case 1:
                    bookAppointment(patient);
                    break;
                case 2:
                    viewPatientAppointments(patient);
                    break;
                case 3:
                    cancelAppointment(patient);
                    break;
                case 4:
                    viewAvailableDoctors();
                    break;
                case 5:
                    viewPatientProfile(patient);
                    break;
                case 6:
                    currentUser = null;
                    System.out.println("Logged out successfully!");
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    private static void bookAppointment(Patient patient) {
        System.out.println("\n=== BOOK APPOINTMENT ===");
        List<Doctor> doctors = database.getAllDoctors();
        if (doctors.isEmpty()) {
            System.out.println("No doctors available!");
            return;
        }
        System.out.println("Available Doctors:");
        for (int i = 0; i < doctors.size(); i++) {
            Doctor doc = doctors.get(i);
            System.out.printf("%d. Dr. %s - %s\n", (i + 1), doc.getName(), doc.getSpecialization());
        }
        System.out.print("Select Doctor (enter number): ");
        int doctorChoice = getIntInput() - 1;
        if (doctorChoice < 0 || doctorChoice >= doctors.size()) {
            System.out.println("Invalid doctor selection!");
            return;
        }
        Doctor selectedDoctor = doctors.get(doctorChoice);
        scanner.nextLine(); // consume newline
        System.out.print("Enter reason for appointment: ");
        String reason = scanner.nextLine();
        if (database.bookAppointment(patient, selectedDoctor, reason)) {
            System.out.println("Appointment booked successfully!");
        } else {
            System.out.println("Failed to book appointment!");
        }
    }

    private static void viewDoctorAppointments(Doctor doctor) {
        System.out.println("\n=== MY APPOINTMENTS ===");
        List<Appointment> appointments = database.getDoctorAppointments(doctor.getUserId());
        if (appointments.isEmpty()) {
            System.out.println("No appointments scheduled.");
            return;
        }
        for (Appointment app : appointments) {
            System.out.println("-".repeat(40));
            System.out.println("Appointment ID: " + app.getAppointmentId());
            System.out.println("Patient: " + database.getPatientById(app.getPatientId()).getName());
            System.out.println("Date: " + app.getAppointmentDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
            System.out.println("Reason: " + app.getReason());
            System.out.println("Status: " + app.getStatus());
        }
    }

    private static void viewPatientAppointments(Patient patient) {
        System.out.println("\n=== MY APPOINTMENTS ===");
        List<Appointment> appointments = database.getPatientAppointments(patient.getUserId());
        if (appointments.isEmpty()) {
            System.out.println("No appointments scheduled.");
            return;
        }
        for (Appointment app : appointments) {
            System.out.println("-".repeat(40));
            System.out.println("Appointment ID: " + app.getAppointmentId());
            System.out.println("Doctor: Dr. " + database.getDoctorById(app.getDoctorId()).getName());
            System.out.println("Specialization: " + database.getDoctorById(app.getDoctorId()).getSpecialization());
            System.out.println("Date: " + app.getAppointmentDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
            System.out.println("Reason: " + app.getReason());
            System.out.println("Status: " + app.getStatus());
        }
    }

    private static void cancelAppointment(Patient patient) {
        System.out.println("\n=== CANCEL APPOINTMENT ===");
        List<Appointment> appointments = database.getPatientAppointments(patient.getUserId());
        if (appointments.isEmpty()) {
            System.out.println("No appointments to cancel.");
            return;
        }
        System.out.println("Your Appointments:");
        for (int i = 0; i < appointments.size(); i++) {
            Appointment app = appointments.get(i);
            System.out.printf("%d. Dr. %s - %s - %s\n",
                    (i + 1),
                    database.getDoctorById(app.getDoctorId()).getName(),
                    app.getAppointmentDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                    app.getStatus());
        }
        System.out.print("Select appointment to cancel (enter number): ");
        int choice = getIntInput() - 1;
        if (choice < 0 || choice >= appointments.size()) {
            System.out.println("Invalid selection!");
            return;
        }
        Appointment selectedApp = appointments.get(choice);
        if (database.cancelAppointment(selectedApp.getAppointmentId())) {
            System.out.println("Appointment cancelled successfully!");
        } else {
            System.out.println("Failed to cancel appointment!");
        }
    }

    private static void updateAppointmentStatus(Doctor doctor) {
        System.out.println("\n=== UPDATE APPOINTMENT STATUS ===");
        List<Appointment> appointments = database.getDoctorAppointments(doctor.getUserId());
        if (appointments.isEmpty()) {
            System.out.println("No appointments to update.");
            return;
        }
        System.out.println("Your Appointments:");
        for (int i = 0; i < appointments.size(); i++) {
            Appointment app = appointments.get(i);
            System.out.printf("%d. Patient: %s - %s - Status: %s\n",
                    (i + 1),
                    database.getPatientById(app.getPatientId()).getName(),
                    app.getAppointmentDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                    app.getStatus());
        }
        System.out.print("Select appointment to update (enter number): ");
        int choice = getIntInput() - 1;
        if (choice < 0 || choice >= appointments.size()) {
            System.out.println("Invalid selection!");
            return;
        }
        System.out.println("1. Completed");
        System.out.println("2. Cancelled");
        System.out.print("Select new status: ");
        int statusChoice = getIntInput();
        String newStatus = statusChoice == 1 ? "Completed" : "Cancelled";
        Appointment selectedApp = appointments.get(choice);
        if (database.updateAppointmentStatus(selectedApp.getAppointmentId(), newStatus)) {
            System.out.println("Appointment status updated successfully!");
        } else {
            System.out.println("Failed to update appointment status!");
        }
    }

    private static void viewAvailableDoctors() {
        System.out.println("\n=== AVAILABLE DOCTORS ===");
        List<Doctor> doctors = database.getAllDoctors();
        if (doctors.isEmpty()) {
            System.out.println("No doctors available.");
            return;
        }
        for (Doctor doc : doctors) {
            System.out.println("-".repeat(40));
            System.out.println("Dr. " + doc.getName());
            System.out.println("Specialization: " + doc.getSpecialization());
            System.out.println("Experience: " + doc.getExperience() + " years");
        }
    }

    private static void viewPatientDetails() {
        System.out.println("\n=== PATIENT DETAILS ===");
        List<Patient> patients = database.getAllPatients();
        if (patients.isEmpty()) {
            System.out.println("No patients registered.");
            return;
        }
        for (Patient patient : patients) {
            System.out.println("-".repeat(40));
            System.out.println("Name: " + patient.getName());
            System.out.println("Age: " + patient.getAge());
            System.out.println("Phone: " + patient.getPhone());
            System.out.println("Address: " + patient.getAddress());
        }
    }

    private static void viewDoctorProfile(Doctor doctor) {
        System.out.println("\n=== MY PROFILE ===");
        System.out.println("Name: Dr. " + doctor.getName());
        System.out.println("Username: " + doctor.getUsername());
        System.out.println("Specialization: " + doctor.getSpecialization());
        System.out.println("Experience: " + doctor.getExperience() + " years");
    }

    private static void viewPatientProfile(Patient patient) {
        System.out.println("\n=== MY PROFILE ===");
        System.out.println("Name: " + patient.getName());
        System.out.println("Username: " + patient.getUsername());
        System.out.println("Age: " + patient.getAge());
        System.out.println("Phone: " + patient.getPhone());
        System.out.println("Address: " + patient.getAddress());
    }

    private static int getIntInput() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}

// Base User Class
abstract class User implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String userId;
    protected String name;
    protected String username;
    protected String password;

    public User(String userId, String name, String username, String password) {
        this.userId = userId;
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public String getUserId() { return userId; }
    public String getName() { return name; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
}

// Doctor Class
class Doctor extends User implements Serializable {
    private static final long serialVersionUID = 1L;
    private String specialization;
    private int experience;

    public Doctor(String userId, String name, String username, String password,
                  String specialization, int experience) {
        super(userId, name, username, password);
        this.specialization = specialization;
        this.experience = experience;
    }

    public String getSpecialization() { return specialization; }
    public int getExperience() { return experience; }
}

// Patient Class
class Patient extends User implements Serializable {
    private static final long serialVersionUID = 1L;
    private int age;
    private String phone;
    private String address;

    public Patient(String userId, String name, String username, String password,
                   int age, String phone, String address) {
        super(userId, name, username, password);
        this.age = age;
        this.phone = phone;
        this.address = address;
    }

    public int getAge() { return age; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
}

// Appointment Class
class Appointment implements Serializable {
    private static final long serialVersionUID = 1L;
    private String appointmentId;
    private String patientId;
    private String doctorId;
    private LocalDateTime appointmentDate;
    private String reason;
    private String status;

    public Appointment(String appointmentId, String patientId, String doctorId,
                      LocalDateTime appointmentDate, String reason, String status) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentDate = appointmentDate;
        this.reason = reason;
        this.status = status;
    }

    public String getAppointmentId() { return appointmentId; }
    public String getPatientId() { return patientId; }
    public String getDoctorId() { return doctorId; }
    public LocalDateTime getAppointmentDate() { return appointmentDate; }
    public String getReason() { return reason; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

// Database Class - In-memory database using Java Collections
class Database implements Serializable {
    private static final long serialVersionUID = 1L;
    private Map<String, User> users;
    private List<Doctor> doctors;
    private List<Patient> patients;
    private List<Appointment> appointments;
    private int userIdCounter;
    private int appointmentIdCounter;

    public Database() {
        users = new HashMap<>();
        doctors = new ArrayList<>();
        patients = new ArrayList<>();
        appointments = new ArrayList<>();
        userIdCounter = 1;
        appointmentIdCounter = 1;
    }

    public void initializeSampleData() {
        // Add sample doctors
        addDoctor("Dr. Smith", "dr.smith", "password123", "Cardiology", 10);
        addDoctor("Dr. Johnson", "dr.johnson", "password123", "Pediatrics", 8);
        addDoctor("Dr. Williams", "dr.williams", "password123", "Orthopedics", 12);
        addDoctor("Dr. Brown", "dr.brown", "password123", "Neurology", 15);
    }

    private void addDoctor(String name, String username, String password,
                          String specialization, int experience) {
        String userId = "DOC" + String.format("%03d", userIdCounter++);
        Doctor doctor = new Doctor(userId, name, username, password, specialization, experience);
        doctors.add(doctor);
        users.put(username, doctor);
    }

    public boolean registerPatient(String name, String username, String password,
                                  int age, String phone, String address) {
        if (users.containsKey(username)) {
            return false;
        }
        String userId = "PAT" + String.format("%03d", userIdCounter++);
        Patient patient = new Patient(userId, name, username, password, age, phone, address);
        patients.add(patient);
        users.put(username, patient);
        return true;
    }

    public User authenticateUser(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public boolean bookAppointment(Patient patient, Doctor doctor, String reason) {
        String appointmentId = "APP" + String.format("%03d", appointmentIdCounter++);
        LocalDateTime appointmentDate = LocalDateTime.now().plusDays(1); // Next day
        Appointment appointment = new Appointment(appointmentId, patient.getUserId(),
                                                doctor.getUserId(), appointmentDate, reason, "Scheduled");
        appointments.add(appointment);
        return true;
    }

    public List<Appointment> getPatientAppointments(String patientId) {
        List<Appointment> patientAppointments = new ArrayList<>();
        for (Appointment app : appointments) {
            if (app.getPatientId().equals(patientId)) {
                patientAppointments.add(app);
            }
        }
        return patientAppointments;
    }

    public List<Appointment> getDoctorAppointments(String doctorId) {
        List<Appointment> doctorAppointments = new ArrayList<>();
        for (Appointment app : appointments) {
            if (app.getDoctorId().equals(doctorId)) {
                doctorAppointments.add(app);
            }
        }
        return doctorAppointments;
    }

    public boolean cancelAppointment(String appointmentId) {
        for (Appointment app : appointments) {
            if (app.getAppointmentId().equals(appointmentId)) {
                app.setStatus("Cancelled");
                return true;
            }
        }
        return false;
    }

    public boolean updateAppointmentStatus(String appointmentId, String newStatus) {
        for (Appointment app : appointments) {
            if (app.getAppointmentId().equals(appointmentId)) {
                app.setStatus(newStatus);
                return true;
            }
        }
        return false;
    }

    public List<Doctor> getAllDoctors() {
        return new ArrayList<>(doctors);
    }

    public List<Patient> getAllPatients() {
        return new ArrayList<>(patients);
    }

    public Patient getPatientById(String patientId) {
        for (Patient patient : patients) {
            if (patient.getUserId().equals(patientId)) {
                return patient;
            }
        }
        return null;
    }

    public Doctor getDoctorById(String doctorId) {
        for (Doctor doctor : doctors) {
            if (doctor.getUserId().equals(doctorId)) {
                return doctor;
            }
        }
        return null;
    }

    public void saveToFile(String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(this);
            System.out.println("Database saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving database: " + e.getMessage());
        }
    }

    public static Database loadFromFile(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            Database loadedDb = (Database) in.readObject();
            System.out.println("Database loaded successfully.");
            return loadedDb;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading database: " + e.getMessage());
            return new Database(); // fresh instance if no file exists
        }
    }
}