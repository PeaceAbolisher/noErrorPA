package pt.isec.pa.apoio_poe.ui.text;

import jdk.swing.interop.SwingInterOpUtils;
import pt.isec.pa.apoio_poe.model.data.*;
import pt.isec.pa.apoio_poe.model.fsm.PoeContext;
import pt.isec.pa.apoio_poe.model.fsm.PoeState;
import pt.isec.pa.apoio_poe.ui.utils.PAInput;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class PoeUi {
    PoeContext fsm;
    Scanner sc;

    public PoeUi(PoeContext fsm) {
        this.fsm = fsm;
        sc = new Scanner(System.in);
    }

    boolean finish = false;

    public void start() {
        System.out.println("start");
        while (!finish) {
            switch (fsm.getState()) {
                case CONFIG -> configUi();   //here
                case CONFIG_BLOCKED -> configBlockedUi();
                case ENROLLMENT -> enrollmentUi();
                case ENROLLMENT_BLOCKED -> enrollmentBlockedUi();
                case AT_PROPOSALS -> atProposalsUi();
                case AT_PROPOSALS_BLOCKED -> atProposalsBlockedUi();
                case AT_ORIENT -> at_OrientUi();
                case CONSULT -> consultUi();
            }
        }
    }

    //1st Phase
    private void configUi() {
        switch (PAInput.chooseOption("***Config Phase***", "Manage Students", "Manage Professors", "Manage Poe",
                "Skip Phase", "End Phase", "Exit")) {
            case 1 -> manageStudents();
            case 2 -> manageProfessors();
            case 3 -> managePoe();
            case 4 -> fsm.next_phase();
            case 5 -> fsm.close_phase();
            case 6 -> finish = true;
        }
    }

    public void manageStudents() {
        String fileName, fullFileName;
        int op = 0;
        boolean outcome;
        do {
            System.out.println("---Students---");
            System.out.println("What do you wish to do?");
            System.out.println(" 1-Add;\n 2-Consult;\n 3-Edit;\n 4-Delete;\n 5-List;\n 6-Exit.");
            System.out.print("> ");
            try {
                op = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("It must be an integer!");
                sc.next();
            }
            switch (op) {
                case 1 -> {
                    op = 0;
                    System.out.print("""
                            Do you want to read from a file or add a student manually?
                            >\s""");
                    do {
                        try {
                            System.out.println("(1-Add by file; 2-Add manually)");
                            op = sc.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("It must be an integer!");
                            sc.next();
                        }
                    } while (op > 2 || op < 1);
                    switch (op) {
                        case 1 -> {
                            fileName = PAInput.readString("File name", true);
                            fullFileName = "..\\noError\\TP\\ficheiros\\" + fileName + ".txt";
                            try (FileReader fr = new FileReader(fullFileName);
                                 BufferedReader br = new BufferedReader(fr)) {
                                String line;
                                while ((line = br.readLine()) != null) {
                                    String[] tokens = line.split(",");
                                    if (Character.isLetter(tokens[0].charAt(0))) {
                                        System.out.println("Please introduce a file with students only");
                                    } else {
                                        outcome = verifyAddStudentsByHand(Long.parseLong(tokens[0]), tokens[1], tokens[2], tokens[3].toUpperCase(), tokens[4].toUpperCase(), Double.parseDouble(tokens[5]), Boolean.parseBoolean(tokens[6]), false);
                                        if (!outcome) {
                                            System.out.println("Student couldn't be added");
                                        } else {
                                            outcome = fsm.addStudentByHand(Long.parseLong(tokens[0]), tokens[1], tokens[2], tokens[3].toUpperCase(), tokens[4].toUpperCase(), Double.parseDouble(tokens[5]), Boolean.parseBoolean(tokens[6]), false);
                                            if (!outcome) {
                                                System.out.println("Error adding students by file");
                                            } else {
                                                System.out.println("Adding students by file succeeded");
                                            }
                                        }
                                    }
                                }
                            } catch (FileNotFoundException e) {
                                System.out.println("File not found");
                            } catch (IOException e) {
                                System.out.println("Error opening the file");
                            }
                        }
                        case 2 -> {
                            System.out.println("***Student's Info***");
                            long number;
                            do {
                                number = 0;
                                try {
                                    System.out.print("Number: ");
                                    number = sc.nextLong();
                                } catch (InputMismatchException e) {
                                    System.out.println("It must be a number");
                                    sc.next();
                                }
                            } while (number == 0);
                            sc.nextLine();
                            System.out.print("Name: ");
                            String name = sc.nextLine();
                            System.out.print("Email: ");
                            String email = sc.nextLine();
                            System.out.print("Course: ");
                            String course = sc.nextLine();
                            System.out.print("Area: ");
                            String area = sc.nextLine();
                            System.out.print("Grade (between 0,0 and 1,0): ");
                            double grade;
                            do {
                                grade = -1;
                                try {
                                    grade = sc.nextDouble();
                                } catch (InputMismatchException e) {
                                    System.out.println("It must be a number separated by ','");
                                    sc.next();
                                }
                            } while (grade == -1);
                            sc.nextLine();
                            System.out.print("Internship (true or false): ");
                            boolean able = false, next;
                            do {
                                next = true;
                                try {
                                    able = sc.nextBoolean();
                                    next = false;
                                } catch (InputMismatchException e) {
                                    System.out.println("You must write 'true' or 'false'");
                                    sc.next();
                                }
                            } while (next);
                            sc.nextLine();
                            boolean print = true;
                            outcome = verifyAddStudentsByHand(number, name, email, course.toUpperCase(), area.toUpperCase(), grade, able, print);
                            if (!outcome) {
                                System.out.println("Student couldn't be added");
                            } else {
                                outcome = fsm.addStudentByHand(number, name, email, course.toUpperCase(), area.toUpperCase(), grade, able, print);
                                if (!outcome) {
                                    System.out.println("Student couldn't be added");
                                } else {
                                    System.out.println("Student was added");
                                }
                            }
                        }
                    }
                }
                case 2 -> {
                    System.out.println("Insert the number whose student you are looking for:");
                    System.out.print("> ");
                    long std_number;
                    do {
                        std_number = 0;
                        try {
                            System.out.print("Number: ");
                            std_number = sc.nextLong();
                        } catch (InputMismatchException e) {
                            System.out.println("It must be a number");
                            sc.next();
                        }
                    } while (std_number == 0);
                    outcome = fsm.existsStudent(std_number);
                    if (!outcome) {
                        System.out.println("Student not found!");
                        return;
                    } else {
                        Student st = consultStudent(std_number);
                        if (st == null)
                            System.out.println("Student not found");
                        else
                            System.out.println(st);
                    }
                }
                case 3 -> {
                    System.out.println("Insert the student number for which you want to edit the data:");
                    System.out.print("> ");
                    long std_number;
                    do {
                        std_number = 0;
                        try {
                            System.out.print("Number: ");
                            std_number = sc.nextLong();
                        } catch (InputMismatchException e) {
                            System.out.println("It must be a number");
                            sc.next();
                        }
                    } while (std_number == 0);
                    sc.nextLine();
                    outcome = fsm.existsStudent(std_number);
                    if (!outcome) {
                        System.out.println("There is no student with said number");
                    } else {
                        do {
                            System.out.println("What do you wish to edit?");
                            System.out.println(" 1-Name;\n 2-Area;\n 3-Course;\n 4-Cancel");
                            try {
                                op = sc.nextInt();
                            } catch (InputMismatchException e) {
                                System.out.println("It must be an integer!");
                                sc.next();
                            }
                            switch (op) {
                                case 1 -> {
                                    String name;
                                    sc.nextLine();
                                    System.out.println("Insert the wanted name: ");
                                    name = sc.nextLine();
                                    while (name.isBlank()) {
                                        System.out.println("Student: " + std_number);
                                        System.out.println("Name is blank.");
                                        do {
                                            System.out.println("Do you want to reintroduce the student number?");
                                            try {
                                                System.out.println("(1 - Reintroduce the student number; 2 - Cancel)");
                                                op = sc.nextInt();
                                            } catch (InputMismatchException e) {
                                                System.out.println("It must be an integer!");
                                                sc.next();
                                            }
                                        } while (op > 2 || op < 1);
                                        if (op == 1) {
                                            System.out.println("Name: ");
                                            System.out.print("> ");
                                            name = sc.next();
                                        } else {
                                            return;
                                        }
                                    }
                                    outcome = fsm.editStudentName(std_number, name);
                                    if (!outcome) {
                                        System.out.println("Student's name was not edited with success");
                                    } else {
                                        System.out.println("Student's name was edited with success");
                                    }
                                }
                                case 2 -> {
                                    sc.nextLine();
                                    String area;
                                    op = 0;
                                    System.out.println("Insert the wanted area: ");
                                    area = sc.nextLine();
                                    while (fsm.verifyArea(area)) {
                                        System.out.println("Student: " + std_number);
                                        System.out.println("Invalid area.");
                                        do {
                                            System.out.println("Do you want to reintroduce area?");
                                            try {
                                                System.out.println("(1 - Reintroduce area; 2 - Cancel)");
                                                op = sc.nextInt();
                                            } catch (InputMismatchException e) {
                                                System.out.println("It must be an integer!");
                                                sc.next();
                                            }
                                        } while (op > 2 || op < 1);
                                        if (op == 1) {
                                            System.out.println("Area (DA, SI or RAS): ");
                                            System.out.print("> ");
                                            area = sc.next();
                                        } else {
                                            return;
                                        }
                                    }
                                    outcome = fsm.editStudentArea(std_number, area);
                                    if (!outcome) {
                                        System.out.println("Student's area was not edited with success");
                                    } else {
                                        System.out.println("Student's area was edited with success");
                                    }
                                }
                                case 3 -> {
                                    sc.nextLine();
                                    String course;
                                    op = 0;
                                    System.out.println("Insert the wanted course: ");
                                    course = sc.nextLine().toUpperCase();
                                    while (fsm.verifyCourse(course)) {
                                        System.out.println("Student: " + std_number);
                                        System.out.println("Invalid course.");
                                        do {
                                            System.out.println("Do you want to reintroduce course?");
                                            try {
                                                System.out.println("(1 - Reintroduce course; 2 - Cancel)");
                                                op = sc.nextInt();
                                            } catch (InputMismatchException e) {
                                                System.out.println("It must be an integer!");
                                                sc.next();
                                            }
                                        } while (op > 2 || op < 1);
                                        if (op == 1) {
                                            System.out.println("Course (LEI or LEI-PL): ");
                                            System.out.print("> ");
                                            course = sc.next();
                                        } else {
                                            return;
                                        }
                                    }
                                    outcome = fsm.editStudentCourse(std_number, course);
                                    if (!outcome) {
                                        System.out.println("Student's course was not edited with success");
                                    } else {
                                        System.out.println("Student's course was edited with success");
                                    }
                                }
                                case 4 -> {
                                    return;
                                }
                                default -> System.out.println("Error");
                            }
                        } while (op != 1 && op != 2 && op != 3);
                    }
                }
                case 4 -> {
                    System.out.println("Insert the number of the student you want to delete the data from:");
                    System.out.print("> ");
                    long std_number;
                    do {
                        std_number = 0;
                        try {
                            System.out.print("Number: ");
                            std_number = sc.nextLong();
                        } catch (InputMismatchException e) {
                            System.out.println("It must be a number");
                            sc.next();
                        }
                    } while (std_number == 0);
                    sc.nextLine();
                    outcome = fsm.existsStudent(std_number);
                    if (!outcome) {
                        System.out.println("Removing student with said number failed");
                    } else {
                        outcome = fsm.deleteDataStudent(std_number);
                        if (!outcome) {
                            System.out.println("Removing student with said number failed");
                        } else {
                            System.out.println("Removing student with said number succeeded");
                        }
                    }
                }
                case 5 -> {
                    System.out.println("***Students***");
                    if (fsm.listStudents(true).isEmpty())
                        System.out.println("No students added yet!");
                    else
                        for (Student s : fsm.listStudents(true))
                            System.out.println(s.toString());
                }
                case 6 -> {
                    return;
                }
                default -> System.out.println("Error");
            }
        } while (op != 6);
    }

    public boolean verifyAddStudentsByHand(long std_number, String name, String email, String course, String area, double grade, boolean able, boolean print) {
        int op = 0;

        //student number
        while (fsm.verifyStdNumber(std_number)) {
            System.out.println("Student: " + name);
            System.out.println("Student number invalid");
            do {
                System.out.println("Do you want to reintroduce the student number?");
                try {
                    System.out.println("(1 - Reintroduce the student number; 2 - Cancel)");
                    op = sc.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("It must be an integer!");
                    sc.next();
                }
            } while (op > 2 || op < 1);
            if (op == 1) {
                do {
                    std_number = 0;
                    try {
                        System.out.print("Number: ");
                        std_number = sc.nextLong();
                    } catch (InputMismatchException e) {
                        System.out.println("It must be a number");
                        sc.next();
                    }
                } while (std_number == 0);
                sc.nextLine();
            } else {
                return false;
            }

        }
        while (fsm.existsStudent(std_number)) {
            System.out.println("Student: " + name);
            System.out.println("That student is already added");
            do {
                System.out.println("Do you want to reintroduce the student number?");
                try {
                    System.out.println("(1 - Reintroduce the student number; 2 - Cancel)");
                    op = sc.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("It must be an integer!");
                    sc.next();
                }
            } while (op > 2 || op < 1);
            if (op == 1) {
                do {
                    std_number = 0;
                    try {
                        System.out.print("Number: ");
                        std_number = sc.nextLong();
                    } catch (InputMismatchException e) {
                        System.out.println("It must be a number");
                        sc.next();
                    }
                } while (std_number == 0);
                sc.nextLine();
            } else {
                return false;
            }
        }

        //email
        while (fsm.verifyEmail(email)) {
            System.out.println("Student: " + std_number);
            System.out.println("The email is invalid.");
            do {
                System.out.println("Do you want to reintroduce the email?\n(1 - Reintroduce email; 2 - Cancel)");
                op = sc.nextInt();
            } while (op > 2 || op < 1);
            if (op == 1) {
                System.out.println("Email: ");
                System.out.print("> ");
                email = sc.next();
            } else {
                return false;
            }
        }

        //name
        while (fsm.verifyName(name)) {
            System.out.println("Student: " + std_number);
            System.out.println("Name is blank.");
            do {
                System.out.println("Do you want to reintroduce name?\n(1 - Reintroduce name; 2 - Cancel)");
                op = sc.nextInt();
            } while (op > 2 || op < 1);
            if (op == 1) {
                System.out.println("Name: ");
                System.out.print("> ");
                name = sc.next();
            } else {
                return false;
            }
        }

        //course
        while (fsm.verifyCourse(course)) {
            System.out.println("Student: " + std_number);
            System.out.println("Invalid course.");
            do {
                System.out.println("Do you want to reintroduce course?\n(1 - Reintroduce course; 2 - Cancel)");
                op = sc.nextInt();
            } while (op > 2 || op < 1);
            if (op == 1) {
                System.out.println("Course (LEI or LEI-PL): ");
                System.out.print("> ");
                course = sc.next();
            } else {
                return false;
            }
        }

        //area
        while (fsm.verifyArea(area)) {
            System.out.println("Invalid area.");
            do {
                System.out.println("Do you want to reintroduce area?\n(1 - Reintroduce area; 2 - Cancel)");
                op = sc.nextInt();
            } while (op > 2 || op < 1);
            if (op == 1) {
                System.out.println("Area (DA, SI or RAS): ");
                System.out.print("> ");
                area = sc.next();
            } else {
                return false;
            }
        }

        //grade
        while (grade > 1.0 || grade < 0.0) {
            System.out.println(std_number + "'s grade is invalid");
            do {
                System.out.println("Do you want to reintroduce a different grade?\n(1 - Reintroduce grade; 2 - Cancel)");
                op = sc.nextInt();
            } while (op > 2 || op < 1);
            if (op == 1) {
                System.out.println("Please introduce a valid grade (from 0.0 to 1.0): ");
                System.out.print("> ");
                do {
                    grade = -1;
                    try {
                        grade = sc.nextDouble();
                    } catch (InputMismatchException e) {
                        System.out.println("It must be a number separated by ','");
                        sc.next();
                    }
                } while (grade == -1);
            } else {
                return false;
            }
        }
        return true;
    }

    public void manageProfessors() {
        String fileName, fullFileName;
        boolean outcome;
        int op = 0;
        do {
            System.out.println("---Professors---");
            System.out.println("What do you wish to do?");
            System.out.println(" 1-Add;\n 2-Consult;\n 3-Edit;\n 4-Delete;\n 5-List;\n 6-Exit.");
            System.out.print("> ");
            try {
                op = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("It must be an integer!");
                sc.next();
            }
            switch (op) {
                case 1 -> {
                    op = 0;
                    System.out.print("Do you want to read from a file or add a professor manually?");
                    do {
                        try {
                            System.out.println("(1-Add by file; 2-Add manually;)");
                            System.out.print("> ");
                            op = sc.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("It must be an integer!");
                            sc.next();
                        }
                    } while (op > 2 || op < 1);
                    switch (op) {
                        case 1 -> {
                            fileName = PAInput.readString("File name", true);
                            fullFileName = "..\\noError\\TP\\ficheiros\\" + fileName + ".txt";
                            try (FileReader fr = new FileReader(fullFileName);
                                 BufferedReader br = new BufferedReader(fr)) {
                                String line;
                                while ((line = br.readLine()) != null) {
                                    String[] tokens = line.split(",");
                                    if (!Character.isLetter(tokens[0].charAt(0))) {
                                        System.out.println("Please introduce a file with professors only");
                                    } else {
                                        outcome = verifyAddProfessorByHand(tokens[0], tokens[1]);
                                        if (!outcome) {
                                            System.out.println("Professors couldn't be added by file");
                                        } else {
                                            outcome = fsm.addProfessorByHand(tokens[0], tokens[1]);
                                            if (!outcome) {
                                                System.out.println("Error adding professors by file");
                                            } else {
                                                System.out.println("Professors were added by file");
                                            }
                                        }
                                    }
                                }
                            } catch (IOException e) {
                                System.out.println("Error opening the file");
                            }
                        }
                        case 2 -> {
                            sc.nextLine();
                            System.out.println("***Professor's Info***");
                            System.out.print("Name: ");
                            String name = sc.nextLine();
                            System.out.print("Email: ");
                            String email = sc.nextLine();
                            outcome = verifyAddProfessorByHand(name, email);
                            if (!outcome) {
                                System.out.println("Error adding professors");
                            } else {
                                outcome = fsm.addProfessorByHand(name, email);
                                if (!outcome) {
                                    System.out.println("Professor couldn't be added");
                                } else {
                                    System.out.println("Professor was added");
                                }
                            }
                        }
                    }
                }
                case 2 -> {
                    System.out.println("Insert the email whose professor you are looking for:");
                    System.out.print("> ");
                    String email = null;
                    do {
                        try {
                            System.out.print("Email: ");
                            email = sc.next();
                        } catch (InputMismatchException e) {
                            System.out.println("It must be a number");
                            sc.next();
                        }
                    } while (email == null);
                    outcome = fsm.existsProfessor(email);
                    if (!outcome) {
                        System.out.println("Professor not found!");
                    } else {
                        Professor prof = consultProfessor(email);
                        if (prof == null)
                            System.out.println("Professor not found");
                        else
                            System.out.println(prof);
                    }
                }
                case 3 -> {
                    System.out.println("Insert the Professor's email for which you want to edit the data:");
                    System.out.print("> ");
                    String email = sc.next();
                    outcome = fsm.existsProfessor(email);
                    if (!outcome) {
                        System.out.println("Student with said number doesn't exist");
                    } else {
                        do {
                            try {
                                System.out.println("What do you wish to edit?");
                                System.out.println(" 1-Name;\n 2-Cancel.");
                                op = sc.nextInt();
                            } catch (InputMismatchException e) {
                                System.out.println("It must be an integer!");
                                sc.next();
                            }
                        } while (op > 2 || op < 1);
                        if (op == 1) {
                            String name;
                            op = 0;
                            sc.nextLine();
                            System.out.println("Insert the wanted name: ");
                            name = sc.next();
                            while (fsm.verifyName(name)) {
                                System.out.println("Professor: " + email);
                                System.out.println("Name is blank.");
                                do {
                                    System.out.println("Do you want to reintroduce name?");
                                    try {
                                        System.out.println("(1 - Reintroduce name; 2 - Cancel)");
                                        System.out.print("> ");
                                        op = sc.nextInt();
                                    } catch (InputMismatchException e) {
                                        System.out.println("It must be an integer!");
                                        sc.next();
                                    }
                                } while (op > 2 || op < 1);
                                if (op == 1) {
                                    System.out.println("Name: ");
                                    System.out.print("> ");
                                    name = sc.next();
                                } else {
                                    return;
                                }
                            }
                            outcome = fsm.editProfessorName(email, name);
                            if (!outcome) {
                                System.out.println("Professor's data was unsuccessfully edited");
                            } else {
                                System.out.println("Professor's data was successfully edited");
                            }
                        }
                    }
                }
                case 4 -> {
                    sc.nextLine();
                    System.out.println("Insert the email of the professor you want to delete the data from:");
                    System.out.print("> ");
                    String email = sc.next();
                    outcome = fsm.existsProfessor(email);
                    if (!outcome) {
                        System.out.println("Professor with said email doesn't exist");
                    } else {
                        outcome = fsm.deleteDataProfessors(email);
                        if (!outcome) {
                            System.out.println("Professor wasn't deleted successfully");
                        } else {
                            System.out.println("Professor was deleted successfully");
                        }
                    }

                }
                case 5 -> {
                    System.out.println("***Professors***");
                    if (fsm.listProfessors(true).isEmpty())
                        System.out.println("No professors added yet!");
                    else
                        for (Professor p : fsm.listProfessors(true))
                            System.out.println(p.toString());
                }
                case 6 -> {
                    return;
                }
                default -> {
                    System.out.println("Error");
                    return;
                }
            }
        } while (op != 6);
    }

    public boolean verifyAddProfessorByHand(String name, String email) {
        int op = 0;
        boolean outcome;

        //email
        outcome = fsm.existsProfessor(email);
        while (outcome) {
            System.out.println("Professor: " + name);
            System.out.println("That professor is already added");
            do {
                System.out.println("Do you want to reintroduce the professor's email?");
                try {
                    System.out.println("(1 - Reintroduce email; 2 - Cancel)");
                    op = sc.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("You must insert an integer!");
                    sc.next();
                }
            } while (op > 2 || op < 1);
            sc.nextLine();
            if (op == 1) {
                System.out.println("Email: ");
                System.out.print("> ");
                email = sc.nextLine();
                outcome = fsm.existsProfessor(email);
            } else {
                return false;
            }
            //name
            while (fsm.verifyName(name)) {
                System.out.println("Professor: " + email);
                System.out.println("Name is blank.");
                do {
                    System.out.println("Do you want to reintroduce name?");
                    try {
                        System.out.println("(1 - Reintroduce name; 2 - Cancel)");
                        op = sc.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("You must insert an integer!");
                        sc.next();
                    }
                } while (op > 2 || op < 1);
                sc.nextLine();
                if (op == 1) {
                    System.out.println("Name: ");
                    System.out.print("> ");
                    name = sc.nextLine();
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    public void managePoe() {
        String fileName, fullFileName;
        int op = 0;
        String type;
        boolean outcome;
        do {
            System.out.println("---POE---");
            System.out.println("What do you wish to do?");
            try {
                System.out.println(" 1-Add;\n 2-Consult;\n 3-Edit;\n 4-Delete;\n 5-List;\n 6-Exit.");
                System.out.print("> ");
                op = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("It must be an integer!");
                sc.next();
            }
            switch (op) {
                case 1 -> {
                    op = 0;
                    System.out.print("Do you want to read from a file or add a poe manually?");
                    do {
                        try {
                            System.out.println("(1-Add by file; 2-Add manually;)");
                            System.out.print("> ");
                            op = sc.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("It must be an integer!");
                            sc.next();
                        }
                    } while (op > 2 || op < 1);
                    switch (op) {
                        case 1 -> { //read by file
                            fileName = PAInput.readString("File name", true);
                            fullFileName = "..\\noError\\TP\\ficheiros\\" + fileName + ".txt";
                            try (FileReader fr = new FileReader(fullFileName);
                                 BufferedReader br = new BufferedReader(fr)) {
                                String line;
                                while ((line = br.readLine()) != null) {
                                    String[] tokens = line.split(",");
                                    if (tokens[0].equalsIgnoreCase("T1")) { //internship
                                        if (tokens.length == 6) {
                                            outcome = verifyAddInternshipByHand(tokens[1].toUpperCase(), tokens[2].toUpperCase(), tokens[3], tokens[4],
                                                    Long.parseLong(tokens[5]));
                                            if (!outcome) {
                                                System.out.println("Error Adding Internship by file");
                                            } else {
                                                outcome = fsm.addInternshipByHand(tokens[1].toUpperCase(), tokens[2].toUpperCase(), tokens[3], tokens[4],
                                                        Long.parseLong(tokens[5]));
                                                if (!outcome) {
                                                    System.out.println("Internships weren't added by file");
                                                } else {
                                                    System.out.println("Internships were added by file");
                                                }
                                            }

                                        } else {
                                            outcome = verifyAddInternshipByHand(tokens[1].toUpperCase(), tokens[2].toUpperCase(), tokens[3], tokens[4],
                                                    -1);
                                            if (!outcome) {
                                                System.out.println("Error Adding Internship by file");
                                            } else {
                                                fsm.addInternshipByHand(tokens[1].toUpperCase(), tokens[2].toUpperCase(), tokens[3], tokens[4],
                                                        -1);
                                            }
                                        }
                                    } else if (tokens[0].equalsIgnoreCase("T2")) { //project
                                        if (tokens.length == 6) {
                                            outcome = verifyAddProjectByHand(tokens[1].toUpperCase(), tokens[2].toUpperCase(), tokens[3], tokens[4],
                                                    Long.parseLong(tokens[5]));
                                            if (!outcome) {
                                                System.out.println("Error Adding Projects by file");
                                            } else {
                                                outcome = fsm.addProjectByHand(tokens[1].toUpperCase(), tokens[2].toUpperCase(), tokens[3], tokens[4],
                                                        Long.parseLong(tokens[5]));
                                                if (!outcome) {
                                                    System.out.println("Projects weren't added by file");
                                                } else {
                                                    System.out.println("Projects were added by file");
                                                }
                                            }
                                        } else {
                                            outcome = verifyAddProjectByHand(tokens[1].toUpperCase(), tokens[2].toUpperCase(), tokens[3], tokens[4],
                                                    -1);
                                            if (!outcome) {
                                                System.out.println("Error Adding Internship by file");
                                            } else {
                                                fsm.addProjectByHand(tokens[1].toUpperCase(), tokens[2].toUpperCase(), tokens[3], tokens[4],
                                                        -1);
                                            }
                                        }
                                    } else if (tokens[0].equalsIgnoreCase("T3")) { //self-proposed
                                        outcome = verifyAddSelfProposedByHand(tokens[1].toUpperCase(), tokens[2], Long.parseLong(tokens[3]));
                                        if (!outcome) {
                                            System.out.println("Error adding SelfProposed by file");
                                        } else {
                                            outcome = fsm.addSelfProposedByHand(tokens[1].toUpperCase(), tokens[2], Long.parseLong(tokens[3]));
                                            if (!outcome) {
                                                System.out.println("Error adding SelfProposed by File ");
                                            } else {
                                                System.out.println("Added SelfProposed by File ");
                                            }
                                        }
                                    }
                                }
                            } catch (FileNotFoundException e) {
                                System.out.println("File not found");
                            } catch (IOException e) {
                                System.out.println("Error opening the file");
                            }
                        }
                        case 2 -> {
                            sc.nextLine();
                            do {
                                System.out.println("What type of POE do you wish to add?\n(T1-Internship; T2-Project; T3-Self-proposed)");
                                System.out.print("> ");
                                type = sc.nextLine().toUpperCase();
                                switch (type) {
                                    case "T1" -> {
                                        op = 0;
                                        long std_number = 0;
                                        System.out.println("***Internship Info***");
                                        System.out.println("Code: ");
                                        String code = sc.next();
                                        System.out.println("Area: ");
                                        String area = sc.next();
                                        System.out.println("Title: ");
                                        String title = sc.next();
                                        System.out.println("Housing entity: ");
                                        String housing_entity = sc.next();
                                        do {
                                            try {
                                                System.out.println("Do you want to associate a student?\n (1-yes; 2-no)");
                                                op = sc.nextInt();
                                            } catch (InputMismatchException e) {
                                                System.out.println("It must be an integer!");
                                                sc.next();
                                            }
                                        } while (op > 2 || op < 1);
                                        if (op == 2)
                                            std_number = -1;
                                        else {
                                            try {
                                                System.out.println("Insert student's number: ");
                                                std_number = sc.nextLong();
                                            } catch (InputMismatchException e) {
                                                System.out.println("You must insert a number!");
                                                sc.next();
                                            }
                                        }
                                        if (std_number != -1) {
                                            outcome = fsm.existsStudent(std_number);
                                            while (!outcome) {
                                                System.out.println("Student not found");
                                                do {
                                                    try {
                                                        System.out.println("Do you want to insert another student number?\n(1-yes; 2-no)");
                                                        op = sc.nextInt();
                                                    } catch (InputMismatchException e) {
                                                        System.out.println("It must be an integer!");
                                                        sc.next();
                                                    }
                                                } while (op > 2 || op < 1);
                                                if (op == 1) {
                                                    try {
                                                        System.out.println("Insert student's number: ");
                                                        std_number = sc.nextLong();
                                                        outcome = fsm.existsStudent(std_number);
                                                    } catch (InputMismatchException e) {
                                                        System.out.println("You must insert a number!");
                                                        sc.nextLine();
                                                    }
                                                } else {
                                                    return;
                                                }
                                            }
                                        }
                                        System.out.println(" T1 ON THE BLOCK" + std_number);
                                        outcome = fsm.addInternshipByHand(code.toUpperCase(), area.toUpperCase(), title, housing_entity, std_number);
                                        if (!outcome) {
                                            System.out.println("Internships weren't added successfully");
                                        } else {
                                            System.out.println("Internships were added successfully");
                                        }
                                    }
                                    case "T2" -> {
                                        op = 0;
                                        long std_number = 0;
                                        String email;
                                        System.out.println("***Project Info***");
                                        System.out.println("Code: ");
                                        String code = sc.nextLine();
                                        System.out.println("Area: ");
                                        String area = sc.nextLine();
                                        System.out.println("Title: ");
                                        String title = sc.nextLine();
                                        do {
                                            System.out.println("Professor's email: ");
                                            email = sc.nextLine();
                                            outcome = fsm.existsProfessor(email);
                                        } while (!outcome);
                                        do {
                                            try {
                                                System.out.println("Do you want to associate a student?\n (1-yes; 2-no)");
                                                op = sc.nextInt();
                                            } catch (InputMismatchException e) {
                                                System.out.println("It must be an integer!");
                                                sc.next();
                                            }
                                        } while (op > 2 || op < 1);
                                        if (op == 2)
                                            std_number = -1;
                                        else {
                                            try {
                                                System.out.println("Insert student's number: ");
                                                std_number = sc.nextLong();
                                            } catch (InputMismatchException e) {
                                                System.out.println("You must insert a number!");
                                                sc.nextLine();
                                            }
                                        }
                                        if (std_number != -1) {
                                            outcome = fsm.existsStudent(std_number);
                                            while (!outcome) {
                                                System.out.println("Student not found");
                                                do {
                                                    try {
                                                        System.out.println("Do you want to insert another student number?\n(1-yes; 2-no)");
                                                        op = sc.nextInt();
                                                    } catch (InputMismatchException e) {
                                                        System.out.println("It must be an integer!");
                                                        sc.next();
                                                    }
                                                } while (op > 2 || op < 1);
                                                if (op == 1) {
                                                    try {
                                                        System.out.println("Insert student's number: ");
                                                        std_number = sc.nextLong();
                                                        outcome = fsm.existsStudent(std_number);
                                                    } catch (InputMismatchException e) {
                                                        System.out.println("You must insert a number!");
                                                        sc.nextLine();
                                                    }
                                                } else {
                                                    return;
                                                }
                                            }
                                        }
                                        outcome = fsm.addProjectByHand(code.toUpperCase(), area.toUpperCase(), title, email, std_number);
                                        if (!outcome) {
                                            System.out.println("Project's weren't added successfully");
                                        } else {
                                            System.out.println("Project's were added successfully");
                                        }
                                    }
                                    case "T3" -> {
                                        outcome = false;
                                        long std_number = 0;
                                        System.out.println("***Self-proposed Info***");
                                        System.out.println("Code: ");
                                        String code = sc.nextLine();
                                        System.out.println("Title: ");
                                        String title = sc.nextLine();
                                        do {
                                            try {
                                                System.out.println("Insert student's number: ");
                                                std_number = sc.nextLong();
                                                outcome = fsm.existsStudent(std_number);
                                            } catch (InputMismatchException e) {
                                                System.out.println("You must insert a number!");
                                                sc.nextLine();
                                            }
                                        } while (!outcome);
                                        System.out.println(" T3 ON THE BLOCK" + std_number);
                                        outcome = fsm.addSelfProposedByHand(code.toUpperCase(), title, std_number);
                                        if (!outcome) {
                                            System.out.println("SelfProposedProjects weren't added successfully");
                                        } else {
                                            System.out.println("SelfProposedProjects were added successfully");
                                        }
                                    }
                                    default -> System.out.println("Error");
                                }
                            } while (!type.equalsIgnoreCase("T1") && !type.equalsIgnoreCase("T2") && !type.equalsIgnoreCase("T3"));
                        }
                    }
                }
                case 2 -> {
                    op = 0;
                    do {
                        System.out.println("What do you wish to consult?");
                        System.out.println(" 1-Internship;\n 2-Project;\n 3-Self-proposed;\n 4-Cancel.");
                        try {
                            System.out.print("> ");
                            op = sc.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("It must be an integer!");
                            sc.next();
                        }
                        switch (op) {
                            case 1 -> {
                                sc.nextLine();
                                System.out.println("Insert code from the internship you are looking for: ");
                                System.out.print("> ");
                                String code = sc.next().toUpperCase();
                                Internship inter = consultInternship(code);
                                System.out.println(inter + "wtf");
                                if (inter == null)
                                    System.out.println("Internship not found");
                                else
                                    System.out.println(inter);
                            }
                            case 2 -> {
                                sc.nextLine();
                                System.out.println("Insert code from the project you are looking for: ");
                                System.out.print("> ");
                                String code = sc.next().toUpperCase();

                                Project prog = consultProject(code);
                                if (prog == null)
                                    System.out.println("Project not found");
                                else
                                    System.out.println(prog);
                            }
                            case 3 -> {
                                sc.nextLine();
                                System.out.println("Insert code from the self-proposed you are looking for: ");
                                System.out.print("> ");
                                String code = sc.next().toUpperCase();
                                SelfProposed self = consultSelfProposed(code);
                                if (self == null)
                                    System.out.println("SelfProposed not found");
                                else
                                    System.out.println(self);
                            }
                            case 4 -> {
                                return;
                            }
                            default -> System.out.println("Error");
                        }
                    } while (op != 1 && op != 2 && op != 3);
                }
                case 3 -> {
                    do {
                        System.out.println("What type of POE do you wish to edit?\n(T1-Internship; T2-Project)");
                        System.out.print("> ");
                        type = sc.next().toUpperCase();
                        switch (type) {
                            case "T1" -> {
                                op = 0;
                                sc.nextLine();
                                System.out.println("Insert the Internship's code for which you want to edit the data:");
                                System.out.print("> ");
                                String code = sc.nextLine();
                                outcome = fsm.existsInternship(code);
                                if (!outcome)
                                    return;
                                do {
                                    try {
                                        System.out.println("What do you wish to edit?");
                                        System.out.println(" 1-Area;\n 2-Housing entity;\n 3-Student number;\n 4-Cancel.");
                                        op = sc.nextInt();
                                    } catch (InputMismatchException e) {
                                        System.out.println("It must be an integer!");
                                        sc.next();
                                    }
                                } while (op > 4 || op < 1);
                                switch (op) {
                                    case 1 -> {
                                        String area;
                                        op = 0;
                                        sc.nextLine();
                                        System.out.println("Insert the wanted area(s) (separated by '|'): ");
                                        area = sc.nextLine();

                                        String[] tokens = area.split("\\|");
                                        while (tokens[0] == null || area.isBlank()) {
                                            System.out.println("You must insert an area (DA, SI or RAS)");
                                            tokens[0] = sc.nextLine();
                                            area = tokens[0];
                                            tokens = area.split("\\|");
                                        }
                                        for (int j = 0; j < tokens.length; j++) {
                                            while (fsm.verifyArea(tokens[j])) {
                                                System.out.println("Invalid area.");
                                                do {
                                                    System.out.println("Do you want to reintroduce area?");
                                                    try {
                                                        System.out.println("(1 - Reintroduce area; 2 - Cancel)");
                                                        System.out.println("> ");
                                                        op = sc.nextInt();
                                                    } catch (InputMismatchException e) {
                                                        System.out.println("It must be an integer!");
                                                        sc.next();
                                                    }
                                                } while (op > 2 || op < 1);
                                                if (op == 1) {
                                                    System.out.println("Area (DA, SI or RAS): ");
                                                    System.out.print("> ");
                                                    area = sc.next();
                                                    tokens[j] = area.toUpperCase();
                                                } else {
                                                    return;
                                                }
                                            }
                                        }
                                        outcome = fsm.editInternshipArea(code, tokens);
                                        if (!outcome) {
                                            System.out.println("Area edited unsuccessfully");
                                        } else {
                                            System.out.println("Area edited successfully");
                                        }
                                    }
                                    case 2 -> {
                                        String housingEntity;
                                        op = 0;
                                        sc.nextLine();
                                        System.out.println("Insert the wanted housing entity: ");
                                        housingEntity = sc.nextLine();
                                        while (fsm.verifyName(housingEntity)) {
                                            System.out.println("Internship: " + code);
                                            System.out.println("Housing entity is blank.");
                                            do {
                                                System.out.println("Do you want to reintroduce housing entity?");
                                                try {
                                                    System.out.println("(1 - Reintroduce housing entity; 2 - Cancel)");
                                                    System.out.print("> ");
                                                    op = sc.nextInt();
                                                } catch (InputMismatchException e) {
                                                    System.out.println("It must be an integer!");
                                                    sc.next();
                                                }
                                            } while (op > 2 || op < 1);
                                            if (op == 1) {
                                                System.out.println("Housing entity: ");
                                                System.out.print("> ");
                                                housingEntity = sc.next();
                                            } else {
                                                return;
                                            }
                                        }
                                        outcome = fsm.editInternshipHousingEntity(code, housingEntity);
                                        if (!outcome) {
                                            System.out.println("Area edited unsuccessfully");
                                        } else {
                                            System.out.println("Area edited successfully");
                                        }
                                    }
                                    case 3 -> {
                                        op = 0;
                                        do {
                                            try {
                                                System.out.println("Do you want to associate a student?");
                                                System.out.println("(1-yes; 2-no)");
                                                System.out.print("> ");
                                                op = sc.nextInt();
                                            } catch (InputMismatchException e) {
                                                System.out.println("It must be an integer!");
                                                sc.next();
                                            }
                                        } while (op != 2 && op != 1);
                                        if (op == 2) {
                                            outcome = fsm.editInternshipStd(code, -1);
                                            if (!outcome) {
                                                System.out.println("Internship's student Number editted unsuccessfully");
                                            } else {
                                                System.out.println("Internship's student Number editted successfully");
                                            }
                                        }
                                        long std_number = 0;
                                        do {
                                            try {
                                                System.out.println("Insert student's number: ");
                                                System.out.print("> ");
                                                std_number = sc.nextLong();
                                            } catch (InputMismatchException e) {
                                                System.out.println("It must be a number!");
                                                sc.next();
                                            }
                                        } while (fsm.existsStudent(std_number));
                                        outcome = fsm.editInternshipStd(code, std_number);
                                        if (!outcome) {
                                            System.out.println("Internship wasn't edited successfully");
                                        } else {
                                            System.out.println("Internship was edited successfully");
                                        }
                                    }
                                }
                            }
                            case "T2" -> {
                                op = 0;
                                sc.nextLine();
                                System.out.println("Insert the project's code for which you want to edit the data:");
                                System.out.print("> ");
                                String code = sc.nextLine();
                                outcome = fsm.existsProject(code);
                                if (!outcome)
                                    return;
                                do {
                                    try {
                                        System.out.println("What do you wish to edit?");
                                        System.out.println(" 1-Area;\n 2-Student number;\n 3-Cancel.");
                                        op = sc.nextInt();
                                    } catch (InputMismatchException e) {
                                        System.out.println("It must be an integer!");
                                        sc.next();
                                    }
                                } while (op > 3 || op < 1);
                                switch (op) {
                                    case 1 -> {
                                        String area;
                                        op = 0;
                                        sc.nextLine();
                                        System.out.println("Insert the wanted area(s) (separated by '|'): ");
                                        area = sc.nextLine();

                                        String[] tokens = area.split("\\|");
                                        while (tokens[0] == null || area.isBlank()) {
                                            System.out.println("You must insert an area (DA, SI or RAS)");
                                            tokens[0] = sc.nextLine();
                                            area = tokens[0].toUpperCase();
                                            tokens = area.split("\\|");
                                        }
                                        for (int j = 0; j < tokens.length; j++) {
                                            while (fsm.verifyArea(tokens[j])) {
                                                System.out.println("Invalid area.");
                                                do {
                                                    System.out.println("Do you want to reintroduce area?");
                                                    try {
                                                        System.out.println("(1 - Reintroduce area; 2 - Cancel)");
                                                        System.out.println("> ");
                                                        op = sc.nextInt();
                                                    } catch (InputMismatchException e) {
                                                        System.out.println("It must be an integer!");
                                                        sc.next();
                                                    }
                                                } while (op > 2 || op < 1);
                                                if (op == 1) {
                                                    System.out.println("Area (DA, SI or RAS): ");
                                                    System.out.print("> ");
                                                    area = sc.next();
                                                    tokens[j] = area.toUpperCase();
                                                } else {
                                                    return;
                                                }
                                            }
                                        }
                                        outcome = fsm.editProjectArea(code, tokens);
                                        if (!outcome) {
                                            System.out.println("Project wasn't edited successfully");
                                        } else {
                                            System.out.println("Project was edited successfully");
                                        }
                                    }
                                    case 2 -> {
                                        op = 0;
                                        do {
                                            try {
                                                System.out.println("Do you want to associate a student?");
                                                System.out.println("(1-yes; 2-no)");
                                                System.out.print("> ");
                                                op = sc.nextInt();
                                            } catch (InputMismatchException e) {
                                                System.out.println("It must be an integer!");
                                                sc.next();
                                            }
                                        } while (op != 2 && op != 1);
                                        if (op == 2) {
                                            fsm.editProjectStd(code, -1);
                                            return;
                                        }
                                        long std_number = 0;
                                        do {
                                            try {
                                                System.out.println("Insert student's number: ");
                                                System.out.print("> ");
                                                std_number = sc.nextLong();
                                            } catch (InputMismatchException e) {
                                                System.out.println("It must be a number!");
                                                sc.next();
                                            }
                                        } while (fsm.existsStudent(std_number));
                                        outcome = fsm.editProjectStd(code, std_number);
                                        if (!outcome) {
                                            System.out.println("Project wasn't edited successfully");
                                        } else {
                                            System.out.println("Project was edited successfully");
                                        }
                                    }
                                }
                            }
                            default -> System.out.println("Error");
                        }
                    } while (!type.equalsIgnoreCase("T1") && !type.equalsIgnoreCase("T2"));
                }
                case 4 -> {
                    do {
                        sc.nextLine();
                        System.out.println("What type of POE do you wish to delete?\n(T1-Internship; T2-Project; T3-Self-proposed)");
                        System.out.print("> ");
                        type = sc.nextLine().toUpperCase();
                        System.out.println(type + "here daddy");
                        switch (type) {
                            case "T1" -> {
                                System.out.println("Insert the code of the internship you want to delete the data from:");
                                System.out.print("> ");
                                String code = sc.nextLine();
                                outcome = fsm.deleteInternshipData(code);
                                if (!outcome) {
                                    System.out.println("Internship wasn't deleted successfully");
                                } else {
                                    System.out.println("Internship was deleted successfully");
                                }
                            }
                            case "T2" -> {
                                sc.nextLine();
                                System.out.println("Insert the code of the project you want to delete the data from:");
                                System.out.print("> ");
                                String code = sc.nextLine();
                                outcome = fsm.deleteProjectData(code);
                                if (!outcome) {
                                    System.out.println("Project wasn't deleted successfully");
                                } else {
                                    System.out.println("Project was deleted successfully");
                                }
                            }
                            case "T3" -> {
                                sc.nextLine();
                                System.out.println("Insert the code of the self-proposed you want to delete the data from:");
                                System.out.print("> ");
                                String code = sc.nextLine();
                                outcome = fsm.deleteSelfProposedData(code);
                                if (!outcome) {
                                    System.out.println("SelfProposedProject wasn't deleted successfully");
                                } else {
                                    System.out.println("SelfProposedProject was deleted successfully");
                                }
                            }
                            default -> System.out.println("Error");
                        }
                    } while (!type.equalsIgnoreCase("T1") && !type.equalsIgnoreCase("T2") && !type.equalsIgnoreCase("T3"));
                }
                case 5 -> {
                    System.out.println("***Internships***");
                    if (fsm.listInternships(true).isEmpty())
                        System.out.println("No internships added yet!");
                    else
                        for (Internship i : fsm.listInternships(true))
                            System.out.println(i.toString());

                    System.out.println("\n***Projects***");
                    if (fsm.listProjects(true).isEmpty())
                        System.out.println("No projects added yet!");
                    else
                        for (Project p : fsm.listProjects(true))
                            System.out.println(p.toString());

                    System.out.println("\n***Self-Proposed***");
                    if (fsm.listSelfProposed(true).isEmpty())
                        System.out.println("No Self-proposed added yet!");
                    else
                        for (SelfProposed sp : fsm.listSelfProposed(true))
                            System.out.println(sp.toString());
                }
                case 6 -> {
                    return;
                }
                default -> {
                    System.out.println("Error");
                    return;
                }
            }
        } while (op != 6);
    }

    public boolean verifyAddInternshipByHand(String code, String area, String title, String housing_entity, long std_number) {
        int op = 0;
        boolean outcome;

        //code
        outcome = fsm.existsInternship(code);
        while (outcome) {
            System.out.println("Internship: " + code);
            System.out.println("That internship is already added");
            do {
                System.out.println("Do you want to reintroduce the internship's code?");
                try {
                    System.out.println("(1 - Reintroduce code; 2 - Cancel)");
                    System.out.print("> ");
                    op = sc.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("It must be an integer!");
                    sc.next();
                }
            } while (op > 2 || op < 1);
            sc.nextLine();
            if (op == 1) {
                System.out.println("Code: ");
                System.out.print("> ");
                code = sc.nextLine();
                outcome = fsm.existsInternship(code);
            } else {
                return false;
            }
        }

        //area
        String[] tokens = area.split("\\|");
        while (tokens[0] == null || area.isBlank()) {
            System.out.println("You must insert an area (DA, SI or RAS)");
            tokens[0] = sc.nextLine();
            area = tokens[0];
            tokens = area.split("\\|");
        }
        for (int j = 0; j < tokens.length; j++) {
            while (fsm.verifyArea(tokens[j])) {
                System.out.println("Invalid area.");
                do {
                    System.out.println("Do you want to reintroduce area?");
                    try {
                        System.out.println("(1 - Reintroduce area; 2 - Cancel)");
                        System.out.println("> ");
                        op = sc.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("It must be an integer!");
                        sc.next();
                    }
                } while (op > 2 || op < 1);
                if (op == 1) {
                    System.out.println("Area (DA, SI or RAS): ");
                    System.out.print("> ");
                    area = sc.next();
                    tokens[j] = area.toUpperCase();
                } else {
                    return false;
                }
            }
        }
        List<String> areas = new ArrayList<>(Arrays.asList(tokens));
        areas.replaceAll(String::toUpperCase);

        //title
        while (fsm.verifyName(title)) {
            System.out.println("Internship: " + code);
            System.out.println("Title is blank.");
            op = 0;
            do {
                System.out.println("Do you want to reintroduce title?");
                try {
                    System.out.println("(1 - Reintroduce title; 2 - Cancel)");
                    System.out.print("> ");
                    op = sc.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("It must be an integer!");
                    sc.next();
                }
            } while (op > 2 || op < 1);
            sc.nextLine();
            if (op == 1) {
                System.out.println("Title: ");
                System.out.print("> ");
                title = sc.nextLine();
            } else {
                return false;
            }
        }

        //housing_entity
        while (fsm.verifyName(housing_entity)) {
            System.out.println("Internship: " + code);
            System.out.println("Housing entity is blank.");
            do {
                System.out.println("Do you want to reintroduce housing entity?");
                try {
                    System.out.println("(1 - Reintroduce housing entity; 2 - Cancel)");
                    System.out.print("> ");
                    op = sc.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("It must be an integer!");
                    sc.next();
                }
            } while (op > 2 || op < 1);
            sc.nextLine();
            if (op == 1) {
                System.out.println("Housing entity: ");
                System.out.print("> ");
                housing_entity = sc.nextLine();
            } else {
                return false;
            }
        }

        //student number
        if (std_number != -1) {
            outcome = fsm.existsStudent(std_number);
            while (fsm.verifyStdNumber(std_number) || !outcome) {
                System.out.println("Error with student number");
                do {
                    System.out.println("Do you want to reintroduce the student number?");
                    try {
                        System.out.println("(1 - Reintroduce the student number; 2 - Cancel)");
                        System.out.print("> ");
                        op = sc.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("It must be an integer!");
                        sc.next();
                    }
                } while (op > 2 || op < 1);
                if (op == 1) {
                    try {
                        System.out.println("Student number: ");
                        System.out.print("> ");
                        std_number = sc.nextLong();
                        outcome = fsm.existsStudent(std_number);
                    } catch (InputMismatchException e) {
                        System.out.println("It must be a number!");
                        sc.next();
                    }
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean verifyAddProjectByHand(String code, String area, String title, String email, long std_number) {
        int op = 0;
        boolean outcome;

        //code
        outcome = fsm.existsProject(code);
        while (outcome) {
            System.out.println("Project: " + code);
            System.out.println("That project is already added");
            do {
                System.out.println("Do you want to reintroduce the project's code?");
                try {
                    System.out.println("(1 - Reintroduce code; 2 - Cancel)");
                    System.out.print("> ");
                    op = sc.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("It must be an integer!");
                    sc.next();
                }
            } while (op > 2 || op < 1);
            sc.nextLine();
            if (op == 1) {
                System.out.println("Code: ");
                System.out.print("> ");
                code = sc.nextLine();
                outcome = fsm.existsProject(code);
            } else {
                return false;
            }
        }

        //area
        String[] tokens = area.split("\\|");
        while (tokens[0] == null || area.isBlank()) {
            System.out.println("You must insert an area (DA, SI or RAS)");
            tokens[0] = sc.nextLine();
            area = tokens[0];
            tokens = area.split("\\|");
        }
        for (int j = 0; j < tokens.length; j++) {
            while (fsm.verifyArea(tokens[j])) {
                System.out.println("Invalid area.");
                do {
                    System.out.println("Do you want to reintroduce area?");
                    try {
                        System.out.println("(1 - Reintroduce area; 2 - Cancel)");
                        System.out.print("> ");
                        op = sc.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("It must be an integer!");
                        sc.next();
                    }
                } while (op > 2 || op < 1);
                if (op == 1) {
                    System.out.println("Area (DA, SI or RAS): ");
                    System.out.print("> ");
                    area = sc.next();
                    tokens[j] = area.toUpperCase();
                } else {
                    return false;
                }
            }
        }
        List<String> areas = new ArrayList<>(Arrays.asList(tokens));
        areas.replaceAll(String::toUpperCase);

        //title
        while (fsm.verifyName(title)) {
            System.out.println("Internship: " + code);
            System.out.println("Title is blank.");
            do {
                System.out.println("Do you want to reintroduce title?");
                try {
                    System.out.println("(1 - Reintroduce title; 2 - Cancel)");
                    System.out.print("> ");
                    op = sc.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("It must be an integer!");
                    sc.next();
                }
            } while (op > 2 || op < 1);
            sc.nextLine();
            if (op == 1) {
                System.out.println("Title: ");
                System.out.print("> ");
                title = sc.nextLine();
            } else {
                return false;
            }
        }

        //email
        outcome = fsm.existsProfessor(email);
        while (!outcome) {
            System.out.println("Internship: " + code);
            System.out.println("Professor not found ");
            do {
                System.out.println("Do you want to reintroduce email?");
                try {
                    System.out.println("(1 - Reintroduce email; 2 - Cancel)");
                    System.out.print("> ");
                    op = sc.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("It must be an integer!");
                    sc.next();
                }
            } while (op > 2 || op < 1);
            sc.nextLine();
            if (op == 2) {
                return false;
            }
            System.out.println("Email: ");
            System.out.print("> ");
            email = sc.nextLine();
            outcome = fsm.existsProfessor(email);
        }

        //student number
        if (std_number != -1) {
            outcome = fsm.existsStudent(std_number);
            while (fsm.verifyStdNumber(std_number) || !outcome) {
                System.out.println("Error with student number");
                do {
                    System.out.println("Do you want to reintroduce the student number?");
                    try {
                        System.out.println("(1 - Reintroduce the student number; 2 - Cancel)");
                        System.out.print("> ");
                        op = sc.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("It must be an integer!");
                        sc.next();
                    }
                } while (op > 2 || op < 1);
                if (op == 1) {
                    try {
                        System.out.println("Student number: ");
                        System.out.print("> ");
                        std_number = sc.nextLong();
                        outcome = fsm.existsStudent(std_number);
                    } catch (InputMismatchException e) {
                        System.out.println("It must be a number!");
                        sc.next();
                    }
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean verifyAddSelfProposedByHand(String code, String title, long std_number) {
        int op = 0;
        boolean outcome;

        //code
        outcome = fsm.existsInternship(code);
        while (outcome) {
            System.out.println("Internship: " + code);
            System.out.println("That internship is already added");
            do {
                System.out.println("Do you want to reintroduce the internship's code?");
                try {
                    System.out.println("(1 - Reintroduce code; 2 - Cancel)");
                    System.out.print("> ");
                    op = sc.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("It must be an integer!");
                    sc.next();
                }
            } while (op > 2 || op < 1);
            sc.nextLine();
            if (op == 1) {
                System.out.println("Code: ");
                System.out.print("> ");
                code = sc.nextLine();
                outcome = fsm.existsInternship(code);
            } else {
                return false;
            }
        }

        //title
        while (fsm.verifyName(title)) {
            System.out.println("Internship: " + code);
            System.out.println("Title is blank.");
            do {
                System.out.println("Do you want to reintroduce title?");
                try {
                    System.out.println("(1 - Reintroduce title; 2 - Cancel)");
                    System.out.print("> ");
                    op = sc.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("It must be an integer!");
                    sc.next();
                }
            } while (op > 2 || op < 1);
            sc.nextLine();
            if (op == 1) {
                System.out.println("Title: ");
                System.out.print("> ");
                title = sc.nextLine();
            } else {
                return false;
            }
        }

        //student number
        outcome = fsm.existsStudent(std_number);
        while (fsm.verifyStdNumber(std_number) || !outcome) {
            System.out.println("Error with student number");
            do {
                System.out.println("Do you want to reintroduce the student number?");
                try {
                    System.out.println("(1 - Reintroduce the student number; 2 - Cancel)");
                    System.out.print("> ");
                    op = sc.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("It must be an integer!");
                    sc.next();
                }
            } while (op > 2 || op < 1);
            if (op == 1) {
                try {
                    System.out.println("Student number: ");
                    System.out.print("> ");
                    std_number = sc.nextLong();
                    outcome = fsm.existsStudent(std_number);
                } catch (InputMismatchException e) {
                    System.out.println("It must be a number!");
                    sc.next();
                }
            } else {
                return false;
            }
        }
        return true;
    }

    //1st Phase Blocked
    private void configBlockedUi() {
        switch (PAInput.chooseOption("***Config Phase Blocked***", "List Students", "List Professors", "List Poes",
                "Next Phase", "Exit")) {
            case 1 -> {
                System.out.println("***Students***");
                if (fsm.listStudents(true).isEmpty())
                    System.out.println("No students added yet!");
                else
                    for (Student s : fsm.listStudents(true))
                        System.out.println(s.toString());
            }
            case 2 -> {
                System.out.println("***Professors***");
                if (fsm.listProfessors(true).isEmpty())
                    System.out.println("No professors added yet!");
                else
                    for (Professor p : fsm.listProfessors(true))
                        System.out.println(p.toString());
            }
            case 3 -> {
                System.out.println("***Internships***");
                if (fsm.listInternships(true).isEmpty())
                    System.out.println("No internships added yet!");
                else
                    for (Internship i : fsm.listInternships(true))
                        System.out.println(i.toString());

                System.out.println("\n***Projects***");
                if (fsm.listProjects(true).isEmpty())
                    System.out.println("No projects added yet!");
                else
                    for (Project p : fsm.listProjects(true))
                        System.out.println(p.toString());

                System.out.println("\n***Self-Proposed***");
                if (fsm.listSelfProposed(true).isEmpty())
                    System.out.println("No Self-proposed added yet!");
                else
                    for (SelfProposed sp : fsm.listSelfProposed(true))
                        System.out.println(sp.toString());
            }
            case 4 -> fsm.next_phase();
            case 5 -> finish = true;
        }
    }

    //2nd Phase
    private void enrollmentUi() {
        switch (PAInput.chooseOption("***Enrolment Phase***", "Manage Applications", "List Students with criteria",
                "List Poe with criteria", "Rewind Phase", "Skip Phase", "End Phase", "Exit")) {
            case 1 -> manageApplications();
            case 2 -> listStudentsWithCriteria();
            case 3 -> listPoeWithCriteria();
            case 4 -> fsm.rewind_phase();
            case 5 -> fsm.next_phase();
            case 6 -> fsm.close_phase();
            case 7 -> finish = true;
        }
    }

    public void manageApplications() {
        String fileName, fullFileName;
        int op = 0;
        boolean outcome;
        do {
            System.out.println("--- Manage Applications---");
            System.out.println("What do you wish to do?");
            try {
                System.out.println(" 1-Add;\n 2-Consult;\n 3-Edit;\n 4-Delete;\n 5-List;\n 6-Exit.");
                System.out.print("> ");
                op = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("It must be an integer!");
                sc.next();
            }
            switch (op) {
                case 1 -> {
                    op = 0;
                    System.out.print("Do you want to read from a file or add a student application manually?");
                    do {
                        try {
                            System.out.println("(1-Add by file; 2-Add manually;)");
                            System.out.print("> ");
                            op = sc.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("It must be an integer!");
                            sc.next();
                        }
                    } while (op > 2 || op < 1);
                    switch (op) {
                        case 1 -> {
                            List<String> codes = new ArrayList<>();
                            fileName = PAInput.readString("File name", true);
                            fullFileName = "..\\noError\\TP\\ficheiros\\" + fileName + ".txt";
                            try (FileReader fr = new FileReader(fullFileName);
                                 BufferedReader br = new BufferedReader(fr)) {
                                String line;
                                while ((line = br.readLine()) != null) {
                                    String[] tokens = line.split(",");
                                    for(int i = 1; i < tokens.length; i++){
                                        codes.add(tokens[i]);
                                    }
                                    outcome = fsm.addApplicationByHand(Long.parseLong(tokens[0]), codes);
                                    if (!outcome)
                                        System.out.println("Error adding application by hand");
                                }
                            } catch(FileNotFoundException e){
                                System.out.println("File not found");
                            } catch (IOException e){
                                System.out.println("Error opening the file");
                            }
                        }
                        case 2 -> {
                            outcome = getApplicationData();
                            if (!outcome) {
                                System.out.println("Application wasn't added successfully");
                            } else {
                                System.out.println("Application was added successfully");
                            }
                        }
                    }
                }
                case 2 -> {
                    Application a = consultApplication();
                    if (a == null)
                        System.out.println("Application not found");
                    else
                        System.out.println(a);
                }
                case 3 -> {
                    op = 0;
                    long num = 0;
                    do {
                        try {
                            System.out.println("Insert the Student's number for which you want to edit the data:");
                            System.out.print("> ");
                            num = sc.nextLong();
                        } catch (InputMismatchException e) {
                            System.out.println("It must be a number");
                            sc.next();
                        }
                    } while (num == 0);
                    Application a = fsm.searchApplication(num);
                    if (a == null)
                        System.out.println("Application not found");
                    else {
                        do {
                            try {
                                System.out.println("What do you wish to edit?");
                                System.out.println(" 1-Codes;\n 2-Cancel.");
                                op = sc.nextInt();
                            } catch (InputMismatchException e) {
                                System.out.println("It must be an integer!");
                                sc.next();
                            }
                        } while (op > 2 || op < 1);
                        if (op == 1) {
                            outcome = editApplication(num);
                            if (!outcome)
                                System.out.println("Error");
                        }
                    }
                }
                case 4 -> {
                    sc.nextLine();
                    long std_number = 0;
                    do {
                        try {
                            System.out.println("Insert the student number whose application you wish to delete:");
                            System.out.print("> ");
                            std_number = sc.nextLong();
                        } catch (InputMismatchException e) {
                            System.out.println("It must be a number!");
                            sc.next();
                        }
                    } while (std_number == 0);
                    outcome = fsm.deleteApplicationData(std_number);
                    if (!outcome)
                        System.out.println("Application deleted unsuccessfully");
                    else
                        System.out.println("Application deleted successfully");
                }
                case 5 -> {
                    System.out.println("***Applications***");
                    if (fsm.listApplications(true).isEmpty())
                        System.out.println("No applications added yet!");
                    else
                        for (Application a : fsm.listApplications(true))
                            System.out.println(a.toString());
                }
                case 6 -> {
                    return;
                }
                default -> System.out.println("Error");
            }
        } while (op != 6);
    }

    public boolean getApplicationData() {
        long std_number;
        List<String> codes = new ArrayList<>();
        String code;
        System.out.println("***Application Info***");
        try {
            System.out.println("Student's number: ");
            std_number = sc.nextLong();
        } catch (InputMismatchException e) {
            return false;
        }
        sc.next();
        do {
            System.out.println("Code: ");
            code = sc.next();
            if (!code.equalsIgnoreCase("end") && !codes.contains(code.toUpperCase()))
                codes.add(code.toUpperCase());
        } while (!code.equalsIgnoreCase("end"));
        return fsm.addApplicationByHand(std_number, codes);
    }

    public Application consultApplication() {
        sc.nextLine();
        long std_number;
        do {
            try {
                System.out.println("Insert the student number whose application you are looking for:");
                System.out.print("> ");
                std_number = sc.nextLong();
            } catch (InputMismatchException e) {
                return null;
            }
        } while (std_number == 0);
        return fsm.searchApplication(std_number);
    }

    public Student consultStudent(long std_number) {
        sc.nextLine();
        return fsm.searchStudent(std_number);
    }

    public Professor consultProfessor(String email) {
        sc.nextLine();
        return fsm.searchProfessor(email);
    }

    public SelfProposed consultSelfProposed(String code) {
        sc.nextLine();
        return fsm.searchSelfProposed(code);
    }

    public Internship consultInternship(String code) {
        sc.nextLine();
        return fsm.searchInternship(code);
    }

    public Project consultProject(String code) {
        sc.nextLine();
        return fsm.searchProject(code);
    }

    public boolean editApplication(long std_number) {
        List<String> codes = new ArrayList<>();
        String code;
        do {
            System.out.println("Code: ");
            code = sc.next();
            if (!code.equalsIgnoreCase("end") && !codes.contains(code.toUpperCase()))
                codes.add(code.toUpperCase());
        } while (!code.equalsIgnoreCase("end"));
        return fsm.editApplicationCodes(std_number, codes);
    }

    public void listStudentsWithCriteria() {
        int op = 0;
        do {
            System.out.println("--- List Students With Criteria---");
            System.out.println("Which criteria do you want to apply?");
            try {
                System.out.println(" 1-Self-proposed;\n 2-With Registered Application;\n 3-Without Registered Application;\n 4-Exit.");
                System.out.print("> ");
                op = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("It must be an integer!");
                sc.next();
            }
            switch (op) {
                case 1 -> {
                    System.out.println("***Self-Proposed Students***");
                    if (fsm.listSelfProposedStudents().isEmpty())
                        System.out.println("No self-proposed students added yet!");
                    else
                        for (Student s : fsm.listSelfProposedStudents())
                            System.out.println(s.toString());
                }
                case 2 -> {
                    System.out.println("***Students With Application***");
                    if (fsm.listStudentsWithApplication().isEmpty())
                        System.out.println("No applications added yet!");
                    else
                        for (Student s : fsm.listStudentsWithApplication())
                            System.out.println(s.toString());
                }
                case 3 -> {
                    System.out.println("***Students Without Application***");
                    if (fsm.listStudentsWithoutApplication().isEmpty())
                        System.out.println("No students without applications!");
                    else
                        for (Student s : fsm.listStudentsWithoutApplication())
                            System.out.println(s.toString());
                }
                case 4 -> {
                    return;
                }
                default -> System.out.println("Error");
            }
        } while (op != 4);
    }

    public void listPoeWithCriteria() {
        ArrayList<Integer> ops = new ArrayList<>();
        String options;
        String[] opts;
        System.out.println("--- List Poe With Criteria---");
        System.out.println("Which criteria do you want to apply?");
        System.out.println("""
                1-Students proposals;
                2-Professors proposals;
                3-Proposals With Registered Application;
                4-Proposals Without Registered Application;
                5-Exit.""".indent(1));
        System.out.print("> ");
        options = sc.next();
        opts = options.split(" ");
        for (String s : opts) {
            int o = Integer.parseInt(s);
            if (!ops.contains(o))
                ops.add(o);
        }
        if (ops.contains(5))
            return;
        System.out.println(fsm.listPoesWithCriteria(ops).toString());
    }

    //2nd Phase Blocked
    private void enrollmentBlockedUi() {
        switch (PAInput.chooseOption("***Enrolment Phase Blocked***", "List Applications", "List Students with criteria",
                "List Poe with criteria", "Previous Phase", "Next Phase", "Exit")) {
            case 1 -> {
                System.out.println("***Applications***");
                if (fsm.listApplications(true).isEmpty())
                    System.out.println("No applications added yet!");
                else
                    for (Application a : fsm.listApplications(true))
                        System.out.println(a.toString());
            }
            case 2 -> listStudentsWithCriteria();
            case 3 -> listPoeWithCriteria();
            case 4 -> fsm.rewind_phase();
            case 5 -> fsm.next_phase();
            case 6 -> finish = true;
        }
    }

    //3rd Phase
    private void atProposalsUi() {
        switch (PAInput.chooseOption("***At_proposals Phase***", "Manual Assignment Of Proposals Available To Students", "List Assignments",
                "Remove An Assignment", "Remove All Assignments", "Search Students With Criteria", "Search Poe With Criteria",
                "Rewind Phase", "Skip Phase", "End Phase", "Exit")) {
            case 1 -> manualAssignment();
            case 2 -> {
                System.out.println("***Assignments***");
                if (fsm.listAssignments(true).isEmpty())
                    System.out.println("No assignments added yet!");
                else
                    for (Assignment a : fsm.listAssignments(true))
                        System.out.println(a.toString());
            }
            case 3 -> removeAssignment();
            case 4 -> removeAllAssignments();
            case 5 -> listStudentsWithCriteria3rdPhase();
            case 6 -> listPoeWithCriteria3rdPhase();
            case 7 -> fsm.rewind_phase();
            case 8 -> fsm.next_phase();
            case 9 -> fsm.close_phase();
            case 10 -> finish = true;
        }
    }

    public void listPoeWithCriteria3rdPhase() {
        ArrayList<Integer> ops = new ArrayList<>();
        String options;
        String[] opts;
        System.out.println("--- List Poe With Criteria---");
        System.out.println("Which criteria do you want to apply?");
        System.out.println("""
                1-Students proposals;
                2-Proposed by professors;
                3-Available for application;
                4-With application attributed;
                5-Exit.""".indent(1));
        System.out.print("> ");
        options = sc.next();
        opts = options.split(" ");
        for (String s : opts) {
            int o = Integer.parseInt(s);
            if (!ops.contains(o))
                ops.add(o);
        }
        if (ops.contains(5))
            return;
        System.out.println(fsm.listPoesWithCriteria3rdPhase(ops).toString());
    }

    public void manualAssignment() {
        long std_number;
        boolean outcome;
        String code;
        System.out.println("Insert the code: ");
        code = sc.nextLine();
        try {
            System.out.println("Insert the student number: ");
            std_number = sc.nextLong();
        } catch (InputMismatchException e) {
            return;
        }
        outcome = fsm.addAssignment(code, std_number);
        if (!outcome) {
            System.out.println("Error adding Assignment");
        } else {
            System.out.println("Assignment added successfully");
        }
    }

    public void removeAssignment() {
        sc.nextLine();
        boolean outcome;
        long std_number = 0;
        do {
            try {
                System.out.println("Insert the student number whose assignment you wish to delete:");
                System.out.print("> ");
                std_number = sc.nextLong();
            } catch (InputMismatchException e) {
                System.out.println("It must be a long!");
                sc.next();
            }
        } while (std_number == 0);
        outcome = fsm.removeAssignment(std_number);
        if (!outcome) {
            System.out.println("Assignment could not be removed");
        } else {
            System.out.println("Assignment was removed");
        }
    }

    public void removeAllAssignments() {
        int op = 0;
        boolean outcome;
        do {
            try {
                System.out.println("Are you sure you want to delete all assignments?");
                System.out.println("(1-delete all assignments; 2-cancel");
                System.out.print("> ");
                op = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("It must be an integer!");
                sc.next();
            }
        } while (op == 0);
        if (op == 2) {
            return;
        }
        outcome = fsm.removeAllAssignments();
        if (!outcome) {
            System.out.println("All assignments couldn't be removed");
        } else {
            System.out.println("All assignments were removed");
        }
    }

    public void listStudentsWithCriteria3rdPhase() {
        int op = 0;
        do {
            System.out.println("--- List Students With Criteria---");
            System.out.println("Which criteria do you want to apply?");
            try {
                System.out.println(" 1-Self-proposed;\n 2-With Registered Application;\n 3-With Self-Proposed Atributed ;\n 4-Without Registered Application;\n 5-Exit.");
                System.out.print("> ");
                op = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("It must be an integer!");
                sc.next();
            }
            switch (op) {
                case 1 -> {
                    System.out.println("***Self-proposed Students***");
                    if (fsm.listSelfProposedStudents().isEmpty())
                        System.out.println("No self-proposed students added yet!");
                    else
                        for (Student s : fsm.listSelfProposedStudents())
                            System.out.println(s.toString());
                }
                case 2 -> {
                    System.out.println("***Students With Application***");
                    if (fsm.listStudentsWithApplication().isEmpty())
                        System.out.println("No applications added yet!");
                    else
                        for (Student s : fsm.listStudentsWithApplication())
                            System.out.println(s.toString());
                }
                case 3 -> {
                    System.out.println("***Students With Self-Proposed Attributed***");
                    if (fsm.listStudentsWithSelfProposedAttributed().isEmpty())
                        System.out.println("No students with self-proposed attributed!");
                    else
                        for (Student s : fsm.listStudentsWithSelfProposedAttributed())
                            System.out.println(s.toString());
                }
                case 4 -> {
                    System.out.println("***Students Without Application***");
                    if (fsm.listStudentsWithoutApplication().isEmpty())
                        System.out.println("No students without applications!");
                    else
                        for (Student s : fsm.listStudentsWithoutApplication())
                            System.out.println(s.toString());
                }
                case 5 -> {
                    return;
                }
                default -> System.out.println("Error");
            }
        } while (op != 4);
    }

    //3rd Phase Blocked
    private void atProposalsBlockedUi() {
        switch (PAInput.chooseOption("***At_proposals Phase Blocked***", "List Assignments",
                "Previous Phase", "Next Phase", "Exit")) {
            case 1 -> {
                System.out.println("***Assignments***");
                if (fsm.listAssignments(true).isEmpty())
                    System.out.println("No assignments added yet!");
                else
                    for (Assignment a : fsm.listAssignments(true))
                        System.out.println(a.toString());
            }
            case 2 -> fsm.rewind_phase();
            case 3 -> fsm.next_phase();
            case 4 -> finish = true;
        }
    }

    //4th Phase
    private void at_OrientUi() {
        switch (PAInput.chooseOption("***at_orient Phase***", "Manage Coordinator", "Search Coordinator With " +
                "Criteria", "List Assignments", "Rewind Phase", "End Phase", "Exit")) {
            case 1 -> manageCoordinator();
            case 2 -> searchCoordinatorWithCriteria();
            case 3 -> {
                System.out.println("***Assignments***");
                if (fsm.listAssignments(true).isEmpty())
                    System.out.println("No assignments added yet!");
                else
                    for (Assignment a : fsm.listAssignments(true))
                        System.out.println(a.toString());
            }
            case 4 -> fsm.rewind_phase();
            case 5 -> fsm.close_phase();
            case 6 -> finish = true;
        }
    }

    public void manageCoordinator() {
        int op = 0;
        do {
            System.out.println("---- Manage Coordinator ----");
            System.out.println("What do you wish to do?");
            try {
                System.out.println(" 1-Add;\n 2-Consult;\n 3-Edit;\n 4-Delete;\n 5-List;\n 6-Exit.");
                System.out.print("> ");
                op = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("It must be an integer!");
                sc.next();
            }
            switch (op) {
                case 1 -> {
                    boolean outcome = getCoordinatorData();
                    if(!outcome)
                        System.out.println("Error adding coordinator");
                    else
                        System.out.println("Coordinator added successfully");
                }
                case 2 -> {
                    System.out.println("Insert the professor's email you are looking for: ");
                    sc.nextLine();
                    System.out.print("> ");
                    String email = sc.next();

                    Professor p = fsm.searchProfessor(email);
                    if(p != null && p.getRole().equalsIgnoreCase("COORDINATOR"))
                        System.out.println(fsm.searchProfessor(email).toString());
                    else
                        System.out.println("Coordinator not found");
                }
                case 3 -> {
                    op = 0;
                    String code;
                    sc.nextLine();
                    do {
                        System.out.println("Insert the code of the Poe whose coordinator you you want to edit");
                        System.out.print("> ");
                        code = sc.nextLine();
                    } while (code.isBlank());
                    do {
                        try {
                            System.out.println("What do you wish to edit?");
                            System.out.println(" 1-Email;\n 2-Cancel.");
                            op = sc.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("It must be an integer!");
                            sc.next();
                        }
                    } while (op > 2 || op < 1);
                    if (op == 1) {
                        boolean outcome = editProfessorEmail(code);
                        if (!outcome) {
                            System.out.println("Error editing professors email");
                        } else {
                            System.out.println("Professors email edited with success");
                        }
                    }
                }
                case 4 -> {
                    String code;
                    sc.nextLine();
                    do{
                        System.out.println("Insert the code of the Poe whose coordinator you wish to delete");
                        System.out.print("> ");
                        code = sc.nextLine();
                    }while(code.isBlank());

                    boolean outcome = fsm.deleteCoordinator(code);
                    if(!outcome)
                        System.out.println("Error deleting coordinator");
                    else
                        System.out.println("Coordinator deleted successfully");
                }
                case 5 -> {
                    System.out.println("***Coordinators***");
                    if (fsm.listCoordinators().isEmpty())
                        System.out.println("No coordinators yet!");
                    else
                        for (Professor p : fsm.listCoordinators())
                            System.out.println(p.toString());
                }
                case 6 -> {
                }
                default -> System.out.println("Error");
            }
        } while (op != 6);
    }

    public boolean getCoordinatorData() {
        String email;
        String code;

        System.out.println("Insert the code: ");
        code = sc.nextLine();
        System.out.println("Insert the coordinators email: ");
        email = sc.nextLine();
        return fsm.addCoordinators(email, code);
    }

    private boolean editProfessorEmail(String code) {
        System.out.println("Insert the wanted email: ");
        String email = sc.nextLine();

        return fsm.editCoordinatorName(code, email);
    }

    public void searchCoordinatorWithCriteria() {
        int op = 0;
        do {
            System.out.println("---- Search Data Regarding Professors ----");
            System.out.println("What do you wish to do?");
            try {
                System.out.println(" 1-List Students With Registered Application and Registered Coordinator;\n " +
                        "2-List Students With Registered Application and Without Registered Coordinator;\n " +
                        "3-List Number of PoEs Coordinated By Professors;\n 4-Exit.");
                System.out.print("> ");
                op = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("It must be an integer!");
                sc.next();
            }
            switch (op) {
                case 1 -> {
                    System.out.println("***Students With Coordinator***");
                    if (fsm.listStudentWithCoordinator().isEmpty())
                        System.out.println("No students with coordinator yet!");
                    else
                        for (Student s : fsm.listStudentWithCoordinator())
                            System.out.println(s.toString());
                }
                case 2 -> {
                    System.out.println("***Students Without Coordinator***");
                    if (fsm.listStudentWithoutCoordinator().isEmpty())
                        System.out.println("No students without coordinator!");
                    else
                        for (Student s : fsm.listStudentWithoutCoordinator())
                            System.out.println(s.toString());
                }
                case 3 -> NumberOfOrientations();
                case 4 -> {
                    return;
                }
                default -> System.out.println("Error");
            }
        } while (op != 4);
    }

    private void NumberOfOrientations() {
        int op = 0;
        do {
            System.out.println("---- List Number of Poes Coordinated By Professors ----");
            System.out.println("What do you wish to do?");
            try {
                System.out.println(" 1-Number of Poes for professor;\n 2-Average;\n 3-Max;\n 4-Min;\n 5-Specific Professor;\n 6-Exit.");
                System.out.print("> ");
                op = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("It must be an integer!");
                sc.next();
            }
            switch (op) {
                case 1 -> {
                    System.out.println("***Professors***");
                    if (fsm.listProfessors(true).isEmpty())
                        System.out.println("No professors added yet!");
                    else
                        for (Professor p : fsm.listProfessors(true))
                            System.out.println(p.toString());
                } //TODO é suposto printar a situação toda dos professors?
                case 2 -> System.out.println("The average number of coordinated Poes is: " + fsm.listAverage());
                case 3 -> System.out.println("Max coordinated Poes : " + fsm.listMax());
                case 4 -> System.out.println("Min coordinated Poes : " + fsm.listMin());
                case 5 -> {
                    System.out.println("Insert the professor's email you are looking for: ");
                    sc.nextLine();
                    System.out.print("> ");
                    String email = sc.next();

                    System.out.println(fsm.searchProfessor(email).toString());
                }
                case 6 -> {
                    return;
                }
                default -> System.out.println("Error");
            }
        } while (op != 6);
    }

    //5th Phase
    private void consultUi() {
        switch (PAInput.chooseOption("***consult Phase***", "List Students With Attributed Proposals",
                "List Students Without Attributed Proposals And With Applications", "List Available Proposals",
                "List Attributed Proposals", "List Number of PoEs Coordinated By Professors", "Exit")) {
            case 1 -> {
                System.out.println("***Students With Attributed Proposals***");
                for (Student s : fsm.listStudentsWithAttributedProposals())
                    System.out.println(s.toString());

            }
            case 2 -> {
                System.out.println("***Students Without Attributed Proposals With Application***");
                for (Student s : fsm.listStudentsWithoutAttributedProposalsWithApplication())
                    System.out.println(s.toString());

            }
            case 3 -> {
                System.out.println("***Available Internships***");
                if (fsm.listAvailableInternships().isEmpty())
                    System.out.println("No available internships!");
                else
                    for (Internship i : fsm.listAvailableInternships())
                        System.out.println(i.toString());

                System.out.println("***Available Projects***");
                if (fsm.listAvailableProjects().isEmpty())
                    System.out.println("No available projects!");
                else
                    for (Project p : fsm.listAvailableProjects())
                        System.out.println(p.toString());
            }
            case 4 -> {
                System.out.println("***Attributed Internships***");
                if (fsm.listAttributedInternships().isEmpty())
                    System.out.println("No internships attributed!");
                else
                    for (Internship i : fsm.listAttributedInternships())
                        System.out.println(i.toString());

                System.out.println("***Attributed Projects***");
                if (fsm.listAttributedProjects().isEmpty())
                    System.out.println("No available projects!");
                else
                    for (Project p : fsm.listAttributedProjects())
                        System.out.println(p.toString());

                System.out.println("***Attributed Self-proposed***");
                if (fsm.listSelfProposed(true).isEmpty())
                    System.out.println("No self-proposed attributed!");
                else
                    for (SelfProposed sp : fsm.listSelfProposed(true))
                        System.out.println(sp.toString());
            }
            case 5 -> NumberOfOrientations5thPhase();
            case 6 -> finish = true;
        }
    }

    public void NumberOfOrientations5thPhase() {
        int op = 0;
        do {
            System.out.println("---- List Number of Poes Coordinated By Professors ----");
            System.out.println("What do you wish to do?");
            try {
                System.out.println(" 1-Number of Poes for professor;\n 2-Average;\n 3-Max;\n 4-Min;\n 5-Specific Professor;\n 6-Exit.");
                System.out.print("> ");
                op = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("It must be an integer!");
                sc.next();
            }
            switch (op) {
                case 1 -> {
                    System.out.println("***Professors***");
                    if (fsm.listProfessors(true).isEmpty())
                        System.out.println("No professors added yet!");
                    else
                        for (Professor p : fsm.listProfessors(true))
                            System.out.println(p.toString());
                    //TODO verificar se é suposto aparecer toda a info de todos os professors
                }
                case 2 -> System.out.println("The average number of coordinated Poes is: " + fsm.listAverage());
                case 3 -> System.out.println("Max coordinated Poes : " + fsm.listMax());
                case 4 -> System.out.println("Min coordinated Poes : " + fsm.listMin());
                case 5 -> {
                    System.out.println("Insert the professor's email you are looking for: ");
                    sc.nextLine();
                    System.out.print("> ");
                    String email = sc.next();

                    System.out.println(fsm.searchProfessor(email).toString());
                }
                case 6 -> {
                    return;
                }
                default -> System.out.println("Error");
            }
        } while (op != 5);
    }
}