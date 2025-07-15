package com.gqt;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class HostelManagementSystem implements Serializable {
    // File paths for serialization
    private static final String STUDENT_FILE = "students.dat";
    private static final String ROOM_FILE = "rooms.dat";
    private static final String STAFF_FILE = "staff.dat";
    private static final String PAYMENT_FILE = "payments.dat";
    private static final String COMPLAINT_FILE = "complaints.dat";

    // In-memory database using Collections
    private static Map<Integer, Student> students = new HashMap<>();
    private static Map<Integer, Room> rooms = new HashMap<>();
    private static Map<Integer, Staff> staffMembers = new HashMap<>();
    private static List<Payment> payments = new ArrayList<>();
    private static List<Complaint> complaints = new ArrayList<>();

    // ID generators
    private static int studentIdCounter = 1000;
    private static int roomIdCounter = 100;
    private static int staffIdCounter = 500;
    private static int paymentIdCounter = 1;
    private static int complaintIdCounter = 1;

    private static Scanner scanner = new Scanner(System.in);

    // Student class
    static class Student implements Serializable {
        private int studentId;
        private String name;
        private String email;
        private String phone;
        private String address;
        private LocalDate joinDate;
        private int roomId;
        private boolean isActive;

        public Student(String name, String email, String phone, String address) {
            this.studentId = studentIdCounter++;
            this.name = name;
            this.email = email;
            this.phone = phone;
            this.address = address;
            this.joinDate = LocalDate.now();
            this.roomId = -1; // Not assigned initially
            this.isActive = true;
        }

        // Getters and Setters
        public int getStudentId() { return studentId; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }
        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }
        public LocalDate getJoinDate() { return joinDate; }
        public int getRoomId() { return roomId; }
        public void setRoomId(int roomId) { this.roomId = roomId; }
        public boolean isActive() { return isActive; }
        public void setActive(boolean active) { isActive = active; }

        @Override
        public String toString() {
            return String.format("Student ID: %d | Name: %s | Email: %s | Phone: %s | Room: %s | Status: %s",
                    studentId, name, email, phone,
                    (roomId == -1 ? "Not Assigned" : String.valueOf(roomId)),
                    (isActive ? "Active" : "Inactive"));
        }
    }

    // Room class
    static class Room implements Serializable {
        private int roomId;
        private String roomType;
        private int capacity;
        private int currentOccupancy;
        private double monthlyRent;
        private boolean isAvailable;
        private List<Integer> studentIds = new ArrayList<>();

        public Room(String roomType, int capacity, double monthlyRent) {
            this.roomId = roomIdCounter++;
            this.roomType = roomType;
            this.capacity = capacity;
            this.currentOccupancy = 0;
            this.monthlyRent = monthlyRent;
            this.isAvailable = true;
            this.studentIds = new ArrayList<>();
        }

        // Getters and Setters
        public int getRoomId() { return roomId; }
        public String getRoomType() { return roomType; }
        public void setRoomType(String roomType) { this.roomType = roomType; }
        public int getCapacity() { return capacity; }
        public void setCapacity(int capacity) { this.capacity = capacity; }
        public int getCurrentOccupancy() { return currentOccupancy; }
        public void setCurrentOccupancy(int currentOccupancy) { this.currentOccupancy = currentOccupancy; }
        public double getMonthlyRent() { return monthlyRent; }
        public void setMonthlyRent(double monthlyRent) { this.monthlyRent = monthlyRent; }
        public boolean isAvailable() { return isAvailable; }
        public void setAvailable(boolean available) { isAvailable = available; }
        public List<Integer> getStudentIds() { return studentIds; }

        public void addStudent(int studentId) {
            if (currentOccupancy < capacity) {
                studentIds.add(studentId);
                currentOccupancy++;
                if (currentOccupancy == capacity) {
                    isAvailable = false;
                }
            }
        }

        public void removeStudent(int studentId) {
            studentIds.remove(Integer.valueOf(studentId));
            currentOccupancy--;
            if (currentOccupancy < capacity) {
                isAvailable = true;
            }
        }

        @Override
        public String toString() {
            return String.format("Room ID: %d | Type: %s | Capacity: %d | Occupied: %d | Rent: $%.2f | Available: %s",
                    roomId, roomType, capacity, currentOccupancy, monthlyRent, (isAvailable ? "Yes" : "No"));
        }
    }

    // Staff class
    static class Staff implements Serializable {
        private int staffId;
        private String name;
        private String position;
        private String phone;
        private double salary;
        private LocalDate hireDate;

        public Staff(String name, String position, String phone, double salary) {
            this.staffId = staffIdCounter++;
            this.name = name;
            this.position = position;
            this.phone = phone;
            this.salary = salary;
            this.hireDate = LocalDate.now();
        }

        // Getters and Setters
        public int getStaffId() { return staffId; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getPosition() { return position; }
        public void setPosition(String position) { this.position = position; }
        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }
        public double getSalary() { return salary; }
        public void setSalary(double salary) { this.salary = salary; }
        public LocalDate getHireDate() { return hireDate; }

        @Override
        public String toString() {
            return String.format("Staff ID: %d | Name: %s | Position: %s | Phone: %s | Salary: $%.2f | Hire Date: %s",
                    staffId, name, position, phone, salary, hireDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        }
    }

    // Payment class
    static class Payment implements Serializable {
        private int paymentId;
        private int studentId;
        private double amount;
        private LocalDate paymentDate;
        private String paymentType;
        private String status;

        public Payment(int studentId, double amount, String paymentType) {
            this.paymentId = paymentIdCounter++;
            this.studentId = studentId;
            this.amount = amount;
            this.paymentType = paymentType;
            this.paymentDate = LocalDate.now();
            this.status = "Completed";
        }

        // Getters
        public int getPaymentId() { return paymentId; }
        public int getStudentId() { return studentId; }
        public double getAmount() { return amount; }
        public LocalDate getPaymentDate() { return paymentDate; }
        public String getPaymentType() { return paymentType; }
        public String getStatus() { return status; }

        @Override
        public String toString() {
            return String.format("Payment ID: %d | Student ID: %d | Amount: $%.2f | Type: %s | Date: %s | Status: %s",
                    paymentId, studentId, amount, paymentType,
                    paymentDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), status);
        }
    }

    // Complaint class
    static class Complaint implements Serializable {
        private int complaintId;
        private int studentId;
        private String description;
        private LocalDate complaintDate;
        private String status;
        private String response;

        public Complaint(int studentId, String description) {
            this.complaintId = complaintIdCounter++;
            this.studentId = studentId;
            this.description = description;
            this.complaintDate = LocalDate.now();
            this.status = "Open";
            this.response = "";
        }

        // Getters and Setters
        public int getComplaintId() { return complaintId; }
        public int getStudentId() { return studentId; }
        public String getDescription() { return description; }
        public LocalDate getComplaintDate() { return complaintDate; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        public String getResponse() { return response; }
        public void setResponse(String response) { this.response = response; }

        @Override
        public String toString() {
            return String.format("Complaint ID: %d | Student ID: %d | Description: %s | Date: %s | Status: %s | Response: %s",
                    complaintId, studentId, description,
                    complaintDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), status, response);
        }
    }

    // Load data from files on startup
    private static void loadData() {
        try {
            if (new File(STUDENT_FILE).exists()) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(STUDENT_FILE))) {
                    students = (Map<Integer, Student>) ois.readObject();
                    studentIdCounter = students.isEmpty() ? 1000 : Collections.max(students.keySet()) + 1;
                }
            }

            if (new File(ROOM_FILE).exists()) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ROOM_FILE))) {
                    rooms = (Map<Integer, Room>) ois.readObject();
                    roomIdCounter = rooms.isEmpty() ? 100 : Collections.max(rooms.keySet()) + 1;
                }
            }

            if (new File(STAFF_FILE).exists()) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(STAFF_FILE))) {
                    staffMembers = (Map<Integer, Staff>) ois.readObject();
                    staffIdCounter = staffMembers.isEmpty() ? 500 : Collections.max(staffMembers.keySet()) + 1;
                }
            }

            if (new File(PAYMENT_FILE).exists()) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PAYMENT_FILE))) {
                    payments = (List<Payment>) ois.readObject();
                    paymentIdCounter = payments.isEmpty() ? 1 : payments.size() + 1;
                }
            }

            if (new File(COMPLAINT_FILE).exists()) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(COMPLAINT_FILE))) {
                    complaints = (List<Complaint>) ois.readObject();
                    complaintIdCounter = complaints.isEmpty() ? 1 : complaints.size() + 1;
                }
            }

            System.out.println("Data loaded successfully.");

        } catch (Exception e) {
            System.err.println("Error loading data: " + e.getMessage());
        }
    }

    // Save data to files
    private static void saveData() {
        try {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(STUDENT_FILE))) {
                oos.writeObject(students);
            }

            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ROOM_FILE))) {
                oos.writeObject(rooms);
            }

            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(STAFF_FILE))) {
                oos.writeObject(staffMembers);
            }

            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PAYMENT_FILE))) {
                oos.writeObject(payments);
            }

            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(COMPLAINT_FILE))) {
                oos.writeObject(complaints);
            }

            System.out.println("All data saved successfully.");

        } catch (IOException e) {
            System.err.println("Error saving data: " + e.getMessage());
        }
    }

    // Helper input methods
    private static int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input! Please enter a number: ");
            scanner.next(); // discard invalid token
        }
        int value = scanner.nextInt();
        scanner.nextLine(); // consume newline
        return value;
    }

    private static double getDoubleInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextDouble()) {
            System.out.print("Invalid input! Please enter a number: ");
            scanner.next(); // discard invalid token
        }
        double value = scanner.nextDouble();
        scanner.nextLine(); // consume newline
        return value;
    }

    // Main method
    public static void main(String[] args) {
        loadData();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            saveData();
            System.out.println("Program exited. Data saved.");
        }));

        System.out.println("=== Welcome to Hostel Management System ===");
        while (true) {
            displayMainMenu();
            int choice = getIntInput("Enter your choice: ");
            switch (choice) {
                case 1:
                    studentManagement();
                    break;
                case 2:
                    roomManagement();
                    break;
                case 3:
                    staffManagement();
                    break;
                case 4:
                    paymentManagement();
                    break;
                case 5:
                    complaintManagement();
                    break;
                case 6:
                    generateReports();
                    break;
                case 7:
                    saveData();
                    System.out.println("Thank you for using Hostel Management System!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    // Display main menu
    private static void displayMainMenu() {
        System.out.println("\n=== MAIN MENU ===");
        System.out.println("1. Student Management");
        System.out.println("2. Room Management");
        System.out.println("3. Staff Management");
        System.out.println("4. Payment Management");
        System.out.println("5. Complaint Management");
        System.out.println("6. Reports");
        System.out.println("7. Exit");
    }

    // STUDENT MANAGEMENT
    private static void studentManagement() {
        while (true) {
            System.out.println("\n=== STUDENT MANAGEMENT ===");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Search Student");
            System.out.println("4. Update Student");
            System.out.println("5. Delete Student");
            System.out.println("6. Assign Room to Student");
            System.out.println("7. Back to Main Menu");
            int choice = getIntInput("Enter your choice: ");
            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    viewAllStudents();
                    break;
                case 3:
                    searchStudent();
                    break;
                case 4:
                    updateStudent();
                    break;
                case 5:
                    deleteStudent();
                    break;
                case 6:
                    assignRoomToStudent();
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    private static void addStudent() {
        System.out.println("\n=== ADD STUDENT ===");
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter phone: ");
        String phone = scanner.nextLine();
        System.out.print("Enter address: ");
        String address = scanner.nextLine();
        Student student = new Student(name, email, phone, address);
        students.put(student.getStudentId(), student);
        System.out.println("Student added successfully! Student ID: " + student.getStudentId());
    }

    private static void viewAllStudents() {
        System.out.println("\n=== ALL STUDENTS ===");
        if (students.isEmpty()) {
            System.out.println("No students found!");
            return;
        }
        for (Student student : students.values()) {
            System.out.println(student);
        }
    }

    private static void searchStudent() {
        System.out.println("\n=== SEARCH STUDENT ===");
        int studentId = getIntInput("Enter student ID: ");
        Student student = students.get(studentId);
        if (student != null) {
            System.out.println("Student found:");
            System.out.println(student);
        } else {
            System.out.println("Student not found!");
        }
    }

    private static void updateStudent() {
        System.out.println("\n=== UPDATE STUDENT ===");
        int studentId = getIntInput("Enter student ID: ");
        Student student = students.get(studentId);
        if (student == null) {
            System.out.println("Student not found!");
            return;
        }
        System.out.println("Current details: " + student);
        System.out.print("Enter new name (or press Enter to keep current): ");
        String name = scanner.nextLine();
        if (!name.trim().isEmpty()) {
            student.setName(name);
        }
        System.out.print("Enter new email (or press Enter to keep current): ");
        String email = scanner.nextLine();
        if (!email.trim().isEmpty()) {
            student.setEmail(email);
        }
        System.out.print("Enter new phone (or press Enter to keep current): ");
        String phone = scanner.nextLine();
        if (!phone.trim().isEmpty()) {
            student.setPhone(phone);
        }
        System.out.println("Student updated successfully!");
    }

    private static void deleteStudent() {
        System.out.println("\n=== DELETE STUDENT ===");
        int studentId = getIntInput("Enter student ID: ");
        Student student = students.get(studentId);
        if (student == null) {
            System.out.println("Student not found!");
            return;
        }
        if (student.getRoomId() != -1) {
            Room room = rooms.get(student.getRoomId());
            if (room != null) {
                room.removeStudent(studentId);
            }
        }
        students.remove(studentId);
        System.out.println("Student deleted successfully!");
    }

    private static void assignRoomToStudent() {
        System.out.println("\n=== ASSIGN ROOM TO STUDENT ===");
        int studentId = getIntInput("Enter student ID: ");
        Student student = students.get(studentId);
        if (student == null) {
            System.out.println("Student not found!");
            return;
        }
        if (student.getRoomId() != -1) {
            System.out.println("Student is already assigned to room " + student.getRoomId());
            return;
        }
        System.out.println("Available rooms:");
        for (Room room : rooms.values()) {
            if (room.isAvailable()) {
                System.out.println(room);
            }
        }
        int roomId = getIntInput("Enter room ID: ");
        Room room = rooms.get(roomId);
        if (room == null || !room.isAvailable()) {
            System.out.println("Room not available or invalid!");
            return;
        }
        room.addStudent(studentId);
        student.setRoomId(roomId);
        System.out.println("Room assigned successfully!");
    }

    // ROOM MANAGEMENT
    private static void roomManagement() {
        while (true) {
            System.out.println("\n=== ROOM MANAGEMENT ===");
            System.out.println("1. Add Room");
            System.out.println("2. View All Rooms");
            System.out.println("3. View Available Rooms");
            System.out.println("4. Update Room");
            System.out.println("5. Delete Room");
            System.out.println("6. Back to Main Menu");
            int choice = getIntInput("Enter your choice: ");
            switch (choice) {
                case 1:
                    addRoom();
                    break;
                case 2:
                    viewAllRooms();
                    break;
                case 3:
                    viewAvailableRooms();
                    break;
                case 4:
                    updateRoom();
                    break;
                case 5:
                    deleteRoom();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    private static void addRoom() {
        System.out.println("\n=== ADD ROOM ===");
        System.out.print("Enter room type (Single/Double/Triple/Quad): ");
        String roomType = scanner.nextLine();
        int capacity = getIntInput("Enter capacity: ");
        double rent = getDoubleInput("Enter monthly rent: ");
        Room room = new Room(roomType, capacity, rent);
        rooms.put(room.getRoomId(), room);
        System.out.println("Room added successfully! Room ID: " + room.getRoomId());
    }

    private static void viewAllRooms() {
        System.out.println("\n=== ALL ROOMS ===");
        if (rooms.isEmpty()) {
            System.out.println("No rooms found!");
            return;
        }
        for (Room room : rooms.values()) {
            System.out.println(room);
        }
    }

    private static void viewAvailableRooms() {
        System.out.println("\n=== AVAILABLE ROOMS ===");
        boolean found = false;
        for (Room room : rooms.values()) {
            if (room.isAvailable()) {
                System.out.println(room);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No available rooms!");
        }
    }

    private static void updateRoom() {
        System.out.println("\n=== UPDATE ROOM ===");
        int roomId = getIntInput("Enter room ID: ");
        Room room = rooms.get(roomId);
        if (room == null) {
            System.out.println("Room not found!");
            return;
        }
        System.out.println("Current details: " + room);
        System.out.print("Enter new room type (or press Enter to keep current): ");
        String roomType = scanner.nextLine();
        if (!roomType.trim().isEmpty()) {
            room.setRoomType(roomType);
        }
        System.out.print("Enter new monthly rent (or 0 to keep current): ");
        double rent = getDoubleInput("");
        if (rent > 0) {
            room.setMonthlyRent(rent);
        }
        System.out.println("Room updated successfully!");
    }

    private static void deleteRoom() {
        System.out.println("\n=== DELETE ROOM ===");
        int roomId = getIntInput("Enter room ID: ");
        Room room = rooms.get(roomId);
        if (room == null) {
            System.out.println("Room not found!");
            return;
        }
        if (room.getCurrentOccupancy() > 0) {
            System.out.println("Cannot delete room with students assigned!");
            return;
        }
        rooms.remove(roomId);
        System.out.println("Room deleted successfully!");
    }

    // STAFF MANAGEMENT
    private static void staffManagement() {
        while (true) {
            System.out.println("\n=== STAFF MANAGEMENT ===");
            System.out.println("1. Add Staff");
            System.out.println("2. View All Staff");
            System.out.println("3. Search Staff");
            System.out.println("4. Update Staff");
            System.out.println("5. Delete Staff");
            System.out.println("6. Back to Main Menu");
            int choice = getIntInput("Enter your choice: ");
            switch (choice) {
                case 1:
                    addStaff();
                    break;
                case 2:
                    viewAllStaff();
                    break;
                case 3:
                    searchStaff();
                    break;
                case 4:
                    updateStaff();
                    break;
                case 5:
                    deleteStaff();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    private static void addStaff() {
        System.out.println("\n=== ADD STAFF ===");
        System.out.print("Enter staff name: ");
        String name = scanner.nextLine();
        System.out.print("Enter position: ");
        String position = scanner.nextLine();
        System.out.print("Enter phone: ");
        String phone = scanner.nextLine();
        double salary = getDoubleInput("Enter salary: ");
        Staff staff = new Staff(name, position, phone, salary);
        staffMembers.put(staff.getStaffId(), staff);
        System.out.println("Staff added successfully! Staff ID: " + staff.getStaffId());
    }

    private static void viewAllStaff() {
        System.out.println("\n=== ALL STAFF ===");
        if (staffMembers.isEmpty()) {
            System.out.println("No staff found!");
            return;
        }
        for (Staff staff : staffMembers.values()) {
            System.out.println(staff);
        }
    }

    private static void searchStaff() {
        System.out.println("\n=== SEARCH STAFF ===");
        int staffId = getIntInput("Enter staff ID: ");
        Staff staff = staffMembers.get(staffId);
        if (staff != null) {
            System.out.println("Staff found:");
            System.out.println(staff);
        } else {
            System.out.println("Staff not found!");
        }
    }

    private static void updateStaff() {
        System.out.println("\n=== UPDATE STAFF ===");
        int staffId = getIntInput("Enter staff ID: ");
        Staff staff = staffMembers.get(staffId);
        if (staff == null) {
            System.out.println("Staff not found!");
            return;
        }
        System.out.println("Current details: " + staff);
        System.out.print("Enter new name (or press Enter to keep current): ");
        String name = scanner.nextLine();
        if (!name.trim().isEmpty()) {
            staff.setName(name);
        }
        System.out.print("Enter new position (or press Enter to keep current): ");
        String position = scanner.nextLine();
        if (!position.trim().isEmpty()) {
            staff.setPosition(position);
        }
        System.out.print("Enter new phone (or press Enter to keep current): ");
        String phone = scanner.nextLine();
        if (!phone.trim().isEmpty()) {
            staff.setPhone(phone);
        }
        System.out.print("Enter new salary (or 0 to keep current): ");
        double salary = getDoubleInput("");
        if (salary > 0) {
            staff.setSalary(salary);
        }
        System.out.println("Staff updated successfully!");
    }

    private static void deleteStaff() {
        System.out.println("\n=== DELETE STAFF ===");
        int staffId = getIntInput("Enter staff ID: ");
        Staff staff = staffMembers.get(staffId);
        if (staff == null) {
            System.out.println("Staff not found!");
            return;
        }
        staffMembers.remove(staffId);
        System.out.println("Staff deleted successfully!");
    }

    // PAYMENT MANAGEMENT
    private static void paymentManagement() {
        while (true) {
            System.out.println("\n=== PAYMENT MANAGEMENT ===");
            System.out.println("1. Record Payment");
            System.out.println("2. View All Payments");
            System.out.println("3. View Student Payments");
            System.out.println("4. Back to Main Menu");
            int choice = getIntInput("Enter your choice: ");
            switch (choice) {
                case 1:
                    recordPayment();
                    break;
                case 2:
                    viewAllPayments();
                    break;
                case 3:
                    viewStudentPayments();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    private static void recordPayment() {
        System.out.println("\n=== RECORD PAYMENT ===");
        int studentId = getIntInput("Enter student ID: ");
        Student student = students.get(studentId);
        if (student == null) {
            System.out.println("Student not found!");
            return;
        }
        System.out.println("Student: " + student.getName());
        double amount = getDoubleInput("Enter payment amount: ");
        System.out.print("Enter payment type (Rent/Deposit/Fee): ");
        String paymentType = scanner.nextLine();
        Payment payment = new Payment(studentId, amount, paymentType);
        payments.add(payment);
        System.out.println("Payment recorded successfully! Payment ID: " + payment.getPaymentId());
    }

    private static void viewAllPayments() {
        System.out.println("\n=== ALL PAYMENTS ===");
        if (payments.isEmpty()) {
            System.out.println("No payments found!");
            return;
        }
        for (Payment payment : payments) {
            System.out.println(payment);
        }
    }

    private static void viewStudentPayments() {
        System.out.println("\n=== STUDENT PAYMENTS ===");
        int studentId = getIntInput("Enter student ID: ");
        boolean found = false;
        for (Payment payment : payments) {
            if (payment.getStudentId() == studentId) {
                System.out.println(payment);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No payments found for this student!");
        }
    }

    // COMPLAINT MANAGEMENT
    private static void complaintManagement() {
        while (true) {
            System.out.println("\n=== COMPLAINT MANAGEMENT ===");
            System.out.println("1. Register Complaint");
            System.out.println("2. View All Complaints");
            System.out.println("3. View Student Complaints");
            System.out.println("4. Respond to Complaint");
            System.out.println("5. Back to Main Menu");
            int choice = getIntInput("Enter your choice: ");
            switch (choice) {
                case 1:
                    registerComplaint();
                    break;
                case 2:
                    viewAllComplaints();
                    break;
                case 3:
                    viewStudentComplaints();
                    break;
                case 4:
                    respondToComplaint();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    private static void registerComplaint() {
        System.out.println("\n=== REGISTER COMPLAINT ===");
        int studentId = getIntInput("Enter student ID: ");
        Student student = students.get(studentId);
        if (student == null) {
            System.out.println("Student not found!");
            return;
        }
        System.out.println("Student: " + student.getName());
        System.out.print("Enter complaint description: ");
        String description = scanner.nextLine();
        Complaint complaint = new Complaint(studentId, description);
        complaints.add(complaint);
        System.out.println("Complaint registered successfully! Complaint ID: " + complaint.getComplaintId());
    }

    private static void viewAllComplaints() {
        System.out.println("\n=== ALL COMPLAINTS ===");
        if (complaints.isEmpty()) {
            System.out.println("No complaints found!");
            return;
        }
        for (Complaint complaint : complaints) {
            System.out.println(complaint);
        }
    }

    private static void viewStudentComplaints() {
        System.out.println("\n=== VIEW STUDENT COMPLAINTS ===");
        int studentId = getIntInput("Enter student ID: ");
        boolean found = false;
        for (Complaint complaint : complaints) {
            if (complaint.getStudentId() == studentId) {
                System.out.println(complaint);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No complaints found for this student!");
        }
    }

    private static void respondToComplaint() {
        System.out.println("\n=== RESPOND TO COMPLAINT ===");
        int complaintId = getIntInput("Enter complaint ID: ");
        for (Complaint complaint : complaints) {
            if (complaint.getComplaintId() == complaintId) {
                if ("Closed".equals(complaint.getStatus())) {
                    System.out.println("This complaint has already been resolved.");
                    return;
                }
                System.out.print("Enter response: ");
                String response = scanner.nextLine();
                complaint.setResponse(response);
                complaint.setStatus("Closed");
                System.out.println("Response recorded successfully.");
                return;
            }
        }
        System.out.println("Complaint not found!");
    }

    // REPORT GENERATION
    private static void generateReports() {
        while (true) {
            System.out.println("\n=== REPORTS ===");
            System.out.println("1. Total Students");
            System.out.println("2. Total Rooms");
            System.out.println("3. Vacant Rooms");
            System.out.println("4. Total Payments Received");
            System.out.println("5. Back to Main Menu");
            int choice = getIntInput("Enter your choice: ");
            switch (choice) {
                case 1:
                    System.out.println("Total Students: " + students.size());
                    break;
                case 2:
                    System.out.println("Total Rooms: " + rooms.size());
                    break;
                case 3:
                    long vacantRooms = rooms.values().stream().filter(Room::isAvailable).count();
                    System.out.println("Vacant Rooms: " + vacantRooms);
                    break;
                case 4:
                    double totalPayments = payments.stream().mapToDouble(Payment::getAmount).sum();
                    System.out.println("Total Payments Received: $" + totalPayments);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}