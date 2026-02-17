package app;

import repository.FileTransactionRepository;
import repository.ITransactionRepository;
import service.ITransactionService;
import service.TransactionService;
import ui.ConsoleMenu;

public class Main {

    public static void main(String[] args) {
        // Dependency Injection
        // Change to InMemoryTransactionRepository() for testing
        ITransactionRepository repository = new FileTransactionRepository();
        ITransactionService service = new TransactionService(repository);
        ConsoleMenu menu = new ConsoleMenu(service);

        // Start application
        menu.start();
    }
}