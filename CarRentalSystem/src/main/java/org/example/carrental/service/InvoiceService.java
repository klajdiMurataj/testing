package org.example.carrental.service;

import org.example.carrental.model.booking.Booking;
import org.example.carrental.model.booking.Invoice;
import org.example.carrental.model.users.Account;
import org.example.carrental.model.vehicles.Car;
import org.example.carrental.storage.repositories.InvoiceRepository;
import org.example.carrental.util.IdGenerator;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Service for managing invoices
 */
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    /**
     * Creates and saves an invoice for a booking
     */
    public Invoice createInvoice(Booking booking, Account user, Car car) {
        String invoiceId = IdGenerator.generateId("INV");

        Invoice invoice = new Invoice(
                invoiceId,
                booking.getId(),
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                car.getDisplayName(),
                booking.getDateRange().toString(),
                booking.getPriceBreakdown(),
                LocalDateTime.now(),
                null // File path will be set when saved
        );

        // Save as .txt file
        invoiceRepository.save(invoice);

        return invoice;
    }

    /**
     * Reads an invoice content by ID
     */
    public Optional<String> getInvoiceContent(String invoiceId) {
        return invoiceRepository.readInvoice(invoiceId);
    }

    /**
     * Deletes an invoice
     */
    public boolean deleteInvoice(String invoiceId) {
        return invoiceRepository.delete(invoiceId);
    }
}