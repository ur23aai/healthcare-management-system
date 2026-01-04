# Healthcare Management System - Part 2

**Student:** Urvil Rathod  
**Module:** 6COM2008 - Software Architecture  
**Tutor:** Dr. John Kanyaru  
**University:** University of Hertfordshire

---

## What This Project Does

This is my implementation of a Healthcare Management System for the Part 2 assignment. It's a Java application that manages patients, appointments, prescriptions, and referrals using proper software architecture patterns.

The system uses **MVC (Model-View-Controller)** pattern to organize the code and **Singleton pattern** for managing referrals. Everything is built with plain Java and Swing GUI - no databases or external libraries.

---

## Architecture & Design Patterns

### MVC Pattern Structure

I organized the code into three main layers:

**Model (`src/model/`)** - All the business objects like:
- Patient, Doctor (abstract class)
- GeneralPractitioner, SpecialistDoctor (inherit from Doctor)
- Appointment, Prescription, Referral
- MedicalRecord, Facility, Staff, Nurse, AdminStaff

**View (`src/view/`)** - The GUI components using Java Swing:
- MainFrame (main window)
- PatientPanel, AppointmentPanel, PrescriptionPanel, ReferralPanel

**Controller (`src/controller/`)** - Business logic and data handling:
- PatientController, AppointmentController
- PrescriptionController, ReferralController
- ReferralManager (Singleton pattern)

### Singleton Pattern - ReferralManager

The `ReferralManager` class uses Singleton pattern to make sure there's only one instance managing all referrals in the system. This is important because:
- Only one referral queue exists
- Email generation is centralized
- Audit trail is consistent
- No duplicate referral processing
```java
// Only one instance created
ReferralManager manager = ReferralManager.getInstance();
```

### Object-Oriented Principles Used

- **Inheritance**: Doctor class is abstract, and GeneralPractitioner and SpecialistDoctor extend it
- **Composition**: Patient contains MedicalRecord objects (strong relationship)
- **Aggregation**: AdminStaff manages Appointment objects (weak relationship)
- **Encapsulation**: All attributes are private with getters/setters

---

## Project Structure
```
HealthcareManagementSystem/
├── src/
│   ├── Main.java
│   ├── model/          (12 classes - Patient, Doctor, etc.)
│   ├── controller/     (5 classes - Controllers + Singleton)
│   ├── view/           (5 classes - GUI panels)
│   └── util/           (2 classes - CSV reading/writing)
├── data/               (7 CSV files with test data)
└── output/             (New prescriptions & referrals saved here)
```

---

## How to Run

1. Open IntelliJ IDEA
2. **File → Open** and select the `HealthcareManagementSystem` folder
3. Make sure Java SDK 8+ is configured (**File → Project Structure**)
4. Navigate to `src/Main.java`
5. Right-click and select **Run 'Main.main()'**
6. The GUI window will open with tabs for Patients, Appointments, Prescriptions, and Referrals

---

## Features Implemented

### Data Loading
- Loads all CSV files automatically when you start the app
- Displays data in tables with columns and scrolling
- Shows: 10 patients, 12 appointments, 10 prescriptions, 10 referrals

### CRUD Operations (Create, Read, Update, Delete)

**Patients:**
- ✅ Add new patient (auto-generates ID like P011, P012...)
- ✅ Edit patient details (phone, email, address)
- ✅ Delete patient
- ✅ View all patients in table
- Saves to: `data/patients.csv`

**Appointments:**
- ✅ Add new appointment
- ✅ Edit appointment (date, time, status)
- ✅ Delete appointment
- ✅ View all appointments
- Saves to: `data/appointments.csv`

**Prescriptions:**
- ✅ Create new prescription (auto-generates ID like RX011, RX012...)
- ✅ Edit prescription status and notes
- ✅ Delete prescription
- ✅ View all prescriptions
- Saves to: `data/prescriptions.csv` AND `output/new_prescriptions.csv`

**Referrals (Uses Singleton Pattern):**
- ✅ Create new referral
- ✅ Edit referral status and notes
- ✅ Delete referral
- ✅ Generate email letter for referral
- Saves to: `data/referrals.csv`
- Referral emails saved to: `output/new_referrals.txt`

### Referral Email Generation (Singleton Pattern)

When you click "Generate Email" button:
1. Singleton ReferralManager processes the referral
2. Creates a professional email letter format
3. Saves it to `output/new_referrals.txt`
4. Logs the action in audit trail
5. Updates electronic health record (simulated)

This demonstrates the Singleton pattern because only ONE instance of ReferralManager handles all referrals system-wide.

---

## Testing the System

### Test 1: View Existing Data
- Open the app and click through all tabs
- You should see pre-loaded data in all tables

### Test 2: Add a New Patient
1. Go to **Patients** tab
2. Click **Add** button
3. Fill in the form (First Name, Last Name, NHS Number, etc.)
4. Click **OK**
5. New patient appears in table with ID P011
6. Check `data/patients.csv` - it's saved there

### Test 3: Create a Prescription
1. Go to **Prescriptions** tab
2. Click **Add** button
3. Enter Patient ID (like P001), Medication name, Dosage, etc.
4. Click **OK**
5. Check `output/new_prescriptions.csv` - new prescription is saved

### Test 4: Create Referral & Generate Email (Singleton Demo)
1. Go to **Referrals** tab
2. Click **Add** button
3. Fill in Patient ID, Reason, Clinical Summary, Urgency
4. Click **OK**
5. Select the referral you just created
6. Click **Generate Email** button
7. Open `output/new_referrals.txt` to see the email letter
8. This uses the Singleton ReferralManager!

---

## File Persistence

All changes are saved immediately:

| Action | Saved To |
|--------|----------|
| Add/Edit/Delete Patient | `data/patients.csv` |
| Add/Edit/Delete Appointment | `data/appointments.csv` |
| Add/Edit/Delete Prescription | `data/prescriptions.csv` + `output/new_prescriptions.csv` |
| Add/Edit/Delete Referral | `data/referrals.csv` |
| Generate Referral Email | `output/new_referrals.txt` |

---

## How Part 1 Design is Reflected

### Class Diagram Implementation
All classes from my Part 1 class diagram are implemented:
- Patient, Doctor hierarchy, Nurse, AdminStaff
- Appointment, Prescription, Referral, MedicalRecord
- Inheritance relationship: Doctor → GP and Specialist
- Composition: Patient contains MedicalRecord
- Aggregation: AdminStaff manages Appointments

### Three-Tier Architecture
- **Presentation Tier**: Swing GUI panels (view package)
- **Business Logic Tier**: Controllers (controller package)
- **Data Tier**: CSV files (data folder)

### Use Cases from Part 1
- Register Patient → Implemented with Add Patient
- Create Prescription → Implemented in Prescriptions tab
- Send Referral → Implemented with Generate Email using Singleton

---

## Design Decisions

### Why MVC Pattern?
I used MVC to separate concerns:
- **Model** classes don't know about GUI
- **View** classes just display and collect input
- **Controller** classes handle all business logic

This makes the code easier to maintain and test. If I need to change the GUI, I don't touch the Model. If business rules change, I only update Controllers.

### Why Singleton for ReferralManager?
Referrals need centralized management because:
- Only one queue should exist
- Email format must be consistent
- Audit trail needs to be in one place
- Prevents multiple instances causing conflicts

The Singleton ensures thread-safety with `synchronized getInstance()` method.

### CSV vs Database
Assignment required plain Java with no database, so I used CSV files with custom reader/writer utilities. All CRUD operations read from and write to CSV files immediately.

---

## Technologies Used

- **Language**: Java 8
- **GUI**: Java Swing (JFrame, JPanel, JTable, JButton, etc.)
- **Data Storage**: CSV files
- **Design Patterns**: MVC, Singleton
- **Data Structures**: ArrayList, LinkedList, Queue
- **Java API**: LocalDate, LocalTime, File I/O

---

## Known Limitations

- CSV files can get corrupted if edited manually while app is running
- No user authentication (not required for assignment)
- Referral emails are text files, not real emails
- No data validation on CSV import (assumes clean data)

---

## What I Learned

1. **MVC Pattern**: How to properly separate UI, logic, and data layers
2. **Singleton Pattern**: When and why to use it (centralized resource management)
3. **Java Swing**: Building GUIs with tables, dialogs, and event listeners
4. **File I/O**: Reading/writing CSV files with proper escaping
5. **OOP Principles**: Implementing inheritance, composition, and aggregation

The hardest part was making sure the Singleton pattern worked correctly with the queue and that all CRUD operations properly updated the CSV files.

---

## Assignment Deliverables

✅ All `.java` source files  
✅ Git log screenshot showing incremental development  
✅ Reflective report (400-500 words) - separate document  
✅ README documentation

---
