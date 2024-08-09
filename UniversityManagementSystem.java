import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

// Main class for University Management System (UMS)
class UMS {
    protected List<String> coursesOffered = new ArrayList<>();
    protected double feeStructure = 50000;

    // Constructor to initialize courses offered
    public UMS() {
        coursesOffered.add("Math");
        coursesOffered.add("Physics");
        coursesOffered.add("Computer Science");
    }

    // Method to handle user login and authentication
    public void login() {
        boolean continueSession = true;
        Scanner scanner = new Scanner(System.in);

        while (continueSession) {
            try {
                // Prompt user to select login type
                System.out.println("Select Login Type: \n1. Admin\n2. Faculty\n3. Student");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                // Get user credentials
                System.out.print("Enter User ID: ");
                String userID = scanner.nextLine();

                System.out.print("Enter Password: ");
                String password = scanner.nextLine();

                // Authenticate user based on choice and proceed
                switch (choice) {
                    case 1:
                        if (authenticateAdmin(userID, password)) {
                            Admin admin = new Admin(this);
                            admin.adminOperations();
                        } else {
                            throw new SecurityException("Invalid Admin credentials.");
                        }
                        break;
                    case 2:
                        if (authenticateFaculty(userID, password)) {
                            FacultyDetails faculty = new FacultyDetails(this);
                            faculty.facultyOperations();
                        } else {
                            throw new SecurityException("Invalid Faculty credentials.");
                        }
                        break;
                    case 3:
                        if (authenticateStudent(userID, password)) {
                            StudentDetails student = new StudentDetails(this);
                            student.studentOperations();
                        } else {
                            throw new SecurityException("Invalid Student credentials.");
                        }
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }

                // Ask user if they want to continue or login again
                System.out.println("Do you want to continue? (yes/no)");
                String continueChoice = scanner.nextLine();
                if (!continueChoice.equalsIgnoreCase("yes")) {
                    continueSession = false;
                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear scanner buffer
            } catch (SecurityException e) {
                System.out.println(e.getMessage());
                System.out.println("Execution stopped due to invalid credentials.");
                System.exit(0); // Stop execution immediately
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
    }

    // Authenticate Admin user
    private boolean authenticateAdmin(String userID, String password) {
        return userID.equals("admin") && password.equals("admin123");
    }

    // Authenticate Faculty user
    private boolean authenticateFaculty(String userID, String password) {
        return userID.equals("faculty") && password.equals("faculty123");
    }

    // Authenticate Student user
    private boolean authenticateStudent(String userID, String password) {
        return userID.equals("student") && password.equals("student123");
    }
}

// Class to handle student details and operations
class StudentDetails extends UMS {
    private String stuId;
    private String name;
    private String address;
    private String fathersName;
    private String mothersName;
    private String mobileNo;
    private String grade;
    private String department;
    private List<String> enrolledCourses = new ArrayList<>();

    // Constructor to initialize inherited attributes
    public StudentDetails(UMS ums) {
        this.coursesOffered = ums.coursesOffered;
        this.feeStructure = ums.feeStructure;
    }

    // Method to handle student-specific operations
    public void studentOperations() {
        boolean continueSession = true;
        Scanner scanner = new Scanner(System.in);

        while (continueSession) {
            try {
                System.out.println("Student Operations:");
                System.out.println("1. Give Exam");
                System.out.println("2. Take Courses");
                System.out.println("3. See Fee Structure");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                // Execute operation based on user choice
                switch (choice) {
                    case 1:
                        giveExam();
                        break;

                    case 2:
                        takeCourses();
                        break;

                    case 3:
                        seeFeeStructure();
                        break;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }

                // Ask user if they want to perform another operation
                System.out.println("Do you want to perform another operation? (yes/no)");
                String continueChoice = scanner.nextLine();
                if (!continueChoice.equalsIgnoreCase("yes")) {
                    continueSession = false;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Clear scanner buffer
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
    }

    // Method for student to give an exam
    public void giveExam() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the subject you want to give an exam in:");
        String subject = scanner.nextLine();

        if (isValidCourse(subject)) {
            System.out.println("Successfully registered for giving an exam in " + subject + ".");
        } else {
            System.out.println("Invalid subject. Please choose from the offered courses.");
        }
    }

    // Method for student to enroll in courses
    public void takeCourses() {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("Available courses:");
            for (int i = 0; i < coursesOffered.size(); i++) {
                System.out.println((i + 1) + ". " + coursesOffered.get(i));
            }

            System.out.print("Enter course number to enroll: ");
            int courseChoice = scanner.nextInt();

            if (courseChoice >= 1 && courseChoice <= coursesOffered.size()) {
                enrolledCourses.add(coursesOffered.get(courseChoice - 1));
                System.out.println("Enrolled in course: " + coursesOffered.get(courseChoice - 1));
            } else {
                System.out.println("Invalid course selection.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.nextLine(); // Clear scanner buffer
        }
    }

    // Method to display the fee structure
    public void seeFeeStructure() {
        System.out.println("The fee structure for each course is: " + feeStructure);
    }

    // Check if the course is valid
    private boolean isValidCourse(String subject) {
        return coursesOffered.contains(subject);
    }
}

// Class to handle faculty details and operations
class FacultyDetails extends UMS {
    private String facId;
    private String name;
    private String mobileNo;
    private String address;
    private String department;
    private String subjectTaught;

    // Constructor to initialize inherited attributes
    public FacultyDetails(UMS ums) {
        this.coursesOffered = ums.coursesOffered;
        this.feeStructure = ums.feeStructure;
    }

    // Method to handle faculty-specific operations
    public void facultyOperations() {
        boolean continueSession = true;
        Scanner scanner = new Scanner(System.in);

        while (continueSession) {
            try {
                System.out.println("Faculty Operations:");
                System.out.println("1. Take Exam");
                System.out.println("2. Assign Grade");
                System.out.println("3. See Fee Structure");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                // Execute operation based on user choice
                switch (choice) {
                    case 1:
                        takeExam();
                        break;

                    case 2:
                        addGrade();
                        break;

                    case 3:
                        seeFeeStructure();
                        break;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }

                // Ask user if they want to perform another operation
                System.out.println("Do you want to perform another operation? (yes/no)");
                String continueChoice = scanner.nextLine();
                if (!continueChoice.equalsIgnoreCase("yes")) {
                    continueSession = false;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Clear scanner buffer
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
    }

    // Method for faculty to prepare an exam
    public void takeExam() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the subject you want to prepare an exam for:");
        String subject = scanner.nextLine();
        System.out.print("Enter a date (dd/mm/yyyy) for exam: ");
        String date = scanner.next();  

        if (isValidCourse(subject)) {
            System.out.println("Exam will be taken in the subject " + subject + " on " + date);
        } else {
            System.out.println("Invalid subject. Please choose from the offered courses.");
        }
    }

    // Method to display the fee structure
    public void seeFeeStructure() {
        System.out.println("The fee structure for each course is: " + feeStructure);
    }

    // Method to assign grade to a student
    public void addGrade() {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Enter Student ID: ");
            String studentId = scanner.nextLine();
            System.out.print("Enter grade to assign: ");
            String grade = scanner.nextLine();

            System.out.println("Grade " + grade + " assigned to Student ID " + studentId);
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter valid details.");
            scanner.nextLine(); // Clear scanner buffer
        }
    }

    // Check if the course is valid
    private boolean isValidCourse(String subject) {
        return coursesOffered.contains(subject);
    }
}

// Class to handle admin details and operations
class Admin extends UMS {
    private List<String> users = new ArrayList<>();
    private String name;
    private String roles;
    private String mobileNo;
    private String address;

    // Constructor to initialize inherited attributes
    public Admin(UMS ums) {
        this.coursesOffered = ums.coursesOffered;
        this.feeStructure = ums.feeStructure;
    }

    // Method to handle admin-specific operations
    public void adminOperations() {
        boolean continueSession = true;
        Scanner scanner = new Scanner(System.in);

        while (continueSession) {
            try {
                System.out.println("Admin Operations:");
                System.out.println("1. Add Faculty");
                System.out.println("2. Add Student");
                System.out.println("3. Add Courses");
                System.out.println("4. Fee Collection");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                // Execute operation based on user choice
                switch (choice) {
                    case 1:
                        addFaculty();
                        break;

                    case 2:
                        addStudent();
                        break;

                    case 3:
                        addCourses();
                        break;

                    case 4:
                        collectFee();
                        break;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }

                // Ask user if they want to perform another operation
                System.out.println("Do you want to perform another operation? (yes/no)");
                String continueChoice = scanner.nextLine();
                if (!continueChoice.equalsIgnoreCase("yes")) {
                    continueSession = false;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Clear scanner buffer
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
    }

    // Method to add a new faculty member
    public void addFaculty() {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Enter Faculty ID: ");
            String facId = scanner.nextLine();
            System.out.print("Enter Faculty Name: ");
            String name = scanner.nextLine();
            System.out.print("Enter Faculty Mobile Number: ");
            String mobileNo = scanner.nextLine();

            users.add("Faculty: " + name + " (ID: " + facId + ")");
            System.out.println("Faculty added successfully!");
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter valid details.");
            scanner.nextLine(); // Clear scanner buffer
        }
    }

    // Method to add a new student
    public void addStudent() {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Enter Student ID: ");
            String stuId = scanner.nextLine();
            System.out.print("Enter Student Name: ");
            String name = scanner.nextLine();
            System.out.print("Enter Student Mobile Number: ");
            String mobileNo = scanner.nextLine();

            users.add("Student: " + name + " (ID: " + stuId + ")");
            System.out.println("Student added successfully!");
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter valid details.");
            scanner.nextLine(); // Clear scanner buffer
        }
    }

    // Method to add new courses
    public void addCourses() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the name of the new course: ");
        String newCourse = scanner.nextLine();
        coursesOffered.add(newCourse);

        System.out.println("Course " + newCourse + " added successfully!");
    }

    // Method for fee collection
    public void collectFee() {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Enter Student Roll Number: ");
            String rollNumber = scanner.nextLine();
            System.out.print("Enter Fee Amount Collected: ");
            double amount = scanner.nextDouble();
            scanner.nextLine(); // Consume newline

            System.out.println("Fee of " + amount + " collected from Roll Number " + rollNumber);
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter valid details.");
            scanner.nextLine(); // Clear scanner buffer
        }
    }
}

// Main execution class
public class UniversityManagementSystem {
    public static void main(String[] args) {
        UMS universityManagementSystem = new UMS();
        universityManagementSystem.login();
    }
}
