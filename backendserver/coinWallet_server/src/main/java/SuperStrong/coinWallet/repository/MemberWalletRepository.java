package SuperStrong.coinWallet.repository;

import SuperStrong.coinWallet.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberWalletRepository extends JpaRepository<Wallet, String>{


}
