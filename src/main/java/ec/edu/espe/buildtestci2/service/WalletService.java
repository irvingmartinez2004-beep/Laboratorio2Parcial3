package ec.edu.espe.buildtestci2.service;

import ec.edu.espe.buildtestci2.dto.WalletResponse;
import ec.edu.espe.buildtestci2.model.Wallet;
import ec.edu.espe.buildtestci2.repository.WalletRepository;


public class WalletService {

    private final WalletRepository walletRepository;
    private final RiskClient riskClient;

    public WalletService(WalletRepository walletRepository, RiskClient riskClient) {
        this.walletRepository = walletRepository;
        this.riskClient = riskClient;
    }

    //Crear una cuenta si cumple con las reglas del negocio
    public WalletResponse createWallet(String ownerEmail, double balance){
        if(ownerEmail == null || ownerEmail.isEmpty() || !ownerEmail.contains("@")){
            throw new IllegalArgumentException("Invalid email address");
        }

        if(balance < 0){
            throw new IllegalArgumentException("Initial balance cannot be negative");
        }

        if(riskClient.isBloqued(ownerEmail)){
            throw new IllegalArgumentException("User blocked");
        }

        if(walletRepository.existByOwner(ownerEmail)){
            throw new IllegalArgumentException("Wallet already exists");
        }

        Wallet wallet = new Wallet(ownerEmail, balance);
        Wallet saved = walletRepository.save(wallet);
        return new WalletResponse(saved.getId(), saved.getBalance());

    }

    //Implementar la funcion para realizar un deposito con la siguiente logica
    public double deposit(String walletId, double amount){
        if (amount <= 0 ){
            throw new IllegalArgumentException("Amount must be positive");

        }

        Wallet wallet = walletRepository.findById(walletId).orElseThrow(() -> new IllegalStateException("Wallet not found"));
        wallet.deposit(amount);

        walletRepository.save(wallet);

        return wallet.getBalance();
    }

    // Retiro de dinero
    public double withdraw(String walletId, double amount) {

        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new IllegalStateException("Wallet not found"));

        if (wallet.getBalance() < amount) {
            throw new IllegalStateException("Insufficient Balance");
        }

        wallet.withDraw(amount);

        walletRepository.save(wallet);

        return wallet.getBalance();
    }





}
