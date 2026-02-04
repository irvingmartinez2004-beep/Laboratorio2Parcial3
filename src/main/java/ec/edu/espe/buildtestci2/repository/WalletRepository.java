package ec.edu.espe.buildtestci2.repository;

import ec.edu.espe.buildtestci2.model.Wallet;

import java.util.Optional;

public interface WalletRepository {

    Wallet save(Wallet wallet);
    Optional<Wallet> findById(String id);
    boolean existByOwner(String email);
}
