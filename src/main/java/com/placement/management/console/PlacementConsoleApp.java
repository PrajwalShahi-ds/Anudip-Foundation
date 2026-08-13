package com.placement.management.console;

import com.placement.management.dto.*;
import com.placement.management.enums.ApplicationStatus;
import com.placement.management.enums.CompanyStatus;
import com.placement.management.enums.InterviewResult;
import com.placement.management.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * Standalone Console Interface for Placement Management System.
 * Allows interactive testing in IntelliJ IDEA terminal without Postman or Web Browser!
 */
@Component
public class PlacementConsoleApp implements CommandLineRunner {

    private final StudentService studentService;
    private final CompanyService companyService;
    private final JobService jobService;
    private final ApplicationService applicationService;
    private final InterviewService interviewService;
    private final PlacementService placementService;
    private final DashboardService dashboardService;

    public PlacementConsoleApp(StudentService studentService,
                               CompanyService companyService,
                               JobService jobService,
                               ApplicationService applicationService,
                               InterviewService interviewService,
                               PlacementService placementService,
                               DashboardService dashboardService) {
        this.studentService = studentService;
        this.companyService = companyService;
        this.jobService = jobService;
        this.applicationService = applicationService;
        this.interviewService = interviewService;
        this.placementService = placementService;
        this.dashboardService = dashboardService;
    }

    @Override
    public void run(String... args) {
        // Check if user ran with --console flag or wants interactive console mode
        System.out.println("\n-----------------------------------------------------------");
        System.out.println(" Placement Management System Console Service Initialized!");
        System.out.println(" You can test via REST API or interactively via this console.");
        System.out.println("-----------------------------------------------------------\n");
    }

    public void startInteractiveConsole() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n=======================================================");
            System.out.println("      PLACEMENT MANAGEMENT SYSTEM - CONSOLE CLI        ");
            System.out.println("=======================================================");
            System.out.println(" 1. View Dashboard & Placement Statistics");
            System.out.println(" 2. List All Students");
            System.out.println(" 3. Register New Student");
            System.out.println(" 4. List All Recruiting Companies");
            System.out.println(" 5. Register New Company");
            System.out.println(" 6. List All Job Openings");
            System.out.println(" 7. Post New Job Opening");
            System.out.println(" 8. Check Eligible Jobs for a Student");
            System.out.println(" 9. Apply for Job (Triggers CGPA Eligibility Check)");
            System.out.println(" 10. Schedule Interview Round");
            System.out.println(" 11. Update Interview Result (Passed/Failed)");
            System.out.println(" 12. Log Confirmed Student Placement");
            System.out.println(" 0. Exit Console Mode");
            System.out.println("=======================================================");
            System.out.print("Select an option (0-12): ");

            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1" -> displayDashboard();
                    case "2" -> listStudents();
                    case "3" -> registerStudent(scanner);
                    case "4" -> listCompanies();
                    case "5" -> registerCompany(scanner);
                    case "6" -> listJobs();
                    case "7" -> postJob(scanner);
                    case "8" -> checkEligibleJobs(scanner);
                    case "9" -> applyForJob(scanner);
                    case "10" -> scheduleInterview(scanner);
                    case "11" -> updateInterviewResult(scanner);
                    case "12" -> logPlacement(scanner);
                    case "0" -> {
                        running = false;
                        System.out.println("Exiting Console Mode...");
                    }
                    default -> System.out.println("Invalid selection. Please enter a number from 0 to 12.");
                }
            } catch (Exception e) {
                System.out.println("\n[ERROR]: " + e.getMessage());
            }
        }
    }

    private void displayDashboard() {
        DashboardStatsDTO stats = dashboardService.getDashboardStats();
        System.out.println("\n=== PLACEMENT DASHBOARD STATS ===");
        System.out.println("Total Students         : " + stats.getTotalStudents());
        System.out.println("Active Students        : " + stats.getActiveStudents());
        System.out.println("Total Companies        : " + stats.getTotalCompanies());
        System.out.println("Active Jobs            : " + stats.getActiveJobs());
        System.out.println("Total Applications     : " + stats.getTotalApplications());
        System.out.println("Shortlisted Candidates : " + stats.getShortlistedApplications());
        System.out.println("Placed Students        : " + stats.getTotalPlacements());
        System.out.println("Average CTC Package    : " + stats.getAveragePackage() + " LPA");
        System.out.println("Highest CTC Package    : " + stats.getHighestPackage() + " LPA");
    }

    private void listStudents() {
        List<StudentDTO> list = studentService.getAllStudents();
        System.out.println("\n=== STUDENT RECORDS (" + list.size() + ") ===");
        for (StudentDTO s : list) {
            System.out.printf("ID: %d | Name: %-15s | Dept: %-15s | CGPA: %.2f | Email: %s\n",
                    s.getId(), s.getName(), s.getDepartment(), s.getCgpa(), s.getEmail());
        }
    }

    private void registerStudent(Scanner scanner) {
        System.out.print("Enter Student Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Phone (10 digits): ");
        String phone = scanner.nextLine();
        System.out.print("Enter Department: ");
        String dept = scanner.nextLine();
        System.out.print("Enter CGPA (0.0 - 10.0): ");
        double cgpa = Double.parseDouble(scanner.nextLine());
        System.out.print("Enter Passing Year: ");
        int year = Integer.parseInt(scanner.nextLine());

        StudentRequestDTO dto = new StudentRequestDTO();
        dto.setName(name);
        dto.setEmail(email);
        dto.setPhone(phone);
        dto.setDepartment(dept);
        dto.setCgpa(cgpa);
        dto.setPassingYear(year);
        dto.setActive(true);

        StudentDTO created = studentService.createStudent(dto);
        System.out.println("\n[SUCCESS] Registered Student with ID: " + created.getId());
    }

    private void listCompanies() {
        List<CompanyDTO> list = companyService.getAllCompanies();
        System.out.println("\n=== RECRUITING COMPANIES (" + list.size() + ") ===");
        for (CompanyDTO c : list) {
            System.out.printf("ID: %d | Name: %-15s | Industry: %-20s | Status: %s\n",
                    c.getId(), c.getName(), c.getIndustry(), c.getStatus());
        }
    }

    private void registerCompany(Scanner scanner) {
        System.out.print("Enter Company Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Industry: ");
        String industry = scanner.nextLine();
        System.out.print("Enter HR Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Contact Phone: ");
        String phone = scanner.nextLine();

        CompanyDTO dto = new CompanyDTO();
        dto.setName(name);
        dto.setIndustry(industry);
        dto.setEmail(email);
        dto.setPhone(phone);
        dto.setStatus(CompanyStatus.ACTIVE);

        CompanyDTO created = companyService.createCompany(dto);
        System.out.println("\n[SUCCESS] Registered Company with ID: " + created.getId());
    }

    private void listJobs() {
        List<JobDTO> list = jobService.getAllJobs();
        System.out.println("\n=== JOB OPENINGS (" + list.size() + ") ===");
        for (JobDTO j : list) {
            System.out.printf("ID: %d | Title: %-25s | Company: %-15s | Min CGPA: %.2f | CTC: %.2f LPA\n",
                    j.getId(), j.getTitle(), j.getCompany().getName(), j.getMinCgpa(), j.getPackageAmount());
        }
    }

    private void postJob(Scanner scanner) {
        System.out.print("Enter Job Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter Company ID: ");
        long companyId = Long.parseLong(scanner.nextLine());
        System.out.print("Enter Minimum Required CGPA: ");
        double minCgpa = Double.parseDouble(scanner.nextLine());
        System.out.print("Enter CTC Package (in LPA): ");
        double pkg = Double.parseDouble(scanner.nextLine());
        System.out.print("Enter Location: ");
        String location = scanner.nextLine();

        JobRequestDTO dto = new JobRequestDTO();
        dto.setTitle(title);
        dto.setCompanyId(companyId);
        dto.setMinCgpa(minCgpa);
        dto.setPackageAmount(pkg);
        dto.setLocation(location);
        dto.setDeadline(LocalDate.now().plusMonths(2));
        dto.setActive(true);

        JobDTO created = jobService.createJob(dto);
        System.out.println("\n[SUCCESS] Created Job Opening with ID: " + created.getId());
    }

    private void checkEligibleJobs(Scanner scanner) {
        System.out.print("Enter Student ID: ");
        long studentId = Long.parseLong(scanner.nextLine());
        List<JobDTO> list = jobService.getEligibleJobsForStudent(studentId);
        System.out.println("\n=== ELIGIBLE JOBS FOR STUDENT ID " + studentId + " (" + list.size() + ") ===");
        for (JobDTO j : list) {
            System.out.printf("Job ID: %d | %-20s | Min CGPA Required: %.2f\n", j.getId(), j.getTitle(), j.getMinCgpa());
        }
    }

    private void applyForJob(Scanner scanner) {
        System.out.print("Enter Student ID: ");
        long studentId = Long.parseLong(scanner.nextLine());
        System.out.print("Enter Job ID: ");
        long jobId = Long.parseLong(scanner.nextLine());

        ApplicationRequestDTO dto = new ApplicationRequestDTO(studentId, jobId);
        ApplicationDTO result = applicationService.applyForJob(dto);
        System.out.println("\n[SUCCESS] Application submitted successfully! Application ID: " + result.getId());
    }

    private void scheduleInterview(Scanner scanner) {
        System.out.print("Enter Application ID: ");
        long appId = Long.parseLong(scanner.nextLine());
        System.out.print("Enter Round Name (e.g., Technical Round 1): ");
        String round = scanner.nextLine();

        InterviewRequestDTO dto = new InterviewRequestDTO();
        dto.setApplicationId(appId);
        dto.setRoundName(round);
        dto.setInterviewDate(LocalDateTime.now().plusDays(2));
        dto.setLocationLink("https://meet.google.com/interview-room");

        InterviewDTO result = interviewService.scheduleInterview(dto);
        System.out.println("\n[SUCCESS] Interview Scheduled! Interview ID: " + result.getId());
    }

    private void updateInterviewResult(Scanner scanner) {
        System.out.print("Enter Interview ID: ");
        long interviewId = Long.parseLong(scanner.nextLine());
        System.out.print("Enter Result (1: PASSED, 2: FAILED): ");
        String resChoice = scanner.nextLine().trim();
        InterviewResult result = "1".equals(resChoice) ? InterviewResult.PASSED : InterviewResult.FAILED;
        System.out.print("Enter Feedback: ");
        String feedback = scanner.nextLine();

        InterviewResultUpdateDTO dto = new InterviewResultUpdateDTO(result, feedback);
        InterviewDTO updated = interviewService.updateInterviewResult(interviewId, dto);
        System.out.println("\n[SUCCESS] Interview result updated to: " + updated.getResult());
    }

    private void logPlacement(Scanner scanner) {
        System.out.print("Enter Student ID: ");
        long studentId = Long.parseLong(scanner.nextLine());
        System.out.print("Enter Company ID: ");
        long companyId = Long.parseLong(scanner.nextLine());
        System.out.print("Enter Job ID: ");
        long jobId = Long.parseLong(scanner.nextLine());
        System.out.print("Enter Package Amount (in LPA): ");
        double pkg = Double.parseDouble(scanner.nextLine());

        PlacementRequestDTO dto = new PlacementRequestDTO();
        dto.setStudentId(studentId);
        dto.setCompanyId(companyId);
        dto.setJobId(jobId);
        dto.setPackageAmount(pkg);
        dto.setJoiningDate(LocalDate.now().plusMonths(3));
        dto.setPlacementStatus("CONFIRMED");

        PlacementDTO result = placementService.createPlacement(dto);
        System.out.println("\n[SUCCESS] Final Placement Record Logged! Placement ID: " + result.getId());
    }
}
