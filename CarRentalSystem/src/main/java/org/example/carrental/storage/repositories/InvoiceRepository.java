package org.example.carrental.storage.repositories;

import org.example.carrental.model.booking.Invoice;
import org.example.carrental.storage.StoragePaths;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Repository for managing invoices (stored as .txt files)
 */
public class InvoiceRepository {

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Saves an invoice as a .txt file
     */
    public void save(Invoice invoice) {
        try {
            // Ensure invoices directory exists
            Files.createDirectories(StoragePaths.INVOICES_DIR);

            String fileName = "invoice_" + invoice.getId() + ".txt";
            Path filePath = StoragePaths.INVOICES_DIR.resolve(fileName);

            StringBuilder content = new StringBuilder();
            content.append("=".repeat(60)).append("\n");
            content.append("                   INVOICE\n");
            content.append("=".repeat(60)).append("\n\n");

            content.append("Invoice ID:        ").append(invoice.getId()).append("\n");
            content.append("Booking ID:        ").append(invoice.getBookingId()).append("\n");
            content.append("Issued Date:       ").append(invoice.getIssuedAt().format(DATE_FORMAT)).append("\n\n");

            content.append("-".repeat(60)).append("\n");
            content.append("CUSTOMER INFORMATION\n");
            content.append("-".repeat(60)).append("\n");
            content.append("Name:              ").append(invoice.getUserFullName()).append("\n");
            content.append("Email:             ").append(invoice.getUserEmail()).append("\n\n");

            content.append("-".repeat(60)).append("\n");
            content.append("RENTAL DETAILS\n");
            content.append("-".repeat(60)).append("\n");
            content.append("Vehicle:           ").append(invoice.getCarDetails()).append("\n");
            content.append("Rental Period:     ").append(invoice.getRentalPeriod()).append("\n\n");

            content.append("-".repeat(60)).append("\n");
            content.append("PRICE BREAKDOWN\n");
            content.append("-".repeat(60)).append("\n");
            content.append(String.format("Base Price:        €%.2f\n",
                    invoice.getPriceBreakdown().getBasePrice()));
            content.append(String.format("VAT:               €%.2f\n",
                    invoice.getPriceBreakdown().getVatAmount()));
            content.append("-".repeat(60)).append("\n");
            content.append(String.format("TOTAL:             €%.2f\n",
                    invoice.getPriceBreakdown().getTotalPrice()));
            content.append("=".repeat(60)).append("\n\n");

            content.append("Thank you for choosing our car rental service!\n");
            content.append("Developed by Thomas Kroj, Eden Pajo\n");

            // Write to file
            try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
                writer.write(content.toString());
            }

            invoice.setFilePath(filePath.toString());

            System.out.println("✓ Invoice saved: " + filePath.toAbsolutePath());

        } catch (IOException e) {
            System.err.println("Error saving invoice: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Finds an invoice file by ID
     */
    public Optional<Path> findFileById(String invoiceId) {
        String fileName = "invoice_" + invoiceId + ".txt";
        Path filePath = StoragePaths.INVOICES_DIR.resolve(fileName);

        if (Files.exists(filePath)) {
            return Optional.of(filePath);
        }
        return Optional.empty();
    }

    /**
     * Deletes an invoice file
     */
    public boolean delete(String invoiceId) {
        Optional<Path> file = findFileById(invoiceId);
        if (file.isPresent()) {
            try {
                Files.delete(file.get());
                System.out.println("✓ Invoice deleted: " + invoiceId);
                return true;
            } catch (IOException e) {
                System.err.println("Error deleting invoice: " + e.getMessage());
            }
        }
        return false;
    }

    /**
     * Lists all invoice files
     */
    public List<String> listAll() {
        List<String> invoiceIds = new ArrayList<>();
        try {
            if (Files.exists(StoragePaths.INVOICES_DIR)) {
                Files.list(StoragePaths.INVOICES_DIR)
                        .filter(path -> path.toString().endsWith(".txt"))
                        .forEach(path -> {
                            String fileName = path.getFileName().toString();
                            String invoiceId = fileName.replace("invoice_", "").replace(".txt", "");
                            invoiceIds.add(invoiceId);
                        });
            }
        } catch (IOException e) {
            System.err.println("Error listing invoices: " + e.getMessage());
        }
        return invoiceIds;
    }

    /**
     * Reads invoice content
     */
    public Optional<String> readInvoice(String invoiceId) {
        Optional<Path> file = findFileById(invoiceId);
        if (file.isPresent()) {
            try {
                return Optional.of(Files.readString(file.get()));
            } catch (IOException e) {
                System.err.println("Error reading invoice: " + e.getMessage());
            }
        }
        return Optional.empty();
    }
}