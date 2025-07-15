package com.gqt;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Map.Entry;

public class FireDepartmentManagementSystem implements Serializable {
    // In-memory databases using Collections
    private static Map<Integer, Firefighter> firefighters = new HashMap<>();
    private static Map<Integer, FireTruck> fireTrucks = new HashMap<>();
    private static Map<Integer, Emergency> emergencies = new HashMap<>();
    private static Map<Integer, Equipment> equipments = new HashMap<>();

    // ID generators
    private static int firefighterIdCounter = 1;
    private static int truckIdCounter = 1;
    private static int emergencyIdCounter = 1;
    private static int equipmentIdCounter = 1;

    private static Scanner scanner = new Scanner(System.in);
    private static final String FILE_NAME = "fire_department_data.ser";

    // Firefighter class
    static class Firefighter implements Serializable {
        private int id;
        private String name;
        private String rank;
        private String phoneNumber;
        private String address;
        private boolean isAvailable;
        private LocalDateTime joinDate;

        public Firefighter(String name, String rank, String phoneNumber, String address) {
            this.id = firefighterIdCounter++;
            this.name = name;
            this.rank = rank;
            this.phoneNumber = phoneNumber;
            this.address = address;
            this.isAvailable = true;
            this.joinDate = LocalDateTime.now();
        }

        // Getters and Setters
        public int getId() { return id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getRank() { return rank; }
        public void setRank(String rank) { this.rank = rank; }
        public String getPhoneNumber() { return phoneNumber; }
        public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }
        public boolean isAvailable() { return isAvailable; }
        public void setAvailable(boolean available) { isAvailable = available; }
        public LocalDateTime getJoinDate() { return joinDate; }

        @Override
        public String toString() {
            return String.format("ID: %d | Name: %s | Rank: %s | Phone: %s | Available: %s | Joined: %s",
                    id, name, rank, phoneNumber, isAvailable ? "Yes" : "No",
                    joinDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        }
    }

    // FireTruck class
    static class FireTruck implements Serializable {
        private int id;
        private String truckNumber;
        private String type;
        private boolean isAvailable;
        private int capacity;
        private LocalDateTime lastMaintenance;

        public FireTruck(String truckNumber, String type, int capacity) {
            this.id = truckIdCounter++;
            this.truckNumber = truckNumber;
            this.type = type;
            this.capacity = capacity;
            this.isAvailable = true;
            this.lastMaintenance = LocalDateTime.now();
        }

        // Getters and Setters
        public int getId() { return id; }
        public String getTruckNumber() { return truckNumber; }
        public void setTruckNumber(String truckNumber) { this.truckNumber = truckNumber; }
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        public boolean isAvailable() { return isAvailable; }
        public void setAvailable(boolean available) { isAvailable = available; }
        public int getCapacity() { return capacity; }
        public void setCapacity(int capacity) { this.capacity = capacity; }
        public LocalDateTime getLastMaintenance() { return lastMaintenance; }
        public void setLastMaintenance(LocalDateTime lastMaintenance) { this.lastMaintenance = lastMaintenance; }

        @Override
        public String toString() {
            return String.format("ID: %d | Truck#: %s | Type: %s | Capacity: %d | Available: %s | Last Maintenance: %s",
                    id, truckNumber, type, capacity, isAvailable ? "Yes" : "No",
                    lastMaintenance.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        }
    }

    // Emergency class
    static class Emergency implements Serializable {
        private int id;
        private String type;
        private String location;
        private String description;
        private String priority;
        private String status;
        private LocalDateTime reportTime;
        private LocalDateTime responseTime;
        private List<Integer> assignedFirefighters;
        private List<Integer> assignedTrucks;

        public Emergency(String type, String location, String description, String priority) {
            this.id = emergencyIdCounter++;
            this.type = type;
            this.location = location;
            this.description = description;
            this.priority = priority;
            this.status = "REPORTED";
            this.reportTime = LocalDateTime.now();
            this.assignedFirefighters = new ArrayList<>();
            this.assignedTrucks = new ArrayList<>();
        }

        // Getters and Setters
        public int getId() { return id; }
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        public String getLocation() { return location; }
        public void setLocation(String location) { this.location = location; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public String getPriority() { return priority; }
        public void setPriority(String priority) { this.priority = priority; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        public LocalDateTime getReportTime() { return reportTime; }
        public LocalDateTime getResponseTime() { return responseTime; }
        public void setResponseTime(LocalDateTime responseTime) { this.responseTime = responseTime; }
        public List<Integer> getAssignedFirefighters() { return assignedFirefighters; }
        public List<Integer> getAssignedTrucks() { return assignedTrucks; }

        @Override
        public String toString() {
            return String.format("ID: %d | Type: %s | Location: %s | Priority: %s | Status: %s | Reported: %s",
                    id, type, location, priority, status,
                    reportTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        }
    }

    // Equipment class
    static class Equipment implements Serializable {
        private int id;
        private String name;
        private String type;
        private int quantity;
        private String condition;
        private LocalDateTime lastInspection;

        public Equipment(String name, String type, int quantity, String condition) {
            this.id = equipmentIdCounter++;
            this.name = name;
            this.type = type;
            this.quantity = quantity;
            this.condition = condition;
            this.lastInspection = LocalDateTime.now();
        }

        // Getters and Setters
        public int getId() { return id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
        public String getCondition() { return condition; }
        public void setCondition(String condition) { this.condition = condition; }
        public LocalDateTime getLastInspection() { return lastInspection; }
        public void setLastInspection(LocalDateTime lastInspection) { this.lastInspection = lastInspection; }

        @Override
        public String toString() {
            return String.format("ID: %d | Name: %s | Type: %s | Quantity: %d | Condition: %s | Last Inspection: %s",
                    id, name, type, quantity, condition,
                    lastInspection.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        }
    }

    public static void main(String[] args) {
        loadData(); // Load existing data at startup

        System.out.println("=== FIRE DEPARTMENT MANAGEMENT SYSTEM ===");
        System.out.println("Welcome to the Fire Department Management System!");

        while (true) {
            displayMainMenu();
            int choice = getIntInput("Enter your choice: ");
            switch (choice) {
                case 1:
                    manageFirefighters();
                    break;
                case 2:
                    manageFireTrucks();
                    break;
                case 3:
                    manageEmergencies();
                    break;
                case 4:
                    manageEquipment();
                    break;
                case 5:
                    generateReports();
                    break;
                case 6:
                    saveData();
                    break;
                case 7:
                    loadData();
                    break;
                case 8:
                    System.out.println("Thank you for using Fire Department Management System!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    private static void displayMainMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("MAIN MENU");
        System.out.println("=".repeat(50));
        System.out.println("1. Manage Firefighters");
        System.out.println("2. Manage Fire Trucks");
        System.out.println("3. Manage Emergencies");
        System.out.println("4. Manage Equipment");
        System.out.println("5. Generate Reports");
        System.out.println("6. Save Data");
        System.out.println("7. Load Data");
        System.out.println("8. Exit");
        System.out.println("=".repeat(50));
    }

    // Utility methods
    private static int getIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                String input = scanner.nextLine();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(firefighters);
            oos.writeObject(fireTrucks);
            oos.writeObject(emergencies);
            oos.writeObject(equipments);
            oos.writeInt(firefighterIdCounter);
            oos.writeInt(truckIdCounter);
            oos.writeInt(emergencyIdCounter);
            oos.writeInt(equipmentIdCounter);
            System.out.println("Data saved successfully to file: " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private static void loadData() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("No saved data found. Initializing with sample data...");
            initializeSampleData();
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            firefighters = (Map<Integer, Firefighter>) ois.readObject();
            fireTrucks = (Map<Integer, FireTruck>) ois.readObject();
            emergencies = (Map<Integer, Emergency>) ois.readObject();
            equipments = (Map<Integer, Equipment>) ois.readObject();
            firefighterIdCounter = ois.readInt();
            truckIdCounter = ois.readInt();
            emergencyIdCounter = ois.readInt();
            equipmentIdCounter = ois.readInt();

            System.out.println("Data loaded successfully from file: " + FILE_NAME);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data: " + e.getMessage());
            initializeSampleData();
        }
    }

    private static void initializeSampleData() {
        // Sample Firefighters
        firefighters.put(1, new Firefighter("John Smith", "Captain", "555-0101", "123 Main St"));
        firefighters.put(2, new Firefighter("Sarah Johnson", "Lieutenant", "555-0102", "456 Oak Ave"));
        firefighters.put(3, new Firefighter("Mike Wilson", "Firefighter", "555-0103", "789 Pine Rd"));
        firefighters.put(4, new Firefighter("Emily Davis", "Firefighter", "555-0104", "321 Elm St"));
        firefighters.put(5, new Firefighter("Robert Brown", "Chief", "555-0105", "654 Cedar Ln"));

        // Sample Fire Trucks
        fireTrucks.put(1, new FireTruck("ENGINE-01", "Engine", 1000));
        fireTrucks.put(2, new FireTruck("LADDER-01", "Ladder", 500));
        fireTrucks.put(3, new FireTruck("RESCUE-01", "Rescue", 300));
        fireTrucks.put(4, new FireTruck("TANKER-01", "Tanker", 2000));

        // Sample Equipment
        equipments.put(1, new Equipment("Fire Hose", "Hose", 25, "Good"));
        equipments.put(2, new Equipment("Extension Ladder", "Ladder", 8, "Good"));
        equipments.put(3, new Equipment("Oxygen Tank", "Oxygen", 20, "Good"));
        equipments.put(4, new Equipment("Hydraulic Cutter", "Tool", 4, "Fair"));
        equipments.put(5, new Equipment("Safety Helmet", "Safety", 50, "Good"));
        equipments.put(6, new Equipment("Fire Extinguisher", "Safety", 30, "Good"));

        // Sample Emergency
        Emergency sampleEmergency = new Emergency("Fire", "Downtown Office Building", "Structure fire on 3rd floor", "HIGH");
        emergencies.put(1, sampleEmergency);

        // Update counters to maintain proper ID generation
        firefighterIdCounter = 6;
        truckIdCounter = 5;
        equipmentIdCounter = 7;
        emergencyIdCounter = 2;

        System.out.println("Sample data initialized successfully!");
        System.out.println("- 5 Firefighters added");
        System.out.println("- 4 Fire Trucks added");
        System.out.println("- 6 Equipment items added");
        System.out.println("- 1 Sample emergency added");
    }

    // Firefighter Management
    private static void manageFirefighters() {
        while (true) {
            System.out.println("\n" + "-".repeat(40));
            System.out.println("FIREFIGHTER MANAGEMENT");
            System.out.println("-".repeat(40));
            System.out.println("1. Add Firefighter");
            System.out.println("2. View All Firefighters");
            System.out.println("3. Update Firefighter");
            System.out.println("4. Delete Firefighter");
            System.out.println("5. Search Firefighter");
            System.out.println("6. Back to Main Menu");
            int choice = getIntInput("Enter your choice: ");
            switch (choice) {
                case 1:
                    addFirefighter();
                    break;
                case 2:
                    viewAllFirefighters();
                    break;
                case 3:
                    updateFirefighter();
                    break;
                case 4:
                    deleteFirefighter();
                    break;
                case 5:
                    searchFirefighter();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private static void addFirefighter() {
        System.out.println("\n--- Add New Firefighter ---");
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter rank (Firefighter/Lieutenant/Captain/Chief): ");
        String rank = scanner.nextLine();
        System.out.print("Enter phone number: ");
        String phone = scanner.nextLine();
        System.out.print("Enter address: ");
        String address = scanner.nextLine();
        Firefighter firefighter = new Firefighter(name, rank, phone, address);
        firefighters.put(firefighter.getId(), firefighter);
        System.out.println("Firefighter added successfully! ID: " + firefighter.getId());
    }

    private static void viewAllFirefighters() {
        System.out.println("\n--- All Firefighters ---");
        if (firefighters.isEmpty()) {
            System.out.println("No firefighters found.");
            return;
        }
        for (Firefighter firefighter : firefighters.values()) {
            System.out.println(firefighter);
        }
    }

    private static void updateFirefighter() {
        System.out.println("\n--- Update Firefighter ---");
        int id = getIntInput("Enter firefighter ID to update: ");
        Firefighter firefighter = firefighters.get(id);
        if (firefighter == null) {
            System.out.println("Firefighter not found!");
            return;
        }
        System.out.println("Current details: " + firefighter);
        System.out.print("Enter new name (or press Enter to keep current): ");
        String name = scanner.nextLine();
        if (!name.trim().isEmpty()) {
            firefighter.setName(name);
        }
        System.out.print("Enter new rank (or press Enter to keep current): ");
        String rank = scanner.nextLine();
        if (!rank.trim().isEmpty()) {
            firefighter.setRank(rank);
        }
        System.out.print("Enter new phone (or press Enter to keep current): ");
        String phone = scanner.nextLine();
        if (!phone.trim().isEmpty()) {
            firefighter.setPhoneNumber(phone);
        }
        System.out.println("Firefighter updated successfully!");
    }

    private static void deleteFirefighter() {
        System.out.println("\n--- Delete Firefighter ---");
        int id = getIntInput("Enter firefighter ID to delete: ");
        if (firefighters.containsKey(id)) {
            firefighters.remove(id);
            System.out.println("Firefighter deleted successfully!");
        } else {
            System.out.println("Firefighter not found!");
        }
    }

    private static void searchFirefighter() {
        System.out.println("\n--- Search Firefighter ---");
        System.out.print("Enter name to search: ");
        String searchName = scanner.nextLine().toLowerCase();
        boolean found = false;
        for (Firefighter firefighter : firefighters.values()) {
            if (firefighter.getName().toLowerCase().contains(searchName)) {
                System.out.println(firefighter);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No firefighters found with that name.");
        }
    }

    // Fire Truck Management
    private static void manageFireTrucks() {
        while (true) {
            System.out.println("\n" + "-".repeat(40));
            System.out.println("FIRE TRUCK MANAGEMENT");
            System.out.println("-".repeat(40));
            System.out.println("1. Add Fire Truck");
            System.out.println("2. View All Fire Trucks");
            System.out.println("3. Update Fire Truck");
            System.out.println("4. Delete Fire Truck");
            System.out.println("5. Schedule Maintenance");
            System.out.println("6. Back to Main Menu");
            int choice = getIntInput("Enter your choice: ");
            switch (choice) {
                case 1:
                    addFireTruck();
                    break;
                case 2:
                    viewAllFireTrucks();
                    break;
                case 3:
                    updateFireTruck();
                    break;
                case 4:
                    deleteFireTruck();
                    break;
                case 5:
                    scheduleMaintenance();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private static void addFireTruck() {
        System.out.println("\n--- Add New Fire Truck ---");
        System.out.print("Enter truck number: ");
        String truckNumber = scanner.nextLine();
        System.out.print("Enter type (Engine/Ladder/Rescue/Tanker): ");
        String type = scanner.nextLine();
        int capacity = getIntInput("Enter capacity (gallons): ");
        FireTruck truck = new FireTruck(truckNumber, type, capacity);
        fireTrucks.put(truck.getId(), truck);
        System.out.println("Fire truck added successfully! ID: " + truck.getId());
    }

    private static void viewAllFireTrucks() {
        System.out.println("\n--- All Fire Trucks ---");
        if (fireTrucks.isEmpty()) {
            System.out.println("No fire trucks found.");
            return;
        }
        for (FireTruck truck : fireTrucks.values()) {
            System.out.println(truck);
        }
    }

    private static void updateFireTruck() {
        System.out.println("\n--- Update Fire Truck ---");
        int id = getIntInput("Enter truck ID to update: ");
        FireTruck truck = fireTrucks.get(id);
        if (truck == null) {
            System.out.println("Fire truck not found!");
            return;
        }
        System.out.println("Current details: " + truck);
        System.out.print("Enter new truck number (or press Enter to keep current): ");
        String truckNumber = scanner.nextLine();
        if (!truckNumber.trim().isEmpty()) {
            truck.setTruckNumber(truckNumber);
        }
        System.out.print("Enter new type (or press Enter to keep current): ");
        String type = scanner.nextLine();
        if (!type.trim().isEmpty()) {
            truck.setType(type);
        }
        System.out.println("Fire truck updated successfully!");
    }

    private static void deleteFireTruck() {
        System.out.println("\n--- Delete Fire Truck ---");
        int id = getIntInput("Enter truck ID to delete: ");
        if (fireTrucks.containsKey(id)) {
            fireTrucks.remove(id);
            System.out.println("Fire truck deleted successfully!");
        } else {
            System.out.println("Fire truck not found!");
        }
    }

    private static void scheduleMaintenance() {
        System.out.println("\n--- Schedule Maintenance ---");
        int id = getIntInput("Enter truck ID for maintenance: ");
        FireTruck truck = fireTrucks.get(id);
        if (truck == null) {
            System.out.println("Fire truck not found!");
            return;
        }
        truck.setLastMaintenance(LocalDateTime.now());
        truck.setAvailable(false);
        System.out.println("Maintenance scheduled for truck: " + truck.getTruckNumber());
        System.out.println("Truck marked as unavailable.");
    }

    // Emergency Management
    private static void manageEmergencies() {
        while (true) {
            System.out.println("\n" + "-".repeat(40));
            System.out.println("EMERGENCY MANAGEMENT");
            System.out.println("-".repeat(40));
            System.out.println("1. Report Emergency");
            System.out.println("2. View All Emergencies");
            System.out.println("3. Update Emergency Status");
            System.out.println("4. Assign Resources");
            System.out.println("5. Close Emergency");
            System.out.println("6. Back to Main Menu");
            int choice = getIntInput("Enter your choice: ");
            switch (choice) {
                case 1:
                    reportEmergency();
                    break;
                case 2:
                    viewAllEmergencies();
                    break;
                case 3:
                    updateEmergencyStatus();
                    break;
                case 4:
                    assignResources();
                    break;
                case 5:
                    closeEmergency();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private static void reportEmergency() {
        System.out.println("\n--- Report New Emergency ---");
        System.out.print("Enter emergency type (Fire/Medical/Rescue/Hazmat): ");
        String type = scanner.nextLine();
        System.out.print("Enter location: ");
        String location = scanner.nextLine();
        System.out.print("Enter description: ");
        String description = scanner.nextLine();
        System.out.print("Enter priority (HIGH/MEDIUM/LOW): ");
        String priority = scanner.nextLine();
        Emergency emergency = new Emergency(type, location, description, priority);
        emergencies.put(emergency.getId(), emergency);
        System.out.println("Emergency reported successfully! ID: " + emergency.getId());
    }

    private static void viewAllEmergencies() {
        System.out.println("\n--- All Emergencies ---");
        if (emergencies.isEmpty()) {
            System.out.println("No emergencies found.");
            return;
        }
        for (Emergency emergency : emergencies.values()) {
            System.out.println(emergency);
            if (!emergency.getAssignedFirefighters().isEmpty()) {
                System.out.println("  Assigned Firefighters: " + emergency.getAssignedFirefighters());
            }
            if (!emergency.getAssignedTrucks().isEmpty()) {
                System.out.println("  Assigned Trucks: " + emergency.getAssignedTrucks());
            }
        }
    }

    private static void updateEmergencyStatus() {
        System.out.println("\n--- Update Emergency Status ---");
        int id = getIntInput("Enter emergency ID: ");
        Emergency emergency = emergencies.get(id);
        if (emergency == null) {
            System.out.println("Emergency not found!");
            return;
        }
        System.out.println("Current status: " + emergency.getStatus());
        System.out.print("Enter new status (REPORTED/DISPATCHED/RESPONDING/ON_SCENE/RESOLVED): ");
        String status = scanner.nextLine();
        emergency.setStatus(status);
        if ("DISPATCHED".equals(status) && emergency.getResponseTime() == null) {
            emergency.setResponseTime(LocalDateTime.now());
        }
        System.out.println("Emergency status updated successfully!");
    }

    private static void assignResources() {
        System.out.println("\n--- Assign Resources to Emergency ---");
        int id = getIntInput("Enter emergency ID: ");
        Emergency emergency = emergencies.get(id);
        if (emergency == null) {
            System.out.println("Emergency not found!");
            return;
        }
        System.out.println("Emergency: " + emergency);
        // Assign firefighters
        System.out.println("\nAvailable Firefighters:");
        for (Firefighter ff : firefighters.values()) {
            if (ff.isAvailable()) {
                System.out.println(ff);
            }
        }
        System.out.print("Enter firefighter IDs to assign (comma-separated, or press Enter to skip): ");
        String ffIds = scanner.nextLine();
        if (!ffIds.trim().isEmpty()) {
            String[] ids = ffIds.split(",");
            for (String ffId : ids) {
                try {
                    int firefighterId = Integer.parseInt(ffId.trim());
                    if (firefighters.containsKey(firefighterId)) {
                        emergency.getAssignedFirefighters().add(firefighterId);
                        firefighters.get(firefighterId).setAvailable(false);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid firefighter ID: " + ffId);
                }
            }
        }
        // Assign trucks
        System.out.println("\nAvailable Fire Trucks:");
        for (FireTruck truck : fireTrucks.values()) {
            if (truck.isAvailable()) {
                System.out.println(truck);
            }
        }
        System.out.print("Enter truck IDs to assign (comma-separated, or press Enter to skip): ");
        String truckIds = scanner.nextLine();
        if (!truckIds.trim().isEmpty()) {
            String[] ids = truckIds.split(",");
            for (String truckId : ids) {
                try {
                    int tId = Integer.parseInt(truckId.trim());
                    if (fireTrucks.containsKey(tId)) {
                        emergency.getAssignedTrucks().add(tId);
                        fireTrucks.get(tId).setAvailable(false);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid truck ID: " + truckId);
                }
            }
        }
        System.out.println("Resources assigned successfully!");
    }

    private static void closeEmergency() {
        System.out.println("\n--- Close Emergency ---");
        int id = getIntInput("Enter emergency ID to close: ");
        Emergency emergency = emergencies.get(id);
        if (emergency == null) {
            System.out.println("Emergency not found!");
            return;
        }
        emergency.setStatus("RESOLVED");
        // Free up assigned resources
        for (int ffId : emergency.getAssignedFirefighters()) {
            if (firefighters.containsKey(ffId)) {
                firefighters.get(ffId).setAvailable(true);
            }
        }
        for (int truckId : emergency.getAssignedTrucks()) {
            if (fireTrucks.containsKey(truckId)) {
                fireTrucks.get(truckId).setAvailable(true);
            }
        }
        System.out.println("Emergency closed and resources freed!");
    }

    // Equipment Management
    private static void manageEquipment() {
        while (true) {
            System.out.println("\n" + "-".repeat(40));
            System.out.println("EQUIPMENT MANAGEMENT");
            System.out.println("-".repeat(40));
            System.out.println("1. Add Equipment");
            System.out.println("2. View All Equipment");
            System.out.println("3. Update Equipment");
            System.out.println("4. Delete Equipment");
            System.out.println("5. Inspect Equipment");
            System.out.println("6. Back to Main Menu");
            int choice = getIntInput("Enter your choice: ");
            switch (choice) {
                case 1:
                    addEquipment();
                    break;
                case 2:
                    viewAllEquipment();
                    break;
                case 3:
                    updateEquipment();
                    break;
                case 4:
                    deleteEquipment();
                    break;
                case 5:
                    inspectEquipment();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private static void addEquipment() {
        System.out.println("\n--- Add New Equipment ---");
        System.out.print("Enter equipment name: ");
        String name = scanner.nextLine();
        System.out.print("Enter type (Hose/Ladder/Oxygen/Tool/Safety): ");
        String type = scanner.nextLine();
        int quantity = getIntInput("Enter quantity: ");
        System.out.print("Enter condition (Good/Fair/Poor/Needs Repair): ");
        String condition = scanner.nextLine();
        Equipment equipment = new Equipment(name, type, quantity, condition);
        equipments.put(equipment.getId(), equipment);
        System.out.println("Equipment added successfully! ID: " + equipment.getId());
    }

    private static void viewAllEquipment() {
        System.out.println("\n--- All Equipment ---");
        if (equipments.isEmpty()) {
            System.out.println("No equipment found.");
            return;
        }
        for (Equipment equipment : equipments.values()) {
            System.out.println(equipment);
        }
    }

    private static void updateEquipment() {
        System.out.println("\n--- Update Equipment ---");
        int id = getIntInput("Enter equipment ID to update: ");
        Equipment equipment = equipments.get(id);
        if (equipment == null) {
            System.out.println("Equipment not found!");
            return;
        }
        System.out.println("Current details: " + equipment);
        int newQuantity = getIntInput("Enter new quantity (or 0 to keep current): ");
        if (newQuantity > 0) {
            equipment.setQuantity(newQuantity);
        }
        System.out.print("Enter new condition (or press Enter to keep current): ");
        String condition = scanner.nextLine();
        if (!condition.trim().isEmpty()) {
            equipment.setCondition(condition);
        }
        System.out.println("Equipment updated successfully!");
    }

    private static void deleteEquipment() {
        System.out.println("\n--- Delete Equipment ---");
        int id = getIntInput("Enter equipment ID to delete: ");
        if (equipments.containsKey(id)) {
            equipments.remove(id);
            System.out.println("Equipment deleted successfully!");
        } else {
            System.out.println("Equipment not found!");
        }
    }

    private static void inspectEquipment() {
        System.out.println("\n--- Inspect Equipment ---");
        int id = getIntInput("Enter equipment ID to inspect: ");
        Equipment equipment = equipments.get(id);
        if (equipment == null) {
            System.out.println("Equipment not found!");
            return;
        }
        equipment.setLastInspection(LocalDateTime.now());
        System.out.print("Enter inspection result (Good/Fair/Poor/Needs Repair): ");
        String condition = scanner.nextLine();
        equipment.setCondition(condition);
        System.out.println("Equipment inspection completed!");
    }

    // Reports
    private static void generateReports() {
        while (true) {
            System.out.println("\n" + "-".repeat(40));
            System.out.println("REPORTS");
            System.out.println("-".repeat(40));
            System.out.println("1. Department Overview");
            System.out.println("2. Active Emergencies Report");
            System.out.println("3. Personnel Status Report");
            System.out.println("4. Equipment Status Report");
            System.out.println("5. Vehicle Status Report");
            System.out.println("6. Back to Main Menu");
            int choice = getIntInput("Enter your choice: ");
            switch (choice) {
                case 1:
                    departmentOverview();
                    break;
                case 2:
                    activeEmergenciesReport();
                    break;
                case 3:
                    personnelStatusReport();
                    break;
                case 4:
                    equipmentStatusReport();
                    break;
                case 5:
                    vehicleStatusReport();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private static void departmentOverview() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("DEPARTMENT OVERVIEW REPORT");
        System.out.println("=".repeat(50));
        System.out.println("Generated on: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        System.out.println();
        // Personnel Summary
        int totalFirefighters = firefighters.size();
        int availableFirefighters = 0;
        for (Firefighter ff : firefighters.values()) {
            if (ff.isAvailable()) availableFirefighters++;
        }
        System.out.println("PERSONNEL SUMMARY:");
        System.out.println("  Total Firefighters: " + totalFirefighters);
        System.out.println("  Available: " + availableFirefighters);
        System.out.println("  On Duty: " + (totalFirefighters - availableFirefighters));
        // Vehicle Summary
        int totalTrucks = fireTrucks.size();
        int availableTrucks = 0;
        for (FireTruck truck : fireTrucks.values()) {
            if (truck.isAvailable()) availableTrucks++;
        }
        System.out.println("\nVEHICLE SUMMARY:");
        System.out.println("  Total Fire Trucks: " + totalTrucks);
        System.out.println("  Available: " + availableTrucks);
        System.out.println("  In Use/Maintenance: " + (totalTrucks - availableTrucks));
        // Emergency Summary
        int totalEmergencies = emergencies.size();
        int activeEmergencies = 0;
        int resolvedEmergencies = 0;
        for (Emergency emergency : emergencies.values()) {
            if ("RESOLVED".equals(emergency.getStatus())) {
                resolvedEmergencies++;
            } else {
                activeEmergencies++;
            }
        }
        System.out.println("\nEMERGENCY SUMMARY:");
        System.out.println("  Total Emergencies: " + totalEmergencies);
        System.out.println("  Active: " + activeEmergencies);
        System.out.println("  Resolved: " + resolvedEmergencies);
        // Equipment Summary
        System.out.println("\nEQUIPMENT SUMMARY:");
        System.out.println("  Total Equipment Types: " + equipments.size());
        int totalQuantity = 0;
        for (Equipment eq : equipments.values()) {
            totalQuantity += eq.getQuantity();
        }
        System.out.println("  Total Equipment Items: " + totalQuantity);
        System.out.println("=".repeat(50));
    }

    private static void activeEmergenciesReport() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("ACTIVE EMERGENCIES REPORT");
        System.out.println("=".repeat(50));
        boolean hasActive = false;
        for (Emergency emergency : emergencies.values()) {
            if (!"RESOLVED".equals(emergency.getStatus())) {
                System.out.println(emergency);
                System.out.println("  Description: " + emergency.getDescription());
                if (!emergency.getAssignedFirefighters().isEmpty()) {
                    System.out.print("  Assigned Personnel: ");
                    for (int ffId : emergency.getAssignedFirefighters()) {
                        Firefighter ff = firefighters.get(ffId);
                        if (ff != null) {
                            System.out.print(ff.getName() + " ");
                        }
                    }
                    System.out.println();
                }
                if (!emergency.getAssignedTrucks().isEmpty()) {
                    System.out.print("  Assigned Vehicles: ");
                    for (int truckId : emergency.getAssignedTrucks()) {
                        FireTruck truck = fireTrucks.get(truckId);
                        if (truck != null) {
                            System.out.print(truck.getTruckNumber() + " ");
                        }
                    }
                    System.out.println();
                }
                System.out.println();
                hasActive = true;
            }
        }
        if (!hasActive) {
            System.out.println("No active emergencies at this time.");
        }
        System.out.println("=".repeat(50));
    }

    private static void personnelStatusReport() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("PERSONNEL STATUS REPORT");
        System.out.println("=".repeat(50));
        System.out.println("AVAILABLE PERSONNEL:");
        boolean hasAvailable = false;
        for (Firefighter ff : firefighters.values()) {
            if (ff.isAvailable()) {
                System.out.println("  " + ff.getName() + " (" + ff.getRank() + ") - " + ff.getPhoneNumber());
                hasAvailable = true;
            }
        }
        if (!hasAvailable) {
            System.out.println("  No personnel currently available.");
        }
        System.out.println("\nON DUTY PERSONNEL:");
        boolean hasOnDuty = false;
        for (Firefighter ff : firefighters.values()) {
            if (!ff.isAvailable()) {
                System.out.println("  " + ff.getName() + " (" + ff.getRank() + ") - " + ff.getPhoneNumber());
                hasOnDuty = true;
            }
        }
        if (!hasOnDuty) {
            System.out.println("  No personnel currently on duty.");
        }
        // Rank Distribution
        Map<String, Integer> rankCount = new HashMap<>();
        for (Firefighter ff : firefighters.values()) {
            rankCount.put(ff.getRank(), rankCount.getOrDefault(ff.getRank(), 0) + 1);
        }
        System.out.println("\nRANK DISTRIBUTION:");
        for (Entry<String, Integer> entry : rankCount.entrySet()) {
            System.out.println("  " + entry.getKey() + ": " + entry.getValue());
        }
        System.out.println("=".repeat(50));
    }

    private static void equipmentStatusReport() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("EQUIPMENT STATUS REPORT");
        System.out.println("=".repeat(50));
        if (equipments.isEmpty()) {
            System.out.println("No equipment registered.");
            return;
        }
        // Group by condition
        Map<String, List<Equipment>> byCondition = new HashMap<>();
        for (Equipment eq : equipments.values()) {
            byCondition.computeIfAbsent(eq.getCondition(), k -> new ArrayList<>()).add(eq);
        }
        for (Entry<String, List<Equipment>> entry : byCondition.entrySet()) {
            System.out.println(entry.getKey().toUpperCase() + " CONDITION:");
            for (Equipment eq : entry.getValue()) {
                System.out.println("  " + eq.getName() + " (" + eq.getType() + ") - Qty: " + eq.getQuantity());
            }
            System.out.println();
        }
        // Equipment by type
        Map<String, Integer> typeCount = new HashMap<>();
        for (Equipment eq : equipments.values()) {
            typeCount.put(eq.getType(), typeCount.getOrDefault(eq.getType(), 0) + eq.getQuantity());
        }
        System.out.println("EQUIPMENT BY TYPE:");
        for (Entry<String, Integer> entry : typeCount.entrySet()) {
            System.out.println("  " + entry.getKey() + ": " + entry.getValue() + " items");
        }
        System.out.println("=".repeat(50));
    }

    private static void vehicleStatusReport() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("VEHICLE STATUS REPORT");
        System.out.println("=".repeat(50));
        System.out.println("AVAILABLE VEHICLES:");
        boolean hasAvailable = false;
        for (FireTruck truck : fireTrucks.values()) {
            if (truck.isAvailable()) {
                System.out.println("  " + truck.getTruckNumber() + " (" + truck.getType() + ") - Capacity: " + truck.getCapacity() + " gallons");
                hasAvailable = true;
            }
        }
        if (!hasAvailable) {
            System.out.println("  No vehicles currently available.");
        }
        System.out.println("\nIN USE/MAINTENANCE VEHICLES:");
        boolean hasInUse = false;
        for (FireTruck truck : fireTrucks.values()) {
            if (!truck.isAvailable()) {
                System.out.println("  " + truck.getTruckNumber() + " (" + truck.getType() + ") - Last Maintenance: " +
                        truck.getLastMaintenance().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
                hasInUse = true;
            }
        }
        if (!hasInUse) {
            System.out.println("  No vehicles currently in use or maintenance.");
        }
        // Vehicle type distribution
        Map<String, Integer> typeCount = new HashMap<>();
        for (FireTruck truck : fireTrucks.values()) {
            typeCount.put(truck.getType(), typeCount.getOrDefault(truck.getType(), 0) + 1);
        }
        System.out.println("\nVEHICLE TYPE DISTRIBUTION:");
        for (Entry<String, Integer> entry : typeCount.entrySet()) {
            System.out.println("  " + entry.getKey() + ": " + entry.getValue());
        }
        System.out.println("=".repeat(50));
    }
}